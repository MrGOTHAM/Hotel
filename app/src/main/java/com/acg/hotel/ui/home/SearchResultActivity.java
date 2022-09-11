package com.acg.hotel.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acg.hotel.MyApplication;
import com.acg.hotel.R;
import com.acg.hotel.adapter.RvRoomTypeAdapter;
import com.acg.hotel.bean.Pagination;
import com.acg.hotel.bean.RoomTypeBean;
import com.acg.hotel.util.DateTimeTools;
import com.acg.hotel.util.OkHttpTool;
import com.acg.hotel.util.ServiceUrls;
import com.acg.hotel.widget.MyActionBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.squareup.timessquare.CalendarPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @Classname SearchResultActivity
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/31 14:39
 * @Created by an
 */
public class SearchResultActivity extends AppCompatActivity {

    private static final String TAG = "SearchResultActivity";
    private MyApplication mMyApplication;
    private Activity mActivityContext;
    private Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Date[] mSelectDates = new Date[2];

    private RecyclerView mRecyclerView;
    private TextView mInsertDateTv;
    private TextView mInsertWeekdayTv;
    private TextView mOutDateTv;
    private TextView mOutWeekdayTv;
    private TextView mTotalNightTv;
    private LinearLayout mHomeDateSelectLl;
    private AlertDialog mDialogCalendarPicker;   // 日期选择弹窗
    private CalendarPickerView mDialogView;      // 日期控件
    private MyActionBar mMyActionBar;
    private List<RoomTypeBean> mListDate;       // 传入recyclerview适配器的数据

    private int mPageNum = 1;       // 当前页
    private int mPageSize = 5;      // 每页的数据条数
    private RvRoomTypeAdapter mRvRoomTypeAdapter;
    private RefreshLayout mRefreshSrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mActivityContext = this;
        mMyApplication = (MyApplication) getApplication();
        // 获取控件
        init();
        // 初始化页面
        initView();
    }

    private void initView() {
        mMyActionBar = findViewById(R.id.myActionBar);
        // 设置为 0 就是没有图标
        mMyActionBar.setData("客房搜索", R.drawable.ic_custom_actionbar_left, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                // 关闭activity
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        initWidgetView();
    }

    private void initWidgetView() {
        // 设置日期选择控制权限
        // 10天后----限制日期控件
        Date dateTenDay = DateTimeTools.dateAddDay(DateTimeTools.getOnlyDate(new Date()), 11);
        // 显示默认：1晚，今天入住，明天离店
        showSetSelectDate();
        // === 初始化日期选择控件    使用 View.inflate(context,resource,parent) 生成View
        // 加载日期控件布局
        mDialogView = (CalendarPickerView) View.inflate(mActivityContext, R.layout.dialog_calendar_picker, null);
        // 初始化日期选择器
        mDialogView.init(DateTimeTools.getOnlyDate(new Date()), dateTenDay)         // 限定 日期选择范围
                .inMode(CalendarPickerView.SelectionMode.RANGE) // 设置时间段的选择
                .withSelectedDates(Arrays.asList(mSelectDates)); // 设置初始化选择的时间
        // 初始化AlertDialog
        mDialogCalendarPicker = new AlertDialog.Builder(mActivityContext)
                .setTitle("请选择入住和离店日期！")
                .setView(mDialogView) // 设置dialog显示布局
                .setNeutralButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss(); //关闭Dialog
                    }
                }).setPositiveButton("确定", null)
                .create();


        // ====recyclerView 初始化
        // 1.设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivityContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 2 设置适配器
        mListDate = new ArrayList<>();
        // 2.1 初始化适配器
        mRvRoomTypeAdapter = new RvRoomTypeAdapter(mListDate, mActivityContext, mMyApplication);
        // 2.2 设置点击事件
        mRvRoomTypeAdapter.setItemClickListener(new RvRoomTypeAdapter.OnItemClickListener() {
            // 每一项的点击事件
            @Override
            public void onItemClick(View view, RoomTypeBean data, int position) {
                Toast.makeText(mActivityContext, String.valueOf(data.getRoomTypeId()), Toast.LENGTH_SHORT).show();
            }

            // 客房类型图片的点击事件
            @Override
            public void onImageClick(View view, RoomTypeBean data, int position) {
                Toast.makeText(mActivityContext, data.getPicture(), Toast.LENGTH_SHORT).show();
            }
        });

        // 2.3 设置RecyclerView的适配器
        mRecyclerView.setAdapter(mRvRoomTypeAdapter);
        // 3. 添加安卓自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivityContext, DividerItemDecoration.VERTICAL));
        // 4. 设置增加或删除条目动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // 加载数据
        loadListDate(true);

    }

    private void loadListDate(Boolean isRefresh) {
        Log.e(TAG, " 开始请求后台");
        if (isRefresh) { // 刷新
            mPageNum = 1; // 加载第一页
        } else { // 加载下一页
            mPageNum++;
        }

        // 请求服务端数据
        String url = ServiceUrls.getReserveMethodUrl("selectRoomTypeByDate"); // 服务端URl
        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        // 准备服务端所需参数
        Map<String, Object> map = new HashMap<>();
        map.put("startDate", slf.format(mSelectDates[0]));
        map.put("endDate", slf.format(mSelectDates[1]));
        map.put("pageSize", mPageSize);
        map.put("pageNum", mPageNum);

        // 异步请求
        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                Log.e(TAG, "请求成功！！！");
                mActivityContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode == 200) {
                            try {
                                Log.e(TAG, response);
                                JSONObject jsonObject = new JSONObject(response);
                                String data1 = jsonObject.getString("data");
                                // 请求成功
                                Type type = new TypeToken<Pagination<RoomTypeBean>>() {
                                }.getType();
                                // 反序列化
                                Pagination<RoomTypeBean> pagination = mGson.fromJson(data1, type);
                                Log.e(TAG, pagination.toString());
                                // 处理数据
                                if (pagination != null) {
                                    // 将分页数据添加到recyclerview适配器
                                    //获取数据总页数
                                    int totalPage = pagination.getTotalPage();
                                    List<RoomTypeBean> roomTypeBeanList = pagination.getData();
                                    for (RoomTypeBean rb : roomTypeBeanList) {
                                        Log.e(TAG, rb.toString());
                                    }
                                    mRvRoomTypeAdapter.addItem(roomTypeBeanList, mPageNum);

                                    if (isRefresh) {
                                        mRefreshSrl.finishRefresh(true); // 刷新完成
                                        if (totalPage > mPageNum) {
                                            mRefreshSrl.setNoMoreData(false);
                                        }
                                    } else {
                                        mRefreshSrl.finishLoadMore(true);//加载更多完成
                                    }

                                    //如果当前页数等于总页数，设置所有页数加载完成提示
                                    if (mPageNum == totalPage) {
                                        //告诉SmartRefreshLayout没有更多数据了
                                        mRefreshSrl.finishLoadMoreWithNoMoreData();
                                    }
                                } else {
                                    //加载失败
                                    if (isRefresh) {
                                        mRefreshSrl.finishRefresh(false);//刷新失败
                                    } else {
                                        mRefreshSrl.finishLoadMore(false);//加载更多失败
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //加载失败
                            if (isRefresh) {
                                mRefreshSrl.finishRefresh(false);//刷新失败
                            } else {
                                mRefreshSrl.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }

    // 显示选中的日期段
//    Locale.getDefault()   设置默认的地区
    private void showSetSelectDate() {
        SimpleDateFormat slfDate = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
        SimpleDateFormat slfWeek = new SimpleDateFormat("EEEE", Locale.CHINESE);
        mInsertDateTv.setText(slfDate.format(mSelectDates[0]));
        mInsertWeekdayTv.setText(slfWeek.format(mSelectDates[0]));
        mOutDateTv.setText(slfDate.format(mSelectDates[1]));
        mOutWeekdayTv.setText(slfWeek.format(mSelectDates[1]));

        // 计算时间差
        int totalNight = (int) (mSelectDates[1].getTime() - mSelectDates[0].getTime()) / (24 * 60 * 60 * 1000);
        // 设置总共入住日期
        mTotalNightTv.setText(String.format(Locale.getDefault(), "共%1$d晚", totalNight));
    }

    private void init() {
        mRecyclerView = findViewById(R.id.rv_search_result_list);
        mInsertDateTv = findViewById(R.id.tv_search_result_insert_date);
        mInsertWeekdayTv = findViewById(R.id.tv_search_result_insert_weekday);
        mOutDateTv = findViewById(R.id.tv_search_result_out_date);
        mOutWeekdayTv = findViewById(R.id.tv_search_result_out_weekday);
        mTotalNightTv = findViewById(R.id.tv_search_result_total_night);
        mHomeDateSelectLl = findViewById(R.id.ll_search_result_date_select);
        mRefreshSrl = findViewById(R.id.srl_search_result);

        // 获取来自HomeFragment传递过来的参数
        long[] selectDates = getIntent().getLongArrayExtra("longSelectDates");
        if (selectDates != null && selectDates.length == 2) {
            // 将接收到的Long型时间转换为Date
            mSelectDates[0] = new Date(selectDates[0]);
            mSelectDates[1] = new Date(selectDates[1]);
        } else {
            Toast.makeText(mActivityContext, "非法访问", Toast.LENGTH_SHORT).show();
        }

        // 设置日期选择点击事件
        mHomeDateSelectLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogCalendarPicker.show();     // 显示入店离店日期选择对话框
                // 为了避免点击 positive 按钮后直接关闭 dialog，把点击事件拿出来设置
                // 需要在.show();后设置
                mDialogCalendarPicker.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                List<Date> listSelectDate = mDialogView.getSelectedDates();
                                // 日期要选择两个
                                if (listSelectDate.size() > 1) {
                                    mSelectDates[0] = listSelectDate.get(0);
                                    mSelectDates[1] = listSelectDate.get(listSelectDate.size() - 1);
                                    // 显示选择的日期
                                    showSetSelectDate();
                                    // !!!刷新RecyclerView
                                    loadListDate(true);
                                    mDialogCalendarPicker.dismiss();//关闭dialog
                                } else {
                                    Toast.makeText(mActivityContext, "入住日期和离店日期不能是同一天", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        // 下拉刷新整个  recyclerview
        mRefreshSrl.setOnRefreshListener(refreshLayout -> loadListDate(true));
        // 上拉，加载更多数据 recyclerView
        mRefreshSrl.setOnLoadMoreListener(refreshLayout -> loadListDate(false));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("longSelectDates", new long[]{mSelectDates[0].getTime(), mSelectDates[1].getTime()});
        setResult(Activity.RESULT_OK, intent);
        Log.e(TAG, "back ok");
        super.onBackPressed();
    }

}

package com.acg.hotel.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.acg.hotel.R;
import com.acg.hotel.adapter.HomeBannerAdapter;
import com.acg.hotel.bean.BannerBean;
import com.acg.hotel.util.DateTimeTools;
import com.acg.hotel.util.OkHttpTool;
import com.acg.hotel.util.ServiceUrls;
import com.acg.hotel.util.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.timessquare.CalendarPickerView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Classname HomeFragment
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/28 17:49
 * @Created by an
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private static final int OPEN_LOGIN_PAGE_REQUEST_CODE = 1001;
    // 存放选择的入住和离店日期
    private Date[] mSelectDate = new Date[2];
    private Activity mActivityContext;
    private Banner mHomeHeaderBanner;
    private RadioButton mCustomerPhoneRb;
    private RadioButton mHotelMapRb;
    private Button mSearchBtn;
    private TextView mInsertDateTv;
    private TextView mInsertWeekdayTv;
    private TextView mOutDateTv;
    private TextView mOutWeekdayTv;
    private TextView mTotalNightTv;
    private LinearLayout mHomeDateSelectLl;

    private Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private CalendarPickerView mDialogView;
    private AlertDialog mDialogCalendarPicker;

    // 根据生命周期可知， 在Fragment被添加到Activity中会回调，且只会回调一次
    // 这个时候可以拿到Activity的context
    @Override
    public void onAttach(@NonNull Context context) {
        this.mActivityContext = (Activity) context;
        super.onAttach(context);
    }

    // fragment 中 不实现onCreate 而需要实现 onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 通过 inflater(打气筒) 把布局转换为 view对象
        // inflate() 的作用就是将一个xml定义的布局文件实例化为view控件对象
        //      resource：View的layout的Id
        //      root : 需要附加到resource资源文件的根控件，inflate():返回一个View的对象     根控件:container
        //      如果第三个参数attachToRoot为True，就将这个root作为根对象返回
        //      否则仅仅将这个root对象的LayoutParams属性附加到resource对象的根布局对象上，也就是布局文件resource的最外层的View上
        //      如果root为null 则会忽略view根对象的LayoutParams属性
        //      attachToRoot : 是否将root 附加到布局文件的根视图上

        // fragment 布局View对象
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initFindViewById(view);

        initView();

        // 设置控件事件
        setViewListener();

        return view;
    }

    private void setViewListener() {
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
                                    mSelectDate[0] = listSelectDate.get(0);
                                    mSelectDate[1] = listSelectDate.get(listSelectDate.size() - 1);
                                    // 显示选择的日期
                                    showSetSelectDate();
                                    mDialogCalendarPicker.dismiss();//关闭dialog
                                } else {
                                    Toast.makeText(mActivityContext, "入住日期和离店日期不能是同一天", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


    }

    // 初始化页面
    private void initView() {
        // ===== 显示轮播
        // ===== 获取服务端轮播数据
        String url = ServiceUrls.getMainPageMethodUrl("getBannerInfo");
        Log.e(TAG, "url= " + url);
        // =====发送请求
        OkHttpTool.httpPost(url, null, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                mActivityContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (isSuccess && responseCode == 200 && Tools.isNotNull(response)) {
                                // banner列表数据在response的data中
                                JSONObject jsonObject = new JSONObject(response);
                                // 反序列化   拿到反序列化所拿到的类型
                                Type type = new TypeToken<List<BannerBean>>() {
                                }.getType();
                                List<BannerBean> bannerList = mGson.fromJson(jsonObject.getString("data"), type);

                                // 显示轮播 需要适配器
                                mHomeHeaderBanner.setAdapter(new HomeBannerAdapter(bannerList, mActivityContext))
                                        .setIndicator(new CircleIndicator(mActivityContext)) // 设置圆形指示器
                                        .setIndicatorSelectedColorRes(R.color.colorPrimary) // 设置指示器颜色
                                        .isAutoLoop(true)       // 自动循环
                                        .setLoopTime(10 * 1000) // 轮播时间
                                        .setOnBannerListener(new OnBannerListener<BannerBean>() {   // 设置点击事件
                                            @Override
                                            // 这里的data就是BannerBean ，强制转换以下就行了
                                            public void OnBannerClick(BannerBean data, int position) {
                                                // 点击事件
                                                int bannerId = data.getBannerId();
                                                Toast.makeText(mActivityContext, String.valueOf(bannerId), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(mActivityContext, "轮播加载失败！", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        initDateWidget();

    }

    private void initDateWidget() {
        Log.e(TAG, " 展示数据");
        //入住离店日期选择部分
        //入店日期
        Date dateStart = DateTimeTools.getOnlyDate(new Date());
        // 默认离店时间为第二天
        Date dateEnd = DateTimeTools.dateAddDay(new Date(), 1);

        if (mSelectDate[0] == null || mSelectDate[1] == null) {
            mSelectDate[0] = dateStart;
            mSelectDate[1] = dateEnd;
        }
        // 设置日期选择控制权限
        // 10天后----限制日期控件
        Date dateTenDay = DateTimeTools.dateAddDay(dateStart, 11);
        // 显示默认：1晚，今天入住，明天离店
        showSetSelectDate();
        // === 初始化日期选择控件    使用 View.inflate(context,resource,parent) 生成View
        // 加载日期控件布局
        mDialogView = (CalendarPickerView) View.inflate(mActivityContext, R.layout.dialog_calendar_picker, null);
        // 初始化日期选择器
        mDialogView.init(dateStart, dateTenDay)         // 限定 日期选择范围
                .inMode(CalendarPickerView.SelectionMode.RANGE) // 设置时间段的选择
                .withSelectedDates(Arrays.asList(mSelectDate)); // 设置初始化选择的时间
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
    }

    // 显示选中的日期段
//    Locale.getDefault()   设置默认的地区
    private void showSetSelectDate() {
        SimpleDateFormat slfDate = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
        SimpleDateFormat slfWeek = new SimpleDateFormat("EEEE", Locale.CHINESE);
        mInsertDateTv.setText(slfDate.format(mSelectDate[0]));
        mInsertWeekdayTv.setText(slfWeek.format(mSelectDate[0]));
        mOutDateTv.setText(slfDate.format(mSelectDate[1]));
        mOutWeekdayTv.setText(slfWeek.format(mSelectDate[1]));

        // 计算时间差
        int totalNight = (int) (mSelectDate[1].getTime() - mSelectDate[0].getTime()) / (24 * 60 * 60 * 1000);
        // 设置总共入住日期
        mTotalNightTv.setText(String.format(Locale.getDefault(), "共%1$d晚", totalNight));
    }

    private void initFindViewById(View view) {
        mHomeHeaderBanner = view.findViewById(R.id.banner_home_header);
        mCustomerPhoneRb = view.findViewById(R.id.rb_home_customer_phone);
        mHotelMapRb = view.findViewById(R.id.rb_home_hotel_map);
        mSearchBtn = view.findViewById(R.id.btn_home_search);
        mInsertDateTv = view.findViewById(R.id.tv_home_insert_date);
        mInsertWeekdayTv = view.findViewById(R.id.tv_home_insert_weekday);
        mOutDateTv = view.findViewById(R.id.tv_home_out_date);
        mOutWeekdayTv = view.findViewById(R.id.tv_home_out_weekday);
        mTotalNightTv = view.findViewById(R.id.tv_home_total_night);
        mHomeDateSelectLl = view.findViewById(R.id.ll_home_date_select);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivityContext, SearchResultActivity.class);
                // 通过bundle，将用户选择的日期传递给SearchResultActivity
                intent.putExtra("longSelectDates", new long[]{mSelectDate[0].getTime(), mSelectDate[1].getTime()});
                startActivityForResult(intent, OPEN_LOGIN_PAGE_REQUEST_CODE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Log.e(TAG, " 没有收到了数据");
            return;
        } else {
            long[] longSelectDates = data.getLongArrayExtra("longSelectDates");
            if (longSelectDates != null && longSelectDates.length == 2) {
                mSelectDate[0] = new Date(longSelectDates[0]);
                mSelectDate[1] = new Date(longSelectDates[1]);
                Log.e(TAG, " 收到了数据");
                initDateWidget();
            }
        }
    }
}

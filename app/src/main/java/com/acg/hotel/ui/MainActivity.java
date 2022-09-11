package com.acg.hotel.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.acg.hotel.MyApplication;
import com.acg.hotel.R;
import com.acg.hotel.bean.MemberBean;

import com.acg.hotel.ui.home.HomeFragment;
import com.acg.hotel.ui.mine.MineFragment;
import com.acg.hotel.ui.order.OrderFragment;
import com.acg.hotel.util.OkHttpTool;
import com.acg.hotel.util.SPUtils;
import com.acg.hotel.util.ServiceUrls;
import com.acg.hotel.util.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// AppCompatActivity 和 Activity 差不多的， 只是AppCompatActivity里面多一些功能 都是Activity延伸而来
// 这两个用哪个 看自己
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private Activity mActivityContext;
    private MyApplication mMyApplication;
    private LinearLayout mContentLl; // 页面容器
    private RadioButton mHomeRb;
    private RadioButton mOrderRb;
    private RadioButton mMineRb;
    private Fragment[] fragments = new Fragment[]{null, null, null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        initView();

    }

    private void init(){
        mActivityContext = this;
        mMyApplication = (MyApplication) getApplication();

        mContentLl = findViewById(R.id.ll_main_content);
        mHomeRb = findViewById(R.id.rb_main_home);
        mOrderRb = findViewById(R.id.rb_main_order);
        mMineRb = findViewById(R.id.rb_main_mine);

        mHomeRb.setOnClickListener(this);
        mOrderRb.setOnClickListener(this);
        mMineRb.setOnClickListener(this);





    }

    private void initView() {
        // 先默认选中home 及 homeFragment
        mHomeRb.setChecked(true);
        switchFragment(0);
        // 获取是否已经登陆过
        String phone = (String) SPUtils.get(mActivityContext, ServiceUrls.SP_MEMBER_PHONE, "");
        String password = (String) SPUtils.get(mActivityContext, ServiceUrls.SP_MEMBER_PASSWORD, "");

        // 如果登陆过 则自动登录
        if (Tools.isNotNull(phone)) {

            Map<String, Object> map = new HashMap<>();
            map.put("phone", phone);
            // 保存进sharepreference的已经是加密后的
            map.put("password", password);
            String url = ServiceUrls.getMemberMethodUrl("loginByPassword");
            OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                @Override
                public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                    mActivityContext.runOnUiThread(new Runnable() {
                        String str = "无法访问服务器， 请检查网络链接！";

                        @Override
                        public void run() {
                            try {
                                if (isSuccess && responseCode == 200) {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int code = jsonObject.getInt("code");
                                    str = jsonObject.getString("message");

                                    if (code == 200) {
                                        String data = jsonObject.getString("data");
                                        Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                        MemberBean memberBean = mGson.fromJson(data, MemberBean.class);
                                        if (memberBean != null) {
                                            // 将用户信息保存到Application(内存)
                                            mMyApplication.setLoginMember(memberBean);
                                            // 将phone 和 password 存入 SharePreferences
                                            // 实际项目中避免这样做， 安全性不高
                                            SPUtils.put(mActivityContext, ServiceUrls.SP_MEMBER_PHONE, memberBean.getPhone().trim());
                                            SPUtils.put(mActivityContext, ServiceUrls.SP_MEMBER_PASSWORD, memberBean.getPassword().trim());


                                        } else {
                                            str = "登陆失败";
                                        }
                                        Log.e(TAG, memberBean.toString());
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(mActivityContext, str, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

        // fragment 基本操作
//        // 在Activity中 显示fragment
//        // 1. 获取fragment管理器
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
//        // 2. 开启fragment事务
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        // 3. 将要显示fragment的对象添加到fragmentTransaction
//        HomeFragment homeFragment = new HomeFragment();
//        // 4. 把homeFragment 添加到 想要添加的布局中
//        transaction.add(R.id.ll_main_content, homeFragment);
//        // 5. 显示fragment
//        transaction.show(homeFragment);
//        // 6. 提交事务
//        transaction.commit();


    }

    // 懒加载 切换fragment显示
    private void switchFragment(int fragmentIndex) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragments[fragmentIndex] == null) {
            switch (fragmentIndex) {
                case 0:
                    HomeFragment homeFragment =new HomeFragment();
                    fragments[0] = homeFragment;
                    break;
                case 1:
                    OrderFragment orderFragment = new OrderFragment();
                    fragments[1] = orderFragment;
                    break;
                case 2:
                    MineFragment mineFragment = new MineFragment();
                    fragments[2] = mineFragment;
                    break;
            }
            transaction.add(R.id.ll_main_content, fragments[fragmentIndex]);
        }
        for (int i = 0; i < fragments.length; i++) {
            if (i!=fragmentIndex && fragments[i]!=null){
                transaction.hide(fragments[i]);
            }
        }
        transaction.show(fragments[fragmentIndex]);
        transaction.commit();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_main_home:
                switchFragment(0);
                break;
            case R.id.rb_main_order:
                switchFragment(1);
                break;
            case R.id.rb_main_mine:
                switchFragment(2);
                break;
        }





    }
}
package com.acg.hotel.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.acg.hotel.MyApplication;
import com.acg.hotel.R;
import com.acg.hotel.bean.MemberBean;
import com.acg.hotel.util.DeviceUtils;
import com.acg.hotel.util.MD5Util;
import com.acg.hotel.util.OkHttpTool;
import com.acg.hotel.util.SPUtils;
import com.acg.hotel.util.ServiceUrls;
import com.acg.hotel.util.Tools;
import com.acg.hotel.widget.MyActionBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private LinearLayout mLoginSmsLL;
    private LinearLayout mLoginPwdLL;
    private TextView mLoginSmsTv;
    private TextView mLoginPwdTv;
    private Button mLoginBtn;
    private TextView mToForgetPwdTv;
    private TextView mToRegisterTv;
    private EditText mLoginSmsEt;
    private EditText mLoginPwdEt;
    private Button mLoginSmsBtn;
    private EditText mPhoneEt;

    private Activity mActivityContext;
    private MyApplication mMyApplication;
    private MyActionBar mMyActionBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivityContext = this;
        mMyApplication = (MyApplication) getApplication();
        setViews();

    }

    private void setViews() {
        mLoginBtn = findViewById(R.id.btn_login);
        mLoginSmsLL = findViewById(R.id.ll_login_sms);
        mLoginPwdLL = findViewById(R.id.ll_login_pwd);
        mLoginSmsTv = findViewById(R.id.tv_login_sms);
        mLoginPwdTv = findViewById(R.id.tv_login_pwd);
        mToForgetPwdTv = findViewById(R.id.tv_login_forget_pwd);
        mToRegisterTv = findViewById(R.id.tv_login_register);
        mLoginSmsEt = findViewById(R.id.et_login_sms_code);
        mLoginPwdEt = findViewById(R.id.et_login_pwd);
        mLoginSmsBtn = findViewById(R.id.btn_login_sms_code);
        mPhoneEt = findViewById(R.id.et_login_phone);
        
        mLoginBtn.setOnClickListener(this);
        mLoginSmsTv.setOnClickListener(this);
        mLoginPwdTv.setOnClickListener(this);
        mToForgetPwdTv.setOnClickListener(this);
        mToRegisterTv.setOnClickListener(this);
        mLoginSmsBtn.setOnClickListener(this);

        mMyActionBar = findViewById(R.id.myActionBar);
        // 设置为 0 就是没有图标
        mMyActionBar.setData("登录", R.drawable.ic_custom_actionbar_left, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                // 关闭activity
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        String url;
        String phone;
        Map<String, Object> map;
        switch (view.getId()) {
            case R.id.btn_login:        // 验证码登录 or 密码登录
                phone = mPhoneEt.getText() == null ? "" : mPhoneEt.getText().toString();
                String smsCode = mLoginSmsEt.getText() == null ? "" : mLoginSmsEt.getText().toString();
                String pwd = mLoginPwdEt.getText() == null ? "" : mLoginPwdEt.getText().toString();
                map = new HashMap<>();
                Log.e(TAG, "phone::" + phone);
                Log.e(TAG, "pwd::" + pwd);
                Log.e(TAG, "smsCode::" + smsCode);
                if (!Tools.isMobile(phone)) {
                    mPhoneEt.setError("请输入正确手机号！");
                    return;
                }
                map.put("phone", phone);

                // 如果是短信验证码登录
                if (mLoginSmsLL.getVisibility() == View.VISIBLE) {
                    if (Tools.isNotNull(smsCode)) {
                        map.put("smsCode", smsCode);
                        url = ServiceUrls.getMemberMethodUrl("loginBySmsCode");
                    } else {
                        mLoginSmsEt.setError("请输入验证码！");
                        return;
                    }

                }// 否则是密码登录
                else {
                    if (Tools.isNotNull(pwd)) {
                        map.put("password", MD5Util.getMD5(pwd));
                        url = ServiceUrls.getMemberMethodUrl("loginByPassword");
                    } else {
                        mLoginPwdEt.setError("请输入密码！");
                        return;
                    }
                }


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

//                                                // ===== 使用sharePreference
//                                                // 1.获取SharePreference          Context.MODE_PRIVATE  只能当前app访问以下数据
//                                                SharedPreferences sp = mActivityContext.getSharedPreferences("loginData", Context.MODE_PRIVATE);
//                                                // 2.获取SharePreference编辑器
//                                                SharedPreferences.Editor editor = sp.edit();
//                                                // 3.使用Editor保存参数
//                                                editor.putString(ServiceUrls.SP_MEMBER_PHONE, memberBean.getPhone());
//                                                editor.putString(ServiceUrls.SP_MEMBER_PASSWORD, memberBean.getPassword());
//                                                // 4.使用Editor提交参数
//                                                editor.apply();
//
//                                                // =====从SharePreference 取出数据
//                                                Log.e(TAG, "sp: "+sp.getString(ServiceUrls.SP_MEMBER_PHONE,""));

                                            } else {
                                                str = "登陆失败";
                                            }
                                            Log.e(TAG, memberBean.toString());
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                break;
            case R.id.tv_login_sms:     // 验证码登录
                mLoginSmsLL.setVisibility(View.VISIBLE);
                mLoginPwdLL.setVisibility(View.GONE);

                mLoginPwdTv.setVisibility(View.VISIBLE);
                mLoginSmsTv.setVisibility(View.GONE);
                break;
            case R.id.tv_login_pwd:     // 密码登录
                mLoginSmsLL.setVisibility(View.GONE);
                mLoginPwdLL.setVisibility(View.VISIBLE);

                mLoginPwdTv.setVisibility(View.GONE);
                mLoginSmsTv.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_login_forget_pwd:
                intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login_sms_code:   // 发送验证码
                // 获取参数
                phone = mPhoneEt.getText() == null ? "" : mPhoneEt.getText().toString();
                if (!Tools.isMobile(phone)) {
                    mPhoneEt.setError("请输入正确的手机号！");
                    return;
                }
                // 获取设备Id
                String deviceId = DeviceUtils.getDeviceUUID(LoginActivity.this);
                // 准备URL
                url = ServiceUrls.getMemberMethodUrl("sendCode");

                map = new HashMap<>();
                map.put("phone", phone);
                map.put("deviceId", deviceId);
                map.put("isRegister", false);
                JSONObject jsonObject = new JSONObject(map);
                Log.e(TAG, "点击了验证码！");

                // 发送网络请求
                OkHttpTool.httpPostJson(url, jsonObject.toString(), new OkHttpTool.ResponseCallback() {
                    // 这个是在子线程中执行的
                    @Override
                    public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                        // 使用这个方法更新ui线程
                        mActivityContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 禁用发送按钮
                                mLoginSmsBtn.setEnabled(false);
                                if (isSuccess && responseCode == 200) {
                                    // 重新发送短信倒计时 -----Android自带倒计时类 倒计时60s, 步长1s
                                    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                                        @Override
                                        public void onTick(long l) {
                                            mLoginSmsBtn.setText(String.format(Locale.getDefault(), "重新获取(%1$d)", (l / 1000)));
                                        }

                                        @Override
                                        public void onFinish() {
                                            mLoginSmsBtn.setEnabled(true);
                                            mLoginSmsBtn.setText("获取验证码");
                                        }
                                    };
                                    timer.start();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        String message = jsonObject.getString("message");

                                        if (code == 200) {
                                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                        } else {
                                            // 发送失败
                                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                        }
                                        return;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Toast.makeText(LoginActivity.this, "验证码发送失败，请稍后再试！", Toast.LENGTH_LONG).show();
                                mLoginSmsBtn.setEnabled(true);
                            }
                        });
                    }
                });
                break;
        }
    }
}

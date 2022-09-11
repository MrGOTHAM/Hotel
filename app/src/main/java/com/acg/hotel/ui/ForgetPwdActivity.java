package com.acg.hotel.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ForgetPwdActivity";
    private Button mForgetBtn;
    private EditText mPasswordEt;
    private EditText mPhoneEt;
    private Button mSmsCodeBtn;
    private EditText mSmsCodeEt;
    private EditText mSurePasswordEt;
    private TextView mToLoginTv;
    private TextView mToRegisterTv;

    private Activity mActivityContext;
    private MyApplication mMyApplication;
    private MyActionBar mMyActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        mActivityContext = this;
        mMyApplication = (MyApplication) getApplication();

        init();
    }

    private void init() {
        mForgetBtn = findViewById(R.id.btn_forget);
        mPasswordEt = findViewById(R.id.et_forget_new_password);
        mPhoneEt = findViewById(R.id.et_forget_phone);
        mSmsCodeBtn = findViewById(R.id.btn_forget_sms_code);
        mSmsCodeEt = findViewById(R.id.et_forget_sms_code);
        mSurePasswordEt = findViewById(R.id.et_forget_sure_password);
        mToLoginTv = findViewById(R.id.tv_forget_login);
        mToRegisterTv = findViewById(R.id.tv_forget_register);

        mForgetBtn.setOnClickListener(this);
        mSmsCodeBtn.setOnClickListener(this);

        mMyActionBar = findViewById(R.id.myActionBar);
        // 设置为 0 就是没有图标
        mMyActionBar.setData("重置密码", R.drawable.ic_custom_actionbar_left, 0, new MyActionBar.ActionBarClickListener() {
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
        String phone;
        Map<String, Object> map;
        String url;
        switch (view.getId()){
            case R.id.btn_forget:
                 phone = mPhoneEt.getText() == null ? "" : mPhoneEt.getText().toString();
                String password = mPasswordEt.getText() == null ? "" : mPasswordEt.getText().toString();
                String smsCode = mSmsCodeEt.getText() == null ? "" : mSmsCodeEt.getText().toString();
                String surePassword = mSurePasswordEt.getText() == null ? "" : mSurePasswordEt.getText().toString();

                // 验证数据
                if (!Tools.isMobile(phone)) {
                    mPhoneEt.setError("请输入正确的手机号码！");
                    return;
                }
                if (!Tools.isNotNull(smsCode)) {
                    mSmsCodeEt.setError("请输入短信验证码！");
                    return;
                }
                if (!Tools.isNotNull(password) || password.length() < 8) {
                    mPasswordEt.setError("密码强度不够!");
                    return;
                }
                if (!password.equals(surePassword)) {
                    mSurePasswordEt.setError("两次密码不一致！");
                    return;
                }

                // url
                url = ServiceUrls.getMemberMethodUrl("forgetBySmsCode");
                // 准备数据
                map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", MD5Util.getMD5(password));
                map.put("smsCode", smsCode);


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
                                            Toast.makeText(mActivityContext, str, Toast.LENGTH_LONG).show();

                                            // 清除内存中保存的用户信息
                                            mMyApplication.setLoginMember(null);

                                            // 清除 SharePreferences中保存的数据
                                            // 实际项目中避免这样做， 安全性不高
                                            SPUtils.remove(mActivityContext, ServiceUrls.SP_MEMBER_PHONE);
                                            SPUtils.remove(mActivityContext, ServiceUrls.SP_MEMBER_PASSWORD);

                                            //跳转到登陆页面
                                            Intent intent = new Intent(mActivityContext, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(ForgetPwdActivity.this,str,Toast.LENGTH_LONG ).show();
                            }
                        });
                    }
                });
                break;
            case R.id.btn_forget_sms_code:
                // 获取参数
                phone = mPhoneEt.getText() == null ? "" : mPhoneEt.getText().toString();
                if (!Tools.isMobile(phone)) {
                    mPhoneEt.setError("请输入正确的手机号！");
                    return;
                }
                // 获取设备Id
                String deviceId = DeviceUtils.getDeviceUUID(ForgetPwdActivity.this);
                // 准备URL
                url = ServiceUrls.getMemberMethodUrl("sendCode");

                map = new HashMap<>();
                map.put("phone", phone);
                map.put("deviceId", deviceId);
                map.put("isRegister", false);
                JSONObject jsonObject = new JSONObject(map);
                Log.e(TAG,"点击了验证码！");

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
                                mSmsCodeBtn.setEnabled(false);
                                if (isSuccess && responseCode == 200) {
                                    // 重新发送短信倒计时 -----Android自带倒计时类 倒计时60s, 步长1s
                                    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                                        @Override
                                        public void onTick(long l) {
                                            mSmsCodeBtn.setText(String.format(Locale.getDefault(), "重新获取(%1$d)", (l / 1000)));
                                        }

                                        @Override
                                        public void onFinish() {
                                            mSmsCodeBtn.setEnabled(true);
                                            mSmsCodeBtn.setText("获取验证码");
                                        }
                                    };
                                    timer.start();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        String message = jsonObject.getString("message");

                                        if (code == 200) {
                                            Toast.makeText(ForgetPwdActivity.this, message, Toast.LENGTH_LONG).show();
                                        } else {
                                            // 发送失败
                                            Toast.makeText(ForgetPwdActivity.this, message, Toast.LENGTH_LONG).show();
                                        }
                                        return;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Toast.makeText(ForgetPwdActivity.this, "验证码发送失败，请稍后再试！", Toast.LENGTH_LONG).show();
                                mSmsCodeBtn.setEnabled(true);
                            }
                        });
                    }
                });
                break;
        }
    }
}
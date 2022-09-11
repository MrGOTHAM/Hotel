package com.acg.hotel.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private final Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static final String TAG = "RegisterActivity";
    private TextView mToLoginTv;
    private TextView mToForgetTv;
    private Button mRegisterBtn;
    private Button mRegisterSmsCodeBtn;
    private RadioGroup mRegisterSexRg;
    private RadioButton mRegisterMaleRb;
    private RadioButton mRegisterFemaleRb;
    private EditText mRegisterPwdEt;
    private EditText mRegisterSurePwdEt;
    private EditText mNameEt;
    private EditText mPhoneEt;
    private EditText mRegisterSmsCodeEt;

    private Activity mActivityContext;
    private MyApplication mMyApplication;
    private MyActionBar mMyActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityContext = this;
        mMyApplication = (MyApplication) getApplication();


        setContentView(R.layout.activity_register);
        // 初始化工作
        init();

        mRegisterSmsCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取参数
                String phone = mPhoneEt.getText() == null ? "" : mPhoneEt.getText().toString();
                if (!Tools.isMobile(phone)) {
                    mPhoneEt.setError("请输入正确的手机号！");
                    return;
                }
                // 获取设备Id
                String deviceId = DeviceUtils.getDeviceUUID(RegisterActivity.this);
                // 准备URL
                String url = ServiceUrls.getMemberMethodUrl("sendCode");
                // 准备参数 以下两种方法都可以
//                UserSmsParam userSmsParam = new UserSmsParam();
//                userSmsParam.setPhone(phone);
//                userSmsParam.setDeviceId(deviceId);
//                userSmsParam.setRegister(true);
//                String s = mGson.toJson(userSmsParam);

                Map<String, Object> map = new HashMap<>();
                map.put("phone", phone);
                map.put("deviceId", deviceId);
                map.put("isRegister", true);
                JSONObject jsonObject = new JSONObject(map);

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
                                mRegisterSmsCodeBtn.setEnabled(false);
                                if (isSuccess && responseCode == 200) {
                                    // 重新发送短信倒计时 -----Android自带倒计时类 倒计时60s, 步长1s
                                    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                                        @Override
                                        public void onTick(long l) {
                                            mRegisterSmsCodeBtn.setText(String.format(Locale.getDefault(), "重新获取(%1$d)", (l / 1000)));
                                        }

                                        @Override
                                        public void onFinish() {
                                            mRegisterSmsCodeBtn.setEnabled(true);
                                            mRegisterSmsCodeBtn.setText("获取验证码");
                                        }
                                    };
                                    timer.start();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        String message = jsonObject.getString("message");

                                        if (code == 200) {
                                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                                        } else {
                                            // 发送失败
                                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                                        }
                                        return;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Toast.makeText(RegisterActivity.this, "验证码发送失败，请稍后再试！", Toast.LENGTH_LONG).show();
                                mRegisterSmsCodeBtn.setEnabled(true);
                            }
                        });
                    }
                });
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mPhoneEt.getText() == null ? "" : mPhoneEt.getText().toString();
                String memberName = mNameEt.getText() == null ? "" : mNameEt.getText().toString();
                String password = mRegisterPwdEt.getText() == null ? "" : mRegisterPwdEt.getText().toString();
                String smsCode = mRegisterSmsCodeEt.getText() == null ? "" : mRegisterSmsCodeEt.getText().toString();
                boolean sex = mRegisterSexRg.getCheckedRadioButtonId() == R.id.rb_register_male;
                String surePassword = mRegisterSurePwdEt.getText() == null ? "" : mRegisterSurePwdEt.getText().toString();

                // 验证数据
                if (!Tools.isMobile(phone)) {
                    mPhoneEt.setError("请输入正确的手机号码！");
                    return;
                }
                if (!Tools.isNotNull(smsCode)) {
                    mRegisterSmsCodeEt.setError("请输入短信验证码！");
                    return;
                }
                if (!Tools.isNotNull(memberName)) {
                    mNameEt.setError("请输入姓名！");
                    return;
                }
                if (!Tools.isNotNull(password) || password.length() < 8) {
                    mRegisterPwdEt.setError("密码强度不够!");
                    return;
                }
                if (!password.equals(surePassword)) {
                    mRegisterSurePwdEt.setError("两次密码不一致！");
                    return;
                }

                // url
                String url = ServiceUrls.getMemberMethodUrl("register");
                // 准备数据
                Map<String, Object> memberMap = new HashMap<>();
                memberMap.put("phone", phone);
                memberMap.put("memberName", memberName);
                memberMap.put("password", MD5Util.getMD5(password));
                memberMap.put("sex", sex);

                Map<String, Object> map = new HashMap<>();
                map.put("member", memberMap);
                map.put("smsCode", mRegisterSmsCodeEt.getText().toString());

                JSONObject jsonObject = new JSONObject(map);

                OkHttpTool.httpPostJson(url, jsonObject.toString(), new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                        mActivityContext.runOnUiThread(new Runnable() {
                            String str;
                            @Override
                            public void run() {
                                try {
                                    if (isSuccess && responseCode == 200) {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        str = jsonObject.getString("message");

                                        if (code == 200) {
                                            String data = jsonObject.getString("data");
                                            MemberBean memberBean = mGson.fromJson(data, MemberBean.class);

                                            if (memberBean!=null){
                                                // 将用户信息保存到 全局Application  （放在内存中）
                                                mMyApplication.setLoginMember(memberBean);

                                                // 将phone 和 password 存入 SharePreferences
                                                // 实际项目中避免这样做， 安全性不高
                                                SPUtils.put(mActivityContext, ServiceUrls.SP_MEMBER_PHONE, memberBean.getPhone().trim());
                                                SPUtils.put(mActivityContext, ServiceUrls.SP_MEMBER_PASSWORD, memberBean.getPassword().trim());
                                            }

                                            Log.e(TAG, memberBean.toString());
                                            finish();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(RegisterActivity.this,str,Toast.LENGTH_LONG ).show();
                            }
                        });
                    }
                });
            }
        });
    }


    private void init() {
        mToLoginTv = findViewById(R.id.tv_register_login);
        mToForgetTv = findViewById(R.id.tv_register_forget);
        mRegisterBtn = findViewById(R.id.btn_register);
        mRegisterSmsCodeBtn = findViewById(R.id.btn_register_sms_code);
        mRegisterSexRg = findViewById(R.id.rg_register_sex);
        mRegisterMaleRb = findViewById(R.id.rb_register_male);
        mRegisterFemaleRb = findViewById(R.id.rb_register_female);
        mRegisterPwdEt = findViewById(R.id.et_register_pwd);
        mRegisterSurePwdEt = findViewById(R.id.et_register_sure_pwd);
        mNameEt = findViewById(R.id.et_register_name);
        mPhoneEt = findViewById(R.id.et_register_phone);
        mRegisterSmsCodeEt = findViewById(R.id.et_register_sms_code);

        mMyActionBar = findViewById(R.id.myActionBar);
        // 设置为 0 就是没有图标
        mMyActionBar.setData("注册账号", R.drawable.ic_custom_actionbar_left, 0, new MyActionBar.ActionBarClickListener() {
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
}
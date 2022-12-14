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
        // ????????? 0 ??????????????????
        mMyActionBar.setData("????????????", R.drawable.ic_custom_actionbar_left, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                // ??????activity
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

                // ????????????
                if (!Tools.isMobile(phone)) {
                    mPhoneEt.setError("?????????????????????????????????");
                    return;
                }
                if (!Tools.isNotNull(smsCode)) {
                    mSmsCodeEt.setError("???????????????????????????");
                    return;
                }
                if (!Tools.isNotNull(password) || password.length() < 8) {
                    mPasswordEt.setError("??????????????????!");
                    return;
                }
                if (!password.equals(surePassword)) {
                    mSurePasswordEt.setError("????????????????????????");
                    return;
                }

                // url
                url = ServiceUrls.getMemberMethodUrl("forgetBySmsCode");
                // ????????????
                map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", MD5Util.getMD5(password));
                map.put("smsCode", smsCode);


                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                        mActivityContext.runOnUiThread(new Runnable() {
                            String str = "???????????????????????? ????????????????????????";
                            @Override
                            public void run() {
                                try {
                                    if (isSuccess && responseCode == 200) {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.getInt("code");
                                        str = jsonObject.getString("message");

                                        if (code == 200) {
                                            Toast.makeText(mActivityContext, str, Toast.LENGTH_LONG).show();

                                            // ????????????????????????????????????
                                            mMyApplication.setLoginMember(null);

                                            // ?????? SharePreferences??????????????????
                                            // ????????????????????????????????? ???????????????
                                            SPUtils.remove(mActivityContext, ServiceUrls.SP_MEMBER_PHONE);
                                            SPUtils.remove(mActivityContext, ServiceUrls.SP_MEMBER_PASSWORD);

                                            //?????????????????????
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
                // ????????????
                phone = mPhoneEt.getText() == null ? "" : mPhoneEt.getText().toString();
                if (!Tools.isMobile(phone)) {
                    mPhoneEt.setError("??????????????????????????????");
                    return;
                }
                // ????????????Id
                String deviceId = DeviceUtils.getDeviceUUID(ForgetPwdActivity.this);
                // ??????URL
                url = ServiceUrls.getMemberMethodUrl("sendCode");

                map = new HashMap<>();
                map.put("phone", phone);
                map.put("deviceId", deviceId);
                map.put("isRegister", false);
                JSONObject jsonObject = new JSONObject(map);
                Log.e(TAG,"?????????????????????");

                // ??????????????????
                OkHttpTool.httpPostJson(url, jsonObject.toString(), new OkHttpTool.ResponseCallback() {
                    // ?????????????????????????????????
                    @Override
                    public void onResponse(boolean isSuccess, int responseCode, String response, Exception exception) {
                        // ????????????????????????ui??????
                        mActivityContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // ??????????????????
                                mSmsCodeBtn.setEnabled(false);
                                if (isSuccess && responseCode == 200) {
                                    // ??????????????????????????? -----Android?????????????????? ?????????60s, ??????1s
                                    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                                        @Override
                                        public void onTick(long l) {
                                            mSmsCodeBtn.setText(String.format(Locale.getDefault(), "????????????(%1$d)", (l / 1000)));
                                        }

                                        @Override
                                        public void onFinish() {
                                            mSmsCodeBtn.setEnabled(true);
                                            mSmsCodeBtn.setText("???????????????");
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
                                            // ????????????
                                            Toast.makeText(ForgetPwdActivity.this, message, Toast.LENGTH_LONG).show();
                                        }
                                        return;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Toast.makeText(ForgetPwdActivity.this, "??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                                mSmsCodeBtn.setEnabled(true);
                            }
                        });
                    }
                });
                break;
        }
    }
}
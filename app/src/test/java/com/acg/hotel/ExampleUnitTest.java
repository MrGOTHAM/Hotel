package com.acg.hotel;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

import com.acg.hotel.bean.UserSmsParam;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        Gson gson = new Gson();
        UserSmsParam userSmsParam = new UserSmsParam();
        userSmsParam.setPhone("123456");
        userSmsParam.setDeviceId("11111");
        userSmsParam.setRegister(true);
        String s = gson.toJson(userSmsParam);
        System.out.println(s);

    }
}
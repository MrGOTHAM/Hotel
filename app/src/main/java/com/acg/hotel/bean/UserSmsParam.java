package com.acg.hotel.bean;



/**
 * @author ancg
 * @date 2022/8/21 18:19
 */

public class UserSmsParam {

    private String phone;

    private String deviceId;

    private boolean isRegister;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }
}

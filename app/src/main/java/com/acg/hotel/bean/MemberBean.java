package com.acg.hotel.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemberBean implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会员Id
     */
    private Integer memberId;

    /**
     * 会员姓名
     */
    private String memberName;

    /**
     * 会员类型id
     */
    private Integer memberTypeId;

    /**
     * 入住次数
     */
    private Integer checkInNum;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 证件类型id
     */
    private Integer certificateTypeId;

    /**
     * 证件号
     */
    private String certificateNumber;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 照片
     */
    private String photo;

    //======扩展字段

    /**
     * 会员类型
     */
    private String memberType;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 最晚预留时间
     */
    private Date lastReserveTime;

    /**
     * 延迟退房时间
     */
    private Date delayedCheckOutTime;

    /**
     * 早餐
     */
    private String breakfast;

    /**
     * 说明
     */
    private String explains;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getMemberTypeId() {
        return memberTypeId;
    }

    public void setMemberTypeId(Integer memberTypeId) {
        this.memberTypeId = memberTypeId;
    }

    public Integer getCheckInNum() {
        return checkInNum;
    }

    public void setCheckInNum(Integer checkInNum) {
        this.checkInNum = checkInNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(Integer certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getLastReserveTime() {
        return lastReserveTime;
    }

    public void setLastReserveTime(Date lastReserveTime) {
        this.lastReserveTime = lastReserveTime;
    }

    public Date getDelayedCheckOutTime() {
        return delayedCheckOutTime;
    }

    public void setDelayedCheckOutTime(Date delayedCheckOutTime) {
        this.delayedCheckOutTime = delayedCheckOutTime;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    @Override
    public String toString() {
        return "MemberBean{" +
                "memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", memberTypeId=" + memberTypeId +
                ", checkInNum=" + checkInNum +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", registerTime=" + registerTime +
                ", certificateTypeId=" + certificateTypeId +
                ", certificateNumber='" + certificateNumber + '\'' +
                ", sex=" + sex +
                ", photo='" + photo + '\'' +
                ", memberType='" + memberType + '\'' +
                ", discount=" + discount +
                ", lastReserveTime=" + lastReserveTime +
                ", delayedCheckOutTime=" + delayedCheckOutTime +
                ", breakfast='" + breakfast + '\'' +
                ", explains='" + explains + '\'' +
                '}';
    }
}

package com.acg.hotel.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ReserveOrderBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 预订单id
     */
    private Integer reserveOrderId;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 客房类型id
     */
    private Integer roomTypeId;

    /**
     * 入住人
     */
    private String checkInName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 早餐
     */
    private String breakfast;

    /**
     * 入住日期
     */
    private Date checkInDate;

    /**
     * 离店时间
     */
    private Date lastOutTime;

    /**
     * 到店时间
     */
    private Date arrivalTime;

    /**
     * 客房单价
     */
    private BigDecimal roomPrice;

    /**
     * 预订客房数
     */
    private Integer reserveNum;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 订单状态
     */
    private Integer orderStateId;

    /**
     * 订单时间
     */
    private Date orderTime;

    /**
     * 预订途径
     */
    private Integer reserveWay;

    /**
     * 支付记录id
     */
    private Integer paymentRecordId;

    /**
     * 入住登记Id
     */
    private Integer checkInId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有效
     */
    private Boolean isValid;    

    //====扩展字段
    
    /**
     * 客房类型名称
     */
    private String roomTypeName;

    /**
     * 参考标准房价
     */
    private BigDecimal roomStandardPrice;

    /**
     * 平均面积
     */
    private String averageArea;

    /**
     * 窗户
     */
    private Boolean window;

    /**
     * 床型
     */
    private String bedType;

    /**
     * 楼层
     */
    private String floor;

    /**
     * 人数
     */
    private Integer peopleNumber;

    /**
     * 网络
     */
    private String network;

    /**
     * 客房备注
     */
    private String rommTypeRemark;

    /**
     * 图片
     */
    private String picture;
    
    /**
     * 订单状态
     */
    private String orderState;

    /**
     * 订单明细信息
     */
    private List<ReserveOrderDetailBean> orderDetails;

    private List<PaymentTypeBean> paymentTypes;

	public List<PaymentTypeBean> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<PaymentTypeBean> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public Integer getReserveOrderId() {
		return reserveOrderId;
	}

	public void setReserveOrderId(Integer reserveOrderId) {
		this.reserveOrderId = reserveOrderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getCheckInName() {
		return checkInName;
	}

	public void setCheckInName(String checkInName) {
		this.checkInName = checkInName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getLastOutTime() {
		return lastOutTime;
	}

	public void setLastOutTime(Date lastOutTime) {
		this.lastOutTime = lastOutTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public BigDecimal getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}

	public Integer getReserveNum() {
		return reserveNum;
	}

	public void setReserveNum(Integer reserveNum) {
		this.reserveNum = reserveNum;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getOrderStateId() {
		return orderStateId;
	}

	public void setOrderStateId(Integer orderStateId) {
		this.orderStateId = orderStateId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getReserveWay() {
		return reserveWay;
	}

	public void setReserveWay(Integer reserveWay) {
		this.reserveWay = reserveWay;
	}

	public Integer getPaymentRecordId() {
		return paymentRecordId;
	}

	public void setPaymentRecordId(Integer paymentRecordId) {
		this.paymentRecordId = paymentRecordId;
	}

	public Integer getCheckInId() {
		return checkInId;
	}

	public void setCheckInId(Integer checkInId) {
		this.checkInId = checkInId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public BigDecimal getRoomStandardPrice() {
		return roomStandardPrice;
	}

	public void setRoomStandardPrice(BigDecimal roomStandardPrice) {
		this.roomStandardPrice = roomStandardPrice;
	}

	public String getAverageArea() {
		return averageArea;
	}

	public void setAverageArea(String averageArea) {
		this.averageArea = averageArea;
	}

	public Boolean getWindow() {
		return window;
	}

	public void setWindow(Boolean window) {
		this.window = window;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getRommTypeRemark() {
		return rommTypeRemark;
	}

	public void setRommTypeRemark(String rommTypeRemark) {
		this.rommTypeRemark = rommTypeRemark;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public List<ReserveOrderDetailBean> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<ReserveOrderDetailBean> orderDetails) {
		this.orderDetails = orderDetails;
	}

	
    
}

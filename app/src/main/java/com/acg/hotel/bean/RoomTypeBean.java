package com.acg.hotel.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoomTypeBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 客房类型ID
     */
    private Integer roomTypeId;

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
     * 备注
     */
    private String remark;

    /**
     * 图片
     */
    private String picture;

    /**
     * 客房数
     */
    private Integer roomNum;

    /**
     * 显示顺序
     */
    private Integer showIndex;

    /**
     * 是否有效
     */
    private Boolean isValid;
    
    //====扩展字段
    /**
     * 最小可预订客房数
     */
    private int mimiRemainingNum;

	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public int getMimiRemainingNum() {
		return mimiRemainingNum;
	}

	public void setMimiRemainingNum(int mimiRemainingNum) {
		this.mimiRemainingNum = mimiRemainingNum;
	}

	@Override
	public String toString() {
		return "RoomTypeBean{" +
				"roomTypeId=" + roomTypeId +
				", roomTypeName='" + roomTypeName + '\'' +
				", roomStandardPrice=" + roomStandardPrice +
				", averageArea='" + averageArea + '\'' +
				", window=" + window +
				", bedType='" + bedType + '\'' +
				", floor='" + floor + '\'' +
				", peopleNumber=" + peopleNumber +
				", network='" + network + '\'' +
				", remark='" + remark + '\'' +
				", picture='" + picture + '\'' +
				", roomNum=" + roomNum +
				", showIndex=" + showIndex +
				", isValid=" + isValid +
				", mimiRemainingNum=" + mimiRemainingNum +
				'}';
	}
}

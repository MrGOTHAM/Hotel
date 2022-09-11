package com.acg.hotel.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReserveOrderDetailBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 预订单明细id
     */
    private Integer reserveOrderDetailId;

    /**
     * 预订单id
     */
    private Integer reserveOrderId;

    /**
     * 费用类型id
     */
    private Integer costTypeId;

    /**
     * 金额
     */
    private BigDecimal detailMoney;

    /**
     * 单价
     */
    private BigDecimal detailPrice;

    /**
     * 数量
     */
    private Integer detailNum;

    /**
     * 描述
     */
    private String describe;
    
    //===扩展字段

    /**
     * 费用类型
     */
    private String costTypeName;

	public Integer getReserveOrderDetailId() {
		return reserveOrderDetailId;
	}

	public void setReserveOrderDetailId(Integer reserveOrderDetailId) {
		this.reserveOrderDetailId = reserveOrderDetailId;
	}

	public Integer getReserveOrderId() {
		return reserveOrderId;
	}

	public void setReserveOrderId(Integer reserveOrderId) {
		this.reserveOrderId = reserveOrderId;
	}

	public Integer getCostTypeId() {
		return costTypeId;
	}

	public void setCostTypeId(Integer costTypeId) {
		this.costTypeId = costTypeId;
	}

	public BigDecimal getDetailMoney() {
		return detailMoney;
	}

	public void setDetailMoney(BigDecimal detailMoney) {
		this.detailMoney = detailMoney;
	}

	public BigDecimal getDetailPrice() {
		return detailPrice;
	}

	public void setDetailPrice(BigDecimal detailPrice) {
		this.detailPrice = detailPrice;
	}

	public Integer getDetailNum() {
		return detailNum;
	}

	public void setDetailNum(Integer detailNum) {
		this.detailNum = detailNum;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCostTypeName() {
		return costTypeName;
	}

	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}
    
}

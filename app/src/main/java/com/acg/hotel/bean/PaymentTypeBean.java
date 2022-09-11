package com.acg.hotel.bean;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author acg
 * @since 2022-09-09
 */
public class PaymentTypeBean implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 支付方式id
     */

    private Integer paymentTypeId;

    /**
     * 支付方式名称
     */
    private String paymentTypeName;

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }
}

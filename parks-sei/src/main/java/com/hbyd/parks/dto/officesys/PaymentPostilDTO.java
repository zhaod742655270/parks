package com.hbyd.parks.dto.officesys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * Created by Len on 2016/3/2.
 */

public class PaymentPostilDTO extends BaseDTO {

    private String parentID;

    private String paymentPostil;

    private String paymentNoPostil;

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getPaymentPostil() {
        return paymentPostil;
    }

    public void setPaymentPostil(String paymentPostil) {
        this.paymentPostil = paymentPostil;
    }

    public String getPaymentNoPostil() {
        return paymentNoPostil;
    }

    public void setPaymentNoPostil(String paymentNoPostil) {
        this.paymentNoPostil = paymentNoPostil;
    }
}

package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.BaseEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Created by Len on 2016/3/2.
 */
@Entity
@Table(name = "oa_contract_paying_postil")
@Audited
public class PaymentPostil extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "parentFK")
    @NotAudited
    private Payment payment;

    private String paymentPostil;

    private String paymentNoPostil;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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

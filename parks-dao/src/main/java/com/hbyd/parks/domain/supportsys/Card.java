package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 卡
 */
@Entity
@Table(name = "base_card")
@Access(AccessType.FIELD)
public class Card extends BaseEntity {
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡属性
     */
    private String cardProp;
    /**
     * 拥有者标识
     */
    private String ownId;
    /**
     * 拥有者属性
     */
    private String ownProp;
    /**
     * 卡编号
     */
    private String cardCode;
    /**
     * 卡状态
     */
    private String status;
    /**
     * 有效期开始时间
     */
    private Timestamp beginDate;
    /**
     * 有效期结束时间
     */
    private Timestamp endDate;
    /**
     * 注册时间
     */
    private Timestamp logDate;
    /**
     * 卡密码
     */
    private String cardPwd;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardProp() {
        return cardProp;
    }

    public void setCardProp(String cardProp) {
        this.cardProp = cardProp;
    }

    public String getOwnId() {
        return ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    public String getOwnProp() {
        return ownProp;
    }

    public void setOwnProp(String ownProp) {
        this.ownProp = ownProp;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getLogDate() {
        return logDate;
    }

    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }
}

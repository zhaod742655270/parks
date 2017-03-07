package com.hbyd.parks.common.model;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/2
 */
public class ConLogQueryBean extends QueryBeanEasyUI {

    private String beginDate;

    private String endDate;

    private String GatheringID;

    private String contractNo;

    public String getGatheringID() {
        return GatheringID;
    }

    public void setGatheringID(String gatheringID) {
        GatheringID = gatheringID;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}

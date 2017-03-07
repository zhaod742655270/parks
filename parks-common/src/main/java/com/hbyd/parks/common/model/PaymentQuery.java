package com.hbyd.parks.common.model;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/15
 */
public class PaymentQuery extends QueryBeanEasyUI {

    private String contractNO;

    private String contractName;

    private String sheetNameQuery;

    private String personQuery;

    private Double contractSumQuery;

    private String companySecondQuery;

    private String contentQuery;

    private String contractTypeQuery;

    private String conGatheringNameQuery;           //合同名称，用于收款合同界面查看付款合同

    public String getSheetNameQuery() {
        return sheetNameQuery;
    }

    public void setSheetNameQuery(String sheetNameQuery) {
        this.sheetNameQuery = sheetNameQuery;
    }

    public String getPersonQuery() {
        return personQuery;
    }

    public void setPersonQuery(String personQuery) {
        this.personQuery = personQuery;
    }

    public Double getContractSumQuery() {
        return contractSumQuery;
    }

    public void setContractSumQuery(Double contractSumQuery) {
        this.contractSumQuery = contractSumQuery;
    }

    public String getCompanySecondQuery() {
        return companySecondQuery;
    }

    public void setCompanySecondQuery(String companySecondQuery) {
        this.companySecondQuery = companySecondQuery;
    }

    public String getContractNO() {
        return contractNO;
    }

    public void setContractNO(String contractNO) {
        this.contractNO = contractNO;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContentQuery() {
        return contentQuery;
    }

    public void setContentQuery(String contentQuery) {
        this.contentQuery = contentQuery;
    }

    public String getContractTypeQuery() {
        return contractTypeQuery;
    }

    public void setContractTypeQuery(String contractTypeQuery) {
        this.contractTypeQuery = contractTypeQuery;
    }

    public String getConGatheringNameQuery() {
        return conGatheringNameQuery;
    }

    public void setConGatheringNameQuery(String conGatheringNameQuery) {
        this.conGatheringNameQuery = conGatheringNameQuery;
    }
}

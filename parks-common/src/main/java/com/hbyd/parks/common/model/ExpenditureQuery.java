package com.hbyd.parks.common.model;

/**
 * Created by Zhao_d on 2017/2/23.
 */
public class ExpenditureQuery extends QueryBeanEasyUI{
    private String recordPersonQuery;       //申请人(项目经理)
    private String projectQuery;        //项目名称
    private String examineDateBeg;       //申请日期起
    private String examineDateEnd;       //申请日期止

    public String getRecordPersonQuery() {
        return recordPersonQuery;
    }

    public void setRecordPersonQuery(String recordPersonQuery) {
        this.recordPersonQuery = recordPersonQuery;
    }

    public String getProjectQuery() {
        return projectQuery;
    }

    public void setProjectQuery(String projectQuery) {
        this.projectQuery = projectQuery;
    }

    public String getExamineDateBeg() {
        return examineDateBeg;
    }

    public void setExamineDateBeg(String examineDateBeg) {
        this.examineDateBeg = examineDateBeg;
    }

    public String getExamineDateEnd() {
        return examineDateEnd;
    }

    public void setExamineDateEnd(String examineDateEnd) {
        this.examineDateEnd = examineDateEnd;
    }
}

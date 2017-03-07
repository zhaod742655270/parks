package com.hbyd.parks.officesys.verLog;

import com.hbyd.parks.officesys.task.SimpleListener;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/5/19
 */
@Entity
@RevisionEntity(SimpleListener.class)
public class VersionLog extends DefaultRevisionEntity {
    private String logDate;

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }
}

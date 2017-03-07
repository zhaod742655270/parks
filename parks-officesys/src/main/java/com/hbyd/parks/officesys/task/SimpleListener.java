package com.hbyd.parks.officesys.task;

import com.hbyd.parks.officesys.verLog.VersionLog;
import org.hibernate.envers.RevisionListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/5/19
 */
public class SimpleListener  implements RevisionListener {

    public void newRevision(Object revisionEntity) {
                VersionLog versionLog = (VersionLog) revisionEntity;
                        //add your additional info to the SimpleRevEntity here
                 versionLog.setLogDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
             }
}

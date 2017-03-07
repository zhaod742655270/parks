package com.hbyd.parks.officesys.task;

import com.hbyd.parks.dao.officesys.ProjectRecordDao;
import com.hbyd.parks.domain.officesys.ProjectRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

/**
 * 任务 Bean 示例
 */


@Service
public class SendMessageTask {

   @Resource
   private ProjectRecordDao projectRecordDao;


    public void methodA() throws Exception{
        List<ProjectRecord> list=projectRecordDao.getProjectRecordList();
        for(int i=0;i<list.size();i++){
            String sendMessage="明日 "+Date.valueOf(list.get(i).getBiddingDate())+" 事业部参与 "+list.get(i).getName()+" 投标";
            System.out.println(sendMessage);
        }

    }

    public void methodC() throws Exception {
        System.out.println("TaskBean.methodC");
    }
}

package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.util.DateUtil;
import com.hbyd.parks.dto.attendancesys.IntervalDTO;
import com.hbyd.parks.ws.attendancesys.IntervalWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Controller
@Scope("prototype")
public class IntervalAction extends ActionSupport implements ModelDriven<IntervalDTO> {

    private static final long serialVersionUID = 1L;
    private IntervalDTO interval = new IntervalDTO();

    public IntervalDTO getModel() {
        return interval;
    }

    @Resource
    private IntervalWS intervalWS;

    private Gson gson = new Gson();

    public String getIntervals() {
        if (interval.getShiftID() == null) {
            JsonHelper.writeJson("{\"total\":1,\"rows\":[]");

        } else {
            List<IntervalDTO> list = intervalWS.findByShiftID(interval.getShiftID());
            PageBeanEasyUI page = new PageBeanEasyUI();
            if (list == null) {
                page.setTotal(0);
                page.setRows(new ArrayList());

            } else {
                page.setTotal(list.size());
                for (int i = 0; i < list.size(); i++) {
                    list.set(i, displayFormat(list.get(i)));
                }
                page.setRows(list);
            }
            String result = gson.toJson(page);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String addInterval() {
        AjaxMessage message = new AjaxMessage();
        try {
            interval = this.saveFormat(interval);
            intervalWS.save(interval);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editInterval() {
        AjaxMessage message = new AjaxMessage();
        try {
            interval = saveFormat(interval);
            intervalWS.update(interval);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteInterval() {
        AjaxMessage message = new AjaxMessage();
        try {
            intervalWS.delByID(interval.getId());
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("删除失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String validateNameExist() {


        return null;
    }

    private IntervalDTO saveFormat(IntervalDTO interval) {
        //上班时间
        String workTimeBegin = interval.getWorkTimeBegin();
        int workTimeBeginFlag = interval.getWorkTimeBeginFlag();
        workTimeBegin = DateUtil.addHoursByDayOffset(workTimeBeginFlag, workTimeBegin);
        interval.setWorkTimeBegin(workTimeBegin);

        //下班时间
        String workTimeEnd = interval.getWorkTimeEnd();
        int workTimeEndFlag = interval.getWorkTimeEndFlag();
        workTimeEnd = DateUtil.addHoursByDayOffset(workTimeEndFlag, workTimeEnd);
        interval.setWorkTimeEnd(workTimeEnd);

        //开始考勤时间
        String signInTimeBegin = interval.getSignInTimeBegin();
        int signInTimeBeginFlag = interval.getSignInTimeBeginFlag();
        signInTimeBegin = DateUtil.addHoursByDayOffset(signInTimeBeginFlag, signInTimeBegin);
        interval.setSignInTimeBegin(signInTimeBegin);

        //结束考勤时间
        String signOutTimeEnd = interval.getSignOutTimeEnd();
        int signOutTimeEndFlag = interval.getSignOutTimeEndFlag();
        signOutTimeEnd = DateUtil.addHoursByDayOffset(signOutTimeEndFlag, signOutTimeEnd);
        interval.setSignOutTimeEnd(signOutTimeEnd);

        //早退开始时间
        String earlyDeadLine = interval.getEarlyDeadLine();
        int earlyDeadLineFlag = interval.getEarlyDeadLineFlag();
        earlyDeadLine = DateUtil.addHoursByDayOffset(earlyDeadLineFlag, earlyDeadLine);
        interval.setEarlyDeadLine(earlyDeadLine);

        //迟到结束时间
        String lateDeadLine = interval.getLateDeadLine();
        int lateDeadLineFlag = interval.getLateDeadLineFlag();
        lateDeadLine = DateUtil.addHoursByDayOffset(lateDeadLineFlag, lateDeadLine);
        interval.setLateDeadLine(lateDeadLine);

        return interval;
    }

    private IntervalDTO displayFormat(IntervalDTO interval) {
        //上班时间
        String workTimeBegin = interval.getWorkTimeBegin();
        String[] wb = DateUtil.resolveTimeString(workTimeBegin);
        int dayOffset = Integer.parseInt(wb[0]);
        String timeStr = wb[1];
        interval.setWorkTimeBeginFlag(dayOffset);
        interval.setWorkTimeBegin(timeStr);
        interval.setWorkTimeBeginFormat(getLongTimeFormat(dayOffset, timeStr));

        //下班时间
        String workTimeEnd = interval.getWorkTimeEnd();
        String[] we = DateUtil.resolveTimeString(workTimeEnd);
        dayOffset = Integer.parseInt(we[0]);
        timeStr = we[1];
        interval.setWorkTimeEndFlag(dayOffset);
        interval.setWorkTimeEnd(timeStr);
        interval.setWorkTimeEndFormat(getLongTimeFormat(dayOffset, timeStr));

        //开始考勤时间
        String signInTimeBegin = interval.getSignInTimeBegin();
        String[] sitb = DateUtil.resolveTimeString(signInTimeBegin);
        dayOffset = Integer.parseInt(sitb[0]);
        timeStr = sitb[1];
        interval.setSignInTimeBeginFlag(dayOffset);
        interval.setSignInTimeBegin(timeStr);
        interval.setSignInTimeBeginFormat(getLongTimeFormat(dayOffset, timeStr));

        //结束考勤时间
        String signOutTimeEnd = interval.getSignOutTimeEnd();
        String[] sotb = DateUtil.resolveTimeString(signOutTimeEnd);
        dayOffset = Integer.parseInt(sotb[0]);
        timeStr = sotb[1];
        interval.setSignOutTimeEndFlag(dayOffset);
        interval.setSignOutTimeEnd(timeStr);
        interval.setSignOutTimeEndFormat(getLongTimeFormat(dayOffset, timeStr));

        //早退开始时间
        String earlyDeadLine = interval.getEarlyDeadLine();
        String[] edl = DateUtil.resolveTimeString(earlyDeadLine);
        dayOffset = Integer.parseInt(edl[0]);
        timeStr = edl[1];
        interval.setEarlyDeadLineFlag(dayOffset);
        interval.setEarlyDeadLine(timeStr);


        //迟到结束时间
        String lateDeadLine = interval.getLateDeadLine();
        String[] ldl = DateUtil.resolveTimeString(lateDeadLine);
        dayOffset = Integer.parseInt(ldl[0]);
        timeStr = ldl[1];
        interval.setLateDeadLineFlag(dayOffset);
        interval.setLateDeadLine(timeStr);


        return interval;
    }

    private String getLongTimeFormat(int dayOffset, String timeStr) {
        String result = null;
        switch (dayOffset) {
            case 0:
                result = "当天 ";
                break;
            case 1:
                result = "后一天 ";
                break;

        }
        result += timeStr;
        return result;
    }

}

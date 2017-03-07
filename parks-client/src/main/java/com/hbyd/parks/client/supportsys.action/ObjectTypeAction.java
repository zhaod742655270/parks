package com.hbyd.parks.client.supportsys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.supportsys.ObjectTypeDTO;
import com.hbyd.parks.ws.supportsys.ObjectTypeWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 14-7-14.
 */
@Controller
@Scope("prototype")
public class ObjectTypeAction extends ActionSupport implements ModelDriven<ObjectTypeDTO> {

    private static final long serialVersionUID = 1L;

    //add、edit 提交的参数
    private ObjectTypeDTO objectType = new ObjectTypeDTO();

    @Resource
    private ObjectTypeWS objectTypeWS;
    private Gson gson = new Gson();

    //员工类型
    public String empTypeList() {
        //员工类型10
        objectTypeList("10");
        return null;
    }

    //员工职务
    public String empDutyList() {
        //职务类型为14
        objectTypeList("14");
        return null;
    }

    //员工职称
    public String empTitleList() {
        return null;
    }

    /**
     * @param predef_object predef_object 参数为预定义类型例如：10是员工类型，11为访客类型，14为职务类型。。。
     */
    private void objectTypeList(String predef_object) {
        QueryBeanEasyUI page = new QueryBeanEasyUI();
        page.setPage(1);
        page.setRows(100000);
        page.setSort("typeName");
        page.setOrder("asc");
        String hql = " where preObj.id=? and isValid=1";
        List params = new ArrayList();
        params.add(predef_object);

        PageBeanEasyUI list = objectTypeWS.getPageBean(page, hql, params.toArray());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list.getRows());
        JsonHelper.writeJson(result);


    }

    //增加类型
    public String addObjectType() {
        AjaxMessage message = new AjaxMessage();
        try {
            ObjectTypeDTO ObjectTypeDTO = objectTypeWS.save(objectType);
            ArrayList<ObjectTypeDTO> data = new ArrayList<>();
            data.add(ObjectTypeDTO);
            message.setData(data);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    //修改类型
    public String editObjectType() {
        AjaxMessage message = new AjaxMessage();
        try {
            objectTypeWS.update(objectType);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    //删除类型
    public String deleteObjectType() {
        AjaxMessage message = new AjaxMessage();
        try {
            objectTypeWS.delFake(objectType.getId());

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

    @Override
    public ObjectTypeDTO getModel() {
        return objectType;
    }


}

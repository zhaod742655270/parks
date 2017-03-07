package com.hbyd.parks.client.managesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.managesys.ResAppWS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by len on 14-7-14.
 */
@Controller
@Scope("prototype")
public class AppAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {

    private static final long serialVersionUID = 1L;
    private QueryBeanEasyUI page = new QueryBeanEasyUI();

    //add、edit 提交的参数
    private ResAppDTO app;

    //delete使用的Id
    private String appId;

    //搜索提交的参数
    private String key;

    private String appName;

    public QueryBeanEasyUI getModel() {
        return page;
    }

    @Resource
    private ResAppWS appWS;

    private Gson gson = new Gson();

    /*
        public String appList() throws IOException {

            String hql = "where appName like ?";
            List params = new ArrayList();
            params.add(key + "%");

            PageBeanEasyUI list = key == null ? appWS.getPageBean(page, "", null) : appWS.getPageBean(page, hql, params.toArray());
            //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
            if (list.getRows() == null) list.setRows(new ArrayList());
            String result = gson.toJson(list);
            JsonHelper.writeJson(result);
            return null;

        }
    */
    private List<ResAppDTO> excludeApp() {
        List<ResAppDTO> list = appWS.findAll();
        UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
        if (!user.getUserName().equals("super")) {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIsVisible() == false) {
                        list.remove(i);
                        i--;
                    }
                }
                return list;
            }
        }
        return list;

    }

    public String getAllApp() {

        List<ResAppDTO> list = excludeApp();
        String result = list == null ? "{}" : gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;
    }

    public String appList() {

        List<ResAppDTO> list = excludeApp();
        PageBeanEasyUI page = new PageBeanEasyUI();
        page.setRows(list);
        page.setTotal(list.size());
        String result = list == null ? "{\"total\":0,\"rows\":[]}" : gson.toJson(page);
        JsonHelper.writeJson(result);
        return null;
    }


    public String addApp() {
        AjaxMessage message = new AjaxMessage();
        try {
            appWS.save(app);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editApp() {
        AjaxMessage message = new AjaxMessage();
        try {
            appWS.update(app);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteApp() {
        AjaxMessage message = new AjaxMessage();
        try {
            appWS.delByID(appId);

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


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public ResAppDTO getApp() {
        return app;
    }

    public void setApp(ResAppDTO app) {
        this.app = app;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}

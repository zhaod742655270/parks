package com.hbyd.parks.client.util;

import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.ResMenuDTO;
import com.hbyd.parks.dto.managesys.ResBtnDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by len on 14-7-30.
 */
public class DOMHelper {

    public static List<HashMap<String, String>> getApps(Collection<ResAppDTO> appDTOs) {
        List<HashMap<String, String>> apps = new ArrayList<>();
        for (ResAppDTO appDTO : appDTOs) {
            HashMap<String, String> app = new HashMap<>();
            String a = String.format("<li><a target='centerPage' onclick=\"window.open('%s?appId=%s','centerPage')\" >%s</a></li>", appDTO.getAppPageUrl(), appDTO.getId(), appDTO.getAppName());
            app.put("app", a);
            apps.add(app);
        }

        return apps;
    }

    public static HashMap<String, String> getMenus(ResAppDTO app, Collection<ResMenuDTO> menuDTOs) {
        HashMap<String, String> according = new HashMap<>();
        String appName = app.getAppName();
        according.put("appName", appName);
        //先生成主菜单
        StringBuffer menus = new StringBuffer("");
        for (ResMenuDTO mainMenu : menuDTOs) {
            if (mainMenu.getParentId() == null) {
                String menuBegin = String.format(" <div title='%s'class='accordion-child'>", mainMenu.getMenuName());
                StringBuffer menu = new StringBuffer(menuBegin);

                for (ResMenuDTO subMenu : menuDTOs) {
                    if (mainMenu.getId().equals(subMenu.getParentId())) {

                        String menuMiddle = String.format("<p> <h3> <a class=\"easyui-linkbutton\" plain=\"true\"  data-options=\"iconCls:'%s'\"\t onclick=\"window.open('%s?menuId=%s','commoncontent')\">%s </a> </h3> </p> ", subMenu.getMenuIconUrl(), subMenu.getMenuPageUrl(), subMenu.getId(), subMenu.getMenuName());
                        menu.append(menuMiddle);
                    }
                }
                menu.append(" </div>");
                menus.append(menu);
            }
        }
        according.put("menus", menus.toString());

        return according;
    }

    public static List<HashMap<String, String>> getBtns(Collection<ResBtnDTO> btnDTOs) {
        List<HashMap<String, String>> btns = new ArrayList<>();
        for (ResBtnDTO btnDTO : btnDTOs) {
            HashMap<String, String> btn = new HashMap<>();
            String b = String.format(" <a class='easyui-linkbutton' iconcls='%s' plain='true' onclick='%s'>%s</a>", btnDTO.getBtnIconUrl(), btnDTO.getBtnScript(), btnDTO.getBtnName());
            btn.put("btn", b);
            btns.add(btn);
        }
        return btns;
    }
}

package com.hbyd.parks.client.util;

import com.hbyd.parks.common.model.EasyUITree;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.dto.managesys.ResMenuDTO;
import com.hbyd.parks.dto.supportsys.RegionDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by len on 14-7-17.
 */
public class TreeHelper {

    public static List<EasyUITree> getDeptTree(List<DepartmentDTO> list) {
        ArrayList<EasyUITree> nodes = new ArrayList<>();
        //得到根节点
        for (int i = 0; i < list.size(); i++) {
            DepartmentDTO dept = list.get(i);
            if (isDeptParenNode(list, dept)) {
                EasyUITree node = new EasyUITree();
                node.setId(dept.getId());
                node.setText(dept.getDeptName());
                // node.setState("closed");
                nodes.add(node);

            }
        }
        //建立临时父节点集合。
        ArrayList<EasyUITree> toDo = new ArrayList<>();
        toDo.addAll(nodes);
        //临时集合有数据，所做的操作
        while (toDo.size() > 0) {
            //移除一个临时节点。
            EasyUITree node = toDo.remove(0);
            //遍历整个节点集合
            for (int i = 0; i < list.size(); i++) {
                DepartmentDTO dept = list.get(i);
                //如果临时节点里面有子节点，将添加子节点，并将子节点存放到临时父节点中
                if (dept.getParentId() != null && dept.getParentId().equals(node.getId())) {

                    EasyUITree child = new EasyUITree();
                    child.setId(dept.getId());
                    child.setText(dept.getDeptName());
                    node.addChild(child);
                    toDo.add(child);
                }
            }

        }

        return nodes;
    }

    public static List<EasyUITree> getRegionTree(List<RegionDTO> list){
        ArrayList<EasyUITree> nodes = new ArrayList<>();
        //得到根节点
        for (int i = 0; i < list.size(); i++) {
            RegionDTO region = list.get(i);
            if (isRegionParenNode(list, region)) {
                EasyUITree node = new EasyUITree();
                node.setId(region.getId());
                node.setText(region.getRegionName());
                // node.setState("closed");
                nodes.add(node);

            }
        }
        //建立临时父节点集合。
        ArrayList<EasyUITree> toDo = new ArrayList<>();
        toDo.addAll(nodes);
        //临时集合有数据，所做的操作
        while (toDo.size() > 0) {
            //移除一个临时节点。
            EasyUITree node = toDo.remove(0);
            //遍历整个节点集合
            for (int i = 0; i < list.size(); i++) {
                RegionDTO region = list.get(i);
                //如果临时节点里面有子节点，将添加子节点，并将子节点存放到临时父节点中
                if (region.getParentFK() != null && region.getParentFK().equals(node.getId())) {

                    EasyUITree child = new EasyUITree();
                    child.setId(region.getId());
                    child.setText(region.getRegionName());
                    node.addChild(child);
                    toDo.add(child);
                }
            }

        }

        return nodes;
    }

    private static boolean isDeptParenNode(List<DepartmentDTO> list, DepartmentDTO dept) {

        boolean hasParent = false;
        for (DepartmentDTO d : list) {
            if (d.getId().equals(dept.getParentId())) {
                hasParent = true;
                break;
            }
        }
        if (dept.getParentId() == null || hasParent == false) {
            return true;
        } else {
            return false;
        }
    }
    private static boolean isRegionParenNode(List<RegionDTO> list, RegionDTO region) {

        boolean hasParent = false;
        for (RegionDTO d : list) {
            if (d.getId().equals(region.getParentFK())) {
                hasParent = true;
                break;
            }
        }
        if (region.getParentFK() == null || hasParent == false) {
            return true;
        } else {
            return false;
        }
    }
    public static List<EasyUITree> getPriTree(ResAppDTO app) {
        List<EasyUITree> list = new ArrayList<>();
        EasyUITree node = new EasyUITree();


        HashMap<String, String> pAtr = new HashMap<>();
        pAtr.put("priResType", "app");
        HashMap<String, String> mAtr = new HashMap<>();
        mAtr.put("priResType", "menu");
        HashMap<String, String> bAtr = new HashMap<>();
        bAtr.put("priResType", "btn");
        //设置父节点
        HashMap<String, String> appAtr = new HashMap<>();
        appAtr.put("url", app.getAppPageUrl());
        appAtr.put("priResType", "app");
        node.setId(app.getId());
        node.setText(app.getAppName());
        node.setAttributes(appAtr);
        //得到所有菜单
        Set<ResMenuDTO> menus = excludeMenu(app.getResMenus());
        if (menus == null) {
            list.add(node);
            return list;
        }
        //load主菜单
        List<EasyUITree> uiMainMenus = new ArrayList<>();

        for (ResMenuDTO mainMenu : menus) {
            if (mainMenu.getParentId() == null) {
                EasyUITree mainNode = new EasyUITree();
                mainNode.setId(mainMenu.getId());
                mainNode.setText(mainMenu.getMenuName());
                mainNode.setAttributes(mAtr);
                //加载子菜单
                List<EasyUITree> uiChildMenus = new ArrayList<>();
                Set<ResMenuDTO> childMenus = excludeMenu(mainMenu.getChildMenus());
                if (childMenus != null && childMenus.size() > 0) {
                    for (ResMenuDTO childMenu : childMenus) {
                        EasyUITree childNode = new EasyUITree();
                        HashMap<String, String> menuAtr = new HashMap<>();
                        menuAtr.put("url", childMenu.getMenuPageUrl());
                        menuAtr.put("priResType", "menu");
                        childNode.setId(childMenu.getId());
                        childNode.setText(childMenu.getMenuName());
//                        childNode.setAttributes(mAtr);
                        childNode.setAttributes(menuAtr);
                        childNode.setIconCls(childMenu.getMenuIconUrl());
                        //加载按钮
                        List<EasyUITree> uiBtns = new ArrayList<>();
                        Set<ResBtnDTO> btns = excludeBtn(childMenu.getResBtns());
                        if (btns != null && btns.size() > 0) {
                            for (ResBtnDTO btn : btns) {
                                HashMap<String, String> btnAtr = new HashMap<>();
                                btnAtr.put("url", btn.getBtnUrl());
                                btnAtr.put("priResType", "btn");
                                EasyUITree bNode = new EasyUITree();
                                bNode.setId(btn.getId());
                                bNode.setText(btn.getBtnName());
//                                bNode.setAttributes(bAtr);
                                bNode.setAttributes(btnAtr);
                                bNode.setIconCls(btn.getBtnIconUrl());
                                uiBtns.add(bNode);
                            }
                        }
                        childNode.setChildren(uiBtns);
                        uiChildMenus.add(childNode);
                    }
                }
                mainNode.setChildren(uiChildMenus);
                uiMainMenus.add(mainNode);
            }
        }
        node.setChildren(uiMainMenus);
        list.add(node);
        return list;

    }

    //排除不可见的菜单资源
    private static Set<ResMenuDTO> excludeMenu(Set<ResMenuDTO> menuDTOs) {
        if (menuDTOs == null) {
            return null;
        }
        List<ResMenuDTO> temp = new ArrayList<>();
        for (ResMenuDTO menu : menuDTOs) {
            if (!menu.getIsVisible()) {
                temp.add(menu);
            }
        }
        for (ResMenuDTO menu : temp) {
            menuDTOs.remove(menu);
        }
        return menuDTOs;
    }

    //排除不可见的按钮资源
    private static Set<ResBtnDTO> excludeBtn(Set<ResBtnDTO> btnDTOs) {
        if (btnDTOs == null) {
            return null;
        }
        List<ResBtnDTO> temp = new ArrayList<>();
        for (ResBtnDTO btn : btnDTOs) {
            //if (!btn.getIsVisible()) {
            //    temp.add(btn);
            //}
        }
        for (ResBtnDTO btn : temp) {
            btnDTOs.remove(btn);
        }
        return btnDTOs;
    }
}

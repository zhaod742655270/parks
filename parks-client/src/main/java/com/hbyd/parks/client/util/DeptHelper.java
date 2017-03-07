package com.hbyd.parks.client.util;

import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.ws.managesys.DeptWS;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Len on 14-8-26.
 */
public class DeptHelper {

    @Resource
    private  DeptWS deptWS;
    public static List<DepartmentDTO> getDeptChildren(DepartmentDTO parent) {
        ArrayList<DepartmentDTO> depts = new ArrayList<DepartmentDTO>();
        getchildren(depts, parent);
        return depts;

    }

    private static void getchildren(ArrayList<DepartmentDTO> depts, DepartmentDTO parent) {
        depts.add(parent);
        if (parent.getChildDepts().size() > 0) {
            for (DepartmentDTO dept : parent.getChildDepts()) {
                getchildren(depts, dept);
            }
        }
    }


    /**
     * 通过部门id在部门集合中，找出该部门下所有子节点的id
     *
     * @param deptId 部门id
     * @param depts  部门集合
     * @return 子节点id集合
     */
    public static List<String> getDeptChildrenIds(String deptId, List<DepartmentDTO> depts) {
        List<String> idList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        tempList.add(deptId);
        while (tempList.size() > 0) {
            //移除一个临时节点。
            String id = tempList.remove(0);
            //添加到结果集合
            idList.add(id);
            //遍历整个节点集合
            for (int i = 0; i < depts.size(); i++) {
                DepartmentDTO dept = depts.get(i);
                //如果临时节点里面有子节点，将添加子节点，并将子节点存放到临时父节点中
                if (dept.getParentId() != null && dept.getParentId().equals(id)) {

                    tempList.add(dept.getId());
                }
            }

        }
        return idList;

    }

    /**
     * 通过部门找出该部门下所有子节点的id
     *
     * @param parent 部门（其实是个部门树，包含子节点）
     * @return 子节点id集合
     */
    public static List<String> getDeptChildrenIds(DepartmentDTO parent) {
        ArrayList<String> idList = new ArrayList<>();
        getchildrenId(idList, parent);
        return idList;
    }

    private static void getchildrenId(ArrayList<String> depts, DepartmentDTO parent) {
        depts.add(parent.getId());
        if (parent.getChildDepts().size() > 0) {
            for (DepartmentDTO dept : parent.getChildDepts()) {
                getchildrenId(depts, dept);
            }
        }
    }

/*
    public static String getDeptchlidrenQueryString(String deptId, List<DepartmentDTO> depts) {
        StringBuffer queryBuf = new StringBuffer("");
        List<String> ids = getDeptChildrenIds(deptId, depts);
        for (int i = 0; i < ids.size(); i++) {
            if (i == ids.size() - 1) {
                queryBuf.append("" + ids.get(i) + "");
            } else {
                queryBuf.append("" + ids.get(i) + ",");
            }
        }


        return queryBuf.toString();
    }
    */
}

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>车辆进出记录查询</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-datagarid-detailview.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/doorsys/carAccessEvent.js"></script>
    <style>
        input, label {
            vertical-align: middle
        }

        .tdRight {
            text-align: right;;
        }
    </style>
</head>
<body>
<table id="carCardEvent-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">id</th>
        <th data-options="field:'plateNo'">车牌号</th>
        <th data-options="field:'personName'">姓名</th>
        <th data-options="field:'deptName'">部门</th>
        <th data-options="field:'eventTime',sortable:'true'">时间</th>
        <th data-options="field:'doorName'">位置</th>
        <th data-options="field:'isValidPassDes'">是否合法</th>
        <th data-options="field:'ioType'">进出方向</th>
        <th data-options="field:'existsBigPhoto'">是否有抓拍照片</th>
        <th data-options="field:'existsSmallPhoto'">是否有车牌照片</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<!-- 用户列表工具栏-->
<div id="carCardEvents-toolbar">
    <form id="cardEvent-form" action="accessEvent/exportExcel" method="post">
        <table id="qcTable">
            <tr>
                <td><input type="checkbox" checked="checked" disabled="true"/><label>开始时间</label> &nbsp;<input
                        id="beginTime" name="beginTime" style="width: 145px;"/>
                </td>
                <td class="tdRight"><input type="checkbox" checked="checked" disabled="true"/><label>记录类型</label> &nbsp;<select
                        id="recordType" name="recordType" class="easyui-combobox"
                        style="width: 125px">
                    <option value="6">员工车辆</option>
                    <option value="7">访客车辆</option>
                </select>&nbsp;
                </td>
                <td><input id="deptIdCheck" name="deptIdCheck" type="checkbox"/><label>部门</label> &nbsp;<input
                        id="deptId" name="deptId" class="easyui-combobox"
                        data-options="valueField:'id',textField:'deptName',url:'../managesys/dept/getDeptsByDeptId'"
                        style="width: 150px"/>
                </td>
                <td class="tdRight"><input id="plateNoCheck" name="plateNoCheck" type="checkbox"/><label>车牌号</label>&nbsp;
                    <input type="text" id="plateNo" name="plateNo" style="width: 120px"/>&nbsp;
                </td>

            </tr>
            <tr>
                <td><input type="checkbox" checked="checked" disabled="true"/><label>结束时间</label> &nbsp;<input
                        id="endTime" name="endTime" style="width: 145px;"/>
                </td>
                <td><input id="doorNameCheck" name="doorNameCheck" type="checkbox"/><label>位置信息</label> &nbsp;<input
                        type="text" id="doorName" name="doorName" style="width: 120px"/></td>
                <td><input id="personNameCheck" name="personNameCheck" type="checkbox"/><label>姓名</label> &nbsp;<input
                        type="text"
                        id="personName"
                        name="personName"
                        style="width: 148px"/>
                </td>
                <td><input id="cardNoCheck" name="cardNoCheck" type="checkbox"/><label>卡片ID</label> &nbsp;<input
                        type="text" id="cardNo" name="cardNo"
                        style="width: 120px"/></td>
                <td rowspan="2">
                    &nbsp;<a class="easyui-linkbutton" onclick="queryHandler()"
                             iconcls="icon-search" plain="false">检索</a></td>
                <td rowspan="2">
                    <a class="easyui-linkbutton" onclick="exportExcel()"
                       iconcls="icon-excel" plain="false">导出</a></td>

            </tr>
        </table>
    </form>
</div>

<input id="menuId" type="hidden" value="${param.menuId}"/>

<div id="photoWindows" class="easyui-window" title="抓拍照片" style="width:576px;height:496px;padding: 2px;"
     data-options="iconCls:'icon-camera',modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
    <table>
        <tr>
            <td style="padding: 2px; font-weight: bold"> 车辆照片：</td>
            <td>
                <img style="width: 480px;height: 360px" src="" id="bigPhoto"/></td>

        </tr>
        <tr> <td style="padding: 2px; font-weight: bold"> 车牌照片：</td>
            <td>
                <img style="width:120px;height: 80px" src="" id="smallPhoto"/>
            </td>
        </tr>
    </table>


</div>
</body>
</html>

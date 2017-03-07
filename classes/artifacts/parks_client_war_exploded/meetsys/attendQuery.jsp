<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>会议签到查询</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/meetsys/attendQuery.js"></script>
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
<table id="empCardEvent-dg">
    <thead>
    <tr>
        <%--<th data-options="field:'id',hidden:true">id</th>--%>
        <th data-options="field:'eventTime',sortable:'true'">时间</th>
        <th data-options="field:'doorName'">位置</th>
        <th data-options="field:'personName'">姓名</th>
        <th data-options="field:'deptName'">部门</th>
        <th data-options="field:'cardNo'">卡片ID</th>
        <%--<th data-options="field:'plateNo'">车牌号</th>--%>
        <%--<th data-options="field:'idNo'">身份证号</th>--%>
        <%--<th data-options="field:'isValidPassDes'">是否通行</th>--%>
        <%--<th data-options="field:'ioType'">进出方向</th>--%>
        <%--<th data-options="field:'existsBigPhoto'">是否有抓拍照片</th>--%>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<!-- 用户列表工具栏-->
<div id="empCardEvents-toolbar">
    <form id="cardEvent-form" action="../doorsys/accessEvent/exportExcel" method="post">
        <table id="qcTable">
            <tr>

                <td class="tdRight"><input type="checkbox" checked="checked" disabled="true"/><label>会议室</label> &nbsp;
                    <input id="roomId" name="roomId" class="easyui-combobox" style="width: 150px"
                           data-options="required:true,valueField:'id',textField:'name',url:'../meetsys/meetRoom/getAllMeetRooms'"/>
                    &nbsp;
                </td>
                <td><input type="checkbox" checked="checked" disabled="true"/><label>开始时间</label> &nbsp;<input
                        id="beginTime" name="beginTime" style="width: 145px;"/>
                </td>
                <td><input id="personNameCheck" name="personNameCheck" type="checkbox"/><label>姓 名</label> &nbsp;
                    <input
                            type="text"
                            id="personName"
                            name="personName"
                            style="width: 150px"/>
                </td>


            </tr>
            <tr>
                <td class="tdRight"><input id="deptIdCheck" name="deptIdCheck" type="checkbox"/><label>部  门</label>
                    &nbsp;
                    <input id="deptId" name="deptId" class="easyui-combobox"
                           data-options="valueField:'id',textField:'deptName',url:'../managesys/dept/getDeptsByDeptId'"
                           style="width: 150px"/> &nbsp;
                </td>
                <td><input type="checkbox" checked="checked" disabled="true"/><label>结束时间</label> &nbsp;<input
                        id="endTime" name="endTime" style="width: 145px;"/>
                </td>


                <td><input id="cardNoCheck" name="cardNoCheck" type="checkbox"/><label>卡片ID</label> &nbsp;<input
                        type="text" id="cardNo" name="cardNo"
                        style="width: 150px"/></td>
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

<div id="photoWindows" class="easyui-window" title="抓拍照片" style="width:502px;height:405px ;padding: 2px;"
     data-options="iconCls:'icon-camera',modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
    <img style="width: 480px;height: 360px" src="" id="capturePhoto"/>
</div>
</body>
</html>








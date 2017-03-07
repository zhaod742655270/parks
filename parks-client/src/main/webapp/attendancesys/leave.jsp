<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>请假管理</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/leave.js"></script>
    <style>
        #leave-form td {
            height: 35px;
        }

        #leave-form .tdRight {
            text-align: right;
            width: 100px;

        }
    </style>
</head>
<body>
<table id="leave-dg" class="easyui-datagrid"
       data-options="title:'请假列表',toolbar:'#leaves-toolbar',pagination:true, sortName:'fromDate',sortOrder:'asc',
                striped:true,rownumbers:true,fitColums:true,fit:true,singleSelect:true,method:'post',url:'leave/leaveList'">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true"> id</th>
        <th data-options="field:'empId',hidden:true"> 员工id</th>
        <th data-options="field:'typeID',hidden:true"> 员工id</th>
        <th data-options="field:'deptName',sortable:true">部门</th>
        <th data-options="field:'empName'">姓名</th>
        <th data-options="field:'typeName'">请假类型</th>
        <th data-options="field:'fromDate',width:150">开始时间</th>
        <th data-options="field:'toDate',width:150">结束时间</th>
        <%--<th data-options="field:' writeDate',width:100">录入时间</th>--%>
        <%--<th data-options="field:' userName'">录入人</th>--%>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<!-- 用户列表工具栏-->
<div id="leaves-toolbar">
    <!-- 之所以把搜索框放在前面目的是为了兼容ie6，如果放在后面ie6中会折行显示 -->
    <div style="float: right; padding-top: 2px; padding-right: 5px;">
        <input id="leave-search" class="easyui-searchbox"
               data-options="prompt:'请输入关键字',
                         searcher: function (value, name) {
                    $('#leave-dg').datagrid('load', { key: value });
                }"/>
    </div>
    <div id="leave-btns">

        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-reload" plain="true"
           onclick="$('#leave-dg').datagrid('reload');">刷新</a>
    </div>

</div>
<!-- 用户window窗口-->
<div id="leave-dlg" class="easyui-dialog"
     style="width: 380px; height: 270px; padding: 10px" modal="true"
     closed="true" buttons="#leave-dlg-buttons">

    <form id="leave-form" method="post">
        <table>
            <tr>
                <td colspan="2" style="height: 5px;"><input id="leaveId" type="hidden" name="leave.id"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>姓名：</label></td>
                <td class="tdLeft"><input id="empId" name="leave.empId" style="width: 165px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>请假类型：</label></td>
                <td class="tdLeft"><input id="typeID" name="leave.typeID"
                                          class="easyui-combobox"
                                          data-options="required:true,valueField:'id',textField:'name',url:'leaveType/getAllLeaveType'"
                                          style="width: 165px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>开始时间：</label></td>
                <td class="tdLeft"><input id="fromDate" class="easyui-datetimebox" name="leave.fromDate"
                                          data-options="required:true,showSeconds:true" style="width:165px"></td>
            </tr>
            <tr>
                <td class="tdRight"><label>结束时间：</label></td>
                <td class="tdLeft"><input id="toDate" class="easyui-datetimebox" name="leave.toDate"
                                          data-options="required:true,showSeconds:true" style="width:165px"></td>
            </tr>
        </table>
    </form>
</div>
<!-- 用户window窗口的功能按钮-->
<div id="leave-dlg-buttons">
    <a id="leave-save-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-ok" onclick="saveLeave()">保存</a>

    <a id="leave-close-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#leave-dlg').dialog('close')">取消</a>
</div>
<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

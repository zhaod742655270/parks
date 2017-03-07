<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>请假类型管理</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/leaveType.js"></script>
    <style>
        #leaveType-form td {
            height: 35px;
        }

        #leaveType-form .tdRight {
            text-align: right;
            width: 100px;

        }
    </style>
</head>
<body>
<table id="leaveType-dg" class="easyui-datagrid"
       data-options="title:'请假类型列表',toolbar:'#leaveTypes-toolbar',pagination:true, sortName:'name',sortOrder:'asc',
                striped:true,rownumbers:true,fitColums:true,fit:true,singleSelect:true,method:'post',url:'leaveType/leaveTypeList',
                  onSelect:function(rowIndex,rowData){ 
                var leaveTypeid=rowData.ID;
               
                } ">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true"></th>
        <%--<th data-options="field:'serialNumber',width:150">编号</th>--%>
        <th data-options="field:'name',width:120,sortable:true">请假类型名称</th>
        <th data-options="field:'symbol',width:50">简称</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<!-- 用户列表工具栏-->
<div id="leaveTypes-toolbar">
    <!-- 之所以把搜索框放在前面目的是为了兼容ie6，如果放在后面ie6中会折行显示 -->
    <div style="float: right; padding-top: 2px; padding-right: 5px;">
        <input id="leaveType-search" class="easyui-searchbox"
               data-options="prompt:'请输入关键字',
                         searcher: function (value, name) {
                    $('#leaveType-dg').datagrid('load', { key: value });
                }"/>
    </div>
    <div id="leaveType-btns">

        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-reload" plain="true"
           onclick="$('#leaveType-dg').datagrid('reload');">刷新</a>
    </div>

</div>
<!-- 用户window窗口-->
<div id="leaveType-dlg" class="easyui-dialog"
     style="width: 330px; height: 190px; padding: 10px" modal="true"
     closed="true" buttons="#leaveType-dlg-buttons">

    <form id="leaveType-form" method="post">
        <table>
            <tr>
                <td colspan="2" style="height: 5px;"><input id="leaveTypeId"
                                                            type="hidden" name="leaveType.id"/></td>
            </tr>
            <%--<tr>--%>
            <%--<td class="tdRight"><label>编号：</label></td>--%>
            <%--<td class="tdLeft"><input id="serialNumber" style="width: 160px;" type="text"--%>
            <%--name="leaveType.serialNumber"/></td>--%>
            <%--</tr>--%>
            <tr>
                <td class="tdRight"><label>请假类型名称：</label></td>
                <td class="tdLeft"><input id="leaveTypeName" type="text" class="easyui-validatebox"
                                          data-options="required:true,validType:'length[1,20]'"
                                          name="leaveType.name" style="width: 160px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>请假类型简称：</label></td>
                <td class="tdLeft"><input id="leaveTypeSymbol" type="text" class="easyui-validatebox"
                                          data-options="required:true,validType:'length[1,1]'"
                                          name="leaveType.symbol" style="width: 160px;"/></td>
            </tr>

        </table>
    </form>
</div>
<!-- 用户window窗口的功能按钮-->
<div id="leaveType-dlg-buttons">
    <a id="leaveType-save-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-ok" onclick="saveLeaveType()">保存</a>

    <a id="leaveType-close-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#leaveType-dlg').dialog('close')">取消</a>
</div>
<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

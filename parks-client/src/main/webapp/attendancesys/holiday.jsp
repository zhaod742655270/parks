<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>假日管理</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/holiday.js"></script>
    <style>
        #holiday-form td {
            height: 35px;
        }

        #holiday-form .tdRight {
            text-align: right;
            width: 100px;

        }
    </style>
</head>
<body>
<table id="holiday-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">id</th>
        <%--<th data-options="field:'serialNumber',width:120,sortable:true">编号</th>th--%>
        <th data-options="field:'name',width:150">名称</th>
        <th data-options="field:'startDate',width:150">开始日期</th>
        <th data-options="field:'endDate',width:150">结束日期</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<!-- 用户列表工具栏-->
<div id="holidays-toolbar">
    <!-- 之所以把搜索框放在前面目的是为了兼容ie6，如果放在后面ie6中会折行显示 -->
    <div style="float: right; padding-top: 2px; padding-right: 5px;">
        <input id="holiday-search" class="easyui-searchbox"
               data-options="prompt:'请输入关键字',
                         searcher: function (value, name) {
                    $('#holiday-dg').datagrid('load', { key: value });
                }"/>
    </div>
    <div id="holiday-btns">

        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-reload" plain="true"
           onclick="$('#holiday-dg').datagrid('reload');">刷新</a>
    </div>

</div>
<!-- 用户window窗口-->
<div id="holiday-dlg" class="easyui-dialog"
     style="width: 380px; height: 250px; padding: 10px" modal="true"
     closed="true" buttons="#holiday-dlg-buttons">

    <form id="holiday-form" method="post">
        <table>
            <tr>
                <td colspan="2" style="height: 5px;"><input id="holidayId"
                                                            type="hidden" name="holiday.id"/></td>
            </tr>
            <%--<tr>--%>
            <%--<td class="tdRight"><label>编号：</label></td>--%>
            <%--<td class="tdLeft"><input id="serialNumber" type="text" class="easyui-validatebox"--%>
            <%--data-options="required:true,validType:'length[1,20]'"--%>
            <%--name="holiday.serialNumber" style="width: 160px;"/></td>--%>
            <%--</tr>--%>
            <tr>
                <td class="tdRight"><label>假日名称：</label></td>
                <td class="tdLeft"><input id="holidayName" type="text" class="easyui-validatebox"
                                          data-options="required:true,validType:'length[1,20]'"
                                          name="holiday.name" style="width: 160px;"/></td>
            </tr>

            <tr>
                <td class="tdRight"><label>开始日期：</label></td>
                <td class="tdLeft"><input id="startDate" name="holiday.startDate" type="text" style="width: 165px;"
                                          class="easyui-datebox"
                                          required="required">
                </td>
            </tr>
            <tr>
                <td class="tdRight"><label>结束日期：</label></td>
                <td class="tdLeft"><input id="endDate" name="holiday.endDate" type="text" style="width: 165px;"
                                          class="easyui-datebox"
                                          required="required"></td>
            </tr>

        </table>
    </form>
</div>
<!-- 用户window窗口的功能按钮-->
<div id="holiday-dlg-buttons">
    <a id="holiday-save-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-ok" onclick="saveHoliday()">保存</a>

    <a id="holiday-close-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#holiday-dlg').dialog('close')">取消</a>
</div>
<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

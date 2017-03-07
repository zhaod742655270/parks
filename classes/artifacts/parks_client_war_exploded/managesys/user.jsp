<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>用户管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/managesys/user.js"></script>
    <style type="text/css">
        #user-form td {
            height: 35px;
        }

        #user-form .tdRight {
            text-align: right;
            width: 100px;
        }
    </style>
</head>
<body>
<table id="user-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">userID</th>

        <th field="userName" sortable="true">用户名称</th>
        <th field="nickname" sortable="true">昵称</th>
        <th field="deptName">部门</th>
        <th field="roleNames">角色</th>

    </tr>

    </thead>
    <tbody>
    </tbody>
</table>
<!-- 用户列表工具栏-->
<div id="users-toolbar">
    <!-- 之所以把搜索框放在前面目的是为了兼容ie6，如果放在后面ie6中会折行显示 -->
    <div style="float: right; padding-top: 2px; padding-right: 5px;">
        <input id="user-search" class="easyui-searchbox"
               data-options="prompt:'请输入关键字',
                         searcher: function (value, name) {
                    $('#user-dg').datagrid('load', { key: value });
                }"/>
    </div>
    <div id="user-btns">
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-reload" plain="true"
           onclick="$('#user-dg').datagrid('reload');">刷新</a>
    </div>


</div>
<!-- 用户window窗口-->
<div id="user-dlg" class="easyui-dialog"
     style="width: 420px; height: 320px; padding: 10px" modal="true"
     closed="true" buttons="#user-dlg-buttons">

    <form id="user-form" method="post">
        <table>
            <tr>
                <td colspan="2" style="height: 5px;"><input id="userId"
                                                            type="hidden" name="user.id"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>用户名：</label></td>
                <td class="tdLeft"><input id="userName" type="text"
                                          name="user.userName" style="width: 200px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>密 码：</label></td>
                <td class="tdLeft"><input id="password" type="text" class="easyui-validatebox"
                                          data-options="required:true,validType:'length[4,20]'"
                                          name="user.password" style="width: 200px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>昵 称：</label></td>
                <td class="tdLeft"><input id="nickname" name="user.nickname"
                                          style="width: 200px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>部 门：</label></td>
                <td class="tdLeft"><input id="deptId" name="user.department.id"
                                          class="easyui-combobox"
                                          data-options="required:true,valueField:'id',textField:'deptName',url:'dept/deptList'"
                                          style="width: 205px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>角 色：</label></td>
                <td class="tdLeft"><input id="roleIds" name="roleIds"
                                          class="easyui-combobox"
                                          data-options="valueField:'id',textField:'roleName',multiple:'true',editable:'false',  url:'role/getAllRoles'"
                                          style="width: 205px;"/></td>


            </tr>

        </table>
    </form>
</div>
<!-- 用户window窗口的功能按钮-->
<div id="user-dlg-buttons">
    <a id="user-save-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-ok" onclick="saveUser()">保存</a>

    <a id="user-close-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#user-dlg').dialog('close')">取消</a>
</div>
<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>角色管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/managesys/role.js"></script>
    <style type="text/css">
        #role-form td {
            height: 35px;
        }

        #role-form .tdRight {
            text-align: right;
            width: 100px;

        }
    </style>
</head>
<body>
<table id="role-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">roleID</th>
        <th data-options="field:'roleName',sortable:true">角色名称</th>
        <th data-options="field:'roleDesc',sortable:true">描述</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<!-- 用户列表工具栏-->
<div id="roles-toolbar">
    <!-- 之所以把搜索框放在前面目的是为了兼容ie6，如果放在后面ie6中会折行显示 -->
    <div style="float: right; padding-top: 2px; padding-right: 5px;">
        <input id="role-search" class="easyui-searchbox"
               data-options="prompt:'请输入关键字',
                         searcher: function (value, name) {
                    $('#role-dg').datagrid('load', { key: value });
                }"/>
    </div>
    <div id="role-btns">
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-reload" plain="true"
           onclick="$('#role-dg').datagrid('reload');">刷新</a>
    </div>

</div>
<!-- 用户window窗口-->
<div id="role-dlg" class="easyui-dialog"
     style="width: 420px; height: 245px; padding: 10px" modal="true"
     closed="true" buttons="#role-dlg-buttons">

    <form id="role-form" method="post">
        <table>
            <tr>
                <td colspan="2" style="height: 5px;"><input id="roleId"
                                                            type="hidden" name="role.id"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>角色名称：</label></td>
                <td class="tdLeft"><input id="roleName" type="text" class="easyui-validatebox"
                                          data-options="required:true,validType:'length[1,20]'"
                                          name="role.roleName" style="width: 200px;"/></td>
            </tr>

            <tr>
                <td class="tdRight"><label>描述：</label></td>
                <td class="tdLeft"><textarea name="role.roleDesc" id="roleDesc" style="width: 200px; height:50px;">
                    ></textarea></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
            </tr>

        </table>
    </form>
</div>
<!-- 用户window窗口的功能按钮-->
<div id="role-dlg-buttons">
    <a id="role-save-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-ok" onclick="saveRole()">保存</a>

    <a id="role-close-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#role-dlg').dialog('close')">取消</a>
</div>
<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>
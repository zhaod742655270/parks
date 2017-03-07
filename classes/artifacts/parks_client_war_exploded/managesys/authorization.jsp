<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>授权管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/managesys/authorization.js"></script>
    <style type="text/css">
    </style>
</head>
<body class="easyui-layout">
<div data-options="region:'center'">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',split:false,border:false"
             title="权限"
             style="height: 65px; padding: 5px; background-color: #efefef;">
            子系统：
            <div id="appId" style="width: 200px;"></div>
            <a class="easyui-linkbutton" onclick="savePrivilege()"
               data-options="iconCls:'icon-ok',plain:true">保存</a>
        </div>

        <div data-options="region:'center',border:false">
            <ul style="margin: 10px;" id="privilege-tree"></ul>
            <div id="privilege-mm" class="easyui-menu" style="width: 120px;">
                <div onclick="chechedChildren()" data-options="iconCls:'icon-add'">全选</div>
                <div onclick="unchechedChildren()" data-options="iconCls:'icon-remove'">全不选</div>


            </div>
        </div>
    </div>

</div>
<div data-options="region:'west',split:true" style="width: 450px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center'">
            <table id="role-dg">
                <thead>
                <tr>
                    <th data-options="field:'roleId',hidden:true">roleId</th>
                    <th data-options="field:'roleName',width:150,sortable:true">角色</th>
                    <th data-options="field:'roleDesc',width:250">描述</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>

        </div>
    </div>
</div>
</body>
</html>
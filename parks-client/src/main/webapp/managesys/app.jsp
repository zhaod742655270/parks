<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>子系统管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/managesys/app.js"></script>
    <style type="text/css">
        #app-form td {
            height: 35px;
        }

        #app-form .tdRight {
            text-align: right;
            width: 100px;
        }
    </style>
</head>
<body>

<table id="app-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">appId</th>
        <th field="appName">系统名称</th>
        <th field="appPageUrl">页面地址</th>
        <%--<th field="appCode">系统编号</th>--%>
        <th field="appOrder">序号</th>
        <th field="appDesc">描述</th>
        <th field="isVisibleString">是否可见</th>
    </tr>

    </thead>
    <tbody>
    </tbody>
</table>
<!-- 子系统列表工具栏-->
<div id="apps-toolbar">

    <a class='easyui-linkbutton' iconcls='icon-add' plain='true' onclick='addApp()'>新建系统</a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconcls="icon-edit" plain="true" onclick="editApp()">编辑系统</a> <a
        href="javascript:void(0)" class="easyui-linkbutton"
        iconcls="icon-remove" plain="true" onclick="deleteApp()">删除系统</a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconcls="icon-reload" plain="true"
       onclick="$('#app-dg').datagrid('reload');">刷新</a>


</div>
<!-- 子系统window窗口-->
<div id="app-dlg" class="easyui-dialog"
     style="width: 420px; height: 335px; padding: 10px" modal="true"
     closed="true" buttons="#app-dlg-buttons">

    <form id="app-form" method="post">
        <table>
            <tr>
                <td colspan="2" style="height: 5px;"><input id="appId"
                                                            type="hidden" name="app.id"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>系统名称：</label></td>
                <td class="tdLeft"><input id="appName" type="text" class="easyui-validatebox"
                                          data-options="required:true,validType:'length[1,20]'"
                                          name="app.appName" style="width: 200px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>页面地址：</label></td>
                <td class="tdLeft"><input id="appPageUrl" type="text" class="easyui-validatebox"
                                          data-options="required:true"
                                          name="app.appPageUrl" style="width: 200px;"/></td>
            </tr>
            <%--<tr>--%>
            <%--<td class="tdRight"><label>系统编号：</label></td>--%>
            <%--<td class="tdLeft"><input id="appCode" type="text"--%>
            <%--name="app.appCode" style="width: 200px;"/></td>--%>
            <%--</tr>--%>
            <tr>
                <td class="tdRight"><label>序 号：</label></td>
                <td class="tdLeft"><input id="appOrder" name="app.appOrder" data-options="min:0,max:1000"
                                          class="easyui-numberspinner" required="true"
                                          style="width: 200px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>是否可见:</label></td>
                <td class="tdLeft"><select id="isVisible" name="app.isVisible" class="easyui-combobox"
                                           style="width: 200px;">
                    <option value=true selected="true">可见</option>
                    <option value=false>不可见</option>

                </select>
                </td>
            </tr>
            <tr>
                <td class="tdRight"><label>描 述：</label></td>
                <td class="tdLeft"><textarea id="appDesc" name="app.appDesc"
                                             style="width: 200px; height: 60px;"></textarea></td>
            </tr>


        </table>
    </form>
</div>
<!-- 子系统window窗口的功能按钮-->
<div id="app-dlg-buttons">
    <a id="app-save-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-ok" onclick="saveApp()">保存</a>

    <a id="app-close-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#app-dlg').dialog('close')">取消</a>
</div>

</body>
</html>
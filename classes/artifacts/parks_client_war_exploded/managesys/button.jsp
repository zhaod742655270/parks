<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>按钮管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="../resources/script/managesys/button.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <style type="text/css">
        #button-form td {
            height: 35px;
        }

        #button-form .tdRight {
            text-align: right;
            width: 100px;

        }
    </style>
</head>
<body>

<table id="button-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">btnId</th>
        <th data-options="field:'menuId',hidden:true">menuId</th>
        <th field="btnName">按钮名称</th>
        <th field="menuName">所属子菜单</th>
        <%--<th field="btnCode">按钮编号</th>--%>
        <th data-options="'field':'btnIconUrl',formatter:formatIcon">图标</th>
        <th field="btnScript">脚本</th>
        <th field="btnUrl">按钮地址</th>
        <th field="btnOrder">序号</th>
        <th field="isVisibleString">是否可见</th>
    </tr>

    </thead>

</table>
<!-- 按钮列表工具栏-->
<div id="buttons-toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconcls="icon-undo" plain="true" onclick="backMenu()">返回菜单</a> <a
        href="javascript:void(0)" class="easyui-linkbutton"
        iconcls="icon-add" plain="true" onclick="addBtn()">新建按钮</a> <a
        href="javascript:void(0)" class="easyui-linkbutton"
        iconcls="icon-edit" plain="true" onclick="editBtn()">编辑按钮</a> <a
        href="javascript:void(0)" class="easyui-linkbutton"
        iconcls="icon-remove" plain="true" onclick="deleteBtn()">删除按钮</a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconcls="icon-reload" plain="true"
       onclick="$('#button-dg').datagrid('reload');">刷新</a>


</div>
<!-- 按钮window窗口-->
<div id="button-dlg" class="easyui-dialog"
     style="width: 420px; height: 340px; padding: 10px" modal="true"
     closed="true" buttons="#button-dlg-buttons">

    <form id="button-form" method="post">
        <table>
            <tr>
                <td style="height: 5px;"><input id="btnId" type="hidden"
                                                name="btn.id"/></td>
                <td style="height: 5px;"><input id="menuId" type="hidden"
                                                name="btn.menuId"/></td>
            </tr>

            <tr>
                <td class="tdRight"><label>按钮名称：</label></td>
                <td class="tdLeft"><input id="btnName" class="easyui-validatebox"
                                          data-options="required:true,validType:'length[1,20]'" type="text"
                                          name="btn.btnName" style="width: 200px;"/></td>
            </tr>
            <%--<tr>--%>
            <%--<td class="tdRight"><label>编号：</label></td>--%>
            <%--<td class="tdLeft"><input id="btnCode" name="btn.btnCode"--%>
            <%--style="width: 200px;"/></td>--%>
            <%--</tr>--%>
            <tr>
                <td class="tdRight"><label>脚本：</label></td>
                <td class="tdLeft"><input id="btnScript" name="btn.btnScript"
                                          style="width: 200px;"/></td>
            </tr>

            <tr>
                <td class="tdRight"><label>按钮地址：</label></td>
                <td class="tdLeft"><input id="btnUrl" name="btn.btnUrl"
                                          required="true" style="width: 200px;"/></td>
            </tr>

            <tr>
                <td class="tdRight"><label>图标：</label></td>
                <td class="tdLeft"><input id="btnIconUrl" name="btn.btnIconUrl"
                                          class="easyui-combotree" style="width: 205px;"/></td>
            </tr>
            <tr>
                <td class="tdRight"><label>序号：</label></td>
                <td class="tdLeft"><input id="btnOrder" name="btn.btnOrder" data-options="min:0,max:1000"
                                          class="easyui-numberspinner" required="true"
                                          style="width: 205px;"/></td>

            </tr>
            <tr>
                <td class="tdRight"><label>是否可见:</label></td>
                <td class="tdLeft"><select id="isVisible" name="btn.isVisible" class="easyui-combobox"
                                           style="width: 205px;">
                    <option value='true' selected="true">可见</option>
                    <option value="false">不可见</option>

                </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 按钮window窗口的功能按钮-->
<div id="button-dlg-buttons">
    <a id="button-save-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-ok" onclick="saveBtn()">保存</a>

    <a id="button-close-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#button-dlg').dialog('close')">取消</a>
</div>


<input id="subMenuId" type="hidden" value="${param.menuId}"/>

</body>
</html>
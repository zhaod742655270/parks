<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>菜单管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/managesys/menu.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <style type="text/css">
        #menu-form td {
            height: 35px;
        }

        #menu-form .tdRight {
            text-align: right;
            width: 100px;
        }

        #submenu-form td {
            height: 35px;
        }

        #submenu-form .tdRight {
            text-align: right;
            width: 100px;
        }
    </style>
</head>
<body class="easyui-layout">

<div data-options="region:'west',split:true" style="width: 450px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',split:false,border:false"
             title="子系统"
             style="height: 60px; padding: 5px; background-color: #efefef;">
            子系统：
            <div id="appId" class="easyui-combobox" style="width: 200px;"></div>
        </div>

        <div data-options="region:'center',border:false">
            <table id="menu-dg">
                <thead>
                <tr>
                    <th data-options="field:'menuId',hidden:true">menuId</th>
                    <th data-options="field:'appId',hidden:true">appId</th>

                    <th field="menuName">主菜单</th>
                    <th field="appName">所属子系统</th>
                    <%--<th field="menuCode">编号</th>--%>
                    <th field="menuOrder">序号</th>
                    <th field="isVisibleString">是否可见</th>
                    <!-- 	<th field="menuIconUrl">图标</th> -->

                </tr>

                </thead>
                <tbody>
                </tbody>
            </table>
            <!-- 主菜单列表工具栏-->
            <div id="menus-toolbar">

                <a href="javascript:void(0)" class="easyui-linkbutton"
                   iconcls="icon-add" plain="true" onclick="addMenu()">新建主菜单</a> <a
                    href="javascript:void(0)" class="easyui-linkbutton"
                    iconcls="icon-edit" plain="true" onclick="editMenu()">编辑主菜单</a> <a
                    href="javascript:void(0)" class="easyui-linkbutton"
                    iconcls="icon-remove" plain="true" onclick="deleteMenu()">删除主菜单</a>
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   iconcls="icon-reload" plain="true"
                   onclick="$('#menu-dg').datagrid('reload');">刷新</a>


            </div>
            <!-- 主菜单window窗口-->
            <div id="menu-dlg" class="easyui-dialog"
                 style="width: 420px; height: 285px; padding: 10px" modal="true"
                 closed="true" buttons="#menu-dlg-buttons">

                <form id="menu-form" method="post">
                    <table>
                        <tr>
                            <td style="height: 5px;"><input id="menuId" type="hidden"
                                                            name="menu.id"/></td>
                            </td>
                        </tr>

                        <tr>
                            <td class="tdRight"><label>主菜单名称：</label></td>
                            <td class="tdLeft"><input id="menuName" class="easyui-validatebox"
                                                      data-options="required:true,validType:'length[1,20]'" type="text"
                                                      name="menu.menuName" style="width: 200px;"/></td>
                        </tr>
                        <tr>
                            <td class="tdRight"><label>所属子系统：</label></td>
                            <td class="tdLeft"><input id="menuAppId"
                                                      name="menu.appId" class="easyui-combobox"
                                                      data-options="valueField:'id',textField:'appName',required:true"
                                                      style="width: 205px;"/></td>
                        </tr>
                        <%--<tr>--%>
                        <%--<td class="tdRight"><label>编号：</label></td>--%>
                        <%--<td class="tdLeft"><input id="menuCode" name="menu.menuCode"--%>
                        <%--style="width: 200px;"/></td>--%>
                        <%--</tr>--%>
                        <tr>
                            <td class="tdRight"><label>序号：</label></td>
                            <td class="tdLeft"><input id="menuOrder" name="menu.menuOrder" data-options="min:0,max:1000"
                                                      class="easyui-numberspinner" required="true"
                                                      style="width: 205px;"/></td>
                        </tr>
                        <tr>
                            <td class="tdRight"><label>是否可见:</label></td>
                            <td class="tdLeft"><select id="menuIsVisible" name="menu.isVisible" class="easyui-combobox"
                                                       style="width: 205px;">
                                <option value='true' selected="true">可见</option>
                                <option value="false">不可见</option>

                            </select>
                            </td>
                        </tr>
                        <!-- <tr>
                            <td class="tdRight"><label>图标：</label></td>
                            <td class="tdLeft"><input id="dept" name="dept"
                                class="easyui-combobox"
                                data-options="valueField:'id',textField:'deptName',url:'dept/loadDepts'"
                                style="width: 200px;" /></td>
                        </tr> -->


                    </table>
                </form>
            </div>
            <!-- 主菜单window窗口的功能按钮-->
            <div id="menu-dlg-buttons">
                <a id="menu-save-btn" href="javascript:void(0)"
                   class="easyui-linkbutton" iconcls="icon-ok" onclick="saveMenu()">保存</a>

                <a id="menu-close-btn" href="javascript:void(0)"
                   class="easyui-linkbutton" iconcls="icon-cancel"
                   onclick="javascript:$('#menu-dlg').dialog('close')">取消</a>
            </div>
        </div>
    </div>

</div>
<div data-options="region:'center'">
    <table id="submenu-dg">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">menuID</th>
            <th data-options="field:'appId',hidden:true">sysyAppID</th>
            <th data-options="field:'parentId',hidden:true">parentID</th>
            <th field="menuName">子菜单名称</th>
            <th field="parentName">所属主菜单</th>
            <%--<th field="menuCode">编号</th>--%>
            <th field="menuPageUrl">页面地址</th>
            <th data-options="'field':'menuIconUrl',formatter:formatIcon">图标</th>
            <th field="menuOrder">序号</th>
            <th field="isVisibleString">是否可见</th>


        </tr>

        </thead>
        <tbody>
        </tbody>
    </table>
    <!-- 子菜单列表工具栏-->
    <div id="submenus-toolbar">

        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-add" plain="true" onclick="addSubmenu()">新建子菜单</a> <a
            href="javascript:void(0)" class="easyui-linkbutton"
            iconcls="icon-edit" plain="true" onclick="editSubmenu()">编辑子菜单</a> <a
            href="javascript:void(0)" class="easyui-linkbutton"
            iconcls="icon-remove" plain="true" onclick="deleteSubmenu()">删除子菜单</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-tip" plain="true" onclick="btnManage()">按钮管理</a> <a
            href="javascript:void(0)" class="easyui-linkbutton"
            iconcls="icon-reload" plain="true"
            onclick="$('#submenu-dg').datagrid('reload');">刷新</a>


    </div>
    <!-- 子菜单window窗口-->
    <div id="submenu-dlg" class="easyui-dialog"
         style="width: 420px; height: 345px; padding: 10px" modal="true"
         closed="true" buttons="#submenu-dlg-buttons">

        <form id="submenu-form" method="post">
            <table>
                <tr>
                    <td style="height: 5px;"><input id="subMenuId" type="hidden"
                                                    name="subMenu.id"/>
                        <input id="subMenuSysAppId" type="hidden" name="subMenu.appId"/></td>

                </tr>

                <tr>
                    <td class="tdRight"><label>子菜单名称：</label></td>
                    <td class="tdLeft"><input class="easyui-validatebox"
                                              data-options="required:true,validType:'length[1,20]'" id="subMenuName"
                                              type="text"
                                              name="subMenu.menuName" style="width: 200px;"/></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>所属主菜单</label></td>
                    <td class="tdLeft"><input id="subMenuParentId" class="easyui-combobox"
                                              data-options="valueField:'id',textField:'menuName'"
                                              name="subMenu.parentId" style="width: 205px;"/></td>
                </tr>
                <%--<tr>--%>
                <%--<td class="tdRight"><label>编号：</label></td>--%>
                <%--<td class="tdLeft"><input id="subMenuCode" name="subMenu.menuCode"--%>
                <%--style="width: 200px;"/></td>--%>
                <%--</tr>--%>
                <tr>
                    <td class="tdRight"><label>页面地址：</label></td>
                    <td class="tdLeft"><input id="subMenuPageUrl" name="subMenu.menuPageUrl"
                                              style="width: 200px;"/></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>图标：</label></td>
                    <td class="tdLeft"><input id="menuIcoUrl" name="subMenu.menuIconUrl"
                                              class="easyui-combotree" style="width: 205px;"/></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>序号：</label></td>
                    <td class="tdLeft">
                        <input id="subMenuOrder" name="subMenu.menuOrder" data-options="min:0,max:1000"
                               class="easyui-numberspinner" required="true"
                               style="width: 205px;"/></td>
                    </td>
                </tr>
                <tr>
                    <td class="tdRight"><label>是否可见:</label></td>
                    <td class="tdLeft"><select id="subMenuIsVisible" name="subMenu.isVisible" class="easyui-combobox"
                                               style="width: 205px;">
                        <option value='true' selected="true">可见</option>
                        <option value="false">不可见</option>

                    </select>
                    </td>
                </tr>

            </table>
        </form>
    </div>
    <!-- 主菜单window窗口的功能按钮-->
    <div id="submenu-dlg-buttons">
        <a id="submenu-save-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-ok" onclick="saveSubmenu()">保存</a>

        <a id="submenu-close-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#submenu-dlg').dialog('close')">取消</a>
    </div>
</div>

</body>
</html>
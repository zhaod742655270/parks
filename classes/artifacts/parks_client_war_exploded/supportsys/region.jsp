<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>区域管理</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/supportsys/region.js"></script>
    <style>
        #region-form td {
            height: 35px;
        }

        #region-form .tdRight {
            text-align: right;
            width: 100px;

        }
    </style>
</head>
<body>
<div class="easyui-panel easyui-layout" title="区域管理" data-options="fit:true">
    <!-- 部门列表工具栏-->
    <div data-options="region:'north',border:false" id="regions-menu" style="width: 100%; height: 30px; background-color: #efefef; padding-top: 2px;">
        <a  href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-add" onclick="addRegion()">add</a>
        <a  href="javascript:void(0)"
            class="easyui-linkbutton" iconcls="icon-edit" onclick="editRegion()">edit</a>
        <a  href="javascript:void(0)"
            class="easyui-linkbutton" iconcls="icon-remove" onclick="deleteRegion()">del</a>

    </div>
    <div data-options="region:'center',border:false">
        <ul style="margin: 10px;" id="region-tree">

        </ul>

        <div id="region-mm" style="width: 120px;">
        </div>
        <!-- 部门window窗口-->
        <div id="region-dlg" class="easyui-dialog"
             style="width: 420px; height:280px; padding: 10px" modal="true"
             closed="true" buttons="#region-dlg-buttons">

            <form id="region-form" method="post">
                <table>
                    <tr>
                        <td colspan="2" style="height: 5px;"><input id="regionId"
                                                                    type="hidden" name="id"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>区域名称：</label></td>
                        <td class="tdLeft"><input id="regionName" type="text" class="easyui-validatebox"
                                                  data-options="required:true,validType:'length[1,20]'"
                                                  name="regionName" style="width: 200px;"/></td>
                    </tr>

                    <tr>
                        <td class="tdRight"><label>上级区域：</label></td>
                        <td class="tdLeft"><input id="parentId" name="parentId"
                                                  class="easyui-combobox"
                                                  data-options="valueField:'id',textField:'regionName',url:'region/regionList'"
                                                  style="width: 205px;"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>描述：</label></td>
                        <td class="tdLeft"><textarea
                                style="width: 200px; height: 60px;"></textarea></td>
                    </tr>

                </table>
            </form>
        </div>
        <!-- 部门window窗口的功能按钮-->
        <div id="region-dlg-buttons">
            <a id="region-save-btn" href="javascript:void(0)"
               class="easyui-linkbutton" iconcls="icon-ok" onclick="saveRegion()">保存</a>

            <a id="region-close-btn" href="javascript:void(0)"
               class="easyui-linkbutton" iconcls="icon-cancel"
               onclick="javascript:$('#region-dlg').dialog('close')">取消</a>
        </div>
    </div>
    <input id="menuId" type="hidden" value="${param.menuId}"/>
</div>
</body>
</html>

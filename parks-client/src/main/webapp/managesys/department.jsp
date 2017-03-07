<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>部门管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/managesys/department.js"></script>

    <style type="text/css">
        /* .tree-title {font-size:medium;
        }
         */
        #dept-form td {
            height: 35px;
        }

        #dept-form .tdRight {
            text-align: right;
            width: 100px;
        }
    </style>
</head>
<body>
<div class="easyui-panel easyui-layout" title="部门管理" data-options="fit:true">
    <!-- 部门列表工具栏-->
    <div data-options="region:'north',border:false" id="depts-menu" style="width: 100%; height: 30px; background-color: #efefef; padding-top: 2px;">


    </div>
    <div data-options="region:'center',border:false">
        <ul style="margin: 10px;" id="dept-tree">

        </ul>

        <div id="dept-mm" style="width: 120px;">
        </div>
        <!-- 部门window窗口-->
        <div id="dept-dlg" class="easyui-dialog"
             style="width: 420px; height: 350px; padding: 10px" modal="true"
             closed="true" buttons="#dept-dlg-buttons">

            <form id="dept-form" method="post">
                <table>
                    <tr>
                        <td colspan="2" style="height: 5px;"><input id="deptId"
                                                                    type="hidden" name="id"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>部门名称：</label></td>
                        <td class="tdLeft"><input id="deptName" type="text" class="easyui-validatebox"
                                                  data-options="required:true,validType:'length[1,20]'"
                                                  name="deptName" style="width: 200px;"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>部门编号：</label></td>
                        <td class="tdLeft"><input id="deptCode" type="text"
                                                  name="deptCode" style="width: 200px;"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>部门简称：</label></td>
                        <td class="tdLeft"><input id="abbrName" name="abbrName"
                                                  style="width: 200px;"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>上级部门：</label></td>
                        <td class="tdLeft"><input id="parentId" name="parentId"
                                                  class="easyui-combobox"
                                                  data-options="valueField:'id',textField:'deptName',url:'dept/deptList'"
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
        <div id="dept-dlg-buttons">
            <a id="dept-save-btn" href="javascript:void(0)"
               class="easyui-linkbutton" iconcls="icon-ok" onclick="saveDept()">保存</a>

            <a id="dept-close-btn" href="javascript:void(0)"
               class="easyui-linkbutton" iconcls="icon-cancel"
               onclick="javascript:$('#dept-dlg').dialog('close')">取消</a>
        </div>
    </div>
    <input id="menuId" type="hidden" value="${param.menuId}"/>
</div>
</body>
</html>
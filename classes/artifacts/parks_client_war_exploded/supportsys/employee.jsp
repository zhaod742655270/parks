<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>员工管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/managesys/app.js"></script>
    <script type="text/javascript" src="../resources/script/supportsys/employee.js"></script>
    <style>
        #employee-form td {
            height: 32px;
        }

        .tdRight {
            text-align: right;
            width: 80px;

        }
    </style>
</head>

<body class="easyui-layout">
<div data-options="region:'west',split:true" style="width: 450px;">
    <ul style="margin: 10px;" id="dept-tree">
    </ul>
</div>
<div data-options="region:'center'">
    <table id="employee-dg">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true"> id</th>
            <th data-options="field:'empName'">姓名</th>
            <th data-options="field:'deptName',sortable:true">部门</th>
            <th data-options="field:'empPayNo'">工资号</th>
            <th data-options="field:'empIDNo'">身份证号</th>
            <th data-options="field:'empDutyName'">职务</th>
            <%--<th data-options="field:'empSex'">性别</th>--%>

        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <!-- 用户列表工具栏-->
    <div id="employees-toolbar">

        <div id="leave-btns">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               iconcls="icon-add" plain="true"
               onclick="addEmp()">新建</a>
            <a href="javascript:void('0')" class="easyui-linkbutton"
               iconcls="icon-edit" plain="true"
               onclick="editEmp()">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               iconcls="icon-remove" plain="true"
               onclick="deleteEmp()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               iconcls="icon-search" plain="true"
               onclick="openSearch()">检索</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               iconcls="icon-excel" plain="true"
               onclick="exportExcel()">导出</a>
        </div>
    </div>
    <div id="search-dlg" class="easyui-dialog" style="width: 330px; height:210px; padding: 10px" modal="true"
         closed="true" buttons="#search-dlg-buttons" title="检索框">
        <form  id="search-form" method="post" action="../supportsys/emp/exportExcel">
            <table>
                <tr>
                    <td class="tdRight"><label>部门：</label></td>
                    <td>
                        <input  id="searchDept" name="emp.deptId"
                               class="easyui-combobox"
                               data-options="valueField:'id',textField:'deptName',url:'../managesys/dept/getDeptsByDeptId'"
                                />
                    </td>
                </tr>
                <tr>
                    <td  class="tdRight"><label>姓名：</label></td>
                    <td><input  id="searchName" type="text" name="emp.empName"></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>工资号</label>：</td>
                    <td>
                        <input  id="searchPayNo" name="emp.empPayNo"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdRight"><label>身份证号：</label></td>
                    <td><input  id="searchIDNo" type="text" name="emp.empIDNo"></td>
                </tr>

            </table>
        </form>
    </div>
    <div id="search-dlg-buttons">
        <a href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-ok" onclick="searchEmp()">确定</a>

        <a href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#search-dlg').dialog('close')">取消</a>
    </div>
    <div id="employee-dlg" class="easyui-dialog"
         style="width: 500px; height:360px; padding: 10px" modal="true"
         closed="true" buttons="#employee-dlg-buttons">

        <form id="employee-form" method="post" enctype="multipart/form-data">
            <table>
                <tr>
                    <td colspan="4" style="height: 5px;"><input id="empID"
                                                                type="hidden" name="emp.id"/>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" rowspan="6">
                        <div style="border:solid #000000 1px;width: 150px;height: 200px;">
                            <img id="photo" src=""
                                 style="width: 150px;height: 200px;max-width:150px; max-height: 200px; overflow: hidden">
                        </div>


                    </td>

                    <td class="tdRight"><label>姓名：</label></td>
                    <td class="tdLeft"><input id="empName" class="easyui-validatebox" name="emp.empName"
                                              data-options="required:true"/></td>


                </tr>
                <tr>
                    <td class="tdRight">
                        <label>性别：</label>
                    </td>
                    <td class="tdLeft">
                        <input id="man" type="radio" name="emp.empSex" value="0"/>男
                        <input id="woman" type="radio" name="emp.empSex" value="1"/>女
                    </td>

                </tr>
                <tr>
                    <td class="tdRight"><label>工资号：</label></td>
                    <td class="tdLeft"><input id="empPayNo" class="easyui-validatebox" name="emp.empPayNo"
                                              data-options="required:true"/></td>

                </tr>
                <tr>
                    <td class="tdRight"><label>身份证号：</label></td>
                    <td class="tdLeft"><input id="empIDNo" class="easyui-validatebox" name="emp.empIDNo"
                                              data-options="required:false"/></td>

                </tr>

                <tr>
                    <td class="tdRight">
                        <label>部门：</label>
                    </td>
                    <td class="tdLeft">
                        <input id="deptId" name="emp.deptId"
                               class="easyui-combobox"
                               data-options="required:true,valueField:'id',textField:'deptName',url:'../managesys/dept/getDeptsByDeptId'"
                                />
                    </td>

                </tr>

                <tr>
                    <td class="tdRight">
                        <label>职务：</label>
                    </td>
                    <td class="tdLeft">
                        <input id="dutyId" name="emp.empDutyId"
                               class="easyui-combobox"
                               data-options="valueField:'id',textField:'typeName',url:'objectType/empDutyList'"
                                />
                    </td>

                </tr>

                <tr>
                    <td>
                        <a href="javascript:void(0)"
                           class="easyui-linkbutton" iconcls="icon-search" onclick="openBrowse();">浏览</a>

                        <input accept="image/gif,image/jpeg,image/png" type="file" id="file" value="选择照片"
                               name="upload" style="display: none"
                               onchange="preview('photo','file')"></td>
                    <td>
                        <a href="javascript:void(0)"
                           class="easyui-linkbutton" iconcls="icon-remove"
                           onclick="delPhoto('photo');">删除</a>
                    </td>
                    <td>
                        <input name="delPhotoFlag" id="delPhoto" type="hidden" value="false"/>
                    </td>
                    <td>
                    </td>
                </tr>

            </table>
        </form>
    </div>
    <!-- 部门window窗口的功能按钮-->
    <div id="employee-dlg-buttons">
        <a id="region-save-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-ok" onclick="saveEmp()">保存</a>

        <a id="region-close-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#employee-dlg').dialog('close')">取消</a>
    </div>
</div>
</body>
</html>

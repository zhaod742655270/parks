<%--
  Created by IntelliJ IDEA.
  User: Len
  Date: 14-8-26
  Time: 上午10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>员工考勤的开关</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/empSwitch.js"></script>
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
            <th data-options="field:'deptName',sortable:true">部门</th>
            <th data-options="field:'empName'">姓名</th>
            <th data-options="field:'Sex'">性别</th>
            <th data-options="field:'empAge'">年龄</th>
            <th data-options="field:'isInvolve'">开启状态</th>

            <%--<th data-options="field:' writeDate',width:100">录入时间</th>--%>
            <%--<th data-options="field:' userName'">录入人</th>--%>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <!-- 用户列表工具栏-->
    <div id="employees-toolbar">

        <div id="leave-btns">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               iconcls="icon-rewind" plain="true"
               onclick="switchAllEmp('1')">开启所有</a>
            <a href="javascript:void('0')" class="easyui-linkbutton"
               iconcls="icon-forward" plain="true"
               onclick="switchAllEmp('0')">关闭所有</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               iconcls="icon-add" plain="true"
               onclick="switchEmp('1')">开启</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               iconcls="icon-remove" plain="true"
               onclick="switchEmp('0')">关闭</a>
        </div>

    </div>
</div>
</body>
</html>

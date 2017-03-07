<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>考勤记录检索</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/attendanceRecord.js"></script>
    <style>
        #attendanceRecord-form td {
            height: 30px;
        }

        #attendanceRecord-form .tdRight {
            text-align: right;

        }
    </style>
</head>
<body>
<table id="attendanceRecord-dg">
    <thead>

    <tr>
        <th data-options="field:'id',hidden:true" rowspan="2">Item ID</th>
        <th data-options="field:'day'" rowspan="2">日期</th>
        <th data-options="field:'deptName'" rowspan="2">部门</th>
        <th data-options="field:'empName'" rowspan="2">姓名</th>
        <th colspan="6">上午</th>
        <th colspan="6">下午</th>
        <th colspan="4">汇总</th>
    </tr>
    <tr>
        <th data-options="field:'s1SignInTime',align:'center'">签到时间</th>
        <th data-options="field:'s1SignOutTime',align:'center'">签退时间</th>
        <th data-options="field:'s1ArriveLateNum',align:'center'">迟到</th>
        <th data-options="field:'s1LeaveEarlyNum',align:'center'">早退</th>
        <th data-options="field:'s1AbsentNum',align:'center'">旷工</th>
        <th data-options="field:'s1LeaveNum',align:'center'">请假</th>

        <th data-options="field:'s2SignInTime',align:'center'">签到时间</th>
        <th data-options="field:'s2SignOutTime',align:'center'">签退时间</th>
        <th data-options="field:'s2ArriveLateNum',align:'center'">迟到</th>
        <th data-options="field:'s2LeaveEarlyNum',align:'center'">早退</th>
        <th data-options="field:'s2AbsentNum',align:'center'">旷工</th>
        <th data-options="field:'s2LeaveNum',align:'center'">请假</th>

        <th data-options="field:'arriveLateCount',align:'center'">迟到</th>
        <th data-options="field:'leaveEarlyCount',align:'center'">早退</th>
        <th data-options="field:'absentCount',align:'center'">旷工</th>
        <th data-options="field:'leaveCount',align:'center'">请假</th>
    </tr>
    </thead>
    <tbody>

    </tbody>
</table>
<div id="attendanceRecords-toolbar">

    <form id="attendanceRecord-form" method="post" action="../attendancesys/record/exportExcel">
        <table>
            <tr>
                <td class="tdRight" style="width: 40px;">部门:</td>
                <td><input id="deptId" name="deptId"
                           class="easyui-combobox"
                           data-options="valueField:'id',textField:'deptName',url:'../managesys/dept/getDeptsByDeptId'"
                           style=""/></td>

                <td class="tdRight" style="width: 75px;">上午(选项):</td>
                <td>

                    <select class="easyui-combobox" id="timeSpan1Option" name="timeSpan1Option"
                            data-options="multiple:true"
                            style="width: 160px;">
                        <option value="迟到">迟到</option>
                        <option value="早退">早退</option>
                        <option value="旷工">旷工</option>
                        <option value="请假">请假</option>
                    </select>
                </td>
                <td class="tdRight" style="width: 40px;">日期:</td>
                <td><input id="date" name="date" class="easyui-datebox"/></td>

            </tr>
            <tr>
                <td class="tdRight">姓名:</td>
                <td><input id="userName" type="text" name="userName"/></td>
                <td class="tdRight">下午(选项):</td>
                <td>
                    <select id="timeSpan2Option" class="easyui-combobox" name="timeSpan2Option"
                            data-options="multiple:true"
                            style="width: 160px;">
                        <option value="迟到">迟到</option>
                        <option value="早退">早退</option>
                        <option value="旷工">旷工</option>
                        <option value="请假">请假</option>

                    </select>

                </td>

                <td></td>
                <td style="text-align: right">
                    &nbsp; <a class="easyui-linkbutton" onclick="recordQuery() "
                              iconcls="icon-search" plain="false">检索</a>
                    &nbsp; <a class="easyui-linkbutton" onclick="exportExcel()"
                              iconcls="icon-excel" plain="false">导出</a>
                </td>
            </tr>
        </table>
    </form>

</div>


<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>班次管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/shift.js"></script>
    <style type="text/css">

        #shift-form td {
            height: 35px;
        }

        #shift-form .tdRight {
            text-align: right;
            width: 110px;
        }

        #interval-form td {
            height: 35px;
        }

        #interval-form .tdRight {
            text-align: right;
            width: 95px;
        }

        .timeFlag {
            width: 65px;
        }

        .time {
            width: 75px;
        }

        input, label {
            vertical-align: middle
        }
    </style>
</head>

<body class="easyui-layout">
<div data-options="region:'center'">
    <table id="interval-dg">
        <thead>
        <tr>
            <th data-options=" field:'id',hidden:true">id
            </th>
            <th field="name">名称</th>
            <th field="type">类型</th>
            <th field="signInTimeBeginFormat">开始考勤时间</th>
            <th field="workTimeBeginFormat">上班时间</th>
            <th field="workTimeEndFormat">下班时间</th>
            <th field="signOutTimeEndFormat">结束考勤时间</th>
            <th field="needSignIn">必需签到</th>
            <th field="needSignOut">必需签退</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <!-- 时段列表工具栏-->
    <div id="intervals-toolbar">

        <a class='easyui-linkbutton' iconcls='icon-add' plain='true' onclick='addInterval()'>新建时段</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-edit" plain="true" onclick="editInterval()">编辑时段</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-remove" plain="true" onclick="deleteInterval()">删除时段</a>

    </div>
    <!-- 班次window窗口-->
    <div id="interval-dlg" class="easyui-dialog"
         style="width: 525px; height: 380px; padding: 10px" modal="true"
         closed="true" buttons="#interval-dlg-buttons">

        <form id="interval-form" method="post">

            <div class="easyui-panel" style="width: 490px; margin-bottom: 10px; padding: 10px 0px;">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tdRight"><label> 时段名称：</label></td>
                        <td class="tdLeft" style="width: 150px;"><input id="intervalName" class="easyui-validatebox"
                                                                        type="text" required="true"
                                                                        name="name" style="width: 135px;"/>
                        </td>
                        <td class="tdRight"><label>时段类型：</label></td>
                        <td class="tdLeft"><select id="intervalType" name="type"
                                                   style="width: 135px;">
                            <option value="上午时段">上午时段</option>
                            <option value="下午时段">下午时段</option>
                        </select>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="easyui-panel" style="width: 490px;">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td colspan="2" style="height: 5px; border: 0px">
                            <input id="shiftID" type="hidden" name="shiftID"/>
                            <input id="intervalID" type="hidden" name="id"/>
                            <input type="hidden" name="attendanceType" value="上班时段"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>开始考勤时间：</label></td>
                        <td class="tdLeft" style="width: 150px;">
                            <select id="signInTimeBeginFlag" name="signInTimeBeginFlag" class="easyui-combobox timeFlag"
                                    data-options="editable:false,onChange:validateTime">

                                <option value="0" selected="true">当天</option>
                                <option value="1">后一天</option>
                            </select>
                            <input id="signInTimeBegin" type="text" showSeconds="true" name="signInTimeBegin"
                                   class="time"/></td>

                        <td class="tdRight"><label>结束考勤时间：</label></td>

                        <td class="tdLeft" style="width: 150px;">
                            <select id="signOutTimeEndFlag" class="easyui-combobox timeFlag" name="signOutTimeEndFlag"
                                    data-options="editable:false,onChange:validateTime">

                                <option value="0" selected="true">当天</option>
                                <option value="1">后一天</option>
                            </select>
                            <input type="text" id="signOutTimeEnd" showSeconds="true" class="time"
                                   name="signOutTimeEnd"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>上班时间：</label></td>
                        <td class="tdLeft">
                            <select id="workTimeBeginFlag" class="easyui-combobox timeFlag" name="workTimeBeginFlag"
                                    data-options="editable:false,onChange:validateTime">

                                <option value="0" selected="true">当天</option>
                                <option value="1">后一天</option>
                            </select>
                            <input type="text" id="workTimeBegin" showSeconds="true"
                                   name="workTimeBegin" class="time"/></td>
                        <td class="tdRight"><label>下班时间：</label></td>
                        <td class="tdLeft">
                            <select id="workTimeEndFlag" class="easyui-combobox timeFlag" name="workTimeEndFlag"
                                    data-options="editable:false,onChange:validateTime">

                                <option value="0" selected="true">当天</option>
                                <option value="1">后一天</option>
                            </select>
                            <input id="workTimeEnd" type="text" showSeconds="true"
                                   name="workTimeEnd" class="time"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight"><label>迟到结束时间：</label></td>
                        <td class="tdLeft">
                            <select id="lateDeadLineFlag" class="easyui-combobox timeFlag" name="lateDeadLineFlag"
                                    data-options="editable:false,onChange:validateTime">

                                <option value="0" selected="true">当天</option>
                                <option value="1">后一天</option>
                            </select>
                            <input id="lateDeadLine" type="text" showSeconds="true"
                                   name="lateDeadLine" class="time"/></td>
                        <td class="tdRight"><label>早退开始时间：</label></td>
                        <td class="tdLeft">
                            <select id="earlyDeadLineFlag" class="easyui-combobox timeFlag" name="earlyDeadLineFlag"
                                    data-options="editable:false,onChange:validateTime">

                                <option value="0" selected="true">当天</option>
                                <option value="1">后一天</option>
                            </select>
                            <input id="earlyDeadLine" type="text" showSeconds="true"
                                   name="earlyDeadLine" class="time"/></td>
                    </tr>
                    <tr>
                        <td class="tdRight">允许迟到：</td>
                        <td class="tdLeft"><input id="lateDelay" type="text" value="5"
                                                  name="lateDelay" style="width: 50px;"/>分钟
                        </td>
                        <td class="tdRight">允许早退：</td>
                        <td class="tdLeft"><input id="earlyDelay" type="text" value="5"
                                                  name="earlyDelay" style="width: 50px;"/>分钟
                        </td>
                    </tr>


                </table>
            </div>

            <div class="easyui-panel" style="width: 490px;margin-top: 10px; padding: 10px; ">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td style="text-align: center">
                            <label>上班必需签到</label> <input id="needSignIn" type="checkbox"
                                                         name="needSignIn"
                                                         value="true" checked/>&nbsp;&nbsp;
                            <label>下班必需签退</label> <input id="needSignOut" type="checkbox"
                                                         name="needSignOut"
                                                         value="true" checked/>
                        </td>
                    </tr>
                </table>
                <%--<div style="margin: 10px 0px;">--%>
                <%--上班无有效签到记录时记--%>
                <%--<select class="easyui-combobox" style="width: 60px" name="invalidSignInAssert">--%>
                <%--<option selected="true" value="旷工">旷工</option>--%>
                <%--<option value="迟到">迟到</option>--%>
                <%--</select>&nbsp;&nbsp;--%>
                <%--<input id="signInDay" name="invalidSignInPunish" value="0.5"--%>
                <%--style="width: 60px;"/> 工作日--%>

                <%--</div>--%>
                <%--<div>--%>
                <%--下班无有效签退记录时记--%>
                <%--<select class="easyui-combobox" name="invalidSignOutAssert" style="width: 60px">--%>
                <%--<option selected="true" value="旷工">旷工</option>--%>
                <%--<option value="早退">早退</option>--%>
                <%--</select>&nbsp;&nbsp;--%>
                <%--<input type="text" value="0.5" id="signOutDay" name="invalidSignOutPunish"--%>
                <%--style="width: 60px;"/> 工作日--%>
                <%--</div>--%>
            </div>
        </form>
    </div>
    <!-- 班次window窗口的功能按钮-->
    <div id="interval-dlg-buttons">
        <a id="interval-save-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-ok" onclick="saveInterval()">保存</a>

        <a id="interval-close-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#interval-dlg').dialog('close')">取消</a>
    </div>

</div>
<div data-options="region:'west',split:true" style="width: 350px;">

    <table id="shift-dg">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">id</th>
            <%--<th data-options="field:'serialNumber',width:100">编号</th>--%>
            <th data-options="field:'name',width:150,sortable:true">名称</th>
            <th data-options="field:'description',width:100">简称</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <!-- 班次列表工具栏-->
    <div id="shifts-toolbar">

        <a class='easyui-linkbutton' iconcls='icon-add' plain='true' onclick='addShift()'>新建班次</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-edit" plain="true" onclick="editShift()">编辑班次</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconcls="icon-remove" plain="true" onclick="deleteShift()">删除班次</a>
    </div>
    <!-- 班次window窗口-->
    <div id="shift-dlg" class="easyui-dialog"
         style="width: 350px; height: 190px; padding: 10px" modal="true"
         closed="true" buttons="#shift-dlg-buttons">

        <form id="shift-form" method="post">

            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="2" style="height: 5px; border: 0px"><input
                            type="hidden" name="id"/>
                        <input id="shiftType" type="hidden" name="shiftType" value="NORMAL"/>
                    </td>
                </tr>

                <tr>
                    <td class="tdRight"><label>班次名称：</label></td>
                    <td class="tdLeft" style="width: 130px;">

                        <input type="text" class="easyui-validatebox"
                               required="true"
                               name="name"
                               style="width: 150px;"/>
                    </td>
                </tr>
                <tr>
                    <td class="tdRight"><label>班次简称：</label></td>
                    <td class="tdLeft"><input type="text" class="easyui-validatebox" required="true"
                                              name="description" style="width: 150px;"/></td>
                </tr>
            </table>

        </form>
    </div>
    <!-- 班次window窗口的功能按钮-->
    <div id="shift-dlg-buttons">
        <a id="shift-save-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-ok" onclick="saveShift()">保存</a>

        <a id="shift-close-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#shift-dlg').dialog('close')">取消</a>
    </div>
</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>进出记录实时显示</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/doorsys/accessEventLatest.js"></script>
    <style>
        input, label {
            vertical-align: middle
        }

        .status {
            color: rgb(255, 0, 0);
        }
    </style>
</head>
<body>
<table id="empCardEvent-dg">
    <thead>
    <tr>
        <th data-options="field:'eventTime'">时间</th>
        <th data-options="field:'doorName'">位置</th>
        <th data-options="field:'personName'">姓名</th>
        <th data-options="field:'deptName'">部门</th>
        <th data-options="field:'cardNo'">卡片ID</th>
        <th data-options="field:'plateNo'">车牌号</th>
        <th data-options="field:'idNo'">身份证号</th>
        <th data-options="field:'isValidPassDes'">是否通行</th>
        <th data-options="field:'ioType'">进出方向</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<div id="empCardEvents-toolbar">
    <div id="dashboard" style="font-weight: bold;font-size: 12px ; padding: 5px;">
        <label>园区内员工人数：</label><label class="status" name="empCnt"></label>&nbsp;&nbsp;
        <label>园区内访客人数：</label><label class="status" name="visCnt"></label>&nbsp;&nbsp;
        <label>园区内员工车辆数：</label><label class="status" name="empCarCnt"></label>&nbsp;&nbsp;
        <label>园区内访客车辆数：</label><label class="status" name="visCarCnt"></label>
    </div>
    <div style="padding: 5px;"><label>记录类型</label>
        <input id="recordType" name="recordType" class="easyui-combotree" value="2"
               data-options="url:'accessEventOptions.json',method:'get',required:true"
               style="width: 125px"/>&nbsp;
        <label>设备类型</label>
        <input class="easyui-combobox" name="deviceType" id="deviceType" value="90"
               data-options="valueField:'id',textField:'name',url:'deviceType.json',method:'get'"/>&nbsp;
        <a id="autoBtn" class="easyui-linkbutton" onclick=""
           iconcls="icon-cancel" plain="false">停止刷新</a>
        <a id="handBtn" class="easyui-linkbutton" onclick=""
           iconcls="icon-reload" plain="false">手动刷新</a>
    </div>
</div>
<input id="menuId" type="hidden" value="${param.menuId}"/>
<input id="isAuto" type="hidden" value='true'/>
</body>
</html>

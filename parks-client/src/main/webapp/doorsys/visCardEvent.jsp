<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>访客读卡数据查询</title>
        <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css" />
        <link rel="stylesheet" href="../resources/easyui/themes/icon.css" />
        <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
        <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
        <script src="../resources/easyui/js/easyui-extentions.js"></script>
        <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="../resources/script/common.js"></script>
        <script type="text/javascript" src="../resources/script/doorsys/visCardEvent.js"></script>
        <style>
            input,label {
                vertical-align: middle
            }
        </style>
    </head>

    <body>
        <table id="visCardEvent-dg" class="easyui-datagrid"
               data-options="title:'访客读卡数据列表', toolbar: '#visCardEvents-toolbar'">
            <thead>
                <tr>
                    <th data-options="field:'id',hidden:true">id</th>
                    <th data-options="field:'eTime',width:150,sortable:'true'">时间</th>
                    <th data-options="field:'personName',width:150,sortable:'true'">姓名</th>
                    <th data-options="field:'cardNo',width:150">卡号</th>
                    <th data-options="field:'idNo',width:150">身份证号</th>
                    <th data-options="field:'deptName',width:150">单位</th>
                    <th data-options="field:'doorName',width:150">位置</th>
                    <th data-options="field:'eResult',width:150">事件结果</th>
                    <th data-options="field:'ioType',width:150">进出方向</th>
                </tr>
            </thead>
        </table>

        <!--<div id="visCardEvent-dg" class="easyui-datagrid"></div>-->

        <div id="visCardEvents-toolbar">
            <form id="qForm" class="easyui-form">
                <table id="tbl">
                    <tr>
                        <td>
                            <input id="deptNameCheck" value="false" type="checkbox" />
                            <label>单位</label> &nbsp;
                            <input id="deptName" class="easyui-textbox"/>
                        </td>
                        <td>
                            <input id="doorNameCheck" value="false" type="checkbox"/>
                            <label>位置</label> &nbsp;
                            <input id="doorName" class="easyui-textbox"/>
                        </td>
                        <td>
                            &nbsp;<label>开始时间</label> &nbsp;
                            <input id="beginTime" class="easyui-datetimebox"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input id="personNameCheck" value="false" type="checkbox" />
                            <label>姓名</label> &nbsp;
                            <input id="personName" class="easyui-textbox"/>
                        </td>
                        <td>
                            <input id="cardNoCheck" value="false" type="checkbox" />
                            <label>卡号</label> &nbsp;
                            <input id="cardNo" class="easyui-textbox"/>
                        </td>
                        <td>
                            &nbsp;<label>结束时间</label> &nbsp;
                            <input id="endTime" class="easyui-datetimebox"/>
                        </td>
                        <td rowspan="2">&nbsp;
                            <a id="queryBtn" class="easyui-linkbutton" iconcls="icon-search" plain="false">检索</a>
                        </td>
                        <td rowspan="2">
                            <a id="exportBtn" class="easyui-linkbutton" iconcls="icon-excel" plain="false">导出</a>
                        </td>
                        <td rowspan="2">
                            <a id="printBtn" class="easyui-linkbutton" iconcls="icon-print" plain="false">打印</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <input id="menuId" type="hidden" value="${param.menuId}" />
    </body>
</html>

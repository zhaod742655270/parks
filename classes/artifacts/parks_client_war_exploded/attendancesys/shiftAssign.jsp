<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>排班管理</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.mask.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/shiftAssign.js"></script>
    <style>
        .hiddenColumn {
            display: none;
        }

        #shiftAssign-form td {
            height: 35px;
        }

        .background {
            background-color: #FAFAFA;
        }

        #shiftAssign-form .tdRight {
            text-align: right;
            width: 100px;

        }

        #shiftList td {
            text-align: center;
            border-right: 1px dotted #CCC;
            border-bottom: 1px solid #CCC;
            padding: 2px;
            white-space: nowrap;
            height: 25px;
            width: 80px;
        }

        td.over {
            background: #FBEC88;
        }

        td.del {
            background: #fba09a;
        }
    </style>
</head>
<body id="mockGrid" class="easyui-layout">
<div data-options="region:'west',border:false,split:true" style="width: 300px;">
    <div id="tt" class="easyui-tabs" data-options="fit:true,tools:'#tab-tools'">
        <div id="dept" title="部门">
            <ul style="margin: 10px;" id="dept-tree">
            </ul>
        </div>
        <div title="员工">

            <table id="employee-dg">
                <thead>
                <tr>
                    <th data-options="field:'id',hidden:true"> id</th>
                    <th data-options="field:'deptName',sortable:true">部门</th>
                    <th data-options="field:'empName'">姓名</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
            <div id="emp-toolbar">
                <input id="employee-search" class="easyui-searchbox"
                       data-options="prompt:'员工姓名',
                         searcher: function (value, name) {
                     var node = $('#dept-tree').tree('getSelected');
                    $('#employee-dg').datagrid('load', { key: value ,deptId:node.id});
                }"/>
            </div>
        </div>

    </div>
    <div id="tab-tools">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'"
           onclick="shiftAssign()">排班</a>

    </div>
</div>
<div data-options="region:'center',border:true">
    <div class="easyui-layout" data-options="fit:true">
        <div title="排班列表" data-options="region:'north',border:false,collapsible:false"
             style="background-color:#F6F6F6;height: 62px; ">
            <%--<div>--%>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton"--%>
            <%--iconcls="icon-add" plain="true"--%>
            <%--onclick="shiftAssign()">排班</a>--%>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton"--%>
            <%--iconcls="icon-cancel" plain="true"--%>
            <%--onclick="deleteShiftAssign()">删除</a>--%>

            <%--</div>--%>
            <div style="padding: 3px;">
                &nbsp; 姓名：<input id="empName" class="easyui-textbox" style="width: 80px;"/>
                &nbsp;月份
                <input id="beginMonth" class="easyui-datetimespinner"
                       data-options="formatter:formatter2,parser:parser2,selections:[[0,4],[5,7]]" style="width:80px;">

                &nbsp;至&nbsp;<input id="endMonth" class="easyui-datetimespinner" value="6/24/2014"
                                    data-options="formatter:formatter2,parser:parser2,selections:[[0,4],[5,7]]"
                                    style="width:80px;">
                &nbsp <a href="javascript:void(0)" class="easyui-linkbutton"
                         iconcls="icon-search" plain="true"
                         onclick="loadRecord()">查询</a>
            </div>

        </div>
        <div id="shiftList" data-options="region:'center',border:false">
            <table id="shiftAssign-dg" cellpadding="0" cellspacing="0" border="0"
                   style="border-collapse: collapse; border-spacing: 0; border-left: 1px dotted #CCC;    border-top: 1px solid #CCC;">


                <thead>
                <tr style="height: 24px; background-color: #EFEFEF;">
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <td class="hiddenColumn">
                        id
                    </td>
                    <td>
                        姓名
                    </td>
                    <td>
                        日期
                    </td>
                    <td>
                        1
                    </td>
                    <td>
                        2
                    </td>
                    <td>
                        3
                    </td>
                    <td>
                        4
                    </td>
                    <td>
                        5
                    </td>
                    <td>
                        6
                    </td>
                    <td>
                        7
                    </td>
                    <td>
                        8
                    </td>
                    <td>
                        9
                    </td>
                    <td>
                        10
                    </td>
                    <td>
                        11
                    </td>
                    <td>
                        12
                    </td>
                    <td>
                        13
                    </td>
                    <td>
                        14
                    </td>
                    <td>
                        15
                    </td>
                    <td>
                        16
                    </td>
                    <td>
                        17
                    </td>
                    <td>
                        18
                    </td>
                    <td>
                        19
                    </td>
                    <td>
                        20
                    </td>
                    <td>
                        21
                    </td>
                    <td>
                        22
                    </td>
                    <td>
                        23
                    </td>
                    <td>
                        24
                    </td>
                    <td>
                        25
                    </td>
                    <td>
                        26
                    </td>
                    <td>
                        27
                    </td>
                    <td>
                        28
                    </td>
                    <td>
                        29
                    </td>
                    <td>
                        30
                    </td>
                    <td>
                        31
                    </td>
                </tr>
                </thead>
                <tbody id="tContent">

                </tbody>
            </table>
        </div>
        <div data-options="region:'south', border:false" style="height: 32px;">
            <div id="pp" style="border: 1px solid #ccc;">
            </div>
        </div>
    </div>
    <!--右键菜单-->
    <div id="dmm" class="easyui-menu" data-options="onClick:deleteShiftAssign" style="width:120px;">

        <div data-options="name:'print',iconCls:'icon-cancel'">删除排班</div>

    </div>
    <div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width:120px;">

    </div>

</div>

<!-- 用户window窗口-->
<div id="shiftAssign-dlg" class="easyui-dialog"
     style="width: 380px; height: 270px; padding: 10px" modal="true"
     closed="true" buttons="#shiftAssign-dlg-buttons">

    <form id="shiftAssign-form" method="post">

        <table>

            <tr>
                <td class="tdRight"><label>名称：</label></td>
                <td class="tdLeft"><input id="shiftAssignName" readonly="true" type="text" name="name"
                                          style="width: 160px;"/></td>
            </tr>

            <tr>
                <td class="tdRight"><label>开始日期：</label></td>
                <td class="tdLeft"><input id="startDate" name="begin" type="text" style="width: 165px;"
                                          class="easyui-datebox"
                                          required="required">
                </td>
            </tr>
            <tr>
                <td class="tdRight"><label>结束日期：</label></td>
                <td class="tdLeft"><input id="endDate" name="end" type="text" style="width: 165px;"
                                          class="easyui-datebox"
                                          required="required"></td>
            </tr>
            <tr>
                <td class="tdRight"><label>规律班次：</label></td>
                <td class="tdLeft"><input id="shifts" name="regularShiftId" type="text" style="width: 165px;"
                                          required="required"></td>
            </tr>

        </table>
    </form>
</div>
<!-- 用户window窗口的功能按钮-->
<div id="shiftAssign-dlg-buttons">
    <a id="shiftAssign-save-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-ok" onclick="saveShiftAssign()">保存</a>

    <a id="shiftAssign-close-btn" href="javascript:void(0)"
       class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#shiftAssign-dlg').dialog('close')">取消</a>
</div>


<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

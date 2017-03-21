<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2016/12/7
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/officesys/softMaintenance.js"></script>
    <style type="text/css">
        #maintenanceDlg td {
            height: 30px;
            padding-left: 5px;
        }

        #handleDlg td {
            height: 30px;
            padding-left: 5px;
        }

        .tdLeft {
            text-align: right;
            width: 90px;
        }

        .tdRight{
            width: 130px;
        }

        label{
            font-size:12px;
        }

        input{
            width:130px
        }
    </style>

    <title>售后维护管理</title>
</head>
<body>
<!--维护记录列表-->
<table id="maintenance-dg"></table>

<!--维护记录工具栏-->
<div id="maintenance-toolbar">
    <div id="maintenance-btn" style="height: 30px">
        <td>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
               onclick="$('#maintenance-dg').datagrid('reload')" iconcls="icon-reload">刷新</a>
        </td>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="query-form">
            <table>
                <tr>
                    <td><label>项目名称</label><input class="text"  id="projectNameQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>产品名称</label><input class="text"  id="productNameQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>编号</label> <input class="text"  id="numberQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>登记人</label><input class="easyui-combobox"  id="regPersonQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>登记日期</label><input class="easyui-datebox"  id="regDateBegQuery"   style="width: 100px"/>
                        <label>&nbsp;至&nbsp;</label><input class="easyui-datebox"  id="regDateEndQuery"   style="width: 100px"/></td>
                    <td>&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="maintenanceQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!--新增修改记录界面-->
<div id="maintenanceDlg" class="easyui-dialog" style="padding: 10px" modal="true"
     closed="true" buttons="#maintenanceDlg-button">
    <form id="addMaintenance" method="post">
        <table>
            <tr>
                <td style="height: 5px;"><input type="hidden" id="id" name="maintenance.id"/></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>项目名称：</label></td>
                <td class="tdRight"><input id="projectName" class="easyui-validatebox" name="maintenance.projectName"
                                            data-options="required:true,validType:['length[0,20]']">&nbsp;</td>

                <td class="tdLeft"><label>产品名称：</label></td>
                <td class="tdRight"><input id="productName" class="easyui-validatebox" name="maintenance.productName"
                                           data-options="required:true,validType:['length[0,20]']">&nbsp;</td>

                <td class="tdLeft"><label>编号：</label></td>
                <td class="tdRight"><input id="number" class="easyui-numberbox" name="maintenance.number"
                                           data-options="required:true,precision:0"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>登录人：</label></td>
                <td class="tdRight"><input id="regPerson" class="easyui-combobox" name="maintenance.regPersonID">&nbsp;</td>

                <td class="tdLeft"><label>登录日期：</label></td>
                <td class="tdRight"><input id="regDate" class="easyui-datebox" name="maintenance.regDate">&nbsp;</td>

                <td class="tdLeft"><label>要求完成日期：</label></td>
                <td class="tdRight"><input id="hopeEndDate" class="easyui-datebox" name="maintenance.hopeEndDate"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>项目联系人：</label></td>
                <td class="tdRight"><input id="projectContracts" class="easyui-combobox" name="maintenance.contractsID">&nbsp;</td>

                <td class="tdLeft"><label>联系方式：</label></td>
                <td class="tdRight"><input id="phoneNo" class="easyui-numberbox" name="maintenance.phoneNo"
                                           data-options="precision:0"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>故障现象：</label></td>
                <td class="tdRight" rowspan="2" colspan="5"><input id="faultDesc" class="easyui-textbox" name="maintenance.faultDesc"
                                                                   style="width: 100%;height:80px" data-options="multiline:true"></td>
            </tr>
        </table>
    </form>
</div>

<!--新增修改记录界面按钮-->
<div id="maintenanceDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveMaintenance()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#maintenanceDlg').dialog('close')">取消</a>
</div>

<!--处理过程列表-->
<div id="handleDgd" class="easyui-dialog" style="width: 900px; height: 420px" modal="true"
     closed="true">
    <table id="handle-dg"></table>
</div>

<!--处理过程工具栏-->
<div id="handle-toolbar">
    <div id="handle-btn" style="height: 30px">
        <td>
            <a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
               onclick="addHandle()" iconcls="icon-add">新建处理过程</a>

            <a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
               onclick="editHandle()" iconcls="icon-edit">修改处理过程</a>

            <a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
               onclick="deleteHandle()" iconcls="icon-remove">删除处理过程</a>

            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
               onclick="$('#handle-dg').datagrid('reload')" iconcls="icon-reload">刷新</a>
        </td>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="queryHandle-form">
            <table>
                <tr>
                    <td><label>承担人</label> &nbsp;<input class="text"  id="handlePersonQuery"   style="width: 100px"/>&nbsp;</td>
                    <td><label>开始时间</label> &nbsp;<input class="easyui-datebox"  id="handleBegTimeQuery"   style="width: 100px"/>&nbsp;</td>
                    <td><label>结束时间</label> &nbsp;<input class="easyui-datebox"  id="handleEndTimeQuery"   style="width: 100px"/>&nbsp;</td>
                    <td><label>处理结果</label> &nbsp;<input class="text"  id="handleResultQuery"   style="width: 100px"/>&nbsp;</td>
                    <td rowspan="2">&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="handleQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!--处理过程修改界面-->
<div id="handleDlg" class="easyui-dialog" style="padding: 10px" modal="true" closed="true" buttons="#handleDlg-button">
    <form id="addHandle">
    <table>
        <tr>
            <td style="height: 5px;"><input type="hidden" id="idHandle" class="textbox" name="handle.id"/></td>
            <td style="height: 5px;"><input type="hidden" id="parentIdFK" class="textbox" name="handle.parentIdFK"/></td>
        </tr>
        <tr>
            <td class="tdLeft"><label>承担人：</label></td>
            <td class="tdRight"><input id="handlePerson" class="easyui-combobox" name="handle.handlePersonID">&nbsp;</td>

            <td class="tdLeft"><label>开始时间：</label></td>
            <td class="tdRight"><input id="handleBegTime" class="easyui-datebox" name="handle.handleBegTime"></td>
        </tr>
        <tr>
            <td class="tdLeft"><label>结束时间：</label></td>
            <td class="tdRight"><input id="handleEndTime" class="easyui-datebox" name="handle.handleEndTime">&nbsp;</td>

            <td class="tdLeft"><label>处理结果：</label></td>
            <td class="tdRight"><input id="handleResult" class="easyui-combobox" name="handle.handleResult"></td>
        </tr>
        <tr>
            <td class="tdLeft"><label>处理过程：</label></td>

            <td class="tdRight" rowspan="2" colspan="3"><input id="handleDesc" class="easyui-textbox" name="handle.handleDesc"
                                                               style="width: 100%;height:50px" data-options="multiline:true"></td>
        </tr>
    </table>
    </form>
</div>

<!--处理界面按钮-->
<div id="handleDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveHandle()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#handleDlg').dialog('close')">取消</a>
</div>

<!--审批界面-->
<div id="approveDlg" class="easyui-dialog" style="padding: 10px" modal="true" closed="true" buttons="#approveDlg-button">
    <form id="addApprove">
        <table>
            <tr>
                <td style="height: 5px;"><input type="hidden" id="approveId" name="maintenance.id"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>审批人：</label></td>
                <td class="tdRight"><input id="approvePerson" class="easyui-combobox" name="maintenance.approvePersonID"></td>

                <td class="tdLeft"><label>审批日期：</label></td>
                <td class="tdRight"><input id="approveDate" class="easyui-datebox" name="maintenance.approveDate"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>审批备注</label></td>
                <td class="tdRight" rowspan="2" colspan="3">
                    <input id="approveNote" class="easyui-textbox" name="maintenance.approveNote"
                           data-options="multiline:true" style="width: 100%;height: 50px">
                </td>
            </tr>
        </table>
    </form>
</div>

<!--审批界面按钮-->
<div id="approveDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveApprove()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#approveDlg').dialog('close')">取消</a>
</div>

<!--最终结论界面-->
<div id="resultDlg" class="easyui-dialog" style="padding: 10px" modal="true" closed="true" buttons="#resultDlg-button">
    <form id="addResult">
        <table>
            <tr>
                <td style="height: 5px;"><input type="hidden" id="resultId" name="maintenance.id"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>总结人：</label></td>
                <td class="tdRight"><input id="resultPerson" class="easyui-combobox" name="maintenance.resultPersonID"></td>

                <td class="tdLeft"><label>总结日期：</label></td>
                <td class="tdRight"><input id="resultDate" class="easyui-datebox" name="maintenance.resultDate"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>最终结论：</label></td>
                <td class="tdRight" colspan="4"><input id="result" class="easyui-textbox" name="maintenance.result"
                                                       style="width:100%;height: 50px" data-options="multiline:true"></td>
            </tr>
        </table>
    </form>
</div>

<!--最终结论界面按钮-->
<div id="resultDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveResult()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#resultDlg').dialog('close')">取消</a>
</div>

<!--指定处理人界面-->
<div id="assignDlg" class="easyui-dialog" style="padding: 10px" modal="true" closed="true" buttons="#assignDlg-button">
    <form id="addAssign">
        <table>
            <tr>
                <td style="height: 5px;"><input type="hidden" id="assignId" name="maintenance.id"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>指定处理人员：</label></td>
                <td class="tdRight"><input id="assignPerson" class="easyui-combobox" name="maintenance.assignPersonId"></td>
            </tr>
        </table>
    </form>
</div>

<!--指定处理人员界面按钮-->
<div id="assignDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveAssign()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#assignDlg').dialog('close')">取消</a>
</div>

<!--word文档模板选择-->
<div id="exportDlg" class="easyui-dialog" style="padding: 10px" modal="true"
     closed="true">
    <form id="exportForm" method="post" enctype="multipart/form-data">
        <input id="file"  name="file" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">&nbsp;
        <a class="easyui-linkbutton" style="width:80px" onclick="exportWordByModel()" >导出数据</a>　　　
    </form>
</div>

<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

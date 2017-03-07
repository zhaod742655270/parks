<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2017/2/23
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script type="text/javascript" src="../resources/script/officesys/expenditure.js"></script>
    <style type="text/css">
        #expenditureDlg td {
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
    </style>
    <title>经费支出记录</title>
</head>
<body>
    <!--经费支出记录列表-->
    <table id="expenditure-dg"></table>

    <!--经费支出工具栏-->
    <div id="expenditure-toolbar">
        <div id="expenditure-btn" style="height: 30px">
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#expenditure-dg').datagrid('reload')" iconcls="icon-reload">刷新</a>
            </td>
        </div>
        <div style="width:100%;height: 1px;border-bottom: 1px groove #effffc"></div>
        <div style="height: 30px">
            <form id="query-form">
                <table>
                    <tr>
                        <td><label>经办人</label> &nbsp;<input type="easyui-combobox"  id="recordPersonQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>
                        <td><label>项目名称</label> &nbsp;<input id="projectQuery" type="easyui-combobox"  style="width: 110px"/>&nbsp;&nbsp;</td>
                        <td colspan="2"><label>批准日期</label> &nbsp;<input class="easyui-datebox"  id="examineDateBeg"   style="width: 110px"/>
                            <label>至</label> <input class="easyui-datebox"  id="examineDateEnd"   style="width: 110px"/>&nbsp;&nbsp;</td>
                        <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="expenditureQuery()"
                                                 iconcls="icon-search" plain="false">查询</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <!--经费支出新增修改界面-->
    <div id="expenditureDlg" class="easyui-dialog" style="padding: 10px" modal="true"
             closed="true" buttons="#expenditureDlg-button">
        <form id="addExpenditure" method="post">
            <table>
                    <tr>
                        <td style="height: 5px;"><input type="hidden" id="id" name="expenditure.id"/></td>
                    </tr>

                    <tr>
                        <td class="tdLeft"><label>经办人：</label></td>
                        <td class="tdRight"><input id="recordPerson" class="easyui-combobox" name="expenditure.recordPersonId"
                                                   data-options="required:true,editable:false"></td>

                        <td class="tdLeft"><label>申请部门：</label></td>
                        <td class="tdRight"><input id="recordDepartment" name="expenditure.recordDepartment"></td>

                        <td class="tdLeft"><label>项目名称：</label></td>
                        <td class="tdRight"><input id="projectRecord" class="easyui-combobox" name="expenditure.projectRecordId"
                                                   data-options="required:true,editable:false"></td>
                    </tr>

                    <tr>
                        <td class="tdLeft"><label>项目支出内容：</label></td>
                        <td class="tdRight"><input id="expendContent" name="expenditure.expendContent"></td>

                        <td class="tdLeft"><label>批准人：</label></td>
                        <td class="tdRight"><input id="examinePerson" class="easyui-combobox" name="expenditure.examinePerson"></td>

                        <td class="tdLeft"><label>批准日期：</label></td>
                        <td class="tdRight"><input id="examineDate" class="easyui-datebox" name="expenditure.examineDate"></td>
                    </tr>

                    <tr>
                        <td class="tdLeft"><label>招待费用：</label></td>
                        <td class="tdRight"><input id="entertainCost" class="easyui-numberbox" name="expenditure.entertainCost"
                                                   data-options="precision:2,onChange:getAllCost"></td>

                        <td class="tdLeft"><label>交通费用：</label></td>
                        <td class="tdRight"><input id="trafficCost" class="easyui-numberbox" name="expenditure.trafficCost"
                                                   data-options="precision:2,onChange:getAllCost"></td>

                        <td class="tdLeft"><label>差旅费：</label></td>
                        <td class="tdRight"><input id="travelCost" class="easyui-numberbox" name="expenditure.travelCost"
                                                   data-options="precision:2,onChange:getAllCost"></td>
                    </tr>

                    <tr>
                        <td class="tdLeft"><label>礼品费：</label></td>
                        <td class="tdRight"><input id="giftCost" class="easyui-numberbox" name="expenditure.giftCost"
                                                   data-options="precision:2,onChange:getAllCost"></td>

                        <td class="tdLeft"><label>油料费：</label></td>
                        <td class="tdRight"><input id="oilCost" class="easyui-numberbox" name="expenditure.oilCost"
                                                   data-options="precision:2,onChange:getAllCost"></td>

                        <td class="tdLeft"><label>办公费：</label></td>
                        <td class="tdRight"><input id="officeCost" class="easyui-numberbox" name="expenditure.officeCost"
                                                   data-options="precision:2,onChange:getAllCost"></td>
                    </tr>

                    <tr>
                        <td class="tdLeft"><label>通讯费：</label></td>
                        <td class="tdRight"><input id="teleCost" class="easyui-numberbox" name="expenditure.teleCost"
                                                   data-options="precision:2,onChange:getAllCost"></td>

                        <td class="tdLeft"><label>差旅补助费：</label></td>
                        <td class="tdRight"><input id="travelSubsidy" class="easyui-numberbox" name="expenditure.travelSubsidy"
                                                   data-options="precision:2,onChange:getAllCost"></td>

                        <td class="tdLeft"><label>其他费用：</label></td>
                        <td class="tdRight"><input id="otherCost" class="easyui-numberbox" name="expenditure.otherCost"
                                                   data-options="precision:2,onChange:getAllCost"></td>
                    </tr>

                    <tr>
                        <td class="tdLeft"><label>费用合计：</label></td>
                        <td class="tdRight"><input id="totalCost" class="easyui-numberbox" name="expenditure.totalCost"
                                                   data-options="precision:2"></td>
                    </tr>

                    <tr>
                        <td class="tdLeft"><label>备注：</label></td>
                        <td rowspan="2" colspan="5"><input id="expenditureNote" class="easyui-textbox" name="expenditure.note"
                                                           data-options="multiline:true" style="width:100%;height:40px"></td>
                    </tr>
            </table>
        </form>
    </div>

    <!--经费支出新增修改界面按钮-->
    <div id="expenditureDlg-button">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveExpenditure()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#expenditureDlg').dialog('close')">取消</a>
    </div>

    <input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Zhang_F
  Date: 2016/5/16
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>项目备案</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/officesys/projectRecord.js"></script>
    <style type="text/css">

    #addRecord .tdRight {
        text-align: right;
        width: 85px;
        height: 40px;
    }

    </style>
</head>
<body>
<table id="record-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">projectRecordID</th>
        <th field="sheetName">年度</th>
        <th field="type">类型</th>
        <th field="recordSN">备案号</th>
        <th field="name" width="210">项目名称</th>
        <th field="contents" width="110">项目内容</th>
        <th field="place">项目地点</th>
        <th field="budget">项目预算（万元）</th>
        <th field="employer" width="100">业主单位</th>
        <th field="connection">业主联系人及联系方式</th>
        <th field="manager">项目负责人</th>
        <th field="recordDate" width="80">备案日期</th>
        <th field="biddingDate" width="80">投标日期</th>
        <th field="biddingName" width="60">是否投标</th>
        <th field="winBiddingName">是否中标</th>
        <th field="amount">中标金额(万元)</th>
        <th field="bidBond">投标保证金缴纳情况</th>
        <th field="bidSubject">投标主体</th>
        <th field="note">备注</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
    
</table>

<!-- 合同列表的工具栏-->
<div id="record-toolbar">
    <div style="height:30px;">
        <div id="projectRecord-btns">
            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#record-dg').datagrid('reload');" iconcls="icon-reload">刷新</a>
            </td>

        </div>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="query-form">
            <table>
                <tr>
                    <td><label>年度</label> &nbsp;<input class="easyui-combobox"  id="sheetNameQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>项目类型</label> &nbsp;<input class="easyui-combobox"  id="typeQuery" name="typeQuery"  style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>项目名称</label>&nbsp;<input type="text" id="nameQuery" name="nameQuery" style="width: 150px"/>&nbsp;&nbsp;</td>

                    <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="recordQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>

                </tr>
            </table>
        </form>
    </div>
</div>


<!-- 增加项目备案的窗口-->
<div id="recordDlg" class="easyui-dialog" style="width: 780px; height: 390px; padding: 10px" modal="true"
     closed="true" buttons="#recordDlg-buttons">
    <form id="addRecord" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="recordID"
                                                            type="hidden" name="record.id"/></td>
            </tr>

            <tr>
                <td class="tdRight"><label>年度：</label></td>
                <td class="tdLeft" ><input class="easyui-combobox" id="sheetName" name="record.sheetName"  style="width: 135px;"></td>

                <td class="tdRight"><label>类型：</label></td>
                <td class="tdLeft" ><input class="easyui-combobox" id="type" name="record.type" style="width: 135px;"/>

                <td class="tdRight"><label>备案号：</label></td>
                <td class="tdLeft"><input id="recordSN" name="record.recordSN" style="width: 130px;"/>

            </tr>

            <tr>
                <td class="tdRight"><label>项目名称：</label></td>
                <td class="tdLeft"><input id="name" name="record.name" style="width: 130px;"/>

                <td class="tdRight"><label>项目内容：</label></td>
                <td class="tdLeft"><input id="contents" name="record.contents" style="width: 130px;"/></td>

                <td class="tdRight"><label>项目地点：</label></td>
                <td class="tdLeft"><input id="place" name="record.place" style="width: 130px;"/></td>

            </tr>



            <tr>
                <td class="tdRight"><label>项目预算：</label></td>
                <td class="tdLeft"><input id="budget" name="record.budget" style="width: 130px;"/>

                <td class="tdRight"><label>业主单位：</label></td>
                <td class="tdLeft"><input id="employer" name="record.employer" style="width: 130px;"/></td>

                <td class="tdRight"><label>联系方式：</label></td>
                <td class="tdLeft"><input id="connection" name="record.connection" style="width: 130px;"/></td>

            </tr>


            <tr>
                <td class="tdRight"><label>项目负责人：</label></td>
                <td class="tdLeft"><input id="manager" name="record.manager" style="width: 130px;"/>

                <td class="tdRight"><label>备案日期：</label></td>
                <td class="tdLeft"><input class="easyui-datebox" id="recordDate" name="record.recordDate" style="width: 135px;"/></td>

                <td class="tdRight"><label>投标日期：</label></td>
                <td class="tdLeft"><input class="easyui-datebox" id="biddingDate" name="record.biddingDate" style="width: 135px;"/></td>

            </tr>

            <tr>
                <td class="tdRight"><label>是否投标：</label></td>
                <td class="tdLeft"><input  class="easyui-combobox" id="bidding" name="record.bidding" style="width: 135px;"/>

                <td class="tdRight"><label>是否中标：</label></td>
                <td class="tdLeft"><input class="easyui-combobox" id="winBidding" name="record.winBidding" style="width: 135px;"/></td>

                <td class="tdRight"><label>中标金额：</label></td>
                <td class="tdLeft"><input id="amount" name="record.amount" style="width: 130px;"/></td>

            </tr>


            <tr>
                <td class="tdRight"><label>投标保证金：</label></td>
                <td class="tdLeft"><input  id="bidBond" name="record.bidBond" style="width: 130px;"/>

                <td class="tdRight"><label>投标主体：</label></td>
                <td class="tdLeft"><input  id="bidSubject" name="record.bidSubject" style="width: 130px;"/></td>

                <td class="tdRight"><label>备注：</label></td>
                <td class="tdLeft"><input id="note" name="record.note" style="width: 130px;"/></td>

            </tr>

        </table>
    </form>
</div>

<!-- 增加项目备案窗口的保存，取消按键-->
<div id="recordDlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" id="savePayment" onclick="saveProjectRecord()">保存</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#recordDlg').dialog('close')">取消</a>
</div>

<!--右键菜单-->
<div id="rightMenu" class="easyui-menu">
    <div data-options="iconCls:'icon-calculator'" onclick="addExpenditure()">添加经费支出</div>
    <div data-options="iconCls:'icon-book'" onclick="openExpenditureList()">查看经费支出记录</div>
</div>

<!--查看经费支出界面-->
<div id="expenditureDgd" class="easyui-dialog" style="width: 900px; height: 420px; padding: 5px" modal="true"
     closed="true" data-options="resizable:true,maximizable:true,maximized:true">
    <table id="expenditure-dg"></table>
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
                <td class="tdRight"><input id="examinePerson" class="wasyui-combobox" name="expenditure.examinePerson"></td>

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

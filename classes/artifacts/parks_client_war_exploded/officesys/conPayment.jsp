<%--
  Created by IntelliJ IDEA.
  User: Zhang_F
  Date: 2016/3/18
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>付款合同管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/officesys/conPayment.js"></script>
    <style type="text/css">

    #addPayment td {
         height: 48px;
        }

    #paySum-form td {
        height: 50px;
    }

    </style>
</head>
<body>
<table id="conPayment-dg">

</table>

<!-- 列表的工具栏-->
<div id="conPayment-toolbar">
    <div style="height:30px;">
        <div id="conPayment-btns">
            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#conPayment-dg').datagrid('reload');"
                   iconcls="icon-reload">刷新</a>
            </td>

        </div>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="query-form">
            <table id="paymentQueryTable">
                <tr>
                    <td><label>合同类型</label> &nbsp;<input class="easyui-combobox"  id="contractTypeQuery"   style="width: 80px"/>&nbsp;&nbsp;</td>

                    <td><label>年度</label> &nbsp;<input class="easyui-combobox"  id="sheetNameQuery"   style="width: 80px"/>&nbsp;&nbsp;</td>

                    <td><label>合同名称</label> &nbsp;<input class="easyui-combobox"  id="contractNameQuery"   style="width: 160px"/>&nbsp;&nbsp;</td>

                    <td><label>乙方单位</label> &nbsp;<input type="text" id="companySecondQuery"   style="width: 160px"/>&nbsp;&nbsp;</td>

                    <td><label>采购负责人</label>&nbsp;<input  type="text" id="personQuery" style="width: 60px"/>&nbsp;&nbsp;</td>

                    <td><label>合同金额</label> &nbsp;<input class="easyui-numberbox"  id="contractSumQuery" data-options="precision:2" style="width: 70px"/>&nbsp;&nbsp;</td>

                    <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="paymentQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>

                </tr>
            </table>
        </form>
    </div>
</div>


<!-- 未付款合同右键弹出菜单-->
<div id="paymentRightMenu" class="easyui-menu">
    <div data-options="iconCls:'icon-help'" onclick="paymentPostil()">查看批注</div>
    <div data-options="iconCls:'icon-tip'" onclick="paymentLog()">查看记录</div>
    <%--<div data-options="iconCls:'icon-camera'" onclick="viewGathering()">查看收款合同</div>--%>
</div>


<!-- 增加付款合同窗口-->
<div id="paymentDlg" class="easyui-dialog" style="width: 560px; height: 480px; padding: 10px" modal="true"
     closed="true" buttons="#payment-buttons">
    <form id="addPayment" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="paymentID"
                                                            type="hidden" name="pay.id"/></td>
                <td colspan="1" style="height: 5px;"><input id="contractName"
                                                            type="hidden" name="pay.contractName"/></td>
            </tr>

             <tr>
                <td>&nbsp;&nbsp;<label>序号：</label></td>
                <td><input  id="contractSn" name="pay.contractSn" class="easyui-textbox" data-options="prompt:'必填'" style="width: 130px;">&nbsp;&nbsp;&nbsp;</td>

                 <td>&nbsp;&nbsp;<label>采购内容：</label></td>
                 <td><input id="purchaseContent" name="pay.purchaseContent" style="width: 130px;"/>

            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>甲方签约单位：</label></td>
                <td><input id="paymentCompanyFirst" name="pay.companyFirst" style="width: 130px;"/>

                <td>&nbsp;&nbsp;<label>乙方签约单位：</label></td>
                <td><input id="paymentCompanySecond" name="pay.companySecond" style="width: 130px;"/></td>


            </tr>

            <tr>

                <td>&nbsp;&nbsp;<label>年度：</label></td>
                <td><input id="sheetName" style="width: 130px;"/>

                <td>&nbsp;&nbsp;<label>所属项目类型：</label></td>
                <td><input id="belongType" style="width: 130px;"/></td>

            </tr>

            <tr>

                <td>&nbsp;&nbsp;<label>所属项目名称：</label></td>
                <td><input id="belongContractNames" name="belongContractNames"  class="easyui-combobox" data-options="multiple:'true'" style="width: 130px;"/>

                <td>&nbsp;&nbsp;<label>签订日期：</label></td>
                <td><input class="easyui-datebox" id="paymentSignDate" name="pay.signDate" style="width: 135px;"/></td>

            </tr>

            <tr>

                <td>&nbsp;&nbsp;<label>采购负责人：</label></td>
                <td><input class="easyui-combobox" id="purchasePerson" name="pay.purchasePerson" style="width: 130px;"/>

                <td>&nbsp;&nbsp;<label>合同金额：</label></td>
                <td><input class="easyui-numberbox" id="contractSum" name="pay.contractSum" data-options="precision:2" style="width: 135px;"/></td>

            </tr>

            <tr>

                <td>&nbsp;&nbsp;<label>已付款金额：</label></td>
                <td><input class="easyui-numberbox" id="payment" name="pay.payment" data-options="precision:2" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>未付款金额：</label></td>
                <td><input class="easyui-numberbox" id="paymentNo" name="pay.paymentNo" data-options="precision:2" style="width: 135px;"/></td>

            </tr>

            <tr>

                <td>&nbsp;&nbsp;<label>付款条件：</label></td>
                <td><textarea id="condition" name="pay.condition" style="width: 130px;"></textarea></td>

                <td>&nbsp;&nbsp;<label>备注：</label></td>
                <td><textarea id="paymentNote" name="pay.note" style="width: 130px;"></textarea></td>
                <td colspan="1" style="height: 5px;"><input id="sheet"
                                                            type="hidden" name="pay.sheetName"/></td>
                <td colspan="1" style="height: 5px;"><input id="contractType"
                                                            type="hidden" name="pay.contractType"/></td>

            </tr>

        </table>
    </form>
</div>

<!-- 增加付款合同窗口的保存，取消按键-->
<div id="payment-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" id="savePayment" onclick="savePayment()">保存</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#paymentDlg').dialog('close')">取消</a>
</div>


<!--付款合同汇总窗口-->
<div id="paySum-dlg" class="easyui-dialog"
     style="width: 450px; height: 200px; padding: 10px" modal="true"
     closed="true">

    <form id="paySum-form">
        <table>
            <tr>
                <td>&nbsp;&nbsp;<label>总条数：</label></td>
                <td><input class="easyui-numberbox" id="payCount" data-options="editable:false" style="width: 100px;">&nbsp;&nbsp;&nbsp;
                </td>

                <td>&nbsp;&nbsp;<label>合同金额：</label></td>
                <td><input class="easyui-numberbox" id="payAmountSum" data-options="precision:2,editable:false" style="width: 100px;"/>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>已收款金额：</label></td>
                <td><input  class="easyui-numberbox" id="payReceivedSum"  data-options="precision:2,editable:false" style="width: 100px;"/>

                <td>&nbsp;&nbsp;<label>未收款金额：</label></td>
                <td><input class="easyui-numberbox" id="payReceiveNoSum" data-options="precision:2" data-options="precision:2,editable:false" style="width: 100px;"/>

            </tr>

        </table>
    </form>

</div>


<!-- 增加 修改付款合同批注窗口-->
<div id="paymentPostil-dlg" class="easyui-dialog" style="width: 635px; height: 170px; padding: 10px" modal="true"
     closed="true" buttons="#paymentPostil-dlg-buttons">
    <form id="addPaymentPostil-form" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="paymentPostilId"
                                                            type="hidden" name="pay.paymentPostil.id"/></td>
                <td colspan="1" style="height: 5px;"><input id="parentID" type="hidden" name="pay.paymentPostil.parentID"></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>已付款金额批注：</label></td>
                <td><textarea id="paymentPostil" name="pay.paymentPostil.paymentPostil"
                              style="width: 175px;"></textarea></td>

                <td>&nbsp;&nbsp;<label>未付款金额批注：</label></td>
                <td><textarea id="paymentNoPostil" name="pay.paymentPostil.paymentNoPostil"
                              style="width: 175px;"></textarea></td>
            </tr>


        </table>
    </form>
</div>

<!-- 付款合同批注保存、取消功能按钮-->
<div id="paymentPostil-dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="savePaymentPostil()">保存</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#paymentPostil-dlg').dialog('close')">取消</a>
</div>



<!-- 未付款合同操作日志窗口-->
<div id="paymentLog-dlg" class="easyui-dialog"
     style="width: 700px; height: 480px; padding: 10px" modal="true"
     closed="true">

    <table id="paymentLog-table">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">paymentLogID</th>
            <th field="userName"  >用户名</th>
            <th field="date">日期</th>
            <th field="operateType">操作类型</th>
            <th field="contractSum">合同金额</th>
            <th field="contractSumChange">变化值</th>
            <th field="payment">已付款金额</th>
            <th field="paymentChange">变化值</th>
            <th field="paymentNo">未付款金额</th>
            <th field="paymentNoChange">变化值</th>

        </tr>
        </thead>

    </table>

</div>



<!-- 未付款合同操作查询窗口-->
<div id="operationPaymentQuery-dlg" class="easyui-dialog"
     style="width:850px; height: 480px; padding: 10px 10px 40px 10px;" modal="true"
     closed="true">


    <table id="operationPaymentDate-table">
        <tr>
            <td><label>开始日期：</label></td>
            <td><input class="easyui-datetimebox" id="paymentBeginDate" data-options="required:true,showSeconds:false" style="width: 125px;"/>&nbsp;&nbsp;


            <td><label>结束日期：</label></td>
            <td><input class="easyui-datetimebox" id="paymentEndDate" data-options="required:true,showSeconds:false" style="width: 125px;"/>&nbsp;&nbsp;

            <td><label>合同名称：</label></td>
            <td><input  id="paymentContractName" style="width: 130px;"/>&nbsp;&nbsp;

            <td><label>合同序号：</label></td>
            <td><input  id="paymentContractSn" style="width: 30px;"/>&nbsp;&nbsp;


            <td><a herf="javascript:void(0)" class="easyui-linkbutton" onclick="operationPaymentDisplay()"
                   iconcls="icon-search" plain="false">查询</a>
            </td>

        </tr>
    </table>

    <table id="operationPaymentQuery-table">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">paymentLogID</th>
            <th field="userName" data-options="sortable:true">用户名</th>
            <th field="contractName" data-options="sortable:true">合同名称</th>
            <th field="contractSn" >合同序号</th>
            <th field="date">日期</th>
            <th field="operateType" data-options="sortable:true">操作类型</th>
            <th field="contractSum">合同金额</th>
            <th field="contractSumChange">变化值</th>
            <th field="payment">已付款金额</th>
            <th field="paymentChange">变化值</th>
            <th field="paymentNo">未付款金额</th>
            <th field="paymentNoChange">变化值</th>
        </tr>
        </thead>

    </table>

</div>


<!-- 关联的收款合同窗口-->
<div id="conGathering-dlg" class="easyui-dialog"
     style="width: 970px; height: 480px; padding: 10px" modal="true"
     closed="true">

    <table id="conGathering-dg">

    </table>

</div>


<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

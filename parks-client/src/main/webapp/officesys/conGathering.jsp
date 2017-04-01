<%--
  Created by IntelliJ IDEA.
  User: Zhang_F
  Date: 2016/3/2
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>收款合同管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/officesys/conGathering.js"></script>
    <style type="text/css">
        #add-form td {
            height: 25px;
        }
        #addPostil-form td {
            height: 48px;
        }

        #moreQuery-form td {
            height: 48px;
        }

        #sum-form td {
            height: 50px;
        }

        #addPayment td {
            height: 48px;
        }

        #paySum-form td {
            height: 50px;
        }

        #addPaymentPostil-form td {
            height: 48px;
        }

    </style>
</head>
<body>
<table id="conGathering-dg">

</table>

<!-- 右键弹出菜单-->
<div id="rightMenu" class="easyui-menu">
    <div data-options="iconCls:'icon-help'" onclick="postil()">查看批注</div>
    <div data-options="iconCls:'icon-tip'" onclick="conLog()">查看记录</div>
    <%--<div data-options="iconCls:'icon-camera'" onclick="viewPayment()">查看付款合同</div>--%>
</div>
<!-- 列表的工具栏-->
<div id="conGathering-toolbar">
    <div style="height:30px;">
        <div id="conGathering-btns">
            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="exportExcel()"
                   iconcls="icon-reload">刷新</a>
                   <!--onclick="$('#conGathering-dg').datagrid('reload');"-->
            </td>

        </div>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="query-form">
            <table id="qcTable">
                <tr>

                    <td><label>年度</label> &nbsp;<input  class="easyui-combobox"  id="sheetNameQuery" style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>项目类型</label> &nbsp;<input class="easyui-combobox"  id="projectTypeQuery" style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>合同名称</label>&nbsp;<input type="text" id="conNameQuery" style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>签约单位</label>&nbsp;<input  type="text" id="comFirstQuery" style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>合同金额</label> &nbsp;<input class="easyui-numberbox"  id="amountQuery" data-options="precision:2" style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="moreQueryCon()"
                                             iconcls="icon-search" plain="false">更多条件</a>&nbsp;&nbsp;</td>

                    <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="query()"
                                             iconcls="icon-search" plain="false">查询</a></td>

                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 增加合同窗口-->
<div id="conGathering-dlg" class="easyui-dialog" style="padding: 10px" modal="true"
     closed="true" buttons="#con-dlg-buttons">
    <form id="add-form" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 1px;"><input id="id"
                                                            type="hidden" name="con.id"/></td>
            </tr>
            <tr>
                <td>&nbsp;&nbsp;<label>年度：</label></td>
                <td><input class="easyui-combobox" id="sheetName" name="con.sheetName" style="width: 135px;">&nbsp;&nbsp;&nbsp;
                </td>

                <td>&nbsp;&nbsp;<label>项目类型：</label></td>
                <td><input class="easyui-combobox" id="projectType" name="con.projectType" style="width: 135px;"
                           data-options="onSelect:onSelectProjectType"/>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>远东合同号：</label></td>
                <td><input id="contractNoYD" name="con.contractNoYD" style="width: 130px;"/>

                <td>&nbsp;&nbsp;<label>合同号：</label></td>
                <td><input id="contractNo" name="con.contractNo" style="width: 130px;"/>

            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>合同名称：</label></td>
                <td><input class="easyui-textbox" data-options="prompt:'弱电、零星类名称唯一'" id="contractName" name="con.contractName" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>甲方签约单位：</label></td>
                <td><input id="companyFirst" name="con.companyFirst" style="width: 130px;"/>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>乙方签约单位：</label></td>
                <td><input id="companySecond" name="con.companySecond" style="width: 130px;"/></td>

                <td>&nbsp;&nbsp;<label>签订日期：</label></td>
                <td><input class="easyui-datebox" id="signDate" name="con.signDate" style="width: 135px;"/></td>

            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>合同金额：</label></td>
                <td><input class="easyui-numberbox" id="amount" name="con.amount" data-options="precision:2" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>已收款金额：</label></td>
                <td><input class="easyui-numberbox" id="received" name="con.received" data-options="precision:2" style="width: 135px;"/></td>
            </tr>


            <tr>
                <td>&nbsp;&nbsp;<label>未收款金额：</label></td>
                <td><input class="easyui-numberbox" id="receiveNo" name="con.receiveNo" data-options="precision:2" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>挂账金额：</label></td>
                <td><input class="easyui-numberbox" id="oncredit" name="con.oncredit"  data-options="precision:2" style="width: 135px;"/></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>剩余金额：</label></td>
                <td><input class="easyui-numberbox" id="remain" name="con.remain" data-options="precision:2" style="width: 135px;"/></td>
                <td>&nbsp;&nbsp;<label>毛利：</label></td>
                <td><input class="easyui-numberbox" id="gross" name="con.gross"  data-options="precision:2" style="width: 135px;"/></td>

            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>市场负责人：</label></td>
                <td><input id="projectManager" name="con.projectManager" style="width: 130px;"/></td>

                <td>&nbsp;&nbsp;<label>工程负责人：</label></td>
                <td><input id="projectDirector" name="con.projectDirector" style="width: 130px;"/></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>项目进展：</label></td>
                <td><input id="isCompleted" name="con.isCompleted" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>盖章：</label></td>
                <td><input id="stamp" name="con.stamp" style="width: 135px;"/></td>

            </tr>


            <tr>
                <td>&nbsp;&nbsp;<label>验收日期：</label></td>
                <td><input class="easyui-datebox" id="acceptanceDate" name="con.acceptanceDate" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>对应原项目：</label></td>
                <td><input id="linkContract" class="easyui-combobox" name="con.linkContractId" style="width: 135px;"></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>备注：</label></td>
                <td colspan="3" rowspan="2"><input id="note" name="con.note" class="easyui-textbox"  style="width: 100%;height: 50px"
                                                   data-options="multiline:true"/></td>
            </tr>

        </table>
    </form>
</div>
<!-- 合同保存、取消功能按钮-->
<div id="con-dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" id="saveContract" onclick="saveContract()">保存</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#conGathering-dlg').dialog('close')">取消</a>
</div>

<!-- 增加 修改合同批注窗口-->
<div id="conGatheringPostil-dlg" class="easyui-dialog" style="width: 615px; height: 380px; padding: 10px" modal="true"
     closed="true" buttons="#conPostil-dlg-buttons">
    <form id="addPostil-form" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="PostilId"
                                                            type="hidden" name="con.contractGatheringPostil.id"/></td>
                <td colspan="1" style="height: 5px;"><input id="ContractId" type="hidden" name="con.contractGatheringPostil.gatheringID"></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>合同名称：</label></td>
                <td><textarea id="contractNamePostil" name="con.contractGatheringPostil.contractNamePostil"
                           style="width: 175px;"></textarea></td>

                <td>&nbsp;&nbsp;<label>验收时间：</label></td>
                <td><textarea id="receptionDatePostil" name="con.contractGatheringPostil.receptionDatePostil"
                              style="width: 175px;"></textarea></td>
            </tr>

            <tr>

                <td>&nbsp;&nbsp;<label>合作方：</label></td>
                <td><textarea id="partnerPostil" name="con.contractGatheringPostil.partnerPostil"
                              style="width: 175px;"></textarea></td>

                <td>&nbsp;&nbsp;<label>合同金额：</label></td>
                <td><textarea id="amountPostil" name="con.contractGatheringPostil.amountPostil"
                              style="width: 175px;"></textarea></td>

            </tr>

            <tr>

                <td>&nbsp;&nbsp;<label>已收款金额：</label></td>
                <td><textarea id="receivedPostil" name="con.contractGatheringPostil.receivedPostil"
                              style="width: 175px;"></textarea></td>

                <td>&nbsp;&nbsp;<label>未收款金额：</label></td>
                <td><textarea id="receiveNoPostil" name="con.contractGatheringPostil.receiveNoPostil"
                              style="width: 175px;"></textarea></td>
            </tr>


            <tr>

                <td>&nbsp;&nbsp;<label>挂账金额：</label></td>
                <td><textarea id="oncreditPostil" name="con.contractGatheringPostil.oncreditPostil"
                              style="width: 175px;"></textarea></td>

                <td>&nbsp;&nbsp;<label>剩余金额：</label></td>
                <td><textarea id="remainPostil" name="con.contractGatheringPostil.remainPostil"
                              style="width: 175px;"></textarea></td>
            </tr>

            <tr>

                <td>&nbsp;&nbsp;<label>毛利：</label></td>
                <td><textarea id="grossPostil" name="con.contractGatheringPostil.grossPostil"
                              style="width: 175px;"></textarea></td>
            </tr>


        </table>
    </form>
</div>

<!-- 合同批注保存、取消功能按钮-->
<div id="conPostil-dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="savePostil()">保存</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#conGatheringPostil-dlg').dialog('close')">取消</a>
</div>


<!-- 更多查询条件窗口-->
<div id="moreQuery-dlg"  class="easyui-dialog" style="width: 570px; height: 460px; padding: 10px" modal="true"
     closed="true" buttons="#moreQuery-dlg-buttons">
    <form id="moreQuery-form">
        <table>

            <tr>
                <td>&nbsp;&nbsp;<label>远东合同号：</label></td>
                <td><input id="contractNoYDQuery" name="contractNoYDQuery" style="width: 130px;"/>&nbsp;&nbsp;&nbsp;</td>

                <td>&nbsp;&nbsp;<label>合同号：</label></td>
                <td><input id="contractNoQuery" name="contractNoQuery" style="width: 130px;"/></td>

            </tr>


            <tr>
                <td>&nbsp;&nbsp;<label>乙方签约单位：</label></td>
                <td><input id="companySecondQuery" name="companySecondQuery" style="width: 130px;"/></td>

                <td>&nbsp;&nbsp;<label>签订日期：</label></td>
                <td><input class="easyui-datebox" id="signDateQuery" name="signDateQuery" style="width: 135px;"/></td>

            </tr>


            <tr>
                <td>&nbsp;&nbsp;<label>已收款金额：</label></td>
                <td><input class="easyui-numberbox" id="receivedQuery" name="receivedQuery" data-options="precision:2" style="width: 135px;"></td>

                <td>&nbsp;&nbsp;<label>挂账金额：</label></td>
                <td><input class="easyui-numberbox" id="oncreditQuery" name="oncreditQuery"  data-options="precision:2" style="width: 135px;"/>
            </tr>


            <tr>
                <td>&nbsp;&nbsp;<label>未收款金额：</label></td>
                <td><input class="easyui-numberbox" id="receiveNoQuery" name="receiveNoQuery" data-options="precision:2" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>剩余金额：</label></td>
                <td><input class="easyui-numberbox"  id="remainQuery" name="remainQuery" data-options="precision:2" style="width: 135px;"/></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>毛利：</label></td>
                <td><input class="easyui-numberbox" id="grossQuery" name="grossQuery" data-options="precision:2" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>工程负责人：</label></td>
                <td><input id="projectDirectorQuery" name="projectDirectorQuery" style="width: 130px;"/></td>


            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>市场负责人：</label></td>
                <td><input id="projectManagerQuery" name="projectManagerQuery" style="width: 130px;"/></td>

                <td>&nbsp;&nbsp;<label>盖章：</label></td>
                <td><input id="stampQuery" name="projectDirectorQuery" style="width: 135px;"/></td>


            </tr>

            <tr>
                 <td>&nbsp;&nbsp;<label>项目进展：</label></td>
                 <td><input id="isCompletedQuery" name="isCompletedQuery" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>验收日期：</label></td>
                <td><input class="easyui-datebox" id="acceptanceDateQuery" name="acceptanceDateQuery" style="width: 135px;"/></td>
            </tr>


        </table>
        </form>
</div>
<!-- 更多查询条件的 查询、取消按钮-->
<div id="moreQuery-dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-search" onclick="query()">查询</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#moreQuery-dlg').dialog('close')">取消</a>
</div>

<!-- 操作日志窗口-->
<div id="conLog-dlg" class="easyui-dialog"
     style="width: 950px; height: 480px; padding: 10px" modal="true"
     closed="true">

    <table id="conLog-table">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">conLogID</th>
            <th field="userName" >用户名</th>
            <th field="date">日期</th>
            <th field="operateType">操作类型</th>
            <th field="amount">合同金额</th>
            <th field="amountChange">变化值</th>
            <th field="received">已收款金额</th>
            <th field="receivedChange">变化值</th>
            <th field="receiveNo">未收款金额</th>
            <th field="receiveNoChange">变化值</th>
            <th field="oncredit">挂账金额</th>
            <th field="oncreditChange">变化值</th>
            <th field="remain">剩余金额</th>
            <th field="remainChange">变化值</th>
            <th field="gross">毛利</th>

        </tr>
        </thead>

    </table>

</div>

<!--收款合同汇总窗口-->
<div id="sum-dlg" class="easyui-dialog"
     style="width: 450px; height: 280px; padding: 10px" modal="true"
     closed="true">

    <form id="sum-form">
        <table>
            <tr>
                <td>&nbsp;&nbsp;<label>总条数：</label></td>
                <td><input class="easyui-numberbox" id="count" data-options="editable:false" style="width: 100px;">&nbsp;&nbsp;&nbsp;
                </td>

                <td>&nbsp;&nbsp;<label>合同金额：</label></td>
                <td><input class="easyui-numberbox" id="amountSum" data-options="precision:2,editable:false" style="width: 100px;"/>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>已收款金额：</label></td>
                <td><input  class="easyui-numberbox" id="receivedSum" name="contractNoYDQuery" data-options="precision:2,editable:false" style="width: 100px;"/>

                <td>&nbsp;&nbsp;<label>未收款金额：</label></td>
                <td><input class="easyui-numberbox" id="receiveNoSum" data-options="precision:2" data-options="precision:2,editable:false" style="width: 100px;"/>

            </tr>


            <tr>
                <td>&nbsp;&nbsp;<label>剩余金额：</label></td>
                <td><input class="easyui-numberbox" id="remainSum" name="receiveNoQuery" data-options="precision:2,editable:false" style="width: 100px;"/></td>

                <td>&nbsp;&nbsp;<label>挂账金额：</label></td>
                <td><input class="easyui-numberbox"  id="oncreditSum" name="remainQuery" data-options="precision:2,editable:false" style="width: 100px;"/></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>毛利：</label></td>
                <td><input class="easyui-numberbox" id="grossSum" name="grossQuery" data-options="precision:2,editable:false" style="width: 100px;"/></td>

            </tr>

        </table>
    </form>

</div>

<!-- 操作查询窗口-->
<div id="operationQuery-dlg" class="easyui-dialog"
     style="width: 980px; height: 480px; padding: 10px 10px 40px 10px;" modal="true"
     closed="true">

    <table id="operationDate-table">
        <tr>
            <td><label>开始日期：</label></td>
            <td><input class="easyui-datetimebox" id="beginDate" data-options="required:true,showSeconds:false" style="width: 135px;"/>&nbsp;&nbsp;


            <td><label>结束日期：</label></td>
            <td><input class="easyui-datetimebox" id="endDate" data-options="required:true,showSeconds:false" style="width: 135px;"/>&nbsp;&nbsp;

            <td><label>合同号：</label></td>
            <td><input  id="gContractNO" style="width: 150px;"/>&nbsp;&nbsp;

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" onclick="operationDisplay()"
                   iconcls="icon-search" plain="false">查询</a>
            </td>

        </tr>
    </table>

    <table id="operationQuery-table">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">conLogID</th>
            <th field="userName" >用户名</th>
            <th field="contractNo" >合同号</th>
            <th field="date">日期</th>
            <th field="operateType">操作类型</th>
            <th field="amount">合同金额</th>
            <th field="amountChange">变化值</th>
            <th field="received">已收款金额</th>
            <th field="receivedChange">变化值</th>
            <th field="receiveNo">未收款金额</th>
            <th field="receiveNoChange">变化值</th>
            <th field="oncredit">挂账金额</th>
            <th field="oncreditChange">变化值</th>
            <th field="remain">剩余金额</th>
            <th field="remainChange">变化值</th>
            <th field="gross">毛利</th>

        </tr>
        </thead>

    </table>

</div>

<!-------------------------------------------------------------------------------------------------->



<!-- 付款合同窗口-->
<div id="payment-dlg" class="easyui-dialog"
     style="width: 950px; height: 480px; padding: 10px" modal="true"
     closed="true">

    <table id="payment-table" data-options="sortName:'contractSn','sortOrder':'desc'">

    </table>

</div>


<!-- 未付款合同右键弹出菜单-->
<div id="paymentRightMenu" class="easyui-menu">
    <div data-options="iconCls:'icon-help'" onclick="paymentPostil()">查看批注</div>
    <div data-options="iconCls:'icon-tip'" onclick="paymentLog()">查看记录</div>
</div>

<!-- 付款合同toolbar-->
<div id="payment-toolbar">
    <div style="height:30px;">
        <div id="payment-btns">
            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="addPayment()" iconcls="icon-add">新建</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="editPayment()" iconcls="icon-edit">修改</a>
            </td>


            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="deletePayment()" iconcls="icon-remove">删除</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="sumPayment()" iconcls="icon-sum">统计</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="operationPaymentQuery()" iconcls="icon-camera">操作查询</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#payment-table').datagrid('reload');"
                   iconcls="icon-reload">刷新</a>
            </td>

        </div>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="payment-form">
            <table id="paymentQueryTable">
                <tr>

                    <td><label>乙方单位</label> &nbsp;<input type="text"   id="pCompanySecondQuery"   style="width: 100px"/>&nbsp;&nbsp;</td>

                    <td><label>采购内容</label> &nbsp;<input type="text"   id="pContentQuery"   style="width: 100px"/>&nbsp;&nbsp;</td>

                    <td><label>采购负责人</label>&nbsp;<input  type="text" id="pPersonQuery"  style="width: 80px"/>&nbsp;&nbsp;</td>

                    <td><label>合同金额</label> &nbsp;<input class="easyui-numberbox"  id="contractSumQuery"  data-options="precision:2" style="width: 80px"/>&nbsp;&nbsp;</td>

                    <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="payQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>

                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 增加付款合同窗口-->
<div id="paymentDlg" class="easyui-dialog" style="width: 560px; height: 420px; padding: 10px" modal="true"
     closed="true" buttons="#payment-buttons">
    <form id="addPayment" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="paymentId"
                                                            type="hidden" name="pay.id"/></td>
                <td colspan="1" style="height: 5px;"><input id="paymentContractName"
                                                            type="hidden" name="pay.contractName"/></td>
                <td colspan="1" style="height: 5px;"><input id="belongContractId"
                                                            type="hidden" name="belongContractNames"/></td>
            </tr>
            <tr>
                <td>&nbsp;&nbsp;<label>序号：</label></td>
                <td><input  id="contractSn" name="pay.contractSn" style="width: 130px;">&nbsp;&nbsp;&nbsp;
                </td>

                <td>&nbsp;&nbsp;<label>甲方签约单位：</label></td>
                <td><input id="paymentCompanyFirst" name="pay.companyFirst" style="width: 130px;"/>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>乙方签约单位：</label></td>
                <td><input id="paymentCompanySecond" name="pay.companySecond" style="width: 130px;"/></td>

                <td>&nbsp;&nbsp;<label>采购内容：</label></td>
                <td><input id="purchaseContent" name="pay.purchaseContent" style="width: 130px;"/>

            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>签订日期：</label></td>
                <td><input class="easyui-datebox" id="paymentSignDate" name="pay.signDate" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>采购负责人：</label></td>
                <td><input id="purchasePerson" name="pay.purchasePerson" style="width: 130px;"/>
            </tr>


            <tr>
                <td>&nbsp;&nbsp;<label>合同金额：</label></td>
                <td><input class="easyui-numberbox" id="contractSum" name="pay.contractSum" data-options="precision:2" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>已付款金额：</label></td>
                <td><input class="easyui-numberbox" id="payment" name="pay.payment" data-options="precision:2" style="width: 135px;"/></td>
            </tr>


            <tr>
                <td>&nbsp;&nbsp;<label>未付款金额：</label></td>
                <td><input class="easyui-numberbox" id="paymentNo" name="pay.paymentNo" data-options="precision:2" style="width: 135px;"/></td>

                <td>&nbsp;&nbsp;<label>付款条件：</label></td>
                <td><textarea id="condition" name="pay.condition" style="width: 130px;"></textarea></td>

            </tr>

            <tr>
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
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="savePayment()">保存</a>

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
            <th field="userName" >用户名</th>
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
     style="width: 850px; height: 480px; padding: 10px 10px 40px 10px;" modal="true"
     closed="true">


    <table id="operationPaymentDate-table">
        <tr>
            <td><label>开始日期：</label></td>
            <td><input class="easyui-datetimebox" id="paymentBeginDate" data-options="required:true,showSeconds:false" style="width: 125px;"/>&nbsp;&nbsp;


            <td><label>结束日期：</label></td>
            <td><input class="easyui-datetimebox" id="paymentEndDate" data-options="required:true,showSeconds:false" style="width: 125px;"/>&nbsp;&nbsp;

            <td><label>合同序号：</label></td>
            <td><input  id="paymentContractSn" style="width: 40px;"/>&nbsp;&nbsp;

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" onclick="operationPaymentDisplay()"
                   iconcls="icon-search" plain="false">查询</a>
            </td>

        </tr>
    </table>

    <table id="operationPaymentQuery-table">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">paymentLogID</th>
            <th field="userName" >用户名</th>
            <th field="contractName" >合同名称</th>
            <th field="contractSn" >合同号</th>
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


<!---------------------------------------------------------------------->
<!-- 附加合同窗口-->
<div id="linkContract-dlg" class="easyui-dialog"
     style="width: 950px; height: 480px; padding: 10px" modal="true"
     closed="true">
    <table id="linkContract-table"></table>
</div>

<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

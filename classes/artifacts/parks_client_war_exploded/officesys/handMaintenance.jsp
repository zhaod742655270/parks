<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2016/12/15
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script type="text/javascript" src="../resources/script/officesys/handMaintenance.js"></script>

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
            width: 100px;
        }

        .tdRight{
            width: 130px;
        }

        label{
            font-size:12px;
        }
    </style>

    <title>产品维修记录</title>
</head>
<body>
<!--维修记录-->
<table id="maintenance-dg"></table>

<!--维修记录工具栏-->
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
                    <td><label>产品名称型号</label><input class="text"  id="productNameQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>编号</label> <input class="text"  id="numberQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>登记人</label><input class="easyui-combobox"  id="registerPersonQuery"   style="width: 110px"/>&nbsp;</td>
                    <td colspan="2"><label>登记日期</label><input class="easyui-datebox"  id="regDateBegQuery"   style="width: 100px"/>
                        <label>&nbsp;至&nbsp;</label><input class="easyui-datebox"  id="regDateEndQuery"   style="width: 100px"/></td>
                    <td>&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="maintenanceQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!--维修记录修改界面-->
<div id="maintenanceDlg" class="easyui-dialog" style="padding:10px"
    modal="true" closed="true" buttons="#maintenanceDlg-button">
    <form id="addMaintenance" method="post">
        <table>
            <tr>
                <td style="height: 5px;"><input type="hidden" id="id" name="maintenance.id"/></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>项目名称：</label></td>
                <td class="tdRight"><input id="projectName" class="easyui-validatebox" name="maintenance.projectName"
                                           data-options="required:true,validType:['length[0,20]']"></td>

                <td class="tdLeft"><label>产品名称型号：</label></td>
                <td class="tdRight"><input id="productName" class="easyui-validatebox" name="maintenance.productName"
                                           data-options="required:true,validType:['length[0,50]']"></td>

                <td class="tdLeft"><label>编号：</label></td>
                <td class="tdRight"><input id="number" class="easyui-numberbox" name="maintenance.number"
                                           data-options="required:true,precision:0"></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>登记人：</label></td>
                <td class="tdRight"><input id="registerPerson" class="easyui-combobox" name="maintenance.registerPersonID"></td>

                <td class="tdLeft"><label>登记日期：</label></td>
                <td class="tdRight"><input id="registerDate" class="easyui-datebox" name="maintenance.registerDate"></td>

                <td class="tdLeft"><label>数量：</label></td>
                <td class="tdRight"><input id="quantity" class="easyui-numberbox" name="maintenance.quantity"
                                           data-options="precision:0"></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>要求完成日期：</label></td>
                <td class="tdRight"><input id="hopeEndDate" class="easyui-datebox" name="maintenance.hopeEndDate"></td>

                <td class="tdLeft"><label>产品编号：</label></td>
                <td class="tdRight"><input id="productNo" class="easyui-validatebox" name="maintenance.productNo"
                                           data-options="validType:['length[0,20]']"></td>

                <td class="tdLeft"><label>固件版本号：</label></td>
                <td class="tdRight"><input id="firmwareVersion" class="easyui-validatebox" name="maintenance.firmwareVersion"
                                           data-options="validType:['length[0,20]']"></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>硬件版本号：</label></td>
                <td class="tdRight"><input id="handwareVersion" class="easyui-validatebox" name="maintenance.handwareVersion"
                                           data-options="validType:['length[0,20]']"></td>
            </tr>
        </table>
        <table>
            <tr>
                <td class="tdLeft"><label>维修\测试依据：</label></td>
                <td style="width:400px">
                    <input id="repairBasis" name="maintenance.repairBasis" style="width:280px">&nbsp;&nbsp;
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectBasis()">选择依据</a>
                </td>
            </tr>
            <tr>
                <td class="tdLeft"><label>维修\测试类别：</label></td>
                <td style="width:400px">
                    <input id="repairType" name="maintenance.repairType" style="width:280px">&nbsp;&nbsp;
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectType()">选择类别</a>
                </td>
            </tr>
        </table>
        <table>
            <tr height="1px">
                <td style="height:1px;width:460px"></td>
                <td class="tdLeft" style="height:1px"></td><td class="tdRight" style="height:1px"></td>
            </tr>
            <tr>
                <td><label>故障上报现象\测试内容：</label></td>
                <td class="tdLeft"><label>上报人：</label></td>
                <td class="tdRight"><input id="reportPerson" class="easyui-combobox" name="maintenance.reportPersonID"></td>
            </tr>
            <tr>
                <td class="tdRight" rowspan="2" colspan="3">
                    <input id="faultContent" class="easyui-textbox" name="maintenance.faultContent"
                           style="width: 100%;height:60px" data-options="multiline:true">
                </td>
            </tr>
        </table>
    </form>
</div>

<!--新增修改界面按钮-->
<div id="maintenanceDlg-button">
    <a href="javascript:void(0)" id="btn-ok" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveMaintenance()">保存</a>
    <a href="javascript:void(0)" id="btn-cancel" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#maintenanceDlg').dialog('close')">取消</a>
    <a href="javascript:void(0)" id="btn-close" class="easyui-linkbutton"
       onclick="javascript:$('#maintenanceDlg').dialog('close')">确定</a>
</div>

<!--选择维修\测试依据界面-->
<div id="repairBasisDlg" class="easyui-dialog" style="padding:10px"
     closed="true" buttons="#repairBasisDlg-button">
    <table>
        <tr>
            <td><input id="repairBasis-1" type="checkbox"></td>
            <td><label>产品说明书</label></td>
            <td><input id="repairBasis-2" type="checkbox"></td>
            <td><label>技术要求</label></td>
            <td><input id="repairBasis-3" type="checkbox"></td>
            <td><label>工程设计方案</label></td>
        </tr>
        <tr>
            <td><input id="repairBasis-4" type="checkbox"></td>
            <td><label>其他(请说明)：</label></td>
           <td colspan="4"><input id="repairBasis-4text"></td>
        </tr>
    </table>
</div>

<!--选择维修\测试依据界面按钮-->
<div id="repairBasisDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveBasis()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#repairBasisDlg').dialog('close')">取消</a>
</div>

<!--选择维修\测试类型界面-->
<div id="repairTypeDlg" class="easyui-dialog" style="padding:10px"
     closed="true" buttons="#repairTypeDlg-button">
    <table>
        <tr>
            <td><input id="repairType-1" type="checkbox"></td>
            <td><label>功能</label></td>
            <td><input id="repairType-2" type="checkbox"></td>
            <td><label>性能</label></td>
            <td><input id="repairType-3" type="checkbox"></td>
            <td><label>产品选型</label></td>
            <td><input id="repairType-4" type="checkbox"></td>
            <td><label>高温</label></td>
            <td><input id="repairType-5" type="checkbox"></td>
            <td><label>低温</label></td>
        </tr>
        <tr>
            <td><input id="repairType-6" type="checkbox"></td>
            <td><label>湿热</label></td>
            <td><input id="repairType-7" type="checkbox"></td>
            <td><label>防水</label></td>
            <td><input id="repairType-8" type="checkbox"></td>
            <td><label>振动</label></td>
            <td><input id="repairType-9" type="checkbox"></td>
            <td><label>跌落</label></td>
            <td><input id="repairType-10" type="checkbox"></td>
            <td><label>绝缘电阻</label></td>
        </tr>
        <tr>
            <td><input id="repairType-11" type="checkbox"></td>
            <td><label>抗电强度</label></td>
            <td><input id="repairType-12" type="checkbox"></td>
            <td><label>外观</label>;</td>
            <td><input id="repairType-13" type="checkbox"></td>
            <td><label>外壳防护</label></td>
            <td><input id="repairType-14" type="checkbox"></td>
            <td><label>机械强度</label></td>
            <td><input id="repairType-15" type="checkbox"></td>
            <td><label>静电放电</label></td>
        </tr>
        <tr>
            <td><input id="repairType-16" type="checkbox"></td>
            <td><label>其他(请说明)：</label></td>
            <td colspan="8"><input id="repairType-16text"></td>
        </tr>
    </table>
</div>

<!--选择维修\测试依据界面按钮-->
<div id="repairTypeDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveTypes()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#repairTypeDlg').dialog('close')">取消</a>
</div>

<!--处理界面-->
<div id="handleDlg" class="easyui-dialog" modal="true" style="padding: 10px"
     closed="true" buttons="#handleDlg-button">
    <form id="addHandle"  method="post" style="width: 90%;padding:10px">
        <table>
            <tr>
                <td style="height: 1px;"><input type="hidden" id="idHandle" name="maintenance.id"/></td>
            </tr>
            <tr>
                <td style="width: 70px;"><label>故障核实：</label></td>
                <td class="tdLeft"><label>核实人：</label></td>
                <td class="tdRight"><input id="verifyPerson" class="easyui-combobox" name="maintenance.verifyPersonID"></td>

                <td style="width: 70px;"><label>技术分析：</label></td>
                <td class="tdLeft"><label>分析人：</label></td>
                <td class="tdRight"><input id="analyPerson" class="easyui-combobox" name="maintenance.analyPersonID"></td>
            </tr>
            <tr>
                <td colspan="3">
                    <input id="faultVerify" class="easyui-textbox" name="maintenance.faultVerify"
                           style="width: 100%;height:50px" data-options="multiline:true">
                </td>

                <td colspan="3">
                    <input id="techAnalysis" class="easyui-textbox" name="maintenance.techAnalysis"
                           style="width: 100%;height:50px" data-options="multiline:true">
                </td>
            </tr>
            <tr>
                <td><label>维修内容：</label></td>
                <td class="tdLeft"><label>维修人：</label></td>
                <td class="tdRight"><input id="repairPerson" class="easyui-combobox" name="maintenance.repairPersonID"></td>

                <td><label>维修结果：</label></td>
                <td class="tdLeft"><label>测试人：</label></td>
                <td class="tdRight"><input id="testPerson" class="easyui-combobox" name="maintenance.testPersonID"></td>
            </tr>
            <tr>
                <td colspan="3">
                    <input id="repairContent" class="easyui-textbox" name="maintenance.repairContent"
                           style="width: 100%;height:50px" data-options="multiline:true">
                </td>

                <td colspan="3">
                    <input id="repairResult" class="easyui-textbox" name="maintenance.repairResult"
                           style="width: 100%;height:50px" data-options="multiline:true">
                </td>
            </tr>
            <tr>
                <td><label>检测费：</label></td>
                <td class="tdRight" colspan="2"><input id="checkCost" class="easyui-textbox" name="maintenance.checkCost"
                                                       style="width: 100%;height:50px" data-options="multiline:true"></td>

                <td><label>工时费：</label></td>
                <td class="tdRight" colspan="2"><input id="manhourCost" class="easyui-textbox" name="maintenance.manhourCost"
                                                       style="width: 100%;height:50px" data-options="multiline:true"></td>
            </tr>

            <tr>
                <td><label>材料费：</label></td>
                <td class="tdRight" colspan="2"><input id="materialsCost" class="easyui-textbox" name="maintenance.materialsCost"
                                                       style="width: 100%;height:50px" data-options="multiline:true"></td>

                <td><label>合计：</label></td>
                <td class="tdRight" colspan="2"><input id="repairCost" class="easyui-textbox" name="maintenance.repairCost"
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
                <td class="tdLeft"><label>审批备注：</label></td>
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

<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

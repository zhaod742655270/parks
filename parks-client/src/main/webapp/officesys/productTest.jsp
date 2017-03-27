<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2016/12/28
  Time: 15:53
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
    <script type="text/javascript" src="../resources/script/officesys/productTest.js"></script>

    <style type="text/css">
        #productTestDlg td {
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
    </style>

    <title>测试登记记录</title>
</head>
<body>
<!--测试记录-->
<table id="productTest-dg"></table>

<!--测试记录工具栏-->
<div id="productTest-toolbar">
    <div id="productTest-btn" style="height: 30px">
        <td>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
               onclick="$('#productTest-dg').datagrid('reload')" iconcls="icon-reload">刷新</a>
        </td>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="query-form">
            <table>
                <tr>
                    <td><label>产品名称型号</label><input class="text"  id="productNameQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>编号</label> <input class="text"  id="numberQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>登记人</label><input class="easyui-combobox"  id="registerPersonQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>登记日期</label><input class="easyui-datebox"  id="regDateBegQuery"   style="width: 100px"/>
                        <label>&nbsp;至&nbsp;</label><input class="easyui-datebox"  id="regDateEndQuery"   style="width: 100px"/>&nbsp;</td>
                    <td><label>测试人</label><input class="text"  id="testPeronQuery"   style="width: 110px"/></td>
                    <td>&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="productTestQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!--测试记录修改界面-->
<div id="productTestDlg" class="easyui-dialog" style="padding: 10px"
     modal="true" closed="true" buttons="#productTestDlg-button">
    <form id="addProductTest" method="post">
        <table>
            <tr>
                <td style="height: 5px;"><input type="hidden" id="id" name="productTest.id"/></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>产品名称型号：</label></td>
                <td class="tdRight"><input id="productName" class="easyui-validatebox" name="productTest.productName"
                                           data-options="required:true,validType:['length[0,50]']"></td>

                <td class="tdLeft"><label>编号：</label></td>
                <td class="tdRight"><input id="number" class="easyui-numberbox" name="productTest.number"
                                           data-options="required:true,precision:0"></td>

                <td class="tdLeft"><label>数量：</label></td>
                <td class="tdRight"><input id="quantity" class="easyui-numberbox" name="productTest.quantity"
                                           data-options="precision:0"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>登记人：</label></td>
                <td class="tdRight"><input id="registerPerson" class="easyui-combobox" name="productTest.registerPersonID"></td>

                <td class="tdLeft"><label>登记日期：</label></td>
                <td class="tdRight"><input id="registerDate" class="easyui-datebox" name="productTest.registerDate"></td>

                <td class="tdLeft"><label>要求完成日期：</label></td>
                <td class="tdRight"><input id="hopeEndDate" class="easyui-datebox" name="productTest.hopeEndDate"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>提取位置：</label></td>
                <td class="tdRight" colspan="5"><input id="extractPosition" class="easyui-textbox" style="width: 90%"
                                                       name="productTest.extractPosition"></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>测试依据：</label></td>
                <td style="width:400px" colspan="5">
                    <input id="testBasis" name="productTest.testBasis" style="width:280px">&nbsp;&nbsp;
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectBasis()">选择依据</a>
                </td>
            </tr>
            <tr>
                <td class="tdLeft"><label>测试类别：</label></td>
                <td style="width:400px" colspan="5">
                    <input id="testType" name="productTest.testType" style="width:280px">&nbsp;&nbsp;
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectType()">选择类别</a>
                </td>
            </tr>
            <tr>
                <td class="tdLeft"><label>测试项描述：</label></td>
                <td class="tdRight" colspan="5">
                    <input id="testDesc" class="easyui-textbox" name="productTest.testDesc"
                           style="width: 90%;height:80px" data-options="multiline:true">
                </td>
            </tr>
        </table>
    </form>
</div>

<!--新增修改界面按钮-->
<div id="productTestDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveProductTest()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#productTestDlg').dialog('close')">取消</a>
</div>

<!--测试结果界面-->
<div id="handleDlg" class="easyui-dialog" style="padding:10px" modal="true"
     closed="true" buttons="#handleDlg-button">
    <form id="addHandle">
        <table>
            <tr>
                <td style="height: 5px;"><input type="hidden" id="idHandle" name="productTest.id"/></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>测试人：</label></td>
                <td class="tdRight"><input id="testPerson" class="easyui-combobox" name="productTest.testPersonID"></td>

                <td class="tdLeft"><label>计划开始日期：</label></td>
                <td class="tdRight"><input id="planBegDate" class="easyui-datebox" name="productTest.planBegDate"></td>

                <td class="tdLeft"><label>计划完成日期：</label></td>
                <td class="tdRight"><input id="planEndDate" class="easyui-datebox" name="productTest.planEndDate"></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>实际开始日期：</label></td>
                <td class="tdRight"><input id="begDate" class="easyui-datebox" name="productTest.begDate"></td>

                <td class="tdLeft"><label>实际完成日期：</label></td>
                <td class="tdRight"><input id="endDate" class="easyui-datebox" name="productTest.endDate"></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><label>已解决BUG编号：</label></td>
                <td><input id="finishedBug" class="easyui-textbox" name="productTest.finishedBug"
                                           style="width: 260px;height:50px" data-options="multiline:true"></td>

                <td><label>未解决BUG编号：</label></td>
                <td><input id="unfinishedBug" class="easyui-textbox" name="productTest.unfinishedBug"
                                           style="width: 260px;height:50px" data-options="multiline:true"></td>
            </tr>
            <tr>
                <td><label>最终产品名称型号：</label></td>
                <td><input id="finallyName" class="easyui-textbox" style="width: 260px" name="productTest.finallyName"></td>

                <td><label>最终产品提取位置：</label></td>
                <td><input id="finallyPosition" class="easyui-textbox" style="width: 260px" name="productTest.finallyPosition"></td>
            </tr>
            <tr>
                <td><label>总结：</label></td>
                <td colspan="3"><input id="summany" class="easyui-textbox" name="productTest.summany"
                           style="width: 100%;height:60px" data-options="multiline:true"></td>
            </tr>
            <tr>
                <td><label>备注：</label></td>
                <td colspan="3"><input id="note" class="easyui-textbox" name="productTest.note"
                                       style="width: 100%;height:40px" data-options="multiline:true"></td>
            </tr>
        </table>
    </form>
</div>

<!--测试结果按钮-->
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
                <td style="height: 5px;"><input type="hidden" id="approveId" name="productTest.id"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>审批人：</label></td>
                <td class="tdRight"><input id="approvePerson" class="easyui-combobox" name="productTest.approvePersonID"></td>

                <td class="tdLeft"><label>审批日期：</label></td>
                <td class="tdRight"><input id="approveDate" class="easyui-datebox" name="productTest.approveDate"></td>
            </tr>
            <tr>
                <td class="tdLeft"><label>审批备注：</label></td>
                <td class="tdRight" rowspan="2" colspan="3">
                    <input id="approveNote" class="easyui-textbox" name="productTest.approveNote"
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
                <td style="height: 5px;"><input type="hidden" id="assignId" name="productTest.id"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>指定处理人员：</label></td>
                <td class="tdRight"><input id="assignPerson" class="easyui-combobox" name="productTest.assignPersonId"></td>
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

<!--选择测试依据界面-->
<div id="testBasisDlg" class="easyui-dialog" style="padding:10px"
     closed="true" buttons="#testBasisDlg-button">
    <table>
        <tr>
            <td><input id="testBasis-1" type="checkbox"></td>
            <td><label>说明书</label></td>
            <td><input id="testBasis-2" type="checkbox"></td>
            <td><label>技术要求</label></td>
            <td><input id="testBasis-3" type="checkbox"></td>
            <td><label>测试用例</label></td>
        </tr>
        <tr>
            <td><input id="testBasis-4" type="checkbox"></td>
            <td><label>其他(请说明)：</label></td>
            <td colspan="4"><input id="testBasis-4text"></td>
        </tr>
    </table>
</div>

<!--选择测试依据界面按钮-->
<div id="testBasisDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveBasis()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#testBasisDlg').dialog('close')">取消</a>
</div>

<!--选择测试类型界面-->
<div id="testTypeDlg" class="easyui-dialog" style="padding:10px"
     closed="true" buttons="#testTypeDlg-button">
    <table>
        <tr>
            <td><input id="testType-1" type="checkbox"></td>
            <td><label>功能</label></td>
            <td><input id="testType-2" type="checkbox"></td>
            <td><label>性能</label></td>
            <td><input id="testType-3" type="checkbox"></td>
            <td><label>高温</label></td>
            <td><input id="testType-4" type="checkbox"></td>
            <td><label>低温</label></td>
            <td><input id="testType-5" type="checkbox"></td>
            <td><label>湿热</label></td>
        </tr>
        <tr>
            <td><input id="testType-6" type="checkbox"></td>
            <td><label>振动</label></td>
            <td><input id="testType-7" type="checkbox"></td>
            <td><label>跌落</label></td>
            <td><input id="testType-8" type="checkbox"></td>
            <td><label>防水</label></td>
            <td><input id="testType-9" type="checkbox"></td>
            <td><label>卡片测试</label></td>
            <td><input id="testType-10" type="checkbox"></td>
            <td><label>绝缘电阻</label></td>
        </tr>
        <tr>
            <td><input id="testType-11" type="checkbox"></td>
            <td><label>抗电强度</label></td>
            <td><input id="testType-12" type="checkbox"></td>
            <td><label>外壳防护</label></td>
            <td><input id="testType-13" type="checkbox"></td>
            <td><label>外观</label>;</td>
            <td><input id="testType-14" type="checkbox"></td>
            <td><label>机械强度</label></td>
            <td><input id="testType-15" type="checkbox"></td>
            <td><label>静电放电</label></td>
        </tr>
        <tr>
            <td><input id="testType-16" type="checkbox"></td>
            <td><label>其他(请说明)：</label></td>
            <td colspan="8"><input id="testType-16text"></td>
        </tr>
    </table>
</div>

<!--选择维修\测试依据界面按钮-->
<div id="testTypeDlg-button">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveTypes()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#testTypeDlg').dialog('close')">取消</a>
</div>

<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

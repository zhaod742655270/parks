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
    <title>验收管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/officesys/acceptance.js"></script>
    <style type="text/css">

    #addAcceptance td {
         height: 28px;
         padding-left: 5px;
        }

    #addAcceptance .tdLeft {
        text-align: right;
        width: 90px;
    }

    </style>
</head>
<body>
<table id="contract-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">contractID</th>
        <th field="sheetName">年度</th>
        <th field="projectType">项目类型</th>
        <th field="contractNo">合同号</th>
        <th field="contractName" width="300">项目名称</th>
        <th field="signDate">签订日期</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
    
</table>

<!-- 合同列表的工具栏-->
<div id="contract-toolbar">
    <div style="height:30px;">
        <div id="contract-btns">
            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#contract-dg').datagrid('reload');" iconcls="icon-reload">刷新</a>
            </td>

        </div>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="query-form">
            <table>
                <tr>
                    <td><label>年度</label> &nbsp;<input class="easyui-combobox"  id="sheetNameQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>项目类型</label> &nbsp;<input class="easyui-combobox"  id="projectTypeQuery" name="projectTypeQuery"  style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>合同名称</label>&nbsp;<input type="text" id="conNameQuery" name="conNameQuery" style="width: 150px"/>&nbsp;&nbsp;</td>

                    <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="contractQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>

                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 验收清单右键弹出菜单-->
<div id="rightMenu" class="easyui-menu">
    <div data-options="iconCls:'icon-lightning'" onclick="saveExamine()">审核通过</div>
    <div data-options="iconCls:'icon-remove'" onclick="removeExamine()">取消审核通过</div>
    <div data-options="iconCls:'icon-search'" onclick="openExamineList()">查看审核记录</div>
    <div data-options="iconCls:'icon-help'" onclick="acceptancePostil()">查看批注</div>
    <div data-options="iconCls:'icon-database'" onclick="importSN()">上传序列号表格</div>
    <div data-options="iconCls:'icon-excel'" onclick="viewSN()">查看序列号</div>
</div>

<div id="viewSNDlg" class="easyui-dialog" style="width: 350px; height: 300px; padding: 10px" modal="true"
     closed="true">
    <form id="viewSN-form" method="post">
        <table>
            <tr>
                <td>&nbsp;&nbsp;<label>序列号：</label></td>
                <td><textarea id="viewSN" style="width: 200px;height: 230px"></textarea></td>
            </tr>


        </table>
    </form>
</div>


<!-- 验收清单datagrid-->
<div id="acceptanceDgd" class="easyui-dialog" style="width: 900px; height: 420px; padding: 5px" modal="true"
     closed="true" data-options="resizable:true,maximizable:true,maximized:true">
    <table id="acceptance-dg">
    </table>
</div>

<!-- 验收清单toolbar-->
<div id="acceptance-toolbar">
        <div id="acceptance-btns">
            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="addAcceptance()" iconcls="icon-add">新建</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="editAcceptance()" iconcls="icon-edit">修改</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="deleteAcceptance()" iconcls="icon-remove">删除</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="saveAllRows()" iconcls="icon-ok">保存修改</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="cancelAllRows()" iconcls="icon-cancel">取消修改</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#acceptance-dg').datagrid('reload');"
                   iconcls="icon-reload">刷新</a>
            </td>

        </div>

    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="AcceptanceQuery">
            <table>
                <tr>

                    <td><label>设备名称</label> &nbsp;<input type="text"  id="equipmentNameQuery" name="equipmentNameQuery"  style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>规格型号</label>&nbsp;<input type="text" id="specificationQuery" name="specificationQuery" style="width: 150px"/>&nbsp;&nbsp;</td>

                    <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="acceptanceQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>

                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 增加验收清单的窗口-->
<div id="acceptanceDlg" class="easyui-dialog" style="padding: 10px" modal="true"
     closed="true" buttons="#acceptanceDlg-buttons">
    <form id="addAcceptance" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="acceptanceID"
                                                            type="hidden" name="acceptance.id"/></td>
                <td colspan="1" style="height: 5px;"><input id="contractID"
                                                            type="hidden" name="acceptance.contractID"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>序号：</label></td>
                <td class="tdRight" ><input  id="SN" name="acceptance.SN" class="easyui-textbox" data-options="prompt:'必填...'" style="width: 135px;">&nbsp;&nbsp;&nbsp;</td>

                <td class="tdLeft"><label>采购员：</label></td>
                <td class="tdRight" ><input class="easyui-combobox" id="purchaserID" name="acceptance.purchaserID" style="width: 135px;"/>

                <td class="tdLeft"><label>设备名称：</label></td>
                <td class="tdRight"><input id="equipmentName" name="acceptance.equipmentName" style="width: 130px;"/>

            </tr>

            <tr>

                <td class="tdLeft"><label>品牌：</label></td>
                <td class="tdRight"><input id="brand" name="acceptance.brand" style="width: 130px;"/></td>

                <td class="tdLeft"><label>技术参数：</label></td>
                <td class="tdRight"><input id="technicalParameter" name="acceptance.technicalParameter"  style="width: 130px;"/>


                <td class="tdLeft"><label>规格型号：</label></td>
                <td class="tdRight" colspan="2" rowspan="2"><textarea id="specification" name="acceptance.specification" style="width: 130px;height:60px;"></textarea></td>


           </tr>

            <tr>

                <td class="tdLeft"><label>数量：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="quantity" name="acceptance.quantity" style="width: 135px;" data-options="precision:2"/>

                <td class="tdLeft"><label>单价：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="price" name="acceptance.price" style="width: 135px;" data-options="precision:2"/>

            </tr>

            <tr>
                <td class="tdLeft"><label>合价：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="valence" name="acceptance.valence" style="width: 135px;" data-options="precision:2"/>

                <td class="tdLeft"><label>单位：</label></td>
                <td class="tdRight"><input id="unit" name="acceptance.unit" style="width: 130px;"/></td>

                <td class="tdLeft"><label>预算备注：</label></td>
                <td class="tdRight" colspan="2" rowspan="2"><textarea id="budgetNote" name="acceptance.budgetNote" style="width: 130px; height:60px;"></textarea></td>

            </tr>

            <tr>
                <td class="tdLeft"><label>要求到货时间：</label></td>
                <td class="tdRight"><input class="easyui-datebox" id="requiredArrivalTime" name="acceptance.requiredArrivalTime" style="width: 135px;"/></td>

                <td class="tdLeft"><label>库存：</label></td>
                <td class="tdRight"><input id="inventory" name="acceptance.inventory" style="width: 130px;"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>供应商：</label></td>
                <td class="tdRight"><input id="supplier" name="acceptance.supplier" style="width: 130px;"/></td>

                <td class="tdLeft"><label>采购数量：</label></td>
                <td class="tdRight"><input  class="easyui-numberbox" id="purchaseQuantity" name="acceptance.purchaseQuantity" style="width: 135px;"/></td>

                <td class="tdLeft"><label>测试情况：</label></td>
                <td class="tdRight" colspan="2" rowspan="2"><textarea id="testNote" name="acceptance.testNote" style="width: 130px; height:60px;"></textarea></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>项目经理：</label></td>
                <td class="tdRight"><input class="easyui-combobox" id="projectManagerID" name="acceptance.projectManagerID" style="width: 135px;"/>
            </tr>
                <%--<td class="tdLeft"><label>到货时间：</label></td>--%>
                <%--<td class="tdRight"><input class="easyui-datebox" id="arrivalTime" name="acceptance.arrivalTime" style="width: 135px;"/>--%>

                <%--<td class="tdLeft"><label>到货数量：</label></td>--%>
                <%--<td class="tdRight"><input class="easyui-numberbox" id="arrivalQuantity" name="acceptance.arrivalQuantity" style="width: 135px;"/></td>--%>




            <%--<tr>--%>

                <%--<td class="tdLeft"><label>剩余数量：</label></td>--%>
                <%--<td class="tdRight"><input class="easyui-numberbox" id="remainQuantity" name="acceptance.remainQuantity" style="width: 135px;"/></td>--%>

                <%--<td class="tdLeft"><label>包装：</label></td>--%>
                <%--<td class="tdRight"><input class="easyui-combobox" id="packages" name="acceptance.packages" style="width: 135px;"/>--%>

                <%--<td class="tdLeft"><label>外观：</label></td>--%>
                <%--<td class="tdRight"><input class="easyui-combobox" id="appearance" name="acceptance.appearance" style="width: 135px;"/></td>--%>

            <%--</tr>--%>


            <%--<tr>--%>

                <%--<td class="tdLeft"><label>随机资料/附件：</label></td>--%>
                <%--<td class="tdRight"><input class="easyui-combobox" id="datasheet" name="acceptance.datasheet" style="width: 135px;"/>--%>

                <%--<td class="tdLeft"><label>加电测试：</label></td>--%>
                <%--<td class="tdRight"><input class="easyui-combobox" id="POST" name="acceptance.POST" style="width: 135px;"/>--%>

                <%--<td class="tdLeft"><label>产品序列号：</label></td>--%>
                <%--<td class="tdRight" colspan="2" rowspan="2"><textarea  id="productID" name="acceptance.productID" style="width: 130px; height:60px;"></textarea></td>--%>

            <%--</tr>--%>

            <%--<tr>--%>
                <%--<td class="tdLeft"><label>设备运行情况：</label></td>--%>
                <%--<td class="tdRight"><input  id="operation" name="acceptance.operation" style="width: 130px;"/></td>--%>

                <%--<td class="tdLeft"><label>到货备注：</label></td>--%>
                <%--<td class="tdRight"><input id="arrivalNote" name="acceptance.arrivalNote" style="width: 130px;"/>--%>



                <%--<%--<td class="tdLeft"></td>--%>
                <%--<%--<td></td>--%>
                <%--<%--<td class="tdLeft"><a id="btn" href="#" class="easyui-linkbutton"  style="width:90px" data-options="iconCls:'icon-lightning'" onclick="importSN()">上传序列号</a></td>--%>

            <%--</tr>--%>

        </table>
    </form>
</div>


<div id="importSN-dlg" class="easyui-dialog" style="width: 340px; height: 120px; padding: 10px" modal="true"
     closed="true">
    <form id="importSN"  method="post" enctype="multipart/form-data">
        <td colspan="1" style="height: 5px;"><input id="SNID" type="hidden" name="id"></td>
        <input id="SNfile"  name="file" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">&nbsp;

        <a href="#" class="easyui-linkbutton" style="width:80px" onclick="uploadSN()" >导入数据库</a>
        　　　　　
    </form>

</div>
<!-- 增加验收清单窗口的保存，取消按键-->
<div id="acceptanceDlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" id="savePayment" onclick="saveAcceptance()">保存</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#acceptanceDlg').dialog('close')">取消</a>
</div>



<!-- 增加 修改付款合同批注窗口-->
<div id="acceptancePostil-dlg" class="easyui-dialog" style="width: 635px; height: 170px; padding: 10px" modal="true"
     closed="true" buttons="#acceptancePostil-dlg-buttons">
    <form id="addAcceptancePostil-form" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="acceptancePostilID"
                                                            type="hidden" name="acceptance.acceptancePostilDTO.id"/></td>
                <td colspan="1" style="height: 5px;"><input id="parentID" type="hidden" name="acceptance.acceptancePostilDTO.parentID"></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;<label>品牌批注：</label></td>
                <td><textarea id="brandPostil" name="acceptance.acceptancePostilDTO.brandPostil"
                              style="width: 175px;"></textarea></td>

                <td>&nbsp;&nbsp;<label>规格型号批注：</label></td>
                <td><textarea id="specificationPostil" name="acceptance.acceptancePostilDTO.specificationPostil"
                              style="width: 175px;"></textarea></td>
            </tr>


        </table>
    </form>
</div>

<!-- 验收清单批注保存、取消功能按钮-->
<div id="acceptancePostil-dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveAcceptancePostil()">保存</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#acceptancePostil-dlg').dialog('close')">取消</a>
</div>


<div id="importExcel-dlg" class="easyui-dialog" style="width: 440px; height: 120px; padding: 10px" modal="true"
     closed="true">
        <form id="importExcel"  method="post" enctype="multipart/form-data">
           <input id="file"  name="file" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">&nbsp;

          <a href="#" class="easyui-linkbutton" style="width:80px" onclick="uploadExcel()" >导入数据库</a>&nbsp;&nbsp;&nbsp;

          <a href="#" class="easyui-linkbutton" style="width:80px" onclick="viewSample()" >查看样例</a>　　     　　　　　

        </form>

</div>

<div id="sampleDlg" class="easyui-dialog" style="width: 960px; height: 340px; padding: 5px" modal="true"
     closed="true">
    <table id="sample-tb">
        <thead>
            <th field="SN" width="30">序号</th>
            <th field="equipmentName" width="30">设备名称</th>
            <th field="brand" width="30">品牌</th>
            <th field="specification" width="30">规格型号</th>
            <th field="technicalParameter" width="30">技术参数</th>
            <th field="unit" width="30">单位</th>
            <th field="quantity" width="30">数量</th>
            <th field="price" width="30">单价</th>
            <th field="valence" width="30">合价</th>
            <th field="requiredArrivalTime"  width="40">要求到货时间</th>
            <th field="inventory" width="30">库存</th>
            <th field="testNote" width="30">测试情况</th>
            <th field="budgetNote" width="30">预算备注</th>

            <!---项目经理选项--------->
            <th field="supplier" width="30">供应商</th>
            <th field="purchaseQuantity" width="30">采购数量</th>
            <th field="purchaserName" width="30">采购员</th>
            <th field="projectManagerName" width="30">项目经理</th>
            <th field="arrivalTime" width="30">到货时间</th>
            <th field="arrivalQuantity" width="30">到货数量</th>
            <th field="remainQuantity" width="30">剩余数量</th>
            <th field="direction" width="30">去向</th>
            <th field="packagesName" width="30">包装</th>
            <th field="appearanceName" width="30">外观</th>
            <th field="datasheetName" width="45">随机资料/附件</th>
            <th field="productID" width="40">产品序列号</th>
            <th field="POSTName" width="30">加电测试</th>
            <th field="operation" width="40">设备运行情况</th>
            <th field="arrivalNote" width="30">到货备注</th>
            <th field="runnedTime" width="30">设备运行时间</th>
        </thead>
    </table>
</div>

<!--审核记录-->
<div id="examineDlg" class="easyui-dialog" style="width: 600px; height: 340px; padding: 5px" modal="true"
     closed="true">
    <table id="examine-dg"></table>
</div>

<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

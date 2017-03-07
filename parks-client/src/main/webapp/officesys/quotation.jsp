<%--
  Created by IntelliJ IDEA.
  User: ZhaoDa
  Date: 2016/10/27
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>报价管理</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script type="text/javascript" src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../resources/easyui/js/easyui-extentions.js"></script>
    <script type="text/javascript" src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/officesys/quotation.js"></script>
    <style type="text/css">

    #addProject td {
               height: 35px;
               padding-left: 5px;
           }

    #addProject .tdLeft {
        text-align: right;
        width: 90px;
    }

    #addQuotation td {
        height: 32px;
        padding-left: 8px;
    }
    </style>
</head>
<body>
<table id="project-dg">
    <thead>
    <tr>
        <th data-options="field:'id',hidden:true">projectID</th>
        <th field="sheetName">年度</th>
        <th field="projectName">项目名称</th>
        <th field="projectType">项目类型</th>
    </tr>
    </thead>
</table>

<!-- 项目列表的工具栏-->
<div id="project-toolbar">
    <div style="height:30px;">
        <div id="project-btns">
            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#project-dg').datagrid('reload');" iconcls="icon-reload">刷新</a>
            </td>

        </div>
    </div>
    <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
    <div>
        <form id="query-form">
            <table>
                <tr>
                    <td><label>年度</label> &nbsp;<input class="easyui-combobox"  id="sheetNameQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td><label>项目名称</label>&nbsp;<input type="text" id="projectNameQuery" name="projectNameQuery" style="width: 150px"/>&nbsp;&nbsp;</td>

                    <td><label>项目类型</label>&nbsp;<input class="easyui-combobox" id="projectTypeQuery" name="projectTypeQuery" style="width: 110px"/>&nbsp;&nbsp;</td>

                    <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="projectQuery()"
                                             iconcls="icon-search" plain="false">查询</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>


<!--新增、修改、删除项目-->
<div id="projectDlg" class="easyui-dialog" style="padding: 10px" modal="true"
     closed="true" buttons="#projectDlg-buttons">
    <form id="addProject" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="projectID"
                                                            type="hidden" name="project.id"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>年度：</label></td>
                <td class="tdRight" ><input  id="sheetName" name="project.sheetName"  class="easyui-combobox" style="width: 130px;"></td>

                <td class="tdLeft"><label>项目名称：</label></td>
                <td class="tdRight" ><input id="projectName" name="project.projectName" class="easyui-validatebox" style="width: 135px;"
                                            data-options="required:true,validType:'nameValid'"/>

                <td class="tdLeft"><label>项目类型：</label></td>
                <td class="tdRight" ><input id="projectType" name="project.projectType" class="easyui-combobox" style="width: 130px;"
                                            data-options="required:true"/>
            </tr>
            </table>
        </form>
    </div>

<div id="projectDlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" id="saveProject" onclick="saveProject()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#projectDlg').dialog('close')">取消</a>
</div>




<!-- 报价清单datagrid-->
<div id="quotationDgd" class="easyui-dialog" style="width: 900px; height: 420px" modal="true"
     closed="true" data-options="maximizable:true,maximized:true">
    <table id="quotation-dg">
        <thead data-options="frozen:true">
            <tr>
                <th data-options="field:'id',hidden:true">quotationID</th>
                <th data-options="field:'projectIpFK',hidden:true">parentProjectID</th>
                <th field="name" width="90">名称</th>
            </tr>
        </thead>
        <thead>
             <tr>
                <th field="specification">规格型号</th>
                <th field="technicalParameter">技术参数</th>
                <th field="brand">品牌</th>
                <th field="quantity">数量</th>
                <th field="unit">单位</th>
                <th field="price">单价（元）</th>
                <th field="valence">合价（元）</th>
                <th field="ratio">系数</th>

                 <!---设计人员选项--------->
                <th field="costPrice" sortable="true">成本单价</th>
                <th field="costValence">成本合价</th>
                 <th data-options="field:'freight',hidden:true">运费(bool)</th>
                <th field="freightText">运费</th>
                <th field="invoice">发票类型</th>
                <th field="warranty">质保期</th>
                 <th data-options="field:'debugging',hidden:true">厂家负责安装调试(bool)</th>
                <th field="debuggingText">厂家负责安装调试</th>
                <th field="validityPeriod">报价有效期</th>
                <th field="supplyCycle">供货周期</th>
                <th field="payment">付款方式</th>
                <th field="note">备注</th>

            </tr>
        </thead>
    </table>
</div>

<!-- 报价清单toolbar-->
<div id="quotation-toolbar">
    <div id="quotation-btns">
            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="addQuotation()" iconcls="icon-add">新建清单</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="editQuotation()" iconcls="icon-edit">修改清单</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="deleteQuotation()" iconcls="icon-remove">删除清单</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="loadToExcel()" iconcls="icon-excel">导出清单</a>
            </td>

            <td><a herf="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#quotation-dg').datagrid('reload');"
                   iconcls="icon-reload">刷新</a>
            </td>
    </div>
    <div>
        <form id="exportForm">
            <table>
                <tr style="padding:10px 10px">
                    <td><label>设备名称</label>&nbsp;<input class="easyui-textbox" id="nameQuery" style="width: 130px"/>&nbsp;&nbsp;</td>

                    <td><label>型号规格</label>&nbsp;<input class="easyui-textbox" id="specificationQuery" style="width: 130px"/>&nbsp;&nbsp;</td>

                    <td><label>品牌名称</label>&nbsp;<input class="easyui-textbox" id="brandQuery" style="width: 130px"/>&nbsp;&nbsp;</td>

                    <td rowspan="2"><a class="easyui-linkbutton" onclick="quotationQuery()" iconcls="icon-search" plain="false">查询</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 增加验收清单的窗口-->
<div id="quotationDlg" class="easyui-dialog" style="width: 710px; height: 370px" modal="true"
     closed="true" buttons="#quotationDlg-buttons">
    <form id="addQuotation" method="post">
        <table>
            <tr>
                <td colspan="1" style="height: 5px;"><input id="quotationID"
                                                            type="hidden" name="quotation.id"/></td>
                <td colspan="1" style="height: 5px;"><input id="projectIdFK"
                                                            type="hidden" name="quotation.projectIdFK"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>名称：</label></td>
                <td class="tdRight" ><input class="easyui-validatebox" id="name" name="quotation.name" style="width: 130px;"
                                            data-options="required:true,validType:'nameValid'"/>&nbsp;</td>

                <td class="tdLeft"><label>品牌：</label></td>
                <td class="tdRight"><input class="easyui-validatebox" id="brand" name="quotation.brand" style="width: 130px;"
                                           data-options="required:true,validType:'nameValid'"/>&nbsp;</td>

                <td class="tdLeft"><label>付款方式：</label></td>
                <td class="tdRight"><input class="easyui-textbox" id="payment" name="quotation.payment" style="width: 130px;"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>数量：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="quantity" name="quotation.quantity" style="width: 130px;"
                                            data-options="required:true,precision:2,onChange:function(){getValence();getInventory();}"/>&nbsp;</td>

                <td class="tdLeft"><label>单位：</label></td>
                <td class="tdRight"><input class="easyui-validatebox" id="unit" name="quotation.unit" style="width: 130px;"
                                           data-options="required:true,validType:'unitValid'"/>&nbsp;</td>

                <td class="tdLeft"><label>系数：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="ratio" name="quotation.ratio" style="width: 130px;"
                                           data-options="required:true,precision:2,onChange:getPrice"/></td>
           </tr>

            <tr>

                <td class="tdLeft"><label>单价(元)：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="price" name="quotation.price" style="width: 130px;"
                                           data-options="required:true,precision:2,onChange:getValence"/>&nbsp;</td>

                <td class="tdLeft"><label>合价(元)：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="valence" name="quotation.valence" style="width: 130px;"
                                            data-options="required:true,precision:2"/>&nbsp;</td>

                <td class="tdLeft"><label>型号规格：</label></td>
                <td class="tdRight" rowspan="2"><input class="easyui-textbox" id="specification" name="quotation.specification" style="width: 130px;height:55px"
                                                       data-options="multiline:true"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>成本单价：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="costPrice" name="quotation.costPrice" style="width: 130px;"
                                           data-options="required:true,precision:2,onChange:function(){getPrice();getInventory();}"/>&nbsp;</td>

                <td class="tdLeft"><label>成本合价：</label></td>
                <td class="tdRight"><input class="easyui-numberbox" id="inventory" name="quotation.costValence"style="width: 130px;"
                                           data-options="required:true,precision:2"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>运费：</label></td>
                <td class="tdRight"><input class="easyui-combobox" id="freight" name="quotation.freight" style="width: 130px;"
                                           data-options="required:true,editable:false"/>&nbsp;</td>

                <td class="tdLeft"><label>发票类型：</label></td>
                <td class="tdRight"><input class="easyui-combobox" id="invoice" name="quotation.invoice" style="width: 130px;"
                                           data-options="required:true,editable:false"/>&nbsp;</td>

                <td class="tdLeft"><label>技术参数：</label></td>
                <td class="tdRight" rowspan="2"><input class="easyui-textbox" id="technicalParameter" name="quotation.technicalParameter" style="width: 130px;height:55px"
                                                       data-options="multiline:true"/></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>质保期：</label></td>
                <td class="tdRight"><input class="easyui-combobox" id="warranty" name="quotation.warranty" style="width: 130px;"
                                           data-options="required:true,editable:false"/>&nbsp;</td>

                <td class="tdLeft"><label>厂家调试：</label></td>
                <td class="tdRight"><input class="easyui-combobox" id="debugging" name="quotation.debugging" style="width: 130px;"
                                           data-options="required:true,editable:false"/></td>
            </tr>

            <tr>


                <td class="tdLeft"><label>报价有效期：</label></td>
                <td class="tdRight"><input class="easyui-combobox" id="validityPeriod" name="quotation.validityPeriod" style="width: 130px;"
                                           data-options="required:true,editable:false"/>&nbsp;</td>

                <td class="tdLeft"><label>供货周期：</label></td>
                <td class="tdRight"><input class="easyui-textbox" id="supplyCycle" name="quotation.supplyCycle" style="width: 130px;"/>&nbsp;</td>

                <td class="tdLeft"><label>备注：</label></td>
                <td class="tdRight" rowspan="2"><input class="easyui-textbox" id="note" name="quotation.note" style="width: 130px;height:45px"
                                                       data-options="multiline:true"/></td>
            </tr>
            <tr></tr>
        </table>
    </form>
</div>

<!-- 增加验收清单窗口的保存，取消按键-->
<div id="quotationDlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" id="savePayment" onclick="saveQuotation()">保存</a>

    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#quotationDlg').dialog('close')">取消</a>
</div>

<!--导入Excel文件选择界面-->
<div id="importExcel-dlg" class="easyui-dialog" style="width: 400px; height: 130px; padding: 25px" modal="true"
     closed="true">
    <form id="importExcel"  method="post" enctype="multipart/form-data">
        <input id="file"  name="file" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">&nbsp;&nbsp;

        <a href="#" class="easyui-linkbutton" style="width:80px" onclick="importExcel()" >导入数据库</a>　     　　　　　

    </form>

</div>

<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

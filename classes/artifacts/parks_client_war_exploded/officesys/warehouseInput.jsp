<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2016/12/27
  Time: 8:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
    <script type="text/javascript" src="../resources/script/officesys/warehouseInput.js"></script>
    <style type="text/css">
            #warehouseDlg td {
                height: 30px;
                padding-left: 5px;
            }

            #productDlg td {
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
    <title>入库管理</title>
</head>
<body>
    <!--入库单列表-->
    <table id="warehouse-dg"></table>

    <!--入库单工具栏-->
    <div id="warehouse-toolbar">
        <div id="warehouse-btn" style="height: 30px">
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#warehouse-dg').datagrid('reload')" iconcls="icon-reload">刷新</a>
            </td>
        </div>
        <div style="width:100%;height: 1px;border-bottom: 1px groove #effffc"></div>
        <div style="height: 30px">
            <form id="query-form">
                <table>
                    <tr>
                        <td><label>入库单号</label> &nbsp;<input type="text"  id="numberQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>
                        <td><label>入库类型</label> &nbsp;<input class="easyui-combobox"  id="inputTypeQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>
                        <td colspan="2"><label>入库日期</label> &nbsp;<input class="easyui-datebox"  id="inputDateBegQuery"   style="width: 110px"/>
                            <label>至</label> <input class="easyui-datebox"  id="inputDateEndQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>
                        <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="warehouseQuery()"
                                                 iconcls="icon-search" plain="false">查询</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <!--入库单新增修改界面-->
    <div id="warehouseDlg" class="easyui-dialog" style="padding: 10px" modal="true"
         closed="true" buttons="#warehouseDlg-button">
        <form id="addWarehouse" method="post">
            <table>
                <tr>
                    <td style="height: 5px;"><input type="hidden" id="id" name="warehouseInput.id"/></td>
                    <td style="height: 5px;"><input type="hidden" id="jsonAllRows" name="jsonAllRows"/></td>
                </tr>

                <tr>
                    <td class="tdLeft"><label>入库单号：</label></td>
                    <td class="tdRight"><input id="number" class="easyui-numberbox" name="warehouseInput.number"
                                               data-options="required:true,precision:0"></td>

                    <td class="tdLeft"><label>入库日期：</label></td>
                    <td class="tdRight"><input id="inputDate" class="easyui-datebox" name="warehouseInput.inputDate"></td>

                    <td class="tdLeft"><label>类型：</label></td>
                    <td class="tdRight"><input id="inputType" class="easyui-combobox" name="warehouseInput.inputType"
                                               data-options="editable:false"/></td>
                </tr>

                <tr>
                    <td class="tdLeft"><label>仓库：</label></td>
                    <td class="tdRight"><input id="warehouse" class="easyui-combobox" name="warehouseInput.warehouseID"
                                               data-options="editable:false"/></td>

                    <td class="tdLeft"><label>供应方：</label></td>
                    <td class="tdRight"><input id="company" class="easyui-combobox" name="warehouseInput.companyId"></td>

                    <td class="tdLeft"><label>申请单：</label></td>
                    <td class="tdRight"><input id="application" class="easyui-combobox" name="warehouseInput.applicationID"
                                               data-options="editable:false"></td>
                </tr>

                <tr>
                    <td class="tdLeft"><label>录入人：</label></td>
                    <td class="tdRight"><input id="recordPerson" class="easyui-combobox" name="warehouseInput.recordPersonID"
                                               data-options="editable:false"></td>

                    <td class="tdLeft"><label>录入日期：</label></td>
                    <td class="tdRight"><input id="recordDate" class="easyui-datebox" name="warehouseInput.recordDate"></td>
                </tr>

                <tr>
                    <td class="tdLeft"><label>备注：</label></td>
                    <td rowspan="2" colspan="5"><input id="note" class="easyui-textbox" name="warehouseInput.note"
                                                           data-options="multiline:true" style="width:100%;height:40px"></td>
                </tr>
            </table>
        </form>
        <div id="application-div" style="height: 200px">
            <table id="application-dg"></table>
        </div>
    </div>

    <!--入库单新增修改界面按钮-->
    <div id="warehouseDlg-button">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveWarehouseInput()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#warehouseDlg').dialog('close')">取消</a>
    </div>

    <!--入库货品列表-->
    <div id="productDgd" class="easyui-dialog" style="width: 900px; height: 420px" modal="true" closed="true">
        <table id="product-dg"></table>
    </div>

    <!--入库货品列表工具栏-->
    <div id="product-toolbar">
        <div style="height: 30px">
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="selectProduct()" iconcls="icon-add">添加货品</a>

                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="saveProduct()" iconcls="icon-add">保存全部信息</a>

                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="deleteProduct()" iconcls="icon-remove">删除货品</a>

                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"
                   onclick="$('#product-dg').datagrid('reload')" iconcls="icon-reload">刷新</a>
            </td>
        </div>
        <div style="width:100%;height:1px;border-bottom:1px groove #effffc;"></div>
        <div style="height: 30px">
            <form id="queryProduct-form">
                <table>
                    <tr>
                        <td><label>简称</label> &nbsp;<input id="nameQuery"   style="width: 110px"/>&nbsp;</td>
                        <td><label>型号</label> &nbsp;<input id="modelNumberQuery"   style="width: 110px"/>&nbsp;</td>
                        <td><label>规格</label> &nbsp;<input id="specificationsQuery"   style="width: 110px"/>&nbsp;</td>
                        <td>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="productQuery()"
                                                 iconcls="icon-search" plain="false">查询</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <!--货品选择列表-->
    <div id="productSelectDgd" class="easyui-dialog" style="width: 900px; height: 420px" modal="true" closed="true">
        <table id="productSelect-dg"></table>
    </div>

    <!--货品选择列表工具栏-->
    <div id="productSelect-toolbar">
        <div style="height: 30px">
            <form id="queryProductSelect-form">
                <table>
                    <tr>
                        <td><label>名称</label> &nbsp;<input id="nameSelectQuery"   style="width: 110px"/>&nbsp;</td>
                        <td><label>类型</label> &nbsp;<input id="productTypeQuery" class="easyui-combobox" style="width: 110px"/>&nbsp;</td>
                        <td><label>品牌</label> &nbsp;<input id="brandQuery"  class="easyui-combobox" style="width: 110px"/>&nbsp;</td>
                        <td>&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="productSelectQuery()"
                                     iconcls="icon-search" plain="false">查询</a></td>
                        <td>&nbsp;&nbsp;&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="productAdd()"
                                     iconcls="icon-ok" plain="false">选择</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <!--审批界面-->
    <div id="approveDlg" class="easyui-dialog" style="padding: 10px" modal="true" closed="true" buttons="#approveDlg-button">
        <form id="addApprove">
            <table>
                <tr>
                    <td style="height: 5px;"><input type="hidden" id="approveId" name="warehouseInput.id"/></td>
                </tr>

                <tr>
                    <td class="tdLeft"><label>批准人：</label></td>
                    <td class="tdRight"><input id="examinePerson" class="easyui-combobox" name="warehouseInput.examinePersonID"></td>

                    <td class="tdLeft"><label>批准日期：</label></td>
                    <td class="tdRight"><input id="examineDate" class="easyui-datebox" name="warehouseInput.examineDate"></td>
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

    <input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

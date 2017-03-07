<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2016/1/17
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
    <script type="text/javascript" src="../resources/script/officesys/warehouseBorrow.js"></script>
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

            input{
                width:130px
            }
        </style>
    <title>借用管理</title>
</head>
<body>
    <!--借用列表-->
    <table id="warehouse-dg"></table>

    <!--借用工具栏-->
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
                        <td><label>借用人</label><input id="borrowPersonQuery" class="easyui-combobox" style="width: 110px"></td>
                        <td><label>状态</label><input id="stateQuery" class="easyui-combobox" style="width: 110px;"></td>
                        <td colspan="2"><label>入库日期</label><input class="easyui-datebox"  id="borrowDateBegQuery"   style="width: 110px"/>
                            <label>至</label> <input class="easyui-datebox"  id="borrowDateEndQuery"   style="width: 110px"/></td>
                        <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="warehouseBorrowQuery()"
                                                 iconcls="icon-search" plain="false">查询</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <!--借用新增修改界面-->
    <div id="warehouseDlg" class="easyui-dialog" style="padding: 10px" modal="true"
         closed="true" buttons="#warehouseDlg-button">
        <form id="addWarehouse" method="post">
            <table>
                <tr>
                    <td style="height: 5px;"><input type="hidden" id="id" name="warehouseBorrow.id"/></td>
                </tr>
                <tr>
                    <td class="tdLeft"><label>借用编号：</label></td>
                    <td class="tdRight"><input id="number" class="easyui-numberbox" name="warehouseBorrow.number"
                                               data-options="required:true,precision:0"></td>
                    <td class="tdLeft"><label>借用人：</label></td>
                    <td class="tdRight"><input id="borrowPerson" class="easyui-combobox" name="warehouseBorrow.borrowPersonId"
                                               data-options="editable:false"></td>
                    <td class="tdLeft"><label>借用日期：</label></td>
                    <td class="tdRight"><input id="borrowDate" class="easyui-datebox" name="warehouseBorrow.borrowDate"></td>
                </tr>
                <tr>
                    <td class="tdLeft"><label>借用物品：</label></td>
                    <td class="tdRight"><input id="warehouseProduct" class="easyui-combobox" name="warehouseBorrow.productId"
                                               data-options="onSelect:onSelectProduct"></td>
                    <td class="tdLeft"><label>物品型号：</label></td>
                    <td class="tdRight"><input id="productModelNumber"></td>
                    <td class="tdLeft"><label>物品封装：</label></td>
                    <td class="tdRight"><input id="productSpecifications"></td>
                </tr>
                <tr>
                    <td class="tdLeft"><label>借用数量：</label></td>
                    <td class="tdRight"><input id="quantity" class="easyui-numberbox" name="warehouseBorrow.quantity"
                                               data-options="required:true,precision:2"></td>
                    <td class="tdLeft"><label>单位：</label></td>
                    <td class="tdRight"><input id="productUnit"></td>
                </tr>
                <tr>
                    <td class="tdLeft"><label>备注：</label></td>
                    <td rowspan="2" colspan="5"><input id="note" class="easyui-textbox" name="warehouseBorrow.note"
                                                       data-options="multiline:true" style="width:100%;height:40px"></td>
                </tr>
            </table>
        </form>
    </div>

    <!--借用新增修改界面按钮-->
    <div id="warehouseDlg-button">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveWarehouseBorrow()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#warehouseDlg').dialog('close')">取消</a>
    </div>

    <input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

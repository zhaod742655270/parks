<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2016/12/30
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/officesys/warehouseProduct.js"></script>
    <style type="text/css">
        #product-dlg td {
            height: 30px;
            padding-left: 5px;
        }

        .tdLeft {
            text-align: right;
            width: 80px;
        }

        .tdRight{
            width: 100px;
        }

        label{
            font-size:12px;
        }
    </style>
    <title>货品信息管理</title>
</head>
<body>
    <!--货品信息列表-->
    <table id="product-dg"></table>

    <!--货品信息列表工具栏-->
    <div id="product-toolbar">
        <div id="product-btn">
            <td>
                <a href="javascipt:void(0)" class="easyui-linkbutton" plain="true" iconcls="icon-reload"
                   onclick="$('#product-dg').datagrid('reload')">刷新</a>
            </td>
        </div>
        <div style="width: 100%;height: 1px;border-bottom: 1px groove #effffc"></div>
        <div style="height: 30px">
            <form id="query-form">
                <table>
                    <tr>
                        <td><label>名称</label> &nbsp;<input id="nameQuery"   style="width: 110px"/>&nbsp;</td>
                        <td><label>类型</label> &nbsp;<input id="productTypeQuery" class="easyui-combobox" style="width: 110px"/>&nbsp;</td>
                        <td><label>品牌</label> &nbsp;<input id="brandQuery" style="width: 110px"/>&nbsp;</td>
                        <td>&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="productQuery()"
                                     iconcls="icon-search" plain="false">查询</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <!--添加修改界面-->
    <div id="product-dlg" class="easyui-dialog" style="padding: 10px" model="true"
         closed="true" buttons="#productDlg-btn">
        <form id="addProduct" method="post">
            <table>
                <tr>
                    <td style="height: 5px;"><input type="hidden" id="id" name="warehouseProduct.id"></td>
                </tr>

                <tr>
                    <td class="tdLeft"><label>名称：</label></td>
                    <td class="tdRight"><input id="name" class="easyui-validatebox" name="warehouseProduct.name"
                                               data-options="validType:['length[0,20]'],required:true"></td>

                    <td class="tdLeft"><label>型号：</label></td>
                    <td class="tdRight"><input id="modelNumber" name="warehouseProduct.modelNumber"></td>

                    <td class="tdLeft"><label>封装：</label></td>
                    <td class="tdRight"><input id="specifications" name="warehouseProduct.specifications"></td>
                </tr>

                <tr>
                    <td class="tdLeft"><label>分类：</label></td>
                    <td class="tdRight"><input id="productType" class="easyui-combobox" name="warehouseProduct.productType"></td>

                    <td class="tdLeft"><label>品牌：</label></td>
                    <td class="tdRight"><input id="brand" name="warehouseProduct.brand"></td>

                    <td class="tdLeft"><label>单位：</label></td>
                    <td class="tdRight"><input id="unit" name="warehouseProduct.unit"></td>
                </tr>

                <tr>
                    <td class="tdLeft"><label>描述：</label></td>
                    <td colspan="5" rowspan="2"><input id="productDesc" class="easyui-textbox" name="warehouseProduct.productDesc"
                                                       data-options="multiline:true" style="height: 50px;width:100%"></td>
                </tr>
            </table>
        </form>
    </div>

    <!--添加修改界面按钮-->
    <div id="productDlg-btn">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveProduct()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#product-dlg').dialog('close')">取消</a>
    </div>

    <input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

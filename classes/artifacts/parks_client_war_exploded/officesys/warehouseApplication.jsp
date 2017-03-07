<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2017/1/23
  Time: 8:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
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
    <script type="text/javascript" src="../resources/script/officesys/warehouseApplication.js"></script>
    <title>申请单信息管理</title>
</head>
<body>
    <!--申请单列表-->
    <table id="warehouse-dg"></table>

    <!--申请单工具栏-->
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
                        <td><label>申请单名称</label> &nbsp;<input type="text"  id="nameQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>
                        <td><label>类型</label> &nbsp;<input id="typeQuery" class="easyui-combobox" style="width: 110px"/>&nbsp;</td>
                        <td colspan="2"><label>录入日期</label> &nbsp;<input class="easyui-datebox"  id="recordDateBegQuery"   style="width: 110px"/>
                            <label>至</label> <input class="easyui-datebox"  id="recordDateEndQuery"   style="width: 110px"/>&nbsp;&nbsp;</td>
                        <td rowspan="2">&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="warehouseQuery()"
                                                 iconcls="icon-search" plain="false">查询</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <!--申请单修改界面-->
    <div id="warehouseDlg" class="easyui-dialog" style="padding: 10px;" model="true" closed="true" buttons="#warehouseBtn">
        <form id="addWarehouse" method="post">
            <table>
                <tr>
                    <td><input id="id" type="hidden" name="warehouseApplication.id"></td>
                </tr>
                <tr>
                    <td class="tdLeft"><label>申请单号：</label></td>
                    <td class="tdRight"><input id="number" class="easyui-numberbox" name="warehouseApplication.number"
                                               data-options="required:true,precision:0"></td>

                    <td class="tdLeft"><label>申请单名称：</label></td>
                    <td class="tdRight"><input id="name" class="easyui-textbox" name="warehouseApplication.name"
                                               data-options="required:true,precision:0"></td>

                    <td class="tdLeft"><label>类型：</label></td>
                    <td class="tdRight"><input id="type" class="easyui-combobox" name="warehouseApplication.type"
                                               data-options="editable:false"></td>
                </tr>
                <tr>
                    <td class="tdLeft"><label>备注：</label></td>
                    <td rowspan="2" colspan="5"><input id="note" class="easyui-textbox" name="warehouseApplication.note"
                                                       data-options="multiline:true" style="width:100%;height:40px"></td>
                </tr>
            </table>
        </form>
    </div>

    <!--申请单修改界面按钮-->
    <div id="warehouseBtn">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveWarehouseApplication()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#warehouseDlg').dialog('close')">取消</a>
    </div>

    <!--入库货品列表-->
    <div id="productDgd" class="easyui-dialog" style="width: 900px; height: 420px" modal="true" closed="true">
        <table id="product-dg"></table>
    </div>

    <!--导入Excel文件选择界面-->
    <div id="importExcel-dlg" class="easyui-dialog" style="width: 400px; height: 130px; padding: 25px" modal="true"
         closed="true">
        <form id="importExcel"  method="post" enctype="multipart/form-data">
            <input id="recordPerson" type="hidden" name="recordPersonID">
            <input id="recordDate" type="hidden" name="recordDate">
            <input id="file"  name="file" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">&nbsp;&nbsp;
            <a href="#" class="easyui-linkbutton" style="width:80px" onclick="importExcel()" >导入数据库</a>　     　　　　　
        </form>
    </div>

    <input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

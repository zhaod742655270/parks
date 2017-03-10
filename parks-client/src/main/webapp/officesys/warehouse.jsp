<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2016/12/30
  Time: 14:28
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
    <script type="text/javascript" src="../resources/script/officesys/warehouse.js"></script>

    <style type="text/css">
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

        input{
            width:130px
        }
    </style>
    <title>库存统计</title>
</head>
<body>
    <!--库存信息列表-->
    <table id="warehouse-dg"></table>

    <!--库存信息列表工具栏-->
    <div id="warehouse-toolbar">
        <div style="height: 30px">
            <form id="query-form">
                <table>
                    <tr>
                        <td><label>名称</label> &nbsp;<input id="nameQuery"   style="width: 110px"/>&nbsp;</td>
                        <td><label>类型</label> &nbsp;<input id="typeQuery" class="easyui-combobox" style="width: 110px"/>&nbsp;</td>
                        <td><label>品牌</label> &nbsp;<input id="brandQuery"  style="width: 110px"/>&nbsp;</td>
                        <td>&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="warehouseQuery()"
                                     iconcls="icon-search" plain="false">查询</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Zhao_d
  Date: 2016/12/30
  Time: 11:24
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
    <script type="text/javascript" src="../resources/script/officesys/warehouseCompanyOut.js"></script>

    <style type="text/css">
        #company-dlg td {
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

        input{
            width:130px
        }
    </style>

    <title>客户信息</title>
</head>
<body>
<!--往来对象信息列表-->
<table id="company-dg"></table>

<!--往来对象信息列表工具栏-->
<div id="company-toolbar">
    <div id="company-btn">
        <td>
            <a href="javascipt:void(0)" class="easyui-linkbutton" plain="true" iconcls="icon-reload"
               onclick="$('#company-dg').datagrid('reload')">刷新</a>
        </td>
    </div>
    <div style="width: 100%;height: 1px;border-bottom: 1px groove #effffc"></div>
    <div style="height: 30px">
        <form id="query-form">
            <table>
                <tr>
                    <td><label>名称</label> &nbsp;<input id="nameQuery"   style="width: 110px"/>&nbsp;</td>
                    <td><label>类型</label> &nbsp;<input id="typeQuery" class="easyui-combobox" style="width: 110px"/>&nbsp;</td>
                    <td>&nbsp;<a herf="javascript:void(0)" class="easyui-linkbutton" onclick="companyQuery()"
                                 iconcls="icon-search" plain="false">查询</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!--添加修改界面-->
<div id="company-dlg" class="easyui-dialog" style="padding: 10px" model="true"
     closed="true" buttons="#companyDlg-btn">
    <form id="addCompany" method="post">
        <table>
            <tr>
                <td style="height: 5px;"><input type="hidden" id="id" name="warehouseCompany.id"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>名称：</label></td>
                <td class="tdRight"><input id="name" class="easyui-validatebox" name="warehouseCompany.name"
                                           data-options="validType:['length[0,20]'],required:true"></td>

                <td class="tdLeft"><label>联系人：</label></td>
                <td class="tdRight"><input id="contact" name="warehouseCompany.contact"></td>

                <td class="tdLeft"><label>电话：</label></td>
                <td class="tdRight"><input id="phone" name="warehouseCompany.phone"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>类别：</label></td>
                <td class="tdRight"><input id="type" class="easyui-combobox" name="warehouseCompany.type"></td>

                <td class="tdLeft"><label>邮编：</label></td>
                <td class="tdRight"><input id="postcode" name="warehouseCompany.postcode"></td>

                <td class="tdLeft"><label>传真：</label></td>
                <td class="tdRight"><input id="fax" name="warehouseCompany.fax"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>税号：</label></td>
                <td class="tdRight"><input id="taxSign" name="warehouseCompany.taxSign"></td>

                <td class="tdLeft"><label>开户行：</label></td>
                <td class="tdRight"><input id="bank" name="warehouseCompany.bank"></td>

                <td class="tdLeft"><label>账号：</label></td>
                <td class="tdRight"><input id="accountNum" name="warehouseCompany.accountNum"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>地址：</label></td>
                <td colspan="5"><input id="address" name="warehouseCompany.address" style="width:100%"></td>
            </tr>

            <tr>
                <td class="tdLeft"><label>描述：</label></td>
                <td colspan="5" rowspan="2"><input id="desc" class="easyui-textbox" name="warehouseCompany.companyDesc"
                                                   data-options="multiline:true" style="width:100%;height: 50px;"></td>
            </tr>
        </table>
    </form>
</div>

<!--添加修改界面按钮-->
<div id="companyDlg-btn">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveCompany()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
       onclick="javascript:$('#company-dlg').dialog('close')">取消</a>
</div>

<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

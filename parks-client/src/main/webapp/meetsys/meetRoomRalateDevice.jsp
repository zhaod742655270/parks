<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/30
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>设备列表</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/meetsys/deviceRelation.js"></script>
    <style type="text/css">
        body {
            margin: auto;
            #max-width: 100%;
        }

        .header {
            width: 100%;
            height: 35px;
            background-color: #E6F0FF;
            padding: 12px 5px;

        }

        .content {
            width: 100%;
            position: absolute;
            top: 43px;
            bottom: 0px;
            left: 0px;
        }

        .content-left {
            float: left;
            height: 100%;
            width: 48%;
        }

        .content-mid {
            height: 100%;
            float: left;
            width: 5%;
            background-color: #E6F0FF;
        }

        .content-right {
            height: 100%;
            float: right;
            width: 47%;
        }
    </style>
    <html>
    <head>
        <title></title>
    </head>
    <body>
    <div class="header">
        <label style="font-size: 14px;">会议室：</label>

        <div id="equipId" class="easyui-combobox"></div>
    </div>
    <div class="content">
        <div class="content-left">
            <table id="equip-west">
                <thead>
                <tr>
                    <th data-options="field:'id',hidden:true">deviceID</th>
                    <th data-options="field:'typeDesc',sortable:true">设备类型</th>
                    <th data-options="field:'descInfo',sortable:true">设备名称</th>
                    <th data-options="field:'position',sortable:true">位置</th>

                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>

            <div id="equip-toolbar">
                <input id="equip-search" class="easyui-searchbox"
                       data-options="prompt:'请输入关键字',
                         searcher: function (value, name) {
                    $('#equip-west').datagrid('load', {descInfo: value });
                }"/>
            </div>

        </div>

        <div class="content-right">
            <table id="equip-east">
                <thead>
                <tr>
                    <th data-options="field:'id',hidden:true">deviceID</th>
                    <th data-options="field:'typeDesc',sortable:true">设备类型</th>
                    <th data-options="field:'descInfo',sortable:true">设备名称</th>
                    <th data-options="field:'position',sortable:true">位置</th>

                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="content-mid">
            <form>
                <br/><br/><br><br/><br/><br/><br><br/><br/><br/><br><br/><br/><br/>&nbsp;<input
                    data-options="position:absolute;" type="button" value="▶▶" onclick="distribute()"/>
                <br/><br/><br/>&nbsp;<input type="button" value="◀◀" onclick="withdraw()"/>
            </form>
        </div>
    </div>
    </body>
    </html>

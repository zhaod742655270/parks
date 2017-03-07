<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>全局班次定义</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/globalShift.js"></script>
    <style>
        td {
            height: 35px;
        }

        .tdRight {
            text-align: right;
            width: 120px;
        }
    </style>
</head>
<body>

<div class="easyui-panel" title="全局班次定义" data-options="fit:true">
    <div style="background-color: #efefef;padding: 5px 10px;">
        <a class="easyui-linkbutton" onclick="saveShift()" iconcls="icon-save" plain="true">保存</a>
        <a class="easyui-linkbutton" iconcls="icon-reload" plain="true">刷新</a>
    </div>
    <form method="post" id="globalShift-form" style="padding: 5px;">
        <div class="easyui-panel" style="width: 400px; height: 200px; ">
            <table>
                <tr>
                    <td><input type="hidden" name="definition.id" id="defId"></td>
                </tr>
                <tr>
                    <td class="tdRight">应上班次：</td>
                    <td><input class="easyui-combobox" id="shiftID" name="definition.shiftID"
                               data-options="valueField:'id',textField:'name',url:'shift/getValidNormal'"
                               style="width: 200px;"/></td>
                </tr>
                <tr>
                    <td class="tdRight">每周固定休息日：</td>
                    <td><select id="restDays" name="days" class="easyui-combobox" data-options="multiple:true"
                                style="width: 200px;">
                        <option value='星期一'>星期一</option>
                        <option value="星期二">星期二</option>
                        <option value="星期三">星期三</option>
                        <option value="星期四">星期四</option>
                        <option value="星期五">星期五</option>
                        <option value="星期六">星期六</option>
                        <option value="星期日">星期日</option>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdRight">工作日无打卡记：</td>
                    <td><select id="invalidAssert" name="invalidAssert" class="easyui-combobox"
                                data-options="multiple:false"
                                style="width: 200px;">
                        <option value="旷工">旷工</option>
                        <option value="休息">休息</option>
                    </select>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
</body>
</html>

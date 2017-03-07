<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>规律班次管理</title>
    <link rel="stylesheet" href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-extentions.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../resources/script/common.js"></script>
    <script type="text/javascript" src="../resources/script/attendancesys/regularShift.js"></script>
    <style>

        .left {
            width: 120px;
            height: 360px;
            float: left;

        }

        .right {

            width: 630px;

            height: 360px;
        }

        .right table {
            background: #E0ECFF;
            width: 100%;
        }

        .right td {
            background: #E0ECEE;
            color: #444;
            text-align: center;
            padding: 2px;
            height: 20px;
            width: 80px;;
        }

        .right td.bgGray {
            background: #fafafa;

        }

        .right td.over {
            background: #FBEC88;
        }

        .item {
            text-align: center;
            border: 1px solid #95B8E7;
            width: 80px;
        }

        .assigned {
            border: 0px solid #BC2A4D;
        }

        .trash {
            background-color: red;
        }

    </style>
</head>
<body class="easyui-layout">
<div data-options="region:'west',border:false,split:true" style="width: 260px;">
    <table id="regularShift-dg">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">id</th>
            <%--<th data-options="field:'serialNumber',width:120,sortable:true">编号</th>th--%>
            <th data-options="field:'name',width:100">名称</th>
            <th data-options="field:'unitCnt',width:50">周期数</th>
            <th data-options="field:'unit',width:50">单位</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

</div>
<div data-options="region:'center',border:true" title="内容">
    <div id="regularShifts-toolbar">

        <div id="regularShift-btns">

            <a href="javascript:void(0)" class="easyui-linkbutton"
               iconcls="icon-reload" plain="true"
               onclick="$('#regularShift-dg').datagrid('reload');">刷新</a>
        </div>

    </div>
    <!-- 用户window窗口-->
    <div id="regularShift-dlg" class="easyui-dialog"
         style="width: 780px; height: 450px; padding: 5px" modal="true"
         closed="true" buttons="#regularShift-dlg-buttons">
        <div class="left">
            <div class="easyui-panel" title="可拖动班次列表" style="height: 100%; width: 100%; padding: 5px;">
                <table id="dragShift">
                </table>
            </div>
        </div>
        <div class="right" style="float: right;  border: 1px solid #95B8E7;">
            <form id="regularShift-form" method="post">

                <div style=" background-color:#ECF3FF;  padding: 5px;">
                    <input id="regularShiftId" type="hidden" name="regularShift.id"> </input>
                    <label>名称：</label>&nbsp;
                    <input id="regularShiftName" type="text" class="easyui-validatebox"
                           data-options="required:true,validType:'length[1,20]'"
                           name="regularShift.name" style="width: 120px;"> </input>
                    &nbsp;&nbsp;
                    <label>周期数：</label></td>&nbsp;
                    <input id="round" class="easyui-numberspinner" style="width:45px;" value="1"
                           required="required" name="regularShift.unitCnt"
                           data-options="min:1,max:4,editable:false,onChange:function(){changeTable();}"> </input>

                    &nbsp;&nbsp;
                    <label>单位：</label>&nbsp;
                    <select id="unit" class="easyui-combobox" name="regularShift.unit" style="width:45px;"
                            data-options="onChange:function(newValue,OldValue)
                        { if(newValue=='周'){$('#round').numberspinner({max:4})} else{$('#round').numberspinner({max:31})}
                         $('#shiftBinding-table').empty();
                          changeTable();}">
                        <option value="周">周</option>
                        <option value="日">日</option>
                    </select>
                </div>
                <div>
                    <table id="shiftBinding-table">

                    </table>
                </div>
            </form>
        </div>
        <!--右键菜单-->

        <div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width:120px;">

            <div data-options="name:'print',iconCls:'icon-bell'">休息</div>

        </div>
    </div>
    <!-- 用户window窗口的功能按钮-->
    <div id="regularShift-dlg-buttons">
        <a id="regularShift-save-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-ok" onclick="saveRegularShift()">保存</a>

        <a id="regularShift-close-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-cancel"
           onclick="javascript:$('#regularShift-dlg').dialog('close')">取消</a>
    </div>
    <div class="right" style="margin: 2px;">
        <table id="bindingView-table">

        </table>
    </div>
</div>

<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

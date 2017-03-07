/**
 * dom加载完成，初始化各个控件
 */
$(function () {

    $('#app-dg').datagrid({
        title: '子系统列表',
        toolbar: '#apps-toolbar',
        striped: true, rownumbers: true,
        fitColums: true, fit: true,
        singleSelect: true, method: 'get', url: 'app/appList',
        loadFilter: function (data) {
            if (data.rows == null) {
                return data;
            }
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].isVisible) {
                    data.rows[i].isVisibleString = "可见";
                }
                else {
                    data.rows[i].isVisibleString = "不可见";
                }
            }
            return data;
        }
    });
})
// 定义窗口的提交地址
var formUrl = 'app/addApp';
// 打开新建子系统的窗口

function addApp() {
    $("#app-dlg").dialog("open").dialog('setTitle', '新建子系统');
    $('#app-form').form('clear');
    $('#isVisible').combobox("setValue", 'true');
    formUrl = 'app/addApp';

}
// 打开编辑子系统的窗口
function editApp() {
    var row = $('#app-dg').datagrid('getSelected');
    if (row) {
        $('#app-dlg').dialog('open').dialog('setTitle', '编辑子系统');

        $('#appId').val(row.id);
        $('#appName').val(row.appName);
        $('#appCode').val(row.appCode);
        $('#appPageUrl').val(row.appPageUrl);
        $('#appOrder').numberspinner('setValue', row.appOrder);
        $('#appDesc').val(row.appDesc);
        $('#app-form').form('validate');
        $('#isVisible').combobox("setValue", String(row.isVisible));
        formUrl = 'app/editApp';
    } else {
        $.messager.alert('提示', '需要选择一个子系统，才能进行编辑操作。', 'info');
    }

}
// 删除子系统
function deleteApp() {
    var row = $('#app-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("app/deleteApp", {
                    appId: row.id
                }, function (result) {
                    if (result.success) {
                        // 成功后刷新表格
                        $('#app-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', result.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个子系统，才能进行删除操作。', 'info');
    }

}
// 保存子系统
function saveApp() {

    $('#app-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#app-dlg').dialog('close'); // close the dialog
                $('#app-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}


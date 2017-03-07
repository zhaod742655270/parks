/**
 *
 */
$(function () {
    loadBtns('leaveType-btns', $('#menuId').val());


})
// 定义窗口的提交地址
var formUrl = 'leaveType/addLeaveType';
// 打开新建请假类型的窗口

function addLeaveType() {
    $("#leaveType-dlg").dialog("open").dialog('setTitle', '新建请假类型');
    $('#leaveType-form').form('clear');
    formUrl = 'leaveType/addLeaveType';
}
// 打开编辑请假类型的窗口
function editLeaveType() {
    var row = $('#leaveType-dg').datagrid('getSelected');
    if (row) {
        $('#leaveType-dlg').dialog('open').dialog('setTitle', '编辑请假类型');
        $('#leaveTypeId').val(row.id);
       // $('#serialNumber').val(row.serialNumber);
        $('#leaveTypeName').val(row.name);
        $('#leaveTypeSymbol').val(row.symbol);
        $('#leaveType-form').form('validate');
        formUrl = 'leaveType/editLeaveType';
    } else {
        $.messager.alert('提示', '需要选择一个请假类型，才能进行编辑操作。', 'info');
    }

}
// 删除请假类型
function deleteLeaveType() {
    var row = $('#leaveType-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("leaveType/deleteLeaveType", {
                    leaveTypeId: row.id
                }, function (result) {
                    if (result.success) {
                        // 成功后刷新表格
                        $('#leaveType-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', result.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个请假类型，才能进行删除操作。', 'info');
    }

}
// 保存请假类型
function saveLeaveType() {

    $('#leaveType-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#leaveType-dlg').dialog('close'); // close the dialog
                $('#leaveType-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}
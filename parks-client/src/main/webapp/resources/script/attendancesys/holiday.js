/**
 *
 */
$(function () {
    loadBtns('holiday-btns', $('#menuId').val());
    $('#holiday-dg').datagrid(
        {
            title: '假日列表',
            toolbar: '#holidays-toolbar',
            sortName: 'startDate',
            sortOrder: 'asc',
            striped: true,
            rownumbers: true,
            fitColums: true,
            fit: true,
            singleSelect: true,
            method: 'post',
            url: 'holiday/holidayList',
            pagination: true
        }
    );

})
// 定义窗口的提交地址
var formUrl = 'holiday/addHoliday';
// 打开新建假日的窗口

function addHoliday() {
    $("#holiday-dlg").dialog("open").dialog('setTitle', '新建假日');
    $('#holiday-form').form('clear');
    formUrl = 'holiday/addHoliday';
}
// 打开编辑假日的窗口
function editHoliday() {
    var row = $('#holiday-dg').datagrid('getSelected');
    if (row) {
        $('#holiday-dlg').dialog('open').dialog('setTitle', '编辑假日');
        $('#holidayId').val(row.id);
        $('#serialNumber').val(row.serialNumber);
        $('#holidayName').val(row.name);
        $('#startDate').datebox('setValue', row.startDate);
        $('#endDate').datebox('setValue', row.endDate);
        $('#holiday-form').form('validate');
        formUrl = 'holiday/editHoliday';
    } else {
        $.messager.alert('提示', '需要选择一个假日，才能进行编辑操作。', 'info');
    }

}
// 删除假日
function deleteHoliday() {
    var row = $('#holiday-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("holiday/deleteHoliday", {
                    holidayId: row.id
                }, function (result) {
                    if (result.success) {
                        // 成功后刷新表格
                        $('#holiday-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', result.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个假日，才能进行删除操作。', 'info');
    }

}
// 保存假日
function saveHoliday() {

    $('#holiday-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#holiday-dlg').dialog('close'); // close the dialog
                $('#holiday-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}
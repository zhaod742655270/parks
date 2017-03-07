/**
 *
 */
$(function () {
    $('#empId').combobox({
        required: true, valueField: 'empId',
        textField: 'empName',
        mode: 'remote',
        loader: myloader,
        formatter: function (row) {
            var s = '<span style="font-weight:bold">' + row.empName + '</span><br/>' +
                '<span style="color:#888">' + row.deptName + '</span>';
            return s;
        }
    });
    loadBtns('leave-btns', $('#menuId').val());


})
var myloader = function (param, success, error) {
    var q = param.q || '';
    if (q.length <= 1) {
        return false
    }
    $.ajax({
        url: '../supportsys/emp/getEmpsByName',
        dataType: 'json',
        type: 'post',
        data: {
            page: 1,
            sort: 'empName',
            rows: 20,
            order:'asc',
            empName: q
        },
        success: function (data) {
            var items = $.map(data.rows, function (item) {
                return {
                    empId: item.id,
                    empName: item.empName,
                    deptName: item.deptName

                };
            });
            success(items);
        },
        error: function () {
            error.apply(this, arguments);
        }
    });

}
// 定义窗口的提交地址
var formUrl = 'leave/addLeave';
// 打开新建请假类型的窗口

function addLeave() {
    $("#leave-dlg").dialog("open").dialog('setTitle', '新建请假记录');
    $('#leave-form').form('clear');
    formUrl = 'leave/addLeave';
}
// 打开编辑请假记录的窗口
function editLeave() {
    var row = $('#leave-dg').datagrid('getSelected');
    if (row) {

        $('#leave-dlg').dialog('open').dialog('setTitle', '编辑请假记录');
        $('#leave-form').form('clear');
        $('#leaveId').val(row.id);
        $('#typeID').combobox('setValue', row.typeID);
        $('#empId').combobox("setValue", row.empId);
        $('#empId').combobox("setText", row.empName);
        $('#fromDate').datetimebox('setValue', row.fromDate);
        $('#toDate').datetimebox('setValue', row.toDate);
        formUrl = 'leave/editLeave';
    } else {
        $.messager.alert('提示', '需要选择一个请假记录，才能进行编辑操作。', 'info');
    }

}
// 删除请假记录
function deleteLeave() {
    var row = $('#leave-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("leave/deleteLeave", {
                    leaveId: row.id
                }, function (result) {
                    if (result.success) {
                        // 成功后刷新表格
                        $('#leave-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', result.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个请假记录，才能进行删除操作。', 'info');
    }

}
// 保存请假记录
function saveLeave() {

    $('#leave-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#leave-dlg').dialog('close'); // close the dialog
                $('#leave-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}
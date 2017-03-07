/**
 *
 */
$(function () {
    loadBtns('role-btns', $('#menuId').val());
    $('#role-dg').datagrid({
        title: '角色列表', toolbar: '#roles-toolbar', pagination: true, sortName: 'roleName', sortOrder: 'asc',multiSort:true,
        striped: true, rownumbers: true, fitColums: true, fit: true, singleSelect: true, method: 'post', url: 'role/roleList'
    });
    //验证用户名是否存在
//    /*$('#roleName').validatebox({
//
//     delay: 600,
//     required: true,
//     validType: ['remoteValidate["/role/validateNameExist","ID::"+$("#roleID").val()]', 'length[3,20]']
//     }
//     );*/

})
// 定义窗口的提交地址
var formUrl = 'role/addRole';
// 打开新建角色的窗口

function addRole() {
    $("#role-dlg").dialog("open").dialog('setTitle', '新建角色');
    $('#role-form').form('clear');
    formUrl = 'role/addRole';
}
// 打开编辑角色的窗口
function editRole() {
    var row = $('#role-dg').datagrid('getSelected');
    if (row) {
        $('#role-dlg').dialog('open').dialog('setTitle', '编辑角色');
        $('#role-form').form('load',row);
        $('#roleId').val(row.id);
        $('#roleName').val(row.roleName);
        $('#roleDesc').val(row.roleDesc);
        formUrl = 'role/editRole';
    } else {
        $.messager.alert('提示', '需要选择一个角色，才能进行编辑操作。', 'info');
    }

}
// 删除角色
function deleteRole() {
    var row = $('#role-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("role/deleteRole", {
                    roleId: row.id
                }, function (result) {
                    if (result.success) {
                        // 成功后刷新表格
                        $('#role-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', result.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个角色，才能进行删除操作。', 'info');
    }

}
// 保存角色
function saveRole() {

    $('#role-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#role-dlg').dialog('close'); // close the dialog
                $('#role-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}
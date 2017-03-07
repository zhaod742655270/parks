/**
 * dom加载完成，初始化各个控件
 */
$(function () {
    $('#user-dg').datagrid({
        title: '用户列表', toolbar: '#users-toolbar', pagination: true, sortName: 'userName', sortOrder: 'asc',
        striped: true, rownumbers: true, fitColums: true, fit: true, singleSelect: true, method: 'post', url: 'user/userList',
        loadFilter: function (data) {
            if (data.rows == null) {
                return data;
            }

            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].roles.length > 0) {
                    var roleNames = "";
                    var roleIds = [];
                    for (var j = 0; j < data.rows[i].roles.length; j++) {
                        roleNames = roleNames + data.rows[i].roles[j].roleName + ' ';
                        roleIds.push(data.rows[i].roles[j].id)
                    }
                    data.rows[i].roleIds = roleIds;
                    data.rows[i].roleNames = roleNames;
                }
                if (data.rows[i].department) {
                    data.rows[i].deptName = data.rows[i].department.deptName
                }


            }
            return data;
        }
    });
    $('#userName').validatebox({
        required: true
    });
    loadBtns('user-btns', $('#menuId').val());

    //验证用户名是否存在


})
// 定义窗口的提交地址
var formUrl = 'user/addUser';
// 打开新建用户的窗口

function addUser() {
    $("#user-dlg").dialog("open").dialog('setTitle', '新建用户');
    $('#user-form').form('clear');
    formUrl = 'user/addUser';

}
// 打开编辑用户的窗口
function editUser() {
    var row = $('#user-dg').datagrid('getSelected');
    if (row) {
        formUrl = 'user/editUser';
        $('#user-form').form('clear');
        $('#user-dlg').dialog('open').dialog('setTitle', '编辑用户');
        $('#userId').val(row.id);
        $('#userName').val(row.userName);
        $('#password').val(row.password);
        $('#nickname').val(row.nickname);
        $('#deptId').combobox("setValue", row.department.id);
        $('#roleIds').combobox("setValues", row.roleIds);

    } else {
        $.messager.alert('提示', '需要选择一个用户，才能进行编辑操作。', 'info');
    }

}
// 删除用户
function deleteUser() {
    var row = $('#user-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("user/deleteUser", {
                    userId: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#user-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个用户，才能进行删除操作。', 'info');
    }

}
// 保存用户
function saveUser() {

    $('#user-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#user-dlg').dialog('close'); // close the dialog
                $('#user-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

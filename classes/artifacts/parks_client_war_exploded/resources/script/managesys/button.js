/**
 * dom加载完成，初始化各个控件
 */
$(function () {

    $('#button-dg').datagrid({
        title: '按钮列表',
        toolbar: '#buttons-toolbar',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'get',
        url: 'btn/btnList',
        queryParams: {
            menuId: $('#subMenuId').val()
        },
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
    })
//   加载图标
    getstyle('btnIconUrl');

})
// 定义窗口的提交地址
var formUrl = 'btn/addBtn';
// 打开新建按钮的窗口

function addBtn() {
    $("#button-dlg").dialog("open").dialog('setTitle', '新建按钮');
    $('#button-form').form('clear');
    $('#menuId').val($('#subMenuId').val());
    $('#isVisible').combobox("setValue", 'true');
    formUrl = 'btn/addBtn';

}
// 打开编辑按钮的窗口
function editBtn() {
    var row = $('#button-dg').datagrid('getSelected');
    if (row) {
        $('#button-dlg').dialog('open').dialog('setTitle', '编辑按钮');
        //  $('#button-form').form('load', row);
        $('#btnId').val(row.id);
        $('#menuId').val(row.menuId);
        $('#btnName').val(row.btnName);
        $('#btnCode').val(row.btnCode);
        $('#btnScript').val(row.btnScript);
        $('#btnUrl').val(row.btnUrl);
        $('#btnIconUrl').combotree('setValue', row.btnIconUrl);
        $('#btnOrder').numberspinner('setValue', row.btnOrder);
        $('#isVisible').combobox("setValue", String(row.isVisible));
        $('#button-form').form('validate');
        formUrl = 'btn/editBtn';
    } else {
        $.messager.alert('提示', '需要选择一个按钮，才能进行编辑操作。', 'info');
    }

}
// 删除按钮
function deleteBtn() {
    var row = $('#button-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("btn/deleteBtn", {
                    btnId: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#button-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个按钮，才能进行删除操作。', 'info');
    }

}
// 保存按钮
function saveBtn() {

    $('#button-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#button-dlg').dialog('close'); // close the dialog
                $('#button-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}
function formatIcon(val, row) {
    if (val) {
        return '<span class="' + val + '">&nbsp;&nbsp;&nbsp;</span>' + val;
    } else {
        return val;
    }
}
//返回菜单管理
function backMenu() {
    parent.document.getElementById('commoncontent').contentWindow.history.back();

}

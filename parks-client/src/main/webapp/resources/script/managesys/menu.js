/**
 * dom加载完成，初始化各个控件
 */
$(function () {

    /*
     // 验证主菜单名是否存在
     $('#menuName')
     .validatebox(
     {

     delay : 600,
     required : true,
     validType : [
     'remoteValidate["/menu/validateNameExist","ID::"+$("#menuid").val()]',
     'length[3,20]' ]
     });
     */

    $('#appId').combobox({
        url: 'app/getAllApp',
        valueField: 'id',
        textField: 'appName',
        onSelect: function (rec) {
            $('#menu-dg').datagrid('load', { appId: rec.id });
            var appData = $('#appId').combobox('getData');
            $('#menuAppId').combobox('loadData', appData);

        },
        onLoadSuccess: function () {
            var data = $(this).combobox('getData');
            if (data && data.length > 0) {
                $(this).combobox('select', data[0].id);
            }
        }
    });

    $('#menu-dg').datagrid(
        {
            title: '主菜单列表',
            toolbar: '#menus-toolbar',
            striped: true,
            rownumbers: true,
            fitColums: true,
            fit: true,
            singleSelect: true,
            method: 'get',
            url: 'menu/menuList',
            onSelect: function (rowIndex, rowData) {
                $('#submenu-dg').datagrid('load', { menuId: rowData.id });
                var menuData = $(this).datagrid('getData').rows;
                $('#subMenuParentId').combobox('loadData', menuData);
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
        }
    )

    $('#submenu-dg').datagrid({
        title: '子菜单列表',
        toolbar: '#submenus-toolbar',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'get',
        url: 'menu/menuList',
        onSelect: function (rowIndex, rowData) {

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

    getstyle('menuIcoUrl');


})

// 定义窗口的提交地址
var formUrl = 'menu/addMenu';
// 打开新建主菜单的窗口

function addMenu() {
    var data = $('#appId').combobox('getValue')
    if (data) {
        $("#menu-dlg").dialog("open").dialog('setTitle', '新建主菜单');
        $('#menu-form').form('clear');
        $('#menuAppId').combobox('setValue', data);
        $('#menuIsVisible').combobox("setValue", 'true');
        formUrl = 'menu/addMenu';
    }
    else {
        $.messager.alert('提示', '需要选择一个子系统，才能新建主菜单。', 'info');
    }

}
// 打开编辑主菜单的窗口
function editMenu() {
    var row = $('#menu-dg').datagrid('getSelected');
    if (row) {
        $('#menu-dlg').dialog('open').dialog('setTitle', '编辑主菜单');
        // $('#menu-form').form('load', row);
        $('#menuId').val(row.id);
        $('#menuName').val(row.menuName);
        $('#menuCode').val(row.menuCode);
        $('#menuAppId').combobox('setValue', row.appId);
        $('#menuOrder').numberspinner('setValue', row.menuOrder);
        $('#menuIsVisible').combobox("setValue", String(row.isVisible));
        formUrl = 'menu/editMenu';
        $('#menu-form').form('validate');
    } else {
        $.messager.alert('提示', '需要选择一个主菜单，才能进行编辑操作。', 'info');
    }

}
// 删除主菜单
function deleteMenu() {
    var row = $('#menu-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("menu/deleteMenu", {
                    menuId: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#menu-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个主菜单，才能进行删除操作。', 'info');
    }

}
// 保存主菜单
function saveMenu() {

    $('#menu-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#menu-dlg').dialog('close'); // close the dialog
                $('#menu-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

// 打开新建子菜单的窗口

function addSubmenu() {
    var row = $('#menu-dg').datagrid('getSelected');
    if (row) {
        $("#submenu-dlg").dialog("open").dialog('setTitle', '新建子菜单');
        $('#submenu-form').form('clear');
        $('#subMenuParentId').combobox('setValue', row.id);
        $('#subMenuSysAppId').val(row.appId);
        $('#subMenuIsVisible').combobox("setValue", 'true');
        formUrl = 'menu/addMenu';
    }
    else {
        $.messager.alert('提示', '需要选择一个主菜单，才能新建子菜单。', 'info');
    }

}
//转换一下图标
function formatIcon(val, row) {
    if (val) {
        return '<span class="' + val + '">&nbsp;&nbsp;&nbsp;</span>' + val;
    } else {
        return val;
    }
}

// 打开编辑子菜单的窗口
function editSubmenu() {
    var row = $('#submenu-dg').datagrid('getSelected');
    if (row) {
        $('#submenu-dlg').dialog('open').dialog('setTitle', '编辑子菜单');
        //$('#submenu-form').form('load', row);
        $('#subMenuId').val(row.id);
        $('#subMenuName').val(row.menuName);
        $('#subMenuCode').val(row.menuCode);
        $('#subMenuSysAppId').val(row.appId);
        $('#subMenuParentId').combobox('setValue', row.parentId);
        $('#subMenuPageUrl').val(row.menuPageUrl);
        $('#menuIcoUrl').combotree('setValue', row.menuIconUrl);
        $('#subMenuOrder').numberspinner('setValue', row.menuOrder);
        $('#subMenuIsVisible').combobox("setValue", String(row.isVisible));
        formUrl = 'menu/editMenu';
        $('#submenu-form').form('validate');
    } else {
        $.messager.alert('提示', '需要选择一个子菜单，才能进行编辑操作。', 'info');
    }

}
// 删除子菜单
function deleteSubmenu() {
    var row = $('#submenu-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("menu/deleteMenu", {
                    menuId: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#submenu-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个子菜单，才能进行删除操作。', 'info');
    }

}
// 保存子菜单
function saveSubmenu() {

    $('#submenu-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#submenu-dlg').dialog('close'); // close the dialog
                $('#submenu-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}
// 打开按钮管理
function btnManage() {

    var row = $('#submenu-dg').datagrid('getSelected');
    if (row) {
        window.location.href = 'button.jsp?menuId=' + row.id;
    }
    else {
        $.messager.alert('提示', '需要选择一个子菜单，才能进行按钮管理。', 'info');
    }

}

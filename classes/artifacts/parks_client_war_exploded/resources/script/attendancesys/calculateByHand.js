/**
 *
 */
$(function () {
    $('#dept-tree').tree(
        {
            url: '../managesys/dept/getTreeByDeptId',
            method: 'get',
            animate: true,
            lines: true,
            dnd: false,
            onSelect: function (node) {
                $('#employee-dg').datagrid('load', {
                    deptId: node.id
                });
            }
        }
    )
    $('#employee-dg').datagrid({
        title: '员工列表', toolbar: '#employees-toolbar',
        pagination: true, sortName: 'empname', sortOrder: 'asc',
        striped: true, rownumbers: true, fitColums: true, fit: true,
        singleSelect: false, method: 'get',
        url: '../supportsys/emp/getEmpByDeptId'
    })
})

function reCalc() {
    var date = $('#date').datebox('getValue');
    var now = new Date().Format("yyyy-MM-dd");
    if (date == "") {
        $.messager.alert('提示', '必须选择日期才能计算', 'info');

    }
    else if (date >= now) {
        $.messager.alert('提示', '日期必须早于今天', 'info');

    }
    else {
        var node = $('#dept-tree').tree('getSelected');
        if (node) {
            var calcUrl = 'calc/calcForDept';
            var rows = $('#employee-dg').datagrid('getSelections');
            var param = {};
            param.date = $('#date').datebox('getValue');
            param.deptId = node.id;
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    param["empIds[" + i + "]"] = rows[i].id;
                }
                calcUrl = 'calc/calcForEmps';
            }
            $.messager.alert('提示','开始计算，计算需要花费几分钟...' , 'info');
            $.post(calcUrl,
                param,
                function (result) {
                    if (!result.success) {
                        $.messager.alert('计算失败', result.message, 'error');
                    }
                }, "json")
        }
        else {
            $.messager.alert('提示', '必须选择一个部门或者一个员工才能计算', 'info');
            return;
        }
    }

}

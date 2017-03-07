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
        title: '员工列表', toolbar: '#employees-toolbar', pagination: true, sortName: 'empname', sortOrder: 'asc',
        striped: true, rownumbers: true, fitColums: true, fit: true, singleSelect: false, method: 'get', url: '../supportsys/emp/getEmpByDeptId',
        loadFilter: function (data) {
            if (data.rows == null) {
                return data;
            }
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].isInvolve == true) {
                    data.rows[i].isInvolve = "开启";
                }
                else {
                    data.rows[i].isInvolve = "关闭";
                }
                if(data.rows[i].empSex==0)
                {
                 data.rows[i].Sex="男";
                }
              else if  (data.rows[i].empSex==1)
                {
                    data.rows[i].Sex="女";
                }
                else
                {
                    data.rows[i].Sex="其他";
                }

            }
            return data;
        }
    })
})

// 切换整个部门下员工的考勤开关。
function switchAllEmp(flag) {

    var node = $('#dept-tree').tree('getSelected');
    if (node) {
        $.post("../supportsys/emp/switchAllEmp", {
            deptId: node.id,
            isInvolve: flag
        }, function (result) {
            if (result.success) {
                // 成功后刷新表格
                $('#employee-dg').datagrid('reload');
            } else {
                $.messager.alert('操作失败', result.message, 'error');
            }
        }, "json");
    }
    else {
        $.messager.alert('提示', '需要选择一个部门，才能进行操作。', 'info');
    }
}
// 切换所选员工的考勤开关。
function switchEmp(flag) {

    var rows = $('#employee-dg').datagrid('getSelections');

    if (rows.length > 0) {
        var param = {};
        param.isInvolve = flag;
        for (var i = 0; i < rows.length; i++) {
            param["empIds[" + i + "]"] = rows[i].id;
        }

        $.ajax({
            url: '../supportsys/emp/switchEmp',
            type: 'POST',
            data: param,
            dataType: 'json',
            success: function (result) {
                if (result.success) {
                    $('#employee-dg').datagrid('reload');
                }
                else {
                    $.messager.alert('错误', result.message, 'error');
                }
            }
        });
    }
    else {
        $.messager.alert('提示', '需要选择一个员工，才能进行操作。', 'info');
    }

}
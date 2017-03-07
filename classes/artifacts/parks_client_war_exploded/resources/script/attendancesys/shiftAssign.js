/**
 *
 */

$(function () {
    //  loadBtns('shiftAssign-btns', $('#menuId').val());


    $('#dept-tree').tree({
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
    );

    $('#employee-dg').datagrid({
        title: '员工列表',
        pagination: true,
        sortName: 'empname',
        sortOrder: 'asc',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: false,
        method: 'post',
        url: '../supportsys/emp/getEmpByDeptId',
        toolbar: '#emp-toolbar'
    });
    var empPage = $('#employee-dg').datagrid('getPager');
    $(empPage).pagination({  showPageList: false,
        showRefresh: false,
        displayMsg: ''});

    $('#pp').pagination({

        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            loadRecord();

            $(this).pagination('loaded');
        }
    });
    $('#shifts').combobox({
        valueField: 'id',
        textField: 'name',
        url: "../attendancesys/regularShift/regularShiftList"

    });
    $.post("shift/getValidShift", function (result) {
        var restShift;
        for (var i = 0; i < result.length; i++) {
            if (result[i].shiftType == "NORMAL") {
                $('#mm').menu('appendItem', {
                    text: result[i].name,
                    id: result[i].id,
                    iconCls: 'icon-clock'
                });
            }
            else {
                restShift = result[i];
            }
        }
        $('#mm').menu('appendItem', {
            separator: true
        });
        $('#mm').menu('appendItem', {
            text: restShift.name,
            id: restShift.id,
            iconCls: 'icon-bell'

        });


    }, "json");

    initControl();

})
function initControl() {
    var now = new Date();
    //当前日期的年月yyyy-MM
    var yearAndMonth = now.Format("yyyy-MM");
    var month = now.getMonth();
    //这个月的第一天
    var firstDay = yearAndMonth + "-01";
    //这个月的最后一天

    now.setMonth(++month);
    now.setDate(0);
    var lastDay = now.Format("yyyy-MM-dd");
    $('#beginMonth').datetimespinner("setValue", yearAndMonth);
    $('#endMonth').datetimespinner("setValue", yearAndMonth);
    $('#startDate').datebox("setValue", firstDay);
    $('#endDate').datebox("setValue", lastDay);

}
function formatter2(date) {
    if (!date) {
        return '';
    }
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    return y + '-' + (m < 10 ? ('0' + m) : m);
}
function parser2(s) {
    if (!s) {
        return null;
    }
    var ss = s.split('-');
    var y = parseInt(ss[0], 10);
    var m = parseInt(ss[1], 10);
    if (!isNaN(y) && !isNaN(m)) {
        return new Date(y, m - 1, 1);
    } else {
        return new Date();
    }
}
function menuHandler(item) {
    var shiftId = item.id;
    var shiftName = item.text == "清除班次" ? '' : item.text;
    var shiftAssignId = $($('.over').parent()[0].cells[1]).text().trim();
    var day = $('.over').attr('index');

    $.post("shiftAssign/updateDayShiftForEmp",
        {shiftId: shiftId, shiftAssignId: shiftAssignId, day: day},
        function (result) {
            if (result.success) {
                $('.over').empty().text(shiftName);
            }
            else {
                $.messager.alert('错误', result.message, 'error');
            }
        }, "json");
}

function initTable() {
    $('.rightClick').bind({ 'contextmenu': function (e) {
            e.preventDefault();
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        },
            'mousedown': function () {
                $('.rightClick').removeClass('over');
                $(this).addClass('over');
            }
        }
    );
    $('.deleteMenu').bind({ 'contextmenu': function (e) {
            e.preventDefault();
            $('#dmm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }, 'mousedown': function () {
            $('.deleteMenu').removeClass('del');
            $(this).addClass('del');
        }
        }
    );
}
// 定义窗口的提交地址
var formUrl = 'shiftAssign/assignShiftForDept';
// 打开新建排班的窗口
var objectIds = [];
var objectId = undefined;
function shiftAssign() {
    var name = "";

    var tabIndex = $('#tt').tabs('options').selected// 0是部门，1是员工。
    if (tabIndex == 0) {
        var node = $('#dept-tree').tree('getSelected');
        if (node) {
            objectId = node.id;
            name = node.text;
            formUrl = 'shiftAssign/assignShiftForDept';
        }
        else {

            $.messager.alert('提示', '必须选择一个部门才能排班', 'info');
            return;
        }
    }
    else {
        var rows = $('#employee-dg').datagrid('getSelections');

        if (rows.length > 0) {
            for (var i = 0; i < rows.length; i++) {
                objectIds.push(rows[i].id);
                name += " ";
                name += rows[i].empName;
            }

            formUrl = 'shiftAssign/assignShiftForEmps';
        }
        else {
            $.messager.alert('提示', '必须选择一个员工才能排班', 'info');
            return;
        }
    }

    // $('#shiftAssign-form').form('clear');
    $('#objectId').val(objectId);
    $('#shiftAssignName').val(name);
    $("#shiftAssign-dlg").dialog("open").dialog('setTitle', '排班');


}

// 删除排班
function deleteShiftAssign() {
    var shiftAssignId = $($('.del').parent()[0].cells[1]).text().trim();
    var empName = $($('.del').parent()[0].cells[2]).text().trim();
    var date = $($('.del').parent()[0].cells[3]).text().trim();
    $.messager.confirm('确认', '是否删除“' + empName + '”,' + date + '排班信息？', function (r) {
        if (r) {
            $.post("shiftAssign/deleteShiftAssign", {
                shiftAssignId: shiftAssignId
            }, function (result) {
                if (result.success) {
                    // 成功后刷新表格
                    loadRecord();
                } else {
                    $.messager.alert('操作失败', result.message, 'error');
                }
            }, "json");
        }
    });
}
// 保存排班
function saveShiftAssign() {
    var validation = $('#shiftAssign-form').form('validate');
    var param = {};
    for (var i = 0; i < objectIds.length; i++) {
        param["empIds[" + i + "]"] = objectIds[i];
    }
    param.deptId = objectId;
    param.begin = $('#startDate').datebox('getValue');
    param.end = $('#endDate').datebox('getValue');
    param.regularShiftId = $('#shifts').combobox('getValue');
    if (validation) {
        $.post(formUrl,
            param,
            function (result) {
                if (result.success) {
                    $('#shiftAssign-dlg').dialog('close'); // close the dialog

                } else {
                    $.messager.alert('保存失败', result.message, 'error');
                }
            }, "json")
    }


}
//查询
function loadRecord() {
    var empIds = [];
    var deptId;
    var tabIndex = $('#tt').tabs('options').selected// 0是部门，1是员工。
    if (tabIndex == 0) {
        var node = $('#dept-tree').tree('getSelected');
        if (node) {
            deptId = node.id;
        }
    }
    else {
        var rows = $('#employee-dg').datagrid('getSelections');

        if (rows.length > 0) {
            for (var i = 0; i < rows.length; i++) {
                empIds.push(rows[i].id);
            }
        }
    }

    var param = {};
    var pagination = $('#pp').pagination('options');
    param.empName = $('#empName').textbox('getValue');
    param.beginMonth = $('#beginMonth').datetimespinner('getValue');
    param.endMonth = $('#endMonth').datetimespinner('getValue');
    param.rows = pagination.pageSize;
    param.page = pagination.pageNumber == 0 ? 1 : pagination.pageNumber;
    param.order = "asc";
    param.sort = "yearAndMonth";
    param.deptId = deptId;
    for (var i = 0; i < empIds.length; i++) {
        param["empIds[" + i + "]"] = empIds[i];
    }
    $("#mockGrid").easyMask('show', {msg: '加载数据中，请稍候。。。'});
    $.post("shiftAssign/shiftAssignList",
        param,
        function (result) {
            //修改分页控件的参数
            setPagination(result.total, result.queryBean);
            fillTable(result.rows);
            initTable();
            $("#mockGrid").easyMask('hide');
        }, "json");
}

function setPagination(total, queryBean) {
    $('#pp').pagination({total: total, pageSize: queryBean.rows, pageNumber: queryBean.page});
}
function fillTable(rows) {
    $('#tContent').empty();
    for (var i = 0; i < rows.length; i++) {

        var bgc = i % 2 == 1 ? '<tr class="background" >' : "<tr></tr>";
        var index = i + 1;
        var row = bgc + '<td class="background">' + index + '</td> <td class="hiddenColumn">' + rows[i].id + '</td><td class="deleteMenu">' + rows[i].empName + ' </td><td>' + rows[i].yearAndMonth + '</td>';
        for (var z = 1; z <= 31; z++) {
            var shiftName = rows[i]['sName' + z] == undefined ? '' : rows[i]['sName' + z];
            var day = '<td index=' + z + ' class="rightClick">' + shiftName + '    </td>';
            row += day;
        }
        row += '</tr> ';
        $(row).appendTo('#tContent');

    }


}
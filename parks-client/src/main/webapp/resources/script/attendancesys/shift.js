/**
 * dom加载完成，初始化各个控件
 */
$(function () {
    $('#shift-dg').datagrid({
        title: '班次列表', toolbar: '#shifts-toolbar',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'get',
        url: 'shift/getAllNormal',
        onSelect: function (rowIndex, rowData) {
            $('#shiftID').val(rowData.id);
            $('#interval-dg').datagrid('reload', {shiftID: rowData.id});
        }
    });
    $('#interval-dg').datagrid({
            title: '时段列表',
            toolbar: '#intervals-toolbar',
            striped: true,
            rownumbers: true,
            fitColums: true,
            fit: true,
            singleSelect: true,
            method: 'get',
            url: 'interval/getIntervals',
            loadFilter: function (data) {
                if (data.rows == null) {
                    return data;
                }
                for (var i = 0; i < data.rows.length; i++) {
                    if (data.rows[i].needSignIn == true) {
                        data.rows[i].needSignIn = "是";
                    }
                    else {
                        data.rows[i].needSignIn = "否";
                    }
                    if (data.rows[i].needSignOut == true) {
                        data.rows[i].needSignOut = "是";
                    }
                    else {
                        data.rows[i].needSignOut = "否";
                    }
                }
                return data;
            }
        }
    )
    $('#intervalType').combobox({
        required: true,
        editable: false,
        validType: {intervalType: ['#interval-dg', '该班次已存这个时段']}
    })
    //考勤开始时间
    $('#signInTimeBegin').timespinner({
        value: "06:00:00",
        required: true,
        onChange: validateTime

    })
    //上班时间
    $('#workTimeBegin').timespinner({
        value: "08:00:00",
        required: true,
        validType: {intervalTime: ['#workTimeBeginFlag', '#signInTimeBeginFlag', '#signInTimeBegin', '必须大于考勤开始时间']},
        onChange: validateTime
    })
    //迟到结束时间
    $('#lateDeadLine').timespinner({
        value: "9:30:59",
        required: true,
        validType: {intervalTime: ['#lateDeadLineFlag', '#workTimeBeginFlag', '#workTimeBegin', '必须大于上班时间']},
        onChange: validateTime
    })
    //早退开始时间
    $('#earlyDeadLine').timespinner({
        value: "9:31:00",
        required: true,
        validType: {intervalTime: ['#earlyDeadLineFlag', '#lateDeadLineFlag', '#lateDeadLine', '必须大于迟到结束时间']},
        onChange: validateTime
    })
    //下班时间
    $('#workTimeEnd').timespinner({
        value: "12:00:00",
        required: true,
        validType: {intervalTime: ['#workTimeEndFlag', '#earlyDeadLineFlag', '#earlyDeadLine', '必须大于早退开始时间']},
        onChange: validateTime
    })
    //结束考勤时间
    $('#signOutTimeEnd').timespinner({
        value: "13:30:00",
        required: true,
        validType: {intervalTime: ['#signOutTimeEndFlag', '#workTimeEndFlag', '#workTimeEnd', '必须大于下班时间']},
        onChange: validateTime
    })

    $('#signInDay').numberbox({
        min: 0, max: 1, precision: 1, required: true
    });
    $('#signOutDay').numberbox({min: 0, max: 1, precision: 1, required: true});

})
// 定义窗口的提交地址
var shiftFormUrl = 'shift/addShift';
var intervalFormUrl = 'interval/addInterval';

//第1个参数是被验证的控件相关联的flagid，第二个参数是要比较控件相关联的flagid，第3个为要比较控件的id， 第4个是错误信息
$.extend($.fn.validatebox.defaults.rules, {
    intervalTime: {
        validator: function (value, param) {
            $.fn.validatebox.defaults.rules.intervalTime.message = param[3];
            var flag = $(param[0]).combobox('getValue');
            var sFlag = $(param[1]).combobox('getValue');
            var sTime = $(param[2]).timespinner('getValue');
            flag = flag + value;
            sFlag = sFlag + sTime;
            return flag > sFlag;
        },
        message: '错误'
    }
});

Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i].id == val) return i;
    }
    return -1;
};
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
//第一个参数为datagrid控件 id，第二个为错误信息
$.extend($.fn.validatebox.defaults.rules, {
    intervalType: {
        validator: function (value, param) {
            $.fn.validatebox.defaults.rules.intervalType.message = param[1];
            var id = $('#intervalID').val();
            var rows = $(param[0]).datagrid('getRows').concat();
            rows.remove(id);
            if (rows) {
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].type == value) {
                        return false;
                    }
                }
            }
            return true;
        },
        message: '错误'
    }
});
function validateTime() {
    $('#interval-form').form('validate');
}
// 打开新建班次的窗口
function addShift() {
    $('#shift-form').form('clear');
    $('#shiftType').val('NORMAL');
    $("#shift-dlg").dialog("open").dialog('setTitle', '新建班次');
    shiftFormUrl = 'shift/addShift';

}
// 打开编辑班次的窗口
function editShift() {
    var row = $('#shift-dg').datagrid('getSelected');
    if (row) {
        $('#shift-form').form('clear');
        $('#shiftType').val('NORMAL');
        $('#shift-dlg').dialog('open').dialog('setTitle', '编辑班次');
        $('#shift-form').form('load', row);

        shiftFormUrl = 'shift/editShift';
    } else {
        $.messager.alert('提示', '需要选择一个班次，才能进行编辑操作。', 'info');
    }

}
// 删除班次
function deleteShift() {
    var row = $('#shift-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("shift/deleteShift", {
                    id: row.id
                }, function (result) {
                    if (result.success) {
                        // 成功后刷新表格
                        $('#shift-dg').datagrid('reload');
                        $('#interval-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', result.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个班次，才能进行删除操作。', 'info');
    }

}
// 保存班次
function saveShift() {

    $('#shift-form').form('submit', {
        url: shiftFormUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#shift-dlg').dialog('close'); // close the dialog
                $('#shift-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

function addInterval() {
    var row = $('#shift-dg').datagrid('getSelected');
    if (row) {
        var rowNum = $('#interval-dg').datagrid('getRows').length;
        if (rowNum < 2) {
            $('#interval-form').form('validate');
            // $('#interval-form').form('clear');
            $('#intervalID').val('');
            $('#intervalName').val();
            $("#interval-dlg").dialog("open").dialog('setTitle', '新建时段');

            intervalFormUrl = 'interval/addInterval';
        }
        else {
            $.messager.alert('提示', '目前系统只支持两个时段。', 'info');
        }
    }
    else {
        $.messager.alert('提示', '需要选择一个班次，才能新建时段。', 'info');
    }

}
// 打开编辑时段的窗口
function editInterval() {
    var row = $('#interval-dg').datagrid('getSelected');
    if (row) {

        $('#interval-dlg').dialog('open').dialog('setTitle', '编辑时段');
        $('#interval-form').form('load', row);
        // $('#interval-form').form('validate');
        if (row.needSignIn == '是') {
            document.getElementById("needSignIn").checked = true;
        }
        if (row.needSignOut == '是') {
            document.getElementById("needSignOut").checked = true;
        }
        intervalFormUrl = 'interval/editInterval';
    } else {
        $.messager.alert('提示', '需要选择一个时段，才能进行编辑操作。', 'info');
    }

}
// 删除时段
function deleteInterval() {
    var row = $('#interval-dg').datagrid('getSelected');
    if (row) {
        //查看有几个时段，如果为1个就不让删。原因是怕删了时段，班次里没时段造成计算异常。
        var rowsCount = $('#interval-dg').datagrid('getRows').length;
        if (rowsCount > 1) {
            $.messager.confirm('确认', '是否删除该条数据？', function (r) {
                if (r) {
                    $.post("interval/deleteInterval", {
                        id: row.id
                    }, function (result) {
                        if (result.success) {
                            // 成功后刷新表格
                            $('#interval-dg').datagrid('reload');
                        } else {
                            $.messager.alert('操作失败', result.message, 'error');
                        }
                    }, "json");
                }
            });
        }
        else
        {
            $.messager.alert('提示', '最后一个时段不能删除', 'info');
        }
    } else {
        $.messager.alert('提示', '需要选择一个班次，才能进行删除操作。', 'info');
    }

}
// 保存时段
function saveInterval() {


    $('#interval-form').form('submit', {
        url: intervalFormUrl,
        onSubmit: function () {
            var row = $('#interval-dg').datagrid('getRows')
            if (row) {
                for (var i = 0; i < row.length; i++) {
                    var cBTimeFlag = $('#signInTimeBeginFlag').combobox('getValue');
                    var cETimeFlag = $('#signOutTimeEndFlag').combobox('getValue');
                    var cBTime = cBTimeFlag + $('#signInTimeBegin').timespinner('getValue');
                    var cETime = cETimeFlag + $('#signOutTimeEnd').timespinner('getValue');
                    if (row[i].id != $('#intervalID').val()) {
                        var oBTimeFlag = row[i].signInTimeBeginFlag;
                        var oETimeFlag = row[i].signOutTimeEndFlag;
                        var oBTime = oBTimeFlag + row[i].signInTimeBegin;
                        var oETime = oETimeFlag + row[i].signOutTimeEnd;
                        if ((cBTime >= oBTime && cBTime <= oETime) || (cETime >= oBTime && cETime <= oETime)) {
                            $.messager.alert('提示', '该时间段与其他时间段有交叉', 'info');
                            return false;
                        }
                    }
                }
            }
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#interval-dlg').dialog('close'); // close the dialog
                $('#interval-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}



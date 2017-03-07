/**
 *
 */
$(function () {
    loadBtns('regularShift-btns', $('#menuId').val());
    $('#regularShift-dg').datagrid(
        {
            title: '规律班次列表',
            toolbar: '#regularShifts-toolbar',
            striped: true,
            rownumbers: true,
            fitColums: true,
            fit: true,
            singleSelect: true,
            method: 'post',
            url: 'regularShift/regularShiftList',
            onSelect: function (rowIndex, rowData) {
                loadBinding('bindingView-table', rowData, false);
            }
        }
    );

})

function loadBinding(tableName, rowData, isDrop) {
    $.post("regularShift/getBindingByRegularShift",
        {regularShiftId: rowData.id},
        function (result) {
            $('#' + tableName).empty();
            if (rowData.unit == "周") {

                createWeekTable(tableName, rowData.unitCnt, isDrop);
            }
            else {
                createDayTable(tableName, rowData.unitCnt, isDrop);
            }
            bindingToTable(tableName, result);

        }, "json");
}
//第一个参数为tableid，第二个参数为绑定数组。
function bindingToTable(tableName, bindings) {
    var rows = document.getElementById(tableName).rows;
    for (var i = 0; i < bindings.length; i++) {
        var rowIndex = parseInt(i / 7) * 2 + 1;
        var cellIndex = i % 7 + 1;
        var shiftFk = bindings[i].shiftFK;
        var shiftType = bindings[i].shiftType;

        var shiftName = shiftType == "NORMAL" ? bindings[i].shiftName : '';


        var content = '  <div class="item assigned" shiftType=' + shiftType + '  shiftFk=' + shiftFk + ' >' + shiftName + '</div>';
        $(content).appendTo(rows[rowIndex].cells[cellIndex]);
    }


}
// 定义窗口的提交地址
var formUrl = 'regularShift/addRegularShift';
// 打开新建规律班次的窗口

function addRegularShift() {
    $("#regularShift-dlg").dialog("open").dialog('setTitle', '新建规律班次');
    $('#regularShift-form').form('clear');
    $('#round').numberspinner('setValue', 1);
    $('#unit').combobox('setValue', '周');
    loadShift();
    formUrl = 'regularShift/addRegularShift';
}

function changeTable() {
    var round = $('#round').numberspinner('getValue');
    var unit = $('#unit').combobox('getValue');
    if (round && unit) {
        if (unit == "周") {
            createWeekTable('shiftBinding-table', round, true);
        }
        else {
            createDayTable('shiftBinding-table', round,true);
        }
    }
}
// 打开编辑规律班次的窗口
function editRegularShift() {
    var row = $('#regularShift-dg').datagrid('getSelected');
    if (row) {

        $('#regularShift-dlg').dialog('open').dialog('setTitle', '编辑规律班次');
        $('#regularShift-form').form('clear');
        loadShift();
        $('#regularShiftId').val(row.id);
        $('#regularShiftName').val(row.name);
        $('#unit').combobox('setValue', row.unit);
        $('#round').numberspinner('setValue', row.unitCnt);
        loadBinding('shiftBinding-table', row, true);
        $('#regularShift-form').form('validate');
        formUrl = 'regularShift/editRegularShift';
    } else {
        $.messager.alert('提示', '需要选择一个规律班次，才能进行编辑操作。', 'info');
    }

}
// 删除规律班次
function deleteRegularShift() {
    var row = $('#regularShift-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("regularShift/deleteRegularShift", {
                    regularShiftId: row.id
                }, function (result) {
                    if (result.success) {
                        // 成功后刷新表格
                        $('#regularShift-dg').datagrid('reload');
                        $('#bindingView-table').empty();
                    } else {
                        $.messager.alert('操作失败', result.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个规律班次，才能进行删除操作。', 'info');
    }

}
function getRegularShift() {
    var data = {};
    data.id = $('#regularShiftId').val();
    data.name = $('#regularShiftName').val();
    data.unit = $('#unit').combobox('getValue');
    data.unitCnt = $('#round').numberspinner('getValue');
    var shifts = getShifts(data.unitCnt, data.unit);
    data.bindings = shifts;

    return data;
}
// 保存规律班次
function saveRegularShift() {

    if ($('#regularShift-form').form('validate')) {
        var data = getRegularShift();
        var param = {
            'regularShift.id': data.id,
            'regularShift.name': data.name,
            'regularShift.unit': data.unit,
            'regularShift.unitCnt': data.unitCnt
        }
        for (var i = 0; i < data.bindings.length; i++) {
            param["regularShift.bindings[" + i + "].idx"] = data.bindings[i].idx;
            param["regularShift.bindings[" + i + "].dayToBound"] = data.bindings[i].dayToBound;
            param["regularShift.bindings[" + i + "].shiftFK"] = data.bindings[i].shiftFK;
            param["regularShift.bindings[" + i + "].shiftType"] = data.bindings[i].shiftType;
        }

        $.post(formUrl, param,
            function (result) {
                if (result.success) {
                    $('#regularShift-dlg').dialog('close'); // close the dialog
                    $('#regularShift-dg').datagrid('reload');
                    $('#bindingView-table').empty();
                } else {
                    $.messager.alert('保存失败', result.message, 'error');
                }
            }, "json");
    }
}

function loadShift() {
    $('#dragShift').empty();
    $.post("shift/getValidNormal", function (result) {
        for (var i = 0; i < result.length; i++) {
            var dragDiv = '<tr><td><div class="item" shiftFk=' + result[i].id + '>' + result[i].name + '</div></td> </tr>';
            $(dragDiv).appendTo('#dragShift');
        }
        $('.left .item').draggable({
            revert: true,
            proxy: 'clone'
        });
    }, "json");
}

function getRecordString() {
    var shifId = records.shiftFk;
    var shifName = record.shifName;
    var recordString = '<td class="drop"><div class="item" shiftFk=' + shiftFk + '>' + shifName + '</div></td>';
    return recordString;

}

function createWeekTable(tableName, count, isDrop) {
    //当前有多少行
    var currentRowsCount = document.getElementById(tableName).rows.length / 2;
    var addRowsCount = count - currentRowsCount;
    if (addRowsCount > 0) {
        //生成以周为单位的表格
        for (var i = currentRowsCount + 1; i <= count; i++) {
            //添加抬头
            var rowTitle = '<tr><td class="blank"> 周</td><td class="title">星期一</td><td class="title">星期二</td>	<td class="title">星期三</td><td class="title">星期四</td><td class="title">星期五</td>	<td class="title">星期六</td><td class="title">星期日</td></tr>';
            $(rowTitle).appendTo('#' + tableName);

            var dataRow = '<tr class="data">';
            dataRow = dataRow + '<td >' + i + '</td>'
            for (var j = 1; j < 8; j++) {
                if (isDrop) {
                    dataRow += '<td class="drop bgGray"></td>';
                }
                else {
                    dataRow += '<td class="bgGray"></td>';
                }
            }
            dataRow += '</tr>';
            $(dataRow).appendTo('#' + tableName);
        }
    }
    else {
        var cRows = document.getElementById(tableName).rows;
        for (var i = currentRowsCount * 2; i >= count * 2; i--) {
            $(cRows[count * 2]).remove();
        }
    }
    initTable();
}

function getTdCount(tableName) {
    var rows = document.getElementById(tableName).rows;
    var maxValue = 0;
    if (rows.length > 0) {
        var row = rows[rows.length - 2];

        for (var i = row.cells.length - 1; i >= 0; i--) {
            if (row.cells[i].innerText != "") {
                maxValue = parseInt(row.cells[i].innerText);
                break;
            }
        }
    }
    return maxValue;
}

function createDayTable(tableName, count, isDrop) {
    //当前表中的周期数
    var currentTdCount = getTdCount(tableName);

    if (currentTdCount != count) {
        //当前行内容。
        var rows = document.getElementById(tableName).rows
        //当前表行数
        var currentRowsCount = currentTdCount % 7 == 0 ? parseInt(currentTdCount / 7) : parseInt(currentTdCount / 7) + 1;
        //将来表行数
        var rowsCount = count % 7 == 0 ? parseInt(count / 7) : parseInt(count / 7) + 1;
        //添加表格
        if (currentTdCount < count) {
            //这行未满，补充td格。
            var rowRemain = currentTdCount % 7;
            if (rowRemain > 0) {
                //判断要补充几个格
                var fillCount = (7 - rowRemain) < (count - currentTdCount) ? 7 - rowRemain : count - currentTdCount;
                for (var i = 1; i <= fillCount; i++) {

                    //写上title。
                    rows[currentRowsCount * 2 - 2].cells[rowRemain + i].innerText = ++currentTdCount;
                    //改变背景颜色
                    $(rows[currentRowsCount * 2 - 1].cells[rowRemain + i]).addClass('bgGray');
                    if (isDrop) {
                        //td能支持拖动。
                        $(rows[currentRowsCount * 2 - 1].cells[rowRemain + i]).addClass('drop');
                    }
                }
            }
            //填充增加行

            for (var i = currentRowsCount; i < rowsCount; i++) {
                //添加抬头
                var rowTitle = i == 0 ? '<tr> <td class="blank">日</td>' : '<tr> <td class="blank"></td>';
                for (var j = 1; j < 8; j++) {
                    var day = i * 7 + j;
                    var t = '<td class="title"></td>';
                    if (day <= count) {
                        t = '<td class="title">' + day + '</td>';
                    }

                    rowTitle += t;
                }
                rowTitle += ' </tr>';

                $(rowTitle).appendTo('#' + tableName);

                var dataRow = '<tr class="data">';
                dataRow = dataRow + '<td class="blank">  </td>'

                for (var z = 1; z < 8; z++) {
                    var day = i * 7 + z;
                    if (day <= count) {
                        if (isDrop) {
                            dataRow += '<td class="drop bgGray"></td>';
                        }
                        else {
                            dataRow += '<td class="bgGray"></td>';
                        }
                    }
                    else {
                        dataRow += '<td></td>';
                    }
                }
                dataRow += '</tr>';
                $(dataRow).appendTo('#' + tableName);
            }
        }
        //删除表格
        else if (currentTdCount > count) {
            //删除行
            for (var i = currentRowsCount * 2; i >= rowsCount * 2; i--) {
                $(rows[rowsCount * 2]).remove();
            }
            //删除多余td格。
            var rowRemain = count % 7;
            if (rowRemain > 0) {
                //判断要删除几个格
                var deleteCount = rowsCount * 7 - count;
                for (var i = 1; i <= deleteCount; i++) {

                    //title置空。
                    rows[rowsCount * 2 - 2].cells[8 - i].innerText = '';
                    //td能不支持拖动。
                    $(rows[rowsCount * 2 - 1].cells[8 - i]).removeClass('droppable');
                    $(rows[rowsCount * 2 - 1].cells[8 - i]).removeClass('drop');
                    $(rows[rowsCount * 2 - 1].cells[8 - i]).removeClass('bgGray');
                }
            }
        }
    }
    //没有变化，返回
    else {
        return;
    }
    initTable();
}

function menuHandler() {
    var shift = $('.over').children();
    if (shift.length > 0) {
        $(shift[0]).remove();
    }
}

function getShifts(unitCnt, unit) {
    var records = [];
    var shiftTable = document.getElementById('shiftBinding-table')
    //记录数据是第几行。
    var dataRowCount = 0;
    for (var i = 1; i < shiftTable.rows.length; i++) {
        //将dom对象转化为jQuery对象
        var row = $(shiftTable.rows[i]);
        //判断row是否为数据行
        if (row.hasClass('data')) {
            dataRowCount++;
            for (var j = 1; j < shiftTable.rows[i].cells.length; j++) {
                var shiftFK = $($(shiftTable.rows[i].cells[j]).children()[0]).attr('shiftFk');
                var idx = 0;
                var dayToBound = records.length + 1;
                var shiftType = shiftFK == null ? "REST" : "NORMAL";
                if (unit == "周") {
                    idx = dataRowCount;
                    dayToBound = j;
                    unitCnt = unitCnt * 7
                }
                var record = {'idx': idx, 'dayToBound': dayToBound, 'shiftFK': shiftFK, 'shiftType': shiftType}
                if (records.length < unitCnt) {
                    records.push(record);
                }

            }
        }
    }
    return records;

}

function initTable() {
    $('.right td.drop').droppable({
        onDragEnter: function () {
            $(this).addClass('over');
        },
        onDragLeave: function () {
            $(this).removeClass('over');
        },
        onDrop: function (e, source) {
            $(this).removeClass('over');

            if ($(source).hasClass('assigned')) {
                $(this).append(source);
            } else {
                var c = $(source).clone().addClass('assigned');
                $(this).empty().append(c);
                $(this).mouseenter();

            }
        }
    });

    $('#shiftBinding-table td').unbind('contextmenu');
    $('#shiftBinding-table td').unbind('mouseenter');

    $('#shiftBinding-table .drop').bind({
            'contextmenu': function (e) {
                e.preventDefault();
                $('#mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            },
            'mouseenter': function () {
                $('.drop').removeClass('over');
                $(this).addClass('over');
            }
        }
    );

}

$(function () {


    $('.left').droppable({
        accept: '.assigned',
        onDragEnter: function (e, source) {
            $(source).addClass('trash');
        },
        onDragLeave: function (e, source) {
            $(source).removeClass('trash');
        },
        onDrop: function (e, source) {
            $(source).remove();
        }
    });
});
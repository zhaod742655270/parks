/**
 * 人员最新刷卡记录，界面会定时刷新
 * @author ren_xt
 */
$(function () {

    var $dg = $("#empCardEvent-dg");

    //加载数据表格
    $dg.datagrid({
        loadMsg: false,
        title: '进出记录实时列表',
        toolbar: '#empCardEvents-toolbar',
        sortName: 'eventTime',
        sortOrder: 'desc',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'post',
        url: 'accessEvent/accessEventLatestList',
        pagination: true,
        rowStyler: function (index, row) {
            if (row.ioType === "出门") {
                return 'background-color:#FFDBDB';
            }
            else{
                return 'background-color:#EAF2FF';
            }
        }
    });

    //dashboard 显示各种状态信息
    var ss = $('#dashboard').children('.status');

    var refreshStatus = function () {
        //加载状态信息
        $.post('accessEvent/getStatus', function (data) {
            [].forEach.call(ss, function (val) {
                var name = $(val).attr('name');
                $(val).text(data[name]);
            });
        }, 'json');
    };

    //初次加载状态信息
    refreshStatus();
    //后续加载状态信息
    window.setInterval(refreshStatus, 3000);

    $('#autoBtn').bind('click', autoEventClick);
    $('#handBtn').bind('click', loadRecords);
});

/**
 * 重载 Datagrid 的数据
 */
function loadRecords() {
    var param = {
        recordType: $('#recordType').combobox('getValue'),
        deviceType: $('#deviceType').combobox('getValue')
    }
    $("#empCardEvent-dg").datagrid('reload',param);
}

/**
 * 启用/取消定时任务
 */
function autoEventClick() {
    if ($('#isAuto').val() == 'true') {
        $('#isAuto').val('false');
        $('#autoBtn').linkbutton({iconCls: 'icon-ok', text: '自动刷新'});
        window.clearInterval(loadTask);
    }
    else {
        $('#isAuto').val('true');
        $('#autoBtn').linkbutton({iconCls: 'icon-cancel', text: '停止刷新'});
        loadTask = window.setInterval(function () {
            loadRecords()
        }, 3000);

    }
}

var loadTask = window.setInterval(function () {
    loadRecords()
}, 3000);



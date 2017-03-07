/**
 *
 */
$(function () {

    $('#empCardEvent-dg').datagrid(
        {
            title: '进出记录列表',
            toolbar: '#empCardEvents-toolbar',
            sortName: 'eventTime',
            sortOrder: 'desc',
            striped: true,
            rownumbers: true,
            fitColums: true,
            fit: true,
            singleSelect: true,
            method: 'post',
            pagination: true,
            rowStyler: function (index, row) {
                if (row.ioType === "出门") {
                    return 'background-color:#FFF3F3';
                }
                else {
                    return 'background-color:#EAF2FF';
                }
            },
            onDblClickRow: function (rowIndex, rowData) {
                var imgUrl = rowData.bigPhotoPath;
                if (imgUrl != "") {
                    $('#capturePhoto').attr('src', imgUrl);
                    $('#photoWindows').window('open');
                }
                else {
                    $.messager.alert('提示', '该记录没有抓拍照片。', 'info');
                }
            }
        }
    );

//添加校验
    var $beginTime = $("#beginTime");
    $beginTime.datetimebox({
        required: true,
        validType: 'dtFormat'
    });

    var $endTime = $("#endTime");
    $endTime.datetimebox({
        required: true,
        validType: ["dtFormat", "lateThanBegin['#beginTime']"]
    });
    setDefaultTime();

})
//设置默认时间
function setDefaultTime() {
    var date = new Date().Format("yyyy-MM-dd");
    var beginTime = date + " 00:00:00";
    var endTime = date + " 23:59:59";
    $('#beginTime').datetimebox("setValue", beginTime);
    $('#endTime').datetimebox("setValue", endTime);
}


/**
 * 查询
 */
function queryHandler() {
    //必须校验通过才能查询
    if (!$('#cardEvent-form').form('validate')) {
        return;
    }
    var params = {
        deptIdCheck: $('#deptIdCheck').prop("checked"),//string "false" -> boolean false.
        deptId: $('#deptId').combobox('getValue'),

        //doorNameCheck:$('#doorNameCheck').prop("checked"),
        //doorName: $("#doorName").textbox('getText'),

        beginTime: $("#beginTime").datetimebox('getValue'),
        endTime: $("#endTime").datetimebox('getValue'),

        personNameCheck: $('#personNameCheck').prop("checked"),
        personName: $("#personName").val(),

        cardNoCheck: $('#cardNoCheck').prop("checked"),
        cardNo: $("#cardNo").val(),

        plateNoCheck: $('#plateNoCheck').prop("checked"),
        plateNo: $("#plateNo").val(),

        doorNameCheck: $('#doorNameCheck').prop("checked"),
        doorName: $("#doorName").val(),

        recordType: $('#recordType').combobox('getValue')
    };
    //load new data from server
    $('#empCardEvent-dg').datagrid({
        url: 'accessEvent/accessEventList',
        queryParams: params
    });
}
function exportExcel() {
    $.messager.confirm('确认', '导出的数据是按照检索条件的，是否继续导出？', function (r) {
        if (r) {
            $('#cardEvent-form').submit();
        }
    });
}
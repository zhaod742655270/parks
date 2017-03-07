/**
 *
 */


$(function () {

    $('#carCardEvent-dg').datagrid(
        {
            title: '车辆进出记录列表',
            toolbar: '#carCardEvents-toolbar',
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
            view: detailview,
            detailFormatter: function (index, row) {
                return '<div style="padding:2px"><table class="ddv"></table></div>';
            },
            onExpandRow: function (index, row) {
                var ddv = $(this).datagrid('getRowDetail', index).find('table.ddv');
                ddv.datagrid({
                    url: 'accessEvent/getAccompanyList?passId=' + row.passId,
                    fitColumns: true,
                    singleSelect: true,
                    rownumbers: true,
                    loadMsg: '',
                    height: 'auto',
                    columns: [[
                        {field: 'plateNo', title: '车牌号'},
                        {field: 'cardPropDes', title: '载体类型'},
                        {field: 'personName', title: '姓名'},
                        {field: 'deptName', title: '部门'},
                        {field: 'identifyResult', title: '认证结果', width: 100},
                        {field: 'isTriggerPassDes', title: '是否导致抬杆', width: 100}

                    ]],
                    onResize: function () {
                        $('#carCardEvent-dg').datagrid('fixDetailRowHeight', index);
                    },
                    onLoadSuccess: function () {
                        setTimeout(function () {
                            $('#carCardEvent-dg').datagrid('fixDetailRowHeight', index);
                        }, 0);
                    }
                });
                $('#carCardEvent-dg').datagrid('fixDetailRowHeight', index);
            },
            onDblClickRow: function (rowIndex, rowData) {
                var bigImgUrl = rowData.bigPhotoPath;
                var smallImgUrl = rowData.smallPhotoPath;
                if (bigImgUrl != "" &&smallImgUrl!="") {
                    $('#bigPhoto').attr('src', bigImgUrl);
                    $('#smallPhoto').attr('src', smallImgUrl);
                    $('#photoWindows').window('open');
                }
                else{
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
    $('#carCardEvent-dg').datagrid({
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
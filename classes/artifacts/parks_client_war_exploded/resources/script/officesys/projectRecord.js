
$(function () {
    loadBtns('projectRecord-btns', $('#menuId').val());
    $('#record-dg').datagrid({
        nowrap: false,
        sortName: 'sheetName',
        sortOrder: 'desc',
        title: '项目备案',
        toolbar: '#record-toolbar',
        method: 'post',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        pagination: true,
        url:'projectRecord/projectRecordList',
        onRowContextMenu:onRowContextMenu,
        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].bidding==1){
                    data.rows[i].biddingName = "是";
                }else if(data.rows[i].bidding==2){
                    data.rows[i].biddingName = "否";
                }else{
                    data.rows[i].biddingName = "";
                }

                if (data.rows[i].winBidding==1){
                    data.rows[i].winBiddingName = "中";
                }else if(data.rows[i].winBidding==2){
                    data.rows[i].winBiddingName = "不中";
                }else if(data.rows[i].winBidding==3){
                    data.rows[i].winBiddingName = "留标";
                }else if(data.rows[i].winBidding==4){
                    data.rows[i].winBiddingName = "延期";
                }else if(data.rows[i].winBidding==5){
                    data.rows[i].winBiddingName = "其它";
                }else{
                    data.rows[i].winBiddingName = "";
                }

                if (data.rows[i].recordDate=="1900-01-01"){
                    data.rows[i].recordDate = "";
                }

                if (data.rows[i].biddingDate=="1900-01-01"){
                    data.rows[i].biddingDate = "";
                }

            }
            return data;
        }

    });

    $('#expenditure-dg').datagrid({
        toolbar:'#expenditure-toolbar',
        method:'post',
        nowrap:true,
        sortName:'examineDate',
        sortOrder:'desc',
        striped:true,
        rownumbers:true,
        fitColumns:true,
        fit:true,
        singleSelect:true,
        pagination: true,
        url:'expenditure/expenditureList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'recordPersonName',title:'经办人'},
            {field:'recordDepartment',title:'申请部门'},
            {field:'projectRecordName',title:'项目名称',width:100}
        ]],
        columns:[[
            {field:'expendContent',title:'项目支出内容'},
            {field:'examinePerson',title:'批准人'},
            {field:'examineDate',title:'批准日期'},
            {field:'entertainCost',title:'招待费用'},
            {field:'trafficCost',title:'交通费用'},
            {field:'travelCost',title:'差旅费'},
            {field:'giftCost',title:'礼品费'},
            {field:'oilCost',title:'油料费'},
            {field:'officeCost',title:'办公费'},
            {field:'teleCost',title:'通讯费'},
            {field:'travelSubsidy',title:'差旅补助费'},
            {field:'otherCost',title:'其他费用'},
            {field:'totalCost',title:'费用合计'},
            {field:'note',title:'备注'}
        ]],
        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].examainDate == "1900-01-01") {
                    data.rows[i].examainDate = "";
                }
            }
            return data;
        }
    });

    function onRowContextMenu (e, index, node) {
        e.preventDefault();
        var row = $('#record-dg').datagrid('getSelected');
        if (row) {
            $('#rightMenu').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    }

    $('#sheetNameQuery').combobox({
        data: [{"id": "2020", "text": "2020"},{"id": "2019", "text": "2019"}, {"id": "2018", "text": "2018"},
            {"id": "2017", "text": "2017"}, {"id": "2016", "text": "2016"}, {"id": "2015", "text": "2015"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#typeQuery').combobox({
        data: [{"id": "自主", "text": "自主"}, {"id": "合作1", "text": "合作1"}, {"id": "合作2", "text": "合作2"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#sheetName').combobox({
        data: [{"id": "2020", "text": "2020"},{"id": "2019", "text": "2019"}, {"id": "2018", "text": "2018"},
            {"id": "2017", "text": "2017"}, {"id": "2016", "text": "2016"}, {"id": "2015", "text": "2015"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#type').combobox({
        data: [{"id": "自主", "text": "自主"}, {"id": "合作1", "text": "合作1"}, {"id": "合作2", "text": "合作2"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#bidding').combobox({
        data: [{"id": "1", "text": "是"}, {"id": "2", "text": "否"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#winBidding').combobox({
        data: [{"id": "1", "text": "中"}, {"id": "2", "text": "不中"}, {"id": "3", "text": "留标"},
            {"id": "4", "text": "延期"}, {"id": "5", "text": "其它"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#recordPerson').combobox({
        required:true,
        url:'expenditure/getRecordPersonList',
        valueField:'id',
        textField:'text'
    });
    
    $('#examinePerson').combobox({
        data: [{"id": "韩小琴", "text": "韩小琴"},{"id": "于宏伟", "text": "于宏伟"},{"id": "李倩", "text": "李倩"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#projectRecord').combobox({
        url:'expenditure/getProjectList',
        valueField:'id',
        textField:'text',
        filter: function(q, row){
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;
        }
    });

});


// 定义窗口的提交地址
var formUrl = 'projectRecord/addProjectRecord';


function recordQuery(){
    var params = {
        sheetNameQuery: $('#sheetNameQuery').combobox('getValue'),
        typeQuery: $('#typeQuery').combobox('getValue'),
        nameQuery: $('#nameQuery').val()
    };
    $('#record-dg').datagrid({
        url: 'projectRecord/projectRecordList',
        queryParams: params
    });
}

//增加项目备案的窗口
function addProjectRecord(){
    $("#recordDlg").dialog("open").dialog('setTitle', '新建项目备案');
    $('#addRecord').form('clear');
    formUrl = 'projectRecord/addProjectRecord';

}

// 编辑项目备案的窗口
function editProjectRecord() {
    var row = $('#record-dg').datagrid('getSelected');
    if (row) {
        formUrl = 'projectRecord/editProjectRecord';
        $('#addRecord').form('clear');
        $('#recordDlg').dialog('open').dialog('setTitle', '编辑项目备案');
        $('#addRecord').form('load', row);
        $('#recordID').val(row.id);
        $('#sheetName').combobox('setValue', row.sheetName);
        $('#type').combobox('setValue', row.type);
        $('#recordSN').val(row.recordSN);
        $('#name').val(row.name);
        $('#contents').val(row.contents);
        $('#place').val(row.place);
        $('#budget').val(row.budget);
        $('#employer').val(row.employer);
        $('#connection').val(row.connection);
        $('#manager').val(row.manager);
        $('#recordDate').datebox("setValue", row.recordDate);
        $('#biddingDate').datebox("setValue", row.biddingDate);
        $('#bidding').combobox("setValue",row.bidding);
        $('#winBidding').combobox("setValue",row.winBidding);
        $('#amount').val(row.amount);
        $('#bidSubject').val(row.bidSubject);
        $('#bidBond').val(row.bidBond);
        $('#note').val(row.note);

    } else {
        $.messager.alert('提示', '需要选择一个清单，才能进行编辑操作。', 'info');
    }

}

//保存按键
function saveProjectRecord(){
    $('#addRecord').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#recordDlg').dialog('close'); // close the dialog
                $('#record-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

// 删除项目备案
function deleteProjectRecord() {
    var row = $('#record-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("projectRecord/deleteProjectRecord", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#record-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个清单，才能进行删除操作。', 'info');
    }

}

//打开经费支出列表
function openExpenditureList(){
    var selectRow = $('#record-dg').datagrid('getSelected');
    if(selectRow) {
        $('#expenditure-dg').datagrid({
            queryParams: {
                projectQuery:selectRow.id
            }
        });
        $('#expenditureDgd').dialog('open').dialog('setTitle', '经费支出列表');
    } else{
        $.messager.alert('提示', '需要选择一个项目备案记录，才能查看经费支出列表。', 'info');
    }
}

//添加经费支出
function addExpenditure(){
    $('#addExpenditure').form('clear');

    var selectRow = $('#record-dg').datagrid('getSelected');
    if(selectRow) {
        var today = new Date();
        $('#examineDate').datebox('setValue', today.toLocaleDateString());       //批准日期
        /*$.ajax({
            url: '../managesys/user/getCurrentUser',
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result) {
                    $('#recordPerson').combobox('setValue', result.userId);        //经办人
                    $('#recordPerson').combobox('setText', result.userName);
                }
            }
        });*/
        $('#projectRecord').combobox('setValue',selectRow.id);          //项目名称
        $('#projectRecord').combobox('setText',selectRow.name);
        
        $('#recordDepartment').val("市场部");
        $('#expendContent').val("差旅");
        
        $('#examinePerson').combobox('setValue',"韩小琴");
        $('#examinePerson').combobox('setText',"韩小琴");

        $("#expenditureDlg").dialog("open").dialog('setTitle', '新建经费支出记录');
    }else{
        $.messager.alert('提示', '需要选择一个项目备案记录，才能添加经费支出。', 'info');
    }
}

//保存经费支出
function saveExpenditure() {
    $('#addExpenditure').form('submit', {
        url: 'expenditure/addExpenditure',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $.messager.alert('提示', '添加成功', 'info');
                $('#expenditureDlg').dialog('close');
                $('#expenditure-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });
}

//自动计算总费用
function getAllCost(){
    var entertainCost = $('#entertainCost').numberbox('getValue') * 1;
    var trafficCost = $('#trafficCost').numberbox('getValue') * 1;
    var travelCost = $('#travelCost').numberbox('getValue') * 1;
    var giftCost = $('#giftCost').numberbox('getValue') * 1;
    var oilCost = $('#oilCost').numberbox('getValue') * 1;
    var officeCost = $('#officeCost').numberbox('getValue') * 1;
    var teleCost = $('#teleCost').numberbox('getValue') * 1;
    var travelSubsidy = $('#travelSubsidy').numberbox('getValue') * 1;
    var otherCost = $('#otherCost').numberbox('getValue') * 1;
    var allCost = entertainCost + trafficCost + travelCost + giftCost + 
        oilCost + officeCost + teleCost + travelSubsidy + otherCost;
    $('#totalCost').numberbox('setValue',allCost);
}





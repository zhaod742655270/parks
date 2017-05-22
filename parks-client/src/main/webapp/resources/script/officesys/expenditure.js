/**
 * Created by Zhao_d on 2017/2/23.
 */

$(function(){
    loadBtns('expenditure-btn', $('#menuId').val());

    $('#expenditure-dg').datagrid({
        title:'经费支出列表',
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
            {field:'projectRecordName',title:'项目名称',width:80}
        ]],
        columns:[[
            {field:'expendContent',title:'项目支出内容',width:80},
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
            {field:'note',title:'备注',width:80}
        ]],
        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].examineDate == "1900-01-01") {
                    data.rows[i].examineDate = "";
                }
            }
            return data;
        }
    });

    $('#recordPerson').combobox({
        required:true,
        url:'expenditure/getRecordPersonList',
        valueField:'id',
        textField:'text'
    });

    $('#recordPersonQuery').combobox({
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

    $('#projectQuery').combobox({
        url:'expenditure/getProjectList',
        valueField:'id',
        textField:'text',
        filter: function(q, row){
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;
        }
    });
});

function expenditureQuery(){
    var query = {
        recordPersonQuery:$('#recordPersonQuery').combobox('getValue'),
        projectQuery:$('#projectQuery').combobox('getValue'),
        examineDateBeg:$('#examineDateBeg').datebox('getValue'),
        examineDateEnd:$('#examineDateEnd').datebox('getValue')
    };
    $('#expenditure-dg').datagrid({
        queryParams:query
    });
}

/**
 * 修改经费支出信息
 */
function editExpenditure(){
    formUrl = "expenditure/editExpenditure";
    var row = $('#expenditure-dg').datagrid('getSelected');
    if(row){
        $('#addExpenditure').form('clear');

        $('#id').val(row.id);
        $('#recordPerson').combobox('setValue',row.recordPersonId);
        $('#recordPerson').combobox('setText',row.recordPersonName);
        $('#recordDate').datebox('setValue',row.recordDate);
        $('#recordDepartment').val(row.recordDepartment);
        $('#projectRecord').combobox('setValue',row.projectRecordId);
        $('#projectRecord').combobox('setText',row.projectRecordName);
        $('#expendContent').val(row.expendContent);
        $('#examinePerson').combobox('setValue',row.examinePerson);
        $('#examinePerson').combobox('setText',row.examinePerson);
        $('#examineDate').datebox('setValue', row.examineDate);
        $('#entertainCost').textbox('setValue',row.entertainCost);
        $('#trafficCost').textbox('setValue',row.trafficCost);
        $('#travelCost').textbox('setValue',row.travelCost);
        $('#giftCost').textbox('setValue',row.giftCost);
        $('#oilCost').textbox('setValue',row.oilCost);
        $('#officeCost').textbox('setValue',row.officeCost);
        $('#teleCost').textbox('setValue',row.teleCost);
        $('#travelSubsidy').textbox('setValue',row.travelSubsidy);
        $('#otherCost').textbox('setValue',row.otherCost);
        $('#totalCost').textbox('setValue',row.totalCost);
        $('#note').textbox('setValue',row.note);

        $('#expenditureDlg').dialog('open').dialog('setTitle', '修改经费支出信息');
    }else{
        $.messager.alert('提示', '需要选择一条经费支出信息，才能进行编辑操作。', 'info');
    }
}

/**
 * 保存修改
 */
function saveExpenditure(){
    $('#addExpenditure').form('submit',{
        url:formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success:function(result){
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#expenditureDlg').dialog('close');
                $('#expenditure-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

/**
 * 删除
 */
function deleteExpenditure(){
    var row = $('#expenditure-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("expenditure/deleteExpenditure",{
                    id:row.id
                },function(data){
                    var result = jQuery.parseJSON(data);
                    if (result.success) {
                        // 成功后刷新表格
                        $('#expenditure-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                });
            }
        });
    }else{
        $.messager.alert('提示', '需要选择一条经费支出信息，才能进行删除操作。', 'info');
    }
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



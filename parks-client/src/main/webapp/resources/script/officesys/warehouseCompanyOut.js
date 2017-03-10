/**
 * Created by Zhao_d on 2016/12/30.
 */
$(function(){
    loadBtns('company-btn',$('#menuId').val());
    $('#company-dg').datagrid({
        title:'客户信息列表',
        toolbar:'#company-toolbar',
        method:'post',
        nowrap:true,
        sortName:'name',
        sortOrder:'asc',
        striped:true,
        rownumbers:true,
        fitColumns:false,
        fit:true,
        singleSelect:true,
        pagination: true,
        url:'warehouseCompanyOut/warehouseCompanyList',
        frozenColumns:[[
            {field:'id',title:'ID',hidden:true},
            {field:'name',title:'名称'}
        ]],
        columns:[[
            {field:'contact',title:'联系人'},
            {field:'phone',title:'电话'},
            {field:'address',title:'地址',width:100},
            {field:'postcode',title:'邮编'},
            {field:'fax',title:'传真'},
            {field:'taxSign',title:'税号'},
            {field:'bank',title:'开户行'},
            {field:'accountNum',title:'账号'},
            {field:'companyDesc',title:'描述',width:120}
        ]]
    });
});


/**
 * 客户查询
 */
function companyQuery(){
    var query = {
        nameQuery:$('#nameQuery').val()
    };
    $('#company-dg').datagrid({
        queryParams:query
    });
}


var formUrl = "";

/**
 * 新增客户
 */
function addCompany(){
    formUrl = "warehouseCompanyOut/addWarehouseCompany";
    $('#addCompany').form('clear');
    $('#company-dlg').dialog('open').dialog('setTitle', '新增客户信息');
}

/**
 * 修改客户信息
 */
function editCompany(){
    formUrl = "warehouseCompanyOut/editWarehouseCompany";
    var row = $('#company-dg').datagrid('getSelected');
    if(row){
        $('#addCompany').form('clear');
        $('#id').val(row.id);
        $('#name').val(row.name);
        $('#contact').val(row.contact);
        $('#phone').val(row.phone);
        $('#postcode').val(row.postcode);
        $('#fax').val(row.fax);
        $('#taxSign').val(row.taxSign);
        $('#bank').val(row.bank);
        $('#accountNum').val(row.accountNum);
        $('#address').val(row.address);
        $('#desc').textbox('setValue',row.companyDesc);

        $('#company-dlg').dialog('open').dialog('setTitle', '修改客户信息');
    }else{
        $.messager.alert('提示', '需要选择一条客户信息，才能进行编辑操作。', 'info');
    }
}

/**
 * 保存修改
 */
function saveCompany(){
    $('#addCompany').form('submit',{
        url:formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success:function(result){
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#company-dlg').dialog('close');
                $('#company-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

/**
 * 删除
 */
function deleteCompany(){
    var row = $('#company-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("warehouseCompanyOut/deleteWarehouseCompany",{
                    id:row.id
                },function(data){
                    var result = jQuery.parseJSON(data);
                    if (result.success) {
                        // 成功后刷新表格
                        $('#company-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                });
            }
        });
    }else{
        $.messager.alert('提示', '需要选择一条客户信息，才能进行删除操作。', 'info');
    }
}
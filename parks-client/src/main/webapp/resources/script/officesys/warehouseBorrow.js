/**
 * Created by Zhao_d on 2016/1/17.
 */
$(function(){
    loadBtns('warehouse-btn', $('#menuId').val());
    $('#warehouse-dg').datagrid({
        title:'借用信息列表',
        toolbar:'#warehouse-toolbar',
        method:'post',
        nowrap:true,
        sortName:'number',
        sortOrder:'desc',
        striped:true,
        rownumbers:true,
        fitColumns:false,
        fit:true,
        url:'warehouseBorrow/warehouseBorrowList',
        singleSelect:true,
        pagination: true,
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'number',title:'借用编号'},
            {field:'borrowPersonName',title:'借用人'},
            {field:'borrowDate',title:'借用日期'},
            {field:'state',title:'状态'}
        ]],
        columns:[[
            {field:'productName',title:'借用物品'},
            {field:'quantity',title:'借用数量'},
            {field:'productModelNumber',title:'物品型号'},
            {field:'productSpecifications',title:'物品封装'},
            {field:'productUnit',title:'物品单位'},
            {field:'backDate',title:'归还日期'},
            {field:'note',title:'备注'}
        ]],
        loadFilter:function(data){
            for(var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].borrowDate == "1900-01-01"){
                    data.rows[i].borrowDate = "";
                }
            }
            return data;
        }
    });
    
    $('#borrowPerson').combotree({
        required:true,
        url:'warehouseBorrow/getUserList',
        valueField:'id',
        textField:'text'
    });

    $('#borrowPersonQuery').combobox({
        url:'warehouseBorrow/getUserList',
        valueField:'id',
        textField:'text'
    });
    
    $('#stateQuery').combobox({
        data:[{"id": "未归还", "text": "未归还"}, {"id": "已归还", "text": "已归还"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#warehouseProduct').combobox({
        required:true,
        url:'warehouseBorrow/getProductList',
        valueField:'id',
        textField:'text'
    });
});

function warehouseBorrowQuery(){
    var query = {
        borrowPersonQuery:$('#borrowPersonQuery').combobox('getValue'),
        stateQuery:$('#stateQuery').combobox('getValue'),
        borrowDateBegQuery:$('#borrowDateBegQuery').datebox('getValue'),
        borrowDateEndQuery:$('#borrowDateEndQuery').datebox('getValue')
    };
    $('#warehouse-dg').datagrid({
        queryParams:query
    });
}

var formUrl = "";

/**
 * 新增借用记录
 */
function addWarehouseBorrow(){
    formUrl = "warehouseBorrow/addWarehouseBorrow";
    $('#addWarehouse').form('clear');
    var today = new Date();
    $('#borrowDate').datebox('setValue',today.toLocaleDateString());

    $.ajax({
        url:'warehouseBorrow/getNewNumber',
        dataType:'json',
        success:function(number){
            $('#number').textbox('setValue',number);        //自动添加编号
        }
    });

    $('#warehouseDlg').dialog('open').dialog('setTitle', '新增借用记录');
}

var oldQuantity = 0;

/**
 * 修改借用信息
 */
function editWarehouseBorrow(){
    formUrl = "warehouseBorrow/editWarehouseBorrow";
    var row = $('#warehouse-dg').datagrid('getSelected');
    if(row) {
        $('#id').val(row.id);
        $('#number').textbox('setValue',row.number);
        $('#borrowPerson').combobox('setValue',row.borrowPersonId);
        $('#borrowPerson').combobox('setText',row.borrowPersonName);
        $('#borrowDate').datebox('setValue',row.borrowDate);
        $('#warehouseProduct').combobox('setValue',row.productId);
        $('#warehouseProduct').combobox('setText',row.productName);
        $('#productModelNumber').val(row.productModelNumber);
        $('#productSpecifications').val(row.productSpecifications);
        $('#productUnit').val(row.productUnit);
        $('#quantity').textbox('setValue',row.quantity);
        $('#note').textbox('setValue',row.note);

        oldQuantity = row.quantity;

        $('#warehouseDlg').dialog('open').dialog('setTitle', '修改借用信息');
    }else{
        $.messager.alert('提示', '需要选择一条借用信息，才能进行编辑操作。', 'info');
    }
}
/**
 * 保存修改
 */
function saveWarehouseBorrow(){
    var productId = $('#warehouseProduct').combobox('getValue');
    var quantity = $('#quantity').textbox('getValue');
    //判断借用数量是否有效
    $.post("warehouseBorrow/getProductInfo",{
        productId:productId
    },function(result){
        var data = jQuery.parseJSON(result);
        if(data.quantityUse < (quantity - oldQuantity)){
            $.messager.confirm('确认','库存中的可用数量少于此次借用数量，是否确定保存？',function(r){
                if(r){
                    //保存数据
                    $('#addWarehouse').form('submit', {
                        url: formUrl,
                        onSubmit: function () {
                            return $(this).form('validate');
                        },
                        success: function (result) {
                            var data = jQuery.parseJSON(result);
                            if (data.success) {
                                $('#warehouseDlg').dialog('close');
                                $('#warehouse-dg').datagrid('reload');
                            } else {
                                $.messager.alert('操作失败', data.message, 'error');
                            }
                        }
                    });
                }
            });
        }else{
            //保存数据
            $('#addWarehouse').form('submit', {
                url: formUrl,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var data = jQuery.parseJSON(result);
                    if (data.success) {
                        $('#warehouseDlg').dialog('close');
                        $('#warehouse-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }
            });
        }
    });
}


/**
 * 删除
 */
function deleteWarehouseBorrow(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("warehouseBorrow/deleteWarehouseBorrow",{
                    id:row.id
                },function(data){
                    var result = jQuery.parseJSON(data);
                    if (result.success) {
                        // 成功后刷新表格
                        $('#warehouse-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                });
            }
        });
    }else{
        $.messager.alert('提示', '需要选择一条借用记录，才能进行删除操作。', 'info');
    }
}

function backProduct(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    $.post("warehouseBorrow/backProduct",{
        id:row.id
    },function(data){
        var result = jQuery.parseJSON(data);
        if (result.success) {
            // 成功后刷新表格
            $('#warehouse-dg').datagrid('reload');
        } else {
            $.messager.alert('操作失败', data.message, 'error');
        }
    });
}

function onSelectProduct(record){
    if(record.text != ""){
        $.post("warehouseBorrow/getProductInfo",{
            productId:record.value
        },function(result){
            var data = jQuery.parseJSON(result);
            $('#productModelNumber').val(data.modelNumber);
            $('#productSpecifications').val(data.specifications);
            $('#productUnit').val(data.unit);
        });
    }
}

function exportExcel(){
    $.messager.confirm('确认','是否将列表导出为Excel文件',function(r){
        if(r)
        {
            $('#query-form').form({
                queryParams:{
                    borrowPersonQuery:$('#borrowPersonQuery').combobox('getValue'),
                    stateQuery:$('#stateQuery').combobox('getValue'),
                    borrowDateBegQuery:$('#borrowDateBegQuery').datebox('getValue'),
                    borrowDateEndQuery:$('#borrowDateEndQuery').datebox('getValue')
                }
            });
            $('#query-form').form('submit',{
                url:"warehouseBorrow/exportExcel",
                method:"post",
                success:function(data){
                    var result = jQuery.parseJSON(data);
                    if(!result.success){
                        $.message.alert("Excel导出失败",result.message,'error');
                    }
                }
            });
        }
    });
}





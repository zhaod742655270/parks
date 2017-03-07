/**
 * Created by Zhao_d on 2016/12/30.
 */
$(function(){
   loadBtns('product-btn',$('#menuId').val());
    $('#product-dg').datagrid({
        title:'货品信息列表',
        toolbar:'#product-toolbar',
        method:'post',
        nowrap:true,
        sortName:'productType',
        sortOrder:'asc',
        striped:true,
        rownumbers:true,
        fitColumns:false,
        fit:true,
        singleSelect:true,
        pagination: true,
        url:'warehouseProduct/warehouseProductList',
        frozenColumns:[[
            {field:'id',title:'ID',hidden:true},
            {field:'name',title:'名称'}
        ]],
        columns:[[
            {field:'modelNumber',title:'型号'},
            {field:'specifications',title:'封装'},
            {field:'productType',title:'分类'},
            {field:'brand',title:'品牌'},
            {field:'unit',title:'单位'},
            {field:'productDesc',title:'描述',width:120}
        ]]
    });

    $('#productTypeQuery').combobox({
        data: [{"id": "原材料", "text": "原材料"}, {"id": "成品", "text": "成品"}, {"id": "半成品", "text": "半成品"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#productType').combobox({
        data: [{"id": "原材料", "text": "原材料"}, {"id": "成品", "text": "成品"}, {"id": "半成品", "text": "半成品"}],
        valueField: 'id',
        textField: 'text'
    });
});

/**
 * 货品查询
 */
function productQuery(){
    var query = {
        nameQuery:$('#nameQuery').val(),
        productTypeQuery:$('#productTypeQuery').combobox('getValue'),
        brandQuery:$('#brandQuery').val()
    };
    $('#product-dg').datagrid({
        queryParams:query
    });
}

var formUrl = "";

/**
 * 新增货品
 */
function addProduct(){
    formUrl = "warehouseProduct/addWarehouseProduct";
    $('#addProduct').form('clear');
    $('#product-dlg').dialog('open').dialog('setTitle', '新增货品信息');
}

/**
 * 修改货品信息
 */
function editProduct(){
    formUrl = "warehouseProduct/editWarehouseProduct";
    var row = $('#product-dg').datagrid('getSelected');
    if(row){
        $('#addProduct').form('clear');
        $('#id').val(row.id);
        $('#name').val(row.name);
        $('#modelNumber').val(row.modelNumber);
        $('#specifications').val(row.specifications);
        $('#fullName').val(row.fullName);
        $('#productType').combobox('setValue',row.productType);
        $('#brand').val(row.brand);
        $('#unit').val(row.unit);
        $('#productDesc').textbox('setValue',row.productDesc);

        $('#product-dlg').dialog('open').dialog('setTitle', '修改货品信息');
    }else{
        $.messager.alert('提示', '需要选择一条货品信息，才能进行编辑操作。', 'info');
    }
}

/**
 * 保存修改
 */
function saveProduct(){
    $('#addProduct').form('submit',{
        url:formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success:function(result){
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#product-dlg').dialog('close');
                $('#product-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

/**
 * 删除
 */
function deleteProduct(){
    var row = $('#product-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("warehouseProduct/deleteWarehouseProduct",{
                    id:row.id
                },function(data){
                    var result = jQuery.parseJSON(data);
                    if (result.success) {
                        // 成功后刷新表格
                        $('#product-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                });
            }
        });
    }else{
        $.messager.alert('提示', '需要选择一条货品信息，才能进行删除操作。', 'info');
    }
}
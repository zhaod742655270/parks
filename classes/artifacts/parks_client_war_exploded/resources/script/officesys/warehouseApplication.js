/**
 * Created by Zhao_d on 2017/1/23.
 */
$(function() {
    loadBtns('warehouse-btn', $('#menuId').val());
    $('#warehouse-dg').datagrid({
        title: '申请单列表',
        toolbar: '#warehouse-toolbar',
        method: 'post',
        nowrap: true,
        sortName: 'number',
        sortOrder: 'desc',
        striped: true,
        rownumbers: true,
        fitColumns: false,
        fit: true,
        singleSelect: true,
        pagination: true,
        url: 'warehouseApplication/applicationList',
        frozenColumns: [[
            {field: 'id', title: 'ID', align: 'left', hidden: true},
            {field: 'number', title: '申请单号'},
            {field:'name',title:'申请单名称'},
            {field:'type',title:'类型'}
        ]],
        columns: [[
            {field: 'recordPersonName', title: '录入人'},
            {field: 'recordDate', title: '录入日期'},
            {field: 'note', title: '备注', width: 80}
        ]],
        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].recordDate == "1900-01-01") {
                    data.rows[i].recordDate = "";
                }
            }
            return data;
        },
        //双击打开货品清单
        onDblClickRow: openProduct
    });

    $('#product-dg').datagrid({
        toolbar: '#product-toolbar',
        method: 'post',
        nowrap: true,
        sortName: 'SND',
        sortOrder: 'asc',
        striped: true,
        rownumbers: true,
        fitColumns: false,
        fit: true,
        singleSelect: true,
        pagination: true,
        url: 'warehouseApplication/applicationProList',
        frozenColumns: [[
            {field: 'id', title: 'ID', align: 'left', hidden: true},
            {field: 'parentIdFK', hidden: true},
            {field:'SND',title:'序号'},
            {field: 'productName', title: '名称'}
        ]],
        columns: [[
            {field: 'productModelNumber', title: '型号'},
            {field: 'productSpecifications', title: '封装'},
            {field: 'productNum', title: '生产任务单号', width: 100},
            {field:'productBrand',title:'品牌'},
            {field: 'quantity', title: '数量'},
            {field:'quantityInput',title:'出入库数量'},
            {field: 'productUnit', title: '单位'},
            {field: 'note', title: '备注', width: 100}
        ]]
    });

    $('#type').combobox({
        data: [{"id": "入库", "text": "入库"}, {"id": "出库", "text": "出库"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#typeQuery').combobox({
        data: [{"id": "入库", "text": "入库"}, {"id": "出库", "text": "出库"}],
        valueField: 'id',
        textField: 'text'
    });
});

function warehouseQuery(){
    var query = {
        nameQuery:$('#nameQuery').val(),
        typeQuery:$('#typeQuery').combobox('getValue'),
        recordDateBegQuery:$('#recordDateBegQuery').datebox('getValue'),
        recordDateEndQuery:$('#recordDateEndQuery').datebox('getValue')
    };
    $('#warehouse-dg').datagrid({
        queryParams:query
    });
}

var formUrl = "";

/**
 * 修改申请单
 */
function editWarehouseApplication(){
    formUrl = "warehouseApplication/editWarehouseApplication";
    var selectRow = $('#warehouse-dg').datagrid('getSelected');
    if(selectRow){
        $('#addWarehouse').form('clear');
        $('#id').val(selectRow.id);
        $('#number').textbox('setValue',selectRow.number);
        $('#name').textbox('setValue',selectRow.name);
        $('#type').combobox('setValue',selectRow.type);
        $('#note').textbox('setValue',selectRow.note);

        $('#warehouseDlg').dialog('open').dialog('setTitle','修改申请单信息');
    }else{
        $.messager.alert('提示', '需要选择一条申请单记录，才能进行编辑操作。', 'info');
    }
}

/**
 * 保存申请单
 */
function saveWarehouseApplication(){
    $('#addWarehouse').form('submit',{
        url:formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success:function(result){
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#warehouseDlg').dialog('close');
                $('#warehouse-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

/**
 * 删除申请单
 */
function deleteWarehouseApplication(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("warehouseApplication/deleteWarehouseApplication",{
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
        $.messager.alert('提示', '需要选择一条申请单记录，才能进行删除操作。', 'info');
    }
}


/**
 * 打开申请单货品界面
 */
function openProduct(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    if (row) {
        $('#product-dg').datagrid({
            queryParams: {
                id:row.id
            }
        });
        $('#productDgd').dialog('open').dialog('setTitle', '申请单货品列表');
    } else{
        $.messager.alert('提示', '需要选择一个申请单记录，才能查看申请单货品列表。', 'info');
    }
}

/**
 * 打开导入文件界面
 */
function openImportDlg(){
    //录入人与录入日期
    var today = new Date();
    $('#recordDate').val(today.toLocaleDateString());

    $.ajax({
        url: '../managesys/user/getCurrentUser',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result) {
                $('#recordPerson').val(result.id);
            }
        }
    });
    $('#importExcel-dlg').dialog('open').dialog('setTitle', '表格导入');
    $('#file').filebox('setValue', '');
}

/**
 * 导入文件
 */
function importExcel(){
    //得到上传文件的全路径
    var fileName = $('#file').filebox('getValue');

    //对文件格式进行校验
    var d1 = /\.[^\.]+$/.exec(fileName);
    if (d1 == ".xls" || d1 == ".xlsx") {
        //提交表单
        $('#importExcel').form('submit', {
            url: 'warehouseApplication/importExcel',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (data) {
                var result = jQuery.parseJSON(data);
                if (result.success) {
                    $('#importExcel-dlg').dialog('close');
                    $('#warehouse-dg').datagrid('reload');
                    $.messager.alert('提示', '操作成功！', 'info');
                } else {
                    $.messager.alert('保存失败', result.message, 'error');
                }
            }
        });
    } else {
        $.messager.alert('提示', '请选择xls格式文件！', 'info');
        $('#importExcel').filebox('setValue', '');
    }
}
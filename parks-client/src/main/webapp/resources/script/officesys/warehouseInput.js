/**
 * Created by Zhao_d on 2016/12/26.
 */
$(function(){
    loadBtns('warehouse-btn', $('#menuId').val());
    $('#warehouse-dg').datagrid({
        title:'入库单列表',
        toolbar:'#warehouse-toolbar',
        method:'post',
        nowrap:true,
        sortName:'number',
        sortOrder:'desc',
        striped:true,
        rownumbers:true,
        fitColumns:false,
        fit:true,
        singleSelect:true,
        pagination: true,
        url:'warehouseInput/warehouseInputList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'number',title:'入库单号'},
            {field:'inputType',title:'入库类型'},
            {field:'inputDate',title:'入库日期'}
        ]],
        columns:[[
            {field:'warehouseName',title:'仓库'},
            {field:'applicationName',title:'申请单名称'},
            {field:'recordPersonName',title:'录入人'},
            {field:'recordDate',title:'录入日期'},
            {field:'examinePersonName',title:'审核人',hidden:true},
            {field:'examineDate',title:'审核日期',hidden:true},
            {field:'companyName',title:'供应方'},
            {field:'note',title:'备注',width:80}
        ]],
        loadFilter:function(data){
            for(var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].inputDate == "1900-01-01"){
                    data.rows[i].inputDate = "";
                }
                if (data.rows[i].recordDate == "1900-01-01"){
                    data.rows[i].recordDate = "";
                }
                if (data.rows[i].examineDate == "1900-01-01"){
                    data.rows[i].examineDate = "";
                }
            }
            return data;
        },
        //双击打开货品清单
        onDblClickRow: openProduct
    });
    
    $('#product-dg').datagrid({
        toolbar:'#product-toolbar',
        method:'post',
        nowrap:true,
        sortName:'id',
        sortOrder:'asc',
        striped:true,
        rownumbers:true,
        fitColumns:false,
        fit:true,
        singleSelect:true,
        pagination: true,
        url:'warehouseInput/warehouseInputProList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'parentIdFK',hidden:true},
            {field:'productId',hidden:true},
            {field:'productName',title:'名称'}
        ]],
        columns:[[
            {field:'productModelNumber',title:'型号'},
            {field:'productSpecifications',title:'封装'},
            {field:'productNum',title:'生产任务单号', width:100,
                editor:{
                    type:'textbox'
                }},
            {field:'quantity',title:'数量',
                editor :{
                    type:'numberbox',
                    options:{precision:2}
                },width:60},
            {field:'productUnit',title:'单位'},
            {field:'price',title:'单价',
                editor :{
                    type:'numberbox',
                    options:{precision:2}
                },width:60},
            {field:'note',title:'备注',editor : 'textbox',width:100}
        ]],
        onClickRow: onClickRow
    });
    
    $('#productSelect-dg').datagrid({
        toolbar:'#productSelect-toolbar',
        method:'post',
        nowrap:true,
        sortName:'name',
        sortOrder:'asc',
        striped:true,
        rownumbers:true,
        fitColumns:false,
        fit:true,
        singleSelect:false,
        pagination: true,
        url:'warehouseProduct/warehouseProductList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'select',checkbox:true},
            {field:'name',title:'名称'}
        ]],
        columns:[[
            {field:'modelNumber',title:'型号'},
            {field:'specifications',title:'封装'},
            {field:'productType',title:'类型'},
            {field:'brand',title:'品牌'},
            {field:'unit',title:'单位'},
            {field:'productDesc',title:'描述'}
        ]]
    });

    $('#application-dg').datagrid({
        method:'post',
        nowrap:true,
        sortName:'SN',
        sortOrder:'asc',
        striped:true,
        rownumbers:true,
        fitColumns:false,
        pagination:false,
        fit:true,
        selectOnCheck:true,
        url: 'warehouseApplication/applicationProList',
        onSelect:onSelect,
        onUnselect:onUnselect,
        frozenColumns: [[
            {field: 'id', title: 'ID', align: 'left', hidden: true},
            {field: 'parentIdFK', hidden: true},
            {field:'select',checkbox:true},
            {field: 'productName', title: '名称'}
        ]],
        columns: [[
            {field: 'quantityInput', title: '数量'},
            {field: 'productUnit', title: '单位'},
            {field: 'price', title: '单价',width: 100,
                editor :{
                    type:'numberbox',
                    options:{precision:2}
                }},
            {field: 'productNum', title: '生产任务单号', width: 120,
                editor:{
                    type:'textbox'
                }},
            {field: 'productModelNumber', title: '型号', width: 100},
            {field: 'productSpecifications', title: '封装', width: 100},
            {field:'productBrand',title:'品牌', width: 100},
            {field: 'note', title: '备注', width: 100,
                editor :{
                    type:'textbox'
                }}
        ]]
    });

    $('#inputType').combobox({
        data: [{"id": "原材料", "text": "原材料"}, {"id": "成品", "text": "成品"}, {"id": "半成品", "text": "半成品"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#inputTypeQuery').combobox({
        data: [{"id": "原材料", "text": "原材料"}, {"id": "成品", "text": "成品"}, {"id": "半成品", "text": "半成品"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#recordPerson').combotree({
        required:true,
        url:'warehouseInput/getUserList',
        valueField:'id',
        textField:'text'
    });

    $('#examinePerson').combotree({
        required:true,
        url:'warehouseInput/getUserList',
        valueField:'id',
        textField:'text'
    });

    $('#company').combotree({
        required:true,
        url:'warehouseInput/getCompanyList',
        valueField:'id',
        textField:'text'
    });

    $('#application').combobox({
        url:'warehouseInput/getApplicationList',
        valueField:'id',
        textField:'text',
        onChange:onChangeApplication
    });

    $('#warehouse').combotree({
        required:true,
        url:'warehouseInput/getWarehouseType',
        valueField:'id',
        textField:'text'
    });

    $('#productTypeQuery').combobox({
        data: [{"id": "原材料", "text": "原材料"}, {"id": "成品", "text": "成品"}, {"id": "半成品", "text": "半成品"}],
        valueField: 'id',
        textField: 'text'
    });
    
    function onSelect(rowIndex,rowData){
        $('#application-dg').datagrid('beginEdit', rowIndex);
    }
    function onUnselect(rowIndex,rowData){
        $('#application-dg').datagrid('cancelEdit', rowIndex);
    }
    var editIndex = undefined;
    function onClickRow(index){
        if (editIndex != index){
            if (endEditing()){
                $('#product-dg').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                editIndex = index;
            } else {
                $('#product-dg').datagrid('selectRow', editIndex);
            }
        }else{
            $('#product-dg').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
        }
    }
    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#product-dg').datagrid('validateRow', editIndex)){
            $('#product-dg').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onChangeApplication() {
        var application = $('#application').combobox('getValue');
        if ($('#id').val() == "" && application != null && application != "") {
            $('#application-div').show();
            $('#application-dg').datagrid({
                queryParams: {
                    id: application,
                    rows: 1000
                }
            });
            $('#warehouseDlg').dialog('vcenter');
        } else {
            $('#application-div').hide();
            $('#warehouseDlg').dialog('vcenter');
        }
    }
});


/**
 * 入库单查询
 */
function warehouseQuery(){
    var query = {
        numberQuery:$('#numberQuery').val(),
        inputTypeQuery:$('#inputTypeQuery').combobox('getValue'),
        inputDateBegQuery:$('#inputDateBegQuery').datebox('getValue'),
        inputDateEndQuery:$('#inputDateEndQuery').datebox('getValue')
    };
    $('#warehouse-dg').datagrid({
        queryParams:query
    });
}

var formUrl = "";

/**
 * 新增入库单
 */
function addWarehouseInput(){
    formUrl = "warehouseInput/addWarehouseInput";
    $('#addWarehouse').form('clear');
    var today = new Date();
    $('#inputDate').datebox('setValue',today.toLocaleDateString());
    $('#recordDate').datebox('setValue',today.toLocaleDateString());

    $.ajax({
        url: '../managesys/user/getCurrentUser',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result) {
                $('#recordPerson').combobox('setValue',result.id);        //登录人
                $('#recordPerson').combobox('setText',result.nickname);
            }
        }
    });

    $.ajax({
        url:'warehouseInput/getNewNumber',
        dataType:'json',
        success:function(number){
            $('#number').textbox('setValue',number);        //自动添加编号
        }
    });
    
    $('#application-div').hide();
    $('#warehouseDlg').dialog('open').dialog('setTitle', '新增入库单');
}

/**
 * 修改入库单
 */
function editWarehouseInput(){
    formUrl = "warehouseInput/editWarehouseInput";
    var row = $('#warehouse-dg').datagrid('getSelected');
    if(row){
        $('#addWarehouse').form('clear');
        $('#id').val(row.id);
        $('#number').textbox('setValue',row.number);
        $('#inputType').combobox('setValue',row.inputType);
        $('#inputDate').datebox('setValue',row.inputDate);
        $('#warehouse').combobox('setValue',row.warehouseID);
        $('#warehouse').combobox('setText',row.warehouseName);
        $('#application').combobox('setValue',row.applicationID);
        $('#application').combobox('setText',row.applicationName);
        $('#recordPerson').combobox('setValue',row.recordPersonID);
        $('#recordPerson').combobox('setText',row.recordPersonName);
        $('#recordDate').datebox('setValue',row.recordDate);
        $('#examinePerson').combobox('setValue',row.examinePersonID);
        $('#examinePerson').combobox('setText',row.examinePersonName);
        $('#examineDate').datebox('setValue',row.examineDate);
        $('#company').combobox('setValue',row.companyId);
        $('#company').combobox('setText',row.companyName);
        $('#note').textbox('setValue',row.note);
        
        if(row.applicationID == null){
            $('#application-div').hide();
        }
        $('#warehouseDlg').dialog('open').dialog('setTitle', '修改入库单');
    }else{
        $.messager.alert('提示', '需要选择一条入库单记录，才能进行编辑操作。', 'info');
    }
}

/**
 * 保存入库单
 */
function saveWarehouseInput(){
    //申请单中的货品信息
    var application = $('#application').combobox('getValue');
    var jsonAllRows = "";
    if(application != null && application != "") {
        var selectRows =  $('#application-dg').datagrid('getChecked');
        for(var i in selectRows){
            var index = $('#application-dg').datagrid('getRowIndex',selectRows[i]);
            $('#application-dg').datagrid('endEdit',index);
        }
        var changeRows = $('#application-dg').datagrid('getSelections');
        jsonAllRows = JSON.stringify(changeRows);
        $('#jsonAllRows').val(jsonAllRows);
    }
    //保存清单信息
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
 * 删除入库单
 */
function deleteWarehouseInput(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("warehouseInput/deleteWarehouseInput",{
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
        $.messager.alert('提示', '需要选择一条入库单记录，才能进行删除操作。', 'info');
    }
}

/**
 * 打开入库货品界面
 */
function openProduct(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    if (row) {
        $('#product-dg').datagrid({
            queryParams: {
                id:row.id
            }
        });
        $('#productDgd').dialog('open').dialog('setTitle', '入库货品列表');
    } else{
        $.messager.alert('提示', '需要选择一个入库单记录，才能查看入库货品列表。', 'info');
    }
}

/**
 * 入库货品查询
 */
function productQuery(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    var query = {
        id:row.id,
        nameQuery:$('#nameQuery').val(),
        modelNumberQuery:$('#modelNumberQuery').val(),
        specificationsQuery:$('#specificationsQuery').val()
    };
    $('#product-dg').datagrid({
        queryParams:query
    });
}

/**
 * 保存入库货品
 */
function saveProduct(){
    var selectRow = $('#product-dg').datagrid('getSelected');
    var index = $('#product-dg').datagrid('getRowIndex',selectRow);
    $('#product-dg').datagrid('endEdit', index);
    var changeRow =  $('#product-dg').datagrid('getChanges');
    var jsonAllRows =JSON.stringify(changeRow);
    $.ajax({
        url:'warehouseInput/editWarehouseInputPro',
        type:'post',
        data:{jsonAllRows:jsonAllRows},
        dataType:'json',
        success:function(data){
            if(!data.success) {
                $.messager.alert("数据保存失败",data.message,'error');
            }else{
                $('#product-dg').datagrid('reload');
            }
        }
    });
}

/**
 * 删除入库货品
 */
function deleteProduct(){
    var row = $('#product-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("warehouseInput/deleteWarehouseInputPro",{
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
        $.messager.alert('提示', '需要选择一条入库货品记录，才能进行删除操作。', 'info');
    }
}

/**
 * 打开货品选择界面
 */
function selectProduct(){
    $('#productSelectDgd').dialog('open').dialog('setTitle', '货品选择列表');
}

/**
 * 货品选择界面查询
 */
function productSelectQuery(){
    var query = {
        nameSelectQuery:$('#nameSelectQuery').val(),
        productTypeQuery:$('#productTypeQuery').combobox('getValue'),
        brandQuery:$('#brandQuery').val()
    };
    $('#productSelect-dg').datagrid({
        queryParams:query
    });
}

/**
 * 添加货品
 */
function productAdd(){
    var parentRow = $('#warehouse-dg').datagrid('getSelected');
    var selectRows = $('#productSelect-dg').datagrid('getSelections');
    for(var i in selectRows){
        $('#product-dg').datagrid('appendRow',{
            parentIdFK:parentRow.id,
            productId:selectRows[i].id,
            productName:selectRows[i].name,
            productNum:selectRows[i].productNum,
            productModelNumber:selectRows[i].modelNumber,
            productSpecifications:selectRows[i].specifications,
            productUnit:selectRows[i].unit
        });
    }
    $('#productSelectDgd').dialog('close');
}

//打开审批界面
function addApprove(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    if(row){
        $('#addApprove').form('clear');
        $('#approveId').val(row.id);
        if(row.examinePersonName) {          //审批人
            $('#examinePerson').combobox('setValue',row.examinePersonID);
            $('#examinePerson').combobox('setText',row.examinePersonName);
        }else{
            $.ajax({
                url: '../managesys/user/getCurrentUser',
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    if (result) {
                        $('#examinePerson').combobox('setValue',result.id);
                        $('#examinePerson').combobox('setText',result.nickname);
                    }
                }
            });
        }
        if(row.examineDate && !(row.examineDate=="")) {            //审批日期
            $('#examineDate').datebox('setValue', row.examineDate);
        }else{
            var today = new Date();
            $('#examineDate').datebox('setValue',today.toLocaleDateString());
        }
        $('#approveDlg').dialog('open').dialog('setTitle', '审批');
    }else{
        $.messager.alert('提示', '需要选择一条入库记录，才能进行审批。', 'info');
    }
}

//保存审批记录
function saveApprove(){
    $('#addApprove').form('submit', {
        url: 'warehouseInput/editWarehouseInput',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if(data.success) {
                $('#approveDlg').dialog('close');
                $('#warehouse-dg').datagrid('reload');
            }else{
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

function exportExcel(){
    $.messager.confirm('确认','是否将列表导出为Excel文件',function(r){
        if(r)
        {
            $('#query-form').form({
                queryParams:{
                    numberQuery:$('#numberQuery').val(),
                    inputTypeQuery:$('#inputTypeQuery').combobox('getValue'),
                    inputDateBegQuery:$('#inputDateBegQuery').datebox('getValue'),
                    inputDateEndQuery:$('#inputDateEndQuery').datebox('getValue')
                }
            });
            $('#query-form').form('submit',{
                url:"warehouseInput/exportExcel",
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

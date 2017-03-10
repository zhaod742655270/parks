/**
 * Created by Zhao_d on 2016/12/26.
 */
$(function(){
    loadBtns('warehouse-btn', $('#menuId').val());
    $('#warehouse-dg').datagrid({
        title:'出库单列表',
        toolbar:'#warehouse-toolbar',
        method:'post',
        nowrap:true,
        sortName:'number',
        sortOrder:'desc',
        striped:true,
        rownumbers:true,
        fitColumns:true,
        fit:true,
        singleSelect:true,
        pagination: true,
        url:'warehouseOutput/warehouseOutputList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'number',title:'出库单号'},
            {field:'outputType',title:'出库类型'},
            {field:'outputDate',title:'出库日期'}
        ]],
        columns:[[
            {field:'warehouseName',title:'仓库'},
            {field:'applicationName',title:'申请单名称'},
            {field:'recordPersonName',title:'录出人'},
            {field:'recordDate',title:'录出日期'},
            {field:'examinePersonName',title:'审核人',hidden:true},
            {field:'examineDate',title:'审核日期',hidden:true},
            {field:'companyName',title:'接收方'},
            {field:'note',title:'备注',width:80}
        ]],
        loadFilter:function(data){
            for(var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].outputDate == "1900-01-01"){
                    data.rows[i].outputDate = "";
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
        url:'warehouseOutput/warehouseOutputProList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'parentIdFK',hidden:true},
            {field:'productId',hidden:true},
            {field:'productName',title:'名称'}
        ]],
        columns:[[
            {field:'productModelNumber',title:'型号'},
            {field:'productSpecifications',title:'封装'},
            {field:'quantity',title:'数量',
                editor :{
                    type:'numberbox',
                    options:{precision:2}
                },width:60},
            {field:'productUnit',title:'单位'},
            {field:'note',title:'备注',editor : 'textbox',width:100}
        ]],
        onClickRow: onClickRow
    });
    
    $('#productSelect-dg').datagrid({
        toolbar:'#productSelect-toolbar',
        method:'post',
        nowrap:true,
        sortName:'id',
        sortOrder:'asc',
        striped:true,
        rownumbers:true,
        fitColumns:true,
        fit:true,
        singleSelect:false,
        pagination: true,
        url:'warehouse/warehouseList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'productId',title:'productId',hidden:true},
            {field:'select',checkbox:true},
            {field:'name',title:'名称'}
        ]],
        columns:[[
            {field:'modelNumber',title:'型号'},
            {field:'specifications',title:'封装'},
            {field:'brand',title:'品牌'},
            {field:'unit',title:'单位'},
            {field:'quantityUse',title:'可用数量'},
            {field:'quantity',title:'库存总量'}
        ]]
    });

    $('#application-dg').datagrid({
        method:'post',
        nowrap:true,
        sortName:'id',
        sortOrder:'asc',
        striped:true,
        rownumbers:true,
        fitColumns:true,
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
            {field: 'productModelNumber', title: '型号'},
            {field: 'productSpecifications', title: '封装'},
            {field: 'productNum', title: '生产任务单号', width: 100},
            {field:'productBrand',title:'品牌'},
            {field: 'quantity', title: '申请数量'},
            {field: 'quantityOutput', title: '出库数量',
                editor :{
                    type:'numberbox',
                    options:{precision:2}
                }},
            {field: 'productUnit', title: '单位'},
            {field: 'note', title: '备注',
                editor :{
                    type:'textbox'
                }}
        ]]
    });


    $('#outputType').combobox({
        data: [{"id": "原材料", "text": "原材料"}, {"id": "成品", "text": "成品"}, {"id": "半成品", "text": "半成品"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#outputTypeQuery').combobox({
        data: [{"id": "原材料", "text": "原材料"}, {"id": "成品", "text": "成品"}, {"id": "半成品", "text": "半成品"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#recordPerson').combotree({
        required:true,
        url:'warehouseOutput/getUserList',
        valueField:'id',
        textField:'text'
    });

    $('#examinePerson').combotree({
        required:true,
        url:'warehouseOutput/getUserList',
        valueField:'id',
        textField:'text'
    });

    $('#company').combotree({
        required:true,
        url:'warehouseOutput/getCompanyList',
        valueField:'id',
        textField:'text'
    });

    $('#application').combotree({
        url:'warehouseOutput/getApplicationList',
        valueField:'id',
        textField:'text',
        onChange:onChangeApplication
    });

    $('#warehouse').combotree({
        required:true,
        url:'warehouseOutput/getWarehouseType',
        valueField:'id',
        textField:'text'
    });

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
    function onSelect(rowIndex,rowData){
        $('#application-dg').datagrid('beginEdit', rowIndex);
    }
    function onUnselect(rowIndex,rowData){
        $('#application-dg').datagrid('cancelEdit', rowIndex);
    }
    function onChangeApplication(){
        var application = $('#application').combobox('getValue');
        if($('#id').val() == "" && application != null && application != ""){
            $('#application-div').show();
            $('#application-dg').datagrid({
                queryParams: {
                    id:application,
                    rows:1000
                }
            });
            $('#warehouseDlg').dialog('vcenter');
        }else{
            $('#application-div').hide();
            $('#warehouseDlg').dialog('vcenter');
        }
    }
});


/**
 * 出库单查询
 */
function warehouseQuery(){
    var query = {
        numberQuery:$('#numberQuery').val(),
        outputTypeQuery:$('#outputTypeQuery').combobox('getValue'),
        outputDateBegQuery:$('#outputDateBegQuery').datebox('getValue'),
        outputDateEndQuery:$('#outputDateEndQuery').datebox('getValue')
    };
    $('#warehouse-dg').datagrid({
        queryParams:query
    });
}

var formUrl = "";

/**
 * 新增出库单
 */
function addWarehouseOutput(){
    formUrl = "warehouseOutput/addWarehouseOutput";
    $('#addWarehouse').form('clear');
    var today = new Date();
    $('#outputDate').datebox('setValue',today.toLocaleDateString());
    $('#recordDate').datebox('setValue',today.toLocaleDateString());

    $.ajax({
        url: '../managesys/user/getCurrentUser',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result) {
                $('#recordPerson').combobox('setValue',result.userId);        //登录人
                $('#recordPerson').combobox('setText',result.userName);
            }
        }
    });

    $.ajax({
        url:'warehouseOutput/getNewNumber',
        dataType:'json',
        success:function(number){
            $('#number').textbox('setValue',number);        //自动添加编号
        }
    });

    $('#application-div').hide();
    $('#warehouseDlg').dialog('open').dialog('setTitle', '新增入库单');
}

/**
 * 修改出库单
 */
function editWarehouseOutput(){
    formUrl = "warehouseOutput/editWarehouseOutput";
    var row = $('#warehouse-dg').datagrid('getSelected');
    if(row){
        $('#addWarehouse').form('clear');
        if(row.applicationID == null){
            $('#application-div').hide();
        }
        
        $('#id').val(row.id);
        $('#number').textbox('setValue',row.number);
        $('#outputType').combobox('setValue',row.outputType);
        $('#outputDate').datebox('setValue',row.outputDate);
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
        
        $('#warehouseDlg').dialog('open').dialog('setTitle', '修改出库单');
    }else{
        $.messager.alert('提示', '需要选择一条出库单记录，才能进行编辑操作。', 'info');
    }
}

/**
 * 保存出库单
 */
function saveWarehouseOutput(){
    //申请单中的货品信息
    var application = $('#application').combobox('getValue');
    var jsonAllRows = "";
    if(application != null && application != "") {
        var selectRows =  $('#application-dg').datagrid('getChecked');
        for(var i in selectRows){
            var index = $('#application-dg').datagrid('getRowIndex',selectRows[i]);
            $('#application-dg').datagrid('endEdit',index);
        }
        var changeRows = $('#application-dg').datagrid('getChanges');
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
            if(data.success) {
                $('#warehouseDlg').dialog('close');
                $('#warehouse-dg').datagrid('reload');
            }else{
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

/**
 * 删除出库单
 */
function deleteWarehouseOutput(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("warehouseOutput/deleteWarehouseOutput",{
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
        $.messager.alert('提示', '需要选择一条出库单记录，才能进行删除操作。', 'info');
    }
}

/**
 * 打开出库货品界面
 */
function openProduct(){
    var row = $('#warehouse-dg').datagrid('getSelected');
    if (row) {
        $('#product-dg').datagrid({
            queryParams: {
                id:row.id
            }
        });
        $('#productDgd').dialog('open').dialog('setTitle', '出库货品列表');
    } else{
        $.messager.alert('提示', '需要选择一个出库单记录，才能查看出库货品列表。', 'info');
    }
}

/**
 * 出库货品查询
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
 * 保存出库货品
 */
function saveProduct(){
    var selectRow = $('#product-dg').datagrid('getSelected');
    var index = $('#product-dg').datagrid('getRowIndex',selectRow);
    $('#product-dg').datagrid('endEdit', index);
    var changeRow =  $('#product-dg').datagrid('getChanges');
    var jsonAllRows =JSON.stringify(changeRow);
    $.ajax({
        url:'warehouseOutput/editWarehouseOutputPro',
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
 * 删除出库货品
 */
function deleteProduct(){
    var row = $('#product-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？',function(r){
            if(r){
                $.post("warehouseOutput/deleteWarehouseOutputPro",{
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
        $.messager.alert('提示', '需要选择一条出库货品记录，才能进行删除操作。', 'info');
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
        brandQuery:$('#brandQuery').combobox('getValue')
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
            productId:selectRows[i].productId,
            productName:selectRows[i].name,
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
                        $('#examinePerson').combobox('setValue',result.userId);
                        $('#examinePerson').combobox('setText',result.userName);
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
        url: 'warehouseOutput/editWarehouseOutput',
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

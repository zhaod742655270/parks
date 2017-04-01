
$(function () {
    loadBtns('contract-btns', $('#menuId').val());
    $('#contract-dg').datagrid({
        nowrap: false,
        sortName: 'projectSn',
        sortOrder: 'desc',
        title: '验收管理',
        toolbar: '#contract-toolbar',
        method: 'post',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        pagination: true,
        url:"conGathering/acceptanceConGatheringList",
        onDblClickRow: function (rowIndex, rowData) {
            findAcceptance();
        }

    });
    $('#acceptance-dg').datagrid({
        sortName: 'SN',
        sortOrder: 'asc',
        toolbar: '#acceptance-toolbar',
        striped: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'post',
        nowrap:false,
        pagination: true,
        onClickRow: onClickRow,
        onLoadSuccess: onLoadSuccess,
        frozenColumns:[[
            {field:'id',title:'acceptanceID',align:'left',hidden:true},
            {field:'SN',title:'序号',align:'left'},
            {field:'equipmentName',title:'设备名称',align:'left'},
            {
                field : 'brand',
                title : "品牌",
                formatter:function(value, row){
                    if (row.acceptancePostilDTO) {
                        if ( row.acceptancePostilDTO.brandPostil) {
                            return "<span style='color:red'>"+row.brand+"</span>";
                        }else{
                            return row.brand;
                        }

                    }else{
                        return row.brand;
                    }
                }
            }
        ]],
        columns : [ [
            {field:'specification',title:'规格型号',align:'left',
                formatter: function (value, row, index){
                    if (row.acceptancePostilDTO) {
                        if ( row.acceptancePostilDTO.specificationPostil) {
                            return "<span style='color:red'>"+row.specification+"</span>";
                        } else {
                            return row.specification;
                        }
                    }else{
                        return row.specification;
                    }
                }},
            {
                field : 'technicalParameter',
                title : "技术参数"
            },
            {field : 'unit', title : "单位"},
            {field:'quantity',title:'数量',align:'left',precision:2},
            {field:'price',title:'单价',align:'left',precision:2},
            {field:'valence',title:'合价',align:'left',precision:2},
         {
            field : 'requiredArrivalTime',
            title : "要求到货时间"
        },{
            field : 'inventory',
            title : "库存"
        },{
            field : 'testNote',
            title : "测试情况"
        },{
            field : 'budgetNote',
            title : "预算备注"
        },{
            field : 'supplier',
            title : "供应商",
            width : 130
        },{
            field : 'purchaseQuantity',
            title : "采购数量"
        },{
            field : 'purchaserName',
            title : "采购员"
        },{
            field : 'projectManagerName',
            title : "项目经理"
        },{
            field : 'arrivalTime',
            title : "到货时间",
            width : 100,
            editor : 'datebox'
        }, {
            field : 'arrivalQuantity',
            title : "到货数量",
            editor : 'text'
        }, {
            field : 'remainQuantity',
            title : "剩余数量",
            editor : 'text'
        }, {
            field : 'direction',
            title : "去向",
            width : 80,
            editor : 'text'
        },{
            field : 'packagesName',
            title : "包装",
            width:60,
            editor:{
                type:'combobox',
                options:{
                    valueField:'id',
                    textField:'text',
                    data: [{"id": "完好", "text": "完好"}, {"id": "有破损", "text": "有破损"}],
                    required:true
                }
            }
        }, {
            field : 'appearanceName',
            title : "外观",
            width:60,
            editor:{
                type:'combobox',
                options:{
                    valueField:'id',
                    textField:'text',
                    data: [{"id": "完好", "text": "完好"}, {"id": "有缺陷", "text": "有缺陷"}],
                    required:true
                }
            }
        }, {
            field : 'datasheetName',
            title : "随机资料/附件",
            editor:{
                type:'combobox',
                options:{
                    valueField:'id',
                    textField:'text',
                    data: [{"id": "齐全", "text": "齐全"}, {"id": "缺项", "text": "缺项"}],
                    required:true
                }
            }
        },{
            field : 'productID',
            title : "产品序列号",
            editor : 'text',
            width : 100
        },{
            field : 'POSTName',
            title : "加电测试",
            editor:{
                type:'combobox',
                options:{
                    valueField:'id',
                    textField:'text',
                    data: [{"id": "正常", "text": "正常"}, {"id": "异常", "text": "异常"}],
                    required:true
                }
            }
        },{
            field : 'operation',
            title : "设备运行情况",
            editor : 'text'
        },{
            field : 'arrivalNote',
            title : "到货备注",
            editor : 'text',
            width : 100
        },{
            field : 'runnedTime',
            title : "设备运行时间",
            editor : 'text',
            width : 100
        },
           /* {
            field : 'options',
            title : "保存/取消",
            formatter:function(value,row,index){
                    var s = '<a href="#" class="easyui-linkbutton" onclick="saveRow()">保存</a> ';
                    var c = '<a href="#" class="easyui-linkbutton" onclick="cancelRow()">取消</a>';
                    return s+c;
            }
        },*/
            {
                field: 'customName',
                title:"是否定制",
                editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'text',
                        data:[{'id':"是",'text':"是"},{'id':"否",'text':"否"}],
                        required:true,
                        onSelect:onSelect
                    }
                }
            },{
                field:'insulationLeather',
                title:"绝缘皮标识",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },{
                field:'diameter',
                title:"线径",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },{
                field:'allLength',
                title:"整盘/箱/根长度",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },{
                field:'thick',
                title:"壁厚",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },{
                field:'outsideDiameter',
                title:"外径",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },{
                field:'size',
                title:"尺寸",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },{
                field:'color',
                title:"颜色",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },{
                field:'material',
                title:"材质",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },{
                field:'boreDistance',
                title:"孔距",
                editor:{
                    type:'validatebox',
                    options:{
                        validType:['length[0,20]']
                    }
                },
                hidden:true
            },
            {field:'examine',title:'examine',hidden:true},
            {field:'purchaseOperate',title:'purchaseOperate',hidden:true}
        ] ],

        onRowContextMenu: function (e, index, node) {
            e.preventDefault();
            var row = $('#acceptance-dg').datagrid('getSelected');
            if (row) {
                $('#rightMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        },
        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].packages==1){
                    data.rows[i].packagesName ="有破损";
                }else{
                    data.rows[i].packagesName ="完好";
                }

                if (data.rows[i].appearance==1){
                    data.rows[i].appearanceName ="有缺陷";
                }else{
                    data.rows[i].appearanceName = "完好";
                }

                if (data.rows[i].datasheet==1){
                    data.rows[i].datasheetName ="缺项";
                }else{
                    data.rows[i].datasheetName = "齐全";
                }

                if (data.rows[i].POST==1){
                    data.rows[i].POSTName ="异常";
                }else{
                    data.rows[i].POSTName = "正常";
                }

                if (data.rows[i].requiredArrivalTime=="1900-01-01"){
                    data.rows[i].requiredArrivalTime ="";
                }
                if (data.rows[i].arrivalTime=="1900-01-01"){
                    data.rows[i].arrivalTime ="";
                }

                if (data.rows[i].purchaseQuantity==0){
                    data.rows[i].purchaseQuantity ="";
                }

                //是否定制部分
                if(data.rows[i].custom==true){
                    data.rows[i].customName = "是";
                } else{
                    data.rows[i].customName = "否";
                }
            }
            //判断隐藏或显示定制部分
            var bool = 0;
            for (var j = 0; j < data.rows.length; j++) {
                if (data.rows[j].customName == "是") {
                    bool += 1;
                }
            }
            if (bool==0) {
                setAllHidden();
            }else{
                setAllShow();
            }
            return data;
        }

    });

    $('#examine-dg').datagrid({
        title:'审核记录',
        method:'post',
        nowrap:true,
        sortName:'examineDate',
        sortOrder:'desc',
        striped:true,
        rownumbers:true,
        fitColumns:false,
        fit:true,
        singleSelect:true,
        pagination: true,
        url:'acceptance/getExamineList',
        columns:[[
            {field:'id',title:'ID',hidden:true},
            {field:'acceptanceId',title:'acceptanceId',hidden:true},
            {field:'examineDate',title:'审核日期'},
            {field:'examineType',title:'操作类型'}
        ]]
    });

    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#acceptance-dg').datagrid('validateRow', editIndex)){
            $('#acceptance-dg').datagrid('endEdit', editIndex);
            
            //根据是否审核改变首列颜色
            var view = $('#acceptance-dg').datagrid('getPanel').find('div.datagrid-view1');     //找到冻结列所在区域
            var cell = view.find('div.datagrid-body').find('tr')[editIndex].cells[1];       //找到单元格
            if ($('#acceptance-dg').datagrid('getRows')[editIndex].purchaseOperate == true) {
                cell.style.cssText = 'background:green;color:white';
            } else if ($('#acceptance-dg').datagrid('getRows')[editIndex].examine == true) {
                cell.style.cssText = 'background:gold;color:black';
            } else {
                cell.style.cssText = 'background:white;color:black';
            }
            
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickRow(index){
        if (editIndex != index){
            if (endEditing()){
                $('#acceptance-dg').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                editIndex = index;
            } else {
                $('#acceptance-dg').datagrid('selectRow', editIndex);
            }
        }else{
            $('#acceptance-dg').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
        }
    }

    function onSelect(record) {
        if (record.text == "是") {
            setAllShow();
        } else {
            var bool = 0;
            var data = $('#acceptance-dg').datagrid('getRows');
            var selectRow = $('#acceptance-dg').datagrid('getSelected');
            var index = $('#acceptance-dg').datagrid('getRowIndex',selectRow);
            setAllNull(selectRow,index);
            //如果所有行都不定制的话，则隐藏定制部分
            for (var i = 0; i < data.length; i++) {
                if (data[i].customName == "是") {
                    bool += 1;
                }
            }
            if ((bool==1 && selectRow.customName== "是") || bool==0) {
                setAllHidden();
            }
        }
    }

    function onLoadSuccess(data){
        //根据是否审核改变首列颜色
        for (var i = 0; i < data.rows.length; i++) {
            var view = $(this).datagrid('getPanel').find('div.datagrid-view1');     //找到冻结列所在区域
            var cell = view.find('div.datagrid-body').find('tr')[i].cells[1];       //找到单元格
            if (data.rows[i].purchaseOperate == true) {
                cell.style.cssText = 'background:green;color:white';
            } else if (data.rows[i].examine == true) {
                cell.style.cssText = 'background:gold;color:black';
            } else {
                cell.style.cssText = 'background:white;color:black';
            }

        }
    }

    $('#sample-tb').datagrid({
        striped: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        showHeader:false,
        nowrap:false

});

    $('#sample-tb').datagrid('loadData',[
        {SN: '年度', equipmentName: 'XXXX'},
        {SN: '合同号', equipmentName: 'XXXX'},
        {SN: '项目名称', equipmentName: 'XXXX'},
        {SN: '采购人员', equipmentName: 'XXXX'},
        {SN: '项目经理', equipmentName: 'XXXX'},
        {SN: '序号', equipmentName: '设备名称', brand:'品牌',specification:'规格型号',technicalParameter:'技术参数',unit:'单位', quantity:'数量',
         price:'单价', valence:'合价',requiredArrivalTime:'要求到货时间', inventory:'库存', testNote:'测试情况',budgetNote:'预算备注',
        supplier:'供应商', purchaseQuantity:'采购数量',purchaserName:'采购员', projectManagerName:'项目经理', arrivalTime:'到货时间', arrivalQuantity:'到货数量',
        remainQuantity:'剩余数量',direction:'去向',packagesName:'包装', appearanceName:'外观', datasheetName:'随机资料/附件', productID:'产品序列号',
        POSTName:'加电测试', operation:'设备运行情况', arrivalNote:'到货备注',runnedTime:'设备运行时间'
        },
        {SN: '1', equipmentName: '',brand:'',specification:'',technicalParameter:'', unit:'', quantity:'',price:'',valence:'', requiredArrivalTime:'', inventory:'', budgetNote:'',
            supplier:'', purchaseQuantity:'',purchaserName:'',projectManagerName:'', arrivalTime:'', arrivalQuantity:'',remainQuantity:'',direction:'', packagesName:'', appearanceName:'', datasheetName:'', productID:'',
            POSTName:'', operation:'', arrivalNote:'',runnedTime:''
        },
        {SN: '2', equipmentName: '',brand:'',specification:'',technicalParameter:'', unit:'', quantity:'',price:'',valence:'', requiredArrivalTime:'', inventory:'', budgetNote:'',
            supplier:'', purchaseQuantity:'',purchaserName:'',projectManagerName:'', arrivalTime:'', arrivalQuantity:'',remainQuantity:'',direction:'', packagesName:'', appearanceName:'', datasheetName:'', productID:'',
            POSTName:'', operation:'', arrivalNote:'',runnedTime:''
        }
    ]);

    $('#sheetNameQuery').combobox({
        data: [{"id": "2019", "text": "2019"}, {"id": "2018", "text": "2018"},
            {"id": "2017", "text": "2017"}, {"id": "2016", "text": "2016"},
            {"id": "2015", "text": "2015"}],
        valueField: 'id',
        textField: 'text'
    });

    $('#projectTypeQuery').combobox({
        data: [{"id": "零星项目", "text": "零星项目"}, {"id": "弱电项目", "text": "弱电项目"},
            {"id": "贸易项目", "text": "贸易项目"},{"value":"洽商项目","text":"洽商项目"}],
        valueField: 'id',
        textField: 'text'
    });


//获取采购人员列表
    $('#purchaserID').combotree({
        required:true,
        url: 'acceptance/getPurchaser',
        valueField: 'id',
        textField: 'text'
    });

//获取项目经理列表
    $('#projectManagerID').combotree({
        required:true,
        url: 'acceptance/getProjectManager',
        valueField: 'id',
        textField: 'text'
    })

});

var editIndex = undefined;
function cancelRow(){
    $('#acceptance-dg').datagrid('rejectChanges');
    editIndex = undefined;
}

function saveRow(){
    var row = $('#acceptance-dg').datagrid('getSelected');
    if(row){
        var at = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'arrivalTime'});
        var arrivalTime = $(at.target).datebox('getText');

        var aq = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'arrivalQuantity'});
        var arrivalQuantity = $(aq.target).val();

        var rq = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'remainQuantity'});
        var remainQuantity = $(rq.target).val();

        var dn = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'direction'});
        var direction = $(dn.target).val();

        var pn = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'packagesName'});
        var packagesName = $(pn.target).combobox('getValue');
        if(packagesName=="有破损"||packagesName=="true"){
            packagesName=true;
        }else{
            packagesName=false;
        }

        var an = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'appearanceName'});
        var appearanceName = $(an.target).combobox('getValue');
        if(appearanceName=="有缺陷"||appearanceName=="true"){
            appearanceName=true;
        }else{
            appearanceName=false;
        }

        var dn = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'datasheetName'});
        var datasheetName = $(dn.target).combobox('getValue');
        if(datasheetName=="缺项"||datasheetName=="true"){
            datasheetName=true;
        }else{
            datasheetName=false;
        }

        var pID = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'productID'});
        var productID = $(pID.target).val();

        var Pn = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'POSTName'});
        var POSTName = $(Pn.target).combobox('getValue');
        if(POSTName=="异常"||POSTName=="true"){
            POSTName=true;
        }else{
            POSTName=false;
        }


        var on = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'operation'});
        var operation = $(on.target).val();

        var arn = $('#acceptance-dg').datagrid('getEditor', {index:editIndex,field:'arrivalNote'});
        var arrivalNote = $(arn.target).val();


        var params={
            arrivalTime:arrivalTime,
            arrivalQuantity:arrivalQuantity,
            remainQuantity:remainQuantity,
            direction:direction,
            packages:packagesName,
            appearance:appearanceName,
            datasheet:datasheetName,
            POST:POSTName,
            operation:operation,
            arrivalNote:arrivalNote,
            productID:productID,
            id:row.id
        }
        $.ajax({
            url: 'acceptance/saveRow',
            type: 'POST',
            data: params,
            dataType: 'json',
            success: function (result) {
                $('#acceptance-dg').datagrid('reload');
            }
        })
    }else {
        $.messager.alert('提示', '需要选择编辑验收清单后，才能进行保存操作。', 'info');
    }

}

function saveAllRows(){
    var selectRow = $('#acceptance-dg').datagrid('getSelected');
    var index = $('#acceptance-dg').datagrid('getRowIndex',selectRow);
    $('#acceptance-dg').datagrid('endEdit', index);
    var changeRow =  $('#acceptance-dg').datagrid('getChanges', "updated");
    var jsonAllRows =JSON.stringify(changeRow);
    $.ajax({
        url:'acceptance/saveAllRows',
        type:'post',
        data:{jsonAllRows:jsonAllRows},
        dataType:'json',
        success:function(data){
            if(!data.success) {
                $.message.alert("数据保存失败",result.message,'error');
            }else{
                $('#acceptance-dg').datagrid('reload');
            }
        }
    });
}

function cancelAllRows(){
    $('#acceptance-dg').datagrid('rejectChanges');
}


// 定义窗口的提交地址
var formUrl = 'acceptance/addAcceptance';
//合同查询
function contractQuery(){
    var params = {
        sheetNameQuery:$('#sheetNameQuery').combobox('getValue'),
        projectTypeQuery: $('#projectTypeQuery').combobox('getValue'),
        conNameQuery: $('#conNameQuery').val()
    };
    $('#contract-dg').datagrid({
        queryParams: params
    });
}

//查看验收清单
function findAcceptance(){
    var row = $('#contract-dg').datagrid('getSelected');
    if (row) {
        $('#acceptanceDgd').dialog('open').dialog('setTitle', '验收清单列表');
        $('#equipmentNameQuery').val(""),
        $('#specificationQuery').val(""),
        $('#acceptance-dg').datagrid({
            url:"acceptance/acceptanceList",
            queryParams: {
                id:row.id
            }
        });

    } else{
        $.messager.alert('提示', '需要选择一个合同，才能查看验收清单。', 'info');
    }
}

//验收清单查询
function acceptanceQuery(){

    var row = $('#contract-dg').datagrid('getSelected');
    if (row) {
        $('#acceptance-dg').datagrid({
            url:"acceptance/acceptanceList",
            queryParams: {
                id:row.id,
                equipmentName: $('#equipmentNameQuery').val(),
                specification: $('#specificationQuery').val()
            }
        });
    } else{
        $.messager.alert('提示', '需要选择一个合同，才能查看验收清单。', 'info');
    }
}

var purchaseQuantity = 0;

//增加验收清单的窗口
function addAcceptance(){
    var row = $('#contract-dg').datagrid('getSelected');
    if(row){
        $.ajax({
            url: 'acceptance/findPermission',
            dataType: 'json',
            success: function (result) {
                if (result.message=="purchases") {
                    $('#acceptanceDlg').dialog('open').dialog('setTitle', '新增验收清单');
                    $('#addAcceptance').form('clear');
                }else{
                    $.messager.alert('错误', '您没有新增的权限', 'error');
                }
            }
        });
    }else{
        $.messager.alert('提示', '需要选择一个合同，才能进行新增验收清单的操作。', 'info');
    }

}

// 打开编辑验收清单的窗口
function editAcceptance() {
    var row = $('#acceptance-dg').datagrid('getSelected');
    if (row) {
        formUrl = 'acceptance/editAcceptance';
        $('#addAcceptance').form('clear');
        $('#addAcceptance').form('load', row);
        $('#acceptanceID').val(row.id);
        $('#contractID').val(row.contractID);
        $.ajax({
            url: 'acceptance/findPermission',
            type: 'POST',
            dataType: 'json',
            success: function (result) {
                if (result.message=="purchases") {
                    $('#acceptanceDlg').dialog('open').dialog('setTitle', '编辑验收清单');
                    $('#SN').textbox("setValue",row.SN);
                    $('#purchaserID').combobox('setValue',row.purchaserID);
                    $('#purchaserID').combobox('setText',row.purchaserName);
                    $('#equipmentName').val(row.equipmentName);
                    $('#brand').val(row.brand);
                    $('#technicalParameter').val(row.technicalParameter);
                    $('#specification').val(row.specification);
                    $('#unit').val(row.unit);
                    $('#quantity').numberbox("setValue", row.quantity);
                    $('#price').numberbox("setValue", row.price);
                    $('#valence').numberbox("setValue", row.valence);
                    $('#requiredArrivalTime').datebox("setValue",row.requiredArrivalTime);
                    $('#inventory').val(row.inventory);
                    $('#testNote').val(row.testNote);
                    $('#budgetNote').val(row.budgetNote);
                    $('#supplier').val(row.supplier);
                    $('#purchaseQuantity').numberbox("setValue", row.purchaseQuantity);
                    $('#projectManagerID').combobox('setValue',row.projectManagerID);
                    $('#projectManagerID').combobox('setText',row.projectManagerName);
                    purchaseQuantity = row.purchaseQuantity;
                }else{
                    $.messager.alert('错误', '您没有修改的权限', 'error');
                }
            }
        });
    } else {
        $.messager.alert('提示', '需要选择一个验收清单，才能进行编辑操作。', 'info');
    }

}

//保存按键
function saveAcceptance(){
    var row = $('#contract-dg').datagrid('getSelected');
    $('#contractID').val(row.id);
    
    $('#addAcceptance').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if(data.success) {
                //如果采购数量有修改，则更新标志
                if ($('#acceptanceID').val() != null && $('#purchaseQuantity').numberbox("getValue") != purchaseQuantity) {
                    var rowAcc = $('#acceptance-dg').datagrid('getSelected');
                    $.post("acceptance/savePurchaseOperate", {
                        id: rowAcc.id
                    }, function () {
                    });
                }

                $('#acceptanceDlg').dialog('close'); // close the dialog
                $('#acceptance-dg').datagrid('reload');
            }else{
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });

}

// 删除验收清单
function deleteAcceptance() {
    var row = $('#acceptance-dg').datagrid('getSelected');
    if (row) {
        $.ajax({
            url: 'acceptance/findPermission',
            dataType: 'json',
            success: function (result) {
                if (result.message=="purchases") {
                    $.messager.confirm('确认', '是否删除该条数据？', function (r) {
                        if (r) {
                            $.post("acceptance/deleteAcceptance", {
                                id: row.id
                            }, function (data) {
                                if (data.success) {
                                    // 成功后刷新表格
                                    $('#acceptance-dg').datagrid('reload');
                                } else {
                                    $.messager.alert('操作失败', data.message, 'error');
                                }
                            }, "json");
                        }
                    });
                }else{
                    $.messager.alert('错误', '您没有删除的权限', 'error');
                }

            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个验收清单，才能进行删除操作。', 'info');
    }

}

//验收清单的标签
function acceptancePostil() {
    var row = $('#acceptance-dg').datagrid('getSelected');
    var object = row.acceptancePostilDTO;
    if (object != null && object != "") {
        formUrl = 'acceptance/editPostil';
        $('#addAcceptancePostil-form').form('clear');
        $('#acceptancePostil-dlg').dialog('open').dialog('setTitle', '批注');
        $('#addAcceptancePostil-form').form('load', row);
        $('#acceptancePostilID').val(object.id);
        $('#parentID').val(object.parentID);
        if(object.brandPostil==null){
            $('#brandPostil').val("");
        }else{
            $('#brandPostil').val(object.brandPostil);
        }

        if(object.specificationPostil==null){
            $('#specificationPostil').val("");
        }else{
            $('#specificationPostil').val(object.specificationPostil);
        }

    }else{
        $("#acceptancePostil-dlg").dialog("open").dialog('setTitle', '批注');
        $('#addAcceptancePostil-form').form('clear');
        $("#parentID").val(row.id);
        formUrl = 'acceptance/addPostil';
    }
}

function importExcel() {

    $('#importExcel-dlg').dialog('open').dialog('setTitle', '表格导入');
    $('#file').filebox('setValue', '');
}

function uploadExcel(){
    //得到上传文件的全路径
    var fileName = $('#file').filebox('getValue');
    //进行基本校验
    //对文件格式进行校验
    var d1 = /\.[^\.]+$/.exec(fileName);
    if (d1 == ".xls"|| d1 == ".xlsx") {
        ////提交表单
        $('#importExcel').form('submit', {
            url: 'acceptance/uploadExcel',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (data) {
                var result = jQuery.parseJSON(data);
                if (result.success) {
                    $('#importExcel-dlg').dialog('close'); // close the dialog
                    $('#acceptance-dg').datagrid('reload');
                    $.messager.alert('提示', '操作成功！', 'info');
                } else {
                    $.messager.alert('保存失败', result.message, 'error');
                }
            }
        });

    } else {
        $.messager.alert('提示', '请选择xls格式或xlsx格式文件！', 'info');
        $('#uploadExcel').filebox('setValue', '');
    }

}
//保存验收清单标签
function saveAcceptancePostil(){
    $('#addAcceptancePostil-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
                $('#acceptancePostil-dlg').dialog('close'); // close the dialog
                $('#acceptance-dg').datagrid('reload');
        }
    });
}

function viewSample(){
    $('#sampleDlg').dialog('open').dialog('setTitle', '查看样例');
}


function importSN(){
    $('#importSN-dlg').dialog('open').dialog('setTitle', '序列号导入');
    $('#SNfile').filebox('setValue', '');
}

function uploadSN(){
    var row = $('#acceptance-dg').datagrid('getSelected');
    $('#SNID').val(row.id);
    //得到上传文件的全路径
    var fileName = $('#SNfile').filebox('getValue');
    //进行基本校验
    //对文件格式进行校验
    var d1 = /\.[^\.]+$/.exec(fileName);
    if (d1 == ".xls" || d1 == ".xlsx") {
        ////提交表单
        $('#importSN').form('submit', {
            url: 'acceptance/uploadSN',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (data) {
                var result = jQuery.parseJSON(data);
                if (result.success) {
                    $('#importSN-dlg').dialog('close'); // close the dialog
                    $('#acceptance-dg').datagrid('reload');
                    $.messager.alert('提示', '操作成功！', 'info');
                } else {
                    $.messager.alert('保存失败', result.message, 'error');
                }
            }
        });

    } else {
        $.messager.alert('提示', '请选择xls格式或xlsx格式文件！', 'info');
        $('#uploadExcel').filebox('setValue', '');
    }

}

function viewSN(){
    $('#viewSNDlg').dialog('open').dialog('setTitle', '查看序列号');
    var row = $('#acceptance-dg').datagrid('getSelected');
    $('#viewSN').val(row.productID);
}

function setAllShow(){
    $('#acceptance-dg').datagrid('showColumn',"insulationLeather");
    $('#acceptance-dg').datagrid('showColumn',"diameter");
    $('#acceptance-dg').datagrid('showColumn',"allLength");
    $('#acceptance-dg').datagrid('showColumn',"thick");
    $('#acceptance-dg').datagrid('showColumn',"outsideDiameter");
    $('#acceptance-dg').datagrid('showColumn',"size");
    $('#acceptance-dg').datagrid('showColumn',"color");
    $('#acceptance-dg').datagrid('showColumn',"material");
    $('#acceptance-dg').datagrid('showColumn',"boreDistance");
}

function setAllHidden(){
    $('#acceptance-dg').datagrid('hideColumn',"insulationLeather");
    $('#acceptance-dg').datagrid('hideColumn',"diameter");
    $('#acceptance-dg').datagrid('hideColumn',"allLength");
    $('#acceptance-dg').datagrid('hideColumn',"thick");
    $('#acceptance-dg').datagrid('hideColumn',"outsideDiameter");
    $('#acceptance-dg').datagrid('hideColumn',"size");
    $('#acceptance-dg').datagrid('hideColumn',"color");
    $('#acceptance-dg').datagrid('hideColumn',"material");
    $('#acceptance-dg').datagrid('hideColumn',"boreDistance");
}

function setAllNull(row,index){
    $('#acceptance-dg').datagrid('endEdit', index);
    row.customName="否";
    row.insulationLeather="";
    row.diameter="";
    row.allLength="";
    row.thick="";
    row.outsideDiameter="";
    row.size="";
    row.color="";
    row.material="";
    row.boreDistance="";
    $('#acceptance-dg').datagrid('updateRow', {
        index:index,
        row:row
    });
}

function saveExamine(){
    var row = $('#acceptance-dg').datagrid('getSelected');
    $.post('acceptance/addExamine',{
        id:row.id
    },function(result){
        var data = jQuery.parseJSON(result);
        if (data.success) {
            // 成功后刷新表格
            $('#acceptance-dg').datagrid('reload');
        } else {
            $.messager.alert('操作失败', data.message, 'error');
        }
    });
}

function removeExamine(){
    var row = $('#acceptance-dg').datagrid('getSelected');
    $.post('acceptance/deleteExamine',{
        id:row.id
    },function(result){
        var data = jQuery.parseJSON(result);
        if (data.success) {
            // 成功后刷新表格
            $('#acceptance-dg').datagrid('reload');
        } else {
            $.messager.alert('操作失败', data.message, 'error');
        }
    });
}

function openExamineList(){
    var row = $('#acceptance-dg').datagrid('getSelected');
    $('#examine-dg').datagrid({
        queryParams: {
            id:row.id
        }
    });
    $('#examineDlg').dialog('open').dialog('setTitle',"审核记录");
}



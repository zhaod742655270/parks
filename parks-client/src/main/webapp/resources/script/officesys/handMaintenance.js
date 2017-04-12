/**
 * Created by Zhao_d on 2016/12/15.
 */

$(function(){
    loadBtns('maintenance-btn', $('#menuId').val());
    $('#maintenance-dg').datagrid({
        title: '产品维修记录',
        toolbar: '#maintenance-toolbar',
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
        url: 'handMaintenance/maintenanceList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'projectName',title:'项目名称',sortable:true},
            {field:'number',title:'编号',sortable:true},
            {field:'productName',title:'产品名称型号',sortable:true}
        ]],
        columns:[[
            {field:'quantity',title:'数量'},
            {field:'registerPersonName',title:'登记人'},
            {field:'registerDate',title:'登记日期',sortable:true},
            {field:'hopeEndDate',title:'要求完成日期',sortable:true},
            {field:'assignPersonName',title:'指定处理人员'},
            {field:'approvePersonName',title:'审批人'},
            
            {field:'approveDate',title:'审批日期',sortable:true},
            {field:'approveNote',title:'审批备注',width:100},
            {field:'faultContent',title:'故障上报现象\\测试内容',width:120},
            {field:'reportPersonName',title:'上报人'},
            {field:'productNo',title:'产品编号'},
            {field:'firmwareVersion',title:'固件版本号'},

            {field:'handwareVersion',title:'硬件版本号'},
            {field:'repairBasis',title:'维修\\测试依据'},
            {field:'repairType',title:'维修\\测试类别'},
            {field:'faultVerify',title:'故障核实情况',width:120},
            {field:'verifyPersonName',title:'核实人'},

            {field:'techAnalysis',title:'技术分析',width:120},
            {field:'analyPersonName',title:'分析人'},
            {field:'repairContent',title:'维修内容',width:120},
            {field:'repairPersonName',title:'维修人'},
            {field:'repairResult',title:'维修结果',width:120},

            {field:'testPersonName',title:'测试人'},
            {field:'checkCost',title:'检测费'},
            {field:'manhourCost',title:'工时费'},
            {field:'materialsCost',title:'材料费'},
            {field:'repairCost',title:'维修成本(合计)'}
        ]],
        loadFilter:function(data){
            for(var i = 0; i < data.rows.length; i++){
                if(data.rows[i].registerDate == "1900-01-01"){
                    data.rows[i].registerDate = "";
                }
                if(data.rows[i].hopeEndDate == "1900-01-01"){
                    data.rows[i].hopeEndDate = "";
                }
                if(data.rows[i].approveDate == "1900-01-01"){
                    data.rows[i].approveDate = "";
                }
            }
            return data;
        },
        onDblClickRow:openMaintenance
    });
    
    //获得登录人ID及昵称
    $.ajax({
        url: '../managesys/user/getCurrentUser',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result) {
                userID = result.id;
                userNickname = result.nickname;
            }
        }
    });


    //获取登录人列表
    $('#registerPerson').combotree({
        required:true,
        url: 'handMaintenance/getRegPerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取登录人选项列表
    $('#registerPersonQuery').combobox({
        url: 'handMaintenance/getRegPerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取批准人列表
    $('#approvePerson').combotree({
        required:true,
        url: 'handMaintenance/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取上报人列表
    $('#reportPerson').combotree({
        url: 'handMaintenance/getRegPerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取核实人列表
    $('#verifyPerson').combotree({
        url: 'handMaintenance/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取分析人列表
    $('#analyPerson').combotree({
        url: 'handMaintenance/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取维修人列表
    $('#repairPerson').combotree({
        url: 'handMaintenance/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取测试人列表
    $('#testPerson').combotree({
        url: 'handMaintenance/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取指定处理人员列表
    $('#assignPerson').combotree({
        required:true,
        url: 'handMaintenance/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });
});

var userID = "";
var userNickname = "";

//列表查询
function maintenanceQuery(){
    var params={
        projectNameQuery:$('#projectNameQuery').val(),
        productNameQuery:$('#productNameQuery').val(),
        numberQuery:$('#numberQuery').val(),
        registerPersonQuery:$('#registerPersonQuery').combobox('getValue'),
        regDateBegQuery:$('#regDateBegQuery').datebox('getValue'),
        regDateEndQuery:$('#regDateEndQuery').datebox('getValue')
    };
    $('#maintenance-dg').datagrid({
        queryParams:params
    });
}

var formUrl = "";

//新增记录
function addMaintenance() {
    formUrl = "handMaintenance/addMaintenance";
    $('#addMaintenance').form('clear');
    var today = new Date();
    $('#registerDate').datebox('setValue', today.toLocaleDateString());   //登录日期
    $('#registerPerson').combobox('setValue', userID);        //登录人
    $('#registerPerson').combobox('setText', userNickname);
    $('#reportPerson').combobox('setValue', userID);        //上报人
    $('#reportPerson').combobox('setText', userNickname);
    $.ajax({
        url: 'handMaintenance/getNewNumber',
        dataType: 'json',
        success: function (number) {
            $('#number').textbox('setValue', number);        //自动添加编号
        }
    });

    //使用保存/取消按钮，将确定按钮隐藏
    document.getElementById("btn-ok").style.display = "";
    document.getElementById("btn-cancel").style.display = "";
    document.getElementById("btn-close").style.display = "none";
    
    $('#maintenanceDlg').dialog('open').dialog('setTitle', '新增维修记录');
}

//修改记录
function editMaintenance(){
    formUrl = "handMaintenance/editMaintenance";
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row){
        $('#addMaintenance').form('clear');

        $('#id').val(row.id);
        $('#projectName').val(row.projectName);
        $('#productName').val(row.productName);
        $('#number').textbox('setValue',row.number);
        $('#quantity').textbox('setValue',row.quantity);
        $('#registerPerson').combobox('setValue',row.registerPersonID);
        $('#registerPerson').combobox('setText',row.registerPersonName);
        $('#registerDate').datebox('setValue',row.registerDate);
        $('#hopeEndDate').datebox('setValue',row.hopeEndDate);
        $('#productNo').val(row.productNo);
        $('#firmwareVersion').val(row.firmwareVersion);
        $('#handwareVersion').val(row.handwareVersion);
        $('#repairBasis').val(row.repairBasis);
        $('#repairType').val(row.repairType);
        $('#reportPerson').combobox('setValue',row.reportPersonID);
        $('#reportPerson').combobox('setText',row.reportPersonName);
        $('#faultContent').textbox('setValue',row.faultContent);

        //使用保存/取消按钮，将确定按钮隐藏
        document.getElementById("btn-ok").style.display = "";
        document.getElementById("btn-cancel").style.display = "";
        document.getElementById("btn-close").style.display = "none";

        $('#maintenanceDlg').dialog('open').dialog('setTitle', '修改维修记录');
    }else {
        $.messager.alert('提示', '需要选择一条产品维修记录，才能进行编辑操作。', 'info');
    }
}

//查看记录
function openMaintenance(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row){
        $('#addMaintenance').form('clear');

        $('#id').val(row.id);
        $('#projectName').val(row.projectName);
        $('#productName').val(row.productName);
        $('#number').textbox('setValue',row.number);
        $('#quantity').textbox('setValue',row.quantity);
        $('#registerPerson').combobox('setValue',row.registerPersonID);
        $('#registerPerson').combobox('setText',row.registerPersonName);
        $('#registerDate').datebox('setValue',row.registerDate);
        $('#hopeEndDate').datebox('setValue',row.hopeEndDate);
        $('#productNo').val(row.productNo);
        $('#firmwareVersion').val(row.firmwareVersion);
        $('#handwareVersion').val(row.handwareVersion);
        $('#repairBasis').val(row.repairBasis);
        $('#repairType').val(row.repairType);
        $('#reportPerson').combobox('setValue',row.reportPersonID);
        $('#reportPerson').combobox('setText',row.reportPersonName);
        $('#faultContent').textbox('setValue',row.faultContent);

        //使用确定按钮，将保存/取消按钮隐藏
        document.getElementById("btn-ok").style.display = "none";
        document.getElementById("btn-cancel").style.display = "none";
        document.getElementById("btn-close").style.display = "";

        $('#maintenanceDlg').dialog('open').dialog('setTitle', '产品维修记录');
    }
}

//保存修改
function saveMaintenance(){
    $('#addMaintenance').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#maintenanceDlg').dialog('close');
                $('#maintenance-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

//删除记录
function deleteMaintenance(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("handMaintenance/deleteMaintenance", {
                    idQuery: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#maintenance-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });
    }else {
        $.messager.alert('提示', '需要选择一条产品维修记录，才能进行删除操作。', 'info');
    }
}

//处理故障
function begHandle(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row){
        $('#addHandle').form('clear');

        $('#verifyPerson').combotree({
            queryParams:{
                userID:userID,
                oldUserID:row.verifyPersonID
            }
        });
        $('#analyPerson').combotree({
            queryParams:{
                userID:userID,
                oldUserID:row.analyPersonID
            }
        });
        $('#repairPerson').combotree({
            queryParams:{
                userID:userID,
                oldUserID:row.repairPersonID
            }
        });
        $('#testPerson').combotree({
            queryParams:{
                userID:userID,
                oldUserID:row.testPersonID
            }
        });

        $('#idHandle').val(row.id);
        $('#faultVerify').textbox('setValue',row.faultVerify);
        $('#verifyPerson').combobox('setValue', row.verifyPersonID);
        $('#verifyPerson').combobox('setText', row.verifyPersonName);
        $('#techAnalysis').textbox('setValue',row.techAnalysis);
        $('#analyPerson').combobox('setValue', row.analyPersonID);
        $('#analyPerson').combobox('setText', row.analyPersonName);
        $('#repairContent').textbox('setValue',row.repairContent);
        $('#repairPerson').combobox('setValue', row.repairPersonID);
        $('#repairPerson').combobox('setText', row.repairPersonName);
        $('#repairResult').textbox('setValue',row.repairResult);
        $('#testPerson').combobox('setValue', row.testPersonID);
        $('#testPerson').combobox('setText', row.testPersonName);
        $('#checkCost').textbox('setValue',row.checkCost);
        $('#manhourCost').textbox('setValue',row.manhourCost);
        $('#materialsCost').textbox('setValue',row.materialsCost);
        $('#repairCost').textbox('setValue',row.repairCost);

        $('#handleDlg').dialog('open').dialog('setTitle', '修改验收清单');
    }else {
        $.messager.alert('提示', '需要选择一条产品维修记录，才能进行处理操作。', 'info');
    }
}

//保存处理故障
function saveHandle(){
    $('#addHandle').form('submit', {
        url: "handMaintenance/editMaintenance",
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#handleDlg').dialog('close');
                $('#maintenance-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

//打开审批界面
function addApprove(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row){
        $('#addApprove').form('clear');
        $('#approveId').val(row.id);
        if(row.approvePersonName) {          //审批人
            $('#approvePerson').combobox('setValue',row.approvePersonID);
            $('#approvePerson').combobox('setText',row.approvePersonName);
        }else {
            $('#approvePerson').combobox('setValue', userID);
            $('#approvePerson').combobox('setText', userNickname);
        }
        if(row.approveDate) {            //审批日期
            $('#approveDate').datebox('setValue', row.approveDate);
        }else{
            var today = new Date();
            $('#approveDate').datebox('setValue',today.toLocaleDateString());
        }
        $('#approveNote').textbox('setValue',row.approveNote);      //审批备注
        $('#approveDlg').dialog('open').dialog('setTitle', '审批');
    }else{
        $.messager.alert('提示', '需要选择一条维修记录，才能进行审批。', 'info');
    }
}

//保存审批记录
function saveApprove(){
    $('#addApprove').form('submit', {
        url: 'handMaintenance/editMaintenance',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#approveDlg').dialog('close');
                $('#maintenance-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

//打开指定处理人员界面
function addAssign(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row){
        $('#addAssign').form('clear');
        $('#assignId').val(row.id);
        $('#assignPerson').combobox('setValue',row.assignPersonId);
        $('#assignPerson').combobox('setText',row.assignPersonName);

        $('#assignDlg').dialog('open').dialog('setTitle', '指定处理人员');
    }else{
        $.messager.alert('提示', '需要选择一条维修记录，才能指定处理人员。', 'info');
    }
}

//保存指定人员
function saveAssign(){
    $('#addAssign').form('submit', {
        url: 'handMaintenance/editMaintenance',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#assignDlg').dialog('close');
                $('#maintenance-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

//导出Word表格
function exportWord(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row) {
        $('#query-form').form({
            queryParams: {
                idQuery: row.id
            }
        });
        $('#query-form').form('submit', {
            url: "handMaintenance/exportWord",
            method: "post",
            success: function (data) {
                var result = jQuery.parseJSON(data);
                if (!result.success) {
                    $.messager.alert("Word导出失败", result.message, 'error');
                }
            }
        })
    }else{
        $.messager.alert('提示', '需要选择一条产品维修记录，才能进行导出操作。', 'info');
    }
}

//导出列表
function exportExcel(){
    $.messager.confirm('确认','是否将列表导出为Excel文件',function(r){
        if(r) {
            $('#query-form').form({
                queryParams:{
                    projectNameQuery:$('#projectNameQuery').val(),
                    productNameQuery:$('#productNameQuery').val(),
                    numberQuery:$('#numberQuery').val(),
                    registerPersonQuery:$('#registerPersonQuery').val(),
                    regDateBegQuery:$('#regDateBegQuery').datebox('getValue'),
                    regDateEndQuery:$('#regDateEndQuery').datebox('getValue')
                }
            });
            $('#query-form').form('submit',{
                url:"handMaintenance/exportExcel",
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

//选择维修依据
function selectBasis(){
    $('#repairBasisDlg').dialog('refresh');
    var repairBasis = $('#repairBasis').val();
    var basisStrings = repairBasis.split(';');
    for(var i in basisStrings){
        if(basisStrings[i] == "产品说明书"){
            var repairBasis1 = document.getElementById("repairBasis-1");
            repairBasis1.checked = true;
        }else if(basisStrings[i] == "技术要求"){
            var repairBasis2 = document.getElementById("repairBasis-2");
            repairBasis2.checked = true;
        }else if(basisStrings[i] == "工程设计方案"){
            var repairBasis3 = document.getElementById("repairBasis-3");
            repairBasis3.checked = true;
        }else{
            if(basisStrings[i] != "") {
                var repairBasis4 = document.getElementById("repairBasis-4");
                repairBasis4.checked = true;
                $('#repairBasis-4text').val(basisStrings[i]);
            }
        }
    }
    $('#repairBasisDlg').dialog('open').dialog('setTitle', '维修\\测试依据');
}

//保存维修依据
function saveBasis(){
    var repairBasis = "";
    var repairBasis1 = document.getElementById("repairBasis-1");
    var repairBasis2 = document.getElementById("repairBasis-2");
    var repairBasis3 = document.getElementById("repairBasis-3");
    var repairBasis4 = document.getElementById("repairBasis-4");
    if(repairBasis1.checked){
        repairBasis = repairBasis + "产品说明书;";
    }
    if(repairBasis2.checked){
        repairBasis = repairBasis + "技术要求;";
    }
    if(repairBasis3.checked){
        repairBasis = repairBasis + "工程设计方案;";
    }
    if(repairBasis4.checked){
        repairBasis = repairBasis + $('#repairBasis-4text').val() + ";";
    }
    $('#repairBasis').val(repairBasis);
    $('#repairBasisDlg').dialog('close');
}

//选择维修类别
function selectType(){
    $('#repairTypeDlg').dialog('refresh');
    var repairType = $('#repairType').val();
    var typeStrings = repairType.split(';');
    for(var i in typeStrings){
        if(typeStrings[i] == "功能"){
            var repairType1 = document.getElementById("repairType-1");
            repairType1.checked = true;
        }else if(typeStrings[i] == "性能"){
            var repairType2 = document.getElementById("repairType-2");
            repairType2.checked = true;
        }else if(typeStrings[i] == "产品选型"){
            var repairType3 = document.getElementById("repairType-3");
            repairType3.checked = true;
        }else if(typeStrings[i] == "高温"){
            var repairType4 = document.getElementById("repairType-4");
            repairType4.checked = true;
        }else if(typeStrings[i] == "低温"){
            var repairType5 = document.getElementById("repairType-5");
            repairType5.checked = true;
        }else if(typeStrings[i] == "湿热"){
            var repairType6 = document.getElementById("repairType-6");
            repairType6.checked = true;
        }else if(typeStrings[i] == "防水"){
            var repairType7 = document.getElementById("repairType-7");
            repairType7.checked = true;
        }else if(typeStrings[i] == "振动"){
            var repairType8 = document.getElementById("repairType-8");
            repairType8.checked = true;
        }else if(typeStrings[i] == "跌落"){
            var repairType9 = document.getElementById("repairType-9");
            repairType9.checked = true;
        }else if(typeStrings[i] == "绝缘电阻"){
            var repairType10 = document.getElementById("repairType-10");
            repairType10.checked = true;
        }else if(typeStrings[i] == "抗电强度"){
            var repairType11 = document.getElementById("repairType-11");
            repairType11.checked = true;
        }else if(typeStrings[i] == "外观"){
            var repairType12 = document.getElementById("repairType-12");
            repairType12.checked = true;
        }else if(typeStrings[i] == "外壳防护"){
            var repairType13 = document.getElementById("repairType-13");
            repairType13.checked = true;
        }else if(typeStrings[i] == "机械强度"){
            var repairType14 = document.getElementById("repairType-14");
            repairType14.checked = true;
        }else if(typeStrings[i] == "静电放电"){
            var repairType15 = document.getElementById("repairType-15");
            repairType15.checked = true;
        }else{
            if(typeStrings[i] != "") {
                var repairType16 = document.getElementById("repairType-16");
                repairType16.checked = true;
                $('#repairType-16text').val(typeStrings[i]);
            }
        }
    }
    $('#repairTypeDlg').dialog('open').dialog('setTitle', '维修\\测试类型');
}

//保存维修类别
function saveTypes(){
    var repairType = "";
    var repairType1 = document.getElementById("repairType-1");
    var repairType2 = document.getElementById("repairType-2");
    var repairType3 = document.getElementById("repairType-3");
    var repairType4 = document.getElementById("repairType-4");
    var repairType5 = document.getElementById("repairType-5");
    var repairType6 = document.getElementById("repairType-6");
    var repairType7 = document.getElementById("repairType-7");
    var repairType8 = document.getElementById("repairType-8");
    var repairType9 = document.getElementById("repairType-9");
    var repairType10 = document.getElementById("repairType-10");
    var repairType11 = document.getElementById("repairType-11");
    var repairType12 = document.getElementById("repairType-12");
    var repairType13 = document.getElementById("repairType-13");
    var repairType14 = document.getElementById("repairType-14");
    var repairType15 = document.getElementById("repairType-15");
    var repairType16 = document.getElementById("repairType-16");
    if(repairType1.checked){
        repairType = repairType + "功能;";
    }
    if(repairType2.checked){
        repairType = repairType + "性能;";
    }
    if(repairType3.checked){
        repairType = repairType + "产品选型;";
    }
    if(repairType4.checked){
        repairType = repairType + "高温;";
    }
    if(repairType5.checked){
        repairType = repairType + "低温;";
    }
    if(repairType6.checked){
        repairType = repairType + "湿热;";
    }
    if(repairType7.checked){
        repairType = repairType + "防水;";
    }
    if(repairType8.checked){
        repairType = repairType + "振动;";
    }
    if(repairType9.checked){
        repairType = repairType + "跌落;";
    }
    if(repairType10.checked){
        repairType = repairType + "绝缘电阻;";
    }
    if(repairType11.checked){
        repairType = repairType + "抗电强度;";
    }
    if(repairType12.checked){
        repairType = repairType + "外观;";
    }
    if(repairType13.checked){
        repairType = repairType + "外壳防护;";
    }
    if(repairType14.checked){
        repairType = repairType + "机械强度;";
    }
    if(repairType15.checked) {
        repairType = repairType + "静电放电;";
    }
    if(repairType16.checked){
        repairType = repairType + $('#repairType-16text').val() + ";";
    }
    $('#repairType').val(repairType);
    $('#repairTypeDlg').dialog('close');
}




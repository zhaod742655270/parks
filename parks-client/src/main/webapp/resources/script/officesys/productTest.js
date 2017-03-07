/**
 * Created by Zhao_d on 2016/12/28.
 */
$.ajaxSetup({cache:false});
$(function(){
    loadBtns('productTest-btn', $('#menuId').val());
    $('#productTest-dg').datagrid({
        title: '测试记录',
        toolbar: '#productTest-toolbar',
        method: 'post',
        nowrap: true,
        sortName: 'number',
        sortOrder: 'desc',
        striped: true,
        rownumbers: true,
        fitColumns: true,
        fit: true,
        singleSelect: true,
        pagination: true,
        url: 'productTest/productTestList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'number',title:'编号'},
            {field:'productName',title:'产品名称型号'}
        ]],
        columns:[[
            {field:'extractPosition',title:'产品提取位置'},
            {field:'registerPersonName',title:'登记人'},
            {field:'registerDate',title:'登记日期'},
            {field:'hopeEndDate',title:'要求完成日期'},
            {field:'approvePersonName',title:'批准人'},
            {field:'approveDate',title:'批准日期'},
            {field:'quantity',title:'数量'},
            {field:'testBasis',title:'测试依据'},
            {field:'testType',title:'测试类别'},
            {field:'testDesc',title:'测试功能项描述',width:120},

            {field:'testPersonName',title:'测试人'},
            {field:'planBegDate',title:'计划开始时间'},
            {field:'planEndDate',title:'计划结束时间'},
            {field:'begDate',title:'实际开始时间'},
            {field:'endDate',title:'实际结束时间'},
            {field:'finishedBug',title:'已解决BUG编号',width:120},
            {field:'unfinishedBug',title:'未解决BUG编号',width:120},
            {field:'finallyName',title:'最终型号名称'},
            {field:'finallyPosition',title:'最终提取位置'},
            {field:'summany',title:'总结',width:120},
            {field:'note',title:'备注',width:120}
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
                if(data.rows[i].planBegDate == "1900-01-01"){
                    data.rows[i].planBegDate = "";
                }
                if(data.rows[i].planEndDate == "1900-01-01"){
                    data.rows[i].planEndDate = "";
                }
                if(data.rows[i].begDate == "1900-01-01"){
                    data.rows[i].begDate = "";
                }
                if(data.rows[i].endDate == "1900-01-01"){
                    data.rows[i].endDate = "";
                }
            }
            return data;
        }
    });
    
    $('#registerPerson').combotree({
        url: 'productTest/getRegPerson',
        valueField: 'id',
        textField: 'text'
    });

    $('#testPerson').combotree({
        url: 'productTest/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });

    $('#approvePerson').combotree({
        url: 'productTest/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });
});

//列表查询
function productTestQuery(){
    var params={
        productNameQuery:$('#productNameQuery').val(),
        numberQuery:$('#numberQuery').val(),
        registerPersonQuery:$('#registerPersonQuery').val(),
        regDateBegQuery:$('#regDateBegQuery').datebox('getValue'),
        regDateEndQuery:$('#regDateEndQuery').datebox('getValue'),
        testPersonQuery:$('#testPersonQuery').val()
    };
    $('#productTest-dg').datagrid({
        queryParams:params
    });
}

var formUrl = "";

//新增记录
function addProductTest(){
    formUrl = "productTest/addProductTest";
    $('#addProductTest').form('clear');
    var today=new Date();
    $('#registerDate').datebox('setValue',today.toLocaleDateString());   //登录日期
    $.ajax({
        url: '../managesys/user/getCurrentUser',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result) {
                $('#registerPerson').combobox('setValue',result.id);        //登录人
                $('#registerPerson').combobox('setText',result.userName);
            }
        }
    });
    $.ajax({
        url:'productTest/getNewNumber',
        dataType:'json',
        success:function(number){
            $('#number').textbox('setValue',number);        //自动添加编号
        }
    });
    $('#productTestDlg').dialog('open').dialog('setTitle', '新增测试记录');
}

//修改记录
function editProductTest(){
    formUrl = "productTest/editProductTest";
    var row = $('#productTest-dg').datagrid('getSelected');
    if(row){
        $('#addProductTest').form('clear');

        $('#id').val(row.id);
        $('#productName').val(row.productName);
        $('#number').textbox('setValue',row.number);
        $('#extractPosition').textbox('setValue',row.extractPosition);
        $('#registerPerson').combobox('setValue',row.registerPersonID);
        $('#registerPerson').combobox('setText',row.registerPersonName);
        $('#registerDate').datebox('setValue',row.registerDate);
        $('#hopeEndDate').datebox('setValue',row.hopeEndDate);
        $('#quantity').textbox('setValue',row.quantity);
        $('#testBasis').val(row.testBasis);
        $('#testType').val(row.testType);
        $('#testDesc').textbox('setValue',row.testDesc);

        $('#productTestDlg').dialog('open').dialog('setTitle', '修改测试记录');
    }else {
        $.messager.alert('提示', '需要选择一条测试记录，才能进行编辑操作。', 'info');
    }
}

//保存修改
function saveProductTest(){
    $('#addProductTest').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#productTestDlg').dialog('close');
                $('#productTest-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

//删除记录
function deleteProductTest(){
    var row = $('#productTest-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("productTest/deleteProductTest", {
                    idQuery: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#productTest-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });
    }else {
        $.messager.alert('提示', '需要选择一条测试记录，才能进行删除操作。', 'info');
    }
}

//填写测试结果
function begHandle(){
    var row = $('#productTest-dg').datagrid('getSelected');
    if(row){
        $('#addHandle').form('clear');

        $('#idHandle').val(row.id);
        if(row.testPersonName){             //测试人
            $('#testPerson').combobox('setValue',row.testPersonID);
            $('#testPerson').combobox('setText',row.testPersonName);
        }else{
            $.ajax({
                url: '../managesys/user/getCurrentUser',
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    if (result) {
                        $('#testPerson').combobox('setValue',result.id);
                        $('#testPerson').combobox('setText',result.userName);
                    }
                }
            });
        }
        $('#planBegDate').datebox('setValue',row.planBegDate);
        $('#planEndDate').datebox('setValue',row.planEndDate);
        $('#begDate').datebox('setValue',row.begDate);
        $('#endDate').datebox('setValue',row.endDate);
        $('#finishedBug').textbox('setValue',row.finishedBug);
        $('#unfinishedBug').textbox('setValue',row.unfinishedBug);
        $('#finallyName').textbox('setValue',row.finallyName);
        $('#finallyPosition').textbox('setValue',row.finallyPosition);
        $('#summany').textbox('setValue',row.summany);
        $('#note').textbox('setValue',row.note);

        $('#handleDlg').dialog('open').dialog('setTitle', '填写测试结果');
    }else {
        $.messager.alert('提示', '需要选择一条测试记录，才能进行修改操作。', 'info');
    }
}

//保存处理故障
function saveHandle(){
    $('#addHandle').form('submit', {
        url: "productTest/editProductTest",
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#handleDlg').dialog('close');
                $('#productTest-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

//打开审批界面
function addApprove(){
    var row = $('#productTest-dg').datagrid('getSelected');
    if(row){
        $('#addApprove').form('clear');
        $('#approveId').val(row.id);
        if(row.approvePersonName) {          //审批人
            $('#approvePerson').combobox('setValue',row.approvePersonID);
            $('#approvePerson').combobox('setText',row.approvePersonName);
        }else{
            $.ajax({
                url: '../managesys/user/getCurrentUser',
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    if (result) {
                        $('#approvePerson').combobox('setValue',result.id);
                        $('#approvePerson').combobox('setText',result.userName);
                    }
                }
            });
        }
        if(row.approveDate) {            //审批日期
            $('#approveDate').datebox('setValue', row.approveDate);
        }else{
            var today = new Date();
            $('#approveDate').datebox('setValue',today.toLocaleDateString());
        }
        $('#approveDlg').dialog('open').dialog('setTitle', '审批');
    }else{
        $.messager.alert('提示', '需要选择一条维修记录，才能进行审批。', 'info');
    }
}

//保存审批记录
function saveApprove(){
    $('#addApprove').form('submit', {
        url: 'productTest/editProductTest',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#approveDlg').dialog('close');
                $('#productTest-dg').datagrid('reload');
            }else {
                $.messager.alert('操作失败', data.message, 'error');
            }
        }
    });
}

//导出Word表格
function exportWord(){
    var row = $('#productTest-dg').datagrid('getSelected');
    if(row) {
        $('#query-form').form({
            queryParams: {
                idQuery: row.id
            }
        });
        $('#query-form').form('submit', {
            url: "productTest/exportWord",
            method: "post",
            success: function (data) {
                var result = jQuery.parseJSON(data);
                if (!result.success) {
                    $.messager.alert("Word导出失败", result.message, 'error');
                }
            }
        })
    }else{
        $.messager.alert('提示', '需要选择一条测试记录，才能进行导出操作。', 'info');
    }
}

//导出Excel列表
function exportExcel(){
    $.messager.confirm('确定','是否将列表导出为Excel文件', function(r){
       if(r){
           $('#query-form').form({
               queryParams:{
                   productNameQuery:$('#productNameQuery').val(),
                   numberQuery:$('#numberQuery').val(),
                   registerPersonQuery:$('#registerPersonQuery').val(),
                   regDateBegQuery:$('#regDateBegQuery').datebox('getValue'),
                   regDateEndQuery:$('#regDateEndQuery').datebox('getValue'),
                   testPersonQuery:$('#testPersonQuery').val()
               }
           });
           $('#query-form').form('submit',{
               url:"productTest/exportExcel",
               method:'post',
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

//选择测试依据
function selectBasis(){
    $('#testBasisDlg').dialog('refresh');
    var testBasis = $('#testBasis').val();
    var basisStrings = testBasis.split(';');
    for(var i in basisStrings){
        if(basisStrings[i] == "说明书"){
            var basis1 = document.getElementById("TestBasis-1");
            basis1.checked = true;
        }else if(basisStrings[i] == "技术要求"){
            var basis2 = document.getElementById("testBasis-2");
            basis2.checked = true;
        }else if(basisStrings[i] == "测试用例"){
            var basis3 = document.getElementById("testBasis-3");
            basis3.checked = true;
        }else{
            if(basisStrings[i] != "") {
                var basis4 = document.getElementById("testBasis-4");
                basis4.checked = true;
                $('#testBasis-4text').val(basisStrings[i]);
            }
        }
    }
    $('#testBasisDlg').dialog('open').dialog('setTitle', '测试依据');
}

//保存测试依据
function saveBasis(){
    var testBasis = "";
    var testBasis1 = document.getElementById("testBasis-1");
    var testBasis2 = document.getElementById("testBasis-2");
    var testBasis3 = document.getElementById("testBasis-3");
    var testBasis4 = document.getElementById("testBasis-4");
    if(testBasis1.checked){
        testBasis = testBasis + "说明书;";
    }
    if(testBasis2.checked){
        testBasis = testBasis + "技术要求;";
    }
    if(testBasis3.checked){
        testBasis = testBasis + "测试用例;";
    }
    if(testBasis4.checked){
        testBasis = testBasis + $('#testBasis-4text').val() + ";";
    }
    $('#testBasis').val(testBasis);
    $('#testBasisDlg').dialog('close');
}

//选择测试类型
function selectType(){
    $('#testTypeDlg').dialog('refresh');
    var testType = $('#testType').val();
    var typeStrings = testType.split(';');
    for(var i in typeStrings){
        if(typeStrings[i] == "功能"){
            var testType1 = document.getElementById("testType-1");
            testType1.checked = true;
        }else if(typeStrings[i] == "性能"){
            var testType2 = document.getElementById("testType-2");
            testType2.checked = true;
        }else if(typeStrings[i] == "高温"){
            var testType3 = document.getElementById("testType-3");
            testType3.checked = true;
        }else if(typeStrings[i] == "低温"){
            var testType4 = document.getElementById("testType-4");
            testType4.checked = true;
        }else if(typeStrings[i] == "湿热"){
            var testType5 = document.getElementById("testType-5");
            testType5.checked = true;
        }else if(typeStrings[i] == "振动"){
            var testType6 = document.getElementById("testType-6");
            testType6.checked = true;
        }else if(typeStrings[i] == "跌落"){
            var testType7 = document.getElementById("testType-7");
            testType7.checked = true;
        }else if(typeStrings[i] == "防水"){
            var testType8 = document.getElementById("testType-8");
            testType8.checked = true;
        }else if(typeStrings[i] == "卡片测试"){
            var testType9 = document.getElementById("testType-9");
            testType9.checked = true;
        }else if(typeStrings[i] == "绝缘电阻"){
            var testType10 = document.getElementById("testType-10");
            testType10.checked = true;
        }else if(typeStrings[i] == "抗电强度"){
            var testType11 = document.getElementById("testType-11");
            testType11.checked = true;
        }else if(typeStrings[i] == "外壳防护"){
            var testType12 = document.getElementById("testType-12");
            testType12.checked = true;
        }else if(typeStrings[i] == "外观"){
            var testType13 = document.getElementById("testType-13");
            testType13.checked = true;
        }else if(typeStrings[i] == "机械强度"){
            var testType14 = document.getElementById("testType-14");
            testType14.checked = true;
        }else if(typeStrings[i] == "静电放电"){
            var testType15 = document.getElementById("testType-15");
            testType15.checked = true;
        }
    }
    $('#testTypeDlg').dialog('open').dialog('setTitle', '测试类型');
}

//保存测试类型
function saveTypes(){
    var testType = "";
    var testType1 = document.getElementById("testType-1");
    var testType2 = document.getElementById("testType-2");
    var testType3 = document.getElementById("testType-3");
    var testType4 = document.getElementById("testType-4");
    var testType5 = document.getElementById("testType-5");
    var testType6 = document.getElementById("testType-6");
    var testType7 = document.getElementById("testType-7");
    var testType8 = document.getElementById("testType-8");
    var testType9 = document.getElementById("testType-9");
    var testType10 = document.getElementById("testType-10");
    var testType11 = document.getElementById("testType-11");
    var testType12 = document.getElementById("testType-12");
    var testType13 = document.getElementById("testType-13");
    var testType14 = document.getElementById("testType-14");
    var testType15 = document.getElementById("testType-15");
    if(testType1.checked){
        testType = testType + "功能;";
    }
    if(testType2.checked){
        testType = testType + "性能;";
    }
    if(testType3.checked){
        testType = testType + "高温;";
    }
    if(testType4.checked){
        testType = testType + "低温;";
    }
    if(testType5.checked){
        testType = testType + "湿热;";
    }
    if(testType6.checked){
        testType = testType + "振动;";
    }
    if(testType7.checked){
        testType = testType + "跌落;";
    }
    if(testType8.checked){
        testType = testType + "防水;";
    }
    if(testType9.checked){
        testType = testType + "卡片测试;";
    }
    if(testType10.checked){
        testType = testType + "绝缘电阻;";
    }
    if(testType11.checked){
        testType = testType + "抗电强度;";
    }
    if(testType12.checked){
        testType = testType + "外壳防护;";
    }
    if(testType13.checked){
        testType = testType + "外观;";
    }
    if(testType14.checked){
        testType = testType + "机械强度;";
    }
    if(testType15.checked) {
        testType = testType + "静电放电;";
    }
    $('#testType').val(testType);
    $('#testTypeDlg').dialog('close');
}
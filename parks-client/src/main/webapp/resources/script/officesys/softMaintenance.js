/**
 * Created by Zhao_d on 2016/12/7.
 */
$(function(){
    loadBtns('maintenance-btn', $('#menuId').val());
    $('#maintenance-dg').datagrid({
        title:'售后维护管理',
        toolbar:'#maintenance-toolbar',
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
        url:'softMaintenance/maintenanceList',
        frozenColumns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'projectName',title:'项目名称'},
            {field:'number',title:'编号',align:'left'},
            {field:'productName',title:'产品名称'}
        ]],
        columns:[[
            {field:'regPersonName',title:'登记人'},
            {field:'regDate',title:'登记日期'},
            {field:'hopeEndDate',title:'要求完成日期'},
            {field:'contractsName',title:'项目联系人'},
            {field:'phoneNo',title:'联系方式'},
            {field:'assignPersonName',title:'指定处理人员'},
            {field:'faultDesc',title:'故障现象',width:80},
            {field:'result',title:'结论',width:80},
            {field:'resultPersonName',title:'总结人'},
            {field:'resultDate',title:'总结日期'}
        ]],
        loadFilter:function(data){
            for(var i = 0; i < data.rows.length; i++){
                if(data.rows[i].regDate == "1900-01-01"){
                    data.rows[i].regDate = "";
                }
                if(data.rows[i].hopeEndDate == "1900-01-01"){
                    data.rows[i].hopeEndDate = "";
                }
                if(data.rows[i].resultDate == "1900-01-01"){
                    data.rows[i].resultDate = "";
                }
            }
            return data;
        },
        onDblClickRow:editMaintenance
    });

    $('#handle-dg').datagrid({
        toolbar: '#handle-toolbar',
        method: 'post',
        nowrap: true,
        sortName:'handleBegTime',
        sortOrder:'asc',
        striped: true,
        rownumbers: true,
        fitColumns: true,
        fit: true,
        singleSelect: true,
        pagination: true,
        url: 'softMaintenance/handleList',
        columns:[[
            {field:'id',title:'ID',align:'left',hidden:true},
            {field:'parentIdFK',title:'parentID',align:'left',hidden:true},
            {field:'handlePersonName',title:'承担人'},
            {field:'handleBegTime',title:'开始时间'},
            {field:'handleDesc',title:'处理过程'},
            {field:'handleEndTime',title:'结束时间'},
            {field:'handleResult',title:'处理结果'}
        ]],
        loadFilter:function(data){
            for(var i = 0; i < data.rows.length; i++){
                if(data.rows[i].handleBegTime == "1900-01-01"){
                    data.rows[i].handleBegTime = "";
                }
                if(data.rows[i].handleEndTime == "1900-01-01"){
                    data.rows[i].handleEndTime = "";
                }
            }
            return data;
        }
    });

    //处理结果列表
    $('#handleResult').combobox({
        data: [{"id": "成功", "text": "成功"}, {"id": "失败", "text": "失败"}],
        valueField: 'id',
        textField: 'text'
    });

    //获取登录人列表
    $('#regPerson').combotree({
        required:true,
        url: 'softMaintenance/getRegPerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取登录人选项列表
    $('#regPersonQuery').combobox({
        url: 'softMaintenance/getRegPerson',
        valueField: 'id',
        textField: 'text'
    });

    //获取项目联系人列表
    $('#projectContracts').combotree({
        required:true,
        url: 'softMaintenance/getRegPerson',
        valueField: 'id',
        textField: 'text'
    });
    
    //获得总结人列表
    $('#resultPerson').combotree({
        required:true,
        url:'softMaintenance/getHandlePerson',
        valueField:'id',
        textField:'text'
    });

    //获得承担人列表
    $('#handlePerson').combotree({
        required:true,
        url:'softMaintenance/getHandlePerson',
        valueField:'id',
        textField:'text'
    });

    //获取指定处理人员列表
    $('#assignPerson').combotree({
        required:true,
        url: 'softMaintenance/getHandlePerson',
        valueField: 'id',
        textField: 'text'
    });
});

//列表查询
function maintenanceQuery(){
    var params={
        projectNameQuery:$('#projectNameQuery').val(),
        productNameQuery:$('#productNameQuery').val(),
        numberQuery:$('#numberQuery').val(),
        regPersonQuery:$('#regPersonQuery').combobox('getValue'),
        regDateBegQuery:$('#regDateBegQuery').datebox('getValue'),
        regDateEndQuery:$('#regDateEndQuery').datebox('getValue')
    };
    $('#maintenance-dg').datagrid({
        queryParams:params
    });
}

var formUrl = "";

//新增记录
function addMaintenance(){
    formUrl = "softMaintenance/addMaintenance";
    $('#addMaintenance').form('clear');
    var today=new Date();
    $('#regDate').datebox('setValue',today.toLocaleDateString());   //登录日期
    $.ajax({
        url: '../managesys/user/getCurrentUser',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result) {
                $('#regPerson').combobox('setValue',result.id);        //登录人
                $('#regPerson').combobox('setText',result.nickname);
            }
        }
    });
    $.ajax({
        url:'softMaintenance/getNewNumber',
        dataType:'json',
        success:function(number){
            $('#number').textbox('setValue',number);        //自动添加编号
        }
    });
    $('#maintenanceDlg').dialog('open').dialog('setTitle', '新增售后维护记录');
}

//修改记录
function editMaintenance(){
    formUrl = "softMaintenance/editMaintenance";
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row){
        $('#addMaintenance').form('clear');

        $('#id').val(row.id);
        $('#projectName').val(row.projectName);
        $('#productName').val(row.productName);
        $('#number').textbox('setValue',row.number);
        $('#regPerson').combobox('setValue',row.regPersonID);
        $('#regPerson').combobox('setText',row.regPersonName);
        $('#regDate').datebox('setValue', row.regDate);
        $('#hopeEndDate').datebox('setValue', row.hopeEndDate);
        $('#projectContracts').combobox('setValue',row.contractsID);
        $('#projectContracts').combobox('setText',row.contractsName);
        $('#phoneNo').textbox('setValue',row.phoneNo);
        $('#faultDesc').textbox('setValue',row.faultDesc);

        $('#maintenanceDlg').dialog('open').dialog('setTitle', '修改售后维护记录');
    }else {
        $.messager.alert('提示', '需要选择一条售后维护记录，才能进行编辑操作。', 'info');
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
                $.post("softMaintenance/deleteMaintenance", {
                    id: row.id
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
        $.messager.alert('提示', '需要选择一条售后维护记录，才能进行删除操作。', 'info');
    }
}

//打开处理过程
function begHandle(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    if (row) {
        $('#handle-dg').datagrid({
            queryParams: {
                parentIdFK:row.id
            },
            url:"softMaintenance/handleList"
        });
        $('#handleDgd').dialog('open').dialog('setTitle', '处理过程列表');
    } else{
        $.messager.alert('提示', '需要选择一个维修记录，才能查看处理过程。', 'info');
    }
}

//处理过程查询
function handleQuery(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    var params={
        parentIdFK:row.id,
        handlePersonQuery:$('#handlePersonQuery').val(),
        handleBegTimeQuery:$('#handleBegTimeQuery').datebox('getValue'),
        handleEndTimeQuery:$('#handleEndTimeQuery').datebox('getValue'),
        handleResultQuery:$('#handleResultQuery').val()
    };
    $('#handle-dg').datagrid({
        queryParams:params
    });
}

//新增处理过程
function addHandle(){
    formUrl = "softMaintenance/addHandle";
    var row = $('#maintenance-dg').datagrid('getSelected');
    $('#handleDlg').form('clear');
    $('#parentIdFK').val(row.id);
    $.ajax({
        url: '../managesys/user/getCurrentUser',
        type: 'get',
        dataType: 'json',
        success: function (result) {
            if (result) {
                $('#handlePerson').combobox('setValue',result.id);        //承担人
                $('#handlePerson').combobox('setText',result.nickname);
            }
        }
    });
    $('#handleDlg').dialog('open').dialog('setTitle', '新增处理过程');
}

//修改处理过程
function editHandle(){
    formUrl = "softMaintenance/editHandle";
    var row = $('#handle-dg').datagrid('getSelected');
    if(row){
        $('#addHandle').form('clear');

        $('#idHandle').val(row.id);
        $('#parentIdFK').val(row.parentIdFK);
        $('#handlePerson').combobox('setValue',row.handlePersonID);
        $('#handlePerson').combobox('setText',row.handlePersonName);
        $('#handleBegTime').datebox('setValue',row.handleBegTime);
        $('#handleDesc').textbox('setValue',row.handleDesc);
        $('#handleEndTime').datebox('setValue',row.handleEndTime);
        $('#handleResult').combobox('setValue',row.handleResult);
        $('#handleResult').combobox('setText',row.handleResult);

        $('#handleDlg').dialog('open').dialog('setTitle', '修改处理过程');
    }else {
        $.messager.alert('提示', '需要选择一条处理过程，才能进行修改操作。', 'info');
    }
}

//删除处理过程
function deleteHandle(){
    var row = $('#handle-dg').datagrid('getSelected');
    if(row){
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("softMaintenance/deleteHandle", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#handle-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });
    }else {
        $.messager.alert('提示', '需要选择一条处理过程，才能进行删除操作。', 'info');
    }
}

//保存修改
function saveHandle(){
    $('#addHandle').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#handleDlg').dialog('close');
                $('#handle-dg').datagrid('reload');
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
        url: 'softMaintenance/editMaintenance',
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

//打开最终结论界面
function addResult(){
    var row = $('#maintenance-dg').datagrid('getSelected');
    if(row){
        $('#addResult').form('clear');
        $('#resultId').val(row.id);
        $('#result').textbox('setValue',row.result);    //最终总结
        if(row.resultPersonName) {          //总结人
            $('#resultPerson').combobox('setValue',row.resultPersonID);
            $('#resultPerson').combobox('setText',row.resultPersonName);
        }else{
            $.ajax({
                url: '../managesys/user/getCurrentUser',
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    if (result) {
                        $('#resultPerson').combobox('setValue',result.id);
                        $('#resultPerson').combobox('setText',result.nickname);
                    }
                }
            });
        }
        if(row.resultDate) {            //总结日期
            $('#resultDate').datebox('setValue', row.resultDate);
        }else{
            var today = new Date();
            $('#resultDate').datebox('setValue',today.toLocaleDateString());
        }
        $('#resultDlg').dialog('open').dialog('setTitle', '最终总结');
    }else{
        $.messager.alert('提示', '需要选择一条维修记录，才能填写最终结论。', 'info');
    }
}

//保存最终结论
function saveResult(){
    $('#addResult').form('submit', {
        url: 'softMaintenance/editMaintenance',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var data = jQuery.parseJSON(result);
            if (data.success) {
                $('#resultDlg').dialog('close');
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
        $('#exportForm').form({
            queryParams: {
                idQuery: row.id
            }
        });
        $('#exportForm').form('submit', {
            url: "softMaintenance/exportWord",
            method: "post",
            success: function (data) {
                var result = jQuery.parseJSON(data);
                if (!result.success) {
                    $.messager.alert("Word导出失败", result.message, 'error');
                }
            }
        })
    }else{
        $.messager.alert('提示', '需要选择一条售后维护记录，才能进行导出操作。', 'info');
    }
}

//导出列表
function exportExcel(){
    $.messager.confirm('确认','是否将列表导出为Excel文件',function(r){
        if(r)
        {
            $('#query-form').form({
                queryParams:{
                    projectNameQuery:$('#projectNameQuery').val(),
                    productNameQuery:$('#productNameQuery').val(),
                    numberQuery:$('#numberQuery').val(),
                    regPersonQuery:$('#regPersonQuery').val(),
                    regDateBegQuery:$('#regDateBegQuery').datebox('getValue'),
                    regDateEndQuery:$('#regDateEndQuery').datebox('getValue')
                }
            });
            $('#query-form').form('submit',{
                url:"softMaintenance/exportExcel",
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


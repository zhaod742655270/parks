
$(function () {
    loadBtns('project-btns', $('#menuId').val());

    //项目列表datagrid
    $('#project-dg').datagrid({
        nowrap: true,
        sortName: 'sheetName',
        sortOrder: 'desc',
        title: '报价管理',
        toolbar: '#project-toolbar',
        method: 'post',
        striped: true,
        rownumbers: true,
        fitColumns: true,
        fit: true,
        singleSelect: true,
        pagination: true,
        url:"quotation/projectList",
        
        //双击打开设备清单
        onDblClickRow: function (rowIndex, rowData) {
            findQuotation();
        }
    });

    //设备清单datagrid
    $('#quotation-dg').datagrid({
        sortName: 'name',
        sortOrder: 'asc',
        toolbar: '#quotation-toolbar',
        striped: true,
        fitColumns: true,
        fit: false,
        singleSelect: true,
        method: 'post',
        nowrap:true,
        pagination: true,
        showFooter:true,
        rownumbers:true,
        autoRowHeight:false,
        
        //双击打开修改界面
        onDblClickRow: editQuotation,

        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].freight==true){
                    data.rows[i].freightText ="含";
                }
                if(data.rows[i].freight==false){
                    data.rows[i].freightText ="不含";
                }

                if (data.rows[i].debugging==true){
                    data.rows[i].debuggingText ="是";
                }
                if(data.rows[i].debugging==false){
                    data.rows[i].debuggingText = "否";
                }
            }
            return data;
        },

        //加载成功后添加总计部分
        onLoadSuccess:function(){
            $("#quotation-dg").datagrid('reloadFooter',[
                {name:'设备材料费', specification:'以上合计', valence:getTotalValence().toFixed(3)},
                {name:'安装调试费', specification:'设备材料费*20%', valence:(getTotalValence()*0.2).toFixed(3)},
                {name:'税金', specification:'（设备材料费+安装调试费）*6%', valence:(getTotalValence()*0.072).toFixed(3)},
                {name:'总计', specification:'设备材料费+安装调试费+税金', valence:(getTotalValence()*1.272).toFixed(3)}
            ]);
            //合并说明部分的列
            $("#quotation-dg").datagrid('mergeCells',{
                index:0,
                type:'footer',
                field:'specification',
                colspan:6
            });
            $("#quotation-dg").datagrid('mergeCells',{
                index:1,
                type:'footer',
                field:'specification',
                colspan:6
            });
            $("#quotation-dg").datagrid('mergeCells',{
                index:2,
                type:'footer',
                field:'specification',
                colspan:6
            });
            $('#quotation-dg').datagrid('mergeCells',{
                index:3,
                type:'footer',
                field:'specification',
                colspan:6
            });
        }
    });

    //筛选条件中的年度选项
    $('#sheetNameQuery').combobox({
        data: [ {"id": "2020", "text": "2020"},{"id": "2019", "text": "2019"}, {"id": "2018", "text": "2018"},
            {"id": "2017", "text": "2017"}, {"id": "2016", "text": "2016"}],
        valueField: 'id',
        textField: 'text'
    });

    //年度选项
    $('#sheetName').combobox({
        data: [ {"id": "2020", "text": "2020"},{"id": "2019", "text": "2019"}, {"id": "2018", "text": "2018"},
            {"id": "2017", "text": "2017"}, {"id": "2016", "text": "2016"}],
        valueField: 'id',
        textField: 'text'
    });

    //项目类型
    $('#projectType').combobox({
        data: [{"id": "零星项目", "text": "零星项目"}, {"id": "弱电项目", "text": "弱电项目"},
            {"id": "贸易项目", "text": "贸易项目"},{"id": "其他项目", "text": "其他项目"}],
        valueField: 'id',
        textField: 'text'
    });

    //项目类型筛选项
    $('#projectTypeQuery').combobox({
        data: [{"id": "零星项目", "text": "零星项目"}, {"id": "弱电项目", "text": "弱电项目"},
            {"id": "贸易项目", "text": "贸易项目"},{"id": "其他项目", "text": "其他项目"}],
        valueField: 'id',
        textField: 'text'
    });

    //运费选项
    $('#freight').combobox({
        data:[{"id":true,"text":"含"},{"id":false,"text":"不含"}],
        valueField:"id",
        textField:"text"
    });
    
    //发票类型选项
    $('#invoice').combobox({
        data:[{"id":"17%增票","text":"17%增票"},
            {"id":"普票","text":"普票"},
            {"id":"无票","text":"无票"},
            {"id":"其他","text":"其他"}],
        valueField:"id",
        textField:"text"
    });

    //质保期选项
    $('#warranty').combobox({
        data:[{"id":"一年","text":"一年"},
            {"id":"两年","text":"两年"},
            {"id":"三年","text":"三年"},
            {"id":"其他","text":"其他"}],
        valueField:"id",
        textField:"text"
    });

    //厂家负责安装调试选项
    $('#debugging').combobox({
        data:[{"id":true,"text":"是"},{"id":false,"text":"否"}],
        valueField:"id",
        textField:"text"
    });
    
    //报价有效期选项
    $('#validityPeriod').combobox({
        data:[{"id":"一个月","text":"一个月"},
            {"id":"三个月","text":"三个月"},
            {"id":"其他","text":"其他"}],
        valueField:"id",
        textField:"text"
    });

    //textbox为空值时的提示
    $('.easyui-textbox').textbox({
        missingMessage:"该字段是必需的。"
    });
});

/**
 * 名称格式检验
 */
$.extend($.fn.validatebox.defaults.rules,{
    nameValid:{
        validator:function(value){
            //字母、汉字或数字组成
            var valid1= /^([a-z]|[A-Z]|[0-9]|[\u4E00-\u9FA5])+$/i.test(value);
            //非完全由数字组成
            var valid2=!(/^([0-9])+$/i.test(value));
            return valid1*valid2;
        },
        message:'格式不正确'
    }
});

/**
 * 单位格式检验
 */
$.extend($.fn.validatebox.defaults.rules,{
    unitValid:{
        validator:function(value){
          //字母与汉字组成
            var valid = /^([a-z]|[A-Z]|[\u4E00-\u9FA5])+$/i.test(value);
            return valid;
        },
        message:'格式不正确'
    }
});

/**
 * 项目查询
 */
function projectQuery(){
    var params = {
        sheetNameQuery:$('#sheetNameQuery').combobox('getValue'),
        projectNameQuery: $('#projectNameQuery').val(),
        projectTypeQuery:$('#projectTypeQuery').combobox('getValue')
    };
    $('#project-dg').datagrid({
        queryParams: params
    });
}

/**
 * 查看项目下的报价清单
 */
function findQuotation(){
    var row = $('#project-dg').datagrid('getSelected');
    if (row) {
        $('#quotation-dg').datagrid({
            queryParams: {
                projectId:row.id
            },
            url:"quotation/quotationList"
        });
        $('#quotationDgd').dialog('open').dialog('setTitle', '设备报价清单列表');
    } else{
        $.messager.alert('提示', '需要选择一个项目，才能查看报价。', 'info');
    }
}
// 定义窗口的提交地址
var formUrl = 'quotation/addProject';
/**
 * 新增项目
 */
function addProject(){
    // 定义窗口的提交地址
    formUrl = 'quotation/addProject';

    $('#projectDlg').dialog('open').dialog('setTitle', '新建项目');
    $('#addProject').form('clear');
}

/**
 * 保存项目
 */
function saveProject(){
    $('#addProject').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#projectDlg').dialog('close'); // close the dialog
                $('#project-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

/**
 * 修改项目
 */
function editProject(){
    var row = $('#project-dg').datagrid('getSelected');
    if(row){
        $('#projectDlg').dialog('open').dialog('setTitle', '修改项目');
        formUrl='quotation/editProject';         //修改点击保存后的提交地址
        $('#addProject').form('clear');
        $('#addProject').form('load', row);
        $('#projectID').val(row.id);
        $('#sheetName').combobox("setValue",row.sheetName);
        $('#sheetName').combobox("setText",row.sheetName);
        $('#projectName').val(row.projectName);
        $('#projectType').combobox("setValue",row.projectType);
        $('#projectType').combobox("setText",row.projectType);
        //由于自动赋值无法触发格式检验，需要再主动检验一次
        $("#projectName").validatebox('validate');  

    }else{
        $.messager.alert('提示', '需要选择一个项目，才能进行编辑操作。', 'info');
    }
}

/**
 * 删除项目
 */
function deleteProject(){
    var row = $('#project-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("quotation/deleteProject", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#project-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });
    } else {
        $.messager.alert('提示', '需要选择一个项目，才能进行删除操作。', 'info');
    }
}

/**
 * 查找设备报价清单
 */
function quotationQuery(){
    var row = $('#project-dg').datagrid('getSelected');
    var params = {
        nameQuery:$('#nameQuery').textbox('getValue'),
        specificationQuery:$('#specificationQuery').textbox('getValue'),
        brandQuery:$('#brandQuery').textbox('getValue'),
        projectId:row.id
    };
    $('#quotation-dg').datagrid({
        queryParams: params
    });
}

/**
 * 增加设备报价清单
 */
function addQuotation(){
    var row = $('#project-dg').datagrid('getSelected');
    quoFormUrl = 'quotation/addQuotation';
    if(row){
        $('#quotationDlg').dialog('open').dialog('setTitle', '新建报价清单');
        $('#addQuotation').form('clear');
        addQuoBegValue();
    }else{
        $.messager.alert('提示', '需要选择一个项目，才能进行新增验收清单的操作。', 'info');
    }
}

function addQuoBegValue(){
    $('#unit').val("台");
    $('#unit').validatebox('validate');
    $('#freight').combobox('setValue',true);
    $('#invoice').combobox('setValue','17%增票');
    $('#warranty').combobox('setValue','一年');
    $('#debugging').combobox('setValue',true);
    $('#validityPeriod').combobox('setValue','一个月');
}

/**
 * 修改设备报价清单
 */
function editQuotation() {
    var row = $('#quotation-dg').datagrid('getSelected');
    if (row) {
        quoFormUrl = 'quotation/editQuotation';   //修改点击保存后在提交地址
        $('#quotationDlg').dialog('open').dialog('setTitle', '修改报价清单');
        $('#addQuotation').form('clear');
        $('#addQuotation').form('load', row);
        
        $('#quotationID').val(row.id);
        $('#projectIdFK').val(row.projectIdFK);
        
        $('#name').val(row.name);
        $('#specification').textbox("setValue",row.specification);
        $('#technicalParameter').textbox("setValue",row.technicalParameter);
        $('#brand').val(row.brand);
        $('#ratio').textbox("setValue",row.ratio);
        $('#quantity').textbox("setValue",row.quantity);
        $('#costPrice').textbox("setValue",row.costPrice);
        $('#unit').val(row.unit);
        $('#price').textbox("setValue",row.price);
        $('#valence').textbox("setValue",row.valence);
        $('#inventory').textbox("setValue",row.costValence);
        $('#freight').combobox("setValue",row.freight);
        $('#invoice').combobox("setValue",row.invoice);
        $('#warranty').combobox("setValue",row.warranty);
        $('#debugging').combobox("setValue",row.debugging);
        $('#validityPeriod').combobox("setValue",row.validityPeriod);
        $('#supplyCycle').textbox("setValue",row.supplyCycle);
        $('#payment').textbox("setValue",row.payment);
        $('#note').textbox("setValue",row.note);

        //由于自动赋值无法触发格式检验，需要再主动检验一次
        $("#name").validatebox('validate');
        $("#brand").validatebox('validate');
        $("#unit").validatebox('validate');
    } else {
        $.messager.alert('提示', '需要选择一个设备报价清单，才能进行编辑操作。', 'info');
    }
}

//设备报价清单提交地址
var quoFormUrl="quotation/addQuotation";
/**
 * 保存设备报价清单修改
 */
function saveQuotation(){
    var row = $('#project-dg').datagrid('getSelected');
    $('#projectIdFK').val(row.id);
    $('#addQuotation').form('submit', {
        url: quoFormUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#quotationDlg').dialog('close'); // close the dialog
                $('#quotation-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

/**
 * 删除设备报价清单
 */
function deleteQuotation() {
    var row = $('#quotation-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("quotation/deleteQuotation", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#quotation-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });
    } else {
        $.messager.alert('提示', '需要选择一个报价清单，才能进行删除操作。', 'info');
    }
}

/**
 * 设备报价清单导出
 */
function loadToExcel(){
    var row = $('#project-dg').datagrid('getSelected');
    $.messager.confirm('确认','是否将文件导出为Excel文件',function(r){
        if(r)
        {
            $('#exportForm').form({
                queryParams:{
                    projectId: row.id,
                    projectName: row.projectName,
                    nameQuery:$('#nameQuery').textbox('getValue'),
                    specificationQuery:$('#specificationQuery').textbox('getValue'),
                    brandQuery:$('#brandQuery').textbox('getValue')
                }
            });
            $('#exportForm').form('submit',{
                url:"quotation/exportExcel",
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

/**
 * 打开导入文件界面
 */
function openImportDlg(){
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
        ////提交表单
        $('#importExcel').form('submit', {
            url: 'quotation/importExcel',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (data) {
                var result = jQuery.parseJSON(data);
                if (result.success) {
                    $('#importExcel-dlg').dialog('close'); // close the dialog
                    $('#project-dg').datagrid('reload');
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

/**
 * 计算单价
 */
function getPrice(){
    var costPrice=$('#costPrice').numberbox('getValue');
    var ratio=$('#ratio').numberbox('getValue');
    $('#price').numberbox('setValue',costPrice*ratio);
}

/**
 * 计算合价
 * @param newValue
 * @param oldValue
 */
function getValence(){
    var number=$('#quantity').numberbox('getValue');
    var price=$('#price').numberbox('getValue');
    $('#valence').numberbox('setValue',number*price);
}
/**
 * 计算成本合价
 * @param newValue
 * @param oldValue
 */
function getInventory(){
    var number=$('#quantity').numberbox('getValue');
    var price=$('#costPrice').numberbox('getValue');
    $('#inventory').numberbox('setValue',number*price);
}

/**
 * 获得价格合计
 * @returns {number}
 */
function getTotalValence(){
    var totalValence=0;
    var rows=$('#quotation-dg').datagrid('getData');
    for(var i=0;i<rows.rows.length;i++)
    {
        if(rows.rows[i].valence != null) {
            totalValence += rows.rows[i].valence * 1;
        }
    }
    return totalValence;
}


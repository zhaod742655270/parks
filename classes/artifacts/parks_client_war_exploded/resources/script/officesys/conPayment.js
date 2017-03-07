
$(function () {
    loadBtns('conPayment-btns', $('#menuId').val());
    $('#conPayment-dg').datagrid({
        title: '付款款合同管理',
        toolbar: '#conPayment-toolbar',
        sortName: 'contractSn',
        sortOrder: 'desc',
        striped: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'post',
        pagination: true,
        selectOnCheck: true,
        url:'payment/paymentList',

        columns:[[
            //{field:'contactNO',title:'合同号'},
            {field:'contractSn',title:'序号',sortable:true},
            {field:'companyFirst',title:'甲方单位'},
            {field:'companySecond',title:'乙方单位'},
            {field:'purchaseContent',title:'采购内容'},
            {field:'signDate',title:'签订日期'},
            {field:'purchasePerson',title:'采购负责人'},
            {field:'contractSum',title:'合同金额'},
            {field:'payment',title:'已付款金额',
                styler: function(value,row,index){
                    if(row.paymentPostil) {
                        if ( row.paymentPostil.paymentPostil) {
                            return 'color:red;';
                        }
                    }
                }

            },

            {field:'paymentNo',title:'未付款金额',
                styler: function(value,row,index){
                    if(row.paymentPostil) {
                        if ( row.paymentPostil.paymentNoPostil) {
                            return 'color:red;';
                        }
                    }
                }

            },
            {field:'text',title:'关联的收款合同'},
            {field:'condition',title:'付款条件'},
            {field:'note',title:'备注'}

        ]],
        onDblClickRow: function (rowIndex, rowData) {
            var actionUrl="officesys/payment/editPayment";
            $.ajax({
                url: 'conGathering/authorization',
                type: 'POST',
                data: {
                    actionUrl: actionUrl
                },
                dataType: 'json',
                success: function (result) {
                    editPayment();
                    if (result.success) {
                        $('#savePayment').linkbutton({ disabled:false});
                    }
                    else {
                        $('#paymentDlg').dialog('open').dialog('setTitle', '查看付款合同');
                        $('#savePayment').linkbutton({ disabled:true});
                    }

                }
            });
        },
        onRowContextMenu: function (e, index, node) {
            e.preventDefault();
            var row = $('#conPayment-dg').datagrid('getSelected');
            if (row) {
                $('#paymentRightMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        },
        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].signDate=="1900-01-01"){
                    data.rows[i].signDate = "";
                }
                if (data.rows[i].contactSum)
                    data.rows[i].contactSum = new Number(data.rows[i].contactSum);
                if (data.rows[i].payment)
                    data.rows[i].payment = new Number(data.rows[i].payment);
                if (data.rows[i].paymentNo)
                    data.rows[i].paymentNo = new Number(data.rows[i].paymentNo);
                if (data.rows[i].contactSum)
                    data.rows[i].contactSum = Math.round(data.rows[i].contactSum*100)/100;
                if (data.rows[i].payment)
                    data.rows[i].payment =Math.round(data.rows[i].payment*100)/100;
                if (data.rows[i].paymentNo)
                    data.rows[i].paymentNo = Math.round(data.rows[i].paymentNo*100)/100;

                if (data.rows[i].contractGatherings.length > 0) {
                    var names = "";
                    var ids = [];
                    for (var j = 0; j < data.rows[i].contractGatherings.length; j++) {
                        names = names + data.rows[i].contractGatherings[j].contractName + '； ';
                        ids.push(data.rows[i].contractGatherings[j].id)
                    }
                    data.rows[i].idd = ids;
                    data.rows[i].text = names;
                }
            }
            return data;
        }
    });

    $('#conGathering-dg').datagrid({
        sortName: 'projectSn',
        sortOrder: 'desc',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'post',
        pagination: true,

        columns:[[
            {field:'sheetName',title:'年度'},
            {field:'projectType',title:'合同类型'},
            {field:'contractNoYD',title:'远东合同号'},
            {field:'contractNo',title:'合同号'},
            {field:'contractName',title:'合同名称',
                styler: function(value,row,index){
                    if(row.contractGatheringPostil) {
                        if ( row.contractGatheringPostil.contractNamePostil) {
                            return 'color:red;';
                        }
                    }
                }
            },
            {field:'companyFirst',title:'甲方签约单位'},
            {field:'companySecond',title:'乙方签约单位'},
            {field:'signDate',title:'签订日期'},
            {field:'amount',title:'合同金额',
                styler: function(value,row,index){
                    if(row.contractGatheringPostil){
                        if ( row.contractGatheringPostil.amountPostil){
                            return 'color:red;';
                        }
                    }
                }
            },
            {field:'received',title:'已收款金额',
                styler: function(value,row,index) {
                    if (row.contractGatheringPostil) {
                        if ( row.contractGatheringPostil.receivedPostil) {
                            return 'color:red;';
                        }
                    }
                }
            },
            {field:'receiveNo',title:'未收款金额',
                styler: function(value,row,index) {
                    if (row.contractGatheringPostil) {
                        if ( row.contractGatheringPostil.receiveNoPostil) {
                            return 'color:red;';

                        }
                    }
                }
            },
            {field:'oncredit',title:'挂账金额',
                styler: function(value,row,index) {
                    if (row.contractGatheringPostil) {
                        if (row.contractGatheringPostil.oncreditPostil) {
                            return 'color:red;';
                        }
                    }
                }
            },
            {field:'remain',title:'剩余金额',
                styler: function(value,row,index) {
                    if (row.contractGatheringPostil) {
                        if (row.contractGatheringPostil.remainPostil) {
                            return 'color:red;';
                        }
                    }
                }
            },
            {field:'gross',title:'毛利',
                styler: function(value,row,index) {
                    if (row.contractGatheringPostil) {
                        if (row.contractGatheringPostil.grossPostil) {
                            return 'color:red;';
                        }
                    }
                }
            },
            {field:'projectManager',title:'市场负责人'},
            {field:'projectDirector',title:'工程负责人'},
            {field:'stampName',title:'盖章情况'},
            {field:'isCompletedName',title:'项目进展'},
            {field:'note',title:'备注'},

        ]],

        loadFilter: function (data) {
          data.rows=data;
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].signDate=="1900-01-01"){
                    data.rows[i].signDate = "";
                }
                if (data.rows[i].amount)
                    data.rows[i].amount = new Number(data.rows[i].amount);
                    data.rows[i].amount = Math.round(data.rows[i].amount*100)/100;
                if (data.rows[i].received)
                    data.rows[i].received = new Number(data.rows[i].received);
                   data.rows[i].received = Math.round(data.rows[i].received*100)/100;
                if (data.rows[i].receiveNo)
                    data.rows[i].receiveNo = new Number(data.rows[i].receiveNo);
                 data.rows[i].receiveNo = Math.round(data.rows[i].receiveNo*100)/100;
                if (data.rows[i].oncredit)
                    data.rows[i].oncredit = new Number(data.rows[i].oncredit);
                data.rows[i].oncredit = Math.round(data.rows[i].oncredit*100)/100;
                if (data.rows[i].remain)
                    data.rows[i].remain = new Number(data.rows[i].remain);
                data.rows[i].remain = Math.round(data.rows[i].remain*100)/100;

                if (data.rows[i].isCompleted==0){
                    data.rows[i].isCompletedName ="未完成";
                }else if(data.rows[i].isCompleted==1){
                    data.rows[i].isCompletedName ="已完成";
                }else{
                    data.rows[i].isCompletedName ="";
                }

                if (data.rows[i].stamp==0){
                    data.rows[i].stampName ="未盖章";
                }else if(data.rows[i].stamp==1) {
                    data.rows[i].stampName = "已盖章";
                }else{
                    data.rows[i].stampName = "";
                }
            }
            return data.rows;
        }

    });

    $('#operationPaymentQuery-table').datagrid({
        striped: true,
        rownumbers: true,
        sortName: 'date',
        sortOrder: 'desc',
        fitColums: true,
        fit: true,
        method: 'post',
        singleSelect: true,
        pagination: true,

        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].contactSum)
                    data.rows[i].contactSum = new Number(data.rows[i].contactSum);
                if (data.rows[i].payment)
                    data.rows[i].payment = new Number(data.rows[i].payment);
                if (data.rows[i].paymentNo)
                    data.rows[i].paymentNo = new Number(data.rows[i].paymentNo);
                if (data.rows[i].contactSumChange)
                    data.rows[i].contactSumChange = Math.round(data.rows[i].contactSumChange*100)/100;
                if (data.rows[i].paymentChange)
                    data.rows[i].paymentChange =Math.round(data.rows[i].paymentChange*100)/100;
                if (data.rows[i].paymentNoChange)
                    data.rows[i].paymentNoChange = Math.round(data.rows[i].paymentNoChange*100)/100;


            }
            return data;
        }
    });

    $('#sheetNameQuery').combobox({
        data: [{"id": "2019", "text": "2019"}, {"id": "2018", "text": "2018"}, {
            "id": "2017",
            "text": "2017"
        }, {"id": "2016", "text": "2016"},
            {"id": "2015", "text": "2015"}],
        valueField: 'id',
        textField: 'text',
        onChange: function (newValue, oldValue) {
            var type= $('#contractTypeQuery').combobox('getValue')
            if(type=="弱电项目"){
                contractType=1;
            }else if(type=="贸易项目"){
                contractType=2
            }else if(type=="其它项目"){
                contractType=3;
            }else{
                contractType=0;
            }
            var sheetName=newValue;
            $('#contractNameQuery').combobox({
                valueField: 'id',
                textField: 'text',
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) >= 0;
                },
                url:'payment/getContractName?sheetName='+sheetName+'&contractType='+contractType
            })

        }
    });


    $('#contractTypeQuery').combobox({
        data: [{"id": "弱电项目", "text": "弱电项目"}, {"id": "零星项目", "text": "零星项目"}, {"id": "贸易项目", "text": "贸易项目"}, {"id": "其它项目", "text": "其它项目"}],
        valueField: 'id',
        textField: 'text',
        onChange: function (newValue, oldValue)  {
            var sheetName = $('#sheetNameQuery').combobox('getValue')
            if (sheetName) {
                var type = newValue;
                if(type=="弱电项目"){
                    contractType=1;
                }else if(type=="贸易项目"){
                    contractType=2
                }else if(type=="其它项目"){
                    contractType=3;
                }else{
                    contractType=0;
                }

                $('#contractNameQuery').combobox({
                    valueField: 'id',
                    textField: 'text',
                    filter: function(q, row){
                        var opts = $(this).combobox('options');
                        return row[opts.textField].indexOf(q) >= 0;
                    },
                    url:'payment/getContractName?sheetName=' + sheetName + '&contractType=' + contractType
                })
            }
        }
    });


    //新增菜单中的所属的类型，所属合同名称

    $('#sheetName').combobox({
        data: [{"id": "2019", "text": "2019"}, {"id": "2018", "text": "2018"}, {
            "id": "2017",
            "text": "2017"
        }, {"id": "2016", "text": "2016"},
            {"id": "2015", "text": "2015"}],
        valueField: 'id',
        textField: 'text',
        onChange: function (newValue, oldValue) {
            var type= $('#belongType').combobox('getValue')
            if(type=="弱电项目"){
                contractType=1;
            }else if(type=="贸易项目"){
                contractType=2
            }else if(type=="其它项目"){
                contractType=3;
            }else{
                contractType=0;
            }
            var sheetName=newValue;
            $('#belongContractNames').combobox({
                valueField: 'id',
                textField: 'text',
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) >= 0;
                },
                url:'payment/getContractName?sheetName='+sheetName+'&contractType='+contractType
            })

        }
    });

    $('#belongType').combobox({
        data: [{"id": "弱电项目", "text": "弱电项目"}, {"id": "零星项目", "text": "零星项目"}, {"id": "贸易项目", "text": "贸易项目"}, {"id": "其它项目", "text": "其它项目"}],
        valueField: 'id',
        textField: 'text',
        onChange: function (newValue, oldValue)  {
            var sheetName = $('#sheetName').combobox('getValue')
            if (sheetName) {
                var type = newValue;
                if(type=="弱电项目"){
                    contractType=1;
                }else if(type=="贸易项目"){
                    contractType=2
                }else if(type=="其它项目"){
                    contractType=3;
                }else{
                    contractType=0;
                }
                    $('#belongContractNames').combobox({
                        valueField: 'id',
                        textField: 'text',
                        filter: function(q, row){
                            var opts = $(this).combobox('options');
                            return row[opts.textField].indexOf(q) >= 0;
                        },
                        url:'payment/getContractName?sheetName=' + sheetName + '&contractType=' + contractType
                    })


            }
        }
    });

    //付款合同的numberbox计算公式

    $('#contractSum').numberbox({
        onChange:function(newValue,oldValue){
            var payment= $('#payment').numberbox('getValue');

            $('#paymentNo').numberbox('setValue',newValue-payment);
        }
    });

    $('#payment').numberbox({
        onChange:function(newValue,oldValue){
            var contractSum= $('#contractSum').numberbox('getValue');
            $('#paymentNo').numberbox('setValue',contractSum-newValue);
        }
    });

    $('#purchasePerson').combobox({
        data: [{"id": "范佳静", "text": "范佳静"}, {"id": "杨文杰", "text": "杨文杰"}, {"id": "高晓琼", "text": "高晓琼"}, {"id": "卜丽华", "text": "卜丽华"}],
        valueField: 'id',
        textField: 'text'
    });

})

// 定义窗口的提交地址
var formUrl = 'payment/addPayment';
var contractType;
//查询
function paymentQuery(){
    var params = {
        contractType:$('#contractTypeQuery').combobox('getValue'),
        contractName: $('#contractNameQuery').combobox('getText'),
        sheetNameQuery: $('#sheetNameQuery').combobox('getValue'),
        companySecondQuery:$('#companySecondQuery').val(),
        personQuery: $('#personQuery').val(),
        contractSumQuery: $('#contractSumQuery').val(),
        contractTypeQuery: $('#contractTypeQuery').combobox('getValue')
    };
    $('#conPayment-dg').datagrid({
        url: 'payment/paymentList',
        queryParams: params
    });

}

//增加付款合同窗口
function addPayment(){
    var sheetName=$('#sheetNameQuery').combobox('getValue');
    var contractType =$('#contractTypeQuery').combobox('getValue');
    var name=$('#contractNameQuery').combobox('getValue');
    if(contractType=="贸易项目" ||contractType=="其它项目"){
        $("#paymentDlg").dialog("open").dialog('setTitle', '新建付款合同');
        $('#addPayment').form('clear');
        if(contractType=="其它项目"){
            $('#sheetName').combobox('disable');
            $('#belongType').combobox('disable');
            $('#belongContractNames').combobox('disable');
            $('#belongContractNames').combobox("setValue","others");
            $('#belongContractNames').combobox("setText","其它");
        }else{
            $('#sheetName').combobox('enable');
            $('#belongType').combobox('enable');
            $('#belongContractNames').combobox('enable');
        }
        formUrl = 'payment/addPayment';
    }else {
        if (name == "" || name == null) {
            $.messager.alert('提示', '弱电项目、零星项目请依次选择合同类型、年度、合同名称。', 'info');
        } else {
            $("#paymentDlg").dialog("open").dialog('setTitle', '新建付款合同');
            $('#addPayment').form('clear');
            $('#sheetName').combobox("setValue",sheetName);
            $('#belongType').combobox("setValue",contractType);
            $('#belongContractNames').combobox({
                valueField: 'id',
                textField: 'text'
            })
            $('#belongContractNames').combobox("setValue",name);
            $('#belongContractNames').combobox("setText",$('#contractNameQuery').combobox('getText'));
            $('#sheetName').combobox('enable');
            $('#belongType').combobox('enable');
            $('#belongContractNames').combobox('enable');
            formUrl = 'payment/addPayment';
        }
    }

}

// 打开编辑付款合同的窗口
function editPayment() {
    var row = $('#conPayment-dg').datagrid('getSelected');
    if (row) {
        formUrl = 'payment/editPayment';
        $('#addPayment').form('clear');
        $('#paymentDlg').dialog('open').dialog('setTitle', '编辑付款合同');
        $('#addPayment').form('load', row);
        if(row.contractType=="其它项目"){
            $('#sheetName').combobox('disable');
            $('#belongType').combobox('disable');
            $('#belongContractNames').combobox('disable');
            $('#belongContractNames').combobox("setValue","others");
            $('#belongContractNames').combobox("setText","其它");
        }else{
            $('#sheetName').combobox('enable');
            $('#sheetName').combobox("setValue",row.sheetName);
            $('#belongType').combobox('enable');
            $('#belongType').combobox("setValue",row.contractType);
            $('#belongContractNames').combobox('enable');
            if(row.idd){
                $('#belongContractNames').combobox("setValues",row.idd);
            }

        }
        $('#paymentID').val(row.id);
        $('#contractType').val(row.contractType);
        $('#sheet').val(row.sheetName);
        $('#contractName').val(row.contractName);
        $('#contractSn').textbox("setValue",row.contractSn);
        $('#paymentCompanyFirst').val(row.companyFirst);
        $('#paymentCompanySecond').val(row.companySecond);
        $('#purchaseContent').val(row.purchaseContent);
        $('#paymentSignDate').datebox("setValue", row.signDate);
        $('#purchasePerson').combobox("setValue",row.purchasePerson);
        $('#contractSum').numberbox("setValue",row.contractSum);
        $('#payment').numberbox("setValue",row.payment);
        $('#condition').val(row.condition);
        $('#paymentNote').val(row.note);
        $('#paymentNo').numberbox("setValue",row.paymentNo);
    } else {
        $.messager.alert('提示', '需要选择一个付款合同，才能进行编辑操作。', 'info');
    }

}

//保存按键
function savePayment(){
    var contractName=$('#contractNameQuery').combobox('getText');
    var sheetNameQuery=$('#sheetNameQuery').combobox('getValue');
    var contractType=$('#contractTypeQuery').combobox('getValue');
    var row= $('#conPayment-dg').datagrid('getSelected');
    if(contractName){
        $('#contractName').val(contractName);
    }else if(row && row.contractName){
        $('#contractName').val(row.contractName);
    }else{
        $('#contractName').val(sheetNameQuery+contractType+"类");
    }

    if(sheetNameQuery){
        $('#sheet').val(sheetNameQuery);
    }else if(row.sheetName){
        $('#sheet').val(row.sheetName);
    }else{
        $('#sheet').val("");
    }

    if(contractType){
        $('#contractType').val(contractType);
    }else if(row.contractType){
        $('#contractType').val(row.contractType);
    }else{
        $('#contractType').val("");
    }
    $('#addPayment').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#paymentDlg').dialog('close'); // close the dialog
                $('#conPayment-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

// 删除付款合同
function deletePayment() {
    var row = $('#conPayment-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("payment/deletePayment", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#conPayment-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个合同，才能进行删除操作。', 'info');
    }

}

//计算付款合同总额
function sumPayment(){
    $('#paySum-dlg').dialog('open').dialog('setTitle', '付款合同汇总');
    $('#paySum-form').form('clear');
    var params = {
        contractName: $('#contractNameQuery').combobox('getText'),
        sheetNameQuery: $('#sheetNameQuery').combobox('getValue'),
        personQuery: $('#personQuery').val(),
        contractSumQuery: $('#contractSumQuery').val(),
        companySecondQuery:$('#companySecondQuery').val(),
        contractTypeQuery: $('#contractTypeQuery').combobox('getValue')
    };
    $.ajax({
        url: 'payment/sumPayment',
        type: 'POST',
        data:params,
        dataType: 'json',
        success: function (result) {
            if (result) {
                $('#payCount').numberbox("setValue",result.count);
                $('#payAmountSum').numberbox("setValue",result.amountSum);
                $('#payReceivedSum').numberbox("setValue",result.paymentSum);
                $('#payReceiveNoSum').numberbox("setValue",result.paymentNoSum);
            }
            else {
                $.messager.alert('错误', result.message, 'error');
            }
        }
    });
}


//未付款合同的标签
function paymentPostil() {
    var row = $('#conPayment-dg').datagrid('getSelected');

    var object = row.paymentPostil;
    if (object != null && object != "") {
        formUrl = 'payment/editPostil';
        $('#addPaymentPostil-form').form('clear');
        $('#paymentPostil-dlg').dialog('open').dialog('setTitle', '批注');
        $('#addPaymentPostil-form').form('load', row);
        $('#id').val(row.id);
        $('#paymentPostilId').val(object.id);
        $('#parentID').val(object.parentID);
        if(object.paymentPostil==null){
            $('#paymentPostil').val("");
        }else{
            $('#paymentPostil').val(object.paymentPostil);
        }

        if(object.paymentNoPostil==null){
            $('#paymentNoPostil').val("");
        }else{
            $('#paymentNoPostil').val(object.paymentNoPostil);
        }

    }else{
        $("#paymentPostil-dlg").dialog("open").dialog('setTitle', '批注');
        $('#addPaymentPostil-form').form('clear');
        $("#parentID").val(row.id);
        formUrl = 'payment/addPostil';
    }
}

//保存未付款合同标签
function savePaymentPostil(){
    $('#addPaymentPostil-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#paymentPostil-dlg').dialog('close'); // close the dialog
                $('#conPayment-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });
}

//操作日志
function paymentLog(){
    var row = $('#conPayment-dg').datagrid('getSelected');
    if (row) {
        $('#paymentLog-dlg').dialog('open').dialog('setTitle', '操作日志查询');

        $('#paymentLog-table').datagrid({
            striped: true,
            rownumbers: true,
            sortName: 'date',
            sortOrder: 'desc',
            fitColums: true,
            fit: true,
            method: 'post',
            singleSelect: true,
            pagination: true,

            loadFilter: function (data) {
                for (var i = 0; i < data.rows.length; i++) {
                    if (data.rows[i].contactSum)
                        data.rows[i].contactSum = new Number(data.rows[i].contactSum);

                    if (data.rows[i].contactSumChange)
                        data.rows[i].contactSumChange = new Number(data.rows[i].contactSumChange);

                    if (data.rows[i].payment)
                        data.rows[i].payment = new Number(data.rows[i].payment);

                    if (data.rows[i].paymentChange)
                        data.rows[i].paymentChange = new Number(data.rows[i].paymentChange);


                    if (data.rows[i].paymentNo)
                        data.rows[i].paymentNo = new Number(data.rows[i].paymentNo);

                    if (data.rows[i].paymentNoChange)
                        data.rows[i].paymentNoChange = new Number(data.rows[i].paymentNoChange);


                    if (data.rows[i].contactSumChange)
                        data.rows[i].contactSumChange =Math.round(data.rows[i].contactSumChange*100)/100;

                    if (data.rows[i].paymentChange)
                        data.rows[i].paymentChange = Math.round(data.rows[i].paymentChange*100)/100;

                    if (data.rows[i].paymentNoChange)
                        data.rows[i].paymentNoChange =Math.round(data.rows[i].paymentNoChange*100)/100;

                }
                return data;
            }
        });

        $('#paymentLog-table').datagrid({
            url: 'paymentLog/paymentLogList',
            queryParams:{paymentID:row.id}
        });
    }else{
        $.messager.alert('提示', '需要选择一个合同，才能进行查看操作记录的操作。', 'info');
    }
}


//查看收款合同
function viewGathering(){
    var row= $('#conPayment-dg').datagrid('getSelected');
    if (row) {
        $('#conGathering-dlg').dialog('open').dialog('setTitle', '查看收款合同');
        $('#conGathering-dg').datagrid('loadData',row.contractGatherings);
    }else{
        $.messager.alert('提示', '需要选择一个合同，才能进行查看收款合同的操作。', 'info');
    }

}

// 未付款合同操作查询窗口
function operationQuery(){

    $('#operationPaymentQuery-dlg').dialog('open').dialog('setTitle', '操作查询');
    $('#paymentBeginDate').datetimebox('setValue',"");
    $('#paymentEndDate').datetimebox('setValue',"");

    $('#operationPaymentQuery-table').datagrid('load',{});
}

// 未付款合同操作查询显示
function operationPaymentDisplay(){

    var beginDate = $('#paymentBeginDate').datetimebox('getValue');
    var endDate= $('#paymentEndDate').datetimebox('getValue');
    var contractName=$('#paymentContractName').val();
    var contractSn=$('#paymentContractSn').val();

    $('#operationPaymentQuery-table').datagrid({
        url: 'paymentLog/operationQueryList',
        queryParams: {
            beginDate: beginDate,
            endDate:endDate,
            contractName:contractName,
            contractSn:contractSn
        }

    });

}

function exportExcel(){
    var params={
        contractName: $('#contractNameQuery').combobox('getText'),
        sheetNameQuery: $('#sheetNameQuery').combobox('getValue'),
        companySecondQuery:$('#companySecondQuery').val(),
        personQuery: $('#personQuery').val(),
        contractSumQuery: $('#contractSumQuery').val(),
        contractTypeQuery: $('#contractTypeQuery').combobox('getValue')
    };
    $.messager.confirm("确认","是否将文件导出为Excel文件",function (r) {
        if(r){
            $('#query-form').form('submit',{
                url:'payment/exportExcel',
                queryParams:params,
                method:"post",
                success:function(data){
                    if(!data.success) {
                        $.messager.alert("Excel导出失败",result.message,"error");
                    }
                }
            });
        }
    });
}


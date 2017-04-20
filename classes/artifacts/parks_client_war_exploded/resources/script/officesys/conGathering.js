
$(function () {
    loadBtns('conGathering-btns', $('#menuId').val());
    $('#conGathering-dg').datagrid({
        title: '收款合同管理',
        toolbar: '#conGathering-toolbar',
        sortName: 'projectSn',
        sortOrder: 'desc',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'post',
        pagination: true,
        selectOnCheck: true,
        checkOnSelect: true,
        url: 'conGathering/conGatheringList',

        columns:[[
            {field:'sheetName',title:'年度'},
            {field:'projectType',title:'合同类型'},
            {field:'contractNoYD',title:'远东合同号'},
            {field:'contractNo',title:'合同号',sortable:true},
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
            {field:'acceptanceDate',title:'验收日期'},
            {field:'linkContractName',title:'所属原项目'},
            {field:'note',title:'备注'}
        ]],

        onDblClickRow: function (rowIndex, rowData) {
            var actionUrl="officesys/conGathering/editConGathering";
            $.ajax({
                url: 'conGathering/authorization',
                type: 'POST',
                data: {
                    actionUrl: actionUrl
                },
                dataType: 'json',
                success: function (result) {
                    editContract();
                    if (result.success) {
                        $('#saveContract').linkbutton({ disabled:false});
                    }
                    else {
                        $('#conGathering-dlg').dialog('open').dialog('setTitle', '查看收款合同');
                        $('#saveContract').linkbutton({ disabled:true});
                    }

                }
            });

        },
        onRowContextMenu: function (e, index, node) {
            e.preventDefault();
            var row = $('#conGathering-dg').datagrid('getSelected');
            if (row) {
                $('#rightMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        },
        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].acceptanceDate=="1900-01-01"){
                    data.rows[i].acceptanceDate = "";
                }
                if (data.rows[i].amount)
                    data.rows[i].amount = new Number(data.rows[i].amount);
                if (data.rows[i].received)
                    data.rows[i].received = new Number(data.rows[i].received);
                if (data.rows[i].receiveNo)
                    data.rows[i].receiveNo = new Number(data.rows[i].receiveNo);
                if (data.rows[i].oncredit)
                    data.rows[i].oncredit = new Number(data.rows[i].oncredit);
                if (data.rows[i].remain)
                    data.rows[i].remain = new Number(data.rows[i].remain);

                if (data.rows[i].amount)
                    data.rows[i].amount = Math.round(data.rows[i].amount*100)/100;
                if (data.rows[i].received)
                    data.rows[i].received =Math.round(data.rows[i].received*100)/100;
                if (data.rows[i].receiveNo)
                    data.rows[i].receiveNo = Math.round(data.rows[i].receiveNo*100)/100;
                if (data.rows[i].oncredit)
                    data.rows[i].oncredit =Math.round(data.rows[i].oncredit*100)/100;
                if (data.rows[i].remain)
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
            return data;
        }

    });

    $('#operationQuery-table').datagrid({
        striped: true,
        rownumbers: true,
        sortName: 'date',
        sortOrder: 'desc',
        fitColums: true,
        fit: true,
        method: 'post',
        singleSelect: true,
        pagination: true,
        //url: 'conLog/operationQueryList',

        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].amount)
                    data.rows[i].amount = new Number(data.rows[i].amount);
                if (data.rows[i].received)
                    data.rows[i].received = new Number(data.rows[i].received);
                if (data.rows[i].receiveNo)
                    data.rows[i].receiveNo = new Number(data.rows[i].receiveNo);
                if (data.rows[i].oncredit)
                    data.rows[i].oncredit = new Number(data.rows[i].oncredit);
                if (data.rows[i].remain)
                    data.rows[i].remain = new Number(data.rows[i].remain);
                if (data.rows[i].amountChange)
                    data.rows[i].amountChange = Math.round(data.rows[i].amountChange*100)/100;
                if (data.rows[i].receivedChange)
                    data.rows[i].receivedChange =Math.round(data.rows[i].receivedChange*100)/100;
                if (data.rows[i].receiveNoChange)
                    data.rows[i].receiveNoChange = Math.round(data.rows[i].receiveNoChange*100)/100;
                if (data.rows[i].oncreditChange)
                    data.rows[i].oncreditChange =Math.round(data.rows[i].oncreditChange*100)/100;
                if (data.rows[i].remainChange)
                    data.rows[i].remainChange = Math.round(data.rows[i].remainChange*100)/100;

              }
            return data;
        }
    });

    $('#payment-table').datagrid({
        striped: true,
        toolbar: '#payment-toolbar',
        fitColums: true,
        fit: true,
        singleSelect: true,
        pagination: true,

        columns:[[
            {field:'contractSn',title:'序号', sortable:true},
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

            {field:'condition',title:'付款条件'},
            {field:'note',title:'备注'}

        ]],
        onDblClickRow: function (rowIndex, rowData) {
            editPayment();
        },
        onRowContextMenu: function (e, index, node) {
            e.preventDefault();
            var row = $('#payment-table').datagrid('getSelected');
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

                if (data.rows[i].contractSum)
                    data.rows[i].contractSum = new Number(data.rows[i].contractSum);
                if (data.rows[i].payment)
                    data.rows[i].payment = new Number(data.rows[i].payment);
                if (data.rows[i].paymentNo)
                    data.rows[i].paymentNo = new Number(data.rows[i].paymentNo);
                if (data.rows[i].contractSum)
                    data.rows[i].contractSum = Math.round(data.rows[i].contractSum*100)/100;
                if (data.rows[i].payment)
                    data.rows[i].payment =Math.round(data.rows[i].payment*100)/100;
                if (data.rows[i].paymentNo)
                    data.rows[i].paymentNo = Math.round(data.rows[i].paymentNo*100)/100;
            }
            return data;
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
        //url: 'paymentLog/operationQueryList',

        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                if (data.rows[i].contractSum)
                    data.rows[i].contractSum = new Number(data.rows[i].contractSum);
                if (data.rows[i].payment)
                    data.rows[i].payment = new Number(data.rows[i].payment);
                if (data.rows[i].paymentNo)
                    data.rows[i].paymentNo = new Number(data.rows[i].paymentNo);
                if (data.rows[i].contractSumChange)
                    data.rows[i].contractSumChange = Math.round(data.rows[i].contractSumChange*100)/100;
                if (data.rows[i].paymentChange)
                    data.rows[i].paymentChange =Math.round(data.rows[i].paymentChange*100)/100;
                if (data.rows[i].paymentNoChange)
                    data.rows[i].paymentNoChange = Math.round(data.rows[i].paymentNoChange*100)/100;

            }
            return data;
        }
    });

    $('#linkContract-table').datagrid({
        striped: true,
        sortName:'projectSn',
        sortOrder:'desc',
        fitColums: true,
        fit: true,
        singleSelect: true,
        pagination: true,
        url: 'conGathering/conGatheringList',

        columns:[[
            {field:'sheetName',title:'年度'},
            {field:'projectType',title:'合同类型'},
            {field:'contractNoYD',title:'远东合同号'},
            {field:'contractNo',title:'合同号',sortable:true},
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
            {field:'acceptanceDate',title:'验收日期'},
            {field:'linkContractName',title:'所属原项目'},
            {field:'note',title:'备注'}
        ]]
    });

    $('#sheetNameQuery').combobox({
        data: [{"value": "2019", "text": "2019"}, {"value": "2018", "text": "2018"}, {"value": "2017", "text": "2017"}, {"value": "2016", "text": "2016"},
            {"value": "2015", "text": "2015"}, {"value": "2014", "text": "2014"}, {"value": "2013", "text": "2013"}, {"value": "2012", "text": "2012"},{"value": "2011", "text": "2011"}, {"value": "2010", "text": "2010"}
            , {"value": "2009", "text": "2009"}, {"value": "2008", "text": "2008"}, {"value": "2007", "text": "2007"}, {"value": "2006", "text": "2006"}, {"value": "2005", "text": "2005"}],
        valueField: 'value',
        textField: 'text'
    });

    $('#sheetName').combobox({
        data: [{"value": "2019", "text": "2019"}, {"value": "2018", "text": "2018"}, {"value": "2017", "text": "2017"}, {"value": "2016", "text": "2016"}, {"value": "2015", "text": "2015"}, {"value": "2014", "text": "2014"}],
        valueField: 'value',
        textField: 'text'
    });

    $('#projectType').combobox({
        data: [{"value": "贸易项目", "text": "贸易项目"}, {"value": "零星项目", "text": "零星项目"}, {
            "value": "弱电项目", "text": "弱电项目"},{"value":"洽商项目","text":"洽商项目"}, {"value": "其它项目", "text": "其它项目"}],
        valueField: 'value',
        textField: 'text'
    });
    $('#projectTypeQuery').combobox({
        data: [{"value": "贸易项目", "text": "贸易项目"}, {"value": "零星项目", "text": "零星项目"}, {
            "value": "弱电项目", "text": "弱电项目"},{"value":"洽商项目","text":"洽商项目"}, {"value": "其它项目", "text": "其它项目"}],
        valueField: 'value',
        textField: 'text'
    });

    $('#stamp').combobox({
        data: [{"value": "0", "text": "未盖章"}, {"value": "1", "text": "已盖章"}],
        valueField: 'value',
        textField: 'text'
    });

    $('#stampQuery').combobox({
        data: [{"value": "0", "text": "未盖章"}, {"value": "1", "text": "已盖章"}],
        valueField: 'value',
        textField: 'text'
    });

    $('#isCompleted').combobox({
        data: [{"value": "0", "text": "未完成"}, {"value": "1", "text": "已完成"}],
        valueField: 'value',
        textField: 'text'
    });

    $('#isCompletedQuery').combobox({
        data: [{"value": "0", "text": "未完成"}, {"value": "1", "text": "已完成"}],
        valueField: 'value',
        textField: 'text'
    });

    $('#amount').numberbox({
        onChange:function(newValue,oldValue){
            var received= $('#received').numberbox('getValue');

            $('#receiveNo').numberbox('setValue',newValue-received);
        }
    });

    $('#received').numberbox({
        onChange:function(newValue,oldValue){
            var amount= $('#amount').numberbox('getValue');

            $('#receiveNo').numberbox('setValue',amount-newValue);
        }
    });

    $('#receiveNo').numberbox({
        onChange:function(newValue,oldValue){
//            var receiveNo= $('#receiveNo').numberbox('getValue');
            var oncredit= $('#oncredit').numberbox('getValue');
            $('#remain').numberbox('setValue',newValue-oncredit);
        }
    });

    $('#oncredit').numberbox({
        onChange:function(newValue,oldValue){
            var receiveNo= $('#receiveNo').numberbox('getValue');

            $('#remain').numberbox('setValue',receiveNo-newValue);
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

   $('#linkContract').combobox({
       url:'conGathering/getContractList',
       valueField: 'id',
       textField: 'text',
       filter: function(q, row){
           var opts = $(this).combobox('options');
           return row[opts.textField].indexOf(q) >= 0;
       }
   });
    
    // 转移对应付款合同界面的年度与项目类型
    $('#tran-sheetName').combobox({
        data: [{"id": "2019", "text": "2019"}, {"id": "2018", "text": "2018"}, {"id": "2017", "text": "2017"}, 
            {"id": "2016", "text": "2016"}, {"id": "2015", "text": "2015"}],
        valueField: 'id',
        textField: 'text',
        onChange: function (newValue, oldValue) {
            var type= $('#tran-conType').combobox('getValue')
            if(type=="零星项目"){
                contractType=1;
            }else if(type=="弱电项目"){
                contractType=2;
            }else if(type=="贸易项目"){
                contractType=3;
            }else if(type=="其它项目"){
                contractType=4;
            }else if(type=="洽商项目"){
                contractType=5;
            }else{
                contractType=0;
            }
            var sheetName=newValue;
            $('#tran-conGathering').combobox({
                valueField: 'id',
                textField: 'text',
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) >= 0;
                },
                url:'payment/getContractName?sheetName='+sheetName+'&contractType='+contractType
            });
            $('#tran-contractSn').combobox({
                valueField: 'id',
                textField: 'text',
                filter: function(q, row){
                    var opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) >= 0;
                },
                url:'payment/getContractSn?sheetName='+sheetName+'&contractType='+contractType
            })
        }
    });
    $('#tran-conType').combobox({
        data: [{"id": "弱电项目", "text": "弱电项目"}, {"id": "零星项目", "text": "零星项目"},
            {"id": "贸易项目", "text": "贸易项目"},{"id":"洽商项目","text":"洽商项目"}, {"id": "其它项目", "text": "其它项目"}],
        valueField: 'id',
        textField: 'text',
        onChange: function (newValue, oldValue)  {
            var sheetName = $('#tran-sheetName').combobox('getValue');
            if (sheetName) {
                var type = newValue;
                if(type=="零星项目"){
                    contractType=1;
                }else if(type=="弱电项目"){
                    contractType=2;
                }else if(type=="贸易项目"){
                    contractType=3;
                }else if(type=="其它项目"){
                    contractType=4;
                }else if(type=="洽商项目"){
                    contractType=5;
                }else{
                    contractType=0;
                }

                $('#tran-conGathering').combobox({
                    valueField: 'id',
                    textField: 'text',
                    filter: function(q, row){
                        var opts = $(this).combobox('options');
                        return row[opts.textField].indexOf(q) >= 0;
                    },
                    url:'payment/getContractName?sheetName=' + sheetName + '&contractType=' + contractType
                });
                $('#tran-contractSn').combobox({
                    valueField: 'id',
                    textField: 'text',
                    filter: function(q, row){
                        var opts = $(this).combobox('options');
                        return row[opts.textField].indexOf(q) >= 0;
                    },
                    url:'payment/getContractSn?sheetName='+sheetName+'&contractType='+contractType
                })
            }
        }
    });

});

// 定义窗口的提交地址
var formUrl = 'conGathering/addConGathering';

// 打开新建合同的窗口
function addContract() {
    $("#conGathering-dlg").dialog("open").dialog('setTitle', '新建收款合同');
    $('#add-form').form('clear');
    formUrl = 'conGathering/addConGathering';

}

// 打开编辑合同的窗口
function editContract() {
    var row = $('#conGathering-dg').datagrid('getSelected');
    if (row) {
        formUrl = 'conGathering/editConGathering';
        $('#add-form').form('clear');
        $('#conGathering-dlg').dialog('open').dialog('setTitle', '编辑收款合同');
        $('#add-form').form('load', row);
        $('#id').val(row.id);
        $('#sheetName').combobox('setValue', row.sheetName);
        $('#projectType').combobox('setValue', row.projectType);
        $('#contractNoYD').val(row.contractNoYD);
        $('#contractNo').val(row.contractNo);
        $('#contractName').textbox('setValue',row.contractName);
        $('#companyFirst').val(row.companyFirst);
        $('#companySecond').val(row.companySecond);
        $('#signDate').datebox("setValue", row.signDate);
        $('#acceptanceDate').datebox("setValue", row.acceptanceDate);
        $('#amount').numberbox("setValue",row.amount);
        $('#received').numberbox("setValue",row.received);
        $('#receiveNo').numberbox("setValue",row.receiveNo);
        $('#oncredit').numberbox("setValue",row.oncredit);
        $('#remain').numberbox("setValue",row.remain);
        $('#gross').numberbox("setValue",row.gross);
        $('#projectManager').val(row.projectManager);
        $('#projectDirector').val(row.projectDirector);
        $('#stamp').combobox("setValue",row.stamp);
        $('#isCompleted').combobox("setValue",row.isCompleted);
        $('#linkContract').combobox("setValue",row.linkContractId);
        $('#linkContract').combobox("setText",row.linkContractName);
        $('#note').textbox("setValue",row.note);
    } else {
        $.messager.alert('提示', '需要选择一个合同，才能进行编辑操作。', 'info');
    }

}

// 删除收款合同
function deleteContract() {
    var row = $('#conGathering-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("conGathering/deleteConGathering", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#conGathering-dg').datagrid('reload');
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

// 保存合同
function saveContract() {

    $('#add-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#conGathering-dlg').dialog('close'); // close the dialog
                $('#conGathering-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

//查询
function query() {
    $("#moreQuery-dlg").dialog("close");
    var params = {
        conNameQuery: $('#conNameQuery').val(),
        comFirstQuery: $('#comFirstQuery').val(),
        amountQuery: $('#amountQuery').val(),
        receivedQuery: $('#receivedQuery').val(),
        oncreditQuery: $('#oncreditQuery').val(),
        sheetNameQuery: $('#sheetNameQuery').combobox("getValue"),
        projectTypeQuery: $('#projectTypeQuery').combobox("getValue"),
        contractNoYDQuery: $('#contractNoYDQuery').val(),
        contractNoQuery: $('#contractNoQuery').val(),
        companySecondQuery: $('#companySecondQuery').val(),
        acceptanceDateQuery: $('#acceptanceDateQuery').datebox("getValue"),
        signDateQuery: $('#signDateQuery').datebox("getValue"),
        receiveNoQuery: $('#receiveNoQuery').val(),
        remainQuery: $('#remainQuery').val(),
        grossQuery: $('#grossQuery').val(),
        projectDirectorQuery: $('#projectDirectorQuery').val(),
        projectManagerQuery: $('#projectManagerQuery').val(),
        stampQuery:$('#stampQuery').combobox("getValue"),
        isCompletedQuery:$('#isCompletedQuery').combobox("getValue")
    };
    $('#conGathering-dg').datagrid({
        url: 'conGathering/conGatheringList',
        queryParams: params
    });

}

//更多条件的查询
function moreQueryCon(){
    $("#moreQuery-dlg").dialog("open").dialog('setTitle', '更多查询条件');
}

//标签
function postil() {
    var row = $('#conGathering-dg').datagrid('getSelected');

    var object = row.contractGatheringPostil;
    if (object != null && object != "") {
        formUrl = 'conGathering/editPostil';
        $('#addPostil-form').form('clear');
        $('#conGatheringPostil-dlg').dialog('open').dialog('setTitle', '批注');
        $('#addPostil-form').form('load', row);
        $('#id').val(row.id);
        $('#PostilId').val(object.id);
        $('#ContractId').val(object.gatheringID);
        if(object.contractNoYDPostil==null){
            $('#contractNoYDPostil').val("");
        }else{
            $('#contractNoYDPostil').val(object.contractNoYDPostil);
        }

        if(object.contractNoPostil==null){
            $('#contractNoPostil').val("");
        }else{
            $('#contractNoPostil').val(object.contractNoPostil);
        }

        if(object.contractNamePostil==null){
            $('#contractNamePostil').val("");
        }else{
            $('#contractNamePostil').val(object.contractNamePostil);
        }

        if(object.companyFirstPostil==null){
            $('#companyFirstPostil').val("");
        }else{
            $('#companyFirstPostil').val(object.companyFirstPostil);
        }

        if(object.companySecondPostil==null){
            $('#companySecondPostil').val("");
        }else{
            $('#companySecondPostil').val(object.companySecondPostil);
        }

        if(object.signDatePostil==null){
            $('#signDatePostil').val("");
        }else{
            $('#signDatePostil').val(object.signDatePostil);
        }

        if(object.amountPostil==null){
            $('#amountPostil').val("");
        }else{
            $('#amountPostil').val(object.amountPostil);
        }

        if(object.receivedPostil==null){
            $('#receivedPostil').val("");
        }else{
            $('#receivedPostil').val(object.receivedPostil);
        }

        if(object.receiveNoPostil==null){
            $('#receiveNoPostil').val("");
        }else{
            $('#receiveNoPostil').val(object.receiveNoPostil);
        }

        if(object.oncreditPostil==null){
            $('#oncreditPostil').val("");
        }else{
            $('#oncreditPostil').val(object.oncreditPostil);
        }

        if(object.remainPostil==null){
            $('#remainPostil').val("");
        }else{
            $('#remainPostil').val(object.remainPostil);
        }

        if(object.projectManagerPostil==null){
            $('#projectManagerPostil').val("");
        }else{
            $('#projectManagerPostil').val(object.projectManagerPostil);
        }

        if(object.projectDirectorPostil==null){
            $('#projectDirectorPostil').val("");
        }else{
            $('#projectDirectorPostil').val(object.projectDirectorPostil);
        }

        if(object.oncreditPostil==null){
            $('#oncreditPostil').val("");
        }else{
            $('#oncreditPostil').val(object.oncreditPostil);
        }

        if(object.receptionDatePostil==null){
            $('#receptionDatePostil').val("");
        }else{
            $('#receptionDatePostil').val(object.receptionDatePostil);
        }

        if(object.partnerPostil==null){
            $('#partnerPostil').val("");
        }else{
            $('#partnerPostil').val(object.partnerPostil);
        }

        if(object.grossPostil==null){
            $('#grossPostil').val("");
        }else{
            $('#grossPostil').val(object.grossPostil);
        }

    }else{
        $("#conGatheringPostil-dlg").dialog("open").dialog('setTitle', '批注');
        $('#addPostil-form').form('clear');
        $("#ContractId").val(row.id);
        formUrl = 'conGathering/addPostil';
    }
}

//操作日志
function conLog(){
    var row = $('#conGathering-dg').datagrid('getSelected');
    if (row) {
        $('#conLog-dlg').dialog('open').dialog('setTitle', '操作日志查询');
        $('#conLog-table').datagrid({
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
                    if (data.rows[i].amount)
                        data.rows[i].amount = new Number(data.rows[i].amount);
                    if (data.rows[i].received)
                        data.rows[i].received = new Number(data.rows[i].received);
                    if (data.rows[i].receiveNo)
                        data.rows[i].receiveNo = new Number(data.rows[i].receiveNo);
                    if (data.rows[i].oncredit)
                        data.rows[i].oncredit = new Number(data.rows[i].oncredit);
                    if (data.rows[i].remain)
                        data.rows[i].remain = new Number(data.rows[i].remain);
                    if (data.rows[i].amountChange)
                        data.rows[i].amountChange = Math.round(data.rows[i].amountChange*100)/100;
                    if (data.rows[i].receivedChange)
                        data.rows[i].receivedChange =Math.round(data.rows[i].receivedChange*100)/100;
                    if (data.rows[i].receiveNoChange)
                        data.rows[i].receiveNoChange = Math.round(data.rows[i].receiveNoChange*100)/100;
                    if (data.rows[i].oncreditChange)
                        data.rows[i].oncreditChange =Math.round(data.rows[i].oncreditChange*100)/100;
                    if (data.rows[i].remainChange)
                        data.rows[i].remainChange = Math.round(data.rows[i].remainChange*100)/100;

                }
                return data;
            }
        });
        $('#conLog-table').datagrid({
            url: 'conLog/conLogList',
            queryParams: {GatheringID:row.id}
        });
    }else{
        $.messager.alert('提示', '需要选择一个合同，才能进行查看操作记录的操作。', 'info');
    }
}

// 保存标签
function savePostil() {

    $('#addPostil-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#conGatheringPostil-dlg').dialog('close'); // close the dialog
                $('#conGathering-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

// 计算合同总额
function contractSum(){
    $('#sum-dlg').dialog('open').dialog('setTitle', '合同汇总');
    $('#sum-form').form('clear');
    var params = {
        conNameQuery: $('#conNameQuery').val(),
        comFirstQuery: $('#comFirstQuery').val(),
        amountQuery: $('#amountQuery').val(),
        receivedQuery: $('#receivedQuery').val(),
        oncreditQuery: $('#oncreditQuery').val(),
        sheetNameQuery: $('#sheetNameQuery').combobox("getValue"),
        projectTypeQuery: $('#projectTypeQuery').combobox("getValue"),
        contractNoYDQuery: $('#contractNoYDQuery').val(),
        contractNoQuery: $('#contractNoQuery').val(),
        companySecondQuery: $('#companySecondQuery').val(),
        signDateQuery: $('#signDateQuery').datebox("getValue"),
        acceptanceDateQuery: $('#acceptanceDateQuery').datebox("getValue"),
        receiveNoQuery: $('#receiveNoQuery').val(),
        remainQuery: $('#remainQuery').val(),
        grossQuery: $('#grossQuery').val(),
        projectDirectorQuery: $('#projectDirectorQuery').val(),
        projectManagerQuery: $('#projectManagerQuery').val(),
        stampQuery:$('#stampQuery').combobox("getValue"),
        isCompletedQuery:$('#isCompletedQuery').combobox("getValue")
    };
    $.ajax({
        url: 'conGathering/gatheringSum',
        type: 'POST',
        data:params,
        dataType: 'json',
        success: function (result) {
            if (result) {
                $('#count').numberbox("setValue",result.count);
                $('#amountSum').numberbox("setValue",result.amountSum);
                $('#receivedSum').numberbox("setValue",result.receivedSum);
                $('#receiveNoSum').numberbox("setValue",result.receiveNoSum);
                $('#oncreditSum').numberbox("setValue",result.oncreditSum);
                $('#remainSum').numberbox("setValue",result.remainSum);
                $('#grossSum').numberbox("setValue",result.grossSum);
                //$('#sum-table').datagrid('reload');
            }
            else {
                $.messager.alert('错误', result.message, 'error');
            }
        }
    });
}

// 操作查询窗口
function operationQuery(){

    $('#operationQuery-dlg').dialog('open').dialog('setTitle', '操作查询');
    $('#beginDate').datetimebox('setValue',"");
    $('#endDate').datetimebox('setValue',"");
    $('#gContractNO').val("");
    $('#operationQuery-table').datagrid('load',{});

}

// 操作查询显示
function operationDisplay(){

    var beginDate = $('#beginDate').datetimebox('getValue');
    var endDate= $('#endDate').datetimebox('getValue');
    var contractNO= $('#gContractNO').val();

    $('#operationQuery-table').datagrid({
        url: 'conLog/operationQueryList',
        queryParams: {
            beginDate: beginDate,
            endDate:endDate,
            contractNo:contractNO
        }

    });

}


///////////////////////////////////////////////////////////////////////////

//查看付款合同
function viewPayment(){

    var row = $('#conGathering-dg').datagrid('getSelected');
    if (row) {
        var contractName=row.contractName;
        if(contractName==""){
            contractName=null;
        }
        $('#payment-dlg').dialog('open').dialog('setTitle', '查看付款合同');
        $('#payment-table').datagrid({
            url: 'payment/paymentList',
            queryParams: {
                conGatheringNameQuery:contractName
            }
        });
    }else{
        $.messager.alert('提示', '需要选择一个合同，才能进行查看付款合同的操作。', 'info');
    }

}

//查询
function payQuery(){
    var row = $('#conGathering-dg').datagrid('getSelected');
    var params = {
        contractName:row.contractName,
        contentQuery: $('#pContentQuery').val(),
        personQuery: $('#pPersonQuery').val(),
        contractSumQuery: $('#contractSumQuery').val(),
        companySecondQuery:$('#pCompanySecondQuery').val()
    };
    $('#payment-table').datagrid({
        url: 'payment/paymentList',
        queryParams: params
    });

}

//增加付款合同窗口
function addPayment(){
    $("#paymentDlg").dialog("open").dialog('setTitle', '新建付款合同');
    $('#addPayment').form('clear');
    formUrl = 'payment/addPayment';
}

// 打开编辑付款合同的窗口
function editPayment() {
    var row = $('#payment-table').datagrid('getSelected');
    if (row) {
        formUrl = 'payment/editPayment';
        $('#addPayment').form('clear');
        $('#paymentDlg').dialog('open').dialog('setTitle', '编辑付款合同');
        $('#addPayment').form('load', row);
        $('#paymentId').val(row.id);
        $('#sheet').val(row.sheetName);
        $('#contractName').textbox('setValue',row.contractName);
        $('#contractSn').val(row.contractSn);
        $('#paymentCompanyFirst').val(row.companyFirst);
        $('#paymentCompanySecond').val(row.companySecond);
        $('#purchaseContent').val(row.purchaseContent);
        $('#paymentSignDate').datebox("setValue", row.signDate);
        $('#purchasePerson').val(row.purchasePerson);
        $('#contractSum').numberbox("setValue",row.contractSum);
        $('#payment').numberbox("setValue",row.payment);
        $('#paymentNo').numberbox("setValue",row.paymentNo);
        $('#condition').val(row.condition);
        $('#paymentNote').val(row.note);
    } else {
        $.messager.alert('提示', '需要选择一个付款合同，才能进行编辑操作。', 'info');
    }

}

//保存按键
function savePayment(){
    var row = $('#conGathering-dg').datagrid('getSelected');
    $('#paymentContractName').val(row.contractName);
    $('#belongContractId').val(row.id);
    $('#sheet').val(row.sheetName);
    $('#contractType').val(row.projectType);
    $('#addPayment').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#paymentDlg').dialog('close'); // close the dialog
                $('#payment-table').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

// 删除付款合同
function deletePayment() {
    var row = $('#payment-table').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("payment/deletePayment", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后刷新表格
                        $('#payment-table').datagrid('reload');
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
    var row = $('#conGathering-dg').datagrid('getSelected');
    var params = {
        contractName:row.contractName,
        contentQuery: $('#pContentQuery').val(),
        personQuery: $('#pPersonQuery').val(),
        contractSumQuery: $('#contractSumQuery').val(),
        companySecondQuery:$('#pCompanySecondQuery').val()
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
    var row = $('#payment-table').datagrid('getSelected');

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
                $('#payment-table').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });
}

//操作日志
function paymentLog(){
    var row = $('#payment-table').datagrid('getSelected');
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
                    if (data.rows[i].contractSum)
                        data.rows[i].contractSum = new Number(data.rows[i].contractSum);

                    if (data.rows[i].contractSumChange)
                        data.rows[i].contractSumChange = new Number(data.rows[i].contractSumChange);

                    if (data.rows[i].payment)
                        data.rows[i].payment = new Number(data.rows[i].payment);

                    if (data.rows[i].paymentChange)
                        data.rows[i].paymentChange = new Number(data.rows[i].paymentChange);


                    if (data.rows[i].paymentNo)
                        data.rows[i].paymentNo = new Number(data.rows[i].paymentNo);

                    if (data.rows[i].paymentNoChange)
                        data.rows[i].paymentNoChange = new Number(data.rows[i].paymentNoChange);


                    if (data.rows[i].contractSumChange)
                        data.rows[i].contractSumChange =Math.round(data.rows[i].contractSumChange*100)/100;

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


// 未付款合同操作查询窗口
function operationPaymentQuery(){

    $('#operationPaymentQuery-dlg').dialog('open').dialog('setTitle', '操作查询');
    $('#paymentBeginDate').datetimebox('setValue',"");
    $('#paymentEndDate').datetimebox('setValue',"");
    $('#pContractName').val("");
    $('#paymentContractSn').val("");
    $('#operationPaymentQuery-table').datagrid('load',{});
}

// 未付款合同操作查询显示
function operationPaymentDisplay(){

    var beginDate = $('#paymentBeginDate').datetimebox('getValue');
    var endDate= $('#paymentEndDate').datetimebox('getValue');
    var contractName=$('#pContractName').val();
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

//转移付款合同窗口
function openTransform(){
    $("#transform-dlg").dialog("open").dialog('setTitle', ' 转移付款合同');
    $('#transform-form').form('clear');
}

//转移付款合同
function transformPayment(){
    var row = $('#conGathering-dg').datagrid('getSelected');
    $.ajax({
        url:'conGathering/transformPayment',
        type:'POST',
        data:{
            oldConId:row.id,
            oldConName:row.contractName,
            newConId:$('#tran-conGathering').combobox('getValue')
        },
        dataType: 'json',
        success: function (result) {
            if (result.success) {
                $('#transform-dlg').dialog('close'); // close the dialog
                $.messager.alert('操作成功','操作成功','info');
            } else {
                $.messager.alert('操作失败', result.message, 'error');
            }
        }
    });
}

function onChangeContractSn(){
    $.ajax({
        url:'conGathering/getConGatheringById',
        type:'POST',
        data:{
            id:$('#tran-contractSn').combobox('getValue')
        },
        dataType: 'json',
        success: function (result) {
            $('#tran-conGathering').combobox('setValue',result.id);
            $('#tran-conGathering').combobox('setText',result.contractName)
        }
    });
}

function onChangeConGathering(){
    $.ajax({
        url:'conGathering/getConGatheringById',
        type:'POST',
        data:{
            id:$('#tran-conGathering').combobox('getValue')
        },
        dataType:'json',
        success:function(result){
            $('#tran-contractSn').combobox('setValue',result.id);
            $('#tran-contractSn').combobox('setText',result.contractNo)
        }
    });
}

function exportExcel(){
    var params = {
        conNameQuery: $('#conNameQuery').val(),
        comFirstQuery: $('#comFirstQuery').val(),
        amountQuery: $('#amountQuery').val(),
        receivedQuery: $('#receivedQuery').val(),
        oncreditQuery: $('#oncreditQuery').val(),
        sheetNameQuery: $('#sheetNameQuery').combobox("getValue"),
        projectTypeQuery: $('#projectTypeQuery').combobox("getValue"),
        contractNoYDQuery: $('#contractNoYDQuery').val(),
        contractNoQuery: $('#contractNoQuery').val(),
        companySecondQuery: $('#companySecondQuery').val(),
        acceptanceDateQuery: $('#acceptanceDateQuery').datebox("getValue"),
        signDateQuery: $('#signDateQuery').datebox("getValue"),
        receiveNoQuery: $('#receiveNoQuery').val(),
        remainQuery: $('#remainQuery').val(),
        grossQuery: $('#grossQuery').val(),
        projectDirectorQuery: $('#projectDirectorQuery').val(),
        projectManagerQuery: $('#projectManagerQuery').val(),
        stampQuery:$('#stampQuery').combobox("getValue"),
        isCompletedQuery:$('#isCompletedQuery').combobox("getValue")
    };
    $.messager.confirm('确认','是否将文件导出为Excel文件',function(r){
        if(r)
        {
            $('#query-form').form('submit',{
                url:"conGathering/exportExcel",
                queryParams:params,
                method:"post",
                success:function(data){
                    if(!data.success) {
                        $.message.alert("导出Excel失败",result.message,'error');
                    }
                }
            });
        }
    });
}

function onSelectProjectType(record){
    if(record.text == "贸易项目"){
        $('#contractName').textbox('setValue',"产品购销合同");
    }
}

//查看附加合同
function viewLinkContract(){

    var row = $('#conGathering-dg').datagrid('getSelected');
    if (row) {
        $('#linkContract-dlg').dialog('open').dialog('setTitle', '查看附加合同');
        $('#linkContract-table').datagrid({
            queryParams: {
                linkContract:row.id
            }
        });
    }else{
        $.messager.alert('提示', '需要选择一个合同，才能进行查看附加合同的操作。', 'info');
    }

}

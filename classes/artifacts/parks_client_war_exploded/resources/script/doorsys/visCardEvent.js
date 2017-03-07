/**
 * 访客读卡数据查询
 */
$(function () {

    var $dg = $("#visCardEvent-dg");

    $dg.datagrid({
        method: 'post',
        url: 'cardEvent/visCardEventList',
        sortName:'eTime',//默认排序字段，会有三角形显示
        sortOrder:'desc',
        remoteSort:false,//默认为 true
        fit:true,
        striped: true,
        fitColums: true,
        rownumbers: true,
        pagination: true,
        singleSelect: true,
        rowStyler: function (index, row) {
            if (row.ioType === "进") {//进：绿色背景
                return 'background-color:#A8BE3F';
            } else {//出：灰色背景
                return 'background-color:#BAAFAF';
            }
        }
        //columns:[[
        //    {field:"id", width:140, align:'center', hidden:true, title:'id'},
        //    {field:"eTime", width:140, align:'center', title:'时间', sortable:true},
        //    {field:"personName", width:140, align:'center', title:'姓名', sortable:true},
        //    {field:"cardNo", width:140, align:'center', title:'卡号'},
        //    {field:"idNo", width:140, align:'center', title:'身份证号'},
        //    {field:"deptName", width:140, align:'center', title:'单位', sortable:true},
        //    {field:"doorName", width:140, align:'center', title:'位置', sortable:true},
        //    {field:"eResult", width:140, align:'center', title:'事件结果'},
        //    {field:"ioType", width:140, align:'center', title:'进出方向', sortable:true}]
        //],
        //title:'访客读卡数据列表',
        //toolbar: '#visCardEvents-toolbar'
    });

    //添加校验
    var $beginTime = $("#beginTime");
    $beginTime.datetimebox({
        required: true,
        validType:'dtFormat'
    });

    var $endTime = $("#endTime");
    $endTime.datetimebox({
        required: true,
        validType:["dtFormat","lateThanBegin['#beginTime']"]
    });

    //绑定按钮事件, bind 有效
    $('#queryBtn').bind('click', queryHandler);
    $('#exportBtn').bind('click', exportHandler);
    $('#printBtn').bind('click', printHandler);

    //添加复选框的切换效果
    $('#tbl :checkbox').each(function () {
        var $curr = $(this);
        $curr.click(function () {
            if(this.value === "false"){
                this.value = "true";
            }else{
                this.value = "false";
            }
        });
    });

    //绑定按钮事件，click 无效
    //$('#queryBtn').click = queryHandler;
    //linkbutton 的 onClick 事件
    //$('#queryBtn').onClick = queryHandler;

    /**
     * 验证表单
     * @param id 表单 ID
     */
    function validateForm(id){
        return $("#"+id).form('enableValidation').form('validate');
    }

    /**
     * 查询
     */
    function queryHandler(){
        //必须校验通过才能查询
        if(!validateForm("qForm")){
            return;
        }

        var params = {
            deptNameCheck: JSON.parse($("#deptNameCheck").val()),//string "false" -> boolean false.
            deptName: $("#deptName").textbox('getText'),
            doorNameCheck: JSON.parse($("#doorNameCheck").val()),
            doorName: $("#doorName").textbox('getText'),
            beginTime: $("#beginTime").datetimebox('getValue'),
            personNameCheck: JSON.parse($("#personNameCheck").val()),
            personName: $("#personName").textbox('getText'),
            cardNoCheck: JSON.parse($("#cardNoCheck").val()),
            cardNo: $("#cardNo").textbox('getText'),
            endTime: $("#endTime").datetimebox('getValue')
        };

        //load new data from server
        $dg.datagrid('load', params);
    }

    /**
     * 导出 Excel
     */
    function exportHandler(){
        console.log("exportHandler!");
    }

    /**
     * 打印
     */
    function printHandler(){
        console.log("printHandler!");
    }
});
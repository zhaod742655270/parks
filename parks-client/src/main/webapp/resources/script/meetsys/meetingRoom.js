/**
 * Created by feng on 2015/1/29.
 */
$(function () {
    loadBtns('conf-btns', $('#menuId').val());
    $('#conf-dg').datagrid({
        title: '会议室列表',
        toolbar: '#conf-toolbar',
        pagination: true,
        sortName: 'name',
        sortOrder: 'asc',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'post',
        url:'meetRoom/meetRoomList'
    });
})

var formUrl ='meetRoom/addMeetRoom';

//新建会议室
function addMeetRoom() {
    $("#conf-dlg").dialog({width: 360,height: 320,resizable: true});
    $("#conf-dlg").dialog("open").dialog('setTitle', '新建会议室');
    $('#conf-form').form('clear');
    formUrl = 'meetRoom/addMeetRoom';
}

//编辑会议室
function editMeetRoom(){
    var row = $('#conf-dg').datagrid('getSelected');
    if (row){
        $('#conf-dlg').dialog('open').dialog('setTitle','编辑会议室');
        $('#conf-form').form('load',row);
        $('#id').val(row.id);
        $('#name').val(row.name);
        $('#regionId').combobox("setValue", row.regionId);
        $('#capacity').val(row.capacity);
        $('#descInfo').val(row.descInfo);
       formUrl = 'meetRoom/editMeetRoom';
    }
    else{
        $.messager.alert('提示', '需要选择一条记录，才能进行编辑操作。', 'info');
    }
}

//删除会议室
function deleteMeetRoom() {
    var row = $('#conf-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确定', '你确定要删除这条记录?', function (r) {
            if (r) {
                $.ajax({
                    url:  'meetRoom/ deleteMeetRoom',
                    type: 'POST',
                    data:{meetRoomId:row.id},
                    dataType: 'json',
                    error: function () {
                        $.messager.alert('操作失败', '已分配的会议室不能删除!', 'error');
                    },
                    success: function () {
                        $('#conf-dg').datagrid('reload');
                    }
                });

            }
        });
    }
    else{
        $.messager.alert('提示', '需要选择一条记录，才能进行编辑操作。', 'info');
    }
}


//保存
function saveMeetRoom(){
    $("#conf-form").form('submit',{
     url:formUrl,
     onsubmit:function(){
         return $(this).form('validate');
     },
     success:function(data){
         var result = jQuery.parseJSON(data);
         if (result.success) {
             $('#conf-dlg').dialog('close'); // close the dialog
             $('#conf-dg').datagrid('reload');
         } else {
             $.messager.alert('保存失败', result.message, 'error');
         }
     }
    });
}
/**
 * Created by feng on 2015/1/30.
 */
$(function () {

    $('#equipId').combobox({
        url: 'meetRoom/getAllMeetRooms',
        valueField: 'id',
        textField: 'name',
        onSelect: function (rec) {
            $('#equip-east').datagrid('reload', { roomId: rec.id });
            $('#equip-west').datagrid('load');
        },
        onLoadSuccess: function () {
            var data = $(this).combobox('getData');
            if (data && data.length > 0) {
                $(this).combobox('select', data[0].id);
            }
        }
    });


    $('#equip-west').datagrid({
       title: '设备列表',
       toolbar: '#equip-toolbar',
        pagination: true,
        sortName: 'descInfo',
        sortOrder: 'asc',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'post',
        multiSort:true,
        url:'../supportsys/device/meetRoomNotRelatedeviceList'
    });


    $('#equip-east').datagrid({
       sortName: 'descInfo',
        sortOrder: 'asc',
        striped: true,
        title:'已分配设备',
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: true,
        method: 'post',
        multiSort:true,
        url:'../supportsys/device/meetRoomRelatedeviceList'
    });
})



function distribute(){
    var row = $('#equip-west').datagrid('getSelected');
    var val = $('#equipId').combobox('getValue');
    if (row) {
        $.messager.confirm('确定', '你确定要绑定这个设备?', function (r) {
            if (r) {
                $.ajax({
                    url: '../supportsys/deviceDescRelation/distribute',
                    type: 'POST',
                    data:{deviceId:row.id,roomId:val},
                    dataType: 'json',
                    error: function () {
                        $.messager.alert('操作失败', '请重新操作!', 'error');
                    },
                    success: function () {
                        $('#equip-west').datagrid('reload');
                        $('#equip-east').datagrid('reload');
                    }
                });

            }
        });
    }
    else {
        $.messager.alert('提示', '需要选择一个设备，才能进行编辑操作。', 'info');
    }
}




function withdraw(){
    var row = $('#equip-east').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确定', '你确定要解绑这个设备?', function (r) {
            if (r) {
                $.ajax({
                    url: '../supportsys/deviceDescRelation/withdraw',
                    type: 'POST',
                    data:{id:row.id},
                    dataType: 'json',
                    error: function () {
                        $.messager.alert('操作失败', '请重新操作!', 'error');
                    },
                    success: function () {
                        $('#equip-east').datagrid('reload');
                        $('#equip-west').datagrid('reload');
                    }
                });

            }
        });
    }
    else {
        $.messager.alert('提示', '需要选择一个设备，才能进行编辑操作。', 'info');
    }
}
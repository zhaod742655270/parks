
$(function () {

    $.ajaxSetup({
        cache: false //关闭AJAX相应的缓存
    });
    $('#region-tree').tree(
        {
            url: 'region/getTree',
            method: 'post',
            animate: true,
            lines: true,
            dnd: true,
            onContextMenu: function (e, node) {
                e.preventDefault();
                $(this).tree('select', node.target);
                $('#region-mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            },
            onBeforeDrop: function (target, source, point) {

                var moveBack = true;


                var targetId = $('#region-tree').tree('getNode', target).id;
                var sourceId = source.id;
                var sourceName = source.text;
                $.ajax({
                    async: false,
                    url: 'region/moveregion',
                    type: 'POST',
                    data: {
                        targetNodeId: targetId,
                        sourceNodeId: sourceId,
                        point: point
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.success) {
                            moveBack = false;
                        }
                        else {
                            $.messager.alert('错误', result.message, 'error');
                        }
                    }
                });
                return !moveBack;

            }

        }
    )
    $.ajax({
        async: false,
        url: 'pri/getBtns?menuId=' + $('#menuId').val(),
        type: 'post',
        dataType: 'json',
        success: function (result) {
            if (result) {
                for (var i = 0; result.length > i; i++) {
                    var dom = result[i].btn;
                    $('#regions-menu').append(dom);
                    dom = dom.replace("<a", "<div");
                    dom = dom.replace("a>", "div>");
                    dom = dom.replace("class='easyui-linkbutton'", "")
                    $('#region-mm').append(dom);
                }
                $('#region-mm').menu();
                $.parser.parse();

            }
        }
    });

})
// 定义窗口的提交地址
var formUrl = 'region/addregion';
// 打开新建区域的窗口

function addRegion() {
    $("#region-dlg").dialog("open").dialog('setTitle', '新建区域');
    $('#region-form').form('clear');
    $('#parenId').combobox('reload', 'region/regionList');

    formUrl = 'region/addRegion';
    var row = $('#region-tree').tree('getSelected');
    if (row) {

        $('#parentId').combobox('setValue', row.id);
    }
}
// 打开编辑区域的窗口
function editRegion() {
    $('#parenId').combobox('reload');
    var row = $('#region-tree').tree('getSelected');
    if (row) {

        $('#region-form').form('clear');
        $.post("region/getRegion", {
            id: row.id
        }, function (result) {
            if (result.success) {
                $('#region-dlg').dialog('open').dialog('setTitle', '编辑区域');
                $('#region-form').form('load', result.data[0]);

            } else {
                $.messager.alert('操作失败', result.message, 'error');
                return false;
            }
        }, "json");
        formUrl = 'region/editRegion';
    }
    else {
        $.messager.alert('提示', '需要选择一个区域，才能进行编辑操作。', 'info');
    }
}

// 删除区域
function deleteRegion() {
    var row = $('#region-tree').tree('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该区域以及子区域？', function (r) {
            if (r) {
                $.post("region/deleteregion", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后操作tree
                     d
                        $('#region-tree').tree('remove', row.target);

                    } else {

                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个区域，才能进行删除操作。', 'info');
    }

}
// 保存区域
function saveRegion() {

    $('#region-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#region-dlg').dialog('close'); // close the dialog

                var t = $('#region-tree');
                //如果是新建
                if (formUrl.indexOf('add') > 0) {
                    //找到父节点
                    var node = t.tree('find', result.data[0].parentId);
                    t.tree('append',
                        { parent: (node ? node.target : null),
                            data: [
                                {id: result.data[0].id, text: result.data[0].regionName}
                            ]
                        });

                }
                //如果是修改
                else {
                    var oldNode = t.tree('getSelected');
                    var newNode = {id: oldNode.id, text: $('#regionName').val()};
                    var parentNode = t.tree('find', $('#parentId').combobox('getValue'));

                    t.tree('update', {
                        target: oldNode.target,
                        text: newNode.text
                    });
                }
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

//区域数据转换为easyui可识别的数据
function convert(rows) {
    function exists(rows, parentId) {
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].id == parentId) return true;
        }
        return false;
    }

    var nodes = [];
    // get the top level nodes
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        if (!exists(rows, row.parentId)) {
            nodes.push({
                id: row.id,
                text: row.regionName
            });
        }
    }

    var toDo = [];
    for (var i = 0; i < nodes.length; i++) {
        toDo.push(nodes[i]);
    }
    while (toDo.length) {
        var node = toDo.shift();    // the parent node
        // get the children nodes
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            if (row.parentId == node.id) {
                var child = {id: row.id, text: row.regionName};
                if (node.children) {
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }
    return nodes;
}
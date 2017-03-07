/**
 *
 */
$(function () {

    $.ajaxSetup({
        cache: false //关闭AJAX相应的缓存
    });
    $('#dept-tree').tree(
        {
            url: 'dept/getTree',
            method: 'post',
            animate: true,
            lines: true,
            dnd: true,
            onContextMenu: function (e, node) {
                e.preventDefault();
                $(this).tree('select', node.target);
                $('#dept-mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            },
            onBeforeDrop: function (target, source, point) {

                var moveBack = true;


                var targetId = $('#dept-tree').tree('getNode', target).id;
                var sourceId = source.id;
                var sourceName = source.text;
                $.ajax({
                    async: false,
                    url: 'dept/moveDept',
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
                    $('#depts-menu').append(dom);
                    dom = dom.replace("<a", "<div");
                    dom = dom.replace("a>", "div>");
                    dom = dom.replace("class='easyui-linkbutton'", "")
                    $('#dept-mm').append(dom);
                }
                $('#dept-mm').menu();
                $.parser.parse();

            }
        }
    });

})
// 定义窗口的提交地址
var formUrl = 'dept/addDept';
// 打开新建部门的窗口

function addDept() {
    $("#dept-dlg").dialog("open").dialog('setTitle', '新建部门');
    $('#dept-form').form('clear');
    $('#parenId').combobox('reload', 'dept/deptList');

    formUrl = 'dept/addDept';
    var row = $('#dept-tree').tree('getSelected');
    if (row) {

        $('#parentId').combobox('setValue', row.id);
    }
}
// 打开编辑部门的窗口
function editDept() {
    $('#parenId').combobox('reload');
    var row = $('#dept-tree').tree('getSelected');
    if (row) {

        $('#dept-form').form('clear');
        $.post("dept/getDept", {
            id: row.id
        }, function (result) {
            if (result.success) {
                $('#dept-dlg').dialog('open').dialog('setTitle', '编辑部门');
                $('#dept-form').form('load', result.data[0]);

            } else {
                $.messager.alert('操作失败', result.message, 'error');
                return false;
            }
        }, "json");
        formUrl = 'dept/editDept';
    }
    else {
        $.messager.alert('提示', '需要选择一个部门，才能进行编辑操作。', 'info');
    }
}

// 删除部门
function deleteDept() {
    var row = $('#dept-tree').tree('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该部门以及子部门？', function (r) {
            if (r) {
                $.post("dept/deleteDept", {
                    id: row.id
                }, function (data) {
                    if (data.success) {
                        // 成功后操作tree
                        //$('#dept-tree').tree('reload');
                        $('#dept-tree').tree('remove', row.target);

                    } else {

                        $.messager.alert('操作失败', data.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个部门，才能进行删除操作。', 'info');
    }

}
// 保存部门
function saveDept() {

    $('#dept-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#dept-dlg').dialog('close'); // close the dialog
                // $('#dept-tree').tree('reload');
                // var p = $('#parenId');
                // var oldDepts = p.combobox('getData');
                var t = $('#dept-tree');
                //如果是新建
                if (formUrl.indexOf('add') > 0) {
                    //找到父节点
                    var node = t.tree('find', result.data[0].parentId);
                    t.tree('append',
                        { parent: (node ? node.target : null),
                            data: [
                                {id: result.data[0].id, text: result.data[0].deptName}
                            ]
                        });

                }
                //如果是修改
                else {
                    var oldNode = t.tree('getSelected');
                    var newNode = {id: oldNode.id, text: $('#deptName').val()};
                    var parentNode = t.tree('find', $('#parentId').combobox('getValue'));

                    t.tree('update', {
                        target: oldNode.target,
                        text: newNode.text
                    });
                    //t.tree('remove', oldNode.target);
                    // t.tree('append',
                    //    { parent: (parentNode ? parentNode.target : null),
                  //  data: [newNode]
                    //    });


                }
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}

//部门数据转换为easyui可识别的数据
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
                text: row.deptName
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
                var child = {id: row.id, text: row.deptName};
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
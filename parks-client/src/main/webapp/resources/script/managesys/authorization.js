/**
 * Created by len on 14-7-23.
 */
$(function () {
    $('#role-dg').datagrid(
        {
            title: '角色列表',
            pagination: true,
            sortName: 'roleName',
            sortOrder: 'asc',
            striped: true,
            rownumbers: true,
            fitColums: true,
            fit: true,
            singleSelect: true,
            method: 'get', url: 'role/roleList',
            onSelect: function (rowIndex, rowData) {

                var appId = $('#appId').combobox('getValue');
                if (appId) {
                    var roleId = rowData.id;
                    loadPrivileges(appId, roleId);
                }

            }
        });
    $('#appId').combobox({
        url: 'app/getAllApp',
        valueField: 'id',
        textField: 'appName',
        onSelect: function (rec) {
            var appId = $('#appId').combobox('getValue');
            $.post("pri/getTree", {
                appId: appId
            }, function (result) {
                // 成功后加载权限树
                $('#privilege-tree').tree("loadData", result);
                var row = $('#role-dg').datagrid('getSelected');
                if (row) {
                    loadPrivileges(appId, row.id);
                }

            }, "json");

        },
        onLoadSuccess: function () {
            var data = $(this).combobox('getData');
            if (data && data.length > 0) {
                $(this).combobox('select', data[0].id);
            }
        }
    });
    $('#privilege-tree').tree({

        animate: true,
        lines: true,
        dnd: false,
        checkbox: true,
        cascadeCheck: false,
        onContextMenu: function (e, node) {
            e.preventDefault();
            $(this).tree('select', node.target);
            $('#privilege-mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        },
        onCheck: function (node, checked) {
            var pTree = $('#privilege-tree');
            if (node == null) {
                return;
            }
            var parentNode = pTree.tree("getParent", node.target);
            //选中某个node所做的操作
            if (checked) {
                //如果选中的节点有父节点，那么父节点也被选中。
                if (parentNode) {
                    pTree.tree("check", parentNode.target);
                }

            }
            //取消选中某个node所做的操作
            else {
                //如果取消的节点有子节点，那么把子节点也取消
                if (node.children) {
                    for (var i = 0; i < node.children.length; i++) {
                        pTree.tree("uncheck", node.children[i].target);
                    }
                }

            }
        }

    });
})
function checkedParent(pTree, node) {

    var parentNode = pTree.tree("getParent", node.target);
    if (parentNode) {
        pTree.tree("check", parentNode.target);
        checkedParent(pTree, parentNode);
    }

}
function uncheckedParent(pTree, node) {

    var parentNode = pTree.tree("getParent", node.target);
    if (parentNode) {

        pTree.tree("uncheck", parentNode.target);
        uncheckedParent(pTree, parentNode);
    }

}
function chechedChildren() {
    var pTree = $('#privilege-tree');
    var node = pTree.tree('getSelected');
    pTree.tree("check", node.target);
    var childrenNode = pTree.tree('getChildren', node.target);
    for (var i = 0; i < childrenNode.length; i++) {
        pTree.tree("check", childrenNode[i].target);
    }


}
function unchechedChildren() {

    var pTree = $('#privilege-tree');
    var node = pTree.tree('getSelected');
    pTree.tree("uncheck", node.target);
    var childrenNode = pTree.tree('getChildren', node.target);
    for (var i = 0; i < childrenNode.length; i++) {
        pTree.tree("uncheck", childrenNode[i].target);
    }
}
function unchechedChildrenByNode(node) {
    var pTree = $('#privilege-tree');
    pTree.tree("uncheck", node.target);
    var childrenNode = pTree.tree('getChildren', node.target);
    for (var i = 0; i < childrenNode.length; i++) {
        pTree.tree("uncheck", childrenNode[i].target);
    }
}

function loadPrivileges(appId, roleId) {

    $.post("pri/priList", {
        appId: appId,
        roleId: roleId
    }, function (result) {
        // 成功后将权限绑定到树上
        bindingPriToTree(result);
    }, "json");

}
function bindingPriToTree(pris) {
    var pTree = $('#privilege-tree');
    var rootNode = pTree.tree('getRoot');
    pTree.tree("uncheck", rootNode.target);
    if (pris == null)
        return;
    for (var i = 0; i < pris.length; i++) {

        var nodes = pTree.tree('find', pris[i].priResId);
        if (nodes == null) {
            continue;
        }
        else if (nodes.length > 1) {
            for (var j = 0; j < nodes.length; j++) {
                if (nodes[j].attributes.priResType == pris[i].priResType) {
                    pTree.tree('check', nodes[j].target);
                    break;
                }
            }
        }
        else {
            pTree.tree('check', nodes.target);
        }


    }

}
function savePrivilege() {
    var row = $('#role-dg').datagrid('getSelected');
    if (row) {
        var nodes = $('#privilege-tree').tree('getChecked');
        var datas = [];
        var priOwnerType = "role";
        var priOwnerId = row.id;
        for (var i = 0; i < nodes.length; i++) {
            var pri = {};
            pri.priOwnerType = priOwnerType;
            pri.priOwnerId = priOwnerId;
            pri.priResType = nodes[i].attributes.priResType;
            pri.priResId = nodes[i].id;
            pri.url= nodes[i].attributes.url;
            datas.push(pri);
        }
        var param = {};
        for (var i = 0; i < datas.length; i++) {
            param["pris[" + i + "].priOwnerType"] = datas[i].priOwnerType;
            param["pris[" + i + "].priOwnerId"] = datas[i].priOwnerId;
            param["pris[" + i + "].priResType"] = datas[i].priResType;
            param["pris[" + i + "].priResId"] = datas[i].priResId;
            param["pris[" + i + "].url"] = datas[i].url;
        }
        param.appId = $('#appId').combobox('getValue');
        param.roleId = priOwnerId;
        $.ajax({
            url: 'pri/savePris',
            type: 'POST',
            data: param,
            dataType: 'json',
            success: function (result) {
                if (result.success) {
                    $.messager.alert('提示', result.message, 'info');
                }
                else {
                    $.messager.alert('错误', result.message, 'error');
                }
            }
        });
    }
    else {
        $.messager.alert('提示', "你需要选择一个角色，才可以进行授权操作", 'info');
    }
}
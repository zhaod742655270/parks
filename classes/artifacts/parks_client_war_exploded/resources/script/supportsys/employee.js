/**
 *
 */
$(function () {
    $('#dept-tree').tree(
        {
            url: '../managesys/dept/getTreeByDeptId',
            method: 'get',
            animate: true,
            lines: true,
            dnd: false,
            onSelect: function (node) {
                $('#employee-dg').datagrid('load', {
                    'emp.deptId': node.id
                });
                //清空检索条件
                $('#search-form').form('clear');
                $('#searchDept').combobox('setValue', node.id);
            }
        }
    );
    $('#employee-dg').datagrid({
        title: '员工列表',
        toolbar: '#employees-toolbar',
        pagination: true,
        sortName: 'empName',
        sortOrder: 'asc',
        striped: true,
        rownumbers: true,
        fitColums: true,
        fit: true,
        singleSelect: false,
        method: 'post',
        url: '../supportsys/emp/empList',
        onDblClickRow: function (rowIndex, rowData) {
            editEmp(rowData);
        }
    });

})

//使用IE条件注释来判断是否IE6，通过判断userAgent不一定准确
if (document.all) document.write('<!--[if lte IE 6]><script type="text/javascript">window.ie6= true<\/script><![endif]-->');
// var ie6 = /msie 6/i.test(navigator.userAgent);//不推荐，有些系统的ie6 userAgent会是IE7或者IE8

function preview(picId, fileId) {
    //更改标删除标志为false
    $('#delPhoto').val(false);

    var pic = document.getElementById(picId);
    var file = document.getElementById(fileId);
    if (window.FileReader) {//chrome,firefox7+,opera,IE10,IE9，IE9也可以用滤镜来实现
        oFReader = new FileReader();
        if (file.files.length > 0) {
            oFReader.readAsDataURL(file.files[0]);
            oFReader.onload = function (oFREvent) {
                pic.src = oFREvent.target.result;
            };
        }
        else {

            pic.src = "";
        }

    }
    else if (document.all) {//IE8-
        file.select();
        var reallocalpath = document.selection.createRange().text//IE下获取实际的本地文件路径
        if (window.ie6) pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可以直接显示图片
        else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所以注意判断FileReader先
            pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
            pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//设置img的src为base64编码的透明图片，要不会显示红xx
        }
    }
    else if (file.files) {//firefox6-
        if (file.files.item(0)) {
            url = file.files.item(0).getAsDataURL();
            pic.src = url;
        }
    }
}

function openBrowse() {
    var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
    if (ie) {
        document.getElementById("file").click();
    } else {
        var a = document.createEvent("MouseEvents");//FF的处理
        a.initEvent("click", true, true);
        document.getElementById("file").dispatchEvent(a);
    }
}

function delPhoto(picId) {
    $('#delPhoto').val(true);
    var pic = document.getElementById(picId);
    pic.src = "";
}
// 定义窗口的提交地址
var formUrl = 'emp/addEmp';
//图片显示地址
var photoUrl = '';
// 打开新建员工的窗口


function addEmp() {
    $("#employee-dlg").dialog("open").dialog('setTitle', '新建员工');
    $('#employee-form')[0].reset();
    $('#empID').val('');
    //$('#empName').val('');
    //$('#empPayNo').val('');
    //$('#empIDNo').val('');
    $('#photo').attr('src', '');
    $("#man").prop("checked", "true");
    var deptNode = $('#dept-tree').tree('getSelected');
    if (deptNode) {
        $('#deptId').combobox('setValue', '');
        $('#deptId').combobox('setValue', deptNode.id);
    }
    formUrl = 'emp/addEmp';
}
// 打开编辑员工的窗口
function editEmp(dataRow) {
    //兼容双击进行编辑，datagrid双击事件会传dataRow.
    var row
    if (dataRow) {
        row = dataRow;
    }
    else {
        row = $('#employee-dg').datagrid('getSelected');
    }
    if (row) {

        $('#employee-dlg').dialog('open').dialog('setTitle', '编辑员工');
        $('#employee-form')[0].reset();
        $('#empID').val(row.id);
        $('#empName').val(row.empName);
        $('#empPayNo').val(row.empPayNo);
        $('#empIDNo').val(row.empIDNo);
        //手动让部门获设置两次，如果设置一次显示不出来名字。原因在于采用了html原生的 form reset方法。
        $('#deptId').combobox('setValue', '');
        $('#deptId').combobox('setValue', row.deptId);


        $('#dutyId').combobox('setValue', row.empDutyId);
        var imgUrl = row.photoName ? "../upload/" + row.photoName : "";
        $('#photo').attr('src', imgUrl);
        row.empSex == "0" ? $("#man").prop("checked", "true") : $("#woman").prop("checked", "true");
        formUrl = 'emp/editEmp';
        $('#employee-form').form('validate');
    } else {
        $.messager.alert('提示', '需要选择一个员工，才能进行编辑操作。', 'info');
    }

}
// 删除员工
function deleteEmp() {
    var row = $('#employee-dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('确认', '是否删除该条数据？', function (r) {
            if (r) {
                $.post("emp/deleteEmp", {
                    empId: row.id
                }, function (result) {
                    if (result.success) {
                        // 成功后刷新表格
                        $('#employee-dg').datagrid('reload');
                    } else {
                        $.messager.alert('操作失败', result.message, 'error');
                    }
                }, "json");
            }
        });

    } else {
        $.messager.alert('提示', '需要选择一个员工，才能进行删除操作。', 'info');
    }

}
// 保存员工
function saveEmp() {

    $('#employee-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $('#employee-dlg').dialog('close'); // close the dialog
                $('#employee-dg').datagrid('reload');
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });
}
//打开搜索框
function openSearch() {
    $("#search-dlg").dialog("open");
}
function searchEmp() {
    var param = {
        'emp.deptId': $('#searchDept').combobox('getValue'),
        'emp.empName': $('#searchName').val().trim(),
        'emp.empPayNo': $('#searchPayNo').val().trim(),
        'emp.empIDNo': $('#searchIDNo').val().trim()
    };
    $('#employee-dg').datagrid('reload', param);
    $("#search-dlg").dialog("close");
}
function exportExcel() {
    $.messager.confirm('确认', '导出的数据是按照检索条件的，是否继续导出？', function (r) {
        if (r) {
            $('#search-form').submit();
        }
    });
}


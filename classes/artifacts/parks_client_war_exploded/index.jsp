<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="resources/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" href="resources/easyui/themes/icon.css"/>
<script src="resources/easyui/js/jquery-1.8.0.min.js"></script>
<script src="resources/easyui/js/jquery.easyui.min.js"></script>
<script src="resources/easyui/js/easyui-lang-zh_CN.js"></script>

<style type="text/css">
    #main-top {
        background-image: url(resources/img/main-top.png);
        background-repeat: repeat-x;
        height: 75px;
        width: auto;
        text-align: center
    }

    #main-top td {
        text-align: right;
    }

    #main-center {
        padding: 0px;
        background-color: #2286C2;
    }

    #main-bottom {
        background-image: url(resources/img/main-bottom.png);
        background-repeat: repeat-x;
        height: 35px;
        line-height: 35px;
        text-align: center;
        color: #000000;
        font-size: 12px;

    }

    #subsystem {
        color: #f5f5f5;
    }

    ul {
        list-style: none; /* 去掉ul前面的符号 */
        margin: 0px; /* 与外界元素的距离为0 */
        padding: 0px; /* 与内部元素的距离为0 */
        width: auto; /* 宽度根据元素内容调整 */
    }

    ul li {

        float: right; /* 向左漂移，将竖排变为横排 */
        margin-left: 5px;
    }

    ul li a, ul li a:visited {

        color: #ffffff;
        font-size: 116%;
        font-weight: bold;
        display: block; /* 此元素将显示为块级元素，此元素前后会带有换行符 */
        line-height: 1.35em; /* 行高 */
        padding: 4px 8px; /* 内部填充的距离 */
        text-decoration: none; /* 不显示超链接下划线 */
        white-space: nowrap; /* 对于文本内的空白处，不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。 */
    }

    ul li a:hover {

        border-color: #73b8d3;
        cursor: pointer;
        background: #b7d2ff;
        text-decoration: no-underline;;
    }

    form td {
        height: 35px;
    }

    form .tdRight {
        text-align: right;
        width: 100px;
    }
</style>
<script type="text/javascript">
    function logout() {
        $.ajax({

            url: 'logout',
            type: 'get',
            dataType: 'json',
            complete: function () {
                window.location.href = "login.jsp";
            }

        });
    }
    $.ajaxSetup({
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        complete: function (XMLHttpRequest, textStatus) {
            //通过XMLHttpRequest取得响应头，sessionstatus
            var sessionStatus = XMLHttpRequest.getResponseHeader("sessionStatus");
            if (sessionStatus == "timeout") {
                logout();
            }
        }
    });

    $(function () {
        $.ajax({

            url: 'managesys/pri/getApps',
            type: 'get',
            dataType: 'json',
            success: function (result) {
                // var l=result.length;
                if (result) {
                    for (var i = 0; result.length > i; i++) {
                        // var dom = i == result.length - 1 ? result[i].app : result[i].app + " |&nbsp;";
                        var dom = result[i].app;
                        $('#subsystem').append(dom);
                    }
                    $.parser.parse('#subsystem');
                    $("ul li:first-child a:first-child").click();
                }
                else {
                    $.messager.alert('提示', '你没有该系统的任何权限', 'info', function () {
                        logout();
                    });

                }

            }
        });
        $('#samePwd').validatebox({
            required: true,
            validType: {
                length: [4, 20],
                sameWord: ['#newPwd', '两次输入的密码不一致']
            }
        })
        loadPersonalInfo();
    })

    function loadPersonalInfo() {
        $.ajax({
            url: 'managesys/user/getCurrentUser',
            type: 'get',
            dataType: 'json',
            success: function (result) {
                // var l=result.length;
                if (result) {
                    $('#user-form').form('clear');
                    $('#userId').val(result.userId);
                    $('#userName').val(result.userName);
                    $('#nickname').val(result.nickname);
                    $('#deptName').val(result.department.deptName);
                    var roleNames = '';

                    for (var j = 0; j < result.roles.length; j++) {
                        roleNames = roleNames + result.roles[j].roleName + ' ';
                    }
                    $('#roleNames').val(roleNames);
                }
                else {
                    $.messager.alert('提示', '加载个人资料失败', 'info');

                }

            }
        });
    }
    function personalInfo() {
        $("#user-dlg").dialog("open").dialog('setTitle', '个人资料');
    }
    function changePwd() {
        $("#pwd-dlg").dialog("open").dialog('setTitle', '修改密码');
    }
    $.extend($.fn.validatebox.defaults.rules, {
        sameWord: {
            validator: function (value, param) {
                $.fn.validatebox.defaults.rules.sameWord.message = param[1];
                var word = $(param[0]).val();
                return value == word;
            },
            message: '错误'
        }
    });
    function savePwd() {
        $('#pwd-form').form('submit', {
            url: 'managesys/user/editUserPwd',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (data) {
                var result = jQuery.parseJSON(data);
                if (result.success) {
                    $('#pwd-dlg').dialog('close'); // close the dialog
                    $.messager.alert('成功', '密码修改成功', 'info');
                } else {
                    $.messager.alert('保存失败', result.message, 'error');
                }
            }
        });
    }
</script>
</head>

<body class="easyui-layout">
<div id="main-top" data-options="region:'north',border:false">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td rowspan="2" height="75" width="280"
                background="resources/img/title.png"></td>
            <td></td>
            <td style="vertical-align: bottom">
                <a class="easyui-linkbutton" iconcls="icon-user" plain="true">${sessionScope.user.userName}</a>&nbsp;
                <a href="javascript:void(0)" id="mb" class="easyui-menubutton"
                   data-options="menu:'#mm',iconCls:'icon-bell',plain:true">个人中心</a>&nbsp;

                <div id="mm" style="width:150px;">
                    <div data-options="iconCls:'icon-tip'" onclick="personalInfo()">个人资料</div>
                    <div data-options="iconCls:'icon-setting'" onclick="changePwd()">修改密码</div>

                </div>
                <a class="easyui-linkbutton" iconcls="icon-no" plain="true"
                   onclick="logout()">退出 </a></td>
            <td width="15"></td>
        </tr>
        <tr>
            <td></td>
            <td style="height: 38px; vertical-align: bottom; padding-bottom: 3px;">
                <ul id="subsystem">

                </ul>
            </td>
            <td></td>
        </tr>
    </table>

    <!-- 用户window窗口-->
    <div id="user-dlg" class="easyui-dialog"
         style="width: 420px; height: 280px; padding: 10px" modal="true"
         closed="true" buttons="#user-dlg-buttons">

        <form id="user-form" method="post">
            <table>

                <tr>
                    <td class="tdRight"><label>用户名：</label></td>
                    <td class="tdLeft"><input id="userName" type="text" readonly="readonly"
                                              style="width: 200px;"/></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>昵 称：</label></td>
                    <td class="tdLeft"><input id="nickname" readonly="readonly"
                                              style="width: 200px;"/></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>部 门：</label></td>
                    <td class="tdLeft"><input id="deptName" readonly="readonly"
                                              style="width: 200px;"/></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>角 色：</label></td>
                    <td class="tdLeft"><input id="roleNames" name="roleIds" readonly="readonly"
                                              style="width: 200px;"/></td>
                </tr>
            </table>
        </form>
    </div>
    <!-- 用户window窗口的功能按钮-->
    <div id="user-dlg-buttons">
        <a id="user-ok-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-ok" onclick="javascript:$('#user-dlg').dialog('close')">确定</a>
    </div>
    <div id="pwd-dlg" class="easyui-dialog" style="width: 420px; height: 250px; padding: 10px" modal="true"
         closed="true" buttons="#pwd-dlg-buttons">
        <form id="pwd-form" method="post">
            <table>
                <tr>
                    <td colspan="2" style="height: 5px;"><input id="userId" name="userId"
                                                                type="hidden"/></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>旧密码：</label></td>
                    <td class="tdLeft"><input id="oldPwd" type="password" name="oldPwd"
                                              class="easyui-validatebox"
                                              data-options="required:true,validType:'length[4,20]'"
                                              style="width: 200px;"/></td>
                </tr>

                <tr>
                    <td class="tdRight"><label>新密码：</label></td>
                    <td class="tdLeft"><input id="newPwd" type="password" name="newPwd"
                                              class="easyui-validatebox"
                                              data-options="required:true,validType:'length[4,20]'"
                                              style="width: 200px;"/></td>
                </tr>
                <tr>
                    <td class="tdRight"><label>确认密码：</label></td>
                    <td class="tdLeft"><input id="samePwd" type="password"
                                              style="width: 200px;"/></td>
                </tr>

            </table>
        </form>
    </div>
    <div id="pwd-dlg-buttons">
        <a id="user-save-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-ok" onclick="savePwd()">确定</a>
        <a id="user-cancel-btn" href="javascript:void(0)"
           class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#pwd-dlg').dialog('close')">取消</a>
    </div>
</div>
<div id="main-center" data-options="region:'center',border:true">
    <iframe name="centerPage" src="" width="100%"
            height="100%" frameborder="no" border="0" marginwidth="0"
            marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
</div>
<div id="main-bottom" data-options="region:'south',border:false">Copyright
    &copy; 1995 - 2015 NCFT. All Rights Reserved. 远东公司 版权所有
</div>
</body>
</html>

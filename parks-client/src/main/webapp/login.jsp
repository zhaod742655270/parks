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

    <script>
        function keyEnter() {
            if (event.keyCode == 13) {
                login();
            }
        }
        document.onkeydown = keyEnter;
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)
                return (r[2]);
            return "../index.jsp";
        }

        function removeTip() {
            $('#tiptext').html("");
        }
        formUrl = "login";
        function login() {

            $.messager.progress({ text: '', msg: '登录中...' });
            $('#ff').form('submit', {
                url: formUrl,
                onSubmit: function () {
                    var isValid = $(this).form('validate');
                    if (!isValid) {
                        $.messager.progress('close');	// 当form不合法的时候隐藏工具条
                    }
                    return isValid;	// 返回false将停止form提交

                },
                success: function (data) {

                    var result = $.parseJSON(data);//将json字符串转化成json对象。

                    $.messager.progress('close');

                    if (result.success) {
                        //跳转到首页
                        window.location.href = "index.jsp";

                    }
                    else {
                        $.messager.show({
                            title: '错误',
                            msg: '用户名与密码不匹配.',
                            timeout: 2000,
                            showType: 'slide'
                        });
                    }
                }
            });
        }

        function restPut() {
            $('#login-user').val('');
            $('#login-pwd').val('');
        }
    </script>

    <style>
        tr {
            line-height: 50px;
        }

        body {
            background: #d8e6ec url(resources/img/bg_body.gif) repeat-x;
            /*font: 80% Arial, Helvetica, Sans-Serif;*/
            /*line-height: 180%;*/
            margin: 0;
            padding: 0;
            text-align: center;
        }
    </style>
    <title>登录</title>
</head>
<body>
<div style="margin-bottom: 150px; font-family: 'Buxton Sketch'">
    <div
            style="font-size: 40px; height: 60px; line-height: 60px; margin-top: 30px;">
        <b>园区综合信息管理平台</b>
    </div>
    <div style="font-size: 16px;">park comprehensive information management platform

    </div>
</div>
<div style="margin: auto; height: 320px; width: 480px; background-image: url(resources/img/login-window.png); background-repeat: no-repeat; padding-top: 70px;">
    <div style="text-align: left">
        <form id="ff" method="post">
            <table style="font-size: 20px;">
                <tr>
                    <td style="width: 150px; text-align: right">用户名：</td>
                    <td><input id="login-user" style="width: 200px;" type="text"
                               name="user.userName" class="easyui-validatebox"
                               data-options="required:true,validType:'length[2,20]'"/></td>
                </tr>
                <tr>
                    <td style="width: 150px; text-align: right">密&nbsp;&nbsp;码：</td>
                    <td><input id="login-pwd" type="password"
                               style="width: 200px;" onkeypress="removeTip()" name="user.password"
                               class="easyui-validatebox"
                               data-options="required:true,validType:'length[4,20]'"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="1" style="text-align: center"><input
                            style="width: 80px; height: 30px; font-size: large" type="button"
                            onclick="restPut()" value="重 置"/>&nbsp; <input
                            style="width: 80px; height: 30px; font-size: large" type="button"
                            value="登 录" onclick="login()"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>
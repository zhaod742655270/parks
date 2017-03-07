<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>会话过期页面</title>
    <link rel="stylesheet"
          href="resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="resources/easyui/js/easyui-extentions.js"></script>
    <script src="resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="resources/script/common.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
            background-color: #f3f3f3;
        }


        #subsystem {
            color: #f5f5f5;
        }

    </style>

    <script type="text/javascript">
        function jspLogout() {
            $.ajax({

                url: 'logout',
                type: 'get',
                dataType: 'json',
                complete: function () {
                    top.location.href = "login.jsp";
                }

            });
        }

        function goback()
        {
            window.history.go(-2);
        }
    </script>
</head>


<body class="easyui-layout">
<div id="main-center" data-options="region:'center',border:true">
    <iframe name="centerPage" src="" width="100%"
            height="20" frameborder="no" border="0" marginwidth="0"
            marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
            <ul>
                <li><h1>页面过期</h1></li>
                </br><li><span style="font-size:large">抱歉您无法访问该页面</span></li></br>
                &nbsp;&nbsp;<li><span style="font-size:large">请您</span><a href="#" onclick ="jspLogout()"><span style="font-size: large">重新登录</span></a></li>
            </ul>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>无权限页面</title>
    <link rel="stylesheet"
          href="resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="resources/easyui/themes/icon.css"/>
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

        function goBack()
        {
            window.history.go(-2);
        }
    </script>
</head>


<body class="easyui-layout">
<div id="main-top" data-options="region:'north',border:false">
    <table width="280" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td rowspan="2" height="75" width="280"
                background="resources/img/title.png"></td>
        </tr>
        </table>
</div>
<div id="main-center" data-options="region:'center',border:true">
    <iframe name="centerPage" src="" width="100%"
            height="20" frameborder="no" border="0" marginwidth="0"
            marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
            <ul>
                <li><h1>权限错误</h1></li>
                </br><li><span style="font-size:large">抱歉您无法访问该页面</span></li>
            </ul>
            </br>
            &nbsp;&nbsp;&nbsp;<a href="#" onclick ="goBack()"><span style="font-size: larger">返回上一页</span></a>||&nbsp;<a href="#" onclick ="logout()"><span style="font-size: larger">重新登录</span></a>
</div>
<div id="main-bottom" data-options="region:'south',border:false">Copyright
    &copy; 1995 - 2015 NCFT. All Rights Reserved. 远东公司 版权所有
</div>
</body>
</html>
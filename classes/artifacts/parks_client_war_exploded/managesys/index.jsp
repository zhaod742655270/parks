<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>支撑平台</title>
    <link rel="stylesheet"
          href="../resources/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
    <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
    <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
    <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                async: false,
                url: 'pri/getMenus?appId=' + $('#appId').val(),
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    var app = result.app;
                    $('#menus').append(result.menus);
                    $('#menus').accordion({
                        fit: true
                    });
                    $.parser.parse();
                }
            });
        })
    </script>
    <style type="text/css">
        .accordion-child {
            text-align: center;
            padding-top: 2px;
        }

        .easyui-accordion a:hover {
            font-weight: bolder;
        }
    </style>
</head>
<body class="easyui-layout">

<div data-options="region:'west',border:true, title:'支撑平台',split:true"
     style="width: 155px;">
    <div id="menus">

    </div>

</div>
<div data-options="region:'center',border:false">
    <input id="appId" type="hidden" value="${param.appId}"/>
    <iframe name="commoncontent" id="commoncontent" src="" width="100%"
            height="99.5%" frameborder="no" border="0" marginwidth="0"
            marginheight="0" scrolling="no" allowtransparency="yes"></iframe>

</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: feng
  Date: 2015/1/29
  Time: 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>会议室列表</title>
  <link rel="stylesheet"
        href="../resources/easyui/themes/default/easyui.css"/>
  <link rel="stylesheet" href="../resources/easyui/themes/icon.css"/>
  <script src="../resources/easyui/js/jquery-1.8.0.min.js"></script>
  <script src="../resources/easyui/js/jquery.easyui.min.js"></script>
  <script src="../resources/easyui/js/easyui-extentions.js"></script>
  <script src="../resources/easyui/js/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="../resources/script/common.js"></script>
  <script type="text/javascript" src="../resources/script/meetsys/meetingRoom.js"></script>
  <style type="text/css">
    #conf-form td {
      height: 35px;
    }

    #conf-form .tdRight {
      text-align: right;
      width: 80px;

    }
  </style>
<html>
<head>
    <title></title>
</head>
<body>


<table id="conf-dg">
  <thead>
  <tr>
    <th data-options="field:'id',hidden:true">descID</th>
    <th data-options="field:'name',sortable:true">会议室名称</th>
    <th data-options="field:'regionName'">会议室位置</th>
    <th data-options="field:'capacity'">容量</th>
    <th data-options="field:'descInfo'">描述</th>
  </tr>
  </thead>
  <tbody>
  </tbody>
</table>

<div id="conf-toolbar">
  <!-- 之所以把搜索框放在前面目的是为了兼容ie6，如果放在后面ie6中会折行显示 -->
  <div style="float: right; padding-top: 2px; padding-right: 5px;">
    <input id="conf-search" class="easyui-searchbox"
           data-options="prompt:'请输入关键字',
                         searcher: function (value, name) {
                          $('#conf-dg').datagrid('load', { key: value });
                }"/>
  </div>
  <div id="conf-btns">
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconcls="icon-reload" plain="true"
       onclick="$('#conf-dg').datagrid('reload');">刷新</a>
  </div>

</div>
<!--主窗口-->
<div id="conf-dlg" class="easyui-dialog"
     style="width: 420px; height: 285px; padding: 10px" modal="true"
     closed="true" buttons="#buttons">

  <form id="conf-form" method="post">
    <table>
      <tr>
        <td colspan="2" style="height: 5px;"><input id="id"
                                                    type="hidden" name="meetRoom.id"/></td>
      </tr>
      <tr>
       <td class="tdRight"><label>会议室名称：</label></td>
       <td class="tdLeft"><input id="name" type="text" class="easyui-validatebox"
                                  data-options="required:true,validType:'length[3,20]'"
                                  name="meetRoom.name" style="width: 200px;"/></td>
      </tr>

     <tr>
       <td class="tdRight"><label>会议室位置：</label></td>
       <td class="tdLeft"><input id="regionId" type="text" class="easyui-combobox"
                                 data-options="valueField:'id',textField:'regionName',url:'../supportsys/region/regionList'"
                                 name="meetRoom.regionId" style="width: 200px;"/></td>
     </tr>

      <tr>
            <td class="tdRight"><label>容量：</label></td>
            <td class="tdLeft"><input id="capacity" type="text" class="easyui-validatebox"
                                      data-options="required:true"
                                      name="meetRoom.capacity" style="width: 200px;"/></td>
      </tr>


      <tr>
        <td class="tdRight"><label>会议室描述：</label></td>
        <td class="tdLeft"><textarea name="meetRoom.descInfo" id="descInfo" style="width: 200px; height:80px;">
          </textarea></td>
      </tr>

    </table>
  </form>
    <div id="buttons">
      <a id="conf-save-btn" href="#" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveMeetRoom()">保存</a>
      <a id="conf-close-btn" href="#" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#conf-dlg').dialog('close')">取消</a>
    </div>

</div>
<input id="menuId" type="hidden" value="${param.menuId}"/>
</body>
</html>

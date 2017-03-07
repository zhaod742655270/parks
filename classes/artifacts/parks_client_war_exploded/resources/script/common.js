/**
 * Created by len on 14-7-21.
 */
//得到easy css中的图标,加载到combotree中，,参数为string类型
$.ajaxSetup({
    cache: false, //关闭AJAX相应的缓存,
    contentType: "application/x-www-form-urlencoded;charset=utf-8",
    complete: function (XMLHttpRequest, textStatus) {
        //通过XMLHttpRequest取得响应头，sessionstatus
        var sessionStatus = XMLHttpRequest.getResponseHeader("sessionStatus");
        if (sessionStatus == "timeout") {
            $.messager.alert('提示', '会话已过期，请重新登录！', 'info');
        }
    }
});
function getstyle(comboId) {
    for (var i = 0; i < document.styleSheets.length; i++) {
        var rules;
        if (document.styleSheets[i].cssRules) {
            rules = document.styleSheets[i].cssRules;
        }
        else {
            rules = document.styleSheets[i].rules;
        }
        for (var j = 0; j < rules.length; j++) {
            if (rules[j].selectorText.substr(0, 5) == ".icon")
                $('#' + comboId).combotree('tree').tree('append', {
                    data: [
                        {
                            id: rules[j].selectorText.substr(1),
                            text: rules[j].selectorText.substr(1),
                            iconCls: rules[j].selectorText.substr(1)
                        }
                    ]
                });
        }
    }
}

function loadBtns(id, menuId, url) {
    //var u = url == undefined ? '../managesys/pri/getBtns?menuId=' + menuId : url + menuId;
    var u = '../managesys/pri/getBtns?menuId=' + menuId;
    $.ajax({
        async: false,
        url: u,
        type: 'post',
        dataType: 'json',
        success: function (result) {
            var dom = "";
            if (result) {
                for (var i = 0; result.length > i; i++) {
                    var d = result[i].btn;
                    dom = dom + d;
                }
                $('#' + id).prepend(dom);
                $.parser.parse();
            }
        }
    });
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function formatTime(val) {
    var re = /-?\d+/;
    var m = re.exec(val);
    var d = new Date(parseInt(m[0]));
    // 按【2012-02-13 09:09:09】的格式返回日期
    return d.Format("yyyy-MM-dd hh:mm:ss");
}


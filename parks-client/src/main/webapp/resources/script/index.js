//dom加载完毕的后的方法，关闭缓存，加载主题

$(function () {
    $.ajaxSetup({
        cache: false //关闭AJAX相应的缓存
    });

    $.ajax({

        url: 'pri/getMenus',
        type: 'get',
        dataType: 'json',
        success: function (result) {
           var app=result.app;
        }
    });
    if ($.cookie('easyuiThemeName')) {
        changeThemeFun($.cookie('easyuiThemeName'));
    }

})
function menufocus(id) {

    alert(id);
    $(id).addClass("menuavtive");

}
function opentab(url, plugin) {
    if ($('#tt').tabs('exists', plugin)) {
        $('#tt').tabs('select', plugin);
    } else {
        $('#tt').tabs('add', {
            title: plugin,
            href: url,
            closable: true
        });
    }
}
function logout() {
    $.post("/Account/Logout", {},
        function (data) {
            if (data.Success) {
                window.location.href = "/login.jsp";

            } else {
                $.messager.alert('操作失败', data.Message, 'error');
            }
        }, 'json');

}
function changeThemeFun(themeName) {/* 更换主题 */
    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
    $easyuiTheme.attr('href', href);

    var $iframe = $('iframe');
    if ($iframe.length > 0) {
        for (var i = 0; i < $iframe.length; i++) {
            var ifr = $iframe[i];
            $(ifr).contents().find('#easyuiTheme').attr('href', href);
        }
    }

    $.cookie('easyuiThemeName', themeName, {
        expires: 7
    });
};

jQuery.cookie = function (key, value, options) {

    // key and value given, set cookie...
    if (arguments.length > 1 && (value === null || typeof value !== "object")) {
        options = jQuery.extend({}, options);

        if (value === null) {
            options.expires = -1;
        }

        if (typeof options.expires === 'number') {
            var days = options.expires, t = options.expires = new Date();
            t.setDate(t.getDate() + days);
        }

        return (document.cookie = [
            encodeURIComponent(key), '=',
            options.raw ? String(value) : encodeURIComponent(String(value)),
            options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
            options.path ? '; path=' + options.path : '',
            options.domain ? '; domain=' + options.domain : '',
            options.secure ? '; secure' : ''
        ].join(''));
    }

    // key and possibly options given, get cookie...
    options = value || {};
    var result, decode = options.raw ? function (s) {
        return s;
    } : decodeURIComponent;
    return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};
   
//例如：validType: 'remoteValidate["/MeetingRoom/ValidateProperyName", "123"]'
//服务端接受参数为：validateValue，validateId
$.extend($.fn.validatebox.defaults.rules, {
    remoteValidate: {
        validator: function (value, param) {
            //为了兼容多重验证，如果该验证在本地不通过，不再向服务器验证，直接返回；

            var url = param[0];
            var data = {};
            data.validateValue = value;
            data.validateId = param[1];

            var result = {};
            $.ajax({
                url: url,
                data: data,
                type: 'post',
                dataType: 'json',
                async: false,
                cache: false,
                success: function (r) {
                    result = r;

                }
            });
            if (result.success) {
                return true;
            }
            else {
                $.fn.validatebox.defaults.rules.remoteValidate.message = result.message;
                return false;
            }
        },
        message: '验证失败'
    }
});


//easyui 验证扩展
//参数为 begintime的id，requied值为 true或者false（ 用于是否验证非空）
$.extend($.fn.validatebox.defaults.rules, {
    timevalid: {
        validator: function (value, param) {
            var begintime = $(param[0]).datetimebox('getText');
            if (param[1] == true && (value == "" || begintime == "")) {
                return false;
            }
            var begintime = StringToDate(begintime);
            var endtime = StringToDate(value);
            if (begintime && endtime) {
                return begintime < endtime;
            }
            else {
                return false;
            }

        },
        message: '开始时间要小于结束时间'
    }
});
//timvalid验证需要该方法
function StringToDate(strDate) {
    if (strDate == "" || strDate == null) return null;
    var d = strDate.split(" ");
    var date = d[0].split("-");
    var time = d[1].split(":");
    var datetime = new Date(date[0], date[1], date[2], time[0], time[1], time[2]);
    return datetime;
}

//author: ren_xt, 下面是自定义的校验器

// 开始时间和结束时间同时存在时，结束时间必须晚于开始时间
$.extend($.fn.validatebox.defaults.rules, {
    lateThanBegin: {
        validator: function(value,param){
            var endTime = value;
            var beginTime = $(param[0]).combo("getValue");

            if(beginTime && endTime){
                return endTime > beginTime;
            }
        },
        message: '结束时间必须晚于开始时间'
    }
});

// 检验日期时间格式
$.extend($.fn.validatebox.defaults.rules, {
    dtFormat: {
        validator: function(value){
            var regex = /\d{4}-[01]\d-[0-3]\d [0-2]\d:[0-5]\d:[0-5]\d/;
            return regex.test(value);
        },
        message: '日期时间格式不正确'
    }
});
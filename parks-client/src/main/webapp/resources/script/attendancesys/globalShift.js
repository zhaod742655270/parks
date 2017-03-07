$(function () {
    loadShift();

})
function loadShift()
{
    $.post("globalShift/loadShift", {
    }, function (result) {
        if (result.success) {
            //成功加载到form表单中。
            var data = result.data[0];
            $('#defId').val(data.id);
            $('#shiftID').combobox('setValue', data.shiftID);
            if (data.restDays != null) {
                $('#restDays').combobox('setValues', data.restDays);
            }
            else
            {
                $('#restDays').combobox('clear');
            }

            $('#invalidAssert').combobox('setValue', data.invalidAssert);
        } else {
            $.messager.alert('加载失败', result.message, 'error');
        }
    }, "json");
}
// 定义窗口的提交地址
var formUrl = 'globalShift/saveShift';
// 打开新建假日的窗口

// 保存假日
function saveShift() {

    $('#globalShift-form').form('submit', {
        url: formUrl,
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var result = jQuery.parseJSON(data);
            if (result.success) {
                $.messager.alert('操作结果','保存成功', 'info');
                loadShift();
            } else {
                $.messager.alert('保存失败', result.message, 'error');
            }
        }
    });

}
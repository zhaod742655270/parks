/**
 *
 */
$(function () {

    $('#attendanceRecord-dg').datagrid(
        {
            title: '考勤记录列表',
            toolbar: '#attendanceRecords-toolbar',
            sortName: 'tai.deptFK',
            sortOrder: 'asc',
            striped: true,
            rownumbers: true,
            fitColums: false,
            fit: true,
            singleSelect: true,
            method: 'post',
            url: 'record/attendanceRecordList',
            pagination: true,

            loadFilter: function (data) {
                if (data.rows == null) {
                    return data;
                }

                for (var i = 0; i < data.rows.length; i++) {
                    if (data.rows[i].details && data.rows[i].details.length > 0) {
                        for (var j = 0; j < data.rows[i].details.length; j++) {
                            if (data.rows[i].details[j].type == "上午时段") {
                                var s1SignInTime = data.rows[i].details[j].signInTime;
                                var s1SignOutTime = data.rows[i].details[j].signOutTime;
                                var s1ArriveLateNum = data.rows[i].details[j].arriveLateNum
                                var s1LeaveEarlyNum = data.rows[i].details[j].leaveEarlyNum
                                var s1AbsentNum = data.rows[i].details[j].absentNum
                                var s1LeaveNum = data.rows[i].details[j].leaveNum

                                data.rows[i].s1SignInTime = s1SignInTime;
                                data.rows[i].s1SignOutTime = s1SignOutTime;
                                data.rows[i].s1ArriveLateNum = s1ArriveLateNum;
                                data.rows[i].s1LeaveEarlyNum = s1LeaveEarlyNum;
                                data.rows[i].s1AbsentNum = s1AbsentNum;
                                data.rows[i].s1LeaveNum = s1LeaveNum;
                            }
                            else if (data.rows[i].details[j].type == "下午时段")
                            {
                                var s2SignInTime = data.rows[i].details[j].signInTime;
                                var s2SignOutTime = data.rows[i].details[j].signOutTime;
                                var s2ArriveLateNum = data.rows[i].details[j].arriveLateNum
                                var s2LeaveEarlyNum = data.rows[i].details[j].leaveEarlyNum
                                var s2AbsentNum = data.rows[i].details[j].absentNum
                                var s2LeaveNum = data.rows[i].details[j].leaveNum

                                data.rows[i].s2SignInTime = s2SignInTime;
                                data.rows[i].s2SignOutTime = s2SignOutTime;
                                data.rows[i].s2ArriveLateNum = s2ArriveLateNum;
                                data.rows[i].s2LeaveEarlyNum = s2LeaveEarlyNum;
                                data.rows[i].s2AbsentNum = s2AbsentNum;
                                data.rows[i].s2LeaveNum = s2LeaveNum;
                            }
                        }
                    }
                }
                return data;
            }
        }
    );
    setDefaultDate();
       setOptionEmpty();

})
function setDefaultDate() {
    $('#date').datebox("setValue", new Date().Format("yyyy-MM-dd"));
    // $('#endDate').datebox("setValue", new Date().Format("yyyy-MM-dd"));
}
function setOptionEmpty() {
    $('#timeSpan1Option').combobox('clear');
    $('#timeSpan2Option').combobox('clear');
}
function recordQuery() {
    var timeSpan1Option = {};
    var t1 = $('#timeSpan1Option').combobox('getValues')
    for (var i = 0; i < t1.length; i++) {
        timeSpan1Option[i] = t1[i];
    }
    var timeSpan2Option = {};
    var t2 = $('#timeSpan2Option').combobox('getValues')
    for (var i = 0; i < t2.length; i++) {
        timeSpan2Option[i] = t2[i];
    }
    $('#attendanceRecord-dg').datagrid('load',
        { userName: $('#userName').val(),
            date: $('#date').datebox('getValue'),
            timeSpan1Option: timeSpan1Option,
            timeSpan2Option: timeSpan2Option,
            deptId: $('#deptId').combobox('getValue')})
}
function exportExcel() {
    $('#attendanceRecord-form').submit();
}

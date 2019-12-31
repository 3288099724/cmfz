<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
    <%--引入bootStrap的css样式--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.min.css">
    <%--引入jqgrid与bootstrap整合的css样式--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入jquery的js文件--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <%--引入bootStrap的js文件--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.min.js"></script>
    <%--引入jqgrid的js文件--%>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>


    <script>
        $(function () {
            $('#user-table').jqGrid({
                url: '${pageContext.request.contextPath}/user/queryAll',
                datatype: 'json',
                editurl: '${pageContext.request.contextPath}/user/edit',//设置编辑表单提交路径
                colNames: ['用户-ID', '用户名称', '用户密码', '盐', '法号', '省份', '城市', '签名',
                    '性别', '头像', '状态', '电话', '注册日期', '师傅'],
                colModel: [
                    {name: "user_id", align: 'center', editable: true, hidden: true},
                    {name: 'user_name', align: 'center'},
                    {name: 'user_password'},
                    {name: 'user_salt', align: 'center'},

                    {name: 'user_dharma', align: 'center'},
                    {name: 'user_province'},
                    {name: 'user_city', align: 'center'},
                    {name: 'user_sign', align: 'center'},
                    {name: 'user_sex'},
                    {
                        name: 'user_photo', align: 'center',
                        formatter: function (value, option, rows) {
                            return "<img  style='width:30%;height:10s%;' " +
                                "src='${pageContext.request.contextPath}/user-img/" + rows.user_photo + "'/>";
                        }
                    },
                    {
                        name: 'user_status', align: 'center', editable: true, edittype: "select",
                        editoptions: {value: "unlock:unlock;lock:lock"}
                    },
                    {name: 'user_phone', align: 'center'},
                    {name: 'user_cdate', align: 'center'},
                    {name: 'user_father', align: 'center'}
                ],
                styleUI: 'Bootstrap',
                caption: "用户的详细信息",
                autowidth: true,
                width: "100%",
                height: 300,

                pager: '#user-pager',
                rowNum: 4,
                rowList: [2, 4, 6, 10],
                viewrecords: true,
                //recreateForm: true     //确保每添加或编辑表单是重新创建。
            }).navGrid('#user-pager', {edit: true, add: false, del: false, search: false, refresh: true}
            );
        })

        function userOut() {
            location.href = "${pageContext.request.contextPath}/user/userOut"
        }

    </script>
</head>
<body>
<div>
    <!-- 导航 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">展示用户</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" onclick="userOut()"
                                   data-toggle="tab">导出用户</a></li>

    </ul>
</div>
<table id="user-table"></table>
<div id="user-pager" style="height: 50px"></div>
</body>
</html>
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

</head>

<script>
    $(function () {
        $('#tt').jqGrid({
            url: '${pageContext.request.contextPath}/banner/queryAll',
            datatype: 'json',
            editurl: '${pageContext.request.contextPath}/banner/edit',//设置编辑表单提交路径
            //cellEdit:true,
            //collEdit:true,
            colNames: ['编号', '名称', '图片', '描述', '状态', '创建时间'],
            colModel: [
                {name: "ban_id", align: 'center', key: true},
                {name: 'ban_name', align: 'center', editable: true},
                {
                    name: 'ban_cover',
                    index: 'cover',
                    edittype: "file",
                    editable: true,
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, option, rows) {
                        return "<img  style='width:30%;height:10s%;' " +
                            "src='${pageContext.request.contextPath}/banner-img/" + rows.ban_cover + "'/>";
                    }
                },
                {name: 'ban_description', align: 'center', editable: true},
                {
                    name: 'ban_status', align: 'center', editable: true, edittype: "select",
                    editoptions: {value: "正常:正常;冻结:冻结"}
                },
                {name: 'ban_cdate', align: 'center', formatter: "date"}
            ],
            styleUI: 'Bootstrap',
            caption: "轮播图的详细信息",
            height: 300,
            autowidth: true,

            pager: '#pager',
            rowNum: 4,
            rowList: [2, 3, 4, 5],
            viewrecords: true,
            //recreateForm: true     //确保每添加或编辑表单是重新创建。
        }).navGrid('#pager', {edit: true, add: true, del: true, search: false, refresh: false},

            {
                //控制修改的相关操作
                closeAfterEdit: close,
                beforeShowForm: function (frm) {
                    frm.find("#ban_cover").attr("disabled", true);
                }
            },

            {
                //jqModal:true,
                closeAfterAdd: close,
                /*  recreateForm:true,
                  onInitializeForm : function(formid){
                      $(formid).attr('method','POST');
                      $(formid).attr('action','');
                      $(formid).attr('enctype','multipart/form-data');
                  },*/
                afterSubmit: function (response) {
                    console.log("pppppppppp");
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    console.log("=========" + status);
                    console.log("+++++++++++" + id);
                    //alert("确认添加");
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/upload",
                            fileElementId: "ban_cover",
                            data: {id: id},
                            type: "post",
                            success: function () {
                                $("#tt").trigger("reloadGrid")
                            }
                        });
                    }
                    return "123";  //随便返回一个值，使得添加完可关闭表单框
                }
            }
        );
    })
</script>
<body>
<table id="tt"></table>
<div id="pager" style="height: 50px"></div>
</body>
</html>

<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
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
            $("#album-table").jqGrid(
                {
                    url: "${pageContext.request.contextPath}/album/queryAll",
                    datatype: "json",
                    editurl: '${pageContext.request.contextPath}/album/edit',//设置编辑表单提交路径
                    colNames: ['编号', '专辑', '封面', '作者', '播音', '得分', '集数', '简介', '创建日期'],
                    colModel: [
                        {name: 'album_id', align: 'center', editable: true, hidden: true, key: true},
                        {name: 'album_title', align: 'center', editable: true},
                        {
                            name: 'album_cover',
                            align: 'center',
                            editable: true,
                            edittype: "file",
                            editoptions: {enctype: "multipart/form-data"},
                            formatter: function (value, option, rows) {
                                console.log("rows.album_cover:" + rows.album_cover);
                                return "<img  style='width:30%;height:10s%;' " +
                                    "src='${pageContext.request.contextPath}/face/" + rows.album_cover + "'/>";
                            }
                        },
                        {name: 'album_author', align: 'center', editable: true},
                        {name: 'album_beam', align: 'center', editable: true},
                        {name: 'album_score', align: 'center'},
                        {name: 'album_count', align: 'center'},
                        {name: 'album_intro', align: 'center', editable: true},
                        {name: 'album_cdate', formatter: "date"}

                    ],
                    styleUI: 'Bootstrap',
                    caption: "展示专辑",
                    autowidth: true,
                    height: 300,
                    width: '100%',

                    pager: '#album-pager',
                    rowNum: 4,
                    rowList: [2, 4, 6, 10],
                    viewrecords: true,

                    subGrid: true,
                    subGridRowExpanded: function (subgrid_id, row_id) {
                        //subgrid_id : 父容器的id
                        //row_id:，本来指行号，如果父表设置有key，则为父表的那一行的id值


                        var subgrid_table_id = subgrid_id + "_t";
                        var pager_id = "p_" + subgrid_table_id;
                        $("#" + subgrid_id).html(
                            "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                            "<div id='" + pager_id + "' class='scroll'></div>");

                        $("#" + subgrid_table_id).jqGrid(
                            {
                                url: "${pageContext.request.contextPath}/chapter/queryAll?al_id=" + row_id,
                                datatype: "json",
                                editurl: '${pageContext.request.contextPath}/chapter/edit?al_id=' + row_id,
                                colNames: ['编号', '章节', '大小', '时长', '路径', '创建日期', '播放'],
                                colModel: [
                                    {name: "ch_id", align: 'center', editable: true, hidden: true, key: true},
                                    {name: "ch_title", align: 'center', editable: true},
                                    {name: "ch_size", align: 'center'},
                                    {name: "ch_duration", align: 'center'},
                                    {
                                        name: "ch_cover",
                                        align: 'center',
                                        editable: true,
                                        edittype: "file",
                                        editoptions: {enctype: "multipart/form-data"}
                                    },
                                    {name: "ch_cdate", formatter: "date"},
                                    {
                                        name: "xxxx",
                                        width: 300,
                                        formatter: function (value, option, rows) {
                                            return "<audio controls loop>\n" +
                                                "  <source src='${pageContext.request.contextPath}/mp3/" + rows.ch_cover + "' type=\"audio/ogg\">\n" +
                                                "</audio>"
                                        }
                                    }
                                ],
                                styleUI: 'Bootstrap',
                                autowidth: true,
                                height: '100%',

                                pager: pager_id,
                                rowNum: 4,
                                rowList: [2, 4, 6, 10],
                                viewrecords: true,
                            }).jqGrid('navGrid', "#" + pager_id,

                            {edit: false, add: true, del: true, search: false},
                            {
                                //控制章节修改
                                closeAfterEdit: close,
                                beforeShowForm: function (frm) {
                                    frm.find("#ch_cover").attr("disabled", true);
                                }
                            },
                            {
                                //控制章节的添加
                                closeAfterAdd: close,
                                afterSubmit: function (response) {
                                    var status = response.responseJSON.status;
                                    var id = response.responseJSON.message;
                                    console.log(status);
                                    console.log(id);
                                    if (status) {
                                        $.ajaxFileUpload({
                                            type: "post",
                                            url: "${pageContext.request.contextPath}/chapter/upload",
                                            data: {id: id},
                                            fileElementId: "ch_cover",
                                            success: function () {
                                                $("#subgrid_table_id").trigger("reloadGrid");
                                            }
                                        })
                                    }
                                    return "1231";
                                }
                            });
                    }
                }).navGrid("#album-pager", {edit: true, add: true, del: false, search: false},
                {
                    //控制专辑修改的相关操作
                    closeAfterEdit: close,
                    beforeShowForm: function (frm) {
                        frm.find("#album_cover").attr("disabled", true);
                    }
                }, {
                    //控制专辑添加的相关操作
                    closeAfterAdd: close,
                    afterSubmit: function (response) {
                        var status = response.responseJSON.status;
                        var id = response.responseJSON.message;
                        console.log(status);
                        console.log(id);
                        if (status) {
                            $.ajaxFileUpload({
                                type: "post",
                                url: "${pageContext.request.contextPath}/album/upload",
                                data: {id: id},
                                fileElementId: "album_cover",
                                success: function () {
                                    $("#album-table").trigger("reloadGrid");
                                }
                            })
                        }
                        return "123";
                    }

                });
        })
    </script>
</head>
<body>
<table id="album-table"></table>
<div id="album-pager" style="height: 50px"></div>
</body>
</html>
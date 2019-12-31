<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<html>
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

    <%--引入kindeditor文件,富文本编辑器--%>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>

    <script>
        $(function () {
            $("#article-table").jqGrid({
                url: '${pageContext.request.contextPath}/article/queryAll',
                prmName: {id: "ar_id"},      //为使id值等于设置的key的属性的值
                datatype: "json",
                editurl: "${pageContext.request.contextPath}/article/edit",
                colNames: ['编号', '标题', '作者', '内容', '时间', '操作'],
                colModel: [
                    {name: "ar_id", align: 'center', key: true},
                    {name: 'ar_title', align: 'center'},
                    {name: 'ar_guru', align: 'center'},
                    {name: 'ar_content'},
                    {name: 'ar_cdate', align: 'center', formatter: "date"},
                    {
                        name: 'aaa', formatter: function (value, options, rows) {
                            console.log("============" + rows.ar_id);
                            return "<a class='btn btn-success' onclick=\"openModal('edit','" + rows.ar_id + "')\" >修改文章</a>"
                        }
                    }
                ],
                styleUI: "Bootstrap",
                caption: "展示文章数据",
                height: 300,
                autowidth: true,


                pager: '#article-pager',
                rowNum: 5,
                rowList: [2, 5, 10],
                viewrecords: true,
            }).navGrid("#article-pager", {edit: false, add: false, del: true, search: false})
        })


        function openModal(oper, id) {  //添加传入oper,  修改传入oper和要修改的那行数据的id
            //对添加来说，只需要显示模态框即可，对修改来说，需要查询出该行的数据，进行展示

            KindEditor.html("#editor_id", "");    //设置textarea为空，防止添加的时候可能模态框有默认值
            var article = $("#article-table").jqGrid("getRowData", id);    //jqGrid的API,用来获取某行数据

            //文章的数据赋值给form表单
            $("#article-title").val(article.ar_title);
            $("#article-author").val(article.ar_guru);
            $("#article-id").val(article.ar_id);
            KindEditor.html("#editor_id", article.ar_content);
            $("#article-oper").val(oper);
            //
            $("#article-modal").modal("show");
        }


        function dealSave() {
            //var ar_id = $("#article-id").val();
            // console.log("ar_id:"+ar_id);
            $.ajax({
                url: "${pageContext.request.contextPath}/article/edit",
                type: "post",
                data: {
                    ar_id: $("#article-id").val(),
                    oper: $("#article-oper").val(),
                    ar_title: $("#article-title").val(),
                    ar_guru: $("#article-author").val(),
                    //保证模态框中的数据一致性
                    ar_content: $(document.getElementsByTagName('iframe')[0].contentWindow.document.body).html()
                },
                dataType: "json",
                success: function () {
                    $("#article-modal").modal("hide");
                    $("#article-table").trigger("reloadGrid");
                }
            })
        }


        KindEditor.create("#editor_id", {
            allowFileManager: true,
            uploadJson: "${pageContext.request.contextPath}/article/upload",
            fileManagerJson: '${pageContext.request.contextPath}/article/getAllImg',
            allowFileManager: true,
            resizeType: 1,   //只能改变高度
            //将文本域中的值同步到form当中
            afterBlur: function () {
                this.sync();
            }
        });
    </script>
</head>

<body>
<div>
    <!-- 导航 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">展示文章</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" onclick="openModal('add')"
                                   data-toggle="tab">添加文章</a></li>
    </ul>
</div>

<!-- jqGrid 表格和分页-->
<table id="article-table"></table>
<div id="article-pager" style="height: 50px"></div>

<%--模态框   添加和修改共用--%>
<div class="modal fade" id="article-modal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 702px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">请输入文章信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="article-form">

                    <input id="article-id" type="hidden" name="ar_id">
                    <input id="article-oper" type="hidden" name="oper">

                    <div class="form-group">
                        <label for="article-title" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="ar_title" class="form-control" id="article-title"
                                   placeholder="title">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-2 control-label">作者</label>
                        <div class="col-sm-10">
                            <input type="text" name="ar_guru" class="form-control" id="article-author"
                                   placeholder="author">
                        </div>
                    </div>
                    <div class="form-group">
                         <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                         </textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="dealSave()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


</body>
</html>
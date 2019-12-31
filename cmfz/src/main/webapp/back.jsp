<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/login/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/login/assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/login/assets/css/form-elements.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/login/assets/css/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/login/assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="${pageContext.request.contextPath}/login/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="${pageContext.request.contextPath}/login/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="${pageContext.request.contextPath}/login/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="${pageContext.request.contextPath}/login/assets/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/login/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/login/assets/js/jquery.backstretch.min.js"></script>
    <script src="${pageContext.request.contextPath}/login/assets/js/scripts.js"></script>
    <script src="${pageContext.request.contextPath}/login/assets/js/jquery.validate.min.js"></script>
    <script>


    </script>
</head>

<body>

<nav class="navbar navbar-default" role="navigation" style="width: 100%;">
    <div class="navbar-header  " style="width: 40%">
        <a class="navbar-brand" href="#">后台管理系统</a>
    </div>
    <div>
        <div class="navbar-text navbar-right">
            欢迎，${sessionScope.loginAdmin.ad_name}
            <a href="#" class="navbar-link">安全退出</a>
        </div>
    </div>
</nav>

<!--下部所有-->
<div class="container">

    <div class="row">

        <!--下部左侧s-->
        <div class="col-md-2">

            <div class="panel-group" id="acc">
                <!--轮播图管理-->
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pc1" class="panel-title" data-toggle="collapse" data-parent="#acc">轮播图管理</a>
                    </div>
                    <div class="panel-collapse collapse" id="pc1">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#content').load('${pageContext.request.contextPath}/banner2.jsp')">所有轮播图</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!--轮播图管理end-->
                <!--专辑管理-->
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pc2" class="panel-title" data-toggle="collapse" data-parent="#acc">专辑管理</a>
                    </div>
                    <div class="panel-collapse collapse" id="pc2">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#content').load('${pageContext.request.contextPath}/album.jsp')">所有专辑</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!--专辑管理end-->
                <!--文章管理-->
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pc3" class="panel-title" data-toggle="collapse" data-parent="#acc">文章管理-</a>
                    </div>
                    <div class="panel-collapse collapse" id="pc3">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#content').load('${pageContext.request.contextPath}/article3.jsp')">所有文章</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!--文章管理end-->
                <!--用户管理-->
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pc4" class="panel-title" data-toggle="collapse" data-parent="#acc">用户管理-</a>
                    </div>
                    <div class="panel-collapse collapse" id="pc4">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#content').load('${pageContext.request.contextPath}/user2.jsp')">所有用户</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!--用户管理end-->


                <%--<!--用户登录折线图-->
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <a href="#pc5" class="panel-title" data-toggle="collapse" data-parent="#acc">用户登录折线图</a>
                    </div>
                    <div class="panel-collapse collapse" id="pc5">
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="javascript:$('#content').load('${pageContext.request.contextPath}/echarts/echarts.jsp')">用户登录折线图</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!--用户登录折线图end-->--%>
            </div>
        </div>


        <!--下部右侧s-->
        <div class="col-md-10" id="content">
            <div class="col-md-12" style="width: 100%">
                <div class="jumbotron">
                    <h1>欢迎来到后台管理系统</h1>
                </div>
            </div>
            <div class="col-md-12">
                <img src="${pageContext.request.contextPath}/back.jpg">
            </div>
        </div>
    </div>

    <!--底部-->
    <div class="panel panel-footer text-center" style="position: absolute;bottom: 0px;width: 100%;">
        <h3>持明法州后台管理系统@百知教育</h3>
    </div>
</div>
</body>

</html>

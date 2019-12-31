<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
    <%--引入jquery的js文件--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <!-- 引入 ECharts 文件 -->
    <script src="echarts.min.js"></script>

</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},//展示对应光标移入的详细信息
        legend: {
            data: ['男', '女']
        },
        xAxis: {
            data: ["12/21", "12/22", "12/23", "12/24", "12/26", "12/27"]
        },
        yAxis: {},
        series: [{
            name: '男',
            type: 'line'
        }, {
            name: '女',
            type: 'line'
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url: "${pageContext.request.contextPath}/user/queryNum",
        dataType: "json",
        type: "get",
        success: function (data) {
            var man = data.man;
            var woman = data.woman;
            console.log(man);
            console.log(woman);

            myChart.setOption({
                series: [{
                    data: man
                }, {
                    data: woman
                }]
            });
        }
    })
</script>
</body>
</html>
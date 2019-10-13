<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title></title>
    <script src="../js/jquery-3.2.0.min.js"></script>
    <script src="../js/bootstrap.js"></script>
    <script src="../js/main2.js"></script>
    <script src="../js/LengYY.js"></script>
    <link href="../css/bootstrap.min.css" rel="stylesheet" />
    <link href="../css/bootstrap-theme.min.css" rel="stylesheet" />
    <link href="../css/newfile.css" rel="stylesheet" />
    <link href="../css/concreteInfo.css" rel="stylesheet" />
    <title>主页</title>
    <script>
        $(document).ready(function () {
            $('#update_p').click(function(){
                $('#test').toggle();
            });
            $('#test').on('input propertychange',function(){
                var result = $(this).val();
                console.log(result);
                $('#update_p').html(result);
            });

            $('#test').onchange(function(){
                $('#update_p').value=$('#test').value;
            });
        });
    </script>


</head>
<body>
<!-- 标题和导航栏 -->
<div class="container">
    <h1>Welcome to boostrap</h1>
    <nav class="navbar navbar-inverse">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-menu"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Brand</a>
        </div>
        <div id="navbar-menu" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="javascript:queryTopCelebrity(0,9,1)">Home</a></li>
                <li>
                    <a href="#systemSetting" class="nav-header collapsed" data-toggle="collapse">
                        <i class="glyphicon glyphicon-cog"></i>
                        明星管理
                        <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                    </a>
                    <ul id="systemSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                        <li><a href="javascript:addCelebrityFlush()"><i class="glyphicon glyphicon-user"></i>添加明星</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-th-list"></i>菜单管理</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-asterisk"></i>角色管理</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-edit"></i>修改密码</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-eye-open"></i>日志查看</a></li>
                    </ul>
                </li>
                <li><a href="#">Page Two</a></li>
            </ul>
        </div>
    </nav>
</div>
<div class="info">
    <h1> this is the area for info</h1>
</div>
<div class="pageSelect col-md-8" >

</div>
<div class="foot">
    <div class="container">
        </br></br></br></br></br></br>
        <div class="col-md-6 footText">

        </div>
    </div>
</div>
<button id="update_p">input test</button>
<input hidden="hidden" id="test"/>



</body>
<script type="text/javascript">
    queryTopCelebrity(0,9,1);
    function  Ajax(data,urls,a){
        console.log(data);
        $.ajax({
            data:data,
            type:"GET",
            url:"<%=pageContext.getServletContext().getContextPath()%>"+urls,
            error : function(data) {
                console.log("出错了！！:");
            },
            success : function(data){
                a(data);
            }

        });
    }
</script>
</html>

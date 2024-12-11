<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            var name = localStorage.getItem('username');  
            if (name) {
                $("#namedisp").text("欢迎, " + name);  
            } else {
                $("#namedisp").text("未找到用户名");  
            }
        });
    </script>
</head>
<body>
    <h1>登陆成功 这里是 main 页面</h1>
    
    <!-- 显示用户名 -->
    <div id="namedisp"></div>

	<a href="login">退出</a>
<p style="text-align: center;">
    <a href="${pageContext.request.contextPath}/fileupload">文件上传</a><br>
    <a href="${pageContext.request.contextPath}/filedownload">文件下载</a>
</p>


</body>
</html>

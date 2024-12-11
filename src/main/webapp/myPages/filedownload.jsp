<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>文件上传与下载</title>
</head>
<body>
    <div id="main" style="width:500px; margin: 0 auto;">
    </div>

    <!-- Use CDN for jQuery -->
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script>
        $(function(){
            var target = $("#main");
            $.ajax({
                url: "fileList",  
                dataType: "json",
                success: function (data) {
                    
                    for (var i in data) {

                    	//解decode
                        var decodedFileName = decodeURIComponent(data[i]);  
                        
                        
                        // 通过 split 方法提取文件名的中文部分
                        var fileNameParts = decodedFileName.split('_'); 
                        var displayName = fileNameParts[fileNameParts.length - 1];  // 获取去掉 UUID 部分的文件名

                        // 创建并显示文件链接
                        var a = $("<a></a><br>").text(displayName); 

                        //var a = $("<a></a><br>").text(decodedFileName);  
                        
 
                        //加密encode
                        a.attr("href", "<%= request.getContextPath() %>/download?filename=" + encodeURIComponent(data[i]));
                        

                        target.append(a);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.error("Request failed: " + textStatus);
                }
            });
        });
    </script>
</body>
</html>

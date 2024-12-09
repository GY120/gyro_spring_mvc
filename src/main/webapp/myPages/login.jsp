<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<meta charset="UTF-8">
<html>
<head>
    <title>Login</title>
    <link type="text/css" rel="stylesheet" href="../" />
    <style type="text/css">
        /* Reset default margin and padding */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            width: 100%;
            max-width: 400px;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
            font-size: 24px;
        }

        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }

        .form-group label {
            display: block;
            font-weight: bold;
            color: #333;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-top: 5px;
            box-sizing: border-box;
        }

        .form-group input:focus {
            border-color: #007bff;
            outline: none;
        }

        .form-group .errors {
            color: #ff0000;
            font-size: 12px;
        }

        .submit-btn {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        .submit-btn:hover {
            background-color: #0056b3;
        }

        .language-switch {
            margin-top: 10px;
            font-size: 20px;
        }

        .language-switch a {
            color: #007bff;
            text-decoration: none;
            margin: 0 5px;
        }

        .language-switch a:hover {
            text-decoration: underline;
        }
    </style>
    <script>
        // 动态提交语言切换表单
        function changeLanguage(lang) {
            var form = document.getElementById("languageForm");
            form.elements["lang"].value = lang;
            form.submit();
        }

        function storeUsername() {
            var username = document.getElementById("username").value;
            localStorage.setItem('username', username);
        }
    </script>
</head>
<body>

    <div class="container">
        <!-- 使用表单提交语言参数 -->
        <form id="languageForm" method="post" action="logining">
            <input type="hidden" name="lang" value="" />
            <div class="language-switch">
                <a href="javascript:void(0);" onclick="changeLanguage('en')">English</a> | 
                <a href="javascript:void(0);" onclick="changeLanguage('zh_CN')">中文</a>
            </div>
        </form>

        <h2><spring:message code="ulogin.label" /></h2>

        <form:form method="post" action="logining" modelAttribute="user" onsubmit="storeUsername()">
            <div class="form-group">
                <label for="username"><spring:message code="username.label" /></label>
                <form:input path="username" id="username" />
                <form:errors path="username" class="errors" />
            </div>

            <div class="form-group">
                <label for="password"><spring:message code="password.label" /></label>
                <form:input path="password" type="password" />
                <form:errors path="password" class="errors" />
            </div>

            <div class="form-group">
                <input type="submit" class="submit-btn" value="<spring:message code='login.label' />" />
            </div>
        </form:form>
    </div>

</body>
</html>

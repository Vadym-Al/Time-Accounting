<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>
    <link rel="stylesheet" href="css/register_login_fon.css">
    <style type="text/css">
        body{
            background-image: url("css/img/bg_login_alt.png");
        }
        form {
            background: white;
            width: 25%;
            padding: 30px;
            margin: 10% auto 0;
            border-radius: 20px 20px 20px 20px;
            color: black;
        }
    </style>
</head>
<body>
<main>
<form method="post" action="login">
    <h1>Sign in</h1>
    <div>
        <%--@declare id="email"--%><label for="email">E-mail<b> *</b></label>
        <label>
            <input type="email" name="email" required>
        </label>
    </div>
    <div>
        <%--@declare id="password"--%><label for="password">Password<b> *</b></label>
        <label>
            <input type="password" name="password" required>
        </label>
    </div>
    <c:if test="${error == true}">
        <div>
                <%--@declare id="error"--%><label for="error"><b>This customer was not found</b></label>
        </div>
    </c:if>
    <button type="submit">Confirm</button>
</form>
</main>
</body>
</html>

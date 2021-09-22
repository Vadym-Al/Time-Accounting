<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link rel="stylesheet" href="css/register_login_fon.css">
    <style type="text/css">
        body{
            background-image: url("css/img/bg_login_alt.png");
        }
        form {
            background: white;
            width: 25%;
            padding: 30px;
            margin: 120px auto 0;
            border-radius: 20px 20px 20px 20px;
            color: black;
        }
    </style>
</head>
<body>
<main>
<form method="post" action="add_activity">
    <h1>Add Activity</h1>
    <div>
        <%--@declare id="name"--%><label for="name">Name<b> *</b></label>
        <label>
            <input type="text" name="name" required>
        </label>
    </div>
    <div>
        <%--@declare id="about"--%><label for="about">About</label>
        <label>
            <input type="text" name="about">
        </label>
    </div>
    <c:if test="${requestScope.error == true}">
        <div>
                <%--@declare id="error"--%><label for="error"><b>This activity has already created</b></label>
        </div>
    </c:if>
    <button type="submit">Confirm</button>
</form>
</main>
</body>
</html>

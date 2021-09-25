<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Task</title>
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
    <form method="post" action="add_task">
        <h1>Add Task</h1>
        <div>
            <%--@declare id="name"--%><label for="name">Name<b> *</b></label>
            <label>
                <input type="text" name="name" required>
            </label>
        </div>
        <div>
            <%--@declare id="dead_line"--%><label for="dead_line">Dead Line<b> *</b></label>
            <label>
                <input type="date" name="dead_line" required>
            </label>
        </div>
        <div>
            <%--@declare id="activity_type"--%><label for="activity_type">Activity type<b> *</b></label>
            <label>
                <select name="activity_type">
                    <c:forEach var="activity" items="${activity}">
                        <option><c:out value="${activity.name}"/></option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <div>
            <%--@declare id="users_name"--%><label for="users_name">User Name<b> *</b></label>
            <label>
                <select name="users_name">
                    <c:forEach var="users_name" items="${users}">
                        <option><c:out value="${users_name.name}"/></option>
                    </c:forEach>
                </select>
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
                    <%--@declare id="error"--%><label for="error"><b>This task has already created</b></label>
            </div>
        </c:if>
        <button type="submit">Confirm</button>
    </form>
</main>
</body>
</html>

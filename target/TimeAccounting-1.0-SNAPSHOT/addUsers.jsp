<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add User</title>
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
    <form method="post" action="add_user">
        <h1>Registration</h1>
        <div>
            <%--@declare id="name"--%><label for="name">Name<b> *</b></label>
            <label>
                <input type="text" name="name" required>
            </label>
        </div>
        <div>
            <%--@declare id="last_name"--%><label for="last_name">Last Name<b> *</b></label>
            <label>
                <input type="text" name="last_name" required>
            </label>
        </div>
        <div>
            <%--@declare id="password"--%><label for="password">Password<b> *</b></label>
            <label>
                <input type="password" name="password" required>
            </label>
        </div>
        <div>
            <%--@declare id="email"--%><label for="email">E-mail<b> *</b></label>
            <label>
                <input type="email" name="email" required>
            </label>
        </div>
        <div>
            <%--@declare id="team_name"--%><label for="team_name">Team Name<b> *</b></label>
            <label>
                <select name="team_name">
                    <c:forEach var="teams" items="${teams}">
                        <option><c:out value="${teams.name}"/></option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <div>
            <%--@declare id="company"--%><label for="company">Company</label>
            <label>
                <input type="text" name="company">
            </label>
        </div>
        <div>
            <%--@declare id="phone_number"--%><label for="phone_number">Phone number</label>
            <label>
                <input type="text" name="phone_number">
            </label>
        </div>
        <c:if test="${error == true}">
            <div>
                    <%--@declare id="error"--%><label for="error"><b>This administrator has already created</b></label>
            </div>
        </c:if>
        <button type="submit">Confirm</button>
    </form>
</main>
</body>
</html>

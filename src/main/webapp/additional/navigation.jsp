<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: aldyk
  Date: 15.05.2022
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value = "${lang}"/>
<fmt:setBundle basename="resources" var="langBundle"/>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="css/main_screen.css">
    <link rel="stylesheet" href="css/main_logic_bar.css">
    <link rel="stylesheet" href="css/form_main.css">
</head>
<body>
<script>
    function updateLanguage(name) {
        document.cookie = 'Local='+name;
        location.reload();
    }
</script>

<div class="navigation">
    <div class="logo"><fmt:message key="LOGO" bundle="${langBundle}"/></div>
    <div class="menu">
        <c:choose>
            <c:when test="${isAdmin eq true}">
                <ul class="nav">
                    <li><a class="link" href="show_context?page=Teams"><fmt:message key="Teams" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="show_context?page=Users"><fmt:message key="Users" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="show_context?page=Activities"><fmt:message key="Activities" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="show_context?page=Tasks"><fmt:message key="Tasks" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="show_context?page=Requests"><fmt:message key="Requests" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="#"><select onchange="updateLanguage(value)">
                        <option value=""><fmt:message key="Language" bundle="${langBundle}"/></option>
                        <option value="en"><fmt:message key="English" bundle="${langBundle}"/></option>
                        <option value="ru"><fmt:message key="Russian" bundle="${langBundle}"/></option>
                        <option value="ua"><fmt:message key="Ukrainian" bundle="${langBundle}"/></option>
                    </select></a></li>
                </ul>
                <ul class="nav">
                    <li><a class="link" href="logout"><fmt:message key="Sign_out" bundle="${langBundle}"/></a></li>
                </ul>
                <ul class="nav icon">
                    <li><a class="link" href="profile"><i>${user}</i></a></li>
                    <li><a class="link" href="profile"><img class="user" src="css/img/user.png" alt="user"></a></li>
                </ul>
            </c:when>
            <c:when test="${isAdmin eq false}">
                <ul class="nav">
                    <li><a class="link" href="show_context?page=Teams"><fmt:message key="Teams" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="show_context?page=Tasks"><fmt:message key="Tasks" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="show_context?page=Activities"><fmt:message key="Activities" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="show_context?page=Requests"><fmt:message key="Requests" bundle="${langBundle}"/></a></li>
                    <li><a class="link" href="#"><select onchange="updateLanguage(value)">
                        <option value=""><fmt:message key="Language" bundle="${langBundle}"/></option>
                        <option value="en"><fmt:message key="English" bundle="${langBundle}"/></option>
                        <option value="ru"><fmt:message key="Russian" bundle="${langBundle}"/></option>
                        <option value="ua"><fmt:message key="Ukrainian" bundle="${langBundle}"/></option>
                    </select></a></li>
                </ul>
                <ul class="nav">
                    <li><a class="link" href="logout"><fmt:message key="Sign_out" bundle="${langBundle}"/></a></li>
                </ul>
                <ul class="nav icon">
                    <li><a class="link" href="profile"><i>${user}</i></a></li>
                    <li><a class="link" href="profile"><img class="user" src="css/img/user.png" alt="user"></a></li>
                </ul>
            </c:when>
            <c:otherwise>
                <ul class="nav">
                    <li><a class="link" href="#"><select onchange="updateLanguage(value)">
                        <option value=""><fmt:message key="Language" bundle="${langBundle}"/></option>
                        <option value="en"><fmt:message key="English" bundle="${langBundle}"/></option>
                        <option value="ru"><fmt:message key="Russian" bundle="${langBundle}"/></option>
                        <option value="ua"><fmt:message key="Ukrainian" bundle="${langBundle}"/></option>
                    </select></a></li>
                </ul>
                <ul class="nav icon">
                    <li><a class="link" href="login.jsp"><i><fmt:message key="Sign_in" bundle="${langBundle}"/></i></a></li>
                    <li><a class="link" href="#"><img class="user" src="css/img/user.png" alt="user"></a></li>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</div>
</body>
</html>

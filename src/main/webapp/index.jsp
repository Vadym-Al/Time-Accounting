<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
    <link rel="stylesheet" href="css/main_screen.css">
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
        main{
            background: url("css/img/mail_layout.png");
            position: absolute;
            display: flex;
            width: 80%;
            height: 80%;
            margin: 0 auto;
            top: 48%;
            left: 40%;
            transform: translate(-38%, -40%);
            border-radius: 20px 20px 20px 20px;
            color: black;
        }
    </style>
</head>
<body>
<jsp:include page="additional/kostil.jsp"/>

<fmt:setLocale value = "${lang}"/>
<fmt:setBundle basename="resources" var="langBundle"/>

<header>
    <jsp:include page="additional/navigation.jsp">
        <jsp:param name="isAdmin" value="${requestScope.isAdmin}"/>
    </jsp:include>
</header>
    <main>
        <div class="description">
            <h1><fmt:message key="Time_tracking" bundle="${langBundle}"/></h1>
            <p><fmt:message key="TEXT" bundle="${langBundle}"/></p><br>
            <a href="registration.jsp" class="submit"><fmt:message key="Get_started" bundle="${langBundle}"/></a>
        </div>
    </main>
</body>
</html>
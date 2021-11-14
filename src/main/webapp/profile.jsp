<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
    <link rel="stylesheet" href="css/main_screen.css">
    <link rel="stylesheet" href="css/profile.css">
    <style>
        main{
            background: white;
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
<div class="navigation">
    <div class="logo">LOGO</div>
    <div class="menu">
        <c:if test="${requestScope.isAdmin == true}">
            <ul class="nav">
                <li><a class="link" href="show_context?page=Teams">Teams</a></li>
                <li><a class="link" href="show_context?page=Users">Users</a></li>
                <li><a class="link" href="show_context?page=Activities">Activities</a></li>
                <li><a class="link" href="show_context?page=Tasks">Tasks</a></li>
                <li><a class="link" href="show_context?page=Requests">Requests</a></li>
                <li><a class="link" href="#"><select>
                    <option onclick="">English</option>
                    <option>Russian</option>
                    <option>French</option>
                </select></a></li>
            </ul>
            <ul class="nav">
                <li><a class="link" href="logout">Sign out</a></li>
            </ul>
        </c:if>
        <c:if test="${requestScope.isAdmin == false}">
            <ul class="nav">
                <li><a class="link" href="show_context?page=Teams">Teams</a></li>
                <li><a class="link" href="show_context?page=Tasks">Tasks</a></li>
                <li><a class="link" href="show_context?page=Activities">Activities</a></li>
                <li><a class="link" href="show_context?page=Requests">Requests</a></li>
                <li><a class="link" href="#"><select>
                    <option onclick="">English</option>
                    <option>Russian</option>
                    <option>French</option>
                </select></a></li>
            </ul>
            <ul class="nav">
                <li><a class="link" href="logout">Sign out</a></li>
            </ul>
        </c:if>
        <ul class="nav icon">
            <li><a class="link" href="profile"><i>${user}</i></a></li>
            <li><a class="link" href="profile"><img class="user" src="css/img/user.png" alt="user"></a></li>
        </ul>
    </div>
</div>
<main>
    <c:forEach var="customer" items="${customers}">
    <form method="post" action="#">
        <h1>${user}</h1>
        <div>
            <%--@declare id="email"--%><label for="email">E-mail: <c:out value="${customer.email}"/></label>
        </div>
        <br>
        <div>
            <%--@declare id="company"--%><label for="company">Company: <c:out value="${customer.company}"/></label>
        </div>
        <br>
        <div>
            <%--@declare id="phoneNumber"--%><label for="phoneNumber">Phone Number: <c:out value="${customer.phoneNumber}"/></label>
        </div>
        <br>
        <div>
                <%--@declare id="createTime"--%><label for="createTime">Create Time: <c:out value="${customer.createTime}"/></label>
        </div>
        <br>
    </form>
    </c:forEach>
</main>
</body>
</html>

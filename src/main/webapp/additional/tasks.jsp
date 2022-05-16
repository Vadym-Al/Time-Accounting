<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aldyk
  Date: 15.05.2022
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="css/main_screen.css">
    <link rel="stylesheet" href="css/main_logic_bar.css">
    <link rel="stylesheet" href="css/form_main.css">
    <style type="text/css">
        main {
            background: none;
            position: absolute;
            display: flex;
            justify-content: space-between;
            width: 80%;
            height: 80%;
            margin: 0 auto;
            top: 52%;
            left: 40%;
            transform: translate(-38%, -40%);
            border-radius: 20px 20px 20px 20px;
            color: black;
        }
    </style>
</head>
<body>
<main>
    <div class="d-flex flex-wrap">
        <c:choose>
            <c:when test="${isAdmin eq true}">
                <c:forEach var="customer" items="${customers}">
                    <form method="post"
                          onSubmit='return confirm("Are you sure, ${customer.name} task will be deleted?");'
                          action="delete?id=${customer.taskId}&type=Task">
                        <div>
                            <h1><c:out value="${customer.name}"/></h1>
                            <br>
                        </div>
                        <div>
                                <%--@declare id="deadline"--%><label for="deadline">Dead Line: <c:out
                                value="${customer.deadline}"/></label>
                                <%--@declare id="about"--%><label for="about">Owner: <c:out
                                value="${customer.about}"/></label>
                                <%--@declare id="wastedTime"--%><label for="wastedTime">Wasted Time: <c:out
                                value="${customer.wastedTime}"/></label>
                        </div>
                        <br>
                        <button type="submit">Delete</button>
                    </form>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach var="customer" items="${customers}">
                    <form method="post" action="edit?id=${customer.taskId}">
                        <div>
                            <h1><c:out value="${customer.name}"/></h1>
                            <br>
                        </div>
                        <div>
                                <%--@declare id="deadline"--%><label for="deadline">Dead Line: <c:out
                                value="${customer.deadline}"/></label>
                                <%--@declare id="about"--%><label for="about">Description: <c:out
                                value="${customer.about}"/></label>
                                <%--@declare id="wasted_time"--%><label for="wasted_time">Wasted Time: <input
                                type="time"
                                name="wastedTime"
                                value="${customer.wastedTime}"
                                required></label>
                        </div>
                        <br>
                        <button type="submit">Edit</button>
                    </form>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>

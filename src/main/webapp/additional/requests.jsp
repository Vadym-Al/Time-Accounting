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
                    <form method="post" onSubmit='return confirm("Are you sure, Yo want to confirm this request?");'
                          action="confirm?id=${customer.requestId}">
                        <div>
                            <h1><c:out value="${customer.activityName}"/></h1>
                            <br>
                        </div>
                        <div>
                                <%--@declare id="about"--%><label for="about">About: <c:out
                                value="${customer.about}"/></label>
                                <%--@declare id="deadline"--%><label for="deadline">Dead Line: <input type="date"
                                                                                                      name="deadline"
                                                                                                      required></label>
                        </div>
                        <br>
                        <button type="submit">Confirm</button>
                    </form>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach var="customer" items="${customers}">
                    <form method="post" action="delete?id=${customer.userId}&type=Request">
                        <div>
                            <h1><c:out value="${customer.activityName}"/></h1>
                            <br>
                        </div>
                        <div>
                                <%--@declare id="about"--%><label for="about">About: <c:out
                                value="${customer.about}"/></label>
                        </div>
                        <br>
                        <button type="submit">Delete</button>
                    </form>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>

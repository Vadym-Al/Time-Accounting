<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aldyk
  Date: 15.05.2022
  Time: 19:37
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
        <c:forEach var="customer" items="${customers}">
            <form method="post" onSubmit='return confirm("Are you sure, ${customer.name} will be deleted?");'
                  action="delete?id=${customer.userId}&type=User">
                <div>
                    <h1><c:out value="${customer.name} ${customer.lastName}"/></h1>
                    <br>
                </div>
                <div>
                        <%--@declare id="email"--%><label for="email">Email: <c:out
                        value="${customer.email}"/></label>
                        <%--@declare id="company"--%><label for="company">Company: <c:out
                        value="${customer.company}"/></label>
                        <%--@declare id="phoneNumber"--%><label for="phoneNumber">Phone Number: <c:out
                        value="${customer.phoneNumber}"/></label>
                </div>
                <br>
                <button type="submit">Delete</button>
            </form>
        </c:forEach>
    </div>
</main>
</body>
</html>

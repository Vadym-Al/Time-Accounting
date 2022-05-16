<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aldyk
  Date: 15.05.2022
  Time: 19:33
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
</head>
<body>
<c:choose>
    <c:when test="${isAdmin eq true}">
        <form style="background: none" action="sort" method="get">
            <div class="logic_bar">
                <div class="main_event">${head}</div>
                <c:choose>
                    <c:when test="${isTask eq true}">
                        <ul class="logic_nav logic_icon">
                            <li>
                                <select style="text-align: right" name="filter_param">
                                    <c:forEach var="activity" items="${activity}">
                                        <option><c:out value="${activity.name}"/></option>
                                    </c:forEach>
                                </select>
                            </li>
                            <li><a class="link"><input class="link"
                                                       style="font-size: 17px; background: none; border: none;"
                                                       type="submit" name="submit" value="Filter"/></a></li>
                        </ul>
                        <ul class="logic_nav logic_icon">
                            <li><select style="text-align: right" name="sort_param">
                                <option onclick="">Name</option>
                                <option>Activity type</option>
                                <option>By deadline</option>
                            </select></li>
                            <li><a class="link"><input class="link"
                                                       style="font-size: 17px; background: none; border: none;"
                                                       type="submit" name="submit" value="Sort"/></a></li>
                        </ul>
                    </c:when>
                    <c:when test="${isRequest eq true}">
                    </c:when>
                    <c:otherwise>
                        <ul class="logic_nav">
                            <li><a class="link" href="redirect?head=${head}">Add</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </c:when>
    <c:otherwise>
        <div class="logic_bar">
            <div class="main_event">${head}</div>
            <c:if test="${isRequest eq true}">
                <div class="logic_menu">
                    <ul class="logic_nav">
                        <li><a class="link" href="redirect?head=${head}">Add</a></li>
                    </ul>
                </div>
            </c:if>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>

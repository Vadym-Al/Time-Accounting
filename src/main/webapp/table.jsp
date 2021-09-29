<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Table</title>
</head>
<body>
<table border="1">
    <c:forEach var="tasks" items="${requestScope.tasks}">
    <tr>
        <td><c:out value="${tasks.taskId}"/></td>
        <td><c:out value="${tasks.name}"/></td>
        <td><c:out value="${tasks.deadline}"/></td>
        <td><c:out value="${tasks.about}"/></td>
        <td><c:out value="${tasks.wastedTime}"/></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>

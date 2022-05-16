<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
</head>
<body>
<jsp:include page="additional/kostil.jsp"/>

<jsp:include page="additional/navigation.jsp">
    <jsp:param name="isAdmin" value="${requestScope.isAdmin}"/>
</jsp:include>

<c:set var="activity" value="${activity}" scope="request"/>
<jsp:include page="additional/logicBar.jsp">
    <jsp:param name="isAdmin" value="${requestScope.isAdmin}"/>
    <jsp:param name="isRequest" value="${requestScope.isRequest}"/>
    <jsp:param name="isTask" value="${requestScope.isTask}"/>
    <jsp:param name="head" value="${requestScope.head}"/>
</jsp:include>


<c:set var="customers" value="${requestScope.customers}" scope="request"/>
<c:if test="${isTeam == true}">
    <jsp:include page="additional/teams.jsp">
        <jsp:param name="isAdmin" value="${requestScope.isAdmin}"/>
    </jsp:include>
</c:if>

<c:if test="${isActivity == true}">
    <jsp:include page="additional/activities.jsp">
        <jsp:param name="isAdmin" value="${requestScope.isAdmin}"/>
    </jsp:include>
</c:if>

<c:if test="${isTask == true}">
    <jsp:include page="additional/tasks.jsp">
        <jsp:param name="isAdmin" value="${requestScope.isAdmin}"/>
    </jsp:include>
</c:if>

<c:if test="${isUser == true}">
    <jsp:include page="additional/users.jsp">
        <jsp:param name="isAdmin" value="${requestScope.isAdmin}"/>
    </jsp:include>
</c:if>

<c:if test="${isRequest == true}">
    <jsp:include page="additional/requests.jsp">
        <jsp:param name="isAdmin" value="${requestScope.isAdmin}"/>
    </jsp:include>
</c:if>

</body>
</html>

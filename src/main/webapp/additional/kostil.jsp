<%--
  Created by IntelliJ IDEA.
  User: aldyk
  Date: 16.05.2022
  Time: 1:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();
    String l = null;
    for (Cookie cookie: cookies) {
        if (cookie.getName().equals("Local")){
            l = cookie.getValue();
            request.setAttribute("lang", l);
        }
    }
    if (l == null) {
        request.setAttribute("lang", "en");
        response.addCookie(new Cookie("Local", "en"));
    }
%>
</body>
</html>

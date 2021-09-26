<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
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
<div class="navigation">
    <div class="logo">LOGO</div>
    <div class="menu">
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
        <ul class="nav icon">
            <li><a class="link" href="profile"><i>${requestScope.user}</i></a></li>
            <li><a class="link" href="profile"><img class="user" src="css/img/user.png" alt="user"></a></li>
        </ul>
    </div>
</div>
<form style="background: none" action="sort" method="get">
    <div class="logic_bar">
        <div class="main_event">${requestScope.head}</div>
        <c:if test="${requestScope.isRequest!=true}">
            <div class="logic_menu">
                <c:if test="${isTask == true}">
                    <ul class="logic_nav logic_icon">
                        <li>
                            <select style="text-align: right" name="filter_param">
                                <c:forEach var="activity" items="${activity}">
                                    <option><c:out value="${activity.name}"/></option>
                                </c:forEach>
                            </select>
                        </li>
                        <li><a class="link"><input class="link" style="font-size: 17px; background: none; border: none;"
                                                   type="submit" name="submit" value="Filter"/></a></li>
                    </ul>
                    <ul class="logic_nav logic_icon">
                        <li><select style="text-align: right" name="sort_param">
                            <option onclick="">Name</option>
                            <option>Activity type</option>
                            <option>By deadline</option>
                        </select></li>
                        <li><a class="link"><input class="link" style="font-size: 17px; background: none; border: none;"
                                                   type="submit" name="submit" value="Sort"/></a></li>
                    </ul>
                </c:if>
                <ul class="logic_nav">
                    <li><a class="link" href="redirect?head=${requestScope.head}">Add</a></li>
                </ul>
            </div>
        </c:if>
    </div>
</form>
<main>
    <div class="d-flex flex-wrap">
        <c:if test="${isTeam == true}">
            <c:forEach var="customer" items="${customers}">
                <form method="post" onSubmit='return confirm("Are you sure, ${customer.name} team will be deleted?");'
                      action="delete?id=${customer.teamId}&type=Team">
                    <div>
                        <h1><c:out value="${customer.name}"/></h1>
                        <br>
                    </div>
                    <div>
                            <%--@declare id="company"--%><label for="company">Company: <c:out
                            value="${customer.company}"/></label>
                            <%--@declare id="description"--%><label for="description">Description: <c:out
                            value="${customer.description}"/></label>
                    </div>
                    <br>
                    <button type="submit">Delete</button>
                </form>
            </c:forEach>
        </c:if>
        <c:if test="${isActivity == true}">
            <c:forEach var="customer" items="${customers}">
                <form method="post"
                      onSubmit='return confirm("Are you sure, ${customer.name} activity will be deleted?");'
                      action="delete?id=${customer.activityId}&type=Activity">
                    <div>
                        <h1><c:out value="${customer.name}"/></h1>
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
        </c:if>
        <c:if test="${isTask == true}">
            <c:forEach var="customer" items="${customers}">
                <form method="post" onSubmit='return confirm("Are you sure, ${customer.name} task will be deleted?");'
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
        </c:if>
        <c:if test="${isUser == true}">
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
        </c:if>
        <c:if test="${isRequest == true}">
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
        </c:if>
    </div>
</main>
</body>
</html>

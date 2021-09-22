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
<header>
    <div class="navigation">
        <div class="logo">LOGO</div>
        <div class="menu">
            <ul class="nav">
                <li><a class="link" href="index.jsp">Welcome</a></li>
                <li><a class="link" href="#"><select>
                    <option onclick="">English</option>
                    <option>Russian</option>
                    <option>French</option>
                </select></a></li>
            </ul>
            <ul class="nav icon">
                <li><a class="link" href="login.jsp"><i>Sign in</i></a></li>
                <li><a class="link" href="#"><img class="user" src="css/img/user.png" alt="user"></a></li>
            </ul>
        </div>
    </div>
</header>
    <main>
        <div class="description">
            <h1>Time tracking</h1>
            <p>Time tracking software used by millions. <br>
                My web-site is a time tracker and timesheet app <br>
                that lets you track work hours across projects. <br>
                Unlimited users, free forever.</p><br>
            <a href="registration.jsp" class="submit">Get started</a>
        </div>
    </main>
</body>
</html>
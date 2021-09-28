<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/main_screen.css">
    <style type="text/css">
        body{
            background-image: url("css/img/bg_login_alt.png");
        }
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
<header>
    <div class="navigation">
        <div class="logo">LOGO</div>
    </div>
</header>
<main>
    <div class="description">
        <h1>Something has gone wrong</h1>
        <p>Problem was found during process request</p><br>
        <a href="logout" class="submit">Log out</a>
    </div>
</main>
</body>
</html>

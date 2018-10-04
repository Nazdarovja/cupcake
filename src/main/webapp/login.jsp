<%-- 
    Document   : login
    Created on : Sep 20, 2017, 7:02:04 PM
    Author     : Mellem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="Style/home.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <div id="options">
            <form name="login" action="Controller" method="POST">
                <input type="hidden" name="origin" value="login">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" name="username" class="form-control" id="username">
                </div>
                <div class="form-group">
                    <label for="password">Password :</label>
                    <input type="password" name="password" class="form-control" id="password">
                </div>
                <input type="submit" class="btn btn-primary" value="Login" /><br><br>
            </form>
        </div>
        <% if(request.getAttribute("error") != null){ %>
        <p> Username or Password is incorrect! Try again.</p>
        <% } %>
    </body>
</html>

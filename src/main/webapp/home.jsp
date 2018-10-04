<%-- 
    Document   : home
    Created on : Sep 20, 2017, 9:35:53 AM
    Author     : Mellem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="Style/home.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        
        
        <h1>HOME</h1>
        <div id="options">
        <form name="login" action="login.jsp" method="POST">
            <input type="hidden" name="origin" value="login">
            
            <input class="btn btn-success" type="submit" value="login" />
        </form>
        
        <form name="registration" action="registration.jsp" method="POST">
            <input type="hidden" name="origin" value="registration">
            
            <input class="btn btn-primary"type="submit" value="registration" />
        </form>
        </div>
    </body>
</html>

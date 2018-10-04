<%-- 
    Document   : registration
    Created on : Sep 20, 2017, 7:07:30 PM
    Author     : Mellem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="Style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <h1 id="registrationh1">Registration</h1>
        <div class="brownframe">
            <form name="registration" action="Controller" method="POST">
                <input type="hidden" name="origin" value="registration">
                <br>
                <table>
                    <tr>
                        <td>username</td>
                        <td id="usernameError"><input type="text" name="username" id="username" required placeholder="Enter Username"/></td>
                    </tr>
                    <tr>
                        <td>e-mail</td>
                        <td id="emailError"><input type="text" name="email" id="email" required placeholder="Enter Email"/></td>
                    </tr>
                    <tr>
                        <td>password</td>
                        <td id="passwordError"><input type="password" name="password" id="password" required placeholder="Enter Password"/></td>
                    </tr>

                </table>
                <input type="submit" value="registrate now" />
            </form>
        </div>

        <% if (request.getAttribute("error") != null) { %>
        <p> USER ALREADY EXISTS!! please try again</p>
        <% }%>
        <div class="cupcakesIMG"></div>

        <script src="scripts/FormValidation.js" type="text/javascript"></script>
    </body>
</html>

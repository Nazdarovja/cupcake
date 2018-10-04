<%-- 
    Document   : invoice
    Created on : Sep 22, 2017, 12:41:23 PM
    Author     : Mellem
--%>

<%@page import="Entitites.Orderline"%>
<%@page import="Entitites.ShoppingCart"%>
<%@page import="Entitites.User"%>
<%@page import="Model.OrderMapper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="Style/admin.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Order purchased!</h1>
        <% OrderMapper om = new OrderMapper(); %>
        <%User user = (User) request.getSession().getAttribute("user");%>

        <%ShoppingCart sc = (ShoppingCart) request.getSession().getAttribute("shoppingcart");%>

        <ul class="list-group col-sm-12">
            <% for (Orderline line : sc.getShoppingCart()) {%>
            <li class='list-group-item'><%=line%></li>
                <% }%>
        </ul>
        <p>the total price of this order is: <%= sc.totalPrice()%></p>
        <% request.getSession().setAttribute("shoppingcart", new ShoppingCart());%>
        <p><a href="shop.jsp">back</a></p>
    </body>
</html>

<%-- 
    Document   : invoiceDetails
    Created on : 22-09-2017, 23:20:30
    Author     : Orchi
--%>

<%@page import="Entitites.User"%>
<%@page import="Entitites.Orderline"%>
<%@page import="Entitites.ShoppingCart"%>
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
        <h1>Detail page of chosen invoice</h1>
        <% ShoppingCart cart = (ShoppingCart) request.getAttribute("order");%>
        <% User user = (User) request.getSession().getAttribute("user");%>
        <p>This invoice' orderd id: <%=cart.getOrderId()%></p>
        <ul class="list-group col-sm-12">
            <% for (Orderline line : cart.getShoppingCart()) {%>
            <li class='list-group-item'><%=line%></li>
                <% }%>
        </ul>
        <p>The total price for this invoice: <%= cart.totalPrice()%></p>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <!--IF USER IS ADMIN, SHOW LINK TO ADMIN PAGE-->    
        <% if (user.getAdmin() == 1) { %>
        <p>Go to admin page <a href="admin.jsp">here</a></p>
        <% } else {%>
        <p><a href="shop.jsp">back</a></p>
        <% } %>
    </body>
</html>

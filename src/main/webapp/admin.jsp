<%-- 
    Document   : admin
    Created on : 27-09-2017, 21:28:13
    Author     : Orchi
--%>

<%@page import="Entitites.ShoppingCart"%>
<%@page import="java.util.List"%>
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

        <!--MENU-->
        <nav class="navbar navbar-inverse">
            <ul class="nav navbar-nav">
                <li><a href="home.jsp"><img src="https://i.imgur.com/20KdV65.png" width="30" height="30"/></a></li>
                <li><a href="shop.jsp">Shop</a></li>
            </ul>
        </nav>

        <div class="col-sm-12">
            <h1>Admin page</h1>
            <h3>Here you can see all orders</h3>
            <p>Click one to see specifics of the order</p>
            <button id="sortByDate" type="button" class="btn btn-success">Sort by date</button>
            <button id="sortByCustomer" type="button" class="btn btn-primary">Sort by customer</button>
            <div>
                <div class="col-sm-3"></div>
                <ul id="orders" class="list-group col-sm-6">
                </ul>
                <script>
                    <%  OrderMapper om = new OrderMapper();
                        List<ShoppingCart> list = om.getOrders();
                    %>
                    var sortByDate = document.getElementById("sortByDate");
                    var sortByCustomer = document.getElementById("sortByCustomer");

                    sortByCustomer.onclick = function () {
                        var orders = [];

                        var output = "";
                    <%
                        for (int i = 0; i < list.size(); i++) {%>
                        var obj = {};
                        obj.id = Number(<%= list.get(i).getOrderId()%>);
                        obj.username = "<%= list.get(i).getUsername()%>";
                        orders[<%=i%>] = obj;
                    <% }
                    %>
                        orders.sort(function (a, b) {
                            return a.username > b.username;
                        });

                        for (var i = 0; i < orders.length; i++) {
                            output += "<li class='list-group-item'>Invoice nr: <a href='ShopController?origin=invoice&specificInvoice=" + orders[i].id + "'>" + orders[i].id + "</a> by " + orders[i].username + "</li>";
                        }
                        document.getElementById("orders").innerHTML = output;
                    };   

                    sortByDate.onclick = function () {
                        var orders = [];

                        var output = "";
                    <%
                        for (int i = 0; i < list.size(); i++) {%>
                        var obj = {};
                        obj.id = Number(<%= list.get(i).getOrderId()%>);
                    <%  String[] d = list.get(i).getOrderDate();%>
                        obj.date = new Date(<%=d[0]%>,<%=d[1]%>,<%=d[2]%>,<%=d[3]%>,<%=d[4]%>,<%=d[5]%>,<%=d[6]%>);
                        obj.username = "<%= list.get(i).getUsername()%>";
                        orders[<%=i%>] = obj;
                    <% }
                    %>
                        orders.sort(function (a, b) {
                            return a.date < b.date;
                        });
                        for (var i = 0; i < orders.length; i++) {
                            output += "<li class='list-group-item'>Invoice nr: <a href='ShopController?origin=invoice&specificInvoice=" + orders[i].id + "'>" + orders[i].id + "</a> by " + orders[i].username + ", " + orders[i].date + "</li>";
                            console.log(orders[i].date);
                        }
                        document.getElementById("orders").innerHTML = output;
                    };

                    function getAllOrders() {
                        var output = "";
                        var orders = [];
                    <%
                        for (int i = 0; i < list.size(); i++) {%>
                        orders[<%=i%>] = <%= list.get(i).getOrderId()%>
                    <% }%>
                        for (var i = <%=list.size() - 1%>; i >= 0; i--) {
                            output += "<li class='list-group-item'>Invoice nr: <a href='ShopController?origin=invoice&specificInvoice=" + orders[i] + "'>" + orders[i] + "</a></li>";
                        }
                        return output;
                    }
                    ;

                    window.onload = function () {
                        document.getElementById("orders").innerHTML = getAllOrders();
                    };


                </script>
                <div class="col-sm-3"></div>
                <div class="col-sm-12">
                    <p><a href="shop.jsp">back</a></p>
                </div>


                <!-- jQuery library -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
                <!-- Latest compiled JavaScript -->
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

                </body>
                </html>

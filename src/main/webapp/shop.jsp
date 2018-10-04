<%-- 
    Document   : Shop
    Created on : 21-09-2017, 10:50:57
    Author     : Orchi
--%>

<%@page import="Model.OrderMapper"%>
<%@page import="Entitites.ShoppingCart"%>
<%@page import="Entitites.Orderline"%>
<%@page import="Entitites.Cupcake"%>
<%@page import="Entitites.Topping"%>
<%@page import="java.util.List"%>
<%@page import="Entitites.Bottom"%>
<%@page import="Entitites.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shop</title>
        
        <!--BOOTSTRAP-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    </head>
    
    <!--START OF PAGE-->
    <body style="padding: 8px">

        <!--MENU-->
        <nav class="navbar navbar-inverse">
            <ul class="nav navbar-nav">
                <li><a href="home.jsp"><img src="https://i.imgur.com/20KdV65.png" width="30" height="30"/></a></li>
                <li><a href="shop.jsp">Shop</a></li>
            </ul>
        </nav>

        <!--USER INFO-->
        <div style="float: right" class="text-muted">
            <%User user = (User) request.getSession().getAttribute("user");%>
            <p><%= user.getUsername()%> is logged in, balance: <%= user.getBalance()%>,-</p>
        </div>
        <br>

        <!--CUPCAKE DATA SETUP-->        
        <% List<Bottom> bottoms = (List<Bottom>) request.getSession().getAttribute("bottoms");%>
        <% List<Topping> toppings = (List<Topping>) request.getSession().getAttribute("toppings");%>

        <!--TOP ROW-->
        <div class="row">

            <!--SPACE COLUMN LEFT 1/12-->
            <div class="col-sm-1"></div>

            <!--CUPCAKE PARTS (LEFTMOST 1/3)-->
            <div class="col-sm-4">
                <h3>Cupcake Parts</h3>

                <!--CUPCAKE PARTS COMBINATION FORM START-->
                <form method="POST" action="ShopController">
                    <input type="hidden" name="origin" value="addline">

                    <!--BOTTOM PARTS TABLE-->
                    <h4>Bottom</h4>
                    <select class="form-control" name="bottom">
                        <% for (Bottom b : bottoms) {%>
                        <option value="<%=b.getName()%>"><%=b.getName()%> <%=b.getPrice()%>,-</option>
                        <% } %>
                    </select>

                    <!--TOPPING PARTS TABLE-->
                    <h4>Topping</h4>
                    <select class="form-control" name="topping">
                        <% for (Topping t : toppings) {%>
                        <option value="<%=t.getName()%>"><%=t.getName()%> <%=t.getPrice()%>,-</option>
                        <% } %>
                    </select>
                    <br>

                    <!--COMBINED PARTS QUANTITY SELECTION-->
                    <h4>Quantity</h4>
                    <select class="form-control" name="quantity">
                        <option value=1>1</option>
                        <option value=2>2</option>
                        <option value=3>3</option>
                        <option value=4>4</option>
                        <option value=5>5</option>
                        <option value=6>6</option>
                        <option value=7>7</option>
                        <option value=8>8</option>
                        <option value=9>9</option>
                        <option value=10>10</option>
                    </select>
                    <!--ADD/SEND SELECTION TO CONTROLLER (add to order)-->
                    <input type="submit" value="add" />
                </form>
            </div>

            <!--ONGING CUPCAKE ORDER (MIDDLE 1/3)-->
            <div class="col-sm-4">

                <!--SETUP SHOPPING CART-->
                <% ShoppingCart sc = (ShoppingCart) request.getSession().getAttribute("shoppingcart");%>
                <ul style="padding-left: 0">

                    <!--IF NOT EMPTY, SHOW CURRENT ORDER INFO FORMATTED IN TABLE-->
                    <% if (!sc.getShoppingCart().isEmpty()) { %>
                    <table class="table table-striped">
                        <h3>Current Order</h3>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Combination</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>

                            <!--ORDERLINE DATA FORMATTING-->
                            <% for (Orderline ol : sc.getShoppingCart()) {%>
                            <% String cupcakeName = ol.getCupcake().getB().getName() + " with " + ol.getCupcake().getT().getName(); %>
                            <% int pricePerPiece = ol.getTotalPrice(); %>
                            <% int qty = ol.getQty();%>

                            <!--FOR EACH ORDERLINE, SHOW CUPCAKE INFO FORMATTED-->
                            <tr>
                                <td><%=qty%></td>
                                <td><%=cupcakeName%></td>
                                <td><%=pricePerPiece%></td>
                                <td>
                                    <form method="POST" action="ShopController">
                                        <input type="hidden" name="origin" value="remove">
                                        <input type="hidden" name="removal" value="<%=ol.getOrderLineId()%>">
                                        <input type="submit" value="remove" />
                                    </form>
                                </td>
                            </tr>
                            <% }
                                }%>
                        </tbody>
                    </table>
                </ul>
                <!--PRICE INFO SHOWN UNDER TABLE-->
                <div style="text-align: left">
                    <% if (sc.totalPrice() > 0) {%>
                    <b><p> Total price: <%=sc.totalPrice()%> </p></b>
                    <p> Balance after buy: <%=user.getBalance() - sc.totalPrice()%> </p>
                    <% } %>
                </div>

                <!--SUBMIT DATA TO SHOPCONTROLLER-->
                <form method="POST" action="ShopController">
                    <input type="hidden" name="origin" value="order">

                    <!--ERROR CHECKING, INSUFFICIENT FUNDS-->
                    <% if (request.getAttribute("error") != null) { %>
                    <p>  Insufficient funds! </p>
                    <% } %>

                    <!--FORWARD SHOPPINGCART TO SESSION (to be used for next orderline..)-->
                    
                    <%request.getSession().setAttribute("shoppingcart", sc);%>
                    
                    <% if (sc.totalPrice() > 0) {%>
                    <input type="submit" value="order" name="order"/>
                    <% } else { %>
                    <input type="submit" value="order" name="order" style="display: none;"/>
                    <% } %>
                    <text style="color: white;">.</text>
                    
                </form>
            </div>

            <!--LIST OF PREVIOUS ORDERS, RIGHTMOST 1/3-->
            <div class="col-sm-2">
                <div>
                    <h3>Previous Orders</h3>

                    <!--ORDER DATA SETUP-->
                    <%  OrderMapper om = new OrderMapper(); %>
                    <%User use = (User) request.getSession().getAttribute("user");%>

                    <!--LIST VIEW-->
                    <ul class="list-group">

                        <!--FOR EACH USER ORDER-->
                        <% for (ShoppingCart cart : om.getOrdersForSingleUser(use)){%>
                        <li class="list-group-item"><a style="display: block;" href="ShopController?origin=invoice&specificInvoice=<%=cart.getOrderId()%>"><%=cart.getOrderId()%></a></li>
                        <% }%>
                    </ul>
                </div>

                <!--IF USER IS ADMIN, SHOW LINK TO ADMIN PAGE-->    
                <% if (user.getAdmin() == 1) { %>
                <p>Go to admin page <a href="admin.jsp">here</a></p>
                <% }%>
            </div>

            <!--SPACE COLUMN, RIGHTMOST 1/12-->
            <div class="col-sm-1"></div>
        </div>

        <!--BOOTSTRAP-->
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    </body>
</html>

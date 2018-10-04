/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entitites.Bottom;
import Entitites.Cupcake;
import Entitites.Orderline;
import Entitites.ShoppingCart;
import Entitites.Topping;
import Entitites.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DataMapper to get and create data concerning execution of an order.
 * @author Stanislav
 */
public class OrderMapper {

    /**
     * Adds new order to the database, for the user in question, then gets the
     * auto generated Sql orderId, and uses that in a support
     * metheod(addOrderLines()) to add all the orderlines to the database ( done
     * in that order because of Foreign key constraints)
     *
     * @param user the user that is logged in the ongoing session
     * @param sc the ShoppingCart object, filled with the standing order of the
     * user
     * @return an orderId for the .jsp page to display
     * @throws Exception if Connection fails, or something goes wrong with
     * fetching the data form database
     */
    public int addOrder(User user, ShoppingCart sc) throws Exception {
        Connection conn = DBConnector.getConnection();
        String sql = "INSERT INTO cupcake.Order (id, username) VALUES(" + null + " ,?);";
        PreparedStatement userPstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        userPstmt.setString(1, user.getUsername());
        conn.setAutoCommit(false);
        int res = userPstmt.executeUpdate();
        int orderId = -1;

        if (res == 1) {
            ResultSet rs = userPstmt.getGeneratedKeys();
            rs.next();
            orderId = rs.getInt(1);
            addOrderLines(sc, orderId);
            updateUserBalance(user, sc);
            conn.commit();
        } else {
            conn.rollback();
        }
        return orderId;
    }

    /**
     * Support metheod for the addOrder() metheod, used to add the individual
     * orderlines for the given order.
     *
     * @param sc The ShoppingCart object, filled with the standing order of the
     * user
     * @param orderId Used to place the orderlines in the right place in the
     * database
     * @throws Exception if Connection fails, or something goes wrong with
     * fetching the data form database
     */
    private void addOrderLines(ShoppingCart sc, int orderId) throws Exception {
        List<Orderline> ol = sc.getShoppingCart();

        for (Orderline o : ol) {

            Cupcake c = o.getCupcake();
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO cupcake.Orderline VALUES( ?, ?, ?, ?);";
            PreparedStatement userPstmt = conn.prepareStatement(sql);
            userPstmt.setString(1, c.getT().getName());
            userPstmt.setString(2, c.getB().getName());
            userPstmt.setInt(3, o.getQty());
            userPstmt.setInt(4, orderId);
            userPstmt.executeUpdate();
        }
    }

    /**
     * Used to remove orderLines in the ShoppingCart object in the ongoing
     * Session
     *
     * @param sc The ShoppingCart object, filled with the standing order of the
     * user
     * @param orderId used find the right order
     * @return the modified ShoppingCart object without the line to be removed.
     */
    public ShoppingCart removeOrderline(ShoppingCart sc, int orderId) {
        List<Orderline> list = sc.getShoppingCart();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOrderLineId() == orderId) {
                sc.getShoppingCart().remove(i);
            }
        }
        return sc;
    }

    /**
     * Updates the users Balance in the database after purchase
     *
     * @param user that is logged in for the ongoing session
     * @param sc ShoppingCart to get the Total value of the order.
     * @throws Exception if Connection fails, or something goes wrong with
     * fetching the data form database
     */
    private void updateUserBalance(User user, ShoppingCart sc) throws Exception {     /// SKULLE NOK HAVE VÆRET NOGET DER LEVER I USERMAPPEREN OG IKKE I ORDERMAPPEREN ?
        int balance = withdrawBalanceFromUser(user);
        int newBalance = balance - sc.totalPrice();
        Connection conn = DBConnector.getConnection();
        String sql = "UPDATE User SET balance = ? WHERE username = ?";
        PreparedStatement userPstmt = conn.prepareStatement(sql);
        userPstmt.setInt(1, newBalance);
        userPstmt.setString(2, user.getUsername());
        userPstmt.executeUpdate();
    }

    /**
     * Gets the balance for the user that is logged in.
     * @param user is the user logged in the ongoing session
     * @return the balance
     * @throws Exception if Connection fails, or something goes wrong with
     * fetching the data form database
     */
    private int withdrawBalanceFromUser(User user) throws Exception {       /// SKULLE NOK HAVE VÆRET NOGET DER LEVER I USERMAPPEREN OG IKKE I ORDERMAPPEREN ?
        Connection conn = DBConnector.getConnection();
        String sql = "SELECT balance FROM User WHERE username = (?)";
        PreparedStatement userPstmt = conn.prepareStatement(sql);
        userPstmt.setString(1, user.getUsername());
        ResultSet row = userPstmt.executeQuery();
        int cash = 0;
        if (row.next()) {
            cash = Integer.parseInt(row.getString("balance"));
        }
        return cash;
    }

    /**
     * Gets all the orders for the user in question, and creates a list of then
     * sends them to a support metheod, that gets the actual orderlines, and
     * returns a list of ShoppingCarts.
     *
     * @param user
     * @return
     * @throws Exception
     */
    public List<ShoppingCart> getOrdersForSingleUser(User user) throws Exception {
        List<ShoppingCart> list = null;
        List<ShoppingCart> list1 = new ArrayList<>();

        Connection conn = DBConnector.getConnection();
        String sql = "SELECT id, username, date FROM cupcake.Order WHERE username = (?)";
        PreparedStatement userPstmt = conn.prepareStatement(sql);
        userPstmt.setString(1, user.getUsername());
        ResultSet rs = userPstmt.executeQuery();

        while (rs.next()) {
            ShoppingCart cart = new ShoppingCart();
            int id = rs.getInt("id");
            String date = rs.getString("date");
            String username = rs.getString("username");
            cart.setOrderId(id);
            cart.setOrderDate(date);
            cart.setUsername(username);
            list1.add(cart);
        }
        list = getAllOrderLines(list1);
        return list;
    }

    /**
     * Gets all the orders in the database for all the users.
     *
     * @return a list of ShoppingCarts, that represent all the orders made in
     * the system.
     * @throws Exception
     */
    public List<ShoppingCart> getOrders() throws Exception {
        List<ShoppingCart> list = null;
        List<ShoppingCart> list1 = new ArrayList<>();

        Connection conn = DBConnector.getConnection();
        String sql = "SELECT id, username, date FROM cupcake.Order";
        PreparedStatement userPstmt = conn.prepareStatement(sql);
        ResultSet rs = userPstmt.executeQuery();
        while (rs.next()) {
            ShoppingCart cart = new ShoppingCart();
            int id = rs.getInt("id");
            String date = rs.getString("date");
            String username = rs.getString("username");
            cart.setOrderId(id);
            cart.setOrderDate(date);
            cart.setUsername(username);
            list1.add(cart);
        }
        list = getAllOrderLines(list1);
        return list;
    }

    /**
     * Gets a list of id's for the users orders, then gets all the Orderlines,
     * and puts them in a list for that order, then puts them in a ShoppingCart
     * Object, and at last returns a List of ShoppingCarts (list of all the
     * orders from the user)
     *
     * @param id
     * @return a List of all the orders
     * @throws Exception if Connection fails, or something goes wrong with
     * fetching the data form database
     */
    private List<ShoppingCart> getAllOrderLines(List<ShoppingCart> cart) throws Exception {
        CupcakeMapper cm = new CupcakeMapper();
        for (ShoppingCart ca : cart) {
            Connection conn = DBConnector.getConnection();

            String sql = "SELECT * FROM cupcake.Orderline WHERE Order_id = (?)";
            PreparedStatement userPstmt = conn.prepareStatement(sql);
            userPstmt.setInt(1, ca.getOrderId());
            ResultSet rs = userPstmt.executeQuery();

            while (rs.next()) {
                String top = rs.getString("topname");
                String bottom = rs.getString("botname");
                int qty = rs.getInt("qty");

                Topping t = cm.getCupcakeTopping(top);
                Bottom b = cm.getCupcakeBottom(bottom);
                Cupcake c = new Cupcake(b, t);
                Orderline o = new Orderline(c, qty);
                ca.addLine(o);
            }
        }
        return cart;
    }

    /**
     * Uses the input orderId to find the specific order in the database,
     *
     * @param orderId
     * @return ShoppingCart object, containing the Id specific order for the
     * user in question.
     * @throws Exception if if Connection fails, or something goes wrong with
     * fetching the data form database
     */
    public ShoppingCart getSpecificShoppingCart(String orderId) throws Exception {
        CupcakeMapper cm = new CupcakeMapper();
        ShoppingCart cart = new ShoppingCart();
        Connection conn;

        conn = DBConnector.getConnection();

        String sql = "SELECT * FROM cupcake.Orderline WHERE Order_id = (?)";
        PreparedStatement userPstmt = conn.prepareStatement(sql);
        userPstmt.setInt(1, Integer.parseInt(orderId));
        ResultSet rs = userPstmt.executeQuery();

        while (rs.next()) {
            String top = rs.getString("topname");
            String bottom = rs.getString("botname");
            int qty = rs.getInt("qty");
            int orderID = rs.getInt("Order_id");

            Topping t = cm.getCupcakeTopping(top);
            Bottom b = cm.getCupcakeBottom(bottom);
            Cupcake c = new Cupcake(b, t);
            Orderline o = new Orderline(c, qty);

            cart.setOrderId(orderID);
            cart.addLine(o);

        }

        return cart;
    }

}

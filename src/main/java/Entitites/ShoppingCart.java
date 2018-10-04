/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitites;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class containing the complete order, composed of a list of Orderline objects, an OrderId, Purchase date and time and the username of the customer.
 * Built while ordering from web app, then used to upload data to the database.
 * @author Mellem
 */
public class ShoppingCart {

    private List<Orderline> lines;
    private int orderId;
    private String[] orderDate;
    private String username;

    /**
     * looped, toString metheod so we can print out the orderlines in the cart.
     * @return 
     */
    @Override
    public String toString() {
        String res = "ORDER ID : " + orderId + "\n ------------------------------- \n";
        for (Orderline o : lines) {
            res += o + " \n";
        }
        return res;
    }

    public ShoppingCart() {
        this.lines = new ArrayList<>();
    }

    public List<Orderline> getShoppingCart() {
        return this.lines;
    }
/**
 * Metheod to add a new orderline, if the orderline matches an existing orderline in the list,
 * the quantity of the existing order is just adjusted instead of adding an extra orderline.
 * @param line 
 */
    public void addLine(Orderline line) {
        boolean found = false;

        for (Orderline l : this.lines) {
            if (l.getCupcake().getB().getName().equals(line.getCupcake().getB().getName())
                    && l.getCupcake().getT().getName().equals(line.getCupcake().getT().getName())) {
                found = true;
                l.setQty(l.getQty() + line.getQty());
            }
        }
        if (!found) {
            this.lines.add(line);
        }

    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
/**
 * Create a array of String containing the Timestamp of the purchase.
 * (Complicated because of SQL date format, not compatible with Java libaries)
 * @param date 
 */
    public void setOrderDate(String date) {
        String[] d = date.split(" ");
        String[] d1 = d[0].split("-");
        String[] d2 = d[1].split(":");
        String[] d3 = new String[7];
        d3[0] = d1[0];
        d3[1] = d1[1];
        d3[2] = d1[2];
        d3[3] = d2[0];
        d3[4] = d2[1];
        d2[2] = d2[2].replace(".0", ""); 
        d3[5] = d2[2];
        d3[6] = "0";
        orderDate = d3;
    }

    public String[] getOrderDate() {
        return orderDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
/**
 * runs through the Orderline list, and calculates the total price of the ShoppingCart, and returns it.
 * @return 
 */
    public int totalPrice() {
        int price = 0;

        for (Orderline line : lines) {
            price += line.getTotalPrice();
        }

        return price;
    }

}

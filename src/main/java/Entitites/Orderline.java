/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitites;

/**
 * Orderline Entity is created with a complete cupcake, an orderId(ShoppingCartId) and the quantity, this goes into a ShoppingCart class.
 * @author Mellem
 */
public class Orderline {
    private Cupcake cupcake;
    private int qty;
    private int orderLineId;
    
    public Orderline(Cupcake cupcake, int qty){
        this.cupcake = cupcake;
        this.qty = qty;
    }

    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
    }


    /** 
     * Uses the cupcake price and quantity, and calculates the complete price of the orderline.
     * @return 
     */
    public int getTotalPrice(){
        return this.cupcake.getFullPrice() * this.qty;
    }
    
    public Cupcake getCupcake() {
        return cupcake;
    }

    public int getQty() {
        return qty;
    }

    public void setCupcake(Cupcake cupcake) {
        this.cupcake = cupcake;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Cupcake of: " + cupcake + ", " + " quantity: " + qty;
    }
    
    
}

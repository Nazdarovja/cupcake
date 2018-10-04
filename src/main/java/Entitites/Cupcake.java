 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitites;
/**
 * Entity class for a complete Cupcake, built with info from Database (a Topping and a Bottom Entity)
 * @author Stanislav
 */
public class Cupcake {
    private Bottom b = null;
    private Topping t = null;

    public Cupcake(Bottom b, Topping t) {
    this.b = b;
    this.t = t;
    }

    public Bottom getB() {
        return b;
    }

    public Topping getT() {
        return t;
    }

    public void setB(Bottom b) {
        this.b = b;
    }

    public void setT(Topping t) {
        this.t = t;
    }
    /**
     * Gets the combined price for the entire Cupcake object (made of a Topping and a Bottom with individual prices)
     * @return 
     */
    public int getFullPrice() {
        return b.getPrice() + t.getPrice();
    }

    @Override
    public String toString() {
        return b.toString() + ", " + t.toString() + ", price: " + getFullPrice() + ",-";
    }
    
    
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entitites.Bottom;
import Entitites.Cupcake;
import Entitites.Topping;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * DataMapper class to get data from the database, concerning cupcakes.
 * @author Orchi
 */
public class CupcakeMapper {
    /**
     * Get a list of all the available Bottoms, form database
     * @return List of all Bottoms
     * @throws Exception if Connection fails, or something goes wrong with fetching the data form database
     */
    public List<Bottom> getCupcakeBottoms() throws Exception {
        List<Bottom> list = new ArrayList<>();
        Statement stmt = DBConnector.getConnection().createStatement();
        String sql = "SELECT name, price FROM Bottom";
        ResultSet rows = stmt.executeQuery(sql);
        
        while(rows.next()) {
            String name = rows.getString("name");
            int price = rows.getInt("price");
            Bottom bottom = new Bottom(name, price);
            list.add(bottom);
        } 
       return list;
    }
   /**
     * Get a list of all the available Toppings, form database
     * @return List of all Toppings
     * @throws Exception if Connection fails, or something goes wrong with fetching the data form database
     */
    public List<Topping> getCupcakeToppings() throws Exception {
        List<Topping> list = new ArrayList<>();
        Statement stmt = DBConnector.getConnection().createStatement();
        String sql = "SELECT name, price FROM Topping";
        ResultSet rows = stmt.executeQuery(sql);
        
        while(rows.next()) {
            String name = rows.getString("name");
            int price = rows.getInt("price");
            Topping topping = new Topping(name, price);
            list.add(topping);
        } 
       return list;
    }
    /**
     * Gets specific 
     * @param name
     * @return
     * @throws Exception if Connection fails, or something goes wrong with fetching the data form database
     */
    public Bottom getCupcakeBottom(String name) throws Exception {
        List<Bottom> bottoms = getCupcakeBottoms();
        for(Bottom b : bottoms) {
            if(name.equals(b.getName())) 
                return b;
        }
        return null;
    }
    /**
     * 
     * @param name
     * @return
     * @throws Exception if Connection fails, or something goes wrong with fetching the data form database
     */
    public Topping getCupcakeTopping(String name) throws Exception {
        List<Topping> toppings = getCupcakeToppings();
        for(Topping t : toppings) {
            if(name.equals(t.getName())) 
                return t;
        }
        return null;
    }
    
    /**
     * 
     * @param bottomName
     * @param toppingName
     * @return
     * @throws Exception if Connection fails, or something goes wrong with fetching the data form database
     */
    
    
}

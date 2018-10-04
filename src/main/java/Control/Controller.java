/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entitites.Bottom;
import Entitites.ShoppingCart;
import Entitites.Topping;
import Entitites.User;
import Model.CupcakeMapper;
import Model.UserMapper;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet controller for the home.jsp, that redirects the user to the jsp, via a switch cases.
 * @author Mellem
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * Consisting of a switch with the possible cases for the home page (check if the user  already exists, login the user, and redirect them to the shop)
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            UserMapper um = new UserMapper();
            CupcakeMapper cm = new CupcakeMapper();
            String origin = request.getParameter("origin");
            switch (origin) {
                case "registration":
                    // TEST OM USER FINDES I FORVEJEN
                    User user = um.getUser(request.getParameter("username"));
                    if (user == null) {
                        um.addUser(request.getParameter("username"),
                                request.getParameter("password"),
                                request.getParameter("email"));
                        
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    } else {
                        request.setAttribute("error", "");
                        request.getRequestDispatcher("registration.jsp").forward(request, response);
                    }
                    
                    break;
                case "login":
                    // TEST OM USERNAME OG PASSWORD EKSISTERER OG PASSER
                    User u = um.getUser(request.getParameter("username"));
                     if (u == null || !u.getPassword().equals(request.getParameter("password"))) {
                         request.setAttribute("error", "");
                         request.getRequestDispatcher("login.jsp").forward(request, response);
                     } 
                    // VIDERSTIL TIL SHOPPAGEN NÅR LOGIN ER VERIFICERET
                     else {
                         List<Bottom> bottoms = cm.getCupcakeBottoms();
                         List<Topping> toppings = cm.getCupcakeToppings();
                         
                         request.getSession().setAttribute("bottoms", bottoms);
                         request.getSession().setAttribute("toppings", toppings);
                         request.getSession().setAttribute("user", u);
                         request.getSession().setAttribute("shoppingcart", new ShoppingCart());

                         request.getRequestDispatcher("shop.jsp").forward(request, response);
                     }
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
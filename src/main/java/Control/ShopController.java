/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entitites.Bottom;
import Entitites.Cupcake;
import Entitites.Orderline;
import Entitites.ShoppingCart;
import Entitites.Topping;
import Entitites.User;
import Model.CupcakeMapper;
import Model.OrderMapper;
import Model.UserMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Orchi
 */
@WebServlet(name = "ShopController", urlPatterns = {"/ShopController"})
public class ShopController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        CupcakeMapper cm = new CupcakeMapper();
        OrderMapper om = new OrderMapper();
        User user = (User) request.getSession().getAttribute("user");
        ShoppingCart sc = (ShoppingCart) request.getSession().getAttribute("shoppingcart");

        String origin = request.getParameter("origin");
        switch (origin) {
            case "remove":
                int orderLineId = (int) Integer.parseInt(request.getParameter("removal"));
                ShoppingCart newSC = om.removeOrderline(sc, orderLineId);
                request.getSession().setAttribute("shoppingcart", newSC);
                response.sendRedirect("shop.jsp");
                break;

            case "addline":
                String bottomName = (String) request.getParameter("bottom");
                String toppingName = (String) request.getParameter("topping");
                int qty = Integer.parseInt(request.getParameter("quantity"));

                List<Bottom> bottoms = (List<Bottom>) request.getSession().getAttribute("bottoms");
                List<Topping> toppings = (List<Topping>) request.getSession().getAttribute("toppings");
                Orderline ol = new Orderline(completeTheCupcake(bottomName, bottoms, toppingName, toppings), qty);

                ol.setOrderLineId(sc.getShoppingCart().size());

                sc.addLine(ol);

                request.getSession().setAttribute("shoppingcart", sc);
                response.sendRedirect("shop.jsp");
                break;

            case "order":
                String orderID = "";
                if (sc.totalPrice() > user.getBalance()) {
                    request.setAttribute("error", "");
                    request.getRequestDispatcher("shop.jsp").forward(request, response);
                } else {
                    try {
                        UserMapper um = new UserMapper();
                        orderID = "" + om.addOrder(user, sc);
                        //sc = om.getSpecificShoppingCart(orderID);
                        request.getSession().setAttribute("shoppingcart", sc);
                        User u = um.getUser(((User)request.getSession().getAttribute("user")).getUsername());
                        request.getSession().setAttribute("user", u);
                        response.sendRedirect("invoice.jsp");
                        //request.getRequestDispatcher("invoice.jsp").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

            case "invoice":
                String orderId = request.getParameter("specificInvoice");
                try {
                    ShoppingCart cart = om.getSpecificShoppingCart(orderId);
                    request.setAttribute("order", cart);
                    request.getRequestDispatcher("invoiceDetails.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Complete cupcake without diving into the db
     *
     * @param bottomName is the bottem chosen in the orderline
     * @param b is the list from the session containing all of the bottoms
     * @param toppingName is the topping chosen in the orderline
     * @param t is the list from the session containing all of the toppings
     * @return complete cupcake object
     */
    public Cupcake completeTheCupcake(String bottomName, List<Bottom> b, String toppingName, List<Topping> t) {
        Bottom bottomRes = null;
        Topping toppingRes = null;

        for (Bottom i : b) {
            if (i.getName().equals(bottomName)) {
                bottomRes = i;
            }
        }
        for (Topping i : t) {
            if (i.getName().equals(toppingName)) {
                toppingRes = i;
            }
        }
        return new Cupcake(bottomRes, toppingRes);
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

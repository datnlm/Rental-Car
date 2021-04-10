/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.controller;

import dat.dto.CarOrdersDTO;
import dat.dto.CartDTO;
import dat.dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author macbook
 */
@WebServlet(name = "AddController", urlPatterns = {"/AddController"})
public class AddController extends HttpServlet {

    private static final String ERROR = "invalid.html";
    private static final String SUCCESS = "search.jsp";

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
        String url = ERROR;
        try {
            String carID = request.getParameter("txtCarID");
            String carName = request.getParameter("txtCarName");
            String rentalDate = request.getParameter("txtRentalDate");
            String returnDate = request.getParameter("txtReturnDate");
            float price = Float.parseFloat(request.getParameter("txtPrice"));
            int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            CarOrdersDTO dto = new CarOrdersDTO(carID, carName, price, quantity, rentalDate, returnDate);
            if (cart == null) {
                cart = new CartDTO(user.getUserID(), null);
            }
            cart.add(dto);
            session.setAttribute("CART", cart);
            url = SUCCESS;
            request.setAttribute("SUCCESS", "Add successful!");
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.controller;

import dat.daos.CarDAO;
import dat.dto.CarOrdersDTO;
import dat.dto.CartDTO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {

    private static final String URL = "viewCart.jsp";

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
        try {
            String carID = request.getParameter("txtCarID");
            String carName = request.getParameter("txtCarName");
            String rentalDate = request.getParameter("txtRentalDate");
            String returnDate = request.getParameter("txtReturnDate");
            float price = Float.parseFloat(request.getParameter("txtPrice"));
            int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
            if (quantity >= 1) {
                HttpSession session = request.getSession();
                CarDAO dao = new CarDAO();
                int quantityStock = dao.getQuantity(carID, rentalDate, returnDate);
                int quantityCart = 0;
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd");
                Date checkin = dF.parse(rentalDate);
                Date checkout = dF.parse(returnDate);
                boolean flag = false;
                for (CarOrdersDTO dto : cart.getCart()) {
                    Date checkinCart = dF.parse(dto.getRentalDate());
                    Date checkoutCart = dF.parse(dto.getReturnDate());
                    if (carID.equals(dto.getCarID()) && ((checkin.after(checkinCart) && checkin.before(checkoutCart))
                            || (checkout.after(checkinCart) && checkout.before(checkoutCart)))) {
                        quantityCart += dto.getQuantity();
                        flag = true;
                    }
                }
                if (flag) {
                    if ((quantityStock - quantity - quantityCart) >= 0) {
                        cart.update(new CarOrdersDTO(carID, carName, price, quantity, rentalDate, returnDate));
                        session.setAttribute("CART", cart);
                        request.setAttribute("MESSAGE", "Update successful!");
                    } else {
                        request.setAttribute("ERROR", "Sorry, you can only rent " + quantityStock + " " + carName);
                    }
                } else if (quantityStock - quantity >= 0) {
                    cart.update(new CarOrdersDTO(carID, carName, price, quantity, rentalDate, returnDate));
                    session.setAttribute("CART", cart);
                    request.setAttribute("MESSAGE", "Update successful!");
                } else {
                    request.setAttribute("ERROR", "Sorry, you can only rent " + quantityStock + " " + carName);
                }
            }
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            request.getRequestDispatcher(URL).forward(request, response);
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

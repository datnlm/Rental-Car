/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.controller;

import dat.daos.CarDAO;
import dat.daos.CartDAO;
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
@WebServlet(name = "SaveController", urlPatterns = {"/SaveController"})
public class SaveController extends HttpServlet {

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
            HttpSession session = request.getSession();
            String action = request.getParameter("btnAction");
            String code = request.getParameter("txtDiscount");
            CartDAO dao = new CartDAO();
            boolean flag = true;
            if (action.equals("verifyDiscount")) {
                int percent = dao.verifyDiscount(code);
                flag = false;
                if (percent != -1) {
                    session.setAttribute("DISCOUNT", percent);
                    session.setAttribute("DISCOUNT_CODE", code);
                } else {
                    if (session.getAttribute("DISCOUNT") != null) {
                        session.removeAttribute("DISCOUNT");
                        session.removeAttribute("DISCOUNT_CODE");
                    }
                    request.setAttribute("ERROR", "Invalid or expiry code");
                }
            } else if (!code.isEmpty() && session.getAttribute("DISCOUNT") == null) {
                request.setAttribute("ERROR", "Please click a Check Availble to check discount!");
                session.removeAttribute("DISCOUNT");
                session.removeAttribute("DISCOUNT_CODE");
                session.setAttribute("DISCOUNT_CODE", code);
                flag = false;
            } else if (code.isEmpty() && session.getAttribute("DISCOUNT") != null) {
                request.setAttribute("ERROR", "Please click a Check Availble to check discount!");
                session.removeAttribute("DISCOUNT");
                session.removeAttribute("DISCOUNT_CODE");
                session.setAttribute("DISCOUNT_CODE", code);
                flag = false;
            }
            if (flag) {
                float total = Float.parseFloat(request.getParameter("txtTotal"));
                String address = request.getParameter("txtAddress");
                String phone = request.getParameter("txtPhone");
                if (address.length() > 30) {
                    request.setAttribute("ERROR", "The address cannot be over 30 characters");
                } else if (phone.length() > 11) {
                    request.setAttribute("ERROR", "The phone number cannot be over 30 characters");
                } else {
                    int percentOfDiscount = -1;
                    if (session.getAttribute("DISCOUNT") != null) {
                        String tmp = request.getParameter("txtPercentOfDiscount");
                        if (tmp != null) {
                            percentOfDiscount = Integer.parseInt(tmp);
                        }
                    }
                    CartDTO cart = (CartDTO) session.getAttribute("CART");
                    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
                    CarOrdersDTO car = dao.save(cart, user.getUserID(), address, phone, total, code, percentOfDiscount);
                    if (car != null) {
                        CarDAO carDAO = new CarDAO();
                        int quantity = carDAO.getQuantity(car.getCarID(), car.getRentalDate(), car.getReturnDate());
                        request.setAttribute("ERROR", "The " + car.getCarName() + " car is out of stock! You can only rent " + quantity + "cars");
                    } else {
                        if (session.getAttribute("DISCOUNT") != null) {
                            session.removeAttribute("DISCOUNT");
                            session.removeAttribute("DISCOUNT_CODE");
                        }
                        session.removeAttribute("CART");
                        request.setAttribute("MESSAGE", "Orders successful!");
                    }
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

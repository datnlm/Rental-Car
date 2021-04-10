/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.controller;

import dat.daos.CarDAO;
import dat.dto.CarDTO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    private static final String URL = "search.jsp";
    private static final int QUANTITY_CAR = 4;

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
            CarDAO dao = new CarDAO();
            if (session.getAttribute("LIST_CATEGORY") == null) {
                List<String> listCategory = dao.getCategory();
                session.setAttribute("LIST_CATEGORY", listCategory);
            } else {
                String type = "carName";

                String name = request.getParameter("txtName");
                String category = request.getParameter("cbxCategory");
                String rentalDate = request.getParameter("txtRentalDate");
                String returnDate = request.getParameter("txtReturnDate");
                String tmp = request.getParameter("txtQuantity");
                int quantity = -1;
                if (!tmp.isEmpty()) {
                    quantity = Integer.parseInt(tmp);
                }
                boolean flag = true;
                if (!name.trim().isEmpty() && !category.equals("")) {
                    request.setAttribute("ERROR", "Just name or category");
                    flag = false;
                } else if (!category.equals("")) {
                    type = "category";
                    name = category;
                }
                if (!rentalDate.isEmpty() && !returnDate.isEmpty()) {
                    SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar beforeDate = Calendar.getInstance();
                    beforeDate.roll(Calendar.DATE, false);
                    Date checkin = dF.parse(rentalDate);
                    Date checkout = dF.parse(returnDate);
                    if (beforeDate.getTime().after(checkin) || checkin.after(checkout)) {
                        request.setAttribute("ERROR", "Invalid date!");
                        flag = false;
                    }
                } else {
                    request.setAttribute("ERROR", "Please choice date to search");
                    flag = false;
                }
                if (quantity <= 0) {
                    request.setAttribute("ERROR", "Quantity is a positive integer");
                    flag = false;
                }
                if (flag) {
                    int index = 1;
                    String paging = request.getParameter("txtIndex");
                    if (paging != null) {
                        index = Integer.parseInt(paging);
                    }
                    List<CarDTO> listCars = dao.getCars(name, rentalDate, returnDate, quantity, (index - 1) * QUANTITY_CAR, type);
                    int result = dao.getPaging(name, rentalDate, returnDate, quantity, type);
                    request.setAttribute("LIST_CARS", listCars);
                    request.setAttribute("NUMBER_PAGE", result);
                    request.setAttribute("CATEGORY", category);
                    if(listCars.isEmpty()){
                        request.setAttribute("EMPTY_LIST", "Not found");
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

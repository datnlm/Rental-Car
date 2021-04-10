/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.controller;

import dat.daos.UserDAO;
import dat.dto.UserDTO;
import dat.dto.UserErrorDTO;
import dat.utils.EmailUtils;
import java.io.IOException;
import java.util.Random;
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
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    private static final String ERROR = "register.jsp";
    private static final String SUCCESS = "verify.jsp";

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
            String email = request.getParameter("txtEmail");
            String name = request.getParameter("txtName");
            String phone = request.getParameter("txtPhone");
            String address = request.getParameter("txtAddress");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirm");
            UserErrorDTO error = new UserErrorDTO();
            boolean flag = true;
            if (email.trim().isEmpty() || !email.contains("@") || email.length() > 50) {
                flag = false;
                error.setUserIDError("Email can't empty or invalid and length must be less than 50 characters");
            }
            if (name.trim().isEmpty() || name.length() > 24) {
                flag = false;
                error.setNameError("Name must be from 1 to 24 characters ");
            }
            if (phone.trim().isEmpty() || !phone.matches("^[0-9]{11}$")) {
                flag = false;
                error.setPhoneError("Phone must be 11 number");
            }
            if (address.trim().isEmpty() || address.length() > 30) {
                flag = false;
                error.setAdrressError("Address must be from 1 to 30 characters");
            }
            if (password.trim().isEmpty() || password.length() > 50) {
                flag = false;
                error.setPasswordError("Password can't empty or invalid and length must be less than 50 characters");
            }
            if (!confirm.equals(password)) {
                flag = false;
                error.setPasswordError("Does not match the password");
            }
            if (flag) {
                Random rd = new Random();
                String code = String.valueOf(rd.nextInt(100));
                UserDTO user = new UserDTO(email, password, name, phone, address, name, code);
                UserDAO dao = new UserDAO();
                if (EmailUtils.sendMail(email, "Rental car: Verify code!", "Your verify code: " + code)) {
                    if (dao.create(user) == 1) {
                        url = SUCCESS;
                        HttpSession session = request.getSession();
                        session.setAttribute("USER_ID", email);
                    } else {
                        error.setUserIDError("The email already exists");
                        request.setAttribute("ERROR", error);
                    }
                } else {
                    error.setUserIDError("Invalid email!");
                    request.setAttribute("ERROR", error);
                }
            } else {
                request.setAttribute("ERROR", error);
            }
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

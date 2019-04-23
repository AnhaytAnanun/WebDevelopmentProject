/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flycode.coolstore;

import com.flycode.coolstore.utils.DBUtils;
import com.flycode.coolstore.utils.SessionUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anhay
 */
public class MakeReservationServlet extends HttpServlet {

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
        
        if (!SessionUtils.isLoggedIn(request.getSession(false))) {
            SessionUtils.loginFailed(request, response, "Please, login first to make reservations.", false);
            
            return;
        }
                
        try {
            HttpSession session = request.getSession();
            
            int book = Integer.parseInt(request.getParameter("book"));

            Connection connection = DBUtils.connect();
            PreparedStatement checkIfReservedStatement = connection.prepareStatement("SELECT * FROM Ei4gDiB26n.reservations WHERE Ei4gDiB26n.reservations.user_id = ? AND Ei4gDiB26n.reservations.book_id = ? LIMIT 1");
            checkIfReservedStatement.setInt(1, (int) session.getAttribute("userId"));
            checkIfReservedStatement.setInt(2, book);
            ResultSet reservedResults = checkIfReservedStatement.executeQuery();
            
            reservedResults.beforeFirst();
            
            if (reservedResults.next()) {
                reservationResults(request, response, "You have already reserved this book!", false);
                
                reservedResults.close();
                
                return;
            }
            
            reservedResults.close();

            PreparedStatement checkIfAvailableStatement = connection.prepareStatement("SELECT * FROM Ei4gDiB26n.books WHERE Ei4gDiB26n.books.book_id = ? AND Ei4gDiB26n.books.is_available = 1 LIMIT 1");
            checkIfAvailableStatement.setInt(1, book);
            ResultSet availableResults = checkIfAvailableStatement.executeQuery();
            
            availableResults.beforeFirst();
            
            if (!availableResults.next()) {
                reservationResults(request, response, "This book is not available for reservation. Please, try later.", true);
            
                availableResults.close();
                
                return;
            }
            
            availableResults.close();
            
            PreparedStatement reserveStatement = connection.prepareStatement(
                    "INSERT INTO Ei4gDiB26n.reservations (Ei4gDiB26n.reservations.user_id, Ei4gDiB26n.reservations.book_id) VALUES (?, ?)");
            reserveStatement.setInt(1, (int) session.getAttribute("userId"));
            reserveStatement.setInt(2, book);
            int affectedRows = reserveStatement.executeUpdate();
                        
            PreparedStatement makeUnavailableStatement = connection.prepareStatement(
                    "UPDATE Ei4gDiB26n.books SET Ei4gDiB26n.books.is_available = '0' WHERE Ei4gDiB26n.books.book_id = ?");
            makeUnavailableStatement.setInt(1, book);
            affectedRows = affectedRows + makeUnavailableStatement.executeUpdate();
            
            if (affectedRows != 2) {
                reservationResults(request, response, "Something went terribly wrong. Please, try later.", true);
            
                return;
            }
            
            reservationResults(request, response, "Your reservation was successful!", false);
            
        } catch (SQLException ex) {
            Logger.getLogger(MakeReservationServlet.class.getName()).log(Level.SEVERE, null, ex);
            reservationResults(request, response, "Something went terribly wrong. Please, try later.", true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MakeReservationServlet.class.getName()).log(Level.SEVERE, null, ex);
            reservationResults(request, response, "Something went terribly wrong. Please, try later.", true);
        }
    }
    
    private void reservationResults(HttpServletRequest request, HttpServletResponse response, String message, boolean isError) throws IOException {
        HttpSession session = request.getSession(false);
        
        if (isError) {
            session.setAttribute("errorMessage", message);
            session.setAttribute("hasErrorMessage", true);
        } else {
            session.setAttribute("message", message);
            session.setAttribute("hasMessage", true);
        }

        response.sendRedirect("../profile.jsp");
    }
}

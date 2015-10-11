package web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import db.DatabaseConnector;
import db.DatabaseManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import subjectArea.Manufacturer;
import subjectArea.Souvenir;

/**
 *
 * @author Dmitry
 */
public class SouvenirList extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
            DatabaseConnector dbConnector;
            dbConnector = new DatabaseConnector();
            DatabaseManager dbManager = new DatabaseManager(dbConnector.getConnection());
            ResultSet res = dbManager.getSouvenirs();
            
            List souvenirList = new ArrayList<>();
            while(res.next()){
                souvenirList.add(new Souvenir(res.getString("title"),res.getByte("manufacturer_id"),res.getDouble("price"),res.getDate("date")));
            }
            request.setAttribute("size", souvenirList.size());
            request.setAttribute("souvenirArrayList", souvenirList);
            request.getRequestDispatcher("souvenirList.jsp").forward(request, response);
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
        PrintWriter out = response.getWriter();
        String name = request.getParameter("producerName");
        String country = request.getParameter("producerCountry");
        if("any".equals(country) && "any".equals(name)){
            try {
                processRequest(request, response);
            } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            DatabaseConnector dbConnector;
            DatabaseManager dbManager = null;
            try {
            dbConnector = new DatabaseConnector();
                dbManager = new DatabaseManager(dbConnector.getConnection());
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet res = null;
            if((country == null && name == null) || "any".equals(country) || "any".equals(name)){
                try {
                    processRequest(request, response);
                } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(!"any".equals(country) && null != country){
                try {
                    res = dbManager.getSouvenirsByCountry(country);
                    List souvenirList = new ArrayList<>();
                    while(res.next()){
                        souvenirList.add(new Souvenir(res.getString("title"),res.getByte("manufacturer_id"),res.getDouble("price"),res.getDate("date")));
                    }
                    request.setAttribute("size", souvenirList.size());
                    request.setAttribute("souvenirArrayList", souvenirList);
                    request.getRequestDispatcher("souvenirList.jsp").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(!"any".equals(name) && null != name){
                try {
                    res = dbManager.getSouvenirsByManufacturers(name);
                    List souvenirList = new ArrayList<>();
                    while(res.next()){
                        souvenirList.add(new Souvenir(res.getString("title"),res.getByte("manufacturer_id"),res.getDouble("price"),res.getDate("date")));
                    }
                    request.setAttribute("size", souvenirList.size());
                    request.setAttribute("souvenirArrayList", souvenirList);
                    request.getRequestDispatcher("souvenirList.jsp").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                processRequest(request, response);
            } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            DatabaseConnector dbConnector;
            PrintWriter out = response.getWriter();
        try {
            dbConnector = new DatabaseConnector();
            DatabaseManager dbManager = new DatabaseManager(dbConnector.getConnection());
            request.getParameter("Date");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateInString = request.getParameter("Date");
            Date date = new Date();
            date = formatter.parse(dateInString);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            
            out.println(date);
            int ProducerID = Integer.parseInt(request.getParameter("Producer_ID"));
            double price = Double.parseDouble(request.getParameter("Price"));
            Souvenir temp = new Souvenir(request.getParameter("Name"),ProducerID,price,sqlDate);
            dbManager.insertSouvenir(temp);
            dbConnector.closeConnection();
            processRequest(request, response);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProducerList.class.getName()).log(Level.SEVERE, null, ex);
            out.println(1);
        } catch (InstantiationException ex) {
            Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
            out.println(3);
        } catch (SQLException ex) {
            Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
            out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
            out.println(5);
        } catch (ParseException ex) {
            Logger.getLogger(SouvenirList.class.getName()).log(Level.SEVERE, null, ex);
            out.println(ex.getMessage());
        }
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

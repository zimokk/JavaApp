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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry
 */
public class AddProducer extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
                out.println("<head>");
                    out.println("<title>Add new producer</title>");
                    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../bootstrap.css\">");
                    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../bootstrap-theme.css\">");
                    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../style/style.css\">");
                out.println("</head>");
            out.println("<body>");
            out.println("<h1 class=\"alert alert-info\" align=\"center\">Add new producer</h1>");
                out.println("<form name=\"AddProducer\" action=\"..\\producer\" method=\"POST\">\n" +
                                "<input type=\"text\" style=\"position:relative ;width:450px; left:10px\" class=\"form-control\" name=\"ProducerName\" placeholder=\"Enter name..\" required />\n" +
                                "<input type=\"text\" style=\"position:relative; width:450px; left:10px\" class=\"form-control\" name=\"ProducerCountry\" placeholder=\"Enter country..\" required/>\n" +
                            "<input style=\"position:relative; left:10px;\" type=\"submit\" value=\"Add producer\"  class=\"btn btn-success\" />\n" +
                        "</form>");
                out.println("<div id=\"wrapper\">\n" +
"                <a href=\"../producer\"><input type=\"button\" value=\"View Producers\" class=\"btn btn-primary\" id=\"view_producer_button\"></a>\n" +
"                <a href=\"../producer/add\"><input type=\"button\" value=\"Add producer\" class=\"btn btn-primary\" id=\"add_producer_button\"></a>\n" +
"                <a href=\"../souvenir\"><input type=\"button\" value=\"View Souvenirs\" class=\"btn btn-primary\" id=\"view_souvenirs_button\"></a>\n" +
"                <a href=\"../souvenir/add\"><input type=\"button\" value=\"Add Souvenir\" class=\"btn btn-primary\" id=\"add_souvenirs_button\"></a>\n" +
"                <a href=\"../\"><input type=\"button\" value=\"Main\"  class=\"btn btn-primary\" id=\"main_button\" /></a>\n" +
"            <form action=\"..\\souvenir\" id=\"find_producer_name\">\n" +
"                <label><h4>Search products by producer</h4></label>\n" +
"                <select name=\"producerName\"  class=\"form-control\" style=\"position: relative; width:230px; top:0px\" >\n" +
"                    <option value=\"any\" selected>any</option>\n");
                DatabaseConnector dbConnector = new DatabaseConnector();
                DatabaseManager dbManager = new DatabaseManager(dbConnector.getConnection());
                ResultSet res = dbManager.getManufacturers();
                while(res.next()){
                    out.println("<option value = \""+ res.getString("title") +"\">"+ res.getString("title") +"</option>");
                }
                out.println(
"                </select>\n" +
"                <input type=\"submit\" class=\"btn btn-success\" value=\"Find\" style=\"position: relative; left:230px; top:-33px\" />\n" +
"            </form>\n" +
"            <form action=\"..\\souvenir\" id=\"find_producer_country\">\n" +
"                <label><h4>Search products by country</h4></label>\n" +
"                <select name=\"producerCountry\"  class=\"form-control\" style=\"position: relative; width:230px; top:0px\" >\n" +
"                    <option value=\"any\" selected>any</option>\n");
                
                res = dbManager.getManufacturers();
                while(res.next()){
                    out.println("<option value = \""+ res.getString("country") +"\">"+ res.getString("country") +"</option>");
                }
                out.println(
"                </select>\n" +
"                <input type=\"submit\" value=\"Find\"  class=\"btn btn-success\" value=\"Find\" style=\"position: relative; left:230px; top:-33px\"/>\n" +
"            </form>\n" +
"            <form action=\"..\\producer\" id=\"find_souvenir_name\">\n" +
"                <label><h4>Search producer by product and year</h4></label>\n" +
"                <select name=\"souvenirName\"   class=\"form-control\" style=\"position: relative; width:140px; top:0px\">\n");
                res = dbManager.getSouvenirs();
                while(res.next()){
                    out.println("<option value = \""+ res.getString("title") +"\">"+ res.getString("title") +"</option>");
                }
                out.println(
"                </select>\n" +
"                <input type=\"number\"  class=\"form-control\" style=\"position: relative; width: 75px; left:145px; top:-33px \" max=\"2015\" min=\"1900\" name=\"year\" required>\n" +
"                <input type=\"submit\" value=\"Find\"   class=\"btn btn-success\"  style=\"position: relative; left:230px; top:-66px\" />\n");
                dbConnector.closeConnection();
                out.println(
"            </form>\n" +
"            <form action=\"..\\producer\" id=\"find_souvenir_price\">\n" +
"                <label><h4>Search producer with price <</h4></label>\n" +
"                <input type=\"number\" min=\"0\" step=\"any\" name=\"souvenirPrice\" class=\"form-control\" style=\"position: relative; width:230px; top:0px\"  required>\n" +
"                <input type=\"submit\" value=\"Find\"  class=\"btn btn-success\"  style=\"position: relative; left:230px; top:-33px\">\n" +
"            </form>\n" +
"        </div>");
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddProducer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddProducer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AddProducer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddProducer.class.getName()).log(Level.SEVERE, null, ex);
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

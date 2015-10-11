<%@page import="java.io.PrintWriter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="db.DatabaseManager"%>
<%@page import="db.DatabaseConnector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="bootstrap.css">
        <link rel="stylesheet" type="text/css" href="bootstrap-theme.css">
        <link rel="stylesheet" type="text/css" href="style/style.css">
        <title>Souvenirs</title>
    </head>
    <body>
        <h1 class="alert alert-info" align="center">
            Welcome to Souvenirs
        </h1>
        <div id="wrapper">
                <a href="../JavaL/producer"><input type="button" value="View Producers" class="btn btn-primary" id="view_producer_button"></a>
                <a href="../JavaL/producer/add"><input type="button" value="Add producer" class="btn btn-primary" id="add_producer_button"></a>
                <a href="../JavaL/souvenir"><input type="button" value="View Souvenirs" class="btn btn-primary" id="view_souvenirs_button"></a>
                <a href="../JavaL/souvenir/add"><input type="button" value="Add Souvenir" class="btn btn-primary" id="add_souvenirs_button"></a>
                <a href="../JavaL"><input type="button" value="Main"  class="btn btn-primary" id="main_button" /></a>
            <form action="souvenir" id="find_producer_name">
                <label><h4>Search products by producer</h4></label>
                <select name="producerName"  class="form-control" style="position: relative; width:230px; top:0px" >
                    <option value="any" selected>any</option>
                    <%  DatabaseConnector dbConnector;
                        dbConnector = new DatabaseConnector();
                        DatabaseManager dbManager = new DatabaseManager(dbConnector.getConnection());
                        ResultSet res = dbManager.getManufacturers();
                        while(res.next()){
                            out.println("<option value = \""+ res.getString("title") +"\">"+ res.getString("title") +"</option>");
                        }
                    %>
                </select>
                <input type="submit" class="btn btn-success" value="Find" style="position: relative; left:230px; top:-33px" />
            </form>
            <form action="souvenir" id="find_producer_country">
                <label><h4>Search products by country</h4></label>
                <select name="producerCountry"  class="form-control" style="position: relative; width:230px; top:0px" >
                    <option value="any" selected>any</option>
                    <%  res = dbManager.getManufacturers();
                        while(res.next()){
                            out.println("<option value = \""+ res.getString("country") +"\">"+ res.getString("country") +"</option>");
                        }
                    %>
                </select>
                <input type="submit" value="Find"  class="btn btn-success" value="Find" style="position: relative; left:230px; top:-33px"/>
            </form>
            <form action="producer" id="find_souvenir_name">
                <label><h4>Search producer by product and year</h4></label>
                <select name="souvenirName"   class="form-control" style="position: relative; width:140px; top:0px">
                    <%  res = dbManager.getSouvenirs();
                        while(res.next()){
                            out.println("<option value = \""+ res.getString("title") +"\">"+ res.getString("title") +"</option>");
                        }
                    %>
                </select>
                <input type="number"  class="form-control" style="position: relative; width: 75px; left:145px; top:-33px " max="2015" min="1900" name="year">
                <input type="submit" value="Find"   class="btn btn-success"  style="position: relative; left:230px; top:-66px" />
                <%
                    dbConnector.closeConnection();
                %>
            </form>
            <form action="producer" id="find_souvenir_price">
                <label><h4>Search producer with price <</h4></label>
                <input type="number" min="0" step="any" name="souvenirPrice" class="form-control" style="position: relative; width:230px; top:0px"  required>
                <input type="submit" value="Find"  class="btn btn-success"  style="position: relative; left:230px; top:-33px">
            </form>
        </div>
    </body>
</html>

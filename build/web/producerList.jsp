<%@page import="db.DatabaseManager"%>
<%@page import="db.DatabaseConnector"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page import="subjectArea.Manufacturer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>ProducerList</title>
        <link rel="stylesheet" type="text/css" href="bootstrap.css">
        <link rel="stylesheet" type="text/css" href="bootstrap-theme.css">
        <link rel="stylesheet" type="text/css" href="style/style.css">
        <script  language="JavaScript" src="jquery-2.1.4.min.js" type="text/javascript" ></script>
        <script  language="JavaScript" src="jquery.tablesorter.js" type="text/javascript" ></script>
    </head>
    <script>
        $(document).ready(function() {
            $("table").tablesorter({
                sortList: [[0,0]] 
            }); 
        }); 
    </script>
    <body>
        <h1 class="alert alert-info" align="center">Producer list${price}</h1>
        <table id="myTable" cellpadding="2" style="width: 40%" class="tablesorter table-hover">
            <thead> 
                <tr>
                    <th><button style="width:100%" class="btn btn-default">Name</button></th> 
                    <th><button style="width:100%" class="btn btn-default">Country</button></th> 
                    <th><button style="width:100%" class="btn btn-default">Delete</button></th>
                </tr> 
            </thead> 
            <tbody>
                <%
                    ArrayList<Manufacturer> list = (ArrayList<Manufacturer>)request.getAttribute("producerArrayList");
                    for(int i = 0; i < (Integer)request.getAttribute("size") ;i++){
                    String deleteName = list.get(i).getTitle();
                %>
                <tr>
                    <%
                        out.println("<th style=\"text-align:center\">"+deleteName+"</th>");
                        out.println("<th style=\"text-align:center\">"+list.get(i).getCountry()+"</th>");
                    %>
                    <th style="text-align:center">
                        <form method="POST" action="./producer" name="DeleteProducer">
                            <%
                                out.println("<input type=\"hidden\" name=\"deleteName\" value="+deleteName+">");
                            %>
                            <input  style="width:100%" class="btn btn-default" type="submit" value="Delete" />
                        </form>
                    </th>
                </tr>         
                <%
                    };
                %>
            </tbody>
        </table>
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
                <label><h4>Search producer by pruduct and year</h4></label>
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

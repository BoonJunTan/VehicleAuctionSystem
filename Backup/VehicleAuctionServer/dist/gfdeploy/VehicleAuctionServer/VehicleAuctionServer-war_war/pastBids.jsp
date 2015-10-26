<%-- 
    Document   : pastBids
    Created on : Oct 26, 2015, 6:18:32 PM
    Author     : Tan Boon Jun, A0125418J
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Past Bids</title>
        <link href="../css/bootstrap.min.css" rel="stylesheet">
        <title>View Past Bids - Page Redirection</title>
    </head>
    <body>
    <br>
    <div class="container-fluid">
        <div class="panel panel-primary">
            <div class="panel-heading" align="center">
                <h4><b><font color="white">View Past Bids </font> </b></h4>
            </div>
            <div class="panel-body"align="center">
                  
                <div class="container " style="margin-top: 5%; margin-bottom: 5%;">
    
                    <div class="panel panel-primary" style="max-width: 70%;" align="left">
                        
                        <div class="panel-heading form-group">
                            <b><font color="white">
                                View Past Bids Page</font> </b>
                        </div>
                    
                        <div class="panel-body" align="center">
                            <%
                                ArrayList data = (ArrayList)request.getAttribute("data");
                                
                                if (data.size() == 2) {
                                    out.print("<p>Status: " + data.get(0) + ". " + data.get(1) + "</p>");
                                    out.print("<p>Redirecting back to home page in 3 seconds...");
                                    response.setHeader("Refresh", "3; URL=../member.jsp"); 
                                } else {
                                    ArrayList bids = (ArrayList) data.get(0);
                                    out.print("<form name='moreInfoForm' method='POST' action='vehicleDetailedStatus'>");
                                    out.print("<table class='table table-bordered'>");
                                    out.print("<tr class='success'><td></td><td>Make</td><td>Model</td><td>Manufactured Year</td><td>Bid Value</td><td>Bid Time</td><td>Auction State</td></tr>");
                                    
                                    for (int i = 0; i < bids.size(); i++) {
                                        String [] currentVehicle = (String [])bids.get(i);
                                        out.print("<tr>");
                                        if (i == 0) {
                                            out.print("<td cellpadding='5'><div class='radio'><label><input type='radio' name='txtNo2' value='" + i + "' checked=true></label></div></td>");
                                        } else {
                                            out.print("<td cellpadding='5'><div class='radio'><label><input type='radio' name='txtNo2' value='" + i + "'></label></div></td>");
                                        }
                                        out.print("<td cellpadding='5'>" + currentVehicle[0] + "</td>");
                                        out.print("<td cellpadding='5'>" + currentVehicle[1] + "</td>");
                                        out.print("<td cellpadding='5'>" + currentVehicle[2] + "</td>");
                                        out.print("<td cellpadding='5'>" + currentVehicle[3] + "</td>");
                                        out.print("<td cellpadding='5'>" + currentVehicle[4] + "</td>");
                                        out.print("<td cellpadding='5'>" + currentVehicle[5] + "</td>");
                                        out.print("</tr>");
                                    }
                                    
                                    out.print("</table>");
                                    out.print("<button type='submit' value='submit' style='width: 100%; font-size:1.1em;' class='btn btn-large btn btn-primary btn-lg btn-block' ><b>More Info!</b></button>");
                                    out.print("</form>");
                                }
                            %>
                            
                        </div>
                    </div>
                    
                </div>
                
            </div>
            <div class="panel-footer" align="center"><font style="color: #111"> Perfecting your experience with us right now! </font></div>
        </div>
    </div>
    </body>
</html>
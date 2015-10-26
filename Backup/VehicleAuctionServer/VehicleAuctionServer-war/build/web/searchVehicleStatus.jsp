<%-- 
    Document   : searchVehicleStatus
    Created on : Oct 24, 2015, 9:28:36 PM
    Author     : Tan Boon Jun, A0125418J
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Vehicle Status</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <title>Search Vehicle Status - Page Redirection</title>
</head>
<body>
<br>
<div class="container-fluid">
    <div class="panel panel-primary">
        <div class="panel-heading" align="center">
            <h4><b><font color="white">Search Vehicle Status </font> </b></h4>
        </div>
        <div class="panel-body"align="center">

            <div class="container " style="margin-top: 15%; margin-bottom: 15%;">

                <div class="panel panel-primary" style="max-width: 100%;" align="left">

                    <div class="panel-heading form-group">
                        <b><font color="white">
                            Search Vehicle Status Page</font> </b>
                    </div>

                    <div class="panel-body" align="center">
                        <%
                            ArrayList data = (ArrayList)request.getAttribute("data");
                            if (data.size() == 2) { // Means nothing
                        %>
                                <p>Status: <%= (String)data.get(0) + "\n" + (String)data.get(1)%></p>
                                Redirecting back to home page in 4 seconds...

                        <% 
                                response.setHeader("Refresh", "4; URL=../member.jsp");
                            } else {
                        %>
                                <form name="getDetailedVehicleForm" method="POST" action="vehicleDetailedStatus">
                                <table class="table table-bordered">
                        <%
                                ArrayList vehicles = (ArrayList) data.get(0);

                                out.print("<tr class='success'><td></td><td>Make</td><td>Model</td><td>Year</td><td>Description</td><td>Auction Status</td><td>Time Left/Ended</td><td>Starting Price</td><td>Current Bid/Closing Price</td></tr>");

                                for (int i = 0; i < vehicles.size(); i++) {
                                    String [] currentVehicle = (String [])vehicles.get(i);
                                    out.print("<tr>");
                                    if (i == 0) {
                                        out.print("<td cellpadding='5'><div class='radio'><label><input type='radio' name='txtNo' value='" + i + "' checked=true></label></div></td>");
                                    } else {
                                        out.print("<td cellpadding='5'><div class='radio'><label><input type='radio' name='txtNo' value='" + i + "'></label></div></td>");
                                    }
                                    out.print("<td cellpadding='5'>" + currentVehicle[0] + "</td>");
                                    out.print("<td cellpadding='5'>" + currentVehicle[1] + "</td>");
                                    out.print("<td cellpadding='5'>" + currentVehicle[2] + "</td>");
                                    out.print("<td cellpadding='5'>" + currentVehicle[3] + "</td>");
                                    out.print("<td cellpadding='5'>" + currentVehicle[4] + "</td>");
                                    out.print("<td cellpadding='5'>" + currentVehicle[5] + "</td>");
                                    out.print("<td cellpadding='5'>" + currentVehicle[6] + "</td>");
                                    out.print("<td cellpadding='5'>" + currentVehicle[7] + "</td>");
                                    out.print("</tr>");
                                }
                        %>
                                </table>
                                <button type="submit" value="submit" style="width: 100%; font-size:1.1em;" class="btn btn-large btn btn-primary btn-lg btn-block" ><b>More Info!</b></button>
                                </form>
                        <%
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

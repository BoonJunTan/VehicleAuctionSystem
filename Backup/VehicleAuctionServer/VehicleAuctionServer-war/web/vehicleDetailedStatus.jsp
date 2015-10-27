<%-- 
    Document   : vehicleDetailedStatus
    Created on : Oct 25, 2015, 8:37:46 PM
    Author     : Tan Boon Jun, A0125418J
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Detailed Vehicle Status</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <title>Search Detailed Vehicle Status - Page Redirection</title>
    <script type="text/javascript">
        function validation() {
            var currentBid = document.bidForVehicleForm.txtCurrentBid.value;
            var bidAmount = document.bidForVehicleForm.txtBidAmount.value;
            
            var errorMessage = "The following contains error: \n";
            var error = false;
            
            if (bidAmount == null || bidAmount.trim() == "") {
                errorMessage = errorMessage +  "-> Bid is empty\n";
                error = true;
            }
            
            if (bidAmount < currentBid.substring(1)) {
                errorMessage = errorMessage +  "-> Bid must meet min bid\n";
                error = true;
            }
            
            if (error == false) {
                return true;
            } else {
                alert(errorMessage);
                return false;
            }
        }
    </script>
</head>
<body>
<br>
<div class="container-fluid">
    <div class="panel panel-primary">
        <div class="panel-heading" align="center">
            <h4><b><font color="white">Search Detailed Vehicle Status </font> </b></h4>
        </div>
        <div class="panel-body"align="center">

            <div class="container " style="margin-top: 2.5%; margin-bottom: 2.5%;">

                <div class="panel panel-primary" style="max-width: 100%;" align="left">

                    <div class="panel-heading form-group">
                        <b><font color="white">
                            Search Vehicle Status Page</font> </b>
                    </div>

                    <div class="panel-body" align="center">
                        <%
                        Map <String, String> detailedVehicles = (Map <String, String>)request.getAttribute("data");
                        
                        if (detailedVehicles.get("status") == "Open") {
                            out.print("<form name='bidForVehicleForm' method='POST' action='bidVehicleStatus' onsubmit='return validation()'>");
                        }
                        out.print("<table class='table table-bordered'>");
                        out.print("<tr class='success'><td>Make</td><td>Model</td><td>Year</td><td>Description</td><td>Registration Number</td><td>Chassis Number</td><td>Engine Number</td><td>Auction Status</td><td>Time Left/Ended</td><td>Starting Price</td><td>Current Bid/Closing Price</td></tr>");
                        out.print("<tr>");
                        
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("make") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("model") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("year") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("description") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("registration") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("chassis") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("engine") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("status") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("remaining") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("startBid") + "</td>");
                        out.print("<td cellpadding='5'>" + detailedVehicles.get("currentBid") + "</td>");
                        out.print("</tr>");
                        
                        out.print("</table><br>");
                        
                        out.print("<table class='table table-bordered'>");
                        if (detailedVehicles.get("certificate") != null) {
                            out.print("<tr class='success'><td>Certifier</td><td>Time</td><td>Description</td></tr><tr>");
                            out.print("<tr><td cellpadding='5'>" + detailedVehicles.get("certificateName") + "</td>");
                            out.print("<td cellpadding='5'>" + detailedVehicles.get("certificateTime") + "</td>");
                            out.print("<td cellpadding='5'>" + detailedVehicles.get("certificateContent") + "</td></tr>");
                        } else {
                            out.print("<tr class='success'><td>No Ceritification</td></tr>");
                        }
                        
                        out.print("</table><br>");
                        
                        out.print("<table class='table table-bordered'>");
                        if (detailedVehicles.get("bidSize") != null) {
                            out.print("<tr class='success'><td>Bidder</td><td>Bid Time</td><td>Value</td></tr><tr>");
                            for (int i = 0; i < Integer.valueOf(detailedVehicles.get("bidSize")); i++) {
                                String bidder = "bidder" + i;
                                String bidTime = "bidTime" + i;
                                String bidAmount = "bidAmount" + i;
                                out.print("<tr><td>" + detailedVehicles.get(bidder) + "</td>");
                                out.print("<td>" + detailedVehicles.get(bidTime) + "</td>");
                                out.print("<td>" + detailedVehicles.get(bidAmount) + "</td></tr>");
                            }
                        } else {
                            out.print("<tr class='success'><td>No Bidder</td></tr>");
                        }
                        out.print("</table><br>");
                        out.print("</table>");
                        if (detailedVehicles.get("status") == "Open") {
                            out.print("<input type='hidden' name='txtCurrentBid' id='txtCurrentBid' value='" + detailedVehicles.get("highestBid") + "'>");
                            out.print("<input type='hidden' name='txtVehicleId' id='txtVehicleId' value='" + detailedVehicles.get("vehicleId") + "'>");
                            out.print("<div class='form-group' width=50% >");
                            out.print("<label for='exampleInputBidAmount'>Bid Amount, *Min Amount: " + detailedVehicles.get("highestBid") + "</label> <input type='text' width=50% class='form-control' name='txtBidAmount' id='txtBidAmount' placeholder='Please Enter Your Bid' required='required'>");
                            out.print("</div>");
                            out.print("<button type='submit' value='submit' style'width: 100%; font-size:1.1em;' class='btn btn-large btn btn-primary btn-lg btn-block' ><b>Bid Now!</b></button>");
                            out.print("</form>");
                        } else {
                            out.print("<p><a href='../member.jsp'>Back to Home</a></p> ");
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


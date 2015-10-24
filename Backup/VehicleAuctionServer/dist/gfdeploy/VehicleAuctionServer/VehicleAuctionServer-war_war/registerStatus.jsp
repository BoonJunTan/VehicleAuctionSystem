<%-- 
    Document   : registerStatus
    Created on : Oct 24, 2015, 1:56:49 PM
    Author     : Tan Boon Jun, A0125418J
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Status</title>
        <link href="../css/bootstrap.min.css" rel="stylesheet">
        <meta http-equiv="refresh" content="3; url=../index.jsp" />
        <title>Register Status - Page Redirection</title>
    </head>
    <body>
    <br>
    <div class="container-fluid">
        <div class="panel panel-primary">
            <div class="panel-heading" align="center">
                <h4><b><font color="white">Register Status </font> </b></h4>
            </div>
            <div class="panel-body"align="center">
                  
                <div class="container " style="margin-top: 15%; margin-bottom: 15%;">
    
                    <div class="panel panel-primary" style="max-width: 35%;" align="left">
                        
                        <div class="panel-heading form-group">
                            <b><font color="white">
                                Register Status Page</font> </b>
                        </div>
                    
                        <div class="panel-body" align="center">
                            <%
                                ArrayList data = (ArrayList)request.getAttribute("data");
                            %>
                            <p>Status: <%= (String)data.get(0)%></p>
                            Redirecting back to home page in 3 seconds...
                        </div>
                    </div>
                    
                </div>
                
            </div>
            <div class="panel-footer" align="center"><font style="color: #111"> Perfecting your experience with us right now! </font></div>
        </div>
    </div>
    </body>
</html>
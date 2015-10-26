<%-- 
    Document   : member.jsp
    Created on : Oct 24, 2015, 8:01:05 PM
    Author     : Tan Boon Jun, A0125418J
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vehicle Auction System</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="css/stylish-portfolio.css" rel="stylesheet">

        <!-- Custom Fonts 
        <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
    -->
        </head>
    <body>
        <!-- Navigation -->
        <a id="menu-toggle" href="#" class="btn btn-dark btn-lg toggle"><i class="fa fa-bars"></i></a>
        <nav id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <a id="menu-close" href="#" class="btn btn-light btn-lg pull-right toggle"><i class="fa fa-times"></i></a>
                <li class="sidebar-brand">
                    <a href="#top"  onclick = $("#menu-close").click(); >Vehicle Auction System</a>
                </li>
                <li>
                    <a href="#top" onclick = $("#menu-close").click(); >Home</a>
                </li>
                <li>
                    <a href="#about" onclick = $("#menu-close").click(); >About Us</a>
                </li>
                <li>
                    <a href="searchVehicle" onclick = $("#menu-close").click(); >Search Vehicle</a>
                </li>
                <li>
                    <a href="vehicleAuction/pastBids" onclick = $("#menu-close").click(); >Past Bid</a>
                </li>
                <li>
                    <a href="vehicleAuction/pastWonAuction" onclick = $("#menu-close").click(); >Past Won Auction</a>
                </li>
                <li>
                    <a href="updateProfile" onclick = $("#menu-close").click(); ><%= session.getAttribute("username") %>'s Profile</a>
                </li>
                <li>
                    <a href="vehicleAuction/logoutStatus" onclick = $("#menu-close").click(); >Logout </a>
                </li>
            </ul>
        </nav>
        <header id="top" class="header">
            <div class="text-vertical-center">
                <h1>Vehicle Auction System</h1>
                <%
                    if (session.getAttribute("username") == null) {
                        response.setHeader("Refresh", "3; URL=index.jsp"); 
                        %>
                        <h3>You are not allow here! Redirect to home page</h3>
                        <%
                    } else if (session.getAttribute("username") != null){
                        %>
                        <h3>(Member) Bid for your DREAM car with us right now!</h3>
                        <br>
                        <a href="updateProfile" class="btn btn-dark btn-lg">Update Profile</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="searchVehicle" class="btn btn-dark btn-lg">Search Vehicle</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="vehicleAuction/pastBids" class="btn btn-dark btn-lg">Past Bids</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="vehicleAuction/pastWonAuction" class="btn btn-dark btn-lg">Past Won Auction</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="vehicleAuction/logoutStatus" class="btn btn-dark btn-lg">Logout</a>
                        <%
                    }
                %>
            </div>
        </header>
        
        <script src="js/jquery.js" type="text/javascript"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
        <!-- Custom Theme JavaScript -->
        <script>
        // Closes the sidebar menu
        $("#menu-close").click(function(e) {
            e.preventDefault();
            $("#sidebar-wrapper").toggleClass("active");
        });

        // Opens the sidebar menu
        $("#menu-toggle").click(function(e) {
            e.preventDefault();
            $("#sidebar-wrapper").toggleClass("active");
        });

        // Scrolls to the selected menu item on the page
        $(function() {
            $('a[href*=#]:not([href=#])').click(function() {
                if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') || location.hostname == this.hostname) {

                    var target = $(this.hash);
                    target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                    if (target.length) {
                        $('html,body').animate({
                            scrollTop: target.offset().top
                        }, 1000);
                        return false;
                    }
                }
            });
        });
        </script>
    </body>
</html>

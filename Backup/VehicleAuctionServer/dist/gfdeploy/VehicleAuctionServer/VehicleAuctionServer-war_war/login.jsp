<%-- 
    Document   : login
    Created on : Oct 23, 2015, 11:47:03 PM
    Author     : Tan Boon Jun, A0125418J
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">
        function validation() {
            var password = document.loginForm.txtPass.value;
            var username = document.loginForm.txtUserName.value;
            
            var errorMessage = "The following contains error: \n";
            var error = false;
            
            if (username == null || username.trim() == "") {
                errorMessage = errorMessage +  "-> Username is empty\n";
                error = true;
            }
            
            if (password == null || password.trim() == "") {
                errorMessage = errorMessage + "-> Password is empty\n";
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
                <h4><b><font color="white">Login Page </font> </b></h4>
            </div>
            <div class="panel-body"align="center">
                  
                <div class="container " style="margin-top: 9.5%; margin-bottom: 9.5%;">
    
                    <div class="panel panel-primary" style="max-width: 35%;" align="left">
                        
                        <div class="panel-heading form-group">
                            <b><font color="white">
                                Login Form</font> </b>
                        </div>
                    
                        <div class="panel-body" >

                        <form action="vehicleAuction/loginStatus" method="POST" name="loginForm" onsubmit="return validation()">
                            <div class="form-group">
                                <label for="exampleInputEmail1">User Name</label> <input
                                    type="text" class="form-control" name="txtUserName" id="txtUserName"
                                    placeholder="Please Enter Your User Name" required="required">
                                    
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">Password</label> <input
                                    type="password" class="form-control" name="txtPass" id="txtPass"
                                    placeholder="Please Enter Your Password" required="required">
                            </div>
                            <button type="submit" style="width: 100%; font-size:1.1em;" class="btn btn-large btn btn-primary btn-lg btn-block" ><b>Login</b></button>
                                                   
                        </form>

                        </div>
                    </div>
                    
                </div>
                
            </div>
            <div class="panel-footer" align="center"><font style="color: #111"> Perfecting your experience with us right now! </font></div>
        </div>
    </div>
    
</body>
</html>

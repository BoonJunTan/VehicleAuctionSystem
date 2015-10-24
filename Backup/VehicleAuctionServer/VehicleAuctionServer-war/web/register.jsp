<%-- 
    Document   : register
    Created on : Oct 23, 2015, 11:47:03 PM
    Author     : Tan Boon Jun, A0125418J
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sign Up</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">
        function validation() {
            var password = document.registerForm.txtPass.value;
            var username = document.registerForm.txtUserName.value;
            var confirmPassword = document.registerForm.txtConfirmPass.value;
            var email = document.registerForm.txtEmail.value;
            var contact = document.registerForm.txtContactNumber.value;
            
            var errorMessage = "The following contains error: \n";
            var error = false;
            
            if (username == null || username.trim() == "") {
                errorMessage = errorMessage +  "-> Username is empty\n";
                error = true;
            }
            
            if (password == null || password.trim() == "" || confirmPassword == null || confirmPassword.trim() == "" || password != confirmPassword ) {
                errorMessage = errorMessage + "-> Password is invalid (Not same or one of the field is empty)\n";
                error = true;
            }
            
            if (email == null || email.trim() == "") {
                errorMessage += "-> Email is empty\n";
                error = true;
            }
            
            if (contact == null || contact.trim() == "") {
                errorMessage += "-> Contact is empty\n"
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
                <h4><b><font color="white">Sign Up Page </font> </b></h4>
            </div>
            <div class="panel-body"align="center">
                  
                <div class="container " style="margin-top: 1%; margin-bottom: 0%;">
    
                    <div class="panel panel-primary" style="max-width: 35%;" align="left">
                        
                        <div class="panel-heading form-group">
                            <b><font color="white">
                                Sign Up Form</font> </b>
                        </div>
                    
                        <div class="panel-body" >

                            <form name="registerForm" method="POST" action="vehicleAuction/registerStatus"  onsubmit="return validation()">
                
                                <div class="form-group">
                                    <label for="exampleInputEmail1">User Name</label> <input
                                        type="text" class="form-control" name="txtUserName" id="txtUserName"
                                        placeholder="Enter Your User Name" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Password</label> <input
                                        type="password" class="form-control" name="txtPass" id="txtPass"
                                        placeholder="Enter Your Password" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword2">Confirmation Password</label> <input
                                        type="password" class="form-control" name="txtConfirmPass" id="txtConfirmPass"
                                        placeholder="Enter Your Confirmation Password" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputContactNumber">Contact Number</label> <input
                                        type="text" class="form-control" name="txtContactNumber" id="txtContactNumber"
                                        placeholder="Enter Your Contact Number" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail">Email</label> <input
                                        type="text" class="form-control" name="txtEmail" id="txtEmail"
                                        placeholder="Enter Your Email" required="required">
                                </div>
                                <button type="submit" value="submit" style="width: 100%; font-size:1.1em;" class="btn btn-large btn btn-primary btn-lg btn-block" ><b>Register</b></button>

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

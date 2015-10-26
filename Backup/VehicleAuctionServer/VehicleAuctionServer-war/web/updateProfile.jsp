<%-- 
    Document   : updateProfile
    Created on : Oct 24, 2015, 8:50:05 PM
    Author     : Tan Boon Jun, A0125418J
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Update Profile</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">
        function validation() {
            var password = document.updateProfileForm.txtPass.value;
            var oldpassword = document.updateProfileForm.txtOldPass.value;
            var confirmPassword = document.updateProfileForm.txtConfirmPass.value;
            var email = document.updateProfileForm.txtEmail.value;
            var contact = document.updateProfileForm.txtContactNumber.value;
            
            var errorMessage = "The following contains error: \n";
            var error = false;
            
            if (oldpassword == null || oldpassword.trim() == "") {
                errorMessage += "-> Old password is empty\n";
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
                <h4><b><font color="white">Update Profile Page </font> </b></h4>
            </div>
            <div class="panel-body"align="center">
                  
                <div class="container " style="margin-top: 2%; margin-bottom: 2%;">
    
                    <div class="panel panel-primary" style="max-width: 35%;" align="left">
                        
                        <div class="panel-heading form-group">
                            <b><font color="white">
                                Update Profile Form</font> </b>
                        </div>
                    
                        <div class="panel-body" >

                            <form name="updateProfileForm" method="POST" action="vehicleAuction/updateProfileStatus"  onsubmit="return validation()">
                                <div class="form-group">
                                    <label for="username">Username: <%= session.getAttribute("username")%></label>
                                </div>
                                
                                <div class="form-group">
                                    <label for="exampleInputOldPassword">Old Password</label> <input
                                        type="password" class="form-control" name="txtOldPass" id="txtOldPass"
                                        placeholder="Enter Your Old Password" required="required">
                                </div>
                                
                                <div class="form-group">
                                    <label for="exampleInputPassword1">New Password</label> <input
                                        type="password" class="form-control" name="txtPass" id="txtPass"
                                        placeholder="Enter Your Password" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword2">New Confirmation Password</label> <input
                                        type="password" class="form-control" name="txtConfirmPass" id="txtConfirmPass"
                                        placeholder="Enter Your Confirmation Password" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputContactNumber">New Contact Number</label> <input
                                        type="text" class="form-control" name="txtContactNumber" id="txtContactNumber"
                                        placeholder="Enter Your Contact Number" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail">New Email</label> <input
                                        type="text" class="form-control" name="txtEmail" id="txtEmail"
                                        placeholder="Enter Your Email" required="required">
                                </div>
                                <button type="submit" value="submit" style="width: 100%; font-size:1.1em;" class="btn btn-large btn btn-primary btn-lg btn-block" ><b>Update Profile</b></button>

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

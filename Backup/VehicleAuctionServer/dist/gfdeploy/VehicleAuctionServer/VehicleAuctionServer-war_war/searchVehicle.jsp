<%-- 
    Document   : searchVehicle
    Created on : Oct 24, 2015, 9:28:27 PM
    Author     : Tan
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Search Vehicles</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">
        function validation() {
            var make = document.searchVehicleForm.txtMake.value;
            var model = document.searchVehicleForm.txtModel.value;
            var year = document.searchVehicleForm.txtYear.value;
            var status = document.searchVehicleForm.txtStatus.value;
            var min = document.searchVehicleForm.txtMin.value;
            var max = document.searchVehicleForm.txtMax.value;
            var errorMessage = "The following contains error: \n";
            var error = false;
            
            if (status == "Select one") {
                errorMessage += "-> Auction Status cannot be empty\n";
                error = true;
            }
            
            if (year != null && year.trim() != "") {
                if (year < 1950 || year > 2050) {
                    errorMessage += "-> Year must be in between 1951 - 2049 \n"
                    error = true;
                }
            }
            
            if (min != null && min.trim() != "" && max != null && max.trim() != "") {
                if (min > max) {
                    errorMessage += "-> Min must not be more than max\n"
                    error = true;
                }
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
                <h4><b><font color="white">Search Vehicles Page </font> </b></h4>
            </div>
            <div class="panel-body"align="center">
                  
                <div class="container " style="margin-top: 1%; margin-bottom: 0%;">
    
                    <div class="panel panel-primary" style="max-width: 35%;" align="left">
                        
                        <div class="panel-heading form-group">
                            <b><font color="white">
                                Search Vehicles Form</font> </b>
                        </div>
                    
                        <div class="panel-body" >

                            <form name="searchVehicleForm" method="POST" action="vehicleAuction/searchVehicleStatus"  onsubmit="return validation()">
                
                                <div class="form-group">
                                    <label for="exampleInputMake">Make</label> <input
                                        type="text" class="form-control" name="txtMake" id="txtMake"
                                        placeholder="Enter Make" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputModel">Model</label> <input
                                        type="text" class="form-control" name="txtModel" id="txtModel"
                                        placeholder="Enter Model" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputYear">Manufactured Year</label> <input
                                        type="text" class="form-control" name="txtYear" id="txtYear"
                                        placeholder="Enter Manufactured Year" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="sel1">Auction Status</label>
                                    <select class="form-control" name="txtStatus" id="txtStatus">
                                        <option>Select one</option>
                                        <option>Open</option>
                                        <option>Closed</option>
                                        <option>Both</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail">Minimum Price</label> <input
                                        type="text" class="form-control" name="txtMin" id="txtMin"
                                        placeholder="Enter Minimum Price" required="required">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail">Maximum Price</label> <input
                                        type="text" class="form-control" name="txtMax" id="txtMax"
                                        placeholder="Enter Maximum Price" required="required">
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

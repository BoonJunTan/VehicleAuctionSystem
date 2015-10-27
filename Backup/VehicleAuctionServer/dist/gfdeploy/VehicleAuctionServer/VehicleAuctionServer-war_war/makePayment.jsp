<%-- 
    Document   : makePayment
    Created on : Oct 26, 2015, 9:09:29 PM
    Author     : Tan Boon Jun, A0125418J
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Make Payment</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">
        function validation() {
            var cardType = document.paymentForm.txtCardType.value;
            var cardNo = document.paymentForm.txtCardNo.value;
            var holderName = document.paymentForm.txtHolder.value;
            var amount = document.paymentForm.txtAmount.value;
            
            var errorMessage = "The following contains error: \n";
            var error = false;
            
            if (cardType == null || cardType.trim() == "") {
                errorMessage = errorMessage +  "-> Card Type is empty\n";
                error = true;
            }
            
            if (cardNo == null || cardNo.trim() == "") {
                errorMessage = errorMessage + "-> Card Number is empty\n";
                error = true;
            }
            
            if (holderName == null || holderName.trim() == "") {
                errorMessage = errorMessage + "-> Holder Name is empty\n";
                error = true;
            }
            
            if (amount == null || amount.trim() == "") {
                errorMessage = errorMessage + "-> Value Paid is empty\n";
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
                <h4><b><font color="white">Make Payment Page </font> </b></h4>
            </div>
            <div class="panel-body"align="center">
                  
                <div class="container " style="margin-top: 9.5%; margin-bottom: 9.5%;">
    
                    <div class="panel panel-primary" style="max-width: 35%;" align="left">
                        
                        <div class="panel-heading form-group">
                            <b><font color="white">
                                Make Payment Form</font> </b>
                        </div>
                    
                        <div class="panel-body" >

                        <form action="vehicleAuction/paymentStatus" method="POST" name="paymentForm" onsubmit="return validation()">
                            <div class="form-group">
                                <label for="exampleInputCardType">Card Type</label> <input
                                    type="text" class="form-control" name="txtCardType" id="txtCardType"
                                    placeholder="Please Enter Your Card Type" required="required">
                            </div>
                            <input type="hidden" name="txtVehicleId" value="<%= request.getParameter("vehicleId") %>">
                            <div class="form-group">
                                <label for="exampleInputCardNo">Card Number</label> <input
                                    type="text" class="form-control" name="cardNo" id="cardNo"
                                    placeholder="Please Enter Your Card Number" required="required">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputHolder">Holder Name</label> <input
                                    type="text" class="form-control" name="holderName" id="holderName"
                                    placeholder="Please Enter Card Holder Name" required="required">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputAmount">Value Paid</label> <input
                                    type="text" class="form-control" name="txtAmount" id="txtAmount"
                                    placeholder="Please Enter Value Paid" required="required">
                            </div>
                            <button type="submit" style="width: 100%; font-size:1.1em;" class="btn btn-large btn btn-primary btn-lg btn-block" ><b>Make Payment</b></button>
                                                   
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


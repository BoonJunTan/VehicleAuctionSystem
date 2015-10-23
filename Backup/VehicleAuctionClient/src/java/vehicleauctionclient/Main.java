/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleauctionclient;

import ejb.VehicleAuctionManagerBeanRemote;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.ejb.EJB;
import java.lang.Integer;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
public class Main {
    @EJB
    private static VehicleAuctionManagerBeanRemote vambr;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main client = new Main();
        client.doVehicleAuction(args);
    }
    
    public void doVehicleAuction(String[] args) {
        try {
            String userSelect = "";
            while (!userSelect.equalsIgnoreCase("q")) {
                System.out.println("*************************************");
                System.out.println("Welcome to Vehicle Auction System Admin Portal!");
                System.out.println("*************************************");
                System.out.println("Please enter your choice (1-12):");
                System.out.println("1. Add user account.");
                System.out.println("2. Delete user account.");
                System.out.println("3. Add model.");
                System.out.println("4. Update model.");
                System.out.println("5. Delete model.");
                System.out.println("6. Add vehicle.");
                System.out.println("7. Update vehicle.");
                System.out.println("8. Delete vehicle.");
                System.out.println("9. View current auction.");
                System.out.println("10. View closed auctions.");
                System.out.println("11. View bids.");
                System.out.println("12. Process certifications");
                System.out.println("q or Q to quit");
                
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Your choice -> ");
                
                userSelect = br.readLine();
                
                if (userSelect.equals("1")) {
                    displayAddUser();
                } else if (userSelect.equals("2")) {
                    displayDeleteUser();
                } else if (userSelect.equals("3")) {
                    displayAddModel();
                } else if (userSelect.equals("4")) {
                    displayUpdateModel();
                } else if (userSelect.equals("5")) {
                    displayDeleteModel();
                } else if (userSelect.equals("6")) {
                    displayAddVehicle();
                } else if (userSelect.equals("7")) {
                    displayUpdateVehicle();
                } else if (userSelect.equals("8")) {
                    displayDeleteVehicle();
                } else if (userSelect.equals("9")) {
                    displayCurrentAuctions();
                } else if (userSelect.equals("10")) {
                    displayClosedAuctions();
                } else if (userSelect.equals("11")) {
                    displayBids();
                } else if (userSelect.equals("12")) {
                    displayProcessCertifications();
                } else if (userSelect.equalsIgnoreCase("q")) {
                    return;
                } else {
                    System.out.println("You have entered invalid choice.");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception found in Main.java");
            e.printStackTrace();
        }
    }
    
    // User choice 1 - Add User
    private void displayAddUser() {
        String name;
        String password;
        String contactNumber;
        String email;
        
        try {
            System.out.println("You have selected Add User Account");
            name = getString("user name", null);
            password = getString("default password", null);
            contactNumber = getString("contact number", null);
            email = getString("email", null);
            
            if (vambr.userExist(name) == true) {
                System.out.println("Error! Account has existed in database\n");
            } else {
                vambr.createUser(name, password, contactNumber, email);
                vambr.generalUserPersist();
                System.out.println("User account has been successfully created!\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to create new user: " + e.getMessage() + "\n");
        }
    }
    
    // User choice 2 - Delete User
    private void displayDeleteUser() {
        String name;
        
        try {
            System.out.println("You have selected Delete User Account");
            name = getString("user name", null);
            
            if (vambr.userExist(name) == false) {
                System.out.println("Error! Account does not exist in database!\n");
            } else {
                if (vambr.checkIfBidExist(name) == false && vambr.checkIfPaymentExist(name) == false) {
                    vambr.removeUser(name);
                    System.out.println("Account has been successfully deleted!\n");
                } else {
                    System.out.println("Error! Account is associated with bids or payments, cannot be deleted!");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to delete existing user: " + e.getMessage() + "\n");
        } 
    }
    
    // User choice 3 - Add Model
    private void displayAddModel() {
        String make;
        String model;
        int manufacturedYear;
        Scanner sc = new Scanner (System.in);
        
        try {
            System.out.println("You have selected Add Vehicle Model");
            make = getString("make", null);
            model = getString("model", null);
            System.out.print("Enter manufactured year: ");
            manufacturedYear = sc.nextInt();
            if (vambr.modelExist(make, model, manufacturedYear) == true) {
                System.out.println("Error! Model has existed in database\n");
            } else {
                int id = vambr.createModel(make, model, manufacturedYear);
                vambr.generalModelPersist();
                System.out.println("Vehicle Model has been successfully created! Model Id: " + id + "\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to add new model: " + e.getMessage() + "\n");
        }     
    }
    
    // User choice 4 - Update Model
    private void displayUpdateModel() {
        int modelId;
        Scanner sc = new Scanner (System.in);
        
        try {
            System.out.println("You have selected Update Vehicle Model");
            System.out.print("Enter model Id: ");
            modelId = sc.nextInt();
            if (vambr.modelIdExist(modelId) == false) {
                System.out.println("Error! Model does not exist in database\n");
            } else {
                if (vambr.checkIfVehicleAssociated(modelId) == false) {
                    // Update model
                    String make = getString("make", null);
                    String model = getString("model", null);
                    System.out.print("Enter manufactured year: ");
                    int manufacturedYear = sc.nextInt();
                    vambr.updateModel(modelId, make, model, manufacturedYear);
                    //vambr.generalModelPersist();
                    System.out.println("Model has been successfully updated!\n");
                } else {
                    System.out.println("Model is associated with auction vehicle");
                    System.out.println("Model cannot be updated.\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to update existing model: " + e.getMessage() + "\n");
        }   
    }
    
    // User choice 5 - Delete Model
    private void displayDeleteModel() {
        int modelId;
        Scanner sc = new Scanner (System.in);
        
        try {
            System.out.println("You have selected Delete Vehicle Model");
            System.out.print("Enter model Id: ");
            modelId = sc.nextInt();
            if (vambr.modelIdExist(modelId) == false) {
                System.out.println("Error! Model does not exist in database\n");
            } else {
                if (vambr.checkIfVehicleAssociated(modelId) == false) {
                    // Delete model
                    vambr.removeModel(modelId);
                    System.out.println("Model has been successfully updated!\n");
                } else {
                    System.out.println("Model is associated with auction vehicle");
                    System.out.println("Model cannot be deleted.\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to update existing model: " + e.getMessage() + "\n");
        }   
    }
    
    // User choice 6 - Add Vehicle
    private void displayAddVehicle() {
        int modelNumber;
        String registrationNumber;
        String chassisNumber;
        String engineNumber;
        String description;
        String startingBid;
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        dateFormat.format(currentDate);
        Scanner sc = new Scanner (System.in);
        
        try {
            System.out.println("You have selected Add Vehicle");
            System.out.print("Enter model number: ");
            modelNumber = sc.nextInt();
            registrationNumber = getString("registration number", null);
            chassisNumber = getString("chassis number", null);
            engineNumber = getString("engine number", null);
            description = getString("description", null);
            while (true) {
                startingBid = getString("starting bid", null);
                if (Integer.valueOf(startingBid.substring(1)) > 0) {
                    break;
                } 
                System.out.println("Error! Starting bid cannot be less than $0");
            }
            String eDate = getString("auction end time", null);
            Date auctionEndTime = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(eDate);
            if (auctionEndTime.compareTo(currentDate) < 0) {
                System.out.println("Error! Auction end time already passed.\n");
            } else if (vambr.modelIdExist(modelNumber) == false) {
                System.out.println("Error! Model does not exist in database\n");
            }
            else {
                vambr.createVehicle(modelNumber, registrationNumber, chassisNumber, engineNumber, description, startingBid, currentDate, auctionEndTime);
                System.out.println("Auction Vehicle has been successfully added!\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to add new vehicle: " + e.getMessage() + "\n");
        }     
    }
    
    // User choice 7 - Update Vehicle
    private void displayUpdateVehicle() {
        int modelId;
        int vehicleId;
        Scanner sc = new Scanner (System.in);
        
        try {
            System.out.println("You have selected Update Vehicle");
            System.out.print("Enter model Id: ");
            modelId = sc.nextInt();
            if (vambr.modelIdExist(modelId) == false) {
                System.out.println("Error! Model does not exist in database\n");
            } else {
                System.out.println("");
                System.out.println("List of vehicles:");
                ArrayList<String []> vehicleIdList = new ArrayList<String []>();
                for (Object o: vambr.getVehicles(modelId)) {
                    String [] currentVehicle = new String [2];
                    Vector p = (Vector) o;
                    System.out.println("Vehicle id: " + p.get(0));
                    currentVehicle[0] = p.get(0).toString();
                    System.out.println("Registration number: " + p.get(1));
                    System.out.println("Starting bid: " + p.get(2));
                    System.out.println("Current bid: $" + p.get(3));
                    System.out.println("End time: " + p.get(4));
                    System.out.println("Auction state: " + p.get(5));
                    currentVehicle[1] = p.get(5).toString();
                    System.out.println("");
                    vehicleIdList.add(currentVehicle);
                }
                System.out.print("Enter Vehicle Id to Update: ");
                vehicleId = sc.nextInt();
                boolean exist = false;
                String vehicleStatus = null;
                for (int i = 0; i < vehicleIdList.size(); i++) {
                    if (vehicleIdList.get(i)[0].equalsIgnoreCase(String.valueOf(vehicleId)) == true) {
                        exist = true;
                        vehicleStatus = vehicleIdList.get(i)[1];
                        break;
                    }
                }
                if (exist == false) {
                    System.out.println("Error! Vehicle does not exist in database\n");
                } else {
                    if (vehicleStatus.equalsIgnoreCase("open")) {
                        Date currentDate = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                        dateFormat.format(currentDate);
                        String startingBid = null;
                        String description = null;
                        if (!vambr.checkIfVehicleHasBid(vehicleId)) {
                            startingBid = getString("new starting bid", null);
                            description = getString("new description", null);
                        }
                        String eDate = getString("new auction end time", null);
                        Date auctionEndTime = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(eDate);
                        if (auctionEndTime.compareTo(currentDate) < 0) {
                            System.out.println("Error! Auction end time already passed.\n");
                        } else {
                            vambr.updateVehicle(vehicleId, startingBid, description, auctionEndTime);
                            System.out.println("Vehicle successfully updated.\n");
                        }
                    } else {
                        System.out.println("Auction closed. Vehicle cannot be updated.\n");
                    }
                }
                
            }
        } catch (Exception e) {
            System.out.println("Failed to update vehicle: " + e.getMessage() + "\n");
        }     
    }
    
    // User choice 8 - Delete Vehicle
    private void displayDeleteVehicle() {
        int modelId;
        int vehicleId;
        Scanner sc = new Scanner (System.in);
        
        try {
            System.out.println("You have selected Delete Vehicle");
            System.out.print("Enter model Id: ");
            modelId = sc.nextInt();
            if (vambr.modelIdExist(modelId) == false) {
                System.out.println("Error! Model does not exist in database\n");
            } else {
                System.out.println("");
                System.out.println("List of vehicles:");
                ArrayList<String []> vehicleIdList = new ArrayList<String []>();
                for (Object o: vambr.getVehicles(modelId)) {
                    String [] currentVehicle = new String [2];
                    Vector p = (Vector) o;
                    System.out.println("Vehicle id: " + p.get(0));
                    currentVehicle[0] = p.get(0).toString();
                    System.out.println("Registration number: " + p.get(1));
                    System.out.println("Starting bid: " + p.get(2));
                    System.out.println("Current bid: $" + p.get(3));
                    System.out.println("End time: " + p.get(4));
                    System.out.println("Auction state: " + p.get(5));
                    currentVehicle[1] = p.get(5).toString();
                    System.out.println("");
                    vehicleIdList.add(currentVehicle);
                }
                System.out.print("Enter Vehicle Id to Delete: ");
                vehicleId = sc.nextInt();
                boolean exist = false;
                String vehicleStatus = null;
                for (int i = 0; i < vehicleIdList.size(); i++) {
                    if (vehicleIdList.get(i)[0].equalsIgnoreCase(String.valueOf(vehicleId)) == true) {
                        exist = true;
                        vehicleStatus = vehicleIdList.get(i)[1];
                        break;
                    }
                }
                if (exist == false) {
                    System.out.println("Error! Vehicle does not exist in database\n");
                } else {
                    if (vehicleStatus.equalsIgnoreCase("open")) {
                        if (vambr.checkIfVehicleHasBid(vehicleId)) {
                            System.out.println("Auction open with bids. Vehicle cannot be deleted");
                        } else {
                            vambr.removeVehicle(vehicleId);
                            System.out.println("Vehicle successfully deleted.\n");
                        }
                    } else {
                        System.out.println("Auction closed. Vehicle cannot be deleted.\n");
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("Failed to delete vehicle: " + e.getMessage() + "\n");
        }  
    }
    
    // User choice 9 - Current Auctions
    private void displayCurrentAuctions() {
        try {
            System.out.println("You have selected View Current Auctions");
            int counter = 1;
            for (Object o: vambr.getCurrentAuctions()) {
                Vector p = (Vector) o;
                System.out.println("Auction " + counter);
                System.out.println("Vehicle id: " + p.get(0));
                System.out.println("");
                System.out.println("Auction start time: " + p.get(1));
                System.out.println("Auction end time: " + p.get(2));
                System.out.println("Remaining time: " + p.get(3));
                System.out.println("");
                System.out.println("Make: " + p.get(4));
                System.out.println("Model: " + p.get(5));
                System.out.println("Manufactured year: " + p.get(6));
                System.out.println("");
                System.out.println("Registration number: " + p.get(7));
                System.out.println("Chassis number: " + p.get(8));
                System.out.println("Engine number: " + p.get(9));
                System.out.println("Description: " + p.get(10));
                System.out.println("");
                System.out.println("Starting bid: " + p.get(11));
                System.out.println("Current bid: " + p.get(12));
                if (p.size() > 13) {
                    System.out.println("Highest bidder: " + p.get(13));
                    System.out.println("Contact number: " + p.get(14));
                    System.out.println("Email: " + p.get(15));
                }
                System.out.println("");
                counter++;
            }
            
        } catch (Exception e) {
            System.out.println("Failed to view current auctions: " + e.getMessage() + "\n");
        }  
    }
    
    // User choice 10 - Closed Auctions
    private void displayClosedAuctions() {
        try {
            System.out.println("You have selected View Closed Auctions");
            int counter = 1;
            for (Object o: vambr.getClosedAuctions()) {
                Vector p = (Vector) o;
                System.out.println("Auction " + counter);
                System.out.println("Vehicle id: " + p.get(0));
                System.out.println("");
                System.out.println("Auction start time: " + p.get(1));
                System.out.println("Auction end time: " + p.get(2));
                System.out.println("");
                System.out.println("Make: " + p.get(3));
                System.out.println("Model: " + p.get(4));
                System.out.println("Manufactured year: " + p.get(5));
                System.out.println("");
                System.out.println("Registration number: " + p.get(6));
                System.out.println("Chassis number: " + p.get(7));
                System.out.println("Engine number: " + p.get(8));
                System.out.println("Description: " + p.get(9));
                System.out.println("");
                String startBid = (String) p.get(10);
                System.out.println("Starting bid: " + startBid);
                System.out.println("Current bid: " + p.get(11));
                if (p.size() > 12) {
                    System.out.println("Highest bidder: " + p.get(12));
                    System.out.println("Contact number: " + p.get(13));
                    System.out.println("Email: " + p.get(14));
                    
                    // Display part payment
                    ArrayList<String[]> paymentDetails = (ArrayList)p.get(15);
                    for (int x = 0; x < paymentDetails.size() ; x++) {
                        String [] current = paymentDetails.get(x);
                        System.out.println("");
                        System.out.println("Payment: " + (x+1));
                        System.out.println("Amount: " + current[0]);
                        System.out.println("Card Type: " + current[1]);
                        System.out.println("Time: " + current[2]);
                    }
                    System.out.println("");
                    String amountPaid = (String) p.get(16);
                    System.out.println("Total amount paid: " + amountPaid);
                    System.out.println("Total amount remaining: " + (Integer.valueOf(startBid) - Integer.valueOf(amountPaid)));
                }
                System.out.println("");
                counter++;
            }
            
        } catch (Exception e) {
            System.out.println("Failed to view closed auctions: " + e.getMessage() + "\n");
        }  
    }
    
    // User choice 11 - View Bids
    private void displayBids() {
        int vehicleId;
        Scanner sc = new Scanner (System.in);
        
        try {
            System.out.println("You have selected View Bids");
            System.out.print("Enter vehicle id: ");
            vehicleId = sc.nextInt();
            if (vambr.checkIfVehicleExist(vehicleId) == false) {
                System.out.println("Error! Vehicle id does not exist in database\n");
            } else {
                ArrayList<String[]> bidDetails = (ArrayList) vambr.getBids(vehicleId);
                if (bidDetails.isEmpty()) {
                    System.out.println("No bids for vehicle.\n");
                } else {
                    System.out.println("");
                    for (int i = 0; i < bidDetails.size(); i++) {
                        String [] bidArray = bidDetails.get(i);
                        System.out.println("Bid " + (i+1));
                        System.out.println("Bidder: " + bidArray[0]);
                        System.out.println("Value: " + bidArray[1]);
                        System.out.println("Bid Time: " + bidArray[2]);
                        System.out.println("");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to Display Bids: " + e.getMessage() + "\n");
        }  
    }
    
    // User choice 12 - Process Certifications
    private void displayProcessCertifications() {
        int certificateId;
        Scanner sc = new Scanner (System.in);
        
        try {
            System.out.println("You have selected Process Certifications");
            ArrayList <String []> certificateList = vambr.getCertificate();
            if (certificateList.isEmpty()) {
                System.out.println("Error! No certificate to in database.\n");
            } else {
                ArrayList<Integer> certificateIdList = new ArrayList<Integer>();
                for (int i = 0; i < certificateList.size(); i++) {
                    System.out.println("");
                    String [] currentCertificate = certificateList.get(i);
                    System.out.println("Certificate Id: " + currentCertificate[1]);
                    certificateIdList.add(Integer.valueOf(currentCertificate[1]));
                    System.out.println("Certifier: " + currentCertificate[2]);
                    System.out.println("Time: " + currentCertificate[3]);
                    System.out.println("Ceritification Details: " + currentCertificate[4]);
                    System.out.println("Vehicle Id: " + currentCertificate[5]);
                }
                System.out.println("");
                System.out.print("Enter the Request id: ");
                certificateId = sc.nextInt();
                if (certificateIdList.contains(certificateId)) {
                    String status = getString("new status", null);
                    vambr.updateCertificationStatus(certificateId, status);
                    System.out.println("Request has been updated.\n");
                } else {
                    System.out.println("Error! Request Id is not found\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to Process Certification: " + e.getMessage() + "\n");
        }  
    }
    
    // Helper Function - Accept String (not null value)
    public String getString(String attrName, String oldValue){
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        String stringValue = null;

        try {
            while (true) {
                System.out.print("Enter " + attrName + 
                                 (oldValue==null?"":"(" + oldValue + 
                                 ")") +" : ");
                stringValue = br.readLine();
                if (stringValue.length() != 0) {
                    break;
                } else if (stringValue.length() == 0 && 
                           oldValue != null) {
                    stringValue = oldValue;
                    break;
                }
                System.out.println("Invalid " + attrName + "...");
            }//end while
        } catch(Exception ex) {
            System.out.println("\nSystem Error Message: " + 
                    ex.getMessage() + "\n");
        }
        return stringValue.trim();        
    }//end method getString
    
}
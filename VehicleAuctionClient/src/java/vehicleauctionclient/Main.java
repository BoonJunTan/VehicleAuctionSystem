/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleauctionclient;

import ejb.UserServerBeanRemote;
import ejb.ModelServerBeanRemote;
import ejb.VehicleServerBeanRemote;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
public class Main {
    @EJB
    private static UserServerBeanRemote userServerBean;
    @EJB
    private static ModelServerBeanRemote modelServerBean;
    @EJB
    private static VehicleServerBeanRemote vehicleServerBean;

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
                    displayDeleteUser(); // Not done
                } else if (userSelect.equals("3")) {
                    displayAddModel();
                } else if (userSelect.equals("4")) {
                    
                } else if (userSelect.equals("5")) {
                    
                } else if (userSelect.equals("6")) {
                    displayAddVehicle();
                } else if (userSelect.equals("7")) {
                    
                } else if (userSelect.equals("8")) {
                    
                } else if (userSelect.equals("9")) {
                    
                } else if (userSelect.equals("10")) {
                    
                } else if (userSelect.equals("11")) {
                    
                } else if (userSelect.equals("12")) {
                    
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
            
            if (userServerBean.userExist(name) == true) {
                System.out.println("Error! Account has existed in database\n");
            } else {
                userServerBean.addUser(name, password, contactNumber, email);
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
            
            // If Associated with bids or payments = Cannot be deleted, "Error! Account is associated with bids or payments, cannot be deleted!"
            // If don't exist, "Error! Account don't exist in database\n"
            // Else, "User account has been successfully deleted!\n"
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
            
            if (modelServerBean.modelExist(make, model, manufacturedYear) == true) {
                System.out.println("Error! Model has existed in database\n");
            } else {
                int id = modelServerBean.createModel(make, model, manufacturedYear);
                System.out.println("Vehicle Model has been successfully created! Model Id: " + id + "\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to add new model: " + e.getMessage() + "\n");
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
            startingBid = getString("starting bid", null);
            String eDate = getString("auction end time", null);
            Date auctionEndTime = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(eDate);
            if (auctionEndTime.compareTo(currentDate) < 0) {
                System.out.println("Error! Auction end time already passed.\n");
            } else {
                vehicleServerBean.addVehicle(modelNumber, registrationNumber, chassisNumber, engineNumber, description, startingBid, currentDate, auctionEndTime);
                System.out.println("Auction Vehicle has been successfully added!\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to add new vehicle: " + e.getMessage() + "\n");
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
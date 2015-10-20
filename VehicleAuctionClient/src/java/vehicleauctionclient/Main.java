/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleauctionclient;

import ejb.UserServerBeanRemote;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import javax.ejb.EJB;

/**
 *
 * @author Tan
 */
public class Main {
    @EJB
    private static UserServerBeanRemote userServerBean;

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
                    
                } else if (userSelect.equals("3")) {
                    
                } else if (userSelect.equals("4")) {
                    
                } else if (userSelect.equals("5")) {
                    
                } else if (userSelect.equals("6")) {
                    
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
    
    private void displayAddUser() {
        Long customerID;
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
            
            userServerBean.addUser(name, password, contactNumber, email);
            System.out.println("User account has been successfully created");
            
        } catch (Exception e) {
            System.out.println("Failed to create new user: " + e.getMessage() + "\n");
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
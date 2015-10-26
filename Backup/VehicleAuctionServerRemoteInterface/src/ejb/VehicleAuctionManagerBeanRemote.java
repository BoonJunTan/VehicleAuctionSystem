/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.Remote;

/**
 *
 * @author Tan
 */
@Remote
public interface VehicleAuctionManagerBeanRemote {
    /* ------------------ CREATION ------------------ */
    public void createUser(String name, String password, String contactNumber, String email);
    public void createBid(String name, int vehicleId, String bidAmount);
    public int createModel(String make, String model, int manufacturedYear);
    public void createVehicle(int modelNumber, String registrationNumber, String chassisNumber, String engineNumber, String description, String startingBid, Date auctionStartTime, Date auctionEndTime);
    public void createPayment(String username, String vehicleId, String cardType, String cardNo, String holder, String amount);
    /* ------------------ Display ------------------ */
    public List <Vector> getVehicles(int modelId);
    public List <Vector> getCurrentAuctions();
    public List <Vector> getClosedAuctions();
    public ArrayList <String[]> getBids(int vehicleId);
    public ArrayList <String[]> getCertificate();
    public ArrayList <String[]> searchVehicle(String make, String model, String year, String status, String min, String max);;
    public Map <String, String> getDetailedVehicle(int vehicleId);
    public ArrayList <String[]> getUserBids(String username);
    public ArrayList <String[]> getPastWonAuction(String username);
    
    /* ------------------ ADDING ------------------ */
    public void addBid();
    public void addVehicle();
    
    /* ------------------ PERSIST ------------------ */
    public void generalUserPersist();
    public void generalModelPersist();
    public void persistUserBid();
    public void persistModelVehicle();
    
    /* ------------------ EXIST ------------------ */
    public boolean userExist(String name);
    public boolean checkIfBidExist(String name);
    public boolean checkIfPaymentExist(String name);
    public boolean modelExist(String make, String model, int manufacturedYear);
    public boolean modelIdExist(int modelId);
    public boolean checkIfVehicleAssociated(int modelId);
    public boolean checkIfVehicleExist(int vehicleId);
    public boolean checkIfVehicleHasBid(int vehicleId);
    public boolean checkPassword(String name, String password);
    
    /* ------------------ UPDATE ------------------ */
    public void updateModel(int modelId, String make, String model, int manufacturedYear);
    public void updateVehicle(int vehicleId, String startingBid, String description, Date auctionEndTime);
    public void updateCertificationStatus(int certificateId, String status);
    public void updateProfile(String username, String password, String contactNumber, String email);
            
    /* ------------------ DELETE ------------------ */
    public void removeUser(String name);
    public void removeModel(int modelId);
    public void removeVehicle(int vehicleId);
    
    /* ------------------ Other Function ------------------ */
    public boolean webLogin(String name, String password);
    
    /* ------------------ DEFAULT ------------------ */
    public void remove();
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public void createBid(Date bidTime, String bidAmount, String name);
    public int createModel(String make, String model, int manufacturedYear);
    public void createVehicle(int modelNumber, String registrationNumber, String chassisNumber, String engineNumber, String description, String startingBid, Date auctionStartTime, Date auctionEndTime);
    
    /* ------------------ Display ------------------ */
    public List <Vector> getVehicles(int modelId);
    public List <Vector> getCurrentAuctions();
    public List <Vector> getClosedAuctions();
    public List <Vector> getBids(int vehicleId);
    public ArrayList <String[]> getCertificate();
            
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
    
    /* ------------------ UPDATE ------------------ */
    public void updateModel(int modelId, String make, String model, int manufacturedYear);
    public void updateVehicle(int vehicleId, String startingBid, String description, Date auctionEndTime);
    public void updateCertificationStatus(int certificateId, String status);
            
    /* ------------------ DELETE ------------------ */
    public void removeUser(String name);
    public void removeModel(int modelId);
    public void removeVehicle(int vehicleId);
    
    /* ------------------ DEFAULT ------------------ */
    public void remove();
    
}
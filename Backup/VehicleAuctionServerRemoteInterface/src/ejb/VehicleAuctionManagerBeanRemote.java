/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
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
    
    /* ------------------ UPDATE ------------------ */
    public void updateModel(int modelId, String make, String model, int manufacturedYear);
    
    /* ------------------ DELETE ------------------ */
    public void removeUser(String name);
    public void removeModel(int modelId);
    
    /* ------------------ DEFAULT ------------------ */
    public void remove();
    
}

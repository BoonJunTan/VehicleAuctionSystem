/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Stateful
public class VehicleAuctionManagerBean implements VehicleAuctionManagerBeanRemote {

    @PersistenceContext
    private EntityManager em;

    // This ID is for Auto Increment Model
    int id = 0;
    
    private ModelEntity modelEntity;
    private VehicleEntity vehicleEntity;
    private UserEntity userEntity;
    private BidEntity bidEntity;
    private CertificationEntity certificationEntity;
    
    private Collection<VehicleEntity> vehicles;
    private Collection<BidEntity> bids;
    
    private ArrayList<ModelEntity> modelList = new ArrayList<ModelEntity>();
    private ArrayList<UserEntity> userList = new ArrayList<UserEntity>();

    public VehicleAuctionManagerBean() {
        
    }
    
    /* ------------------ CREATION  ------------------ */
    @Override
    public void createUser(String name, String password, String contactNumber, String email) {
        userEntity = new UserEntity();
        userEntity.addUser(name, password, contactNumber, email);
        bids = new ArrayList<BidEntity>();
    }

    @Override
    public void createBid(Date bidTime, String bidAmount, String name) {
        bidEntity = new BidEntity();
        userEntity = em.find(UserEntity.class, name);
        bidEntity.addBid(bidTime, bidAmount);
        bidEntity.setUser(userEntity);
    }
    
    @Override
    public int createModel(String make, String model, int manufacturedYear) {
        modelEntity = new ModelEntity();
        id++;
        modelEntity.addModel(id, make, model, manufacturedYear);
        vehicles = new ArrayList<VehicleEntity>();
        modelList.add(modelEntity);
        return id;
    }

    @Override
    public void createVehicle(int modelNumber, String registrationNumber, String chassisNumber, String engineNumber, String description, String startingBid, Date auctionStartTime, Date auctionEndTime) {
        vehicleEntity = new VehicleEntity();
        modelEntity = em.find(ModelEntity.class, modelNumber);
        vehicleEntity.addVehicle(registrationNumber, chassisNumber, engineNumber, description, startingBid, auctionStartTime, auctionEndTime);
        vehicleEntity.setModel(modelEntity);
        
        vehicles = modelEntity.getVehicles();
        vehicles.add(vehicleEntity);
        modelEntity.setVehicles(vehicles);
        vehicleEntity.setUser(userEntity);
        
        em.persist(vehicleEntity);
        //em.merge(modelEntity);
        em.flush();
    }
    
    /* ------------------ ADDING  ------------------ */
    @Override
    public void addBid() {
        bids.add(bidEntity);
    }
    
    @Override
    public void addVehicle() {
        vehicles = modelEntity.getVehicles();
        vehicles.add(vehicleEntity);
    }

    /* ------------------ PERSIST ------------------ */
    @Override
    public void generalUserPersist() {
        em.persist(userEntity);
    }
    
    @Override
    public void generalModelPersist() {
        em.persist(modelEntity);
    }
    
    @Override
    public void persistUserBid() {
        userEntity.setBids(bids);
        em.persist(bidEntity);
        em.flush();
    }
    
    @Override
    public void persistModelVehicle() {
        modelEntity.setVehicles(vehicles);
        em.persist(vehicleEntity);
        em.flush();
    }
    
    /* ------------------ EXIST ------------------ */
    @Override
    public boolean userExist(String name) {
        Query q = em.createQuery("SELECT u FROM Username u WHERE u.name = '" + name + "'");
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean checkIfBidExist(String name) {
        Query q = em.createQuery("SELECT u FROM Username u");
        for (Object o: q.getResultList()) {
            UserEntity u = (UserEntity) o;
            if (u.getName().equalsIgnoreCase(name) && u.getBids().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean checkIfPaymentExist(String name) {
        Query q = em.createQuery("SELECT u FROM Username u");
        for (Object o: q.getResultList()) {
            UserEntity u = (UserEntity) o;
            if (u.getName().equalsIgnoreCase(name) && u.getPayments().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean modelExist(String make, String model, int manufacturedYear) {
        Query q = em.createQuery("SELECT m FROM Model m WHERE m.make = '" + make + "' AND m.model = '" + model + "' AND m.manufacturedYear = '" + manufacturedYear + "'");
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean modelIdExist(int modelId) {
        Query q = em.createQuery("SELECT m FROM Model m WHERE m.id = '" + modelId + "'");
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean checkIfVehicleAssociated(int modelId) {
        Query q = em.createQuery("SELECT v FROM Vehicle v");
        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            if (v.getModel().getId() == modelId) {
                return true;
            }
        }
        return false;
    }
    
    /* ------------------ UPDATE ------------------ */
    @Override
    public void updateModel(int modelId, String make, String model, int manufacturedYear) {
        // This is wrong - Reminder for future
        // Query q = em.createQuery("UPDATE Model m SET m.make = '" + make + "', m.model = '" + model + "', m.manufacturedYear = " + manufacturedYear + " WHERE m.id = " + modelId);
        
        modelEntity = em.find(ModelEntity.class, modelId);
        modelEntity.setMake(make);
        modelEntity.setModel(model);
        modelEntity.setManufacturedYear(manufacturedYear);
        em.persist(modelEntity);
        em.flush();
    }
    
    /* ------------------ DELETE ------------------ */
    @Override
    public void removeModel(int modelId) {
        modelEntity = em.find(ModelEntity.class, modelId);
        em.remove(modelEntity);
        em.flush();
    }
    
    @Override
    public void removeUser(String name) {
        userEntity = em.find(UserEntity.class, name);
        em.remove(userEntity);
        em.flush();
    }
    
    /* ------------------ DEFAULT ------------------ */
    @Override
    @Remove
    public void remove() {
        System.out.println("VehicleAuctionManagerBean:remove()");
    }
}

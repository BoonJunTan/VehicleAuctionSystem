/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
public class ModelServerBean implements ModelServerBeanRemote {
    @PersistenceContext
    private EntityManager em;
    
    private ModelEntity modelEntity;
    private VehicleEntity vehicleEntity;
    private Collection<VehicleEntity> vehicle;
    
    int id = 0;
    ArrayList<String []> modelList = new ArrayList<String []>();
    
    public ModelServerBean() {
        
    }
    
    @Override
    public int createModel(String make, String model, int manufacturedYear) {
        modelEntity = new ModelEntity();
        id++;
        modelEntity.addModel(id, make, model, manufacturedYear);
        vehicle = new ArrayList<VehicleEntity>();
        return id;
    }
    
    @Override
    public void createVehicle(String registrationNumber, String chassisNumber, String engineNumber, String description, String startingBid, Date auctionStartTime, Date auctionEndTime) {
        vehicleEntity = new VehicleEntity();
        vehicleEntity.addVehicle(id, registrationNumber, chassisNumber, engineNumber, description, startingBid, auctionStartTime, auctionEndTime);
    }
    
    @Override
    public void addModel() {
        vehicle.add(vehicleEntity);
    }
    
    @Override
    public void persist() {
        modelEntity.setVehicles(vehicle);
        em.persist(modelEntity);
    }
    
    @Override
    public boolean modelExist(String make, String model, int manufacturedYear) {
        for (int i = 0; i < modelList.size(); i++) {
            if (modelList.get(i)[0].equalsIgnoreCase(make) && modelList.get(i)[1].equalsIgnoreCase(model) && modelList.get(i)[2].equalsIgnoreCase(String.valueOf(manufacturedYear))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void updateModel(String name) {
        
    }

    @Override
    public void removeModel(String name) {
        
    }
    
    @Override
    @Remove
    public void remove() {
        System.out.println("ModelManagerBean:remove()");        
    }
}

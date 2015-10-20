/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Stateless
public class VehicleServerBean implements VehicleServerBeanRemote {
    @PersistenceContext
    private EntityManager em;
    
    ArrayList<String> vehicleList = new ArrayList<String>();
    
    public VehicleServerBean() {
        
    }
    
    public void addVehicle(int modelNumber, String registrationNumber, int chassisNumber, int engineNumber, String description, String startingBid, Date auctionEndTime) {
        VehicleEntity v = new VehicleEntity();
        v.addVehicle(modelNumber, registrationNumber, chassisNumber, engineNumber, description, startingBid, auctionEndTime);
        //vehicleList.add(name);
        em.persist(v);
    }
    
    @Remove
    public void remove() {
        System.out.println("VehicleManagerBean:remove()");        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Tan Boon Jun, A0125418
 */
@Entity(name="Vehicle")
public class VehicleEntity implements Serializable {
    @Id
    private int id;
    private String registrationNumber;
    private int chassisNumber;
    private int engineNumber;
    private String description;
    private String startingBid;
    private Date auctionEndTime;
    
    /** Creates a new instance of VehicleEntity*/
    public VehicleEntity() {
        
    }

    public void addVehicle(int modelNumber, String registrationNumber, int chassisNumber, int engineNumber, String description, String startingBid, Date auctionEndTime) {
        this.setId(modelNumber);
        this.setRegistrationNumber(registrationNumber);
        this.setChassisNumber(chassisNumber);
        this.setEngineNumber(engineNumber);
        this.setDescription(description);
        this.setStartingBid(startingBid);
        this.setAuctionEndTime(auctionEndTime);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the registrationNumber
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * @param registrationNumber the registrationNumber to set
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * @return the chassisNumber
     */
    public int getChassisNumber() {
        return chassisNumber;
    }

    /**
     * @param chassisNumber the chassisNumber to set
     */
    public void setChassisNumber(int chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    /**
     * @return the engineNumber
     */
    public int getEngineNumber() {
        return engineNumber;
    }

    /**
     * @param engineNumber the engineNumber to set
     */
    public void setEngineNumber(int engineNumber) {
        this.engineNumber = engineNumber;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startingBid
     */
    public String getStartingBid() {
        return startingBid;
    }

    /**
     * @param startingBid the startingBid to set
     */
    public void setStartingBid(String startingBid) {
        this.startingBid = startingBid;
    }

    /**
     * @return the auctionEndTime
     */
    public Date getAuctionEndTime() {
        return auctionEndTime;
    }

    /**
     * @param auctionEndTime the auctionEndTime to set
     */
    public void setAuctionEndTime(Date auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }
    
}

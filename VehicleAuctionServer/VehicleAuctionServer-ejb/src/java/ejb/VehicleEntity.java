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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Tan Boon Jun, A0125418
 */
@Entity(name="Vehicle")
public class VehicleEntity implements Serializable {
    @Id
    private int id;
    private String registrationNumber;
    private String chassisNumber;
    private String engineNumber;
    private String description;
    private String startingBid;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date auctionStartTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date auctionEndTime;
    
    @ManyToOne
    private ModelEntity model = new ModelEntity();
    
    /** Creates a new instance of VehicleEntity*/
    public VehicleEntity() {
        
    }

    public void addVehicle(int modelNumber, String registrationNumber, String chassisNumber, String engineNumber, String description, String startingBid, Date auctionStartTime, Date auctionEndTime) {
        this.setId(modelNumber);
        this.setRegistrationNumber(registrationNumber);
        this.setChassisNumber(chassisNumber);
        this.setEngineNumber(engineNumber);
        this.setDescription(description);
        this.setStartingBid(startingBid);
        this.setAuctionStartTime(auctionStartTime);
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
    public String getChassisNumber() {
        return chassisNumber;
    }

    /**
     * @param chassisNumber the chassisNumber to set
     */
    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    /**
     * @return the engineNumber
     */
    public String getEngineNumber() {
        return engineNumber;
    }

    /**
     * @param engineNumber the engineNumber to set
     */
    public void setEngineNumber(String engineNumber) {
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
     * @return the auctionStartTime
     */
    public Date getAuctionStartTime() {
        return auctionStartTime;
    }

    /**
     * @param auctionStartTime the auctionEndTime to set
     */
    public void setAuctionStartTime(Date auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
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
    
    public ModelEntity getModel() {
        return model;
    }
    
    public void setModel(ModelEntity model) {
        this.model = model;
    }
    
}

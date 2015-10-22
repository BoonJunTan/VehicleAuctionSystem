/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Tan Boon Jun, A0125418
 */
@Entity(name="Vehicle")
public class VehicleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vehicleId;
    private String registrationNumber;
    private String chassisNumber;
    private String engineNumber;
    private String description;
    private String startingBid;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date auctionStartTime;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date auctionEndTime;
    
    @ManyToOne
    private ModelEntity model = new ModelEntity();
    
    @ManyToOne
    private UserEntity user = new UserEntity();
    
    @OneToOne(cascade={CascadeType.PERSIST})
    private CertificationEntity certification;
    
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="vehicle")
    private Collection<PaymentEntity> payments = new ArrayList<PaymentEntity>();
    
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="vehicle")
    private Collection<BidEntity> bids = new ArrayList<BidEntity>();

    /** Creates a new instance of VehicleEntity*/
    public VehicleEntity() {
        
    }
    
    public Collection<PaymentEntity> getPayments() {
        return payments;
    }
    
    public void setPayments(Collection<PaymentEntity> payments) {
        this.payments = payments;
    }
    
    public Collection<BidEntity> getBids() {
        return bids;
    }
    
    public void setBids(Collection<BidEntity> bids) {
        this.bids = bids;
    }

    public void addVehicle(String registrationNumber, String chassisNumber, String engineNumber, String description, String startingBid, Date auctionStartTime, Date auctionEndTime) {
        this.setRegistrationNumber(registrationNumber);
        this.setChassisNumber(chassisNumber);
        this.setEngineNumber(engineNumber);
        this.setDescription(description);
        this.setStartingBid(startingBid);
        this.setAuctionStartTime(auctionStartTime);
        this.setAuctionEndTime(auctionEndTime);
    }
    
    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(String startingBid) {
        this.startingBid = startingBid;
    }
    
    public Date getAuctionStartTime() {
        return auctionStartTime;
    }

    public void setAuctionStartTime(Date auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public Date getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(Date auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    public ModelEntity getModel() {
        return model;
    }
    
    public void setModel(ModelEntity model) {
        this.model = model;
    }
    
    public CertificationEntity getCertification() {
        return certification;
    }
    
    public void setCertification(CertificationEntity certification) {
        this.certification = certification;
    }
    
}

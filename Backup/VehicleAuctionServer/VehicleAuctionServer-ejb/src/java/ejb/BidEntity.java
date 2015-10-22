/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Entity(name="Bid")
public class BidEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date bidTime;
    private String bidAmount;
    private UserEntity user = new UserEntity();
    private VehicleEntity vehicle = new VehicleEntity();
    
    public BidEntity() {
        
    }
    
    public void addBid(Date bidTime, String bidAmount) {
        this.setBidTime(bidTime);
        this.setBidAmount(bidAmount);
    }
    
    @ManyToOne
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    @ManyToOne
    public VehicleEntity getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BidEntity)) {
            return false;
        }
        BidEntity other = (BidEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.BidEntity[ id=" + id + " ]";
    }
    
}

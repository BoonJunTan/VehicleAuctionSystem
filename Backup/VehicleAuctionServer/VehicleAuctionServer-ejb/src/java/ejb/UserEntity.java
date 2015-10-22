/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Query;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Entity(name="Username")
public class UserEntity implements Serializable {
    private String name;
    private String password;
    private String contactNumber;
    private String email;
    private Collection<BidEntity> bids = new ArrayList<BidEntity>();
    private Collection<PaymentEntity> payments = new ArrayList<PaymentEntity>();
    private Collection<VehicleEntity> vehicles = new ArrayList<VehicleEntity>();
    
    /** Creates a new instance of UserEntity*/
    public UserEntity() {
        
    }
    
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="user")
    public Collection<PaymentEntity> getPayments() {
        return payments;
    }
    
    public void setPayments(Collection<PaymentEntity> payments) {
        this.payments = payments;
    }
    
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="user")
    public Collection<VehicleEntity> getVehicles() {
        return vehicles;
    }
    
    public void setVehicles(Collection<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
    }
    
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="user")
    public Collection<BidEntity> getBids() {
        return bids;
    }
    
    public void setBids(Collection<BidEntity> bids) {
        this.bids = bids;
    }

    public void addUser(String name, String password, String contactNumber, String email) {
        this.setName(name);
        this.setPassword(password);
        this.setContactNumber(contactNumber);
        this.setEmail(email);
    }
    
    @Id
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword (String password) {
        this.password = password;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber (String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.UserEntity[ id=" + name + " ]";
    }
    
}

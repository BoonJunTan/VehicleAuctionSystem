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

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Entity(name="Model")
public class ModelEntity implements Serializable {
    private int id = 0;
    private String make;
    private String model;
    private int manufacturedYear;
    private Collection<VehicleEntity> vehicles = new ArrayList<VehicleEntity>();

    public void addModel(int id, String make, String model, int manufacturedYear) {
        this.setId(id);
        this.setMake(make);
        this.setModel(model);
        this.setManufacturedYear(manufacturedYear);
    }
    
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="model")
    public Collection<VehicleEntity> getVehicles() {
        return vehicles;
    }
    
    public void setVehicles(Collection<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
    }
    
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getMake() {
        return make;
    }
    
    public void setMake(String make) {
        this.make = make;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel (String model) {
        this.model = model;
    }
    
    public int getManufacturedYear() {
        return manufacturedYear;
    }
    
    public void setManufacturedYear (int manufacturedYear) {
        this.manufacturedYear = manufacturedYear;
    }
    
}
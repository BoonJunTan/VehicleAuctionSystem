/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Entity(name="Model")
public class ModelEntity implements Serializable {
    @Id
    private int id = 0;
    private String make;
    private String model;
    private int manufacturedYear;

    public void addModel(int id, String make, String model, int manufacturedYear) {
        this.setId(id);
        this.setMake(make);
        this.setModel(model);
        this.setManufacturedYear(manufacturedYear);
    }
    
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
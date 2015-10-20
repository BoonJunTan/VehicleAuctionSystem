/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
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
public class ModelServerBean implements ModelServerBeanRemote {
    @PersistenceContext
    private EntityManager em;
    
    ArrayList<String []> modelList = new ArrayList<String []>();
    
    public ModelServerBean() {
        
    }
    
    public void addModel(String make, String model, int manufacturedYear) {
        ModelEntity m = new ModelEntity();
        m.addModel(make, model, manufacturedYear);
        String [] details = {make, model, Integer.toString(manufacturedYear)};
        modelList.add(details);
        em.persist(m);
    }
    
    public boolean modelExist(String make, String model, int manufacturedYear) {
        for (int i = 0; i < modelList.size(); i++) {
            if (modelList.get(i)[0].equalsIgnoreCase(make) && modelList.get(i)[1].equalsIgnoreCase(model) && modelList.get(i)[2].equalsIgnoreCase(String.valueOf(manufacturedYear))) {
                return true;
            }
        }
        return false;
    }
    
    public void updateModel(String name) {
        
    }

    public void removeModel(String name) {
        
    }
    
    @Remove
    public void remove() {
        System.out.println("CustomerManagerBean:remove()");        
    }
}

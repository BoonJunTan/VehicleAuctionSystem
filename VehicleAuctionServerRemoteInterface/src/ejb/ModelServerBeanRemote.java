/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Remote;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Remote
public interface ModelServerBeanRemote {
    
    public void addModel(String make, String model, int manufacturedYear);
    public boolean modelExist(String make, String model, int manufacturedYear);
    public void updateModel(String name);
    public void removeModel(String name);
    public void remove();
    
}

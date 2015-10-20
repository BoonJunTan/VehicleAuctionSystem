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
public interface UserServerBeanRemote {

    public void addUser(String name, String password, String contactNumber, String email);
    public boolean userExist(String name);
    public void removeUser(String name);
    
}

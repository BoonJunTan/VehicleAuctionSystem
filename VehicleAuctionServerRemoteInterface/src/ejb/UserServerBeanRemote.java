/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Remote;

/**
 *
 * @author Tan
 */
@Remote
public interface UserServerBeanRemote {

    public void addUser(String name, String password, String contactNumber, String email);
    public void removeUser(String name);
    
}

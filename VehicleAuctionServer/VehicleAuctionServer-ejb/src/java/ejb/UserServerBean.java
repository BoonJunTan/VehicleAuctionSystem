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
public class UserServerBean implements UserServerBeanRemote {
    @PersistenceContext
    private EntityManager em;
    
    ArrayList<String> userList = new ArrayList<String>();
    
    public UserServerBean() {
        
    }
    
    public void addUser(String name, String password, String contactNumber, String email) {
        UserEntity u = new UserEntity();
        u.addUser(name, password, contactNumber, email);
        userList.add(name);
        em.persist(u);
    }
    
    public boolean userExist(String name) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeUser(String name) {
        
    }
    
    @Remove
    public void remove() {
        System.out.println("UserManagerBean:remove()");        
    }
}

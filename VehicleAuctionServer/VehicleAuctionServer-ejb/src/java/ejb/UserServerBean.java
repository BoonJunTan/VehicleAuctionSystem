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
 * @author Tan
 */
@Stateless
public class UserServerBean implements UserServerBeanRemote {
    @PersistenceContext
    private EntityManager em;
    
    public UserServerBean() {
        
    }
    
    public void addUser(String name, String password, String contactNumber, String email) {
        UserEntity u = new UserEntity();
        u.addUser(name, password, contactNumber, email);
        em.persist(u);
    }

    public void removeUser(String name) {
        
    }
    
    @Remove
    public void remove() {
        System.out.println("UserManagerBean:remove()");        
    }
}

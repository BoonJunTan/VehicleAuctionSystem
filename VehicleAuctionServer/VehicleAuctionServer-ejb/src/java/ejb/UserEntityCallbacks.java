/*
 * UserCallBacks.java
 */
package ejb;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class UserEntityCallbacks {
    
    @PrePersist
    public void prePersist(UserEntity u) {
        System.out.println("UserEntityCallbacks:prePersist " + u);
    }   
    
    @PostPersist
    public void postPersist(UserEntity u) {
        System.out.println("UserEntityCallbacks:postPersist " + u);
    }  
    
    @PreRemove
    public void preRemove(UserEntity u) {
        System.out.println("UserEntityCallbacks:preRemove " + u);
    }    
    
    @PostRemove
    public void postRemove(UserEntity u) {
        System.out.println("UserEntityCallbacks:postRemove " + u);
    }
        
    @PreUpdate
    public void preUpdate(UserEntity u) {
        System.out.println("UserEntityCallbacks:preUpdate " + u);
    }
        
    @PostUpdate
    public void postUpdate(UserEntity u) {
        System.out.println("UserEntityCallbacks:postUpdate " + u);
    }
        
    @PostLoad
    public void postLoad(UserEntity u) {
        System.out.println("UserEntityCallbacks:postLoad " + u);
    }      
}

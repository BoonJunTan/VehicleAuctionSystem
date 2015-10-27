/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Stateless
public class CertificationManagementServerBean implements CertificationManagementServerBeanRemote {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void sendMessage(String username, String vehicleId, String content) {
        
    }
    
}

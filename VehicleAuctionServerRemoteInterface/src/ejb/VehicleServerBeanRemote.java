/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import javax.ejb.Remote;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Remote
public interface VehicleServerBeanRemote {
    public void addVehicle(int modelNumber, String registrationNumber, String chassisNumber, String engineNumber, String description, String startingBid, Date auctionStartTime, Date auctionEndTime);
    public void remove();
}

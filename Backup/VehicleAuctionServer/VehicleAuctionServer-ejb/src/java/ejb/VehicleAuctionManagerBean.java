/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@Stateful
public class VehicleAuctionManagerBean implements VehicleAuctionManagerBeanRemote {

    @PersistenceContext
    private EntityManager em;

    // This ID is for Auto Increment Model
    int id = 0;
    
    private ModelEntity modelEntity;
    private VehicleEntity vehicleEntity;
    private UserEntity userEntity;
    private BidEntity bidEntity;
    private CertificationEntity certificationEntity;
    
    private Collection<VehicleEntity> vehicles;
    private Collection<BidEntity> bids;
    
    private ArrayList<ModelEntity> modelList = new ArrayList<ModelEntity>();
    private ArrayList<UserEntity> userList = new ArrayList<UserEntity>();

    public VehicleAuctionManagerBean() {
        
    }
    
    /* ------------------ CREATION  ------------------ */
    @Override
    public void createUser(String name, String password, String contactNumber, String email) {
        userEntity = new UserEntity();
        userEntity.addUser(name, password, contactNumber, email);
        bids = new ArrayList<BidEntity>();
    }

    @Override
    public void createBid(Date bidTime, String bidAmount, String name) {
        bidEntity = new BidEntity();
        userEntity = em.find(UserEntity.class, name);
        bidEntity.addBid(bidTime, bidAmount);
        bidEntity.setUser(userEntity);
    }
    
    @Override
    public int createModel(String make, String model, int manufacturedYear) {
        modelEntity = new ModelEntity();
        id++;
        modelEntity.addModel(id, make, model, manufacturedYear);
        vehicles = new ArrayList<VehicleEntity>();
        modelList.add(modelEntity);
        return id;
    }

    @Override
    public void createVehicle(int modelNumber, String registrationNumber, String chassisNumber, String engineNumber, String description, String startingBid, Date auctionStartTime, Date auctionEndTime) {
        vehicleEntity = new VehicleEntity();
        modelEntity = em.find(ModelEntity.class, modelNumber);
        vehicleEntity.addVehicle(registrationNumber, chassisNumber, engineNumber, description, startingBid, auctionStartTime, auctionEndTime);
        vehicleEntity.setModel(modelEntity);
        
        vehicles = modelEntity.getVehicles();
        vehicles.add(vehicleEntity);
        modelEntity.setVehicles(vehicles);
        vehicleEntity.setUser(userEntity);
        
        em.persist(vehicleEntity);
        //em.merge(modelEntity);
        em.flush();
    }
    
    /* ------------------ Display ------------------ */
    @Override
    public List <Vector> getVehicles(int modelId) {
        Query q = em.createQuery("SELECT v FROM Vehicle v");
        List <Vector> entityList = new ArrayList();
        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            if (v.getModel().getId() == modelId) {
                Vector entity = new Vector();
                entity.add(v.getVehicleId());
                entity.add(v.getRegistrationNumber());
                entity.add(v.getStartingBid());
                Query q2 = em.createQuery("SELECT b FROM Bid b");
                int startingBid = Integer.valueOf(v.getStartingBid().substring(1));
                int highestBid = startingBid;
                for (Object o2: q2.getResultList()) {
                    BidEntity b = (BidEntity) o2;
                    if (b.getVehicle().getVehicleId() == v.getVehicleId()) {
                        int currentBid = Integer.valueOf(b.getBidAmount().substring(1));
                        if (highestBid < currentBid) {
                            highestBid = currentBid;
                        }
                    }
                }
                entity.add(highestBid);
                Date currentDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                entity.add(dateFormat.format(v.getAuctionEndTime()));
                dateFormat.format(currentDate);
                if (v.getAuctionEndTime().compareTo(currentDate) < 0) {
                    entity.add("closed");
                } else {
                    entity.add("open");
                }
                entityList.add(entity);
            }
        }
        return entityList;
    }
    
    @Override
    public List <Vector> getCurrentAuctions() {
        Query q = em.createQuery("SELECT v FROM Vehicle v");
        List <Vector> entityList = new ArrayList();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        dateFormat.format(currentDate);
        
        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            if (v.getAuctionEndTime().compareTo(currentDate) > 0) {
                Vector entity = new Vector();
                entity.add(v.getVehicleId());
                String auctionStartTime = dateFormat.format(v.getAuctionStartTime());
                String auctionEndTime = dateFormat.format(v.getAuctionEndTime());
                entity.add(auctionStartTime);
                entity.add(auctionEndTime);
                long diff = 0;
                try {
                    diff = dateFormat.parse(auctionEndTime).getTime() - dateFormat.parse(auctionStartTime).getTime();
                } catch (ParseException ex) {
                    System.out.println("Error with getting remaining time");
                }
                String diffSeconds = Long.toString(diff / 1000 % 60);
                String diffMinutes = Long.toString(diff / (60 * 1000) % 60);
                String diffHours = Long.toString(diff / (60 * 60 * 1000) % 24);
                String diffDays = Long.toString(diff / (24 * 60 * 60 * 1000));
                entity.add(diffDays + "d " + diffHours + "h " + diffMinutes + "m " + diffSeconds + "s");
                entity.add(v.getModel().getMake());
                entity.add(v.getModel().getModel());
                entity.add(v.getModel().getManufacturedYear());
                entity.add(v.getRegistrationNumber());
                entity.add(v.getChassisNumber());
                entity.add(v.getEngineNumber());
                entity.add(v.getDescription());
                entity.add(v.getStartingBid());
                if (v.getBids().isEmpty()) {
                    entity.add("None");
                } else {
                    Query q2 = em.createQuery("SELECT b FROM Bid b");
                    int startingBid = Integer.valueOf(v.getStartingBid().substring(1));
                    int highestBid = startingBid;
                    String highestBidder = null;
                    String contactNumber = null;
                    String email = null;
                    for (Object o2: q2.getResultList()) {
                        BidEntity b = (BidEntity) o2;
                        if (b.getVehicle().getVehicleId() == v.getVehicleId()) {
                            int currentBid = Integer.valueOf(b.getBidAmount().substring(1));
                            if (highestBid < currentBid) {
                                highestBid = currentBid;
                                highestBidder = b.getUser().getName();
                                contactNumber = b.getUser().getContactNumber();
                                email = b.getUser().getEmail();
                            }
                        }
                    }
                    entity.add(highestBid);
                    entity.add(highestBidder);
                    entity.add(contactNumber);
                    entity.add(email);
                }
                entityList.add(entity);
            }
        }
        
        return entityList;
    }
    
    @Override
    public List <Vector> getClosedAuctions() {
        Query q = em.createQuery("SELECT v FROM Vehicle v");
        List <Vector> entityList = new ArrayList();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        dateFormat.format(currentDate);
        
        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            if (v.getAuctionEndTime().compareTo(currentDate) > 0) {
                Vector entity = new Vector();
                entity.add(v.getVehicleId());
                String auctionStartTime = dateFormat.format(v.getAuctionStartTime());
                String auctionEndTime = dateFormat.format(v.getAuctionEndTime());
                entity.add(auctionStartTime);
                entity.add(auctionEndTime);
                entity.add(v.getModel().getMake());
                entity.add(v.getModel().getModel()); // 5
                entity.add(v.getModel().getManufacturedYear());
                entity.add(v.getRegistrationNumber());
                entity.add(v.getChassisNumber());
                entity.add(v.getEngineNumber());
                entity.add(v.getDescription()); // 10
                entity.add(v.getStartingBid());
                if (v.getBids().isEmpty()) {
                    entity.add("None"); // Empty - 11 - 1 Array
                } else {
                    Query q2 = em.createQuery("SELECT b FROM Bid b");
                    int startingBid = Integer.valueOf(v.getStartingBid().substring(1));
                    int highestBid = startingBid;
                    String highestBidder = null;
                    String contactNumber = null;
                    String email = null;
                    for (Object o2: q2.getResultList()) {
                        BidEntity b = (BidEntity) o2;
                        if (b.getVehicle().getVehicleId() == v.getVehicleId()) {
                            int currentBid = Integer.valueOf(b.getBidAmount().substring(1));
                            if (highestBid < currentBid) {
                                highestBid = currentBid;
                                highestBidder = b.getUser().getName();
                                contactNumber = b.getUser().getContactNumber();
                                email = b.getUser().getEmail();
                            }
                        }
                    }
                    entity.add(highestBid);
                    entity.add(highestBidder);
                    entity.add(contactNumber);
                    entity.add(email); // 15 - 1 Array Non empty
                    
                    ArrayList<String[]> paymentDetails = new ArrayList<String[]>();
                    ArrayList<PaymentEntity> paymentRecord = (ArrayList) v.getPayments();
                    int totalAmount = 0;
                    
                    for (int x = 0; x < paymentRecord.size(); x++) {
                        String [] paymentArray = new String [3];
                        PaymentEntity p = paymentRecord.get(x);
                        paymentArray[0] = p.getPaymentAmount();
                        paymentArray[1] = p.getCardType();
                        paymentArray[2] = dateFormat.format(p.getPaymentTime());
                        paymentDetails.add(paymentArray);
                        totalAmount = Integer.valueOf(paymentArray[0]);
                    }
                    entity.add(paymentDetails);
                    entity.add(totalAmount); // 17 - 1 for Array
                }
                entityList.add(entity);
            }
        }
        return entityList;
    }
    
    @Override
    public List <Vector> getBids(int vehicleId) {
        Query q = em.createQuery("SELECT v FROM Vehicle v WHERE v.vehicleId = " + vehicleId);
        List <Vector> entityList = new ArrayList();
        ArrayList<String[]> bidDetails = new ArrayList<String[]>();
        String [] bidArray = new String [3];
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        
        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            ArrayList<BidEntity> bidList = (ArrayList) v.getBids();
            for (int i = 0; i < bidList.size(); i++) {
                bidArray[0] = bidList.get(i).getUser().getName();
                bidArray[1] = bidList.get(i).getBidAmount();
                bidArray[2] = dateFormat.format(bidList.get(i).getBidTime());
                bidDetails.add(bidArray);
            }
        }
        return entityList;
    }
    
    @Override
    public ArrayList <String[]> getCertificate() {
        Query q = em.createQuery("SELECT c FROM Certification c WHERE c.status != 'Processed'");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        ArrayList<String[]> certificateDetails = new ArrayList<String[]>();
        String [] certificateArray = new String [5];
        
        for (Object o: q.getResultList()) {
            CertificationEntity c = (CertificationEntity) o;
            certificateArray[0] = String.valueOf(c.getId());
            certificateArray[1] = c.getCertiferName();
            certificateArray[2] = dateFormat.format(c.getCertificationTime());
            certificateArray[3] = c.getCertificationContent();
            certificateArray[4] = String.valueOf(c.getVehicle().getVehicleId());
            certificateDetails.add(certificateArray);
        }
        return certificateDetails;
    }
    
    @Override
    public ArrayList <String[]> searchVehicle(String make, String model, String year, String status, String min, String max) {
        Query q = em.createQuery("SELECT v FROM Vehicle v");
        ArrayList<String[]> vehicleDetails = new ArrayList<String[]>();
        String [] vehicleArray = new String [8];
        
        if (make == null) {
                System.out.println("EMPTY");
            } else if (make.equals("")) {
                System.out.println("HEllo");
            } else {
                System.out.println("Lame");
            }
        
        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            
        }
        
        return vehicleDetails;
    }
    
    
    /* ------------------ ADDING  ------------------ */
    @Override
    public void addBid() {
        bids.add(bidEntity);
    }
    
    @Override
    public void addVehicle() {
        vehicles = modelEntity.getVehicles();
        vehicles.add(vehicleEntity);
    }

    /* ------------------ PERSIST ------------------ */
    @Override
    public void generalUserPersist() {
        em.persist(userEntity);
    }
    
    @Override
    public void generalModelPersist() {
        em.persist(modelEntity);
    }
    
    @Override
    public void persistUserBid() {
        userEntity.setBids(bids);
        em.persist(bidEntity);
        em.flush();
    }
    
    @Override
    public void persistModelVehicle() {
        modelEntity.setVehicles(vehicles);
        em.persist(vehicleEntity);
        em.flush();
    }
    
    /* ------------------ EXIST ------------------ */
    @Override
    public boolean userExist(String name) {
        Query q = em.createQuery("SELECT u FROM Username u WHERE u.name = '" + name + "'");
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean checkIfBidExist(String name) {
        Query q = em.createQuery("SELECT u FROM Username u");
        for (Object o: q.getResultList()) {
            UserEntity u = (UserEntity) o;
            if (u.getName().equalsIgnoreCase(name) && u.getBids().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean checkIfPaymentExist(String name) {
        Query q = em.createQuery("SELECT u FROM Username u");
        for (Object o: q.getResultList()) {
            UserEntity u = (UserEntity) o;
            if (u.getName().equalsIgnoreCase(name) && u.getPayments().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean modelExist(String make, String model, int manufacturedYear) {
        Query q = em.createQuery("SELECT m FROM Model m WHERE m.make = '" + make + "' AND m.model = '" + model + "' AND m.manufacturedYear = '" + manufacturedYear + "'");
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean modelIdExist(int modelId) {
        Query q = em.createQuery("SELECT m FROM Model m WHERE m.id = '" + modelId + "'");
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean checkIfVehicleAssociated(int modelId) {
        Query q = em.createQuery("SELECT v FROM Vehicle v");
        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            if (v.getModel().getId() == modelId) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean checkIfVehicleExist(int vehicleId) {
        Query q = em.createQuery("SELECT v FROM Vehicle v WHERE v.vehicleId = " + vehicleId);
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean checkIfVehicleHasBid(int vehicleId) {
        Query q = em.createQuery("SELECT v FROM Vehicle v WHERE v.vehicleId = " + vehicleId);
        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            if (v.getBids().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean checkPassword(String name, String password) {
        userEntity = em.find(UserEntity.class, name);
        if (userEntity.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    
    /* ------------------ UPDATE ------------------ */
    @Override
    public void updateModel(int modelId, String make, String model, int manufacturedYear) {
        // This is wrong - Reminder for future
        // Query q = em.createQuery("UPDATE Model m SET m.make = '" + make + "', m.model = '" + model + "', m.manufacturedYear = " + manufacturedYear + " WHERE m.id = " + modelId);
        
        modelEntity = em.find(ModelEntity.class, modelId);
        modelEntity.setMake(make);
        modelEntity.setModel(model);
        modelEntity.setManufacturedYear(manufacturedYear);
        em.persist(modelEntity);
        em.flush();
    }
    
    @Override
    public void updateVehicle(int vehicleId, String startingBid, String description, Date auctionEndTime) {
        vehicleEntity = em.find(VehicleEntity.class, Long.valueOf(vehicleId));
        vehicleEntity.setAuctionEndTime(auctionEndTime);
        if (startingBid != null && description != null) {
            vehicleEntity.setStartingBid(startingBid);
            vehicleEntity.setDescription(description);
        }
        em.persist(vehicleEntity);
        em.flush();
    }
    
    @Override
    public void updateCertificationStatus(int certificateId, String status) {
        certificationEntity = em.find(CertificationEntity.class, Long.valueOf(certificateId));
        certificationEntity.setStatus(status);
        em.persist(certificationEntity);
        em.flush();
    }
    
    @Override
    public void updateProfile(String username, String password, String contactNumber, String email) {
        userEntity = em.find(UserEntity.class, username);
        userEntity.setContactNumber(contactNumber);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        em.persist(userEntity);
        em.flush();
    }
    
    /* ------------------ DELETE ------------------ */
    @Override
    public void removeUser(String name) {
        userEntity = em.find(UserEntity.class, name);
        em.remove(userEntity);
        em.flush();
    }
    
    @Override
    public void removeModel(int modelId) {
        modelEntity = em.find(ModelEntity.class, modelId);
        em.remove(modelEntity);
        em.flush();
    }
    
    @Override
    public void removeVehicle(int vehicleId)  {
        vehicleEntity = em.find(VehicleEntity.class, Long.valueOf(vehicleId));
        em.remove(vehicleEntity);
        em.flush();
    }
    
    @Override
    /* ------------------ Other Function ------------------ */
    public boolean webLogin(String name, String password) {
        Query q = em.createQuery("SELECT u FROM Username u WHERE u.name = '" + name + "' AND u.password = '" + password + "'");
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
            
    /* ------------------ DEFAULT ------------------ */
    @Override
    @Remove
    public void remove() {
        System.out.println("VehicleAuctionManagerBean:remove()");
    }
}

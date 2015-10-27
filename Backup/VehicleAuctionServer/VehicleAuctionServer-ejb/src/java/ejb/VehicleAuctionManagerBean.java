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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private PaymentEntity paymentEntity;

    private Collection<VehicleEntity> vehicles;
    private Collection<BidEntity> bids;
    private Collection<PaymentEntity> payment;

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
    public void createBid(String name, int vehicleId, String bidAmount) {
        bidEntity = new BidEntity();
        userEntity = em.find(UserEntity.class, name);
        vehicleEntity = em.find(VehicleEntity.class, Long.valueOf(vehicleId));
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        dateFormat.format(currentDate);
        bidEntity.addBid(currentDate, bidAmount);
        bidEntity.setUser(userEntity);
        bidEntity.setVehicle(vehicleEntity);
        
        long diff = 0;
        String endingTime = dateFormat.format(vehicleEntity.getAuctionEndTime());
        
        try {
            diff = dateFormat.parse(endingTime).getTime() - currentDate.getTime();
        } catch (ParseException ex) {
            System.out.println("Error with getting remaining time");
        }
        
        // Add 1 hr if end time less than 1 hr
        if (diff < 3600000) {
            //Date newEndTime = new Date(vehicleEntity.getAuctionEndTime().getTime() + (1000 * 60 * 60));
            //vehicleEntity.setAuctionEndTime(newEndTime);
        }

        bids = userEntity.getBids();
        bids.add(bidEntity);
        userEntity.setBids(bids);

        bids = vehicleEntity.getBids();
        bids.add(bidEntity);
        vehicleEntity.setBids(bids);

        em.persist(bidEntity);
        em.merge(vehicleEntity);
        em.merge(userEntity); // New added
        em.flush();
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
        em.merge(modelEntity);
        em.flush();
    }
    
    @Override
    public void createPayment(String username, String vehicleId, String cardType, String cardNo, String holder, String amount) {
        userEntity = em.find(UserEntity.class, username);
        vehicleEntity = em.find(VehicleEntity.class, Long.valueOf(vehicleId));
        paymentEntity = new PaymentEntity();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        dateFormat.format(currentDate);
        
        paymentEntity.addPayment(cardType, cardNo, holder, amount, currentDate);
        paymentEntity.setUser(userEntity);
        paymentEntity.setVehicle(vehicleEntity);
        
        payment = vehicleEntity.getPayments();
        payment.add(paymentEntity);
        vehicleEntity.setPayments(payment);
        
        payment = userEntity.getPayments();
        payment.add(paymentEntity);
        userEntity.setPayments(payment);
        
        em.persist(paymentEntity);
        em.merge(vehicleEntity);
        em.merge(userEntity);
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
                    diff = dateFormat.parse(auctionEndTime).getTime() - currentDate.getTime();
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
                        if (b.getVehicle().getVehicleId().equals(v.getVehicleId())) {
                            int currentBid = Integer.valueOf(b.getBidAmount());
                            if (highestBid <= currentBid) {
                                System.out.println("HELLO" + currentBid);
                                highestBid = currentBid;
                                System.out.println("HELLO" + b.getUser().getName());
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
            if (v.getAuctionEndTime().compareTo(currentDate) < 0) {
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
                        if (b.getVehicle().getVehicleId().equals(v.getVehicleId())) {
                            int currentBid = Integer.valueOf(b.getBidAmount());
                            if (highestBid <= currentBid) {
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
                    int totalAmount = 0;
                    
                    Query q3 = em.createQuery("SELECT p FROM Payment p");
                    for (Object o3: q3.getResultList()) {
                        String [] paymentArray = new String [3];
                        PaymentEntity p = (PaymentEntity) o3;
                        
                        if (p.getVehicle().getVehicleId() == v.getVehicleId()) {
                            paymentArray[0] = p.getPaymentAmount();
                            paymentArray[1] = p.getCardType();
                            paymentArray[2] = dateFormat.format(p.getPaymentTime());
                            paymentDetails.add(paymentArray);
                            totalAmount += Integer.valueOf(paymentArray[0]);
                        }
                        
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
    public ArrayList <String[]> getBids(int vehicleId) {
        Query q = em.createQuery("SELECT v FROM Vehicle v WHERE v.vehicleId = " + vehicleId);
        ArrayList<String[]> bidDetails = new ArrayList<String[]>();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        for (Object o: q.getResultList()) {
            VehicleEntity v = (VehicleEntity) o;
            
            Query q2 = em.createQuery("SELECT b FROM Bid b");
            for (Object o2: q2.getResultList()) {
                BidEntity b = (BidEntity) o2;
                String [] bidArray = new String [3];
                System.out.println(vehicleId + " vs " + b.getVehicle().getVehicleId());
                if (b.getVehicle().getVehicleId() == vehicleId) {
                    bidArray[0] = b.getUser().getName();
                    bidArray[1] = b.getBidAmount();
                    bidArray[2] = dateFormat.format(b.getBidTime());
                    bidDetails.add(bidArray);
                }
            }
        }
        return bidDetails;
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

        //System.out.println(" | " + max + " | " + model + " | " + year + " | " + status + " | " + min + " | " + max + " | ");

        for (Object o: q.getResultList()) {
            String [] vehicleArray = new String [9];

            boolean vehicleCriteria = true;
            VehicleEntity v = (VehicleEntity) o;
            if (!make.equals("")) {
                if (!v.getModel().getMake().equalsIgnoreCase(make)) {
                    vehicleCriteria = false;
                }
            }
            System.out.println("Make: " + vehicleCriteria);
            if (!model.equals("")) {
                if (!v.getModel().getModel().equalsIgnoreCase(model)) {
                    vehicleCriteria = false;
                }
            }
            System.out.println("Model: " + vehicleCriteria);
            if (!year.equals("")) {
                if (v.getModel().getManufacturedYear() != Integer.valueOf(year)) {
                    vehicleCriteria = false;
                }
            }
            System.out.println("Year: " + vehicleCriteria);

            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            dateFormat.format(currentDate);

            String vehicleStatus = null;

            if (v.getAuctionEndTime().compareTo(currentDate) > 0) {
                vehicleStatus = "Open";
            } else {
                vehicleStatus = "Closed";
            }

            if (status.equals("Both")) {
            } else if (status.equals("Open") && !vehicleStatus.equals("Open")) {
                vehicleCriteria = false;
            } else if (status.equals("Closed") && !vehicleStatus.equals("Closed")) {
                vehicleCriteria = false;
            }
            System.out.println("Status: " + vehicleCriteria);

            int highestBid = 0;

            if (v.getBids().isEmpty()) {
                // Starting bid no one yet
                highestBid = Integer.valueOf(v.getStartingBid().substring(1));
            } else {
                // Find highest bid
                Query q2 = em.createQuery("SELECT b FROM Bid b");
                int startingBid = Integer.valueOf(v.getStartingBid().substring(1));
                highestBid = startingBid;

                for (Object o2: q2.getResultList()) {
                    BidEntity b = (BidEntity) o2;
                    if (b.getVehicle().getVehicleId().equals(v.getVehicleId())) {
                    int currentBid = Integer.valueOf(b.getBidAmount());
                        if (currentBid >= highestBid) {
                            highestBid = currentBid;
                        }
                    }
                }
            }

            if (!min.equals("") && !max.equals("")) {
                if (highestBid < Integer.valueOf(min) && highestBid > Integer.valueOf(max)) {
                    vehicleCriteria = false;
                }
            } else if (!min.equals("") && highestBid < Integer.valueOf(min)) {
                vehicleCriteria = false;
            } else if (!max.equals("") && highestBid > Integer.valueOf(max)) {
                vehicleCriteria = false;
            }

            System.out.println("$$: " + vehicleCriteria);

            if (vehicleCriteria == true) {
                vehicleArray[0] = v.getModel().getMake();
                vehicleArray[1] = v.getModel().getModel();
                vehicleArray[2] = String.valueOf(v.getModel().getManufacturedYear());
                vehicleArray[3] = v.getDescription();
                vehicleArray[4] = vehicleStatus;

                long diff = 0;
                String endingTime = dateFormat.format(v.getAuctionEndTime());
                try {
                    diff = dateFormat.parse(endingTime).getTime() - currentDate.getTime();
                } catch (ParseException ex) {
                    System.out.println("Error with getting remaining time");
                }

                String diffSeconds = Long.toString(diff / 1000 % 60);
                String diffMinutes = Long.toString(diff / (60 * 1000) % 60);
                String diffHours = Long.toString(diff / (60 * 60 * 1000) % 24);
                String diffDays = Long.toString(diff / (24 * 60 * 60 * 1000));

                if (vehicleStatus.equals("Open")) {
                    vehicleArray[5] = "Time Left: " + diffDays + "d " + diffHours + "h " + diffMinutes + "m " + diffSeconds + "s";
                } else {
                    vehicleArray[5] = "Ended: " + v.getAuctionEndTime();
                }

                vehicleArray[6] = v.getStartingBid();
                if (vehicleStatus.equals("Open") && v.getBids().isEmpty()) {
                    vehicleArray[7] = "Current Bid: No bidding yet";
                } else if (vehicleStatus.equals("Open")) {
                    vehicleArray[7] = "Current Bid: " + highestBid;
                } else if (vehicleStatus.equals("Closed") && v.getBids().isEmpty()) {
                    vehicleArray[7] = "Closing Price: Unsold";
                } else {
                    vehicleArray[7] = "Closing Price: " + highestBid;
                }
                vehicleArray[8] = String.valueOf(v.getVehicleId());
                vehicleDetails.add(vehicleArray);
            }
        }
        return vehicleDetails; // Must check empty in case no vehicle
    }

    @Override
    public Map <String, String> getDetailedVehicle(int vehicleId) {
        Query q = em.createQuery("SELECT v FROM Vehicle v WHERE v.vehicleId = " + vehicleId);

        Map <String, String> detailedVehicle = new HashMap<String, String>();

        for (Object o: q.getResultList()) { // There is only 1
            VehicleEntity v = (VehicleEntity) o;
            detailedVehicle.put("vehicleId", String.valueOf(v.getVehicleId()));
            detailedVehicle.put("make", v.getModel().getMake());
            detailedVehicle.put("model", v.getModel().getModel());
            detailedVehicle.put("year", String.valueOf(v.getModel().getManufacturedYear()));
            detailedVehicle.put("description", v.getDescription());
            detailedVehicle.put("registration", v.getRegistrationNumber());
            detailedVehicle.put("chassis", v.getChassisNumber());
            detailedVehicle.put("engine", v.getEngineNumber());

            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            dateFormat.format(currentDate);

            String vehicleStatus = null;

            if (v.getAuctionEndTime().compareTo(currentDate) > 0) {
                detailedVehicle.put("status", "Open");
                vehicleStatus = "Open";
            } else {
                detailedVehicle.put("status", "Closed");
                vehicleStatus = "Closed";
            }

            long diff = 0;
            String endingTime = dateFormat.format(v.getAuctionEndTime());
            try {
                diff = dateFormat.parse(endingTime).getTime() - currentDate.getTime();
            } catch (ParseException ex) {
                System.out.println("Error with getting remaining time");
            }

            String diffSeconds = Long.toString(diff / 1000 % 60);
            String diffMinutes = Long.toString(diff / (60 * 1000) % 60);
            String diffHours = Long.toString(diff / (60 * 60 * 1000) % 24);
            String diffDays = Long.toString(diff / (24 * 60 * 60 * 1000));

            if (vehicleStatus.equals("Open")) {
                detailedVehicle.put("remaining", "Time Left: " + diffDays + "d " + diffHours + "h " + diffMinutes + "m " + diffSeconds + "s");
            } else {
                detailedVehicle.put("remaining", "Ended: " + v.getAuctionEndTime());
            }
            
            Query q2 = em.createQuery("SELECT b FROM Bid b");
            int highestBid = 0;
            int startingBid = Integer.valueOf(v.getStartingBid().substring(1));

            if (v.getBids().isEmpty()) {
                // Starting bid no one yet
                highestBid = Integer.valueOf(v.getStartingBid().substring(1));
            } else {
                // Find highest bid
                for (Object o2: q2.getResultList()) {
                    BidEntity b = (BidEntity) o2;
                    highestBid = startingBid;
                    if (b.getVehicle().getVehicleId() == vehicleId) {
                        int currentBid = Integer.valueOf(b.getBidAmount());
                        if (currentBid >= highestBid) {
                            highestBid = currentBid;
                        }
                    }
                }
            }
            
            detailedVehicle.put("startBid", v.getStartingBid());
            if (vehicleStatus.equals("Open") && v.getBids().isEmpty()) {
                detailedVehicle.put("currentBid", "Current Bid: No bidding yet");
            } else if (vehicleStatus.equals("Open")) {
                detailedVehicle.put("currentBid", "Current Bid: " + highestBid);
            } else if (vehicleStatus.equals("Closed") && v.getBids().isEmpty()) {
                detailedVehicle.put("currentBid", "Closing Price: Unsold");
            } else {
                detailedVehicle.put("currentBid", "Closing Price: " + highestBid);
            }

            if (v.getBids().isEmpty()) {
                detailedVehicle.put("highestBid", v.getStartingBid());
            } else {
                detailedVehicle.put("highestBid", "$" + String.valueOf(highestBid + 100));
            }
            
            if (v.getCertification() != null) {
                if (v.getCertification().getStatus().equals("Processed")) {
                    detailedVehicle.put("certificate", "Certification Not empty");
                    detailedVehicle.put("certificateName", v.getCertification().getCertiferName());
                    detailedVehicle.put("certificateTime", dateFormat.format(v.getCertification().getCertificationTime()));
                    detailedVehicle.put("certificateContent", v.getCertification().getCertificationContent());
                }
            }

            if (!v.getBids().isEmpty()) {
                Query q3 = em.createQuery("SELECT b FROM Bid b");
                int counter = 0;
                
                for (Object o3: q3.getResultList()) {
                    BidEntity b = (BidEntity) o3;
                    if (b.getVehicle().getVehicleId() == vehicleId) {
                        String bidder = "bidder" + counter;
                        String bidTime = "bidTime" + counter;
                        String bidAmount = "bidAmount" + counter;
                        detailedVehicle.put(bidder, b.getUser().getName());
                        detailedVehicle.put(bidTime, dateFormat.format(b.getBidTime()));
                        detailedVehicle.put(bidAmount, b.getBidAmount());
                        counter++;
                    }
                }
                detailedVehicle.put("bidSize", String.valueOf(counter));
            }
        }
        return detailedVehicle;
    }
    
    @Override
    public ArrayList <String[]> getUserBids(String username) {
        userEntity = em.find(UserEntity.class, username);
        ArrayList<String[]> bidDetails = new ArrayList<String[]>();
        
        if (userEntity.getBids().isEmpty()) {
            bidDetails = null;
        } else {
            Query q = em.createQuery("SELECT b FROM Bid b");
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            dateFormat.format(currentDate);
            
            for (Object o: q.getResultList()) {
                BidEntity b = (BidEntity) o;
                String [] details = new String [7];
                if (b.getUser().getName().equals(username)) {
                    details[0] = b.getVehicle().getModel().getMake();
                    details[1] = b.getVehicle().getModel().getModel();
                    details[2] = dateFormat.format(b.getVehicle().getModel().getManufacturedYear());
                    details[3] = b.getBidAmount();
                    details[4] = dateFormat.format(b.getBidTime());
                    if (b.getVehicle().getAuctionEndTime().compareTo(currentDate) > 0) {
                        details[5] = "Open";
                    } else {
                        details[5] = "Closed";
                    }
                    details[6] = String.valueOf(b.getVehicle().getVehicleId());
                    bidDetails.add(details);
                }
            }
        }
        return bidDetails;
    }
    
    @Override
    public ArrayList <String[]> getPastWonAuction(String username) {
        userEntity = em.find(UserEntity.class, username);
        ArrayList<String[]> bidDetails = new ArrayList<String[]>();
        
        if (userEntity.getBids().isEmpty()) {
            bidDetails = null;
        } else {
            //bidDetails = null; // If no bid details
            Query q = em.createQuery("SELECT b FROM Bid b");
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            dateFormat.format(currentDate);
            
            for (Object o: q.getResultList()) {
                BidEntity b = (BidEntity) o;
                if (b.getUser().getName().equals(username)) {
                    String currentStatus;
                    if (b.getVehicle().getAuctionEndTime().compareTo(currentDate) > 0) {
                        currentStatus = "Open";
                    } else {
                        currentStatus = "Closed";
                    }
                    
                    if (currentStatus.equals("Closed")) {
                        Query q2 = em.createQuery("SELECT b FROM Bid b");
                        int highestBid = 0;
                        int startingBid = Integer.valueOf(b.getVehicle().getStartingBid().substring(1));
                        highestBid = startingBid;
                        
                        for (Object o2: q2.getResultList()) {
                            BidEntity b2 = (BidEntity) o2;
                            if (b2.getVehicle().getVehicleId() == b.getVehicle().getVehicleId()) {
                                if (highestBid <= Integer.valueOf(b2.getBidAmount())) {
                                    highestBid = Integer.valueOf(b2.getBidAmount());
                                }
                            }
                        }
                        
                        String [] details = new String [8];
                        if (Integer.valueOf(b.getBidAmount()) == highestBid) {
                            details[0] = b.getVehicle().getModel().getMake();
                            details[1] = b.getVehicle().getModel().getModel();
                            details[2] = String.valueOf(b.getVehicle().getModel().getManufacturedYear());
                            details[3] = b.getBidAmount();
                            details[4] = dateFormat.format(b.getVehicle().getAuctionEndTime());
                            
                            int totalPaid = 0;
                            Query q3 = em.createQuery("SELECT p FROM Payment p");
                            for (Object o3: q3.getResultList()) {
                                PaymentEntity p = (PaymentEntity) o3;
                                if (p.getUser().getName().equals(username) && p.getVehicle().getVehicleId() == b.getVehicle().getVehicleId()) {
                                    totalPaid = totalPaid + Integer.valueOf(p.getPaymentAmount());
                                }
                            }
                            details[5] = String.valueOf(totalPaid);
                            details[6] = String.valueOf((Integer.valueOf(b.getBidAmount()) - totalPaid));
                            details[7] = String.valueOf(b.getVehicle().getVehicleId());
                            bidDetails.add(details);
                        }
                    }
                }
            }
        }
        return bidDetails;
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

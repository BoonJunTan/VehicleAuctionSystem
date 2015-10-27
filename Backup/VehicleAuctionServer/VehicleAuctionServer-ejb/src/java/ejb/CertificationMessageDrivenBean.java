/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
@MessageDriven(mappedName = "jms/Topic", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/Topic")
})

public class CertificationMessageDrivenBean implements MessageListener {
    @PersistenceContext()
    EntityManager em;
    CertificationEntity certificationEntity;
    VehicleEntity vehicleEntity;

    @Resource(mappedName = "jms/TopicConnectionFactory")
    private ConnectionFactory topicConnectionFactory;

    private Random processingTime = new Random();

    public CertificationMessageDrivenBean() {
    }

    /* ------------------ CREATION  ------------------ */
    public void createCertification(String username, String vehicleId, String content) {
        vehicleEntity = em.find(VehicleEntity.class, Long.valueOf(vehicleId));
        certificationEntity = new CertificationEntity();
        certificationEntity.addCertificate(username, content);
        certificationEntity.setVehicle(vehicleEntity);

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        dateFormat.format(currentDate);

        certificationEntity.setCertificationTime(currentDate);
        certificationEntity.setStatus("Unread");

        vehicleEntity.setCertification(certificationEntity);

        em.persist(certificationEntity);
        em.merge(vehicleEntity);
        em.flush();
    }
    
    public boolean vehicleExist (String vehicleId) {
        vehicleEntity = em.find(VehicleEntity.class, Long.valueOf(vehicleId));
        if (vehicleEntity  == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean checkCertificationExist (String vehicleId) {
        vehicleEntity = em.find(VehicleEntity.class, Long.valueOf(vehicleId));
        Query q = em.createQuery("SELECT c FROM Certification c");
        
        for (Object o: q.getResultList()) {
            CertificationEntity c = (CertificationEntity) o;
            if (vehicleEntity.getVehicleId().equals(c.getVehicle().getVehicleId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onMessage(Message inMessage) {
        MapMessage msg = null;
        try {
            if (inMessage instanceof MapMessage) {
                msg = (MapMessage) inMessage;
                Thread.sleep(processingTime.nextInt(5) * 1000);
                setUpEntities(msg);
            } else {
                System.out.println("CertificatonMessageDrivenBean.onMessage: " +
                  "Message of wrong type: " + inMessage.getClass().getName());
            }
        } catch (InterruptedException ie) {
            System.out.println("CertificatonMessageDrivenBean.onMessage: " +
                               "InterruptedException: " + ie.toString());
        } catch (Throwable te) {
            System.out.println("CertificatonMessageDrivenBean.onMessage: Exception: " +
              te.toString());
        }
    }

    void setUpEntities(MapMessage msg) {
        String vehicleId = null;
        String content = null;
        String username = null;

        Connection connection         = null;
        Session session               = null;
        MessageProducer producer      = null;
        MapMessage replyMsg           = null;
        Destination replyDest         = null;
        String replyCorrelationMsgId  = null;
        boolean done                  = false;

        // Unused;
        Session queueSession          = null;
        MessageProducer queueProducer = null;
        TextMessage message           = null;

        String replyMessageForClient = null;
        
        try {
            vehicleId     = msg.getString("VehicleId");
            content   = msg.getString("Content");
            username  = msg.getString("Username");
            System.out.println("Username: " + username + " | Vehicle Id: " + vehicleId + " | Content: " + content);
            if (vehicleExist(vehicleId) == true) {
                if (checkCertificationExist(vehicleId) == true) {
                    replyMessageForClient = "Vehicle already has certificates!\n";
                } else {
                    createCertification(username, vehicleId, content);
                    replyMessageForClient = "Certification added successfully\n";
                }
            } else {
                replyMessageForClient = "Vehicle does not exist in database!\n";
            }
             
        } catch (IllegalArgumentException iae) {
            System.out.println("CertificationMessageDrivenBean.setUpEntities: " +
                               "No entity found");
        } catch (Exception e) {
            System.out.println("CertificationMessageDrivenBean.setUpEntities: " +
                               "em.find failed without throwing " +
                               "IllegalArgumentException");
        }

        try {
            connection = topicConnectionFactory.createConnection();
        } catch (Exception ex) {
            System.out.println("CertificationMessageDrivenBean.setUpEntities: " +
              "Unable to connect to JMS provider: " + ex.toString());
        }

        try {
            // Call createReplyMsg to construct the reply.
            replyDest = msg.getJMSReplyTo();
            replyCorrelationMsgId = msg.getJMSMessageID();
            session  = connection.createSession(true, 0);
            producer = session.createProducer(replyDest);
            replyMsg = createReplyMsg(session, replyCorrelationMsgId, replyMessageForClient);
            producer.send(replyMsg);
            System.out.println("CertificationMessageDrivenBean.setUpEntities: " + "Sent reply message for passengerId ");
        } catch (JMSException je) {
            System.out.println("CertificationMessageDrivenBean.setUpEntities: " +
              "JMSException: " + je.toString());
        } catch (Exception e) {
            System.out.println("CertificationMessageDrivenBean.setUpEntities: " +
                          "Exception: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException je) {
                    System.out.println("CertificationMessageDrivenBean.setUpEntities: " +
                      "JMSException: " + je.toString());
                }
                connection = null;
            }
        }
    }

    // The createReplyMsg method composes the reply message
    private MapMessage createReplyMsg(Session session, String msgId, String msgContent)
        throws JMSException {
        MapMessage replyMsg = session.createMapMessage();
        replyMsg.setString("Message", msgContent);
        replyMsg.setJMSCorrelationID(msgId);
        return replyMsg;
    }

}

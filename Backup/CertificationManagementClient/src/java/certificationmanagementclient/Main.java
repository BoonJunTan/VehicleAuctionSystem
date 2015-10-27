/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package certificationmanagementclient;

import java.util.Scanner;
import javax.ejb.EJB;
import ejb.CertificationManagementServerBeanRemote;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
public class Main {
    @EJB
    private static CertificationManagementServerBeanRemote cmsbr;
    
    @Resource(mappedName = "jms/TopicConnectionFactory")
    private static ConnectionFactory topicVehicleAuctionConnectionFactory;
    @Resource(mappedName = "jms/Topic")
    private static Topic topic;
    
    static Object waitUntilDone      = new Object();
    static int    requestOutstanding = 0;
    
    public static void main(String[] args) {
        Connection topicConnection = null;
        Session session            = null;
        MapMessage message         = null;
        Queue replyQueue           = null;
        MessageProducer producer   = null;
        MessageConsumer consumer   = null;
    
        //System.out.println("Main: Certification Management Client started... ");
        while (true) {
            try {
                System.out.println("*************************************");
                System.out.println(" Welcome to CMS User Portal");
                System.out.println("*************************************");

                Scanner sc = new Scanner(System.in);

                // No login is required. 
                // External certifier only needs to input his/her name and provide a certification to a vehicle Id. 
                System.out.print("Enter your user name: ");
                String username = sc.nextLine();

                // If the vehicle Id is not present, an error should be returned and only one certification is allowed per vehicle. 
                System.out.print("Enter vehicle id: ");
                String vehicleId = sc.nextLine();

                System.out.print("Enter the request content: ");
                String content = sc.nextLine();

                // cmsbr.sendMessage(username, vehicleId, content);
                // The certification time is system-generated, and the certification status is set to “unread” by default.
                topicConnection = topicVehicleAuctionConnectionFactory.createConnection();
                session = topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                replyQueue = session.createTemporaryQueue();
                consumer   = session.createConsumer(replyQueue);
                consumer.setMessageListener(new CertificationListener());
                topicConnection.start();

                producer = session.createProducer(topic);

                message  = session.createMapMessage();
                message.setJMSReplyTo(replyQueue);

                message.setString("Username", username);
                message.setString("VehicleId", vehicleId);
                message.setString("Content", content);

                System.out.println("Certification details sent: Username: " +
                      message.getString("Username") + ", Vehicle Id: " +
                      message.getString("VehicleId") + 
                      " with Content: " + message.getString("Content"));
                producer.send(message);        
                System.out.println("Certification details sent out\n");
                
                synchronized (waitUntilDone) {
                    waitUntilDone.wait();
                }  
            } catch (Exception e) {
                System.err.println("Certification Main: Exception: " +  e.toString());
            } finally {
                if (topicConnection != null) {
                    try {
                        topicConnection.close();
                    } catch (Exception e) {
                        System.out.println("Certification Main: " + "Close exception: " + e.toString());
                    }
                }
            }
        }
    }
    
    static class CertificationListener implements MessageListener {
        
        public void onMessage(Message message) {
            MapMessage msg = (MapMessage) message;
            
            //System.out.println("certificationListener.onMessage(): " + "Processing map messages...");
            
            try {
                //System.out.println("Processing message: " + msg.getJMSCorrelationID());
                System.out.println("Message From Server: " + msg.getString("Message"));
            } catch (JMSException je) {
                System.out.println("CertificateListener.onMessage(): " + "Exception: " + je.toString());
            }
            
            synchronized (waitUntilDone) {
                waitUntilDone.notify();
            }
        }
    }
}

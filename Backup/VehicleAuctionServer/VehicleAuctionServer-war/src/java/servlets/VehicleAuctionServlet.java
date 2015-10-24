/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.VehicleAuctionManagerBeanRemote;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tan Boon Jun, A0125418J
 */
public class VehicleAuctionServlet extends HttpServlet {
    ejb.VehicleAuctionManagerBeanRemote vambr = lookupVehicleAuctionManagerBeanRemote();
    private ArrayList data = null;
    
    private String username = null;
    
    public void init() {
        System.out.println("Vehicle Auction Servlet: init()");
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("VehicleAuctionServlet: processRequest() ");
        
        try {
            RequestDispatcher dispatcher;
            ServletContext servletContext = getServletContext();
            
            String page = request.getPathInfo();
            page = page.substring(1);
            
            if ("registerStatus".equals(page)) {
                data = register(request);
                request.setAttribute("data", data);
            } else if ("loginStatus".equals(page)) {
                data = login(request);
                request.setAttribute("data", data);
            } else if ("logoutStatus".equals(page)) {
                data = logout(request);
                request.setAttribute("data", data);
            } else if ("updateProfileStatus".equals(page)) {
                data = updateProfile(request);
                request.setAttribute("data", data);
            } else if ("searchVehicleStatus".equals(page)) {
                data = searchVehicle(request);
                request.setAttribute("data", data);
            }
            else {
                page = "error";
            }
            
            dispatcher = servletContext.getNamedDispatcher(page); 
            
            System.out.println(page);
            // The dispatcher uses the Servlet Name in the web.xml
            // Therefore, the value of variable page must match
            // the Servlet Name in the web.xml

            if (dispatcher == null) {
                dispatcher = servletContext.getNamedDispatcher("error");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            log("Exception in VehicleAuctionServlet.processRequest()");
        }
    }
    
    private ArrayList register (HttpServletRequest request) {
        ArrayList al = new ArrayList();
        String name = request.getParameter("txtUserName");
        String password = request.getParameter("txtPass");
        String contactNumber = request.getParameter("txtContactNumber");
        String email = request.getParameter("txtEmail");
        if (vambr.userExist(name) == true) {
            al.add("Username already exist in database");
        } else {
            vambr.createUser(name, password, contactNumber, email);
            vambr.generalUserPersist();
            al.add("Customer added successfully.");
        }
        return al;
    }
    
    private ArrayList login (HttpServletRequest request) {
        ArrayList al = new ArrayList();
        String name = request.getParameter("txtUserName");
        String password = request.getParameter("txtPass");
        if (vambr.webLogin(name, password) == true) {
            al.add("Login successfully.\n Welcome, " + name);
            al.add(name);
            username = name;
        } else {
            al.add("Username/Password invalid");
        }
        return al;
    }
    
    private ArrayList logout (HttpServletRequest request) {
        ArrayList al = new ArrayList();
        al.add("Logout successfully");
        username = null;
        return al;
    }
    
    private ArrayList updateProfile (HttpServletRequest request) {
        ArrayList al = new ArrayList();
        String oldPassword = request.getParameter("txtOldPass");
        String password = request.getParameter("txtPass");
        String contactNumber = request.getParameter("txtContactNumber");
        String email = request.getParameter("txtEmail");
        if (vambr.checkPassword(username, oldPassword) == true) {
            vambr.updateProfile(username, password, contactNumber, email);
            al.add("Profile successfully updated");
        } else {
            al.add("Invalid password");
        }
        return al;
    }
    
    private ArrayList searchVehicle (HttpServletRequest request) {
        ArrayList al = new ArrayList();
        String make = request.getParameter("txtMake");
        String model = request.getParameter("txtModel");
        String year = request.getParameter("txtYear");
        String status = request.getParameter("txtStatus");
        String min = request.getParameter("txtMin");
        String max = request.getParameter("txtMax");
        
        ArrayList<String[]> listOfVehicles = vambr.searchVehicle(make, model, year, status, min, max);
        
        return al;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("VehicleAuctionServlet: doGet()");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("VehicleAuctionServlet: doPost()");                
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    @Override        
    public void destroy() {
         System.out.println("Vehicle Auction Servlet: destroy()");             
    }

    private ejb.VehicleAuctionManagerBeanRemote lookupVehicleAuctionManagerBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ejb.VehicleAuctionManagerBeanRemote) c.lookup("java:global/VehicleAuctionServer/VehicleAuctionServer-ejb/VehicleAuctionManagerBean!ejb.VehicleAuctionManagerBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    

}

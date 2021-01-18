/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ContactDTO;
import dto.ContactsDTO;
import entities.Contact;
import facades.ContactFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Dane
 */
@Path("contact")
public class ContactResource {

    @Context
    private UriInfo context;
   
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ContactFacade FACADE = ContactFacade.getContactFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Creates a new instance of ContactResource
     */
    public ContactResource() {
    }

    /**
     * Retrieves representation of an instance of rest.ContactResource
     * @return an instance of java.lang.String
     */
    @Path("allContacts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllContacts() {
        ContactsDTO contacts = FACADE.getAllContacts();
        return GSON.toJson(contacts.getAll());
                
    }

    /**
     * PUT method for updating or creating an instance of ContactResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @Path("getContactById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findContactById(@PathParam("id")int id) {
        Contact c = FACADE.getContactById(id);
        return new Gson().toJson(c);
    }
    
    @Path("remove/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String removeContactById(@PathParam("id")int id) {
        ContactDTO c = FACADE.deleteContact(id);
        return new Gson().toJson(c);
    }
    
    @Path("update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateContactById(@PathParam("id")int id){
        ContactDTO c = FACADE.updateContact(id);
        return new Gson().toJson(c);
    }        
            
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addContact (String contact){
        ContactDTO c = GSON.fromJson(contact, ContactDTO.class);
        ContactDTO contactAdded = FACADE.addContact(c);
        return GSON.toJson(contactAdded);
        
    }
}

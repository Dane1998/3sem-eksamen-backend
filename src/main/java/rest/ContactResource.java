/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.ContactDTO;
import dto.ContactsDTO;
import dto.OpportunityDTO;
import entities.Contact;
import errorhandling.ContactNotFoundException;
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
import javax.ws.rs.core.Response;
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
    public String removeContactById(@PathParam("id")int id) throws ContactNotFoundException {
        ContactDTO c = FACADE.deleteContact(id);
        return new Gson().toJson(c);
    }
    
    @Path("update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateContact(String jsonString) throws ContactNotFoundException{
        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            int contactId = json.get("id").getAsInt();
            Contact contact = FACADE.getContactById(contactId);
            
            if(json.has("name")) {
                contact.setName(json.get("name").getAsString());
            }
            if(json.has("email")){
               contact.setEmail(json.get("email").getAsString());
            }
            if(json.has("company")){
                contact.setCompany(json.get("company").getAsString());
            }
            if(json.has("jobtitle")){
                contact.setJobtitle(json.get("jobtitle").getAsString());
            }
            if(json.has("phone")){
                contact.setPhone(json.get("phone").getAsString());
            }
            
            FACADE.updateContact(contactId);
            JsonObject response = new JsonObject();
            response.addProperty("message", String.format("Successfully updated team %d", contactId));
            return Response.ok(new Gson().toJson(response)).build();
        }
        catch (Exception e) {
            return Response.status(400, "Malformed JSON supplied").build();
        }
    }        
            
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addContact (String contact){
        ContactDTO c = GSON.fromJson(contact, ContactDTO.class);
        ContactDTO contactAdded = FACADE.addContact(c);
        return GSON.toJson(contactAdded);       
    }
    
    @Path("addOpportunity")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addOpportunity (String opportunity){
        OpportunityDTO o = GSON.fromJson(opportunity, OpportunityDTO.class);
        OpportunityDTO opportunityAdded = FACADE.addOpportunity(o);
        return GSON.toJson(opportunityAdded);       
    }
    
}

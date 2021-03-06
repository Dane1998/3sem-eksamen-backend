package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import facades.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

@Path("info")
public class UserResource {

    public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    public UserResource() {
    }
    
     @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(String jsonString) throws AuthenticationException, API_Exception {
        String username;
        String password;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            username = json.get("username").getAsString();
            password = json.get("password").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        User user = USER_FACADE.addUser(username, password);
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("username", username);
        responseJson.addProperty("msg", "Welcome on board!");

        return Response.ok(new Gson().toJson(responseJson)).build();

    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getAllMembers() {
        List<User> allMembers = USER_FACADE.getAllUsers();
        return allMembers;
    }
    
    
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(String jsonString) throws AuthenticationException, API_Exception {
        String username;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            username = json.get("username").getAsString();
        }
        catch (Exception e) {
             throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
        User user = USER_FACADE.deleteUser(username);
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("username", username);
        return Response.ok(new Gson().toJson(responseJson)).build();
    }
}
package BO;

import DB.DAL.UserDb;
import ViewModel.UserViewModel;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Path("/Users")
public class UserService {

    private UserDb db;

    public UserService() {
        this.db = new UserDb();
    }

    @POST
    @Path("/All")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllUsers(UserViewModel user) {
        System.out.printf(user.getUsername());
        return Response.ok().entity(new Gson().toJson(user)).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)         //return
    @Consumes(MediaType.APPLICATION_JSON)   //parameter Argument
    public UserViewModel login(UserViewModel user) {
        user.setPassword(digestPassword(user.getPassword()));
        return ModelConverter.convertToUserViewModel(db.authenticate(user));
    }

    @POST
    @Path("/Register")
    @Consumes(MediaType.APPLICATION_JSON)   //parameter Argument
    public void register(UserViewModel user) {

        if(user.getPassword() == null)
            return;

        user.setPassword(digestPassword(user.getPassword()));
        System.out.println(user.toString());
        db.register(user);
    }


    /**
     * Hash method that takes a string and digest it.
     * @param password users
     * @return Digest
     */
    private String digestPassword(String password) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not digest password.");
            return null;
        }

        return new String(messageDigest.digest());
    }

}
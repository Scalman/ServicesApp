package BO;

import DB.DAL.UserDb;
import ViewModel.UserViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/User")
public class UserService {

    private UserDb db;

    public UserService() {
        this.db = new UserDb();
    }

    @GET
    @Path("/Users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@PathParam("id") String id) {
        int userId = Integer.parseInt(id);
        List<UserViewModel> users =  db.findUsers(userId).stream().map(ModelConverter::convertToUserViewModel).collect(Collectors.toList());
        Type usersType = new TypeToken<List<UserViewModel>>() {}.getType();
        String json = new Gson().toJson(users,usersType);
        return Response.ok().entity(json).build();
    }

    @GET
    @Path("/Users/{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByName(@PathParam("id") String id,@PathParam("name") String name) {
        int userId = Integer.parseInt(id);
        List<UserViewModel> users =  db.findUsersByName(userId,name).stream().map(ModelConverter::convertToUserViewModel).collect(Collectors.toList());
        Type usersType = new TypeToken<List<UserViewModel>>() {}.getType();
        String json = new Gson().toJson(users,usersType);
        return Response.ok().entity(json).build();
    }

    @POST
    @Path("/Login")
    @Produces(MediaType.TEXT_PLAIN)         //return
    @Consumes(MediaType.APPLICATION_JSON)   //parameter Argument
    public Response login(UserViewModel user) {
        user.setPassword(digestPassword(user.getPassword()));
        return Response.ok().entity(new Gson().toJson(ModelConverter.convertToUserViewModel(db.authenticate(user)))).build();
    }

    @POST
    @Path("/Register")
    @Consumes(MediaType.APPLICATION_JSON)   //parameter Argument
    public Response register(UserViewModel user) {

        if(user.getPassword() == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(new Exception("no password")).build();
        user.setPassword(digestPassword(user.getPassword()));
        db.register(user);
        return Response.ok().build();
    }


    /**
     * Hash method that takes a string and digest it.
     * @param password users
     * @return Digest
     */
    private String digestPassword(String password) {
        MessageDigest messageDigest;

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
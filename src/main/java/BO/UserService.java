package BO;

import DB.DAL.UserDb;
import DB.Entities.UserEntity;
import ViewModel.UserViewModel;

import javax.enterprise.inject.Model;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Path("/")
public class UserService {

    private UserDb db;

    public UserService() {
        this.db = new UserDb();
    }

    @GET
    @Path("/users")
    @Produces(MediaType.TEXT_PLAIN)
    public List<UserViewModel> getAllUsers(UserViewModel user, String searchTerm) {
        Collection<UserEntity> userEntities = db.findUsersByName(user, searchTerm);
        return userEntities.stream().map(ModelConverter::convertToUserViewModel).collect(Collectors.toList());
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
    @Path("/login")
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

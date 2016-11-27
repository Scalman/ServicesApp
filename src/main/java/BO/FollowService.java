package BO;

import DB.DAL.FollowDb;
import ViewModel.FollowViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;


@Path("/Follow")
public class FollowService {

    private FollowDb db;

    public FollowService(){
        this.db = new FollowDb();
    }

    @POST
    @Path("/Add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFriend(FollowViewModel follow) {
        db.addFollower(follow);
        return Response.ok().build();
    }

    @GET
    @Path("/Following/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getYourFollows(@PathParam("id") String id) {
        int userId = Integer.parseInt(id);
        List<FollowViewModel> follows =  db.findYourFollows(userId).stream().map(ModelConverter::convertToFollowViewModel).collect(Collectors.toList());
        Type type = new TypeToken<List<FollowViewModel>>() {}.getType();
        String json = new Gson().toJson(follows,type);
        return Response.ok().entity(json).build();
    }

    @GET
    @Path("/Following/{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getYourFollowsByName(@PathParam("id") String id,@PathParam("name") String name) {
        int userId = Integer.parseInt(id);
        List<FollowViewModel> follows =  db.findYourFollowsByName(userId,name).stream().map(ModelConverter::convertToFollowViewModel).collect(Collectors.toList());
        Type type = new TypeToken<List<FollowViewModel>>() {}.getType();
        String json = new Gson().toJson(follows,type);
        return Response.ok().entity(json).build();
    }


}

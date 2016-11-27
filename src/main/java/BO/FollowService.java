package BO;

import DB.DAL.FollowDb;
import DB.Entities.FollowEntity;
import ViewModel.FollowViewModel;
import ViewModel.UserViewModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by waleedhassan on 27/11/16.
 */
@Path("/Follow")
public class FollowService {

    private FollowDb db;

    public FollowService(){
        this.db = new FollowDb();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addFriend(FollowViewModel follow) {
        db.addFollower(follow);
    }

    @POST
    @Path("/getYourFollows")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public List<FollowViewModel> getYourFollows(UserViewModel user, String searchTerm) {
        Collection<FollowEntity> follows = db.findYourFollows(user, searchTerm);
        return follows.stream().map(ModelConverter::convertToFollowViewModel).collect(Collectors.toList());
    }
}

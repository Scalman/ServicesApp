package BO;

import DB.DAL.PostDb;
import DB.Entities.PostEntity;
import ViewModel.FollowViewModel;
import ViewModel.PostViewModel;
import ViewModel.UserViewModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by waleedhassan on 26/11/16.
 */

@Path("/Post")
public class PostMessageService {

    private PostDb db;
    public PostMessageService(){
        this.db = new PostDb();
    }

    @POST
    @Path("/Create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPost(PostViewModel post) {
        db.addPost(post);
    }

    @POST
    @Path("/getFollowingPosts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public List<PostViewModel> getFollowingPosts(List<FollowViewModel> follows) {
        Collection<PostEntity> postEntities = new ArrayList<>();
        for (FollowViewModel f:follows) {
            postEntities.addAll(db.findPostsByUser(f.getFollowing()));
        }

        return postEntities.stream().map(ModelConverter::convertToPostViewModel).collect(Collectors.toList());
    }

    @POST
    @Path("/getYourPosts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public List<PostViewModel> getYourPosts(UserViewModel user) {
        Collection<PostEntity> postEntities = db.findPostsByUser(user);
        return postEntities.stream().map(ModelConverter::convertToPostViewModel).collect(Collectors.toList());
    }
}

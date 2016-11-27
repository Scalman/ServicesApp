package BO;

import DB.DAL.PostDb;
import DB.Entities.PostEntity;
import ViewModel.FollowViewModel;
import ViewModel.PostViewModel;
import ViewModel.UserViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/Post")
public class PostMessageService {

    private PostDb db;
    public PostMessageService(){
        this.db = new PostDb();
    }

    @POST
    @Path("/Create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(PostViewModel post) {
        db.addPost(post);
        return Response.ok().build();
    }

    @POST
    @Path("/FollowingPosts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowingPosts(List<FollowViewModel> follows) {
        Collection<PostEntity> postEntities = new ArrayList<>();
        for (FollowViewModel f:follows) {
            postEntities.addAll(db.findPostsByUser(f.getFollowing()));
        }
        List<PostViewModel> posts =  postEntities.stream().map(ModelConverter::convertToPostViewModel).collect(Collectors.toList());
        Type type = new TypeToken<List<PostViewModel>>() {}.getType();
        String json = new Gson().toJson(posts,type);
        return Response.ok().entity(json).build();
    }

    @POST
    @Path("/YourPosts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getYourPosts(UserViewModel user) {
        List<PostViewModel> posts =  db.findPostsByUser(user).stream().map(ModelConverter::convertToPostViewModel).collect(Collectors.toList());
        Type type = new TypeToken<List<PostViewModel>>() {}.getType();
        String json = new Gson().toJson(posts,type);
        return Response.ok().entity(json).build();
    }
}

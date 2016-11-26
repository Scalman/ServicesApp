import DB.DAL.UserDb;
import DB.Entities.UserEntity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;


@Path("/get")
public class UserService {

    private UserDb db;
    @GET
    @Path("/users")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        this.db = new UserDb();
        String str = "";
        Collection<UserEntity> userEntities = db.findUsersByName();
        for (UserEntity u: userEntities) {
            str += u.getUsername() + "\n";
        }
        return str;
    }



}

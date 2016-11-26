import DB.DAL.UserDb;
import DB.Entities.UserEntity;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by waleedhassan on 26/11/16.
 */


@Path("/get")
public class HelloWorld {

    private UserDb db;
    @GET
    @Path("/hello")
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

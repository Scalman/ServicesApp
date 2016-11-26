import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by waleedhassan on 26/11/16.
 */

@Path("/get")
public class HelloWorld {



    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMessage() {
       // this.db = new UserDb();
        //String companyName = this.context.getInitParameter("ServiceLab2");
        //entityManagerFactory = Persistence.createEntityManagerFactory("test");
        //  Collection<UserEntity> userEntities = db.findUsersByName(null, "");

        return "Hello World";//userEntities.toString();
    }
}

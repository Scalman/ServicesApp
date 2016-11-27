package DB.DAL;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbContext {
    final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
}

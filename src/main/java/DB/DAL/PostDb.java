package DB.DAL;

import BO.ModelConverter;
import DB.Entities.PostEntity;
import DB.Entities.UserEntity;
import ViewModel.PostViewModel;
import ViewModel.UserViewModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by waleedhassan on 26/11/16.
 */
public class PostDb {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private PostEntity post;

    public PostDb() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
    }

    public void addPost(PostViewModel post) {
        this.post = ModelConverter.convertToPostEntity(post);
        this.post.setCreationDate(new Date(Calendar.getInstance().getTime().getTime()));
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(this.post);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public Collection<PostEntity> findPostsByUser(UserViewModel usr) {
        entityManager = entityManagerFactory.createEntityManager();
        UserEntity user = entityManager.find(UserEntity.class,usr.getUserId());
        Collection<PostEntity> posts = user.getPosts();
        entityManager.close();
        return posts;
    }
}

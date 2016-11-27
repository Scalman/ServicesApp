package DB.DAL;

import BO.ModelConverter;
import DB.Entities.PostEntity;
import DB.Entities.UserEntity;
import ViewModel.PostViewModel;
import ViewModel.UserViewModel;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

public class PostDb {

    private EntityManager entityManager;
    private PostEntity post;

    public PostDb() {
    }

    public void addPost(PostViewModel post) {
        this.post = ModelConverter.convertToPostEntity(post);
        this.post.setCreationDate(new Date(Calendar.getInstance().getTime().getTime()));
        entityManager = DbContext.emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(this.post);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public Collection<PostEntity> findPostsByUser(UserViewModel usr) {
        entityManager = DbContext.emf.createEntityManager();
        UserEntity user = entityManager.find(UserEntity.class,usr.getUserId());
        Collection<PostEntity> posts = user.getPosts();
        entityManager.close();
        return posts;
    }
}

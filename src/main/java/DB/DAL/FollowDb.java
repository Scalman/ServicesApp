package DB.DAL;

import BO.ModelConverter;
import DB.Entities.FollowEntity;
import DB.Entities.UserEntity;
import ViewModel.FollowViewModel;

import javax.persistence.EntityManager;
import java.util.Collection;
/**
 * Created by waleedhassan on 26/11/16.
 * Model for Follow
 */
public class FollowDb {

    private EntityManager entityManager;
    private FollowEntity follow;

    public FollowDb(){
    }
    public Collection<FollowEntity> findYourFollows(int userId) {
        entityManager = DbContext.emf.createEntityManager();
        UserEntity user = entityManager.find(UserEntity.class,userId);
        Collection<FollowEntity> follows = user.getFollow();
        entityManager.close();
        return follows;
    }
    public Collection<FollowEntity> findYourFollowsByName(int userId, String name) {
        entityManager = DbContext.emf.createEntityManager();
        UserEntity user = entityManager.find(UserEntity.class,userId);
        Collection<FollowEntity> follows = user.getFollow();
        follows.removeIf(f -> !f.getFollowing().getUsername().contains(name));
        entityManager.close();
        return follows;
    }
    public void addFollower(FollowViewModel follow) {
        this.follow = ModelConverter.convertToFollowEntity(follow);
        entityManager = DbContext.emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(this.follow);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

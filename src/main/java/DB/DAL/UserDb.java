package DB.DAL;

import BO.ModelConverter;
import DB.Entities.FollowEntity;
import DB.Entities.UserEntity;
import ViewModel.UserViewModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.Collection;


public class UserDb {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private UserEntity user;

    public UserDb() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
    }


    public Collection<UserEntity> findUsersByName() {

        entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> u = cq.from(UserEntity.class);
        cq.select(u);
        Collection<UserEntity> users = entityManager.createQuery(cq).getResultList();
        entityManager.close();

        return  users;
    }

    public void register(UserViewModel user){
        this.user =  ModelConverter.convertToUserEntity(user);
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(this.user);
        entityManager.getTransaction().commit();
        entityManager.close();

    }
    public UserEntity authenticate(UserViewModel user){
        this.user = ModelConverter.convertToUserEntity(user);
        entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> u = cq.from(UserEntity.class);
        Predicate p1 = cb.and(cb.equal(u.get("username"),this.user.getUsername()),cb.equal(u.get("password"),this.user.getPassword()));
        cq.where(p1);
        UserEntity usr = entityManager.createQuery(cq).getSingleResult();
        entityManager.close();
        return  usr;
    }
}

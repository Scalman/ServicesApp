package DB.DAL;

import BO.ModelConverter;
import DB.Entities.FollowEntity;
import DB.Entities.UserEntity;
import ViewModel.UserViewModel;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


public class UserDb {
    private EntityManager entityManager;
    private UserEntity user;

    public UserDb() {
    }


    private Collection<String> getUserFollowings(){
        Collection<FollowEntity> follows = this.user.getFollow();
        return follows.stream().map(f -> f.getFollowing().getUsername()).collect(Collectors.toCollection(ArrayList::new));
    }
    public Collection<UserEntity> findUsersByName(int userId, String name) {
        entityManager = DbContext.emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> u = cq.from(UserEntity.class);
        this.user = entityManager.find(UserEntity.class,userId);
        Predicate p1 = cb.and(cb.notEqual(u.get("username"),this.user.getUsername()),cb.like(u.get("username"),"%"+name+"%"));
        Collection<String> usrs = getUserFollowings();
        if(usrs.size()>0) {
            Expression<String> exp = u.get("username");
            Predicate p2 = cb.not(exp.in(usrs));
            cq.where(cb.and(p1,p2));
        } else {
            cq.where(p1);
        }
        Collection<UserEntity> users = entityManager.createQuery(cq).getResultList();
        entityManager.close();
        return  users;
    }
    public Collection<UserEntity> findUsers(int userId) {
        entityManager = DbContext.emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> u = cq.from(UserEntity.class);
        this.user = entityManager.find(UserEntity.class,userId);
        Predicate p1 = cb.notEqual(u.get("username"),this.user.getUsername());
        Collection<FollowEntity> follows = this.user.getFollow();
        if(follows.size()>0) {
            Collection<String> usrs = getUserFollowings();
            Expression<String> exp = u.get("username");
            Predicate p2 = cb.not(exp.in(usrs));
            cq.where(cb.and(p1,p2));
        } else {
            cq.where(p1);
        }
        Collection<UserEntity> users = entityManager.createQuery(cq).getResultList();
        entityManager.close();
        return  users;
    }
    public void register(UserViewModel user){
        this.user =  ModelConverter.convertToUserEntity(user);
        entityManager = DbContext.emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(this.user);
        entityManager.getTransaction().commit();
        entityManager.close();

    }
    public UserEntity authenticate(UserViewModel user){
        this.user = ModelConverter.convertToUserEntity(user);
        entityManager = DbContext.emf.createEntityManager();
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

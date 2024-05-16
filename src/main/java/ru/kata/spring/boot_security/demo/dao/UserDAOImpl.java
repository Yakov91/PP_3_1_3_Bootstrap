//package ru.kata.spring.boot_security.demo.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import ru.kata.spring.boot_security.demo.entities.User;
//
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//@Repository
//public class UserDAOImpl implements UserDAO {
//
//    @PersistenceContext
//    private final EntityManager entityManager;
//
//    @Autowired
//    public UserDAOImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    @Override
//    public List<User> findAll() {
//        return entityManager.createQuery("FROM User", User.class)
//                .getResultList();
//    }
//
//    @Override
//    public void save(User user) {
//        entityManager.persist(user);
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        try {
//            List<User> users = entityManager.createQuery(
//                            "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username", User.class)
//                    .setParameter("username", username)
//                    .getResultList();
//            if (!users.isEmpty()) {
//                return users.get(0);
//            }
//        } catch (NoResultException e) {
//            System.out.println("The user was not found");
//        }
//        return null;
//    }
//
//    @Override
//    public User findById(Long id) {
//        return entityManager.find(User.class, id);
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        User user = entityManager.find(User.class, id);
//        if(user != null) {
//            user.getRoles().clear();
//        }
//        entityManager.createQuery("DELETE FROM User WHERE id =: userId")
//                .setParameter("userId", id)
//                .executeUpdate();
//    }
//
//
//}

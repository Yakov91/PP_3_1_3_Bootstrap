//package ru.kata.spring.boot_security.demo.dao;
//
//import ch.qos.logback.core.encoder.EchoEncoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import ru.kata.spring.boot_security.demo.entities.Role;
//
//
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Repository
//public class RoleDAOImpl implements RoleDAO {
//
//    private final EntityManager entityManager;
//
//    @Autowired
//    public RoleDAOImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    @Override
//    public void saveRole(List<Role> role) {
//        entityManager.persist(role);
//    }
//
//    @Override
//    public List<Role> getRoles() {
//        return entityManager.createQuery("from Role", Role.class)
//                .getResultStream().collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteRole(Long id) {
//        Role role = entityManager.find(Role.class, id);
//        if (role == null) {
//            throw new NullPointerException("The role was not found");
//        }
//        entityManager.remove(role);
//    }
//
//    @Override
//    public Role findByName(String roleName) {
//        try {
//            return entityManager.createQuery("FROM Role WHERE roleName =: roleName", Role.class)
//                    .setParameter("roleName", roleName).getSingleResult();
//        } catch (NoResultException e) {
//            return null; // Обработка случая, когда роль не найдена
//        } catch (Exception e) {
//            throw new RuntimeException("Erorr finding role", e); // Обработка других исключений
//        }
//    }
//}

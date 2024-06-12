package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

public interface RoleDAO {
    void saveRole(List<Role> role);
    List<Role> getRoles();
    void deleteRole(Long id);
    Role findByName(String roleName);
}

package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService {
    void saveRole(List<Role> role);
    Collection<Role> getRoles();
    void deleteRole(Long id);
    Role findByName(String roleName);

}

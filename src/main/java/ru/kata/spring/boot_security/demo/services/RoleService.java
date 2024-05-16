package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);
    List<Role> getRoles();
    void deleteRole(Long id);

}

package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO =  roleDAO;
    }

    @Override
    public void saveRole(List<Role> role) {
        roleDAO.saveRole(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        roleDAO.deleteRole(id);
    }

    @Override
    public Role findByName(String roleName) {
        return roleDAO.findByName(roleName);
    }

}

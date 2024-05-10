package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.entities.Role;



import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDao;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void saveRole(List<Role> role) {
        roleDao.saveRole(role);

    }

    @Override
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    @Override
    public void deleteRole(Long id) {
        roleDao.deleteRole(id);
    }

    @Override
    public Role findByUsername(String roleName) {
        return roleDao.findByName(roleName);
    }
}

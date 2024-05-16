package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository  repository) {
        this. repository =  repository;
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        repository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        repository.deleteById(id);
    }

}

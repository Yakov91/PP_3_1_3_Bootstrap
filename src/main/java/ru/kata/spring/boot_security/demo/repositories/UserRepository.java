package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
               //использование пользовательского запроса  вместо стандартного запроса, генерируемого Spring Data JPA
    @Query("SELECT u FROM User u JOIN FETCH u.roles where u.username = :username")
    User findByUsername(@Param("username") String username); //возвращает пользователя с заданным именем пользователя и его ролями
}

package fr.esgi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.esgi.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

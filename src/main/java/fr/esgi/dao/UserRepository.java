package fr.esgi.dao;

import fr.esgi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Spring Data JPA repository for the UserRepository entity.
 * @author christopher
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByPseudoIgnoreCase(String toLowerCase);

    Optional<User> findOneByEmailIgnoreCase(String email);

}

package gianlucamessina.BE_U2_S3_L5_gestioneEventi.repositories;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}

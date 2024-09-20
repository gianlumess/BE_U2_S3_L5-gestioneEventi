package gianlucamessina.BE_U2_S3_L5_gestioneEventi.repositories;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

}

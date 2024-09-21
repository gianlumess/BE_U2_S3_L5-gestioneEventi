package gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Booking {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private int seatsBooked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Booking(int seatsBooked, User user, Event event) {
        this.seatsBooked = seatsBooked;
        this.user = user;
        this.event = event;
    }
}

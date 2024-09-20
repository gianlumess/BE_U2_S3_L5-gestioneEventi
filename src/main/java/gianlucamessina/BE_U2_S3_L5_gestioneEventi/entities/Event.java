package gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String place;
    private int seats;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;

    public Event(String title, String description, LocalDate date, String place, int seats, User user) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.place = place;
        this.seats = seats;
        this.user = user;
    }
}

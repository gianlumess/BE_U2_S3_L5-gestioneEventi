package gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewEventDTO(@NotEmpty(message = "Il titolo dell'evento è obbligatorio!")
                          String title,
                          @NotEmpty(message = "La descrizione dell'evento è obbligatoria!")
                          String description,
                          @NotNull(message = "La data dell'evento è obbligatoria!")
                          LocalDate date,
                          @NotEmpty(message = "La location dell'evento è obbligatoria!")
                          String place,
                          @NotNull(message = "I posti disponibili dell'evento sono obbligatori!")
                          Integer seats,
                          @NotEmpty(message = "Devi inserire l'organizzatore!")
                          String userId) {
}

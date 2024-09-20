package gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(@NotEmpty(message = "Inserisci l'email per effettuare il login!")
                           @Email(message = "Formato email non valido!")
                           String email,
                           @NotEmpty(message = "Inserisci la password per effettuare il login!")
                           String password) {
}

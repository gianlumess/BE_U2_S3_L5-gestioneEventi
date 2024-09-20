package gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.enums.Role;
import jakarta.validation.constraints.*;

public record NewUserDTO(@NotEmpty(message = "L'username è obbligatorio!")
                         @Size(min = 4,max = 30,message = "L'username deve essere compreso tra i 4 e i 30 caratteri!")
                         String username,
                         @NotEmpty(message = "Il nome è obbligatorio!")
                         @Size(min = 3,max = 40,message = "Il nome deve essere compreso tra i 3 e i 40 caratteri!")
                         String name,
                         @NotEmpty(message = "Il cognome è obbligatorio!")
                         @Size(min = 3,max = 40,message = "Il cognome deve essere compreso tra i 3 e i 40 caratteri!")
                         String surname,
                         @NotEmpty(message = "L'email è obbligatoria!")
                         @Email(message = "L'email inserita non è valida!")
                         String email,
                         @NotEmpty(message = "La password è obbligatoria!")
                         @Size(min = 4,message = "La password deve avere almeno 4 caratteri!")
                         String password,
                         @NotEmpty(message = "Il ruolo dell'utente è obbligatorio!")
                         @Pattern(regexp ="^(NORMALE|ORGANIZZATORE)$",message =  "Valore non valido, i valori ammessi sono 'NORMALE' o 'ORGANIZZATORE'")
                         String role) {
}

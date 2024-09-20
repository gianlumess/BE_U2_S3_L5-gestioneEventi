package gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads;

import java.time.LocalDate;
import java.util.UUID;

public record EventResponseDTO(UUID id,
                               String title,
                               String description,
                               LocalDate date,
                               String place,
                               Integer seats,
                               String userId) {
}

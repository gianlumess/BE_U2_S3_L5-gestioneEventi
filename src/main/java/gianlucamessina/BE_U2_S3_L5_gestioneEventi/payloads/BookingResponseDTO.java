package gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads;

import java.util.UUID;

public record BookingResponseDTO(UUID id,
                                 Integer seatsBooked,
                                 String userId,
                                 String eventId) {
}

package gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(String message, LocalDateTime timeStamp) {
}

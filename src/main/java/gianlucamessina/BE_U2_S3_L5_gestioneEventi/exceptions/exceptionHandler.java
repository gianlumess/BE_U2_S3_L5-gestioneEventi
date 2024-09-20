package gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.ErrorsResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class exceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponseDTO handleBadRequest(BadRequestException e){
        return new ErrorsResponseDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResponseDTO handleNotFound(NotFoundException e){
        return new ErrorsResponseDTO(e.getMessage(),LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResponseDTO handleGenericErrors(Exception e){
        //questo mi serve per capire l'entità dell'errore
        e.printStackTrace();
        return new ErrorsResponseDTO("Problema lato server, lo risolveremo al più presto!",LocalDateTime.now());
    }
}

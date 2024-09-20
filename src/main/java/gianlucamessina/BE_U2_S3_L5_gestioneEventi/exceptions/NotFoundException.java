package gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{
    public NotFoundException(UUID id){
        super("La risorsa con ID : "+id+" nonè stata trovata!");
    }
    public NotFoundException(String message){
        super(message);
    }
}

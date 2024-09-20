package gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}

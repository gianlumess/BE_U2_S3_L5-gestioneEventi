package gianlucamessina.BE_U2_S3_L5_gestioneEventi.services;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.UnauthorizedException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.UserLoginDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bCrypt;

    public String credentialControlAndTokenGeneration(UserLoginDTO body){
        //controllo tramite email se l'utente esiste
        User found=this.userService.findByEmail(body.email());

        if (bCrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createToken(found);
        }else {
            throw new UnauthorizedException("Credenziali sbagliate!");
        }
    }

}

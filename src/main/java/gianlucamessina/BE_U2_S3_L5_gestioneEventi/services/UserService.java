package gianlucamessina.BE_U2_S3_L5_gestioneEventi.services;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.enums.Role;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.NotFoundException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.NewUserDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bCrypt;

    //FIND BY ID
    public User findById(UUID userId){
        return this.userRepository.findById(userId).orElseThrow(()->new NotFoundException(userId));
    }

    //SAVE USER
    public User save(NewUserDTO body){
        //prima controllo se l'email è già in uso
        this.userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email: "+body.email()+" è già in uso!");
        });

        User newUser=new User(body.username(), body.name(), body.surname(), body.email(),bCrypt.encode(body.password()) ,Role.valueOf(body.role()));

        return this.userRepository.save(newUser);
    }

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("L'utente con email: "+ email+" non è stato trovato!"));
    }
}

package gianlucamessina.BE_U2_S3_L5_gestioneEventi.services;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.enums.Role;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.NotFoundException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.NewUserDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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

        User newUser=new User(body.username(), body.name(), body.surname(), body.email(), body.password(),body.role());

        return this.userRepository.save(newUser);
    }
}

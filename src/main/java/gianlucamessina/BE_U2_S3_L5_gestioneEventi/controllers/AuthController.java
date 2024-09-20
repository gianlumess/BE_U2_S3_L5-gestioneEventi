package gianlucamessina.BE_U2_S3_L5_gestioneEventi.controllers;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.NewUserDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.UserLoginDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.UserLoginRespDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.services.AuthService;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginRespDTO login(@RequestBody @Validated UserLoginDTO payload,BindingResult validation){
        if(validation.hasErrors()){
            String messages=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload: "+messages);
        }
        return new UserLoginRespDTO(this.authService.credentialControlAndTokenGeneration(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserDTO payload, BindingResult validation){
        if(validation.hasErrors()){
            String messages=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload: "+messages);
        }
        return this.userService.save(payload);
    }
}

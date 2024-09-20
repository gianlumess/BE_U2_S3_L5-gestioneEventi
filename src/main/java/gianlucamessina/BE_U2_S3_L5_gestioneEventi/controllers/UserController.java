package gianlucamessina.BE_U2_S3_L5_gestioneEventi.controllers;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.NewUserDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //GET FIND BY ID (http://localhost:3001/users/{usersId})
    @GetMapping("/{userId}")
    public User findById(@PathVariable UUID userId){
        return this.userService.findById(userId);
    }

    //POST (http://localhost:3001/users)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String message=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("ci sono stati errori nel payload: "+ message);
        }

        return this.userService.save(body);
    }
}

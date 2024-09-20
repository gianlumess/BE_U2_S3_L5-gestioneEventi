package gianlucamessina.BE_U2_S3_L5_gestioneEventi.controllers;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.Event;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.NewEventDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.NewUserDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    //GET DELLA LISTA DI EVENTI (http://localhost:3001/events)
    @GetMapping
    public Page<Event> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "15") int size,
                                    @RequestParam(defaultValue = "id") String sortBy){

        return this.eventService.findAll(page,size,sortBy);
    }

    //GET FIND BY ID (http://localhost:3001/events/{eventId})
    @GetMapping("/{eventId}")
    public Event findById(@PathVariable UUID eventId){
        return this.eventService.findById(eventId);
    }

    //POST (http://localhost:3001/events)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event save(@RequestBody @Validated NewEventDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String message=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("ci sono stati errori nel payload: "+ message);
        }

        return this.eventService.save(body);
    }
}

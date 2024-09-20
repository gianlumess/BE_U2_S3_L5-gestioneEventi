package gianlucamessina.BE_U2_S3_L5_gestioneEventi.controllers;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.Event;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.UnauthorizedException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.EventResponseDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.NewEventDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.UpdateEventDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public EventResponseDTO save(@RequestBody @Validated NewEventDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String message=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("ci sono stati errori nel payload: "+ message);
        }

        return this.eventService.save(body);
    }

    //ENDPOINT PER MODIFICARE ED ELIMINARE I PROPRI EVENTI

    @PutMapping("/mine/{eventId}")
    public EventResponseDTO updateMyEvent(@AuthenticationPrincipal User user,@PathVariable UUID eventId,@RequestBody @Validated UpdateEventDTO payload){
        Event foundEvent=this.eventService.findById(eventId);

        if(!foundEvent.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("Non sei autorizzato a modificare il post in quanto non è tuo!");
        }

        return this.eventService.findByIdAndUpdateForUsers(eventId,payload);
    }

    @DeleteMapping("/mine/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyEvent(@AuthenticationPrincipal User user,@PathVariable UUID eventId){
        Event foundEvent=this.eventService.findById(eventId);

        if(!foundEvent.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("Non sei autorizzato a modificare il post in quanto non è tuo!");
        }
        this.eventService.findByIdAndDeleteForUsers(eventId);
    }
}

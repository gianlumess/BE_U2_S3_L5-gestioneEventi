package gianlucamessina.BE_U2_S3_L5_gestioneEventi.services;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.Event;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.BadRequestException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.NotFoundException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.NewEventDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    UserService userService;

    //FIND ALL CON PAGINAZIONE
    public Page<Event> findAll(int page, int size, String sortBy){
        //controllo se viene richiesta una pagina superiore a quelle presenti effettivamente
        if(page>150)page=150;

        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));

        return this.eventRepository.findAll(pageable);
    }

    //FIND BY ID
    public Event findById(UUID eventId){
        return this.eventRepository.findById(eventId).orElseThrow(()->new NotFoundException(eventId));
    }

    //SAVE EVENT
    public Event save(NewEventDTO body){
        //Controlla che la data inserita non sia già passata
        if(body.date().isBefore(LocalDate.now())){
            throw  new BadRequestException("Non è possibile creare un viaggio per una data già passata!");
        }

        User foundUser=this.userService.findById(UUID.fromString(body.userId()));


        Event newEvent=new Event(body.title(), body.description(), body.date(), body.place(), body.seats(), foundUser);

        return this.eventRepository.save(newEvent);
    }
}

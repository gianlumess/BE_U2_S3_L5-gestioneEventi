package gianlucamessina.BE_U2_S3_L5_gestioneEventi.controllers;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.Booking;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.BookingResponseDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    BookingService bookingService;


    //GET LISTA BOOKINGS (http://localhost:3001/bookings)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Booking>findAll(@RequestParam(defaultValue = "0")int page,
                                @RequestParam(defaultValue = "15")int size,
                                @RequestParam(defaultValue = "id")String sortBy){

        return this.bookingService.findAll(page,size,sortBy);
    }

    //GET FIND BY ID (http://localhost:3001/bookings/{bookingId})
    @GetMapping("/{bookingId}")
    public Booking findById(@PathVariable UUID bookingId){
        return this.bookingService.findById(bookingId);
    }

    //POST (http://localhost:3001/bookings)
    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDTO save(@AuthenticationPrincipal User user,@PathVariable UUID eventId){
        return this.bookingService.save(user,eventId);
    }
}

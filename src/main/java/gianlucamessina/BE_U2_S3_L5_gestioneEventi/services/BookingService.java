package gianlucamessina.BE_U2_S3_L5_gestioneEventi.services;

import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.Booking;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.NotFoundException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.payloads.BookingResponseDTO;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    //FIND ALL CON PAGINAZIONE
    public Page<Booking> findAll(int page,int size, String sortBy){
        if(page>150)page=150;

        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));
        return this.bookingRepository.findAll(pageable);
    }

    //FIND BY ID
    public Booking findById(UUID bookingId){
        return this.bookingRepository.findById(bookingId).orElseThrow(()->new NotFoundException(bookingId));
    }

    //SAVE BOOKING
    /*public BookingResponseDTO save(Booking body){

    }*/
}

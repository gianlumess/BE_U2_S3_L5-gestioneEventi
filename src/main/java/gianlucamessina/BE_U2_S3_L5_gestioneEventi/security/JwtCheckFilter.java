package gianlucamessina.BE_U2_S3_L5_gestioneEventi.security;


import gianlucamessina.BE_U2_S3_L5_gestioneEventi.entities.User;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.exceptions.UnauthorizedException;
import gianlucamessina.BE_U2_S3_L5_gestioneEventi.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtCheckFilter extends OncePerRequestFilter {
    @Autowired
    JWTTools jwtTools;
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //metodo che varrà richiamato ad ogni richiesta essendo un filtro(tranne quelle che si vogliono escludere)

        //1. prima di tutto verifico che nella richiesta ci sia l'Authorization Header, altrimenti lancia 401
        String authHeader=request.getHeader("Authorization");
        // "Authorization": "Bearer eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MjY0ODE1MDMsImV4cCI6MTcyNzA4NjMwMywic3ViIjoiOTFkMTg2MGItZjE2Yy00MTYwLWIyYTYtODU2NWY0MzY5MTBiIn0.l58gBS6yJnRom0gYNRECl3W_e1B0TmdNkOivPncYP0fO3LIC2QXwvgft71jNYhfJ"
        if(authHeader==null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Inserisci correttamente il token");

        //2. se l'Authorization Header è presente, estraggo il token
        String token=authHeader.substring(7);
        System.out.println("TOKEN : "+ token);

        //3. verifico se il token è stato manipolato
        jwtTools.verifyToken(token);

        //4. per abilitare l'autorizzazione devo informare spring security su chi sia l'utente che sta effettuando la richiesta
        //per controllare il ruolo

        //CERCO L'UTENTE CHE STA EFFETTUANDO LA RCICHIESTA TRAMITE ID NEL TOKEN
        String id= jwtTools.extractFromToken(token);
        User CurrentUser=this.userService.findById(UUID.fromString(id));

        //una volta trovato l'utente lo posso associare al security context, vale a dire associare l'utente autenticato alla richiesta corrente
        Authentication authentication=new UsernamePasswordAuthenticationToken(CurrentUser,null,CurrentUser.getAuthorities());
        //il terzo parametro mi serve per utilizzare i @PreAuthorize perchè ci ritorna la lista di ruoli del dipendente corrente
        //quindi associo il dipendente autenticato al context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //serve per disabilitare questo filtro per alcune richieste, su determinati endpoint o controller,
        //in questo caso il filtro non dovrà essere chiamato per tutte le richieste di login e register
        return new AntPathMatcher().match("/auth/**",request.getServletPath());
    }
}

package spittr.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/* W przypadku gdy pojawia si� wyj�tek aplikacji a wbudowane mapowania si� nie przydaj�, 
mo�na napisa� sw�j w�asny wyj�tek odwzorowany na odpowied� HTTP. Domy�lnie, kazdy 
nieodwzorowany wyj�tek zwraca odpowied� 500. Do odwzorowania wyj�tku mo�na u�y� adnotacji 
ResponseStatus. Przyjmuje ona dwa parametry - value czyli status odpowiedzi oraz 
reason czyli komunikat. */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Spittle nie zosta� znaleziony")
public class SpittleNotFoundException extends RuntimeException {
    
}

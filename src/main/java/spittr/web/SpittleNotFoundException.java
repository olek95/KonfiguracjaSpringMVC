package spittr.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/* W przypadku gdy pojawia siê wyj¹tek aplikacji a wbudowane mapowania siê nie przydaj¹, 
mo¿na napisaæ swój w³asny wyj¹tek odwzorowany na odpowiedŸ HTTP. Domyœlnie, kazdy 
nieodwzorowany wyj¹tek zwraca odpowiedŸ 500. Do odwzorowania wyj¹tku mo¿na u¿yæ adnotacji 
ResponseStatus. Przyjmuje ona dwa parametry - value czyli status odpowiedzi oraz 
reason czyli komunikat. */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Spittle nie zosta³ znaleziony")
public class SpittleNotFoundException extends RuntimeException {
    
}

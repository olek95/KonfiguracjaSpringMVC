package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/* Kontrolery to klasy zawieraj�ce metody oznaczone adnotacj� @RequestMapping.
Adnotacja @Controller jest stereotypem utworzonym w oparciu o adnotacj� @Component,
dzi�ki temu skaner komponent�w potrafi go automatycznie wyszuka�. To samo 
otrzyma�oby si� oznaczaj�c kontroler jako @Component, ale wtedy jego zadanie nie 
by�oby tak oczywiste. */
@Controller
/* Definiuje obs�ug� ��da� na poziomie klasy. Dotycz wszystkich metod tej klasy, 
natomiast RequestMapping przy metodach s� uzupe�nieniem tej adnotacji. Mo�na 
ustawi� wi�cej ni� jedn� �cie�k�, dzi�ki czemu b�d� obs�ugiwane ��dania na obie �cie�ki. */
@RequestMapping({"/", "/homepage"})
public class HomeController {
    /* Ta adnotacja okre�la rodzaj obs�ugiwanych ��da�. Tu obs�ugiwane jest 
    ��danie typu GET na adres / */
    //@RequestMapping(value="/", method=GET)
    // Ta adnotacja jest uzupe�nieniem adnotacji z poziomu klasy. 
    @RequestMapping(method=GET)
    public String home() {
        return "home"; // nazwa widoku 
    }
}

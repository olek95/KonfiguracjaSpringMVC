package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/* Kontrolery to klasy zawieraj¹ce metody oznaczone adnotacj¹ @RequestMapping.
Adnotacja @Controller jest stereotypem utworzonym w oparciu o adnotacjê @Component,
dziêki temu skaner komponentów potrafi go automatycznie wyszukaæ. To samo 
otrzyma³oby siê oznaczaj¹c kontroler jako @Component, ale wtedy jego zadanie nie 
by³oby tak oczywiste. */
@Controller
/* Definiuje obs³ugê ¿¹dañ na poziomie klasy. Dotycz wszystkich metod tej klasy, 
natomiast RequestMapping przy metodach s¹ uzupe³nieniem tej adnotacji. Mo¿na 
ustawiæ wiêcej ni¿ jedn¹ œcie¿kê, dziêki czemu bêd¹ obs³ugiwane ¿¹dania na obie œcie¿ki. */
@RequestMapping({"/", "/homepage"})
public class HomeController {
    /* Ta adnotacja okreœla rodzaj obs³ugiwanych ¿¹dañ. Tu obs³ugiwane jest 
    ¿¹danie typu GET na adres / */
    //@RequestMapping(value="/", method=GET)
    // Ta adnotacja jest uzupe³nieniem adnotacji z poziomu klasy. 
    @RequestMapping(method=GET)
    public String home() {
        return "home"; // nazwa widoku 
    }
}

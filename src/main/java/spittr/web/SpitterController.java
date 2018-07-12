package spittr.web;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.Part;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestPart;
import spittr.Spitter;
import spittr.data.SpitterRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller 
@RequestMapping("/spitter")
public class SpitterController {
    private SpitterRepository spitterRepository; 
    
    @Autowired 
    public SpitterController(SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }
    
    public SpitterController(){}
    
    @RequestMapping(value="/register", method=GET)
    public String showRegistrationForm(Model model) {
        /* Poniewa¿ commandName ma wartoœæ spitter, potrzeba zapisaæ do modelu 
        obiekt pod kluczem spitter, w przeciwnym przypadku pojawi siê b³¹d */
        model.addAttribute(new Spitter());
        return "registerForm";
    }
    
    /*@RequestMapping(value="/register", method=POST)
    @Valid oznacza parametr do walidacji, Errors - zawiera wszystkie dostêpne b³êdny.
    Wa¿ne jest aby obiekt Errors wyst¹pi³ zaraz za parametrem oznaczonym Valid. 
    Adnotacja RequestPart mówi dane jakiej czêœci ¿¹dania maj¹ byæ zapisane w tablicy 
    bajtów. Wi¹¿ê ona dane ¿¹dania wieloczêœciowego z argumentem metody. Jeœli u¿ytkownik 
    wyœle formularz bez zdjêcia tablica bêdzie pusta (nie bêdzie nullem). 
    public String processRegistration(@RequestPart("profilePicture") byte[] profilePicture,
            @Valid Spitter spitter, Errors errors) {
        // sprawdza czy formularz zawiera b³êdy 
        if (errors.hasErrors()) {
            return "registerForm";
        }
        spitterRepository.save(spitter);
         redirect: umo¿liwia przekierowanie na now¹ stronê. Oprócz tej opcji, klasa 
        InternalResourceViewResolver rozpoznaje te¿ prefiks forward:. W nim ¿¹danie 
        nie jest przekierowywane, a przekazywane do danej œcie¿ki URL. 
        return "redirect:/spitter/" + spitter.getUsername();
    }*/
    
    /* Przyk³ad pobierania danych za pomoc¹ interfejsu Multipartfile, który umo¿liwia 
    na uzyskanie bardziej kompletnych informacji ni¿ tablica bajtów. 
    @RequestMapping(value="/register", method=POST)
    public String processRegistration(@RequestPart("profilePicture") MultipartFile profilePicture,
            @Valid Spitter spitter, Errors errors) throws IOException {
        if(errors.hasErrors()) {
            return "registerForm";
        }
        spitterRepository.save(spitter);
        // Metoda transferTo umo¿liwia zapis przes³anego pliku w systemie plików. 
        // Metoda getOriginalFilename zwraca oryginaln¹ nazwê pliku. 
        // Minusem jest np. dbanie o miejsce na dysku, kopiê zapasow¹ w przypadku awarii itd. 
        profilePicture.transferTo(new File("/data/spittr/" + profilePicture.getOriginalFilename()));
        return "redirect:/spitter/" + spitter.getUsername();
    } */
    
    /* Interfejs Part jest alternatyw¹ dla interfejsu MultipartFile. Posiada kilka 
    podobnych metod do tego interfejsu. Ten interfejs nie wymaga konfiguracji 
    komponentu StandardServletMultipartResolver w przeciwieñstwie do MultipartFile. */ 
    @RequestMapping(value="/register", method=POST)
    public String processRegistration(@RequestPart("profilePicture") Part profilePicture,
            @Valid Spitter spitter, RedirectAttributes model, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            return "registerForm";
        }
        spitterRepository.save(spitter);
        /* Do modelu przekazywana jest wartoœæ która zastêpuje symbol zastêpczy w zwracanym adresie.
        Ze wzglêdu na u¿ycie atrybutów jednorazowych u¿yto tu klasê RedirectAttributes, ale 
        wystarczaj¹cy Model */
        model.addAttribute("username", spitter.getUsername());
        /* Umo¿liwia ustawienie atrybutu jednorazowego. Normalnie wartoœci z modelu 
        s¹ usuwanie podczas przekierowywania, jednak¿e atrybuty jednorazowe umo¿liwiaj¹
        zapisane atrybutu do sesji, odczytanie go po przekierowaniu a nastêpne 
        automatycznemu usunieciu ze sesji. */
        model.addFlashAttribute("spitter", spitter);
        /* Metoda getSubmittedFileName odpowiada metodzie getOriginalFilename 
        klasy MultipartFile, co pozwala na pobranie oryginalnej nazwy pliku. 
        Natomiast metoda write odpowiada metodzie transferTo, co pozwala na zapis pliku.
        Podana œcie¿ka bêdzie siê zaczynaæ w miejscu podanym podczas konfiguracji 
        MultipartConfigElement przy u¿yciu metody setMultipartConfig. */
        profilePicture.write("/data/spittr/" + profilePicture.getSubmittedFileName());
        /* Redirect umo¿liwia przekierowanie do nowej strony, dziêki temu blokuje to 
        mo¿liwoœæ np. ponownego wys³ania formularza. */
        //return "redirect:/spitter/" + spitter.getUsername();
        /* Konkatenacja podczas tworzenia adresów jest niebezpieczna. Dlatego 
        lepiej u¿ywaæ szablonów. Aby zast¹piæ symbol zastêpczy w adresie, nale¿y 
        przekazaæ wartoœæ do obiektu Modelu. Niebezpieczne znaki s¹ poddawane 
        eskejpowaniu, gdy¿ string nie jest bezpoœrednio dodawany. W przypadku gdy 
        ustawi siê wartoœæ w Modelu, ale nie bêdzie symbolu zastêpczego, bêdzie ona 
        ustawiona jako parametr zapytania np. ?zmienna=42. */
        return "redirect:/spitter/{username}";
    }
    
    @RequestMapping(value="/{username}", method=GET)
    public String showSpitterProfile(@PathVariable String username, Model model) {
        /* sprawdza czy w modelu istnieje ju¿ atrybut o nazwie spitter np. przekazany 
        jako atrybut jednorazowy za pomoc¹ klasy RedirectAttributes. Jeœli nie, 
        dodaje dane do modelu. */
        if (!model.containsAttribute("spitter")) {
            model.addAttribute(spitterRepository.findByUsername(username));
        }
        return "profile";
    }
}

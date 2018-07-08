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
            @Valid Spitter spitter, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            return "registerForm";
        }
        spitterRepository.save(spitter); 
        /* Metoda getSubmittedFileName odpowiada metodzie getOriginalFilename 
        klasy MultipartFile, co pozwala na pobranie oryginalnej nazwy pliku. 
        Natomiast metoda write odpowiada metodzie transferTo, co pozwala na zapis pliku.
        Podana œcie¿ka bêdzie siê zaczynaæ w miejscu podanym podczas konfiguracji 
        MultipartConfigElement przy u¿yciu metody setMultipartConfig. */
        profilePicture.write("/data/spittr/" + profilePicture.getSubmittedFileName());
        return "redirect:/spitter/" + spitter.getUsername();
    }
    
    @RequestMapping(value="/{username}", method=GET)
    public String showSpitterProfile(@PathVariable String username, Model model) {
        Spitter spitter = spitterRepository.findByUsername(username); 
        model.addAttribute(spitter);
        return "profile";
    }
}

package spittr.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import spittr.Spittle;
import spittr.SpittleForm;
import spittr.data.SpittleRepository;

@Controller
@RequestMapping("/spittles")
public class SpittleController {
    private SpittleRepository spittleRepository; 
    private static final String MAX_LONG_AS_STRING = Long.MAX_VALUE + "";
    
    @Autowired
    public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository; 
    }
    
    // Wersja z wykorzystaniem klasy Model 
    /*@RequestMapping(method=RequestMethod.GET)
    // Model jest przekazywan� do widoku map�, kt�ra umo�liwia wy�wietlenie listy obiekt�w u�ytkownikowi
    public String spittles(Model model) {
        // mo�na wywo�a� bez okre�lenia klucza (wtedy tworzony jest domy�lny na podstawie typu
        // (tu typ to List<Spittle> wi�c b�dzie nazwane spittleList, b�d� umie�ci� klucz na 1 miejscu 
        model.addAttribute(spittleRepository.findSpittles(Long.MAX_VALUE, 20));
        return "spittles";
    }*/ 
    
    //Wersja z wykorzystaniem mapy: 
    /*@RequestMapping(method=RequestMethod.GET)
    public String spittles(Map model) {
        model.put("spittleList", spittleRepository.findSpittles(Long.MAX_VALUE, 20));
        return "spittles";
    }*/ 
    
    // Wersja z brakiem zwracania jawnego widoku: 
    @RequestMapping(method=RequestMethod.GET)
    /* @RequestParam wskazuje �e parametr metody powinien by� zwi�zany z parametrem ��dania. 
    Value okre�la nazw� parametru, natomiast defaultValue warto�� domy�ln� w przypadku 
    gdy nie podano warto�ci lub jest ona pusta. DefaultValue musi by� typem String 
    bo parametry zapyta� s� zawsze stringami, dlatego u�yto sta�� zawieraj�c� Long w postaci String.
    Przy wi�zaniu z parametrem metody ten String jest konwertowany na odpowiedni typ (tu long)*/
    public List<Spittle> spittles(@RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
            @RequestParam(value="count", defaultValue="20") int count) {
        return spittleRepository.findSpittles(max, count);
    }
    
    /* Symbole zast�pcze { i } umo�liwiaj� prac� ze zmiennymi �cie�ki. Mog� zawiera� 
    dowolne warto�ci. Adnotacja PathVariable nie musi mie� atrybutu value (@PathVariable("spittleId"),
    gdy nazwa parametru metody jest taka sama jak parametr �cie�ki, gdy� spring potrafi 
    sam to wywnoskowa� po nazwie */
    @RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
    public String spittle(@PathVariable long spittleId, Model model) {
        Spittle spittle = spittleRepository.findOne(spittleId);
        if (spittle == null) {
            throw new SpittleNotFoundException(); 
        }
        model.addAttribute(spittle);
        return "spittle";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String saveSpittle(SpittleForm form, Model model) {
        List<Spittle> spittles = spittleRepository.findSpittles(Long.MAX_VALUE, Integer.MAX_VALUE);
        spittleRepository.save(new Spittle(spittles.get(spittles.size() - 1).getId() + 1, form.getMessage(), new Date(), 
                form.getLongitude(), form.getLatitude()));
        return "redirect:/spittles";
    }
    
    /* Adnotacja ExceptionHandler pozwala na przechwytywanie wyj�tk�w wyrzuconych 
    w dowolnej metodzie obs�ugi ��dania zdefiniowanej w tym samym kontrolerze. 
    Dzi�ki temu zamiast dawa� try-catch w ka�dej metodzie kt�ra mo�e rzuci� wyj�tek, 
    wystarczy go przechwycic w jednej uniwersalnej. Dzi�ki temu metoda b�dzie mniej 
    z�o�ona gdy� oddzielamy prawid�owe wyj�cie z metody od wyj�cia rzucaj�cego wyj�tek. 
    Dzi�ki obs�u�eniu wyj�tku, strona nie wyrzuci kodu b��du. */
    /*@ExceptionHandler(DuplicateSpittleException.class)
    public String handleDuplicateSpittle() {
        return "error/duplicate";
    }*/ 
}

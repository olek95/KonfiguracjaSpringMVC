package spittr.web;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import spittr.Spittle;
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
    // Model jest przekazywan¹ do widoku map¹, która umo¿liwia wyœwietlenie listy obiektów u¿ytkownikowi
    public String spittles(Model model) {
        // mo¿na wywo³aæ bez okreœlenia klucza (wtedy tworzony jest domyœlny na podstawie typu
        // (tu typ to List<Spittle> wiêc bêdzie nazwane spittleList, b¹dŸ umieœciæ klucz na 1 miejscu 
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
    /* @RequestParam wskazuje ¿e parametr metody powinien byæ zwi¹zany z parametrem ¿¹dania. 
    Value okreœla nazwê parametru, natomiast defaultValue wartoœæ domyœln¹ w przypadku 
    gdy nie podano wartoœci lub jest ona pusta. DefaultValue musi byæ typem String 
    bo parametry zapytañ s¹ zawsze stringami, dlatego u¿yto sta³¹ zawieraj¹c¹ Long w postaci String.
    Przy wi¹zaniu z parametrem metody ten String jest konwertowany na odpowiedni typ (tu long)*/
    public List<Spittle> spittles(@RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
            @RequestParam(value="count", defaultValue="20") int count) {
        return spittleRepository.findSpittles(max, count);
    }
    
    /* Symbole zastêpcze { i } umo¿liwiaj¹ pracê ze zmiennymi œcie¿ki. Mog¹ zawieraæ 
    dowolne wartoœci. Adnotacja PathVariable nie musi mieæ atrybutu value (@PathVariable("spittleId"),
    gdy nazwa parametru metody jest taka sama jak parametr œcie¿ki, gdy¿ spring potrafi 
    sam to wywnoskowaæ po nazwie */
    @RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
    public String spittle(@PathVariable long spittleId, Model model) {
        Spittle spittle = spittleRepository.findOne(spittleId);
//        if (spittle == null) {
//            throw new SpittleNotFoundException(); 
//        }
        model.addAttribute(spittle);
        return "spittle";
    }
}

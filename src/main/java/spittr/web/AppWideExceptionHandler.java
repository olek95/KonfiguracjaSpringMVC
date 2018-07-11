package spittr.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* ControllerAdvice to klasa porady (porada kontrolera). Posiada ona jedn¹ lub kilka 
metod oznaczonych adnotacjami: ExceptionHandler (obs³uga wyj¹tku), InitBinder lub
ModelAttribute. Te metody klasy porad s¹ stosowane do wszystkich metod oznaczonych 
adnotacja RequestMapping we wszystkich kontrolerach. Adnotacja ControllerAdvice 
jest oznaczona adnotacj¹ Component wiêc bêdzie wychwytywana przez mechanizm skanowania 
komponentów. */ 
@ControllerAdvice
public class AppWideExceptionHandler {
    //Przechwytuje wszystkie wyst¹pienia wyjatku DuplicateSpittleException we wszystkich kontrolerach. 
    @ExceptionHandler(DuplicateSpittleException.class)
    public String duplicateSpittleHandler() {
        return "error/duplicate";
    }
}

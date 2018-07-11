package spittr.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* ControllerAdvice to klasa porady (porada kontrolera). Posiada ona jedn� lub kilka 
metod oznaczonych adnotacjami: ExceptionHandler (obs�uga wyj�tku), InitBinder lub
ModelAttribute. Te metody klasy porad s� stosowane do wszystkich metod oznaczonych 
adnotacja RequestMapping we wszystkich kontrolerach. Adnotacja ControllerAdvice 
jest oznaczona adnotacj� Component wi�c b�dzie wychwytywana przez mechanizm skanowania 
komponent�w. */ 
@ControllerAdvice
public class AppWideExceptionHandler {
    //Przechwytuje wszystkie wyst�pienia wyjatku DuplicateSpittleException we wszystkich kontrolerach. 
    @ExceptionHandler(DuplicateSpittleException.class)
    public String duplicateSpittleHandler() {
        return "error/duplicate";
    }
}

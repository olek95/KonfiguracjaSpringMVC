package spittr.data;

import org.springframework.cache.annotation.Cacheable;
import spittr.domain.Spitter;

public interface SpitterRepository {
    Spitter save(Spitter spitter);
    
    /* Wskazuje �e spring powinien sprawdzi� czy warto�� zwracana przez t� metod�
    jest zapisana w pami�ci podr�cznej. Je�li jest zapisana to j� zwraca, a je�li 
    nie, to wywo�uje metod� i zapisuje warto�� w pami�ci podr�cznej. 
    SpitterMapCache to nazwa wykorzystanej pami�ci podr�cznej. Tutaj kluczem 
    cachowania jest przekazana nazwa u�ytkownika. Oznaczenie metody adnotacj� 
    cacheable w interfejsie spowoduje odziedziczenie cachowania przez wszystkie 
    implementacje tej metody. 
    */
    @Cacheable("spitterMapCache")
    Spitter findByUsername(String username);
}

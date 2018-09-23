package spittr.data;

import org.springframework.cache.annotation.Cacheable;
import spittr.domain.Spitter;

public interface SpitterRepository {
    Spitter save(Spitter spitter);
    
    /* Wskazuje ¿e spring powinien sprawdziæ czy wartoœæ zwracana przez t¹ metodê
    jest zapisana w pamiêci podrêcznej. Jeœli jest zapisana to j¹ zwraca, a jeœli 
    nie, to wywo³uje metodê i zapisuje wartoœæ w pamiêci podrêcznej. 
    SpitterMapCache to nazwa wykorzystanej pamiêci podrêcznej. Tutaj kluczem 
    cachowania jest przekazana nazwa u¿ytkownika. Oznaczenie metody adnotacj¹ 
    cacheable w interfejsie spowoduje odziedziczenie cachowania przez wszystkie 
    implementacje tej metody. 
    */
    @Cacheable("spitterMapCache")
    Spitter findByUsername(String username);
}

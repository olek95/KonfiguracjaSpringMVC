package spittr.data;

import java.util.List;
import org.springframework.cache.annotation.CachePut;
import spittr.Spittle;
import spittr.web.DuplicateSpittleException;

public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
    
    Spittle findOne(long id);
    
    /* CachePut zapisuje w pamiêci podrecznej wartoœæ zwrócon¹ z metody. Pamiêæ
    nie jest sprawdzana przed wywo³aniem metody, wiêc metoda jest zawsze wywo³ywana. 
    Domyœlnie klucz opiera siê na parametrach przekazanych do metody. W tym przypadku 
    cachowanie jest potrzebne aby pobraæ z pamiêci obiekt po zapisaniu nowego, wiêc
    nie chce siê u¿ywaæ klucza Spittle do pobierania obiektu Spittle. Lepszym 
    wyjœciem jest u¿ycie do tego id zwracanego obiektu, jednak aby zmieniæ domyœlny
    klucz nale¿y u¿yæ parametru key i podaæ mu wyra¿enei SpEL które obliczy klucz. 
    W tym przypadku wykorzystano metadan¹ #result która odwo³uje siê do zwracanego 
    obiektu co umo¿liwia pobranie z niego id. */
    @CachePut(value="spitterMapCache", key="#result.id")
    void save(Spittle spittle) throws DuplicateSpittleException; 
}

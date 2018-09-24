package spittr.data;

import java.util.List;
import org.springframework.cache.annotation.CachePut;
import spittr.Spittle;
import spittr.web.DuplicateSpittleException;

public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
    
    Spittle findOne(long id);
    
    /* CachePut zapisuje w pami�ci podrecznej warto�� zwr�con� z metody. Pami��
    nie jest sprawdzana przed wywo�aniem metody, wi�c metoda jest zawsze wywo�ywana. 
    Domy�lnie klucz opiera si� na parametrach przekazanych do metody. W tym przypadku 
    cachowanie jest potrzebne aby pobra� z pami�ci obiekt po zapisaniu nowego, wi�c
    nie chce si� u�ywa� klucza Spittle do pobierania obiektu Spittle. Lepszym 
    wyj�ciem jest u�ycie do tego id zwracanego obiektu, jednak aby zmieni� domy�lny
    klucz nale�y u�y� parametru key i poda� mu wyra�enei SpEL kt�re obliczy klucz. 
    W tym przypadku wykorzystano metadan� #result kt�ra odwo�uje si� do zwracanego 
    obiektu co umo�liwia pobranie z niego id. */
    @CachePut(value="spitterMapCache", key="#result.id")
    void save(Spittle spittle) throws DuplicateSpittleException; 
}

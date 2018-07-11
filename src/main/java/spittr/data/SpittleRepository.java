package spittr.data;

import java.util.List;
import spittr.Spittle;
import spittr.web.DuplicateSpittleException;

public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
    
    Spittle findOne(long id);
    
    void save(Spittle spittle) throws DuplicateSpittleException; 
}

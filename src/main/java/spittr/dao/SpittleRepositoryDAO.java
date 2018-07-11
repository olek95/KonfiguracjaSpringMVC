package spittr.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.springframework.stereotype.Repository;
import spittr.Spittle;
import spittr.data.SpittleRepository;
import spittr.web.DuplicateSpittleException;

@Repository
public class SpittleRepositoryDAO implements SpittleRepository{
    private static List<Spittle> spittles = new ArrayList(Arrays.asList(new Spittle[]{
        new Spittle(0L, "Message", new Date(), 0.0, 0.0),
        new Spittle(1L, "Message", new Date(), 0.0, 0.0)
    }));
    
    @Override
    public List<Spittle> findSpittles(long max, int count) {
        return spittles;
    }
    
    @Override
    public Spittle findOne(long id) {
        for (Spittle spittle : spittles) {
            if (spittle.getId() == id) return spittle; 
        }
        return null;
    }
    
    @Override 
    public void save(Spittle spittle) throws DuplicateSpittleException {
        for (Spittle s : spittles) {
            if (s.getId() == spittle.getId()) {
                throw new DuplicateSpittleException();
            }
        }
        spittles.add(spittle);
    }
}

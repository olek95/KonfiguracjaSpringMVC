package spittr.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.stereotype.Repository;
import spittr.Spittle;
import spittr.data.SpittleRepository;

@Repository
public class SpittleRepositoryDAO implements SpittleRepository{
    @Override
    public List<Spittle> findSpittles(long max, int count) {
        List<Spittle> spittles = new ArrayList();
        spittles.add(new Spittle("Message", new Date()));
        return spittles;
    }
    
    @Override
    public Spittle findOne(long id) {
        return new Spittle("Message", new Date());
    }
    
}

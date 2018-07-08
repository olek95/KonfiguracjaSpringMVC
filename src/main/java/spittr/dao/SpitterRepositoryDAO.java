package spittr.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import spittr.Spitter;
import spittr.data.SpitterRepository;

/* Interfejsy reprezentuj�ce repozytoria musz� mie� swoj� implementacj� oraz by� 
oznaczonymi adnotacj� Repository, poniewa� w przeciwnym przypadku pojawi si� wyj�tek 
m�wi�cy o braku zale�no�ci (dependency) dla danego interfejsu. */
@Repository
public class SpitterRepositoryDAO implements SpitterRepository{
    private static List<Spitter> spitters = new ArrayList(); 
    @Override
    public Spitter save(Spitter spitter) {
        spitters.add(spitter); 
        return spitter;
    }
    
    @Override
    public Spitter findByUsername(String username) {
        Spitter spitter = null; 
        for (int i = 0; i < spitters.size() && spitter == null; i++) {
            if (spitters.get(i).getUsername().equals(username)) {
                spitter = spitters.get(i);
            }
        }
        return spitter;
    }
}

package spittr.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spittr.data.SpitterRepository;
import spittr.domain.Spitter;

@Repository
@Transactional
public class JpaSpitterRepository implements SpitterRepository {
    /* PersistenceUnit pozwala na wstrzykniêcie komponentu EntityManagerFactory 
    do repozytorium. */
    //@PersistenceUnit
    //private EntityManagerFactory emf;
    
    /* Ta adnotacja pozwala na przekazanie instancji mened¿era encji do repozytorium. 
    Dziêki temu nie trzeba za ka¿dym razem wywo³ywaæ metody createEntityManager na 
    obiekcie EntityManagerFactory. Nale¿y przekazaæ instancjê mened¿era a nie 
    wstrzykiwaæ go, poniewa¿ nie jest bezpieczny ze wzglêdu na w¹tki. */
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Spitter save(Spitter spitter) {
        /* CreateEntityManager tworzy mened¿era encji u¿ywanego do przeprowadzania 
        operacji na bazie danych. Persist umo¿liwia utworzenie obiektu w bazie. */
        //emf.createEntityManager().persist(spitter);
        em.persist(spitter);
        return spitter;
    }
    
    public Spitter getSpitterById(long id) {
        /* Znajduje obiekt w bazie danych wed³ug podanego id. Dodatkowo rzutuje go 
        na podany jako pierwszy parametr typ. */
        //return emf.createEntityManager().find(Spitter.class, id);
        return em.find(Spitter.class, id);
    }
    
    @Override
    public Spitter findByUsername(String username) {
        /* CreateQuery tworzy instancjê Query dla wykonania zapytania jêzyka Java
        Persistence. Metoda setParameter ustawia wartoœæ parametru który jest oznaczony 
        dwukropkiem :. Metoda getResultList wykonuje zapytanie SELECT i zwraca wynik 
        w postaci listy. */
        //return emf.createEntityManager().createQuery("SELECT * FROM spitter where username = :username", Spitter.class)
        //        .setParameter("username", username).getResultList().get(0);
        return em.createQuery("SELECT * FROM spitter WHERE username = :username", Spitter.class)
                .setParameter("username", username).getResultList().get(0);
    }
    
    public void updateSpitter(Spitter spitter) {
        // £¹czy stan podanego obiektu z ju¿ istniej¹cym obiektem w bazie danych 
        //emf.createEntityManager().merge(spitter);
        em.merge(spitter);
    }
    
}

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
    /* PersistenceUnit pozwala na wstrzykni�cie komponentu EntityManagerFactory 
    do repozytorium. */
    //@PersistenceUnit
    //private EntityManagerFactory emf;
    
    /* Ta adnotacja pozwala na przekazanie instancji mened�era encji do repozytorium. 
    Dzi�ki temu nie trzeba za ka�dym razem wywo�ywa� metody createEntityManager na 
    obiekcie EntityManagerFactory. Nale�y przekaza� instancj� mened�era a nie 
    wstrzykiwa� go, poniewa� nie jest bezpieczny ze wzgl�du na w�tki. */
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Spitter save(Spitter spitter) {
        /* CreateEntityManager tworzy mened�era encji u�ywanego do przeprowadzania 
        operacji na bazie danych. Persist umo�liwia utworzenie obiektu w bazie. */
        //emf.createEntityManager().persist(spitter);
        em.persist(spitter);
        return spitter;
    }
    
    public Spitter getSpitterById(long id) {
        /* Znajduje obiekt w bazie danych wed�ug podanego id. Dodatkowo rzutuje go 
        na podany jako pierwszy parametr typ. */
        //return emf.createEntityManager().find(Spitter.class, id);
        return em.find(Spitter.class, id);
    }
    
    @Override
    public Spitter findByUsername(String username) {
        /* CreateQuery tworzy instancj� Query dla wykonania zapytania j�zyka Java
        Persistence. Metoda setParameter ustawia warto�� parametru kt�ry jest oznaczony 
        dwukropkiem :. Metoda getResultList wykonuje zapytanie SELECT i zwraca wynik 
        w postaci listy. */
        //return emf.createEntityManager().createQuery("SELECT * FROM spitter where username = :username", Spitter.class)
        //        .setParameter("username", username).getResultList().get(0);
        return em.createQuery("SELECT * FROM spitter WHERE username = :username", Spitter.class)
                .setParameter("username", username).getResultList().get(0);
    }
    
    public void updateSpitter(Spitter spitter) {
        // ��czy stan podanego obiektu z ju� istniej�cym obiektem w bazie danych 
        //emf.createEntityManager().merge(spitter);
        em.merge(spitter);
    }
    
}

package spittr.dao;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spittr.domain.Spitter;
import spittr.data.SpitterRepository;

@Repository
@Transactional
@Primary
public class HibernateSpitterRepository implements SpitterRepository {
    SessionFactory sessionFactory;
    
    /* Wi��e fabryk� sesji Hibernate bezpo�rednio do repozytorim, dzi�ki czemu nie 
    trzeba u�ywa� HibernateTemplate */
    @Autowired
    public HibernateSpitterRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    // Pobiera aktualn� sesj� z fabryki sesji, czyli sesj� aktualnej transakcji 
    private Session currentSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }
    
    public long count() {
        return findAll().size();
    }
    
    @Override
    public Spitter save(Spitter spitter) {
        // save zapisuje dane do bazy danych 
        Serializable id = currentSession().save(spitter);
        return new Spitter((Long) id, spitter.getUsername(), spitter.getPassword(), 
                spitter.getFirstName(), spitter.getLastName(), spitter.getEmail());
    }
    
    public Spitter findOne(long id) {
        // zwraca instancj� klasy o podanym identyfikatorze 
        return (Spitter)currentSession().get(Spitter.class, id);
    }
    
    @Override
    public Spitter findByUsername(String username) {
        /* createCriteria tworzy instancj� klasy Criteria kt�ra umo�liwia definiowanie 
        kryteri�w na podstawie kt�rych pobierane s� dane z bazy. Metoda add dodaje 
        kryterium. Metoda eq oznacza "equal" czyli sprawdza czy warto�� podanej 
        kolumny jest r�wna podanej warto�ci. List zapisuje wynik jako list�. */
        return (Spitter)currentSession().createCriteria(Spitter.class)
                .add(Restrictions.eq("username", username)).list().get(0);
    }
    
    public List<Spitter> findAll() {
        return (List<Spitter>)currentSession().createCriteria(Spitter.class).list();
    }
}

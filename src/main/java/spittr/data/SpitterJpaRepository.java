package spittr.data;

import org.springframework.data.jpa.repository.JpaRepository;
import spittr.domain.Spitter;

/* Interfejs JpaRepository posiada kilka przygotowanych metod s³u¿¹cych do 
komunikacji z baz¹ danych. Dziêki temu nie trzeba dla ka¿dej klasy definiowaæ takich 
samych metod i mo¿na skorzystaæ z ju¿ gotowych. Jest to typ sparametryzowany który 
umo¿liwia okreœlenie typu obiektów na jakich siê operuje oraz typu ich identyfikatora. */
public interface SpitterJpaRepository extends JpaRepository<Spitter, Long> {
    
}

package spittr.data;

import org.springframework.data.jpa.repository.JpaRepository;
import spittr.domain.Spitter;

/* Interfejs JpaRepository posiada kilka przygotowanych metod s�u��cych do 
komunikacji z baz� danych. Dzi�ki temu nie trzeba dla ka�dej klasy definiowa� takich 
samych metod i mo�na skorzysta� z ju� gotowych. Jest to typ sparametryzowany kt�ry 
umo�liwia okre�lenie typu obiekt�w na jakich si� operuje oraz typu ich identyfikatora. */
public interface SpitterJpaRepository extends JpaRepository<Spitter, Long> {
    
}

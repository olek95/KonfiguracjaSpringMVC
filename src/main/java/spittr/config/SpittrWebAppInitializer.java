package spittr.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/* Konfiguracja serwletu dyspozytora. Odgrywa on centraln¹ rolê w Spring MVC. 
Tu w pierwszej kolejnoœci trafiaja ¿¹dania i s¹ one przekierowanie do pozosta³ych komponentów */
public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /* Zwraca tablicê œcie¿ek, którym przypisano odwzorowanie serwletu dystrybutora. 
    Tu domyœlnym serwletem jest "/" i to on obs³u¿y wszystkie ¿¹dania przychodz¹ce 
    do aplikacji. */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    /* Klasy opatrzone adnotacj¹ @Configuration zwrócone przez t¹ metodê, s³u¿¹ 
    do konfiguracji kontekstu aplikacji dla serwletu nas³uchuj¹æego ContextLoaderListener*/
    @Override 
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }
    
    /* Umo¿liwia serwletowi dystrybutora za³adowanie do kontekstu aplikacji 
    komponentów zdefiniowanych w klasie konfiguracji WebConfig.*/
    @Override 
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }
    
    /* Serwlet dystrybutora s³u¿y do za³adowania komponentów internetowych np.
    kontrolery, producenci widoków i odwzorowania obs³ugi, natomiast serwlet 
    nas³uchuj¹cy ContextLoaderListener s³u¿y do wczytywania pozosta³ych komponentów 
    np. komponenty warstwy poœredniej i warstwy danych*/
    
    
    /* Metoda wywo³ywana po zarejestrowania serwletu dystrybutora przez  
    AbstractAnnotationConfigDispatcherServletInitializer za pomoc¹ kontenera serwletów.
    ServletRegistration.Dynamic jest obiektem pozyskanym w wyniku rejestracji serwletu.*/
    @Override 
    protected void customizeRegistration(Dynamic registration) {
        /* Aby skorzystaæ z mo¿liwoœci w zakresie konfiguracji ¿adañ wieloczêœciowych 
        (wysy³anie plików), nale¿y w³¹czyæ ich obs³ugê przy rejestracji serwletu. 
        S³u¿y do tego poni¿sza metoda. Podana œcie¿ka okresla miejsce przechowywania 
        przes³anych plików. Istnieje równie¿ druga wersja konstruktora, która poza 
        œcie¿k¹, posiada te¿ nastêpuj¹ce parametry: maksymalny rozmiar przesy³anych
        plików, maksymalny rozmiar ca³ego ¿¹dania wieloczêœciowego oraz maksymalny 
        rozmiar pliku, który mo¿e zostaæ przes³any bez zapisu do tymczasowej lokacji.
        Wszystkie te wartoœci s¹ podawane w bajtach. Od podanej œcie¿ki bêdzie siê
        zaczynaæ œcie¿ka podana podczas zapisywania pliku */
        registration.setMultipartConfig(new MultipartConfigElement("/tmp/spittr/uploads"));
    }
}

package spittr.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/* Konfiguracja serwletu dyspozytora. Odgrywa on centraln� rol� w Spring MVC. 
Tu w pierwszej kolejno�ci trafiaja ��dania i s� one przekierowanie do pozosta�ych komponent�w */
public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /* Zwraca tablic� �cie�ek, kt�rym przypisano odwzorowanie serwletu dystrybutora. 
    Tu domy�lnym serwletem jest "/" i to on obs�u�y wszystkie ��dania przychodz�ce 
    do aplikacji. */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    /* Klasy opatrzone adnotacj� @Configuration zwr�cone przez t� metod�, s�u�� 
    do konfiguracji kontekstu aplikacji dla serwletu nas�uchuj��ego ContextLoaderListener*/
    @Override 
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }
    
    /* Umo�liwia serwletowi dystrybutora za�adowanie do kontekstu aplikacji 
    komponent�w zdefiniowanych w klasie konfiguracji WebConfig.*/
    @Override 
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }
    
    /* Serwlet dystrybutora s�u�y do za�adowania komponent�w internetowych np.
    kontrolery, producenci widok�w i odwzorowania obs�ugi, natomiast serwlet 
    nas�uchuj�cy ContextLoaderListener s�u�y do wczytywania pozosta�ych komponent�w 
    np. komponenty warstwy po�redniej i warstwy danych*/
    
    
    /* Metoda wywo�ywana po zarejestrowania serwletu dystrybutora przez  
    AbstractAnnotationConfigDispatcherServletInitializer za pomoc� kontenera serwlet�w.
    ServletRegistration.Dynamic jest obiektem pozyskanym w wyniku rejestracji serwletu.*/
    @Override 
    protected void customizeRegistration(Dynamic registration) {
        /* Aby skorzysta� z mo�liwo�ci w zakresie konfiguracji �ada� wielocz�ciowych 
        (wysy�anie plik�w), nale�y w��czy� ich obs�ug� przy rejestracji serwletu. 
        S�u�y do tego poni�sza metoda. Podana �cie�ka okresla miejsce przechowywania 
        przes�anych plik�w. Istnieje r�wnie� druga wersja konstruktora, kt�ra poza 
        �cie�k�, posiada te� nast�puj�ce parametry: maksymalny rozmiar przesy�anych
        plik�w, maksymalny rozmiar ca�ego ��dania wielocz�ciowego oraz maksymalny 
        rozmiar pliku, kt�ry mo�e zosta� przes�any bez zapisu do tymczasowej lokacji.
        Wszystkie te warto�ci s� podawane w bajtach. Od podanej �cie�ki b�dzie si�
        zaczyna� �cie�ka podana podczas zapisywania pliku */
        registration.setMultipartConfig(new MultipartConfigElement("/tmp/spittr/uploads"));
    }
}

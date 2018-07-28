package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration 
// W��cza ustawienia bezpiecze�stwa. 
@EnableWebSecurity
/* W Spring MVC mo�na u�y� poni�szej adnotacji. Jednym z jej zada� jest konfiguracja 
rozwi�zywania argument�w, aby metoda obs�ugi �adania uzyska�a dost�p do danch 
uwierzytelniania u�ytkownika. Konfiguruje te� komponenty, kt�re automatycznie dodaj� 
do formularzy ukryte pola token�w CSRF. */
//@EnableWebMvcSecurity
/* Konfiguracja Spring Security musi si� znale�� w komponencie implementuj�cym 
interfejs WebSecurityConfigurer lub rozszerzaj�cym klas� WebSecurityConfigurerAdapter.
Posiada on nast�puj�ce metody do nadpisania: configure(WebSecurity) - konfiguracja 
�a�cucha filtr�w, configure(HttpSecurity) - konfiguracja sposobu zabezpieczenia ��da�, 
configure(AuthenticationManagerBuilder) - konfiguracja us�ug szczeg��w u�ytkownika. 
Domy�lna implementacja 2 wersji blokuje przed jakimkolwiek wej�ciem do aplikacji. */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
}

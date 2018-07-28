package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration 
// W³¹cza ustawienia bezpieczeñstwa. 
@EnableWebSecurity
/* W Spring MVC mo¿na u¿yæ poni¿szej adnotacji. Jednym z jej zadañ jest konfiguracja 
rozwi¹zywania argumentów, aby metoda obs³ugi ¿adania uzyska³a dostêp do danch 
uwierzytelniania u¿ytkownika. Konfiguruje te¿ komponenty, które automatycznie dodaj¹ 
do formularzy ukryte pola tokenów CSRF. */
//@EnableWebMvcSecurity
/* Konfiguracja Spring Security musi siê znaleŸæ w komponencie implementuj¹cym 
interfejs WebSecurityConfigurer lub rozszerzaj¹cym klasê WebSecurityConfigurerAdapter.
Posiada on nastêpuj¹ce metody do nadpisania: configure(WebSecurity) - konfiguracja 
³añcucha filtrów, configure(HttpSecurity) - konfiguracja sposobu zabezpieczenia ¿¹dañ, 
configure(AuthenticationManagerBuilder) - konfiguracja us³ug szczegó³ów u¿ytkownika. 
Domyœlna implementacja 2 wersji blokuje przed jakimkolwiek wejœciem do aplikacji. */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
}

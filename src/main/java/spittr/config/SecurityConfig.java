package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    @Override
    /* KLasa AuthenticationManagerBuilder udostêpnia kilka metod umo¿liwiaj¹cych 
    konfiguracjê mechanizmów Spring Security. */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* InmemoryAuthentication pozwala w³¹czyæ i skonfigurowaæ bazê u¿ytkowników 
        zapisan¹ w pamiêci. WithUser tworzy u¿ytkownika. Jako parametr przyjmuje 
        jego nazwê, a zwraca obiekt typu UserDetailsManagerConfigurer.UserDetailsBuilder, 
        który udostêpnia metody umo¿liwiaj¹ce dalsz¹ konfiguracjê u¿ytkownika. 
        Metoda password okreœla has³o, a roles przydziela uprawnienia. Istnieje 
        podobna metoda - authorities w której role przydziela siê poprzedzaj¹c je 
        prefiksem ROLE_ np. ROLE_USER. Konfiguracje ustawieñ poszczególnych 
        u¿ytkowników rozdzielone s¹ za pomoc¹ metody and. */
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")
                .and().withUser("admin").password("password").roles("USER", "ADMIN");
    }
}

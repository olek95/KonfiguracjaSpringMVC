package spittr.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

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
    //@Autowired 
    //DataSource dataSource; 
    
    @Override
    /* KLasa AuthenticationManagerBuilder udost�pnia kilka metod umo�liwiaj�cych 
    konfiguracj� mechanizm�w Spring Security. */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* InmemoryAuthentication pozwala w��czy� i skonfigurowa� baz� u�ytkownik�w 
        zapisan� w pami�ci. WithUser tworzy u�ytkownika. Jako parametr przyjmuje 
        jego nazw�, a zwraca obiekt typu UserDetailsManagerConfigurer.UserDetailsBuilder, 
        kt�ry udost�pnia metody umo�liwiaj�ce dalsz� konfiguracj� u�ytkownika. 
        Metoda password okre�la has�o, a roles przydziela uprawnienia. Istnieje 
        podobna metoda - authorities w kt�rej role przydziela si� poprzedzaj�c je 
        prefiksem ROLE_ np. ROLE_USER. Konfiguracje ustawie� poszczeg�lnych 
        u�ytkownik�w rozdzielone s� za pomoc� metody and. */
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")
                .and().withUser("admin").password("password").roles("USER", "ADMIN");
        /* Do konfiguracji uwierzytelnienia z wykorzystaniem bazy JDBC mo�na 
        zastosowa� metod� jdbcAuthentication. Za pomoc� dataSource nale�y poda� 
        �r�d�o danych. Je�li domy�lnie u�ywane zapytania nie s� wystarczaj�ce, 
        mo�na je nadpisac. UsersByUsernameQuery pobiera zapytanie uwierzytelnienia, 
        czyli pobieraj�ce nazw�, has�o oraz informacj� o tym czy konto jest w��czone. 
        AuthoritiesByUsernameQuery s�u�y do autoryzacji. Istnieje te� groupAuthoritiesByUsername 
        kt�ra pobiera uprawnienia przyznane u�ytkownikowi jako cz�onkowi grupy. 
        Aby zaszyfrowa� has�o nale�y u�y� metody passwordEncoder, kt�ra przyjmuje 
        dowoln� implementacj� interfejsu PasswordEncoder. S� trzy: BCryptPasswordEncoder, 
        NoOpPasswordEncoder i StandardPasswordEncoder. Aby przygotowa� w�asn� implementacj�
        nalezy nadpisa� metody encode i matches. Has�a w bazie nigdy nie s� odszyfrowywane, 
        zamiast tego szyfrowane s� has�� wprowadzone przez u�ytkownika i takie has�o 
        jest por�wnywane z has�em z bazy za pomoc� matches. */
        /*auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, true from Spitter where username=?")
                .authoritiesByUsernameQuery("select username, 'ROLE_USER' from Spitter where username=?")
                .passwordEncoder(new StandardPasswordEncoder("53Kr3t"));*/
    }
    
    /* Metoda configure z parametrem HttpSecurity umo�liwia przechwytywanie (zabezpieczanie)
    ��da�. Decyduje kt�re ��dania wymagaj� uwierzytelnienia, a kt�re nie. */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* AuthorizeRequests umo�liwia konfiguracj� szczeg��w zabezpiecze� na 
        poziomie ��dania. Pierwsze wywo�anie metody antMatches i authenticated 
        powoduje �e podane ��danie nale�y uwierzytelni�. Drugie wywo�anie tej metody 
        m�wi �e ka�de ��danie POST o podanym url nale�y uwierzytelni�. Natomiast 
        metoda anyRequest i permitAll m�wi� �e wszystkie inne ��dania maj� by� 
        dost�pne bez uwierzytelnienia i uprawnie�. Aby do okreslenia nazwy �adania 
        u�y� wyra�enia regularnego nale�y zastosowa� metod� regexMatchers. 
        Aby wymusi� skorzystanie url-a z kana�u zaszyfrowanego (HTTPS), nale�y 
        zastosowa� metod� requiresChannel a nast�pnie requiresSecure na wybranym 
        adresie. Dzi�ki temu przesy�ane informacje b�d� zaszyfrowane. Natomiast 
        metoda requiresInsecure powoduje przesy�anie ��da� zawsze przez kana� HTTP. */
        http.authorizeRequests().antMatchers("/spitters/me").authenticated()
                .antMatchers(HttpMethod.POST, "/spittles").authenticated()
                .anyRequest().permitAll().and().requiresChannel()
                .antMatchers("/spitter/form").requiresSecure().antMatchers("/")
                .requiresInsecure();
    }
}

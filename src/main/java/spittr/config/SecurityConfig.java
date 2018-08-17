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
// W³¹cza ustawienia bezpieczeñstwa. 
//@EnableWebSecurity
/* W Spring MVC mo¿na u¿yæ poni¿szej adnotacji. Jednym z jej zadañ jest konfiguracja 
rozwi¹zywania argumentów, aby metoda obs³ugi ¿adania uzyska³a dostêp do danch 
uwierzytelniania u¿ytkownika. Konfiguruje te¿ komponenty, które automatycznie dodaj¹ 
do formularzy ukryte pola tokenów CSRF. */
@EnableWebMvcSecurity
/* Konfiguracja Spring Security musi siê znaleŸæ w komponencie implementuj¹cym 
interfejs WebSecurityConfigurer lub rozszerzaj¹cym klasê WebSecurityConfigurerAdapter.
Posiada on nastêpuj¹ce metody do nadpisania: configure(WebSecurity) - konfiguracja 
³añcucha filtrów, configure(HttpSecurity) - konfiguracja sposobu zabezpieczenia ¿¹dañ, 
configure(AuthenticationManagerBuilder) - konfiguracja us³ug szczegó³ów u¿ytkownika. 
Domyœlna implementacja 2 wersji blokuje przed jakimkolwiek wejœciem do aplikacji. */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //@Autowired 
    //DataSource dataSource; 
    
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
        /* Do konfiguracji uwierzytelnienia z wykorzystaniem bazy JDBC mo¿na 
        zastosowaæ metodê jdbcAuthentication. Za pomoc¹ dataSource nale¿y podaæ 
        Ÿród³o danych. Jeœli domyœlnie u¿ywane zapytania nie s¹ wystarczaj¹ce, 
        mo¿na je nadpisac. UsersByUsernameQuery pobiera zapytanie uwierzytelnienia, 
        czyli pobieraj¹ce nazwê, has³o oraz informacjê o tym czy konto jest w³¹czone. 
        AuthoritiesByUsernameQuery s³u¿y do autoryzacji. Istnieje te¿ groupAuthoritiesByUsername 
        która pobiera uprawnienia przyznane u¿ytkownikowi jako cz³onkowi grupy. 
        Aby zaszyfrowaæ has³o nale¿y u¿yæ metody passwordEncoder, która przyjmuje 
        dowoln¹ implementacjê interfejsu PasswordEncoder. S¹ trzy: BCryptPasswordEncoder, 
        NoOpPasswordEncoder i StandardPasswordEncoder. Aby przygotowaæ w³asn¹ implementacjê
        nalezy nadpisaæ metody encode i matches. Has³a w bazie nigdy nie s¹ odszyfrowywane, 
        zamiast tego szyfrowane s¹ has³¹ wprowadzone przez u¿ytkownika i takie has³o 
        jest porównywane z has³em z bazy za pomoc¹ matches. */
        /*auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, true from Spitter where username=?")
                .authoritiesByUsernameQuery("select username, 'ROLE_USER' from Spitter where username=?")
                .passwordEncoder(new StandardPasswordEncoder("53Kr3t"));*/
    }
    
    /* Metoda configure z parametrem HttpSecurity umo¿liwia przechwytywanie (zabezpieczanie)
    ¿¹dañ. Decyduje które ¿¹dania wymagaj¹ uwierzytelnienia, a które nie. Po 
    nadpisaniu tej metody traci siê dostêp do domyœlnej strony logowania. */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* AuthorizeRequests umo¿liwia konfiguracjê szczegó³ów zabezpieczeñ na 
        poziomie ¿¹dania. Pierwsze wywo³anie metody antMatches i authenticated 
        powoduje ¿e podane ¿¹danie nale¿y uwierzytelniæ. Drugie wywo³anie tej metody 
        mówi ¿e ka¿de ¿¹danie POST o podanym url nale¿y uwierzytelniæ. Natomiast 
        metoda anyRequest i permitAll mówi¹ ¿e wszystkie inne ¿¹dania maj¹ byæ 
        dostêpne bez uwierzytelnienia i uprawnieñ. Aby do okreslenia nazwy ¿adania 
        u¿yæ wyra¿enia regularnego nale¿y zastosowaæ metodê regexMatchers. 
        Aby wymusiæ skorzystanie url-a z kana³u zaszyfrowanego (HTTPS), nale¿y 
        zastosowaæ metodê requiresChannel a nastêpnie requiresSecure na wybranym 
        adresie. Dziêki temu przesy³ane informacje bêd¹ zaszyfrowane. Natomiast 
        metoda requiresInsecure powoduje przesy³anie ¿¹dañ zawsze przez kana³ HTTP. 
        Metoda formLogin w³¹cza domyœln¹ stronê logowania. Aby u¿yæ w³asnej strony 
        logowania nale¿y u¿yæ metody loginPage do której podaje siê url strony. 
        Aby w³¹czyæ uwierzytelnienie HTTP Basic trzeba wywo³aæ httpBasic. Polega ono 
        na pojawieniu siê okna dialogowego z proœb¹ o podanie danych logowania. 
        Mo¿na te¿ okreœliæ domenê poprzez ustawienie realmName. Aby w³¹czyæ funkcjê 
        pamiêtaj¹c¹ zalogowanego u¿ytkownika, nale¿y wywo³aæ metodê rememberMe. 
        Metoda tokenValiditySeconds okresla liczbê sekund przez które bêdzie pamiêtane 
        ciasteczko z danymi logowania (domyœlnie 2 tygodnie). Mo¿na te¿ okreœliæ 
        nazwê klucza prywatnego przechowywanego w ciasteczku za pomoc¹ key(). Domyœlnie
        klucz to SpringSecured. Metoda logout() udostêpnia metody konfiguracji 
        zachowania w trakcie wylogowywania. LogoutSuccessUrl wskazuje url na który
        u¿ytkownik jest przekierowany po wylogowaniu, natomiast logoutUrl nadpisuje 
        œcie¿kê ¿adania wylogowania. Metoda access udostêpnia dostêp jeœli wartoœci¹
        podanego wyra¿enia SpEL bêdzie true. */
        http.formLogin().loginPage("/login").and().rememberMe().tokenValiditySeconds(2419200)
                .key("spittrKey").and().logout().logoutSuccessUrl("/").logoutUrl("/signout")
                .and().httpBasic().realmName("Spittr").and().authorizeRequests()
                .antMatchers("/spitters/me").authenticated()
                .antMatchers(HttpMethod.POST, "/spittles").authenticated()
                .antMatchers("/admin").access("isAuthenticated()")
                .anyRequest().permitAll().and().requiresChannel()
                .antMatchers("/spitter/form").requiresSecure().antMatchers("/")
                .requiresInsecure();
    }
}

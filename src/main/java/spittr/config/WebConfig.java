package spittr.config;

import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@EnableWebMvc // w��cza Spring MVC. W XML u�ywa si� <mvc:annotation-drive>
@ComponentScan("spittr.web")
public class WebConfig extends WebMvcConfigurerAdapter {
    
    // Konfiguracja producenta widok�w
    /*@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        Producent widok�w s�u�y do wyszukiwania plik�w jsp poprzez dodanie prefiksu 
        i sufiksu do nazw widok�w np. widok o nazwie home b�dzie odczytany jako 
        /WEB-INF/views/home.jsp
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        umo�liwia produkcj� widok�w JstlView zamiast InternalResourceView. 
        Umo�liwiaj� one w�a�ciwe formatowanie warto�ci opartych na ustawieniach 
        regionalnych np. daty, waluty, za pomoc� znacznik�w formatowania JSTL 
        resolver.setViewClass(JstlView.class);
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }*/
    
    // Konfiguracja obs�ugi statycznych zasob�w
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        /* Powoduje �e ��dania do zasob�w statycznych nie be� ju� obs�ugiwane a zostan� 
        przes�ane do domy�lnego serwletu kontenera serwlet�w. */
        configurer.enable();
    }
    
    /* Interfejs MessageSource jest przeznaczony dla klas �r�de� danych. Nalezy 
    poda� name, bo inaczej pojawi si� b��d z niewykryciem klucza w pliku w�a�ciwo�ci */
    /*@Bean(name ="messageSource")
    public MessageSource messageSource() {
        // Jest to najcz�ciej stosowana klasa implementuj�ca MessageSource. Pobiera 
        // komunikat z pliku w�a�ciwo�ci. W przeciwie�stwie do ReloadableResourceBundleMessageSource
        // wynika ponownej kompilacji lub restartu aplikacji po zmianie pliku w�a�ciwo�ci 
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // okresla podstawow� nazw� (baz�) pliku w�a�ciwo�ci, do kt�rej jest doklajany j�zyk np. 
        // messages.properties - domyslny plik, messages_en.properties - j�zyk angielski itd. 
        // Plik jest szukany w g��wnym folderze �cie�ki klas 
        messageSource.setBasename("messages");
        return messageSource;
    }*/
    
    @Bean(name="messageSource")
    public MessageSource messageSource() {
        /* Dzia�a tak samo jak ResourceBundleMessageSource, ale udost�pnia mo�liwo�� 
        ponownego wczytania w�a�ciwo�ci komunikat�w bez kompilacji lub restartu aplikacji. */
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        /* R�nica jest taka, �e warto�� basename ustawia si� tak, aby nazwy plik�w 
        szukane by�y poza aplikacj� (a nie w �cie�ce klas). Mo�na j� ustawi� tak, aby 
        wyszukiwa�a komunikaty w �cie�ce klas (prefiks: classpath), w �cie�ce do systemu 
        plik�w (prefiks: file) albo w g��wnym katalogu aplikacji (gdy nie u�ywa si� 
        �adnego prefiksu). */
        messageSource.setBasename("classpath:/messages");
        /* Ustawia liczb� sekund przechowywania wczytanych plik�w w�a�ciwo�ci. 
        Domy�lnie -1, co wskazuje na pami�� podr�czn�. Liczba dodatnia buforuje 
        za�adowane pliki w�a�ciwo�ci przez podan� liczb� sekund. */
        messageSource.setCacheSeconds(10);
        return messageSource;
    }
    
    //----------------- TILES --------------------------------------------------
    
    /* TilesConfigurer to komponent lokalizuj�cy i wczytuj�cy definicje kafelk�w
    oraz zarz�dzaj�cy og�lnie dzia�aniem Apache Tiles. */
    //@Bean
    //public TilesConfigurer tilesConfigurer() {
    //    TilesConfigurer tiles = new TilesConfigurer();
        // okre�la lokalizacj� definicji kafelk�w 
    //    tiles.setDefinitions(new String[] {"/WEB-INF/layout/tiles.xml"});
        // w��cza od�wie�anie 
    //    tiles.setCheckRefresh(true);
    //    return tiles;
    //}
    
    //@Bean
    //public ViewResolver viewResolver() {
        // komponent mapuj�cy nazwy widok�w na definicje kafelk�w 
    //    return new TilesViewResolver();
    //}
    
    // ---------------THYMELEAF ------------------------------------------------
    @Bean 
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        // producent widok�w odwzorowuj�cy nazwy widok�w na szablony Thymeleaf
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        // ustawia mechanizm szablon�w przetwarzaj�cy szablony 
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
    
    @Bean 
    public SpringTemplateEngine templateEngine(TemplateResolver templateResolveer) {
        // silnik przetwarzaj�cy szablony i generuj�cy wyniki
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolveer);
        /* rejestruje dialekt bezpiecze�stwa dla szablon�w Thymeleaf. Umo�liwia 
        to korzystanie ze znacznik�w takich jak np. authorize */
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }
    
    @Bean 
    public TemplateResolver templateResolver() {
        // producent kt�ry wczytuje szablony Thymeleaf
        TemplateResolver templateResolver = new ServletContextTemplateResolver(); 
        // warto�ci prefix i suffix wraz z nazw� widoku u�ywane s� do lokalizowania szablonu 
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // warto�� HTML5 wskazuje, �e oczekiwanym wynikiem generowania szablonu jest kod HTML5 
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }
    // -------------------------------------------------------------------------
    
    /* Zajmuje si� logik� analizy danych w ��daniu wielocz�ciowym. Istniej� dwie 
    implementacje MultipartResolvera - CommonsMultipartResolver oraz StandardServletMultipartResolver. 
    Pierwsza jest to zewn�trzna biblioteka, powinno si� j� korzysta� gdy wdra�a si� 
    aplikacj� na kontener w wersji wcze�niejszej ni� 3.0 lub nie korzysta si� ze 
    Springa w wersji 3.1 lub wy�szej. Powinno si� u�ywa� drugiej implementacji, kt�ra 
    wykorzystuje dost�pne w kontenerze serwlet�w mechanizmy. 
    */
    @Bean 
    public MultipartResolver multipartResolver() throws IOException {
        return new StandardServletMultipartResolver(); 
    }
    
    /* Umo�liwia t�umaczenie wyj�tk�w w niezawieraj�cej szablonu klasie repozytorium 
    Hibernate. Dodaje doradc� do ka�dego oznaczonego adnotacj� Repository komponentu  */
    @Bean
    public BeanPostProcessor persistanceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}

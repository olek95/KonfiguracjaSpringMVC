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
@EnableWebMvc // w³¹cza Spring MVC. W XML u¿ywa siê <mvc:annotation-drive>
@ComponentScan("spittr.web")
public class WebConfig extends WebMvcConfigurerAdapter {
    
    // Konfiguracja producenta widoków
    /*@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        Producent widoków s³u¿y do wyszukiwania plików jsp poprzez dodanie prefiksu 
        i sufiksu do nazw widoków np. widok o nazwie home bêdzie odczytany jako 
        /WEB-INF/views/home.jsp
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        umo¿liwia produkcjê widoków JstlView zamiast InternalResourceView. 
        Umo¿liwiaj¹ one w³aœciwe formatowanie wartoœci opartych na ustawieniach 
        regionalnych np. daty, waluty, za pomoc¹ znaczników formatowania JSTL 
        resolver.setViewClass(JstlView.class);
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }*/
    
    // Konfiguracja obs³ugi statycznych zasobów
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        /* Powoduje ¿e ¿¹dania do zasobów statycznych nie be¹ ju¿ obs³ugiwane a zostan¹ 
        przes³ane do domyœlnego serwletu kontenera serwletów. */
        configurer.enable();
    }
    
    /* Interfejs MessageSource jest przeznaczony dla klas Ÿróde³ danych. Nalezy 
    podaæ name, bo inaczej pojawi siê b³¹d z niewykryciem klucza w pliku w³aœciwoœci */
    /*@Bean(name ="messageSource")
    public MessageSource messageSource() {
        // Jest to najczêœciej stosowana klasa implementuj¹ca MessageSource. Pobiera 
        // komunikat z pliku w³aœciwoœci. W przeciwieñstwie do ReloadableResourceBundleMessageSource
        // wynika ponownej kompilacji lub restartu aplikacji po zmianie pliku w³aœciwoœci 
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // okresla podstawow¹ nazwê (bazê) pliku w³aœciwoœci, do której jest doklajany jêzyk np. 
        // messages.properties - domyslny plik, messages_en.properties - jêzyk angielski itd. 
        // Plik jest szukany w g³ównym folderze œcie¿ki klas 
        messageSource.setBasename("messages");
        return messageSource;
    }*/
    
    @Bean(name="messageSource")
    public MessageSource messageSource() {
        /* Dzia³a tak samo jak ResourceBundleMessageSource, ale udostêpnia mo¿liwoœæ 
        ponownego wczytania w³aœciwoœci komunikatów bez kompilacji lub restartu aplikacji. */
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        /* Ró¿nica jest taka, ¿e wartoœæ basename ustawia siê tak, aby nazwy plików 
        szukane by³y poza aplikacj¹ (a nie w œcie¿ce klas). Mo¿na j¹ ustawiæ tak, aby 
        wyszukiwa³a komunikaty w œcie¿ce klas (prefiks: classpath), w œcie¿ce do systemu 
        plików (prefiks: file) albo w g³ównym katalogu aplikacji (gdy nie u¿ywa siê 
        ¿adnego prefiksu). */
        messageSource.setBasename("classpath:/messages");
        /* Ustawia liczbê sekund przechowywania wczytanych plików w³aœciwoœci. 
        Domyœlnie -1, co wskazuje na pamiêæ podrêczn¹. Liczba dodatnia buforuje 
        za³adowane pliki w³aœciwoœci przez podan¹ liczbê sekund. */
        messageSource.setCacheSeconds(10);
        return messageSource;
    }
    
    //----------------- TILES --------------------------------------------------
    
    /* TilesConfigurer to komponent lokalizuj¹cy i wczytuj¹cy definicje kafelków
    oraz zarz¹dzaj¹cy ogólnie dzia³aniem Apache Tiles. */
    //@Bean
    //public TilesConfigurer tilesConfigurer() {
    //    TilesConfigurer tiles = new TilesConfigurer();
        // okreœla lokalizacjê definicji kafelków 
    //    tiles.setDefinitions(new String[] {"/WEB-INF/layout/tiles.xml"});
        // w³¹cza odœwie¿anie 
    //    tiles.setCheckRefresh(true);
    //    return tiles;
    //}
    
    //@Bean
    //public ViewResolver viewResolver() {
        // komponent mapuj¹cy nazwy widoków na definicje kafelków 
    //    return new TilesViewResolver();
    //}
    
    // ---------------THYMELEAF ------------------------------------------------
    @Bean 
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        // producent widoków odwzorowuj¹cy nazwy widoków na szablony Thymeleaf
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        // ustawia mechanizm szablonów przetwarzaj¹cy szablony 
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
    
    @Bean 
    public SpringTemplateEngine templateEngine(TemplateResolver templateResolveer) {
        // silnik przetwarzaj¹cy szablony i generuj¹cy wyniki
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolveer);
        /* rejestruje dialekt bezpieczeñstwa dla szablonów Thymeleaf. Umo¿liwia 
        to korzystanie ze znaczników takich jak np. authorize */
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }
    
    @Bean 
    public TemplateResolver templateResolver() {
        // producent który wczytuje szablony Thymeleaf
        TemplateResolver templateResolver = new ServletContextTemplateResolver(); 
        // wartoœci prefix i suffix wraz z nazw¹ widoku u¿ywane s¹ do lokalizowania szablonu 
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // wartoœæ HTML5 wskazuje, ¿e oczekiwanym wynikiem generowania szablonu jest kod HTML5 
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }
    // -------------------------------------------------------------------------
    
    /* Zajmuje siê logik¹ analizy danych w ¿¹daniu wieloczêœciowym. Istniej¹ dwie 
    implementacje MultipartResolvera - CommonsMultipartResolver oraz StandardServletMultipartResolver. 
    Pierwsza jest to zewnêtrzna biblioteka, powinno siê j¹ korzystaæ gdy wdra¿a siê 
    aplikacjê na kontener w wersji wczeœniejszej ni¿ 3.0 lub nie korzysta siê ze 
    Springa w wersji 3.1 lub wy¿szej. Powinno siê u¿ywaæ drugiej implementacji, która 
    wykorzystuje dostêpne w kontenerze serwletów mechanizmy. 
    */
    @Bean 
    public MultipartResolver multipartResolver() throws IOException {
        return new StandardServletMultipartResolver(); 
    }
    
    /* Umo¿liwia t³umaczenie wyj¹tków w niezawieraj¹cej szablonu klasie repozytorium 
    Hibernate. Dodaje doradcê do ka¿dego oznaczonego adnotacj¹ Repository komponentu  */
    @Bean
    public BeanPostProcessor persistanceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}

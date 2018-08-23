package spittr.config;

import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
public class DataSourceConfiguration {
    
    @Bean
    public DataSource dataSource() throws SQLException{
        /* Ten typ klasy nale¿y do Ÿróde³ danych opartych na sterowniku JDBC. 
        Oprócz tej klasy s¹ jeszcze SimpleDriverDataSource i SingleConnectionDataSource. 
        Ta klasa zwraca nowe po³¹czenie za ka¿dym razem, gdy po³aczenie jest wymagane. 
        Nie oferuje puli po³¹czeñ. */
        DriverManagerDataSource ds = new DriverManagerDataSource();
        // ustawia nazwê sterownika jdbc 
        ds.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        // ustawia adres url dla po³¹czenia przez sterownik 
        ds.setUrl("jdbc:mysql://localhost:3306/spittr");
        // ustawia nazwê u¿ytkownika 
        ds.setUsername("root");
        // ustawia has³o 
        ds.setPassword("haslo1234");
        return ds; 
    }
    
    /* JdbcTemplate jest klas¹ szablonow¹, dziêki której nie trzeba pisaæ powielonego 
    kodu np. obs³ugi wyj¹tków czy zamykanie po³¹czenia. Ta klasa szablonowa jest 
    najbardziej podstawowym szablonem, który zapewnia prosty dostêp do bazy danych i 
    zapytania z indeksowanymi parametrami. Oprócz tej klasy istniej¹ te¿: 
    NamedParameterJdbcTemplate oraz SimpleJdbcTemplate. */
    /*@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        // jdbctemplate potrzebuje do dzia³ania Ÿród³a danych 
        return new JdbcTemplate(dataSource); 
    }*/
    
    /* Ten szablon umo¿liwia u¿ywanie parametrów nazwanych. Oznacza to ¿e nie 
    trzeba pamiêtaæ kolejnoœci parametrów tak jak w zwyk³ym JdbcTemplate podczas
    odwo³ywania siê za pomoc¹ ?, tylko mo¿na odwo³aæ siê do nich za pomoc¹ nazwy. */
    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
    
    /*LocalSessionFactoryBean jest jedn¹ z fabryk sesji dostêpnych w Hibernate. 
    Implementacja SessionFactory jest odpowiedzialna za otwieranie i zamykanie sesji
    Hibernate, które umo¿liwiaj¹ wykonywanie czynnoœci na bazie danych. Wybrany typ
    umo¿liwia mapowanie obiektów na wpisy bazy zarówno w XML, jak i jako adnotacje. 
    Jeœli wybierze siê t¹ sam¹ klasê ale z pakietu org.springframework.orm.hibernate3
    to bêdzie umo¿liwia³a tylko mapowanie w XML. W wersji Hibernate od 3.2 do 4 trzeba 
    wtedy u¿yæ AnnotationSessionFactoryBean które umo¿liwia zapis w postaci adnotacji. */
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        // Ustawia referencê do Ÿród³a danych 
        sfb.setDataSource(dataSource);
        /* Ustawia pakiety w których poszukiwane s¹ klasy oznaczone jako utrwalane 
        przez Hibernate, w³¹czaj¹c w to klasy z adnotacjami JPA @Entity i @MappedSuperClass 
        oraz w³asn¹ adnotacj¹ Hibernate @Entity */ 
        sfb.setPackagesToScan(new String[]{"spittr.domain"});
        Properties props = new Properties();
        props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        /* ustawia szczegó³owe wytyczne dla Hibernate, tu informujemy ¿e bêdzie 
        wspó³pracowa³ z baz¹ MySQL */ 
        sfb.setHibernateProperties(props);
        return sfb;
    }
}

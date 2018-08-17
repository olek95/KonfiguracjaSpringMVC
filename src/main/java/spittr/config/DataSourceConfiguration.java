package spittr.config;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        // jdbctemplate potrzebuje do dzia³ania Ÿród³a danych 
        return new JdbcTemplate(dataSource); 
    }
}

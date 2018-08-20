package spittr.config;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfiguration {
    
    @Bean
    public DataSource dataSource() throws SQLException{
        /* Ten typ klasy nale�y do �r�de� danych opartych na sterowniku JDBC. 
        Opr�cz tej klasy s� jeszcze SimpleDriverDataSource i SingleConnectionDataSource. 
        Ta klasa zwraca nowe po��czenie za ka�dym razem, gdy po�aczenie jest wymagane. 
        Nie oferuje puli po��cze�. */
        DriverManagerDataSource ds = new DriverManagerDataSource();
        // ustawia nazw� sterownika jdbc 
        ds.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        // ustawia adres url dla po��czenia przez sterownik 
        ds.setUrl("jdbc:mysql://localhost:3306/spittr");
        // ustawia nazw� u�ytkownika 
        ds.setUsername("root");
        // ustawia has�o 
        ds.setPassword("haslo1234");
        return ds; 
    }
    
    /* JdbcTemplate jest klas� szablonow�, dzi�ki kt�rej nie trzeba pisa� powielonego 
    kodu np. obs�ugi wyj�tk�w czy zamykanie po��czenia. Ta klasa szablonowa jest 
    najbardziej podstawowym szablonem, kt�ry zapewnia prosty dost�p do bazy danych i 
    zapytania z indeksowanymi parametrami. Opr�cz tej klasy istniej� te�: 
    NamedParameterJdbcTemplate oraz SimpleJdbcTemplate. */
    /*@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        // jdbctemplate potrzebuje do dzia�ania �r�d�a danych 
        return new JdbcTemplate(dataSource); 
    }*/
    
    /* Ten szablon umo�liwia u�ywanie parametr�w nazwanych. Oznacza to �e nie 
    trzeba pami�ta� kolejno�ci parametr�w tak jak w zwyk�ym JdbcTemplate podczas
    odwo�ywania si� za pomoc� ?, tylko mo�na odwo�a� si� do nich za pomoc� nazwy. */
    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}

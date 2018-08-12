package spittr.config;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
    
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
}

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
}

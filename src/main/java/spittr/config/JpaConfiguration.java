package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/* Definiowanie w�asnej obs�ugi JPA jest mo�liwe bez dodatkowej konfiguracji. Je�li jednak 
chcemy skorzysta� z gotowych metod z JpaRepository nale�y uruchomi� funkcj� Spring
Data JPA. Aby to uruchomi� nale�y opatrzy� konfiguracj� adnotacj� EnableJpaRepositories */
@Configuration
@EnableJpaRepositories(basePackages="spittr.data")
public class JpaConfiguration {
    
}

package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/* Definiowanie w³asnej obs³ugi JPA jest mo¿liwe bez dodatkowej konfiguracji. Jeœli jednak 
chcemy skorzystaæ z gotowych metod z JpaRepository nale¿y uruchomiæ funkcjê Spring
Data JPA. Aby to uruchomiæ nale¿y opatrzyæ konfiguracjê adnotacj¹ EnableJpaRepositories */
@Configuration
@EnableJpaRepositories(basePackages="spittr.data")
public class JpaConfiguration {
    
}

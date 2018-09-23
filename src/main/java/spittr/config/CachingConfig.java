package spittr.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/* Odblokowuje mechanizm cachowania, czyli np. przechowywania pobranych wczeœniej
danych co zapobiega ponownemu pobieraniu tych samych danych. */
@EnableCaching
public class CachingConfig {
    @Bean
    /* CacheManager to komponent mened¿era pamiêci podrêcznej, który umo¿liwia 
    integracjê z jedn¹ z kilku popularnych implementacji cachowania. */
    public CacheManager cacheManager() {
        /* Prosty mechanizm pamiêci podrêcznej, wykorzystuj¹cy do przechowywania
        danych mapê ConcurrentHashMap. Ze wzglêdu na prostotê przydaje siê na czas 
        tworzenia aplikacji lub testowania, ale poniewa¿ cachowanie danych przebiega
        w pamiêci i przez to jest powi¹zania z cyklem ¿ycia aplikacji - nie jest
        najlepszym wyborem dla du¿ych aplikacji produkcyjnych. Jako parametr podano
        nazwê pamiêci podrêcznej. */
        return new ConcurrentMapCacheManager("spitterMapCache");
    }
}

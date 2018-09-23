package spittr.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/* Odblokowuje mechanizm cachowania, czyli np. przechowywania pobranych wcze�niej
danych co zapobiega ponownemu pobieraniu tych samych danych. */
@EnableCaching
public class CachingConfig {
    @Bean
    /* CacheManager to komponent mened�era pami�ci podr�cznej, kt�ry umo�liwia 
    integracj� z jedn� z kilku popularnych implementacji cachowania. */
    public CacheManager cacheManager() {
        /* Prosty mechanizm pami�ci podr�cznej, wykorzystuj�cy do przechowywania
        danych map� ConcurrentHashMap. Ze wzgl�du na prostot� przydaje si� na czas 
        tworzenia aplikacji lub testowania, ale poniewa� cachowanie danych przebiega
        w pami�ci i przez to jest powi�zania z cyklem �ycia aplikacji - nie jest
        najlepszym wyborem dla du�ych aplikacji produkcyjnych. Jako parametr podano
        nazw� pami�ci podr�cznej. */
        return new ConcurrentMapCacheManager("spitterMapCache");
    }
}

package spittr.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/* Klasa roszerzaj¹ca interfejs SecurityWebApplicationInitializer umo¿liwia 
   konfiguracjê filtra DelegatingFilterProxy. Jest to specjalny filtr serwletów 
   który deleguje obs³ugê filtrów do odpowiedniego komponentu filtra w kontekœcie 
   aplikacji Spring. Tutaj rozszerzono klasê AbstractSecurityWebApplicationInitializer 
   która implementuje potrzebny interfejs. */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
    
}

package spittr.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/* Klasa roszerzaj�ca interfejs SecurityWebApplicationInitializer umo�liwia 
   konfiguracj� filtra DelegatingFilterProxy. Jest to specjalny filtr serwlet�w 
   kt�ry deleguje obs�ug� filtr�w do odpowiedniego komponentu filtra w kontek�cie 
   aplikacji Spring. Tutaj rozszerzono klas� AbstractSecurityWebApplicationInitializer 
   kt�ra implementuje potrzebny interfejs. */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
    
}

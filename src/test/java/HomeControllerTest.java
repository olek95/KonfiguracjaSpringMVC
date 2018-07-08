import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import org.springframework.web.servlet.view.InternalResourceView;
import spittr.Spitter;
import spittr.Spittle;
import spittr.data.SpitterRepository;
import spittr.data.SpittleRepository;
import spittr.web.HomeController;
import spittr.web.SpitterController;
import spittr.web.SpittleController;

public class HomeControllerTest {
    //@Test
    public void testHomePage() throws Exception {
        HomeController controller = new HomeController();
        //assertEquals("home", controller.home()); // ten test sprawdza jedynie zwracany tekst
        /* Tworzy instancje atrapy MockMvc, kt�ra jest atrap� mechanizm�w Spring 
        MVC umo�liwiaj�c� wywo�anie �ada� http na kontrolerach */
        MockMvc mockMvc = standaloneSetup(controller).build();
        // wywo�uje dane ��danie do zasobu i weryfikuje nazw� zwr�conego widoku 
        mockMvc.perform(get("")).andExpect(view().name("home"));
    }
    
    //@Test
    public void shouldShowRecentSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittleList(20); 
        // tworzy atrap� repozytorium 
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        /* when jest u�ywany gdy chce si� aby zwr�cono pewn� warto�� gdy wywo�ywana 
        jest pewna metoda. Natomiast thenReturn ustawia pewn� warto�� kt�ra jest 
        zwracana w chwili wywo�ania metody */
        when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles); 
        SpittleController controller = new SpittleController(mockRepository);
        /* setSingleView powoduje �e framework obs�uguj�cy mechanizm atrap nie b�dzie 
        pr�bowa� samodzielnie rozwi�zywa� nawy widoku zwr�conej z kontrolera. 
        Domy�lne dzia�anie spowoduje wyst�pienie b��du, bo �cie�ka do widoku b�dzie 
        mylona ze �cie�k� do kontrolera. Rzeczywista �cie�ka powsta�a przy konstruowaniu 
        InternalResourceView nie jest w tym te�cie istotna, jednak ustawiono j�, 
        aby zachowa� sp�jno�� z konfiguracj� producenta widok�w InternalResourceViewResolver. 
        */
        MockMvc mockMvc = standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
        /* get w perform pobiera list� spittl�w, natomiast pierwszy andEpxect sprawdza nazw�
        widoku, drugi czy model posiada atrybut o nazwie spittleList, a ostatni andExpect 
        weryfikuje wyniki */
        mockMvc.perform(get("/spittles")).andExpect(view().name("spittles")).andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));
    }
    
    //@Test 
    public void shouldShowPagedSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittleList(50); 
        SpittleRepository mockRepository = mock(SpittleRepository.class); 
        when(mockRepository.findSpittles(238900, 50)).thenReturn(expectedSpittles);
        SpittleController controller = new SpittleController(mockRepository); 
        MockMvc mockMvc = standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
        // przekazanie parametr�w max i count zapytania 
        mockMvc.perform(get("/spittles?max=238900&count=50")).andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList")).andExpect(model().attribute("spittleList",
                        hasItems(expectedSpittles.toArray())));   
    }
    
    //@Test
    public void testSpittle() throws Exception {
        Spittle expectedSpittle = new Spittle("Hello", new Date());
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);
        SpittleController controller = new SpittleController(mockRepository); 
        MockMvc mockMvc = standaloneSetup(controller).build(); 
        // parametr �cie�ki. Nale�y u�ywa� gdy zapytania dotycz� zasob�w.
        mockMvc.perform(get("/spittles/12345")).andExpect(view().name("spittle"))
                .andExpect(model().attributeExists("spittle")).andExpect(model().attribute("spittle", expectedSpittle));
    }
    
    // @Test 
    public void shouldShowRegistration() throws Exception {
        SpitterController controller = new SpitterController(); 
        MockMvc mockMvc = standaloneSetup(controller).build(); 
        mockMvc.perform(get("/spitter/register")).andExpect(view().name("registerForm"));
    }
    
    @Test 
    public void shouldProcessRegistration() throws Exception {
        /*SpitterRepository mockRepository = mock(SpitterRepository.class);
        Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "jakisMail@poczta.onet.pl");
        Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "jakisMail@poczta.onet.pl");
        when(mockRepository.save(unsaved)).thenReturn(saved);
        SpitterController controller = new SpitterController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();*/
        /* w ten spos�b mo�na wywo�a� ��danie formularza i wype�ni� jego parametry.
        Metoda redirectedUrl zak�ada przekierowane na podany url po wykonaniu POST */
        //mockMvc.perform(post("/spitter/register").param("firstName", "Jack").param("lastName", "Bauer")
        //        .param("username", "jbauer").param("password", "24hours").param("email", "jakisMail@poczta.onet.pl")).andExpect(redirectedUrl("/spitter/jbauer"));
        // sprawdza czy dosz�o do zapisu, atLeastOnce jest trybem sprawdzaj�cym czy co� wykona�o sie chocia� raz
        //verify(mockRepository, atLeastOnce()).save(unsaved);
    }
    
    private List<Spittle> createSpittleList(int count) {
        List<Spittle> spittles = new ArrayList<Spittle>(); 
        for (int i = 0; i < count; i++) {
            spittles.add(new Spittle("Spittle " + i, new Date())); 
        }
        return spittles; 
    }
}

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
        /* Tworzy instancje atrapy MockMvc, która jest atrap¹ mechanizmów Spring 
        MVC umo¿liwiaj¹c¹ wywo³anie ¿adañ http na kontrolerach */
        MockMvc mockMvc = standaloneSetup(controller).build();
        // wywo³uje dane ¿¹danie do zasobu i weryfikuje nazwê zwróconego widoku 
        mockMvc.perform(get("")).andExpect(view().name("home"));
    }
    
    //@Test
    public void shouldShowRecentSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittleList(20); 
        // tworzy atrapê repozytorium 
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        /* when jest u¿ywany gdy chce siê aby zwrócono pewn¹ wartoœæ gdy wywo³ywana 
        jest pewna metoda. Natomiast thenReturn ustawia pewn¹ wartoœæ która jest 
        zwracana w chwili wywo³ania metody */
        when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles); 
        SpittleController controller = new SpittleController(mockRepository);
        /* setSingleView powoduje ¿e framework obs³uguj¹cy mechanizm atrap nie bêdzie 
        próbowa³ samodzielnie rozwi¹zywaæ nawy widoku zwróconej z kontrolera. 
        Domyœlne dzia³anie spowoduje wyst¹pienie b³êdu, bo œcie¿ka do widoku bêdzie 
        mylona ze œcie¿k¹ do kontrolera. Rzeczywista œcie¿ka powsta³a przy konstruowaniu 
        InternalResourceView nie jest w tym teœcie istotna, jednak ustawiono j¹, 
        aby zachowaæ spójnoœæ z konfiguracj¹ producenta widoków InternalResourceViewResolver. 
        */
        MockMvc mockMvc = standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
        /* get w perform pobiera listê spittlów, natomiast pierwszy andEpxect sprawdza nazwê
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
        // przekazanie parametrów max i count zapytania 
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
        // parametr œcie¿ki. Nale¿y u¿ywaæ gdy zapytania dotycz¹ zasobów.
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
        /* w ten sposób mo¿na wywo³aæ ¿¹danie formularza i wype³niæ jego parametry.
        Metoda redirectedUrl zak³ada przekierowane na podany url po wykonaniu POST */
        //mockMvc.perform(post("/spitter/register").param("firstName", "Jack").param("lastName", "Bauer")
        //        .param("username", "jbauer").param("password", "24hours").param("email", "jakisMail@poczta.onet.pl")).andExpect(redirectedUrl("/spitter/jbauer"));
        // sprawdza czy dosz³o do zapisu, atLeastOnce jest trybem sprawdzaj¹cym czy coœ wykona³o sie chocia¿ raz
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

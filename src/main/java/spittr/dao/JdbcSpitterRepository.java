package spittr.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;
import spittr.domain.Spitter;
import spittr.data.SpitterRepository;

/* Interfejsy reprezentuj�ce repozytoria musz� mie� swoj� implementacj� oraz by� 
oznaczonymi adnotacj� Repository, poniewa� w przeciwnym przypadku pojawi si� wyj�tek 
m�wi�cy o braku zale�no�ci (dependency) dla danego interfejsu. */
@Repository
public class JdbcSpitterRepository implements SpitterRepository {
    /* Aby odwo�a� si� do parametr�w indeksowanych nale�y zastosowa� ?. Kolejno��
    jest istotna. */
    /* private static final String SQL_INSERT_SPITTER = "insert into spitter (username, password, first_name, last_name, email) values (?,?,?,?,?)";
    private static final String SQL_SELECT_SPITTER = "select * from spitter where username = ?";
    private JdbcOperations jdbcOperations;
    
    @Autowired
    public JdbcSpitterRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    } */
    
    /* Aby odwo�a� si� do parametr�w nazwanych nale�y zastosowa� symbol :. 
    Kolejno�� nie jest istotna. */
    private static final String SQL_INSERT_SPITTER = "insert into spitter (username, password, first_name, last_name, email) values (:username,:password,:first_name,:last_name,:email)";
    private static final String SQL_SELECT_SPITTER = "select * from spitter where username = :username";
    private NamedParameterJdbcOperations jdbcOperations;
    
    @Autowired
    public JdbcSpitterRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }
    
    @Override
    public Spitter save(Spitter spitter) {
        /* Wykonuje pojedyncze polecenie sql, kt�re aktualizuje baz� (update, delete 
        lub insert) z wykorzystaniem tzw. PreparedStatement. Kolejne parametry to 
        warto�ci kt�re b�d� podstawiane (wi�zane) pod znak ? w zapytaniu. Metoda ta
        zawiera powtarzaj�ce si� fragmenty kodu np. tworzenie po��czenia, obs�uga wyj�tk�w, 
        wiec nie trzeba tego kodu powtarza�. */
        /* jdbcOperations.update(SQL_INSERT_SPITTER, spitter.getUsername(), 
                new StandardPasswordEncoder("53Kr3t").encode(spitter.getPassword()),
                spitter.getFirstName(), spitter.getLastName(), spitter.getEmail()); */
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("username", spitter.getUsername());
        paramMap.put("password", new StandardPasswordEncoder("53Kr3t").encode(spitter.getPassword()));
        paramMap.put("first_name", spitter.getFirstName());
        paramMap.put("last_name", spitter.getLastName());
        paramMap.put("email", spitter.getEmail());
        /* Metody dost�pu do bazy danych dla szablonu parametr�w nazwanych wygl�daj�
        podobnie do ich odpowiednik�w dla parametr�w indeksowych. R�nica taka, ze
        ta wersja przyjmuje parametry w postaci mapy, natomiast poprzednia - w postaci
        dowolnej liczby paramet�w, przez co ta wersja zajmuje nieco wi�cej kodu. */
        jdbcOperations.update(SQL_INSERT_SPITTER, paramMap);
        return spitter; 
    }
    
    @Override
    public Spitter findByUsername(String username) {
        /* Wysy�a do bazy zapytanie zwracaj�ce dane (SELECT). Pierwszy parametr to 
        �a�cuch znak�w z poleceniem pobieraj�cym dane, drugi parametr to RowMapper 
        wydobywaj�cy warto�ci z ResultSet, natomiast ostatni to zmienna lista argument�w
        podstawianych pod znak ?. */
        //return jdbcOperations.queryForObject(SQL_SELECT_SPITTER, new SpitterRowMapper(), username);
        /* Interfejs RowMapper deklaruje tylko jedn� metod� wi�c jest interfejsem 
        funkcyjnym, a wi�c w Javie 8 mo�na zamiast implementacji klasy zapisa� t�
        metod� w postaci lambdy */
        /*return jdbcOperations.queryForObject(SQL_SELECT_SPITTER, (rs, rowNum) -> {
            return new Spitter(rs.getLong("id"), rs.getString("username"), rs.getString("password"), 
                    rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
        }, username);*/
        /* W Javie 8 mo�na tak�e u�y� odwo�ania do metody i zdefiniowa� mapowanie 
        w osobnej metodzie. */
        //return jdbcOperations.queryForObject(SQL_SELECT_SPITTER, this::mapSpitter, username);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("username", username);
        return jdbcOperations.queryForObject(SQL_SELECT_SPITTER, paramMap, this::mapSpitter);
    }
    
    private static final class SpitterRowMapper implements RowMapper<Spitter> {
        
        /* Wykonuje si� dla ka�dego zwr�conego wiersza wyniku zapytania SELECT. 
        Jako parametr przekazuje obiekt ResultSet oraz indeks wiersza. */
        @Override
        public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Spitter(rs.getLong("id"), rs.getString("username"), rs.getString("password"), 
                    rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
        }
    }
    
    /* Aby m�c si� odwo�a� do tej metody w queryForObject musi ona posiada� te same 
    parametry i zwraca� ten sam typ co mapRow */
    private Spitter mapSpitter(ResultSet rs, int row) throws SQLException {
        return new Spitter(rs.getLong("id"), rs.getString("username"), rs.getString("password"), 
                    rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
    }
}

package spittr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;
import spittr.Spitter;
import spittr.data.SpitterRepository;

/* Interfejsy reprezentuj¹ce repozytoria musz¹ mieæ swoj¹ implementacjê oraz byæ 
oznaczonymi adnotacj¹ Repository, poniewa¿ w przeciwnym przypadku pojawi siê wyj¹tek 
mówi¹cy o braku zale¿noœci (dependency) dla danego interfejsu. */
@Repository
public class JdbcSpitterRepository implements SpitterRepository {
    private static final String SQL_INSERT_SPITTER = "insert into spitter (username, password, first_name, last_name, email) values (?,?,?,?,?)";
    private static final String SQL_SELECT_SPITTER = "select * from spitter where username = ?";
    private JdbcOperations jdbcOperations;
    
    @Autowired
    public JdbcSpitterRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }
    
    @Override
    public Spitter save(Spitter spitter) {
        /* Wykonuje pojedyncze polecenie sql, które aktualizuje bazê (update, delete 
        lub insert) z wykorzystaniem tzw. PreparedStatement. Kolejne parametry to 
        wartoœci które bêd¹ podstawiane (wi¹zane) pod znak ? w zapytaniu. Metoda ta
        zawiera powtarzaj¹ce siê fragmenty kodu np. tworzenie po³¹czenia, obs³uga wyj¹tków, 
        wiec nie trzeba tego kodu powtarzaæ. */
        jdbcOperations.update(SQL_INSERT_SPITTER, spitter.getUsername(), 
                new StandardPasswordEncoder("53Kr3t").encode(spitter.getPassword()),
                spitter.getFirstName(), spitter.getLastName(), spitter.getEmail());
        return spitter; 
    }
    
    @Override
    public Spitter findByUsername(String username) {
        /* Wysy³a do bazy zapytanie zwracaj¹ce dane (SELECT). Pierwszy parametr to 
        ³añcuch znaków z poleceniem pobieraj¹cym dane, drugi parametr to RowMapper 
        wydobywaj¹cy wartoœci z ResultSet, natomiast ostatni to zmienna lista argumentów
        podstawianych pod znak ?. */
        //return jdbcOperations.queryForObject(SQL_SELECT_SPITTER, new SpitterRowMapper(), username);
        /* Interfejs RowMapper deklaruje tylko jedn¹ metodê wiêc jest interfejsem 
        funkcyjnym, a wiêc w Javie 8 mo¿na zamiast implementacji klasy zapisaæ t¹
        metodê w postaci lambdy */
        /*return jdbcOperations.queryForObject(SQL_SELECT_SPITTER, (rs, rowNum) -> {
            return new Spitter(rs.getLong("id"), rs.getString("username"), rs.getString("password"), 
                    rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
        }, username);*/
        /* W Javie 8 mo¿na tak¿e u¿yæ odwo³ania do metody i zdefiniowaæ mapowanie 
        w osobnej metodzie. */
        return jdbcOperations.queryForObject(SQL_SELECT_SPITTER, this::mapSpitter, username);
    }
    
    private static final class SpitterRowMapper implements RowMapper<Spitter> {
        
        /* Wykonuje siê dla ka¿dego zwróconego wiersza wyniku zapytania SELECT. 
        Jako parametr przekazuje obiekt ResultSet oraz indeks wiersza. */
        @Override
        public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Spitter(rs.getLong("id"), rs.getString("username"), rs.getString("password"), 
                    rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
        }
    }
    
    /* Aby móc siê odwo³aæ do tej metody w queryForObject musi ona posiadaæ te same 
    parametry i zwracaæ ten sam typ co mapRow */
    private Spitter mapSpitter(ResultSet rs, int row) throws SQLException {
        return new Spitter(rs.getLong("id"), rs.getString("username"), rs.getString("password"), 
                    rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
    }
}

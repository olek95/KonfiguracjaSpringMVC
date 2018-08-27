package spittr.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/* Klasy oznaczone Entity bior� udzia� w skanowaniu w poszukiwaniu encji przez 
Hibernate. Jako parametr przyjmuje nazw� tabeli, domy�lnie jest to nazwa klasy. */
@Entity
public class Spitter implements Serializable {
    // Okre�la kt�ry atrybut wyst�puje jako klucz g��wny tabeli 
    @Id
    /* Okre�la strategi� generowania kluczy g��wnych. Typ generowania IDENTITY 
    oznacza �e klucze s� generowane na podstawie kolumny */ 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* Mapuje nazw� w�a�ciwo�ci z odpowiedni� kolumn�. Name to nazwa kolumny, 
    domy�lnie nazwa pola klasy. Unique m�wi czy kolumna jest unikalnym kluczem. 
    Nullable natomiast okre�la czy kolumna pozwala na puste warto�ci. */
    @Column(name = "id",unique=true, nullable = false)
    private final Long id;
    /* sprawdza czy pole jest nie puste i jego d�ugo�� jest z zakresu od 2 do 30. 
    Size mo�na u�y� dla string�w, kolekcji b�d� tablicy */
    @NotEmpty
    /* message jest to klucz z pliku properties z kt�rego ma zosta� pobrany komunikat 
    b��du. Je�li pominie si� klamry, zostanie wy�wietlone firstName.size */ 
    @Size(min=2, max=30, message="{firstName.size}")
    @Column(name="first_name")
    private String firstName;
    @NotEmpty
    @Size(min=2, max=30, message="{lastName.size}")
    @Column(name="last_name")
    private String lastName; 
    @NotEmpty
    @Size(min=5, max=16, message="{username.size}")
    @Column
    private String username; 
    @NotEmpty
    @Size(min=5, max=25, message="{password.size}")
    @Column
    private String password; 
    @NotEmpty
    // sprawdza czy adres email ma poprawny format 
    @Email(message="{email.valid}")
    @Column
    private String email;
    
    public Spitter(){
        this.id = null;
    }
    
    public Spitter(Long id, String username, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username; 
        this.password = password;
        this.firstName = firstName; 
        this.lastName = lastName; 
        this.email = email;
    }
    
    public Spitter(String username, String password, String firstName, String lastName, String email) {
        this(null, username, password, firstName, lastName, email);
    }
    
    public String getFirstName() {
        return firstName; 
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName; 
    }
    
    public String getLastName() {
        return lastName; 
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName; 
    }
    
    public String getUsername() {
        return username; 
    }
    
    public void setUsername(String username) {
        this.username = username; 
    }
    
    public String getPassword() {
        return password; 
    }
    
    public void setPassword(String password) {
        this.password = password; 
    }
    
    public String getEmail() {
        return email; 
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

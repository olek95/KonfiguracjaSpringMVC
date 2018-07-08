package spittr;

import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Spitter {
    private final Long id;
    /* sprawdza czy pole jest nie puste i jego d³ugoœæ jest z zakresu od 2 do 30. 
    Size mo¿na u¿yæ dla stringów, kolekcji b¹dŸ tablicy */
    @NotEmpty
    /* message jest to klucz z pliku properties z którego ma zostaæ pobrany komunikat 
    b³êdu. Jeœli pominie siê klamry, zostanie wyœwietlone firstName.size */ 
    @Size(min=2, max=30, message="{firstName.size}")
    private String firstName;
    @NotEmpty
    @Size(min=2, max=30, message="{lastName.size}")
    private String lastName; 
    @NotEmpty
    @Size(min=5, max=16, message="{username.size}")
    private String username; 
    @NotEmpty
    @Size(min=5, max=25, message="{password.size}")
    private String password; 
    @NotEmpty
    // sprawdza czy adres email ma poprawny format 
    @Email(message="{email.valid}")
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

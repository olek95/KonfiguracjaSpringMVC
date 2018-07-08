package spittr;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Spittle {
    private final Long id; 
    private final String message;
    private final Date time; 
    private Double latitude; 
    private Double longitude; 
    
    public Spittle(String message, Date time) {
        this(message, time, null, null); 
    }
    
    // Dodane w celu zmockowania Spittl�w w SpittleRepositoryDAO
    public Spittle(Long id, String message, Date time) {
        this.id = id; 
        this.message = message; 
        this.time = time; 
    }
    
    public Spittle(String message, Date time, Double longitude, Double latitude) {
        this.id = null; 
        this.message = message; 
        this.time = time; 
        this.longitude = longitude; 
        this.latitude = latitude; 
    }
    
    public long getId() {
        return id; 
    }
    
    public String getMessage() {
        return message; 
    }
    
    public Date getTime() {
        return time; 
    }
    
    public Double getLongitude() {
        return longitude; 
    }
    
    public Double getLatitude() {
        return latitude; 
    }
    
    @Override 
    public boolean equals(Object that) {
        /* U�ywa refleksji do sprawdzenia czy obiekty s� r�wne. Poniewa� cz�sto 
        takie pola s� prywatne - zmienia ich dost�powo��. Na ko�cu s� wypisane 
        pola kt�re s� wy��czone ze sprawdzania. */
        return EqualsBuilder.reflectionEquals(this, that, "id", "time");
    }
    
    @Override 
    public int hashCode() {
        /* U�ywa refleksji do wyliczenia poprawnego kodu haszuj�cego. Na ko�cu 
        znajduj� si� pola wy��czone z oblicze�. */
        return HashCodeBuilder.reflectionHashCode(this, "id", "time");
    }
}

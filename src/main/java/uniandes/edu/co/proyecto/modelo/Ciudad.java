package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ciudad")
public class Ciudad {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer codigo;

    private String nombre;
    

    public Ciudad(String nombre)
    {
        this.nombre = nombre; 
    }

    public Ciudad()
    {;}

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    


}
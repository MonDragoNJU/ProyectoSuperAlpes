package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Clase que mapea la tabla Categoria en la base de datos
@Entity
@Table(name="categoria")
public class Categoria {

    @Id //Indica que es la llave primaria
    @GeneratedValue(strategy = GenerationType.AUTO) //Genera automaticamente el valor de la llave primaria
    
    //Atributos de la clase
    private Integer codigo;
    private String nombre;
    private String descripcion;
    private String caracteristicasAlmacenamiento;

    public Categoria(String nombre, String descripcion, String caracteristicasAlmacenamiento){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.caracteristicasAlmacenamiento = caracteristicasAlmacenamiento;
    }

    public Categoria()
    {;}

    public Integer getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCaracteristicasAlmacenamiento() {
        return caracteristicasAlmacenamiento;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCaracteristicasAlmacenamiento(String caracteristicasAlmacenamiento) {
        this.caracteristicasAlmacenamiento = caracteristicasAlmacenamiento;
    }

    

}

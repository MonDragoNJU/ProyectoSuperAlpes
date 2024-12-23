package uniandes.edu.co.proyecto.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


//Clase que mapea la tabla Bodega en la base de datos
@Entity
@Table(name="bodega")
public class Bodega {
    @Id //Indica que es la llave primaria
    @GeneratedValue(strategy=GenerationType.AUTO) //Genera automaticamente el valor de la llave primaria

    //Atributos de la clase
    private Integer id;
    private String nombre;
    private Double tamanio;
    private Integer capacidad;

    //Relacion con la tabla Sucursal
    @ManyToOne
    @JoinColumn(name="id_sucursal", referencedColumnName="id")
    private Sucursal idSucursal;

    public Bodega(String nombre, Double tamanio, Integer capacidad, Sucursal idSucursal){
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.capacidad = capacidad;
        this.idSucursal = idSucursal;
    }

    public Bodega(){
    ;}

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getTamanio() {
        return tamanio;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public Sucursal getIdSucursal() {
        return idSucursal;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTamanio(Double tamanio) {
        this.tamanio = tamanio;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public void setIdSucursal(Sucursal idSucursal) {
        this.idSucursal = idSucursal;
    }

    

}

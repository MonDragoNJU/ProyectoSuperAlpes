package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//Clase que mapea la tabla OrdenDeCompra en la base de datos
@Entity
@Table(name="orden_de_compra")
public class OrdenDeCompra {
    
    @Id //Indica que es la llave primaria
    @GeneratedValue(strategy=GenerationType.AUTO) //Genera automaticamente el valor de la llave primaria
    private Integer numero;

    //Atributos de la clase
    private LocalDate fechaEntrega;
    private String estado;
    private LocalDate fechaCreacion;


    //Relacion con la tabla Sucursal
    @ManyToOne
    @JoinColumn(name="id_sucursal", referencedColumnName="id")
    private Sucursal idSucursal;

    //Relacion con la tabla Proveedor
    @ManyToOne
    @JoinColumn(name="nit_proveedor", referencedColumnName="nit")
    private Proveedor nitProveedor;

    public OrdenDeCompra(LocalDate fechaEntrega, String estado, LocalDate fechaCreacion, Sucursal idSucursal, Proveedor nitProveedor){
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.idSucursal = idSucursal;
        this.nitProveedor = nitProveedor;
    }


    public OrdenDeCompra(){
    ;}

    public Integer getNumero() {
        return numero;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Sucursal getIdSucursal() {
        return idSucursal;
    }

    public Proveedor getNitProveedor() {
        return nitProveedor;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setIdSucursal(Sucursal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setNitProveedor(Proveedor nitProveedor) {
        this.nitProveedor = nitProveedor;
    }


    

}

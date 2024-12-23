package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//Clase que mapea la tabla ProductoEnBodega en la base de datos
@Entity
@Table(name="producto_en_bodega")
public class ProductoEnBodega {

    //Llave primaria compuesta
    @EmbeddedId //Indica que es una llave primaria compuesta
    private ProductoEnBodegaPK pk;

    //Atributos de la clase
    private Integer nivelMinimoReorden;
    private Double costoPromedio;
    private Integer capacidadAlmacenarProducto;
    private Integer cantidadEnBodega;

    

    public ProductoEnBodega(Producto idProducto, Bodega idBodega,Integer nivelMinimoReorden, Double costoPromedio,
                             Integer capacidadAlmacenarProducto, Integer cantidadEnBodega) 
    {
        this.pk = new ProductoEnBodegaPK(idProducto, idBodega);
        this.nivelMinimoReorden = nivelMinimoReorden;
        this.costoPromedio = costoPromedio;
        this.capacidadAlmacenarProducto = capacidadAlmacenarProducto;
        this.cantidadEnBodega = cantidadEnBodega;
    }

    public ProductoEnBodega()
    {;}

    public ProductoEnBodegaPK getPk() {
        return pk;
    }

    public void setPk(ProductoEnBodegaPK pk) {
        this.pk = pk;
    }

    public Integer getNivelMinimoReorden() {
        return nivelMinimoReorden;
    }

    public void setNivelMinimoReorden(Integer nivelMinimoReorden) {
        this.nivelMinimoReorden = nivelMinimoReorden;
    }

    public Double getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(Double costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public Integer getCapacidadAlmacenarProducto() {
        return capacidadAlmacenarProducto;
    }

    public void setCapacidadAlmacenarProducto(Integer capacidadAlmacenarProducto) {
        this.capacidadAlmacenarProducto = capacidadAlmacenarProducto;
    }

    public Integer getCantidadEnBodega() {
        return cantidadEnBodega;
    }

    public void setCantidadEnBodega(Integer cantidadEnBodega) {
        this.cantidadEnBodega = cantidadEnBodega;
    }
    
    
    
    
}

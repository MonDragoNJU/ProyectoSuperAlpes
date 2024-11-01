package uniandes.edu.co.proyecto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.ProductoEnBodega;
import uniandes.edu.co.proyecto.modelo.ProductoEnBodegaPK;

public interface ProductoEnBodegaRepository extends JpaRepository<ProductoEnBodega, ProductoEnBodegaPK> {

    // Consulta nativa para obtener un producto específico en una bodega específica
    @Query(value = "SELECT * FROM PRODUCTO_EN_BODEGA WHERE IDENTIFICADOR_PRODUCTO = :idProducto AND ID_BODEGA = :idBodega", nativeQuery = true)
    ProductoEnBodega findByProductoYBodega(@Param("idProducto") Integer idProducto, @Param("idBodega") Integer idBodega);

    //NEW NEW NEW 
    //Hacer calculo del costo promedio con query SQL
    @Modifying
    @Transactional
    @Query(value = "UPDATE PRODUCTO_EN_BODEGA \r\n" +
                   "SET CANTIDAD_EN_BODEGA = CANTIDAD_EN_BODEGA + :cantidadIngresada, \r\n" +
                   "COSTO_PROMEDIO = ((COSTO_PROMEDIO * CANTIDAD_EN_BODEGA) + (:precioUnitario * :cantidadIngresada)) / (CANTIDAD_EN_BODEGA + :cantidadIngresada) " +
                   "WHERE IDENTIFICADOR_PRODUCTO = :idProducto AND ID_BODEGA = :idBodega", nativeQuery = true)
    void actualizarCostoPromedioyCantidad(@Param("idProducto") Integer idProducto, @Param("idBodega") Integer idBodega, @Param("precioUnitario") Double precioUnitario, @Param("cantidadIngresada") Integer cantidadIngresada);
}


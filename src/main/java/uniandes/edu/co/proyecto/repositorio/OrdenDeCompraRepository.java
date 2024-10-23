package uniandes.edu.co.proyecto.repositorio;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.OrdenDeCompra;

//Repositorio de la entidad OrdenDeCompra que se encarga de realizar las consultas a la base de datos
public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Integer> {

    //Consulta CRUD de darOrdenesDeCompra
    @Query(value = "SELECT * FROM orden_de_compra", nativeQuery=true)
    Collection<OrdenDeCompra> darOrdenesDeCompra();

    //Consulta CRUD de darOrdenDeCompra
    @Query(value = "SELECT * FROM orden_de_compra WHERE numero= :numero", nativeQuery=true)
    OrdenDeCompra darOrdenDeCompra(@Param("numero") Integer numero);

    //Consulta CRUD de insertarOrdenDeCompra
    @Modifying //Indica que se va a realizar una modificacion en la base de datos
    @Transactional //Indica que es una transaccion
    @Query(value = "INSERT INTO orden_de_compra(numero, fecha_entrega, estado, fecha_creacion, id_sucursal, nit_proveedor) VALUES(secuencia_orden_de_compra.nextval, :fecha_entrega, :estado, :fecha_creacion, :id_sucursal, :nit_proveedor)", nativeQuery = true)
    void insertarOrdenDeCompra(@Param("fecha_entrega") LocalDate fechaEntrega, @Param("estado") String estado, @Param("fecha_creacion") LocalDate fechaCreacion, @Param("id_sucursal") Integer idSucursal, @Param("nit_proveedor") String nitProveedor);

    //Consulta para actualizar el estado de una orden de compra
    @Modifying //Indica que se va a realizar una modificacion en la base de datos
    @Transactional //Indica que es una transaccion
    @Query(value = "UPDATE orden_de_compra SET estado = :estado WHERE numero = :numero", nativeQuery = true)
    void actualizarEstadoOrdenDeCompra(@Param("numero") Integer numero, @Param("estado") String estado);

    // NUEVO!!! Consultar el estado de una orden de compra
    @Query(value = "SELECT estado FROM orden_de_compra WHERE numero = :numero", nativeQuery = true)
    String obtenerEstadoOrdenCompra(@Param("numero") Long numero);
    
}

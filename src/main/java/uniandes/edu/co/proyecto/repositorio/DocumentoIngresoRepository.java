package uniandes.edu.co.proyecto.repositorio;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.QueryHint;
import uniandes.edu.co.proyecto.modelo.DocumentoIngreso;

@Repository
public interface DocumentoIngresoRepository extends JpaRepository<DocumentoIngreso, Long> {

    //Consulta CRUD de darDocumentos
    @Query(value = "SELECT * FROM documento_ingreso", nativeQuery = true)
    Collection<DocumentoIngreso> obtenerTodosLosDocumentos();

    //Consulta CRUD de darDocumento
    @Query(value = "SELECT * FROM documento_ingreso WHERE id = :id", nativeQuery = true)
    DocumentoIngreso obtenerDocumentoPorId(@Param("id") Long id);

    //Consulta CRUD de insertarDocumentoIngreso
    @Modifying //Indica que se va a realizar una modificacion en la base de datos
    @Transactional //Indica que es una transaccion 
    @Query(value = "INSERT INTO documento_ingreso(id, fecha_ingreso, id_bodega, numero_orden_de_compra) VALUES (secuencia_documento_ingreso.nextval, :fechaIngreso, :idBodega, :numeroOrdenCompra)", nativeQuery = true)
    void insertarDocumentoIngreso(@Param("fechaIngreso") LocalDate fechaIngreso, @Param("idBodega") Long idBodega, @Param("numeroOrdenCompra") Long numeroOrdenCompra);

    //NEW NEW FOR UPDATE
    @Query(value = "SELECT DOCUMENTO_INGRESO.ID AS id_documento, DOCUMENTO_INGRESO.FECHA_INGRESO AS fecha_ingreso, SUCURSAL.NOMBRE AS nombre_sucursal, BODEGA.NOMBRE AS nombre_bodega, PROVEEDOR.NOMBRE AS nombre_proveedor \r\n" +
        "FROM DOCUMENTO_INGRESO \r\n" +
        "JOIN ORDEN_DE_COMPRA ON DOCUMENTO_INGRESO.NUMERO_ORDEN_DE_COMPRA = ORDEN_DE_COMPRA.NUMERO \r\n" +
        "JOIN SUCURSAL ON ORDEN_DE_COMPRA.ID_SUCURSAL = SUCURSAL.ID \r\n" +
        "JOIN BODEGA ON DOCUMENTO_INGRESO.ID_BODEGA = BODEGA.ID \r\n" +
        "JOIN PROVEEDOR ON ORDEN_DE_COMPRA.NIT_PROVEEDOR = PROVEEDOR.NIT \r\n" +
        "WHERE ORDEN_DE_COMPRA.ID_SUCURSAL = :idSucursal AND DOCUMENTO_INGRESO.ID_BODEGA = :idBodega AND DOCUMENTO_INGRESO.FECHA_INGRESO >= :fechaLimite " +
        "ORDER BY DOCUMENTO_INGRESO.FECHA_INGRESO DESC \r\n" +
        "FOR UPDATE", nativeQuery = true)
        //Usamos un map por lo que no solo regresa objetos de tipo DocumentoIngreso, sino otros elementos 
        @QueryHints(value = {@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
        List<Map<String, Object>> obtenerDocumentosIngreso(@Param("idSucursal") Long idSucursal, @Param("idBodega") Long idBodega, @Param("fechaLimite") LocalDate fechaLimite);

}


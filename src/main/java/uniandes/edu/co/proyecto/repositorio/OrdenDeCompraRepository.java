package uniandes.edu.co.proyecto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import uniandes.edu.co.proyecto.modelo.OrdenDeCompra;

public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Integer>{

    @Query(value = "SELECT * FROM ORDEN_DE_COMPRA", nativeQuery = true)
    Collection<OrdenDeCompra> darOrdenesDeCompra();
}

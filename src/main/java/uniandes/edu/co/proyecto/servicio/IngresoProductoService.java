package uniandes.edu.co.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Bodega;
import uniandes.edu.co.proyecto.modelo.OrdenDeCompra;
import uniandes.edu.co.proyecto.modelo.Producto;
import uniandes.edu.co.proyecto.modelo.ProductoPedido;
import uniandes.edu.co.proyecto.repositorio.BodegaRepository;
import uniandes.edu.co.proyecto.repositorio.OrdenDeCompraRepository;
import uniandes.edu.co.proyecto.repositorio.ProductoRepository;

@Service
public class IngresoProductoService {

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepo;

    @Autowired
    private BodegaRepository bodegaRepo;



    @Transactional
    public void registrarIngresoProductos(Integer idOrdenCompra, int bodegaId) {

        OrdenDeCompra ordenDeCompra = ordenDeCompraRepo.darOrdenDeCompra(idOrdenCompra);//Buscar la orden de compra
        
        String estadoOrden = ordenDeCompra.getEstado();//Obtener el estado de la orden de compra
        
        Bodega bodega = bodegaRepo.darBodega(bodegaId); //Obtener la bodega


        //Registrar el ingreso de productos a la bodega

        //Actualizar inventario y costo promedio en ProductoBodega

        // Actualizar cantidad en bodega



        //Verificar las cosas:
        if (estadoOrden == "entregada" && ordenDeCompra != null && !bodega.getIdSucursal().equals(ordenDeCompra.getIdSucursal())) {
            throw new RuntimeException("");
        }
           

        //Cambiar el estado de la orden de compra a ENTREGADA
        ordenDeCompra.setEstado("entregada");
        ordenDeCompraRepo.save(ordenDeCompra);
    }

    /**
     * Calcula el nuevo costo promedio basado en la cantidad y precio unitario ingresados.
    
    private Double calcularNuevoCostoPromedio(Double costoPromedioAnterior, Integer cantidadAnterior,
                                              Double precioUnitario, Integer cantidadIngresada) {
        return ((costoPromedioAnterior * cantidadAnterior) + (precioUnitario * cantidadIngresada))
                / (cantidadAnterior + cantidadIngresada);
    }*/
}

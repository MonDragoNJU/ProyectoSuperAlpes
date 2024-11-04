package uniandes.edu.co.proyecto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uniandes.edu.co.proyecto.modelo.Producto;
import uniandes.edu.co.proyecto.repositorio.ProductoRepository;
import org.springframework.stereotype.Controller;

//Controlador de la entidad Producto que se encarga de realizar las peticiones HTTP
@Controller
public class ProductoController {

    //Inyeccion de dependencias
    @Autowired //Inyecta el bean que se encarga de la logica de la aplicacion
    private ProductoRepository productoRepository; //Bean de la interfaz ProductoRepository

    //Metodo que se encarga de devolver todos los productos
    @GetMapping("/productos") 
    @ResponseBody //Indica que el metodo se activa cuando se hace una peticion GET a la URL /productos
    public Collection<Producto> producto(){
        return productoRepository.darProductos();
    }

    @GetMapping("/productos/nombre/{nombre}")
    public ResponseEntity<?> obtenerProductoNombre(@PathVariable String nombre) {
        Producto producto = productoRepository.darProductoNombre(nombre);
    
        // Devolver si existe el producto
        if (producto != null) {
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Producto con nombre " + nombre + " no encontrado.", HttpStatus.NOT_FOUND);
        }
    }


    //Metodo que se encarga de devolver un producto por su identificador
    @GetMapping("/productos/{identificador}")
    public ResponseEntity<?> obtenerProducto(@PathVariable int identificador) {
        Producto producto = productoRepository.darProducto(identificador);
    
        // Devolver si existe el producto
        if (producto != null) {
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Producto con identificador " + identificador + " no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    //Metodo que se encarga de crear un producto
    @PostMapping("/productos/new/save") // Indica que el método se activa cuando se hace una petición POST a la URL /productos/new/save
    public ResponseEntity<String> productoGuardar(@RequestBody Producto producto) {
        try {
            // Inserta el producto en la base de datos usando la consulta en el repositorio
            productoRepository.insertarProducto(
                producto.getNombre(),
                producto.getCostoEnBodega(),
                producto.getPresentacion(),
                producto.getCantidadPresentacion(),
                producto.getUnidadMedida(),
                producto.getVolumenEmpaque(),
                producto.getPesoEmpaque(),
                producto.getFechaExpiracion(),
                producto.getCodigoDeBarras(),
                producto.getClasificacionCategoria().getCodigo()
            );
            return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en la consola para ver detalles
            return new ResponseEntity<>("Error al crear el producto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/productos/filtro")
    public String mrpdoctosfiltro() {
        return "filtrarProductos";
    }


    // Metodo que se encarga de editar un producto y lanza error si el usuario intenta modificar la fecha de expiración
    @PostMapping("/productos/{identificador}/edit/save")
    public ResponseEntity<String> productoEditarGuardar(@PathVariable("identificador") Integer identificador, @RequestBody Producto producto) {
    try {
        // Verificar si el producto con el identificador existe
        if (!productoRepository.existeProducto(identificador)) {
            return new ResponseEntity<>("El producto con identificador " + identificador + " no existe", HttpStatus.NOT_FOUND);
        }

        // Verificar que ninguno de los campos obligatorios sea null (sin verificar fecha de expiración)
        if (producto.getNombre() == null || producto.getCostoEnBodega() == null || producto.getPresentacion() == null ||
            producto.getCantidadPresentacion() == null || producto.getUnidadMedida() == null || 
            producto.getVolumenEmpaque() == null || producto.getPesoEmpaque() == null || producto.getCodigoDeBarras() == null ||
            producto.getClasificacionCategoria() == null || producto.getClasificacionCategoria().getCodigo() == null) {

            return new ResponseEntity<>("Uno o más campos obligatorios están vacíos o nulos.", HttpStatus.BAD_REQUEST);
        }

        // Obtener el producto actual desde la base de datos
        Producto productoActual = productoRepository.darProducto(identificador);
        if (productoActual == null) {
            return new ResponseEntity<>("El producto con identificador " + identificador + " no existe", HttpStatus.NOT_FOUND);
        }

        // Verificar si el usuario está intentando modificar la fecha de expiración
        if (producto.getFechaExpiracion() != null && !producto.getFechaExpiracion().equals(productoActual.getFechaExpiracion())) {
            return new ResponseEntity<>("No está permitido modificar la fecha de expiración del producto.", HttpStatus.BAD_REQUEST);
        }

        // Actualizar el producto sin modificar la fecha de expiración
        productoRepository.actualizarProducto(identificador, producto.getNombre(), producto.getCostoEnBodega(), 
                                              producto.getPresentacion(), producto.getCantidadPresentacion(), 
                                              producto.getUnidadMedida(), producto.getVolumenEmpaque(), 
                                              producto.getPesoEmpaque(), productoActual.getFechaExpiracion(), 
                                              producto.getCodigoDeBarras(), producto.getClasificacionCategoria().getCodigo());

        return new ResponseEntity<>("Producto actualizado exitosamente", HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>("Error al actualizar el producto", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    //Metodo que se encarga de obtener los productos filtrados por precio, fecha de expiracion, sucursal o categoria
    @GetMapping("productos/filtrados")
    @ResponseBody       
    public Collection<Producto> productosfiltrados(
        @RequestParam(required = false) Double precioMinU, 
        @RequestParam(required = false) Double precioMaxU,
        @RequestParam(required = false) String fechaSuperiorU,
        @RequestParam(required = false) String fechaInferiorU,
        @RequestParam(required = false) Integer sucursalIdU,
        @RequestParam(required = false) String categoriaNombreU
    ){
        return productoRepository.darProductosFiltrados(precioMinU, precioMaxU, fechaSuperiorU, fechaInferiorU, sucursalIdU, categoriaNombreU);
    }

    @GetMapping("/productos/productosEnBodega/formulario")
    public String menuBodega(Model model) {
        return "inventario";
    }

    //Para RCF4:
    //Metodo que se encarga de obtener los productos en una bodega dada de una sucursal dada
    @GetMapping("/productos/productosEnBodega")
    @ResponseBody
    public Collection<Map<String, Object>> obtenerProductosEnBodega(
            @RequestParam Integer idSucursal,
            @RequestParam Integer idBodega) {
        Collection<Object[]> resultado = productoRepository.darProductosBodega(idSucursal, idBodega);
        
        // Convertir los resultados en una colección de Map<String, Object> para mayor claridad en el retorno.
        Collection<Map<String, Object>> listaproductos = new ArrayList<>();
        for (Object[] fila : resultado) {
            Map<String, Object> productos = new HashMap<>();
            productos.put("id", fila[0]);
            productos.put("nombre", fila[1]);
            productos.put("nivelMinimoReorden", fila[2]);
            productos.put("costoPromedio", fila[3]);
            productos.put("cantidadEnBodega", fila[4]);
            listaproductos.add(productos);
        }
        return listaproductos;
    }

    @GetMapping("/productos/bajoNivelReorden")
    public String mostrarProductosBajoNivelReorden(Model model) {
        return "productosBajoNivelReorden"; // Nombre del archivo HTML que mostraría la lista de productos
    }

    //Para RCF5:
    //Metodo que se encarga de obtener los productos para orden de compra
    @GetMapping("/productosParaOrdenDeCompra")
    @ResponseBody
    public Collection<Map<String, Object>> obtenerProductosParaOrdenDeCompra() {
    Collection<Object[]> resultado = productoRepository.obtenerProductosBajoNivelReorden();
    
    // Convertir los resultados en una colección de Map<String, Object> para mayor claridad en el retorno.
    Collection<Map<String, Object>> productosParaOrdenCompra = new ArrayList<>();
    for (Object[] fila : resultado) {
        Map<String, Object> producto = new HashMap<>();
        producto.put("identificador", fila[0]);
        producto.put("nombreProducto", fila[1]);
        producto.put("nombreBodegaNivelBajo", fila[2]);
        producto.put("nombreSucursalAsociada", fila[3]);
        producto.put("nombrePosibleProveedor", fila[4]);
        producto.put("cantidadActualEnBodega", fila[5]);
        producto.put("nivelMinimoReorden", fila[6]);
        productosParaOrdenCompra.add(producto);
    }
    return productosParaOrdenCompra;
    }


    //Metodo para el HTML

    @GetMapping("/menuProductos")
    public String menuProducto() {
        return "menuProductos";
    }

    @GetMapping("/productos/nuevo")
    public String mostrarFormularioCreacionProducto() {
        return "nuevoProducto";
    }

    @GetMapping("/productos/buscar")
    public String mostrarFormularioBusquedaProducto() {
        return "buscarProducto";
    }

    @GetMapping("/productos/editar")
    public String mostrarFormularioEdicionProducto() {
        return "editarProducto";
    }
    
}

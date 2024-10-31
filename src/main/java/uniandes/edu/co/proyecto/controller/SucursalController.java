package uniandes.edu.co.proyecto.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import uniandes.edu.co.proyecto.modelo.Sucursal;
import uniandes.edu.co.proyecto.repositorio.SucursalRepository;

// Controlador de la entidad Sucursal que se encarga de realizar las peticiones HTTP
@Controller
public class SucursalController {

    // Inyeccion de dependencias
    @Autowired // Inyecta el bean que se encarga de la logica de la aplicacion
    private SucursalRepository sucursalRepository; // Bean de la interfaz SucursalRepository

    // Metodo que se encarga de devolver todas las sucursales
    @GetMapping("/sucursales") // Indica que el metodo se activa cuando se hace una peticion GET a la URL /sucursales
    public String sucursales(Model model) {
        Collection<Sucursal> listaSucursales = sucursalRepository.darSucursales();
        model.addAttribute("sucursales", listaSucursales);
        return "sucursales";
    }

    @GetMapping("/menusucursales")
    public String menusucursales(Model model) {
        return "menusucursales";
    }

    @GetMapping("/sucursalxproducto")
    public String sucursalxproducto(Model model) {
        return "sucursalxproducto";
    }

    // Metodo que se encarga de devolver una sucursal por su ID
    @GetMapping("/sucursales/{id}") // Indica que el metodo se activa cuando se hace una peticion GET a la URL /sucursales/{id}
    public ResponseEntity<Sucursal> obtenerSucursal(@PathVariable int id) {
        Sucursal sucursal = sucursalRepository.darSucursal(id);

        // Devolver si existe
        if (sucursal != null) {
            return new ResponseEntity<>(sucursal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Metodo que se encarga de crear una sucursal
    @PostMapping("/sucursales/new/save") // Se activa cuando se hace una petición POST a /sucursales/new/save
    public ResponseEntity<String> sucursalGuardar(@RequestBody Sucursal sucursal) {
        try {
        // Guardamos la nueva sucursal usando el repositorio
            sucursalRepository.insertarSucursal(
                sucursal.getNombre(),
                sucursal.getTamanio(),
                sucursal.getDireccion(),
                sucursal.getTelefono(),
                sucursal.getCodigoCiudad().getCodigo() // Asegurarse de que `getCodigoCiudad` y `getCodigo` estén correctos
            );
            return new ResponseEntity<>("Sucursal creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la sucursal", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/sucursales/nueva")
    public String mostrarFormularioNuevaSucursal() {
        return "nuevaSucursal"; // Retorna la vista nuevaSucursal.html
    }


    // Método para buscar sucursales por ID de producto y devolver JSON
    @GetMapping("/sucursales/buscarPorProductoId/{idProducto}")
    @ResponseBody // Para devolver JSON directamente
    public ResponseEntity<Collection<Sucursal>> buscarPorProductoId(@PathVariable int idProducto) {
        Collection<Sucursal> sucursales = sucursalRepository.darSucursalesConProductoIdentificador(idProducto);
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }

    // Método para buscar sucursales por nombre de producto y devolver JSON
    @GetMapping("/sucursales/buscarPorProductoNombre/{nombreProducto}")
    @ResponseBody // Para devolver JSON directamente
    public ResponseEntity<Collection<Sucursal>> buscarPorProductoNombre(@PathVariable String nombreProducto) {
        Collection<Sucursal> sucursales = sucursalRepository.darSucursalesConProductoNombre(nombreProducto);
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }
}

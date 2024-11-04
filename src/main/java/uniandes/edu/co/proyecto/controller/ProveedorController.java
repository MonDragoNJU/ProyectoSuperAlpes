package uniandes.edu.co.proyecto.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import uniandes.edu.co.proyecto.modelo.Proveedor;
import uniandes.edu.co.proyecto.repositorio.ProveedorRepository;

//Controlador de la entidad Proveedor que se encarga de realizar las peticiones HTTP
@Controller
public class ProveedorController {

    //Inyeccion de dependencias
    @Autowired //Inyecta el bean que se encarga de la logica de la aplicacion
    private ProveedorRepository proveedorRepository; //Bean de la interfaz ProveedorRepository

    //Metodo que se encarga de devolver todos los proveedores
    @GetMapping("/proveedores")
    @ResponseBody //Indica que el metodo se activa cuando se hace una peticion GET a la URL /proveedores
    public Collection<Proveedor> proveedores(){
        return proveedorRepository.darProveedores();
    }

    //Metodo que se encarga de devolver un proveedor por su NIT
    @GetMapping("/proveedores/{nit}")
    public ResponseEntity<Proveedor> obtenerProveedor(@PathVariable String nit){
        
        Proveedor proveedor = proveedorRepository.darProveedor(nit);

        //Devolver si existe
        if (proveedor != null) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Metodo que se encarga de crear un proveedor
    @PostMapping("/proveedores/new/save") //Indica que el metodo se activa cuando se hace una peticion POST a la URL /proveedores/new/save
    public ResponseEntity<String> proveedorGuardar(@RequestBody Proveedor proveedor){
        
        try{
            proveedorRepository.insertarProveedor(proveedor.getNit(), proveedor.getNombre(), proveedor.getDireccion(), proveedor.getNombrePersonaContacto(), proveedor.getTelefonoPersonaContacto());
            return new ResponseEntity<>("Proveedor creado exitosamente", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error al crear el proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodo que se encarga de editar un proveedor
    @PostMapping("/proveedores/{nit}/edit/save") //Indica que el metodo se activa cuando se hace una peticion POST a la URL /proveedores/{nit}/edit/save
    public ResponseEntity<String> proveedorEditarGuardar(@PathVariable("nit") String nit, @RequestBody Proveedor proveedor){
        try {
            
        // Verificar si el proveedor con el NIT existe    
        if (!proveedorRepository.existsByNit(nit)) {
            return new ResponseEntity<>("Error: El proveedor con NIT " + nit + " no existe.", HttpStatus.NOT_FOUND);
        }
        //Actualizar el proveedor
        proveedorRepository.actualizarProveedor(nit, proveedor.getNombre(), proveedor.getDireccion(), proveedor.getNombrePersonaContacto(), proveedor.getTelefonoPersonaContacto());
        return new ResponseEntity<>("Proveedor actualizado exitosamente", HttpStatus.OK);
       }
       catch (Exception e){
        return new ResponseEntity<>("Error al actualizar el proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    

    //Metodos para el HTML

    @GetMapping("/menuProveedor")
    public String menuProveedor() {
        return "menuProveedor";
    }
    
    @GetMapping("/proveedores/nuevo")
    public String mostrarFormularioNuevoProveedor() {
        return "nuevoProveedor";
    }

    @GetMapping("/proveedores/editar")
    public String mostrarFormularioEditarProveedor() {
        return "editarProveedor";
    }


}

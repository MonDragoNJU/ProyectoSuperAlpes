package uniandes.edu.co.proyecto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import uniandes.edu.co.proyecto.modelo.Bodega;
import uniandes.edu.co.proyecto.repositorio.BodegaRepository;

//Controlador de la entidad Bodega que se encarga de realizar las peticiones HTTP
@Controller
public class BodegaController {

    //Inyeccion de dependencias
    @Autowired //Inyecta el bean que se encarga de la logica de la aplicacion
    private BodegaRepository bodegaRepository; //Bean de la interfaz BodegaRepository

    //Metodo que se encarga de devolver todas las bodegas
    @GetMapping("/bodegas") //Indica que el metodo se activa cuando se hace una peticion GET a la URL /bodegas
    public Collection<Bodega> bodegas(){
        return bodegaRepository.darBodegas();
    }

    @GetMapping("/bodegas/nueva")
    public String mostrarFormularioNuevaBodega() {
        return "nuevaBodega"; // Este nombre debe coincidir con el archivo HTML en la carpeta de plantillas
    }


    //Metodo que se encarga de devolver una bodega por su ID
    @GetMapping("/bodegas/{id}") //Indica que el metodo se activa cuando se hace una peticion GET a la URL /bodegas/{id}
    public ResponseEntity<Bodega> obtenerBodega(@PathVariable int id){ 

        Bodega bodega = bodegaRepository.darBodega(id);

        // Devolver si existe la bodega
        if (bodega != null) {
            return new ResponseEntity<>(bodega, HttpStatus.OK);
        // Devolver si no existe la bodega
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Metodo que se encarga de crear una bodega
    @PostMapping("/bodegas/new/save") //Indica que el metodo se activa cuando se hace una peticion POST a la URL /bodegas/new/save
    public ResponseEntity<String> bodegaGuardar(@RequestBody Bodega bodega){
        
        try{
            //Insertar la bodega en la base de datos
            bodegaRepository.insertarBodega(bodega.getNombre(), bodega.getTamanio(), bodega.getCapacidad(), bodega.getIdSucursal().getId());
            return new ResponseEntity<>("Bodega creada exitosamente", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error al crear la bodega", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/menuBodega")
    public String menuBodega(Model model) {
        return "menuBodega";
    }

    //Metodo que se encarga de eliminar una bodega
    @DeleteMapping("/bodegas/{id}/delete") //Indica que el metodo se activa cuando se hace una peticion DELETE a la URL /bodegas/{id}/delete
    public ResponseEntity<String> bodegaEliminar(@PathVariable("id") int id) {

        // Verificar si la bodega con el ID existe
        if (!bodegaRepository.existsById(id)) {
            // Devolver un mensaje de error si la bodega no existe
            return new ResponseEntity<>("Error: La bodega con ID " + id + " no existe.", HttpStatus.NOT_FOUND);
        }

        // Eliminar la bodega
        bodegaRepository.eliminarBodega(id);
        return new ResponseEntity<>("Bodega eliminada exitosamente", HttpStatus.OK);
    }

    @GetMapping("/bodegas/eliminar")
    public String mostrarFormularioEliminarBodega() {
        return "eliminarBodega"; // Asegúrate de que este nombre coincida con el archivo HTML en templates
    }

    // Método para mostrar el formulario HTML de ocupación de bodegas
    @GetMapping("/bodegas/ocupacion/formulario")
    public String mostrarFormularioOcupacionBodegas() {
        return "ocupacionBodegas"; // Asegúrate de que el archivo HTML se llame ocupacionBodegas.html y esté en templates
    }


    @GetMapping("/bodegas/ocupacion")
    @ResponseBody
    public Collection<Map<String, Object>> obtenerOcupacionBodegas(
            @RequestParam Integer idSucursalU,
            @RequestParam Collection<Integer> listaProductosU) {

        Collection<Object[]> resultado = bodegaRepository.obtenerOcupacionBodegas(idSucursalU, listaProductosU);

        Collection<Map<String, Object>> ocupacionBodegas = new ArrayList<>();
        for (Object[] fila : resultado) {
            Map<String, Object> bodega = new HashMap<>();
            bodega.put("id", fila[0]);
            bodega.put("nombre", fila[1]);
            bodega.put("porcentajeOcupacion", fila[2]);
            ocupacionBodegas.add(bodega);
        }
        return ocupacionBodegas;
    }

    

}

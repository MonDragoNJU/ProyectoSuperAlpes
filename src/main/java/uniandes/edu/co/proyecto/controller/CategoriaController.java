package uniandes.edu.co.proyecto.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import uniandes.edu.co.proyecto.modelo.Categoria;
import uniandes.edu.co.proyecto.repositorio.CategoriaRepository;

//Controlador de la entidad Categoria que se encarga de realizar las peticiones HTTP
@Controller
public class CategoriaController {

    //Inyeccion de dependencias
    @Autowired //Inyecta el bean que se encarga de la logica de la aplicacion
    private CategoriaRepository categoriaRepository; //Bean de la interfaz CategoriaRepository

    //Metodo que se encarga de devolver todas las categorias
    @GetMapping("/categorias")
    @ResponseBody //Indica que el metodo se activa cuando se hace una peticion GET a la URL /categorias
    public Collection<Categoria> categoria(){
        return categoriaRepository.darCategorias();
    }

    //Metodo que se encarga de devolver una categoria por su codigo
    @GetMapping("/categorias/codigo/{codigo}") //Indica que el metodo se activa cuando se hace una peticion GET a la URL /categorias/codigo/{codigo}
    public ResponseEntity<?> obtenerCategoriaCodigo(@PathVariable int codigo){ //Indica que el parametro se obtiene de la URL
        Categoria categoria = categoriaRepository.darCategoriaPorCodigo(codigo);

        // Devolver si existe
        if (categoria != null) {
            return new ResponseEntity<>(categoria, HttpStatus.OK);
        } else {
        // Mensaje personalizado cuando la categoría no existe
            String mensajeError = "Error: La categoría con el código " + codigo + " no existe.";
        return new ResponseEntity<>(mensajeError, HttpStatus.NOT_FOUND);
    }
    }

    //Metodo que se encarga de devolver una categoria por su nombre
    @GetMapping("/categorias/nombre/{nombre}") //Indica que el metodo se activa cuando se hace una peticion GET a la URL /categorias/nombre/{nombre}
    public ResponseEntity<?> obtenerCategoriaNombre(@PathVariable String nombre){
        Categoria categoria = categoriaRepository.darCategoriaPorNombre(nombre);

        // Devolver si existe
        if (categoria != null) {
            return new ResponseEntity<>(categoria, HttpStatus.OK);
        } else {
        // Mensaje personalizado cuando la categoría no existe
            String mensajeError = "Error: La categoría con el nombre " + nombre + " no existe.";
        return new ResponseEntity<>(mensajeError, HttpStatus.NOT_FOUND);
    }
    }

    //Metodo que se encarga de crear una categoria
    @PostMapping("/categorias/new/save")
    public ResponseEntity<String> categoriaGuardar(@RequestBody Categoria categoria) {
        try {
            categoriaRepository.insertarCategoria(categoria.getNombre(), categoria.getDescripcion(), categoria.getCaracteristicasAlmacenamiento());
            return new ResponseEntity<>("Categoría creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la categoría", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodo que se encarga de eliminar una categoria
    @DeleteMapping("/categorias/{codigo}/delete") //Indica que el metodo se activa cuando se hace una peticion DELETE a la URL /categorias/{codigo}/delete
    public ResponseEntity<String> categoriaEliminar(@PathVariable("codigo") int codigo) {

        categoriaRepository.eliminarCategoria(codigo);
        return new ResponseEntity<>("Categoria eliminada exitosamente", HttpStatus.OK);
    }

    //Metodos nuevos para le HTML
    @GetMapping("/menuCategoria")
    public String menuCategoria() {
        return "menuCategoria"; //Nombre del archivo HTML
    }

    @GetMapping("/categorias/nueva")
    public String mostrarFormularioNuevaCategoria() {
        return "nuevaCategoria"; //Nombre del archivo HTML
    }

    @GetMapping("/categorias/buscar")
    public String mostrarFormularioBuscarCategoria() {
        return "buscarCategoria"; //Nombre del archivo HTML
    }

}

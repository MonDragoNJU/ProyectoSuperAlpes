package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import uniandes.edu.co.proyecto.modelo.Ciudad;
import uniandes.edu.co.proyecto.repositorio.CiudadRepository;

import java.util.Collection;

@Controller
public class CiudadController {

    @Autowired
    private CiudadRepository ciudadRepository;

    @GetMapping("/ciudades")
    public String ciudades(Model model) {
        Collection<Ciudad> listaCiudades = ciudadRepository.darCiudades();
        model.addAttribute("ciudades", listaCiudades);
        return "ciudades"; // Retorna la plantilla ciudades.html
    }

    @GetMapping("/ciudades/nueva")
    public String nuevaCiudad() {
        return "nuevaCiudad"; // Retorna la plantilla nuevaCiudad.html
    }

    @PostMapping("/ciudades/new/save")
    public String ciudadGuardar(@RequestBody Ciudad ciudad) {
        ciudadRepository.insertarCiudad(ciudad.getNombre());
        return "redirect:/ciudades"; // Redirige a la lista de ciudades después de guardar
    }
}

package uniandes.edu.co.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/") // Mapea la raíz de la aplicación a este método
    public String home() {
        return "home"; // Devuelve el nombre de la plantilla home.html
    }
}

package uniandes.edu.co.proyecto.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import uniandes.edu.co.proyecto.modelo.DocumentoIngreso;
import uniandes.edu.co.proyecto.repositorio.DocumentoIngresoRepository;

@Controller
public class DocumentoIngresoController {

    @Autowired
    private DocumentoIngresoRepository documentoIngresoRepository;

    // Método para obtener todos los documentos de ingreso
    @GetMapping("/documentosIngreso")
    public Collection<DocumentoIngreso> obtenerTodosLosDocumentos() {
        return documentoIngresoRepository.obtenerTodosLosDocumentos();
    }

    @GetMapping("/documentosIngreso/formulario")
    public String obtenerdocs() {
        return "consultardocs";
    }

    // Método para obtener un documento de ingreso específico por su ID
    @GetMapping("/documentosIngreso/{id}")
    public ResponseEntity<DocumentoIngreso> obtenerDocumentoPorId(@PathVariable Long id) {
        DocumentoIngreso documento = documentoIngresoRepository.obtenerDocumentoPorId(id);
        if (documento != null) {
            return new ResponseEntity<>(documento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

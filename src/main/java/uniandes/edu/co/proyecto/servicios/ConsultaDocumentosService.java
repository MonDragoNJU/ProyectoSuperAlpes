package uniandes.edu.co.proyecto.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.repositorio.DocumentoIngresoRepository;


@Service
public class ConsultaDocumentosService {

    @Autowired
    private DocumentoIngresoRepository documentoIngresoRepository;

    //RFC6: Este es el metodo con nivel de aislamiento serializable
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public ResponseEntity<?> obtenerDocumentosIngresoSerializable(Long idSucursal, Long idBodega) {

        try {

   // Temporizador de 30 segundos antes de la consulta
 Thread.sleep(10000);
          
            //Al buscar como restar fechas, es posible usar minusDays para hacerlo, que bieeen
            //Entonces, restamos 30 dias a la fecha de hoy
            LocalDate fechaLimite = LocalDate.now().minusDays(30);

            //Tomamos los docs de ingreso de los últimos 30 dias al hacer la busqueda con el repository
            List<Map<String, Object>> documentos = documentoIngresoRepository.obtenerDocumentosIngreso(idSucursal, idBodega, fechaLimite);

 // Temporizador de 30 segundos antes de la consulta
 Thread.sleep(10000);


            //¿Hay documentos en los ultimos 30 dias?
            //Si la consulta no da como resultado vacio, entonces si hay
            if (!documentos.isEmpty()) {
                //Mapa para responder
                Map<String, Object> resultado = new HashMap<>();

                //Ponemos el nombre de la sucursal y bodega del primer documento
                //Da igual que sea el primero porque igual es el mismo dato para todos
                resultado.put("nombre_sucursal", documentos.get(0).get("nombre_sucursal"));
                resultado.put("nombre_bodega", documentos.get(0).get("nombre_bodega"));

                //Lista de mapas (con info de cada doc) para almacenar los detalles de cada documento
                List<Map<String, Object>> documentosDetalle = new ArrayList<>();

                //Iteramos doc por doc (que viene  su info en mapa) para poner su info en la lista
                for (Map<String, Object> documentoIngreso : documentos) {

                    //Mapa del documento con su info que luego se añadira
                    Map<String, Object> detalleDoc = new HashMap<>();
                    detalleDoc.put("id_documento", documentoIngreso.get("id_documento"));
                    detalleDoc.put("fecha_ingreso", documentoIngreso.get("fecha_ingreso"));
                    detalleDoc.put("nombre_proveedor", documentoIngreso.get("nombre_proveedor"));
                    documentosDetalle.add(detalleDoc);
                }

                //Ponemos todos los los detalles de todos todos todos documentos en el mapa resultado
                //O sea, ponemos la lista de mapas de info de documentos en el mapa grande de respuesta
                resultado.put("documentos", documentosDetalle);

                return new ResponseEntity<>(resultado, HttpStatus.OK);
            } 
            //Si da vacio, entonces no hay docs en los ultimos 30 dias
            else {
                return new ResponseEntity<>("No se encontraron documentos de ingreso en los últimos 30 días.", HttpStatus.OK);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>("Error: interrupción en la espera.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al realizar la consulta de documentos de ingreso.", HttpStatus.INTERNAL_SERVER_ERROR);
        }   

    }

    //RFC7: Este es el metodo con nivel de aislamiento read committed
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public ResponseEntity<?> obtenerDocumentosIngresoReadCommitted(Long idSucursal, Long idBodega)  {

        try {

            // Temporizador de 30 segundos antes de la consulta
            Thread.sleep(60000);

            //Al buscar como restar fechas, es posible usar minusDays para hacerlo, que bieeen
            //Entonces, restamos 30 dias a la fecha de hoy
            LocalDate fechaLimite = LocalDate.now().minusDays(30);

            //Tomamos los docs de ingreso de los últimos 30 dias al hacer la busqueda con el repository
            List<Map<String, Object>> documentos = documentoIngresoRepository.obtenerDocumentosIngreso(idSucursal, idBodega, fechaLimite);

            //¿Hay documentos en los ultimos 30 dias?
            //Si la consulta no da como resultado vacio, entonces si hay
            if (!documentos.isEmpty()) {
                //Mapa para responder
                Map<String, Object> resultado = new HashMap<>();

                //Ponemos el nombre de la sucursal y bodega del primer documento
                //Da igual que sea el primero porque igual es el mismo dato para todos
                resultado.put("nombre_sucursal", documentos.get(0).get("nombre_sucursal"));
                resultado.put("nombre_bodega", documentos.get(0).get("nombre_bodega"));

                //Lista de mapas (con info de cada doc) para almacenar los detalles de cada documento
                List<Map<String, Object>> documentosDetalle = new ArrayList<>();

                //Iteramos doc por doc (que viene  su info en mapa) para poner su info en la lista
                for (Map<String, Object> documentoIngreso : documentos) {

                    //Mapa del documento con su info que luego se añadira
                    Map<String, Object> detalleDoc = new HashMap<>();
                    detalleDoc.put("id_documento", documentoIngreso.get("id_documento"));
                    detalleDoc.put("fecha_ingreso", documentoIngreso.get("fecha_ingreso"));
                    detalleDoc.put("nombre_proveedor", documentoIngreso.get("nombre_proveedor"));
                    documentosDetalle.add(detalleDoc);
                }

                //Ponemos todos los los detalles de todos todos todos documentos en el mapa resultado
                //O sea, ponemos la lista de mapas de info de documentos en el mapa grande de respuesta
                resultado.put("documentos", documentosDetalle);

                return new ResponseEntity<>(resultado, HttpStatus.OK);
            } 
            //Si da vacio, entonces no hay docs en los ultimos 30 dias
            else {
                return new ResponseEntity<>("No se encontraron documentos de ingreso en los últimos 30 días.", HttpStatus.OK);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>("Error: interrupción en la espera.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al realizar la consulta de documentos de ingreso.", HttpStatus.INTERNAL_SERVER_ERROR);
        }   

    }

}
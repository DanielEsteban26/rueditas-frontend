package pe.edu.cibertec.rueditasfrontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.rueditasfrontend.dto.RueditasRequest;
import pe.edu.cibertec.rueditasfrontend.dto.RueditasResponse;
import pe.edu.cibertec.rueditasfrontend.viewmodel.RueditaModel;

@Controller // Indica que esta clase es un controlador de Spring MVC
@RequestMapping("/rueditas") // Mapea las solicitudes HTTP a /rueditas a este controlador
public class RueditasController {

    @Autowired // Inyecta una instancia de RestTemplate
    RestTemplate restTemplate;

    @GetMapping("/buscar") // Mapea las solicitudes GET a /rueditas/buscar a este método
    public String buscarRueditas(Model model) {
        // Crea un modelo de Ruedita con código "00" y atributos nulos
        RueditaModel rueditaModel = new RueditaModel("00", null, null, null, null, null, null);
        // Añade el modelo al objeto Model
        model.addAttribute("rueditasModel", rueditaModel);
        // Devuelve el nombre de la vista 'inicio'
        return "inicio";
    }

    @PostMapping("/resultado") // Mapea las solicitudes POST a /rueditas/resultado a este método
    public String resultado(RueditasRequest rueditasRequest, Model model) {
        // Validar la solicitud
        if (rueditasRequest == null || rueditasRequest.placa() == null || rueditasRequest.placa().isEmpty()) {
            // Si la solicitud es nula o el campo 'placa' está vacío, añadir un mensaje de error al modelo
            RueditaModel rueditaModel = new RueditaModel("01", "Por favor, rellene el campo.", null, null, null, null, null);
            model.addAttribute("rueditasModel", rueditaModel);
            // Redirigir a la página 'inicio'
            return "inicio";
        }

        try {
            // URL del servicio REST
            String url = "http://localhost:8081/rueditas/buscar";
            // Realizar la solicitud POST al servicio REST
            RueditasResponse rueditasResponse = restTemplate.postForObject(url, rueditasRequest, RueditasResponse.class);

            // Verificar si el código de la respuesta es correcto
            if ("00".equals(rueditasResponse.codigo())) {
                // Si el código es '00', añadir la respuesta al modelo
                model.addAttribute("rueditasModel", rueditasResponse);
                // Redirigir a la página 'principalRuedita'
                return "principalRuedita";
            } else {
                // Si el código de respuesta es incorrecto, añadir un mensaje de error al modelo
                RueditaModel rueditaModel = new RueditaModel("01", "Código de respuesta incorrecto.", null, null, null, null, null);
                model.addAttribute("rueditasModel", rueditaModel);
                // Redirigir a la página 'inicio'
                return "inicio";
            }
        } catch (Exception e) {
            // Si ocurre una excepción, añadir un mensaje de error al modelo
            RueditaModel rueditaModel = new RueditaModel("99", "Error al encontrar el auto.", null, null, null, null, null);
            model.addAttribute("rueditasModel", rueditaModel);
            // Redirigir a la página 'inicio'
            return "inicio";
        }
    }
}
package co.edu.unbosque.app.controllers;

import co.edu.unbosque.app.model.KMPAlgorithm;
import co.edu.unbosque.app.model.BoyerMooreAlgorithm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

@Controller
public class SearchController {

    @GetMapping("/")
    public String index() {
        return "search"; // Carga la vista search.html
    }

    @PostMapping("/upload")
    public String uploadFile(
        @RequestParam("file") MultipartFile file, 
        Model model) {

        // Verificar si el archivo está vacío
        if (file.isEmpty()) {
            model.addAttribute("error", "Debe seleccionar un archivo válido");
            return "search";
        }

        try {
            // Leer el archivo y convertirlo en un String
            byte[] bytes = file.getBytes();
            String content = new String(bytes);

            // Agregar el contenido del archivo al modelo
            model.addAttribute("content", content);

        } catch (IOException e) {
            // Manejar posibles errores de lectura del archivo
            model.addAttribute("error", "Error al leer el archivo: " + e.getMessage());
            return "search";
        }

        return "search"; // Cargar la vista con el contenido del archivo
    }

    @PostMapping("/search")
    public String searchWord(
        @RequestParam("file") MultipartFile file, 
        @RequestParam("word") String word, 
        @RequestParam("algorithm") String algorithm, 
        Model model) {

        // Verificar si el archivo está vacío
        if (file.isEmpty()) {
            model.addAttribute("error", "Debe seleccionar un archivo válido");
            return "search";
        }

        try {
            // Leer el archivo y convertirlo en un String
            byte[] bytes = file.getBytes();
            String content = new String(bytes);

            // Elegir el algoritmo de búsqueda
            List<Integer> positions;
            if ("KMP".equals(algorithm)) {
                positions = KMPAlgorithm.search(content, word);
            } else {
                positions = BoyerMooreAlgorithm.search(content, word);
            }

            // Agregar los resultados al modelo
            model.addAttribute("positions", positions);
            model.addAttribute("count", positions.size());
            model.addAttribute("word", word);
            model.addAttribute("content", content);

        } catch (IOException e) {
            // Manejar posibles errores de lectura del archivo
            model.addAttribute("error", "Error al leer el archivo: " + e.getMessage());
            return "search";
        }

        return "search";
    }
}

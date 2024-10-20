package co.edu.unbosque.app.controllers;

import co.edu.unbosque.app.model.KMPAlgorithm;
import co.edu.unbosque.app.model.BoyerMooreAlgorithm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.nio.file.Files;
import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.List;
//import java.io.IOException;

@Controller
public class SearchController {

    @GetMapping("/")
    public String index() {
        return "search"; // Carga la vista search.html
    }

    @PostMapping("/search")
    public String searchWord(@RequestParam("file") MultipartFile file,
                             @RequestParam("word") String word,
                             @RequestParam("algorithm") String algorithm,
                             Model model) throws Exception {
        // Leer el archivo
        Path tempFile = Files.createTempFile(null, ".txt");
        file.transferTo(tempFile);
        
        // Read file content as a string
        String content = new String(Files.readAllBytes(tempFile));
        
        // Elegir algoritmo
        List<Integer> positions;
        if ("KMP".equals(algorithm)) {
            positions = KMPAlgorithm.search(content, word);
        } else {
            positions = BoyerMooreAlgorithm.search(content, word);
        }
        
        // Agregar resultados al modelo
        model.addAttribute("positions", positions);
        model.addAttribute("count", positions.size());
        model.addAttribute("word", word);
        model.addAttribute("content", content);
        
        return "search";
    }
}

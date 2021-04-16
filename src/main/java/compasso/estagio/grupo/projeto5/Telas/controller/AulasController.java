package compasso.estagio.grupo.projeto5.Telas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("aulas")
public class AulasController {
    
    @GetMapping
    public String aulas(){
        return "aulas";
    }
}
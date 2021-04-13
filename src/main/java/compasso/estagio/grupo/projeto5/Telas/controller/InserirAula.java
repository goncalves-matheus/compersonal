package compasso.estagio.grupo.projeto5.Telas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("inseriraula")
public class InserirAula {
	
	@GetMapping
    public String inserir(){
        return "inseriraula";
    }
}

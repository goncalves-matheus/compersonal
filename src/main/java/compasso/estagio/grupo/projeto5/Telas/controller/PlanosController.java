package compasso.estagio.grupo.projeto5.Telas.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("planos")
public class PlanosController {
    
    @GetMapping
    public String planos(){
        return "pagar";
    }
}
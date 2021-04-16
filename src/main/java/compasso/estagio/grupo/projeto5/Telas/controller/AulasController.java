package compasso.estagio.grupo.projeto5.Telas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("aulas")
public class AulasController {
	
	@Autowired
	AulaRepository aulaRepository;
    
    @GetMapping
    public String aulas(Model modelo){
    	modelo.addAttribute("aula", aulaRepository.findById((long) 1).get());
        return "aulas";
    }
}
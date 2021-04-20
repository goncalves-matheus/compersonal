package compasso.estagio.grupo.projeto5.Telas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
import compasso.estagio.grupo.projeto5.Telas.model.Tipo;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;

@Controller
@RequestMapping("aulas")
public class AulasController {

	@Autowired
	AulaRepository aulaRepository;

	@GetMapping
	public String aulas(Model modelo, MensagemDto mensagemDto) {

		modelo.addAttribute("aula", aulaRepository.findById((long) 1).get());
		modelo.addAttribute("gluteo", aulaRepository.findByTipo(Tipo.GLUTEO));
		modelo.addAttribute("abdomen", aulaRepository.findByTipo(Tipo.ABDOMEN));
		modelo.addAttribute("perna", aulaRepository.findByTipo(Tipo.PERNAS));
		modelo.addAttribute("braco", aulaRepository.findByTipo(Tipo.BRACOS));
		modelo.addAttribute("peito", aulaRepository.findByTipo(Tipo.PEITO));

		return "aulas";
	}

	@GetMapping("/{titulo}")
	public String AulaId(@PathVariable String titulo, Model modelo, MensagemDto mensagemDto) {

		modelo.addAttribute("aula", aulaRepository.findByTitulo(titulo));
		modelo.addAttribute("gluteo", aulaRepository.findByTipo(Tipo.GLUTEO));
		modelo.addAttribute("abdomen", aulaRepository.findByTipo(Tipo.ABDOMEN));
		modelo.addAttribute("perna", aulaRepository.findByTipo(Tipo.PERNAS));
		modelo.addAttribute("braco", aulaRepository.findByTipo(Tipo.BRACOS));
		modelo.addAttribute("peito", aulaRepository.findByTipo(Tipo.PEITO));

		return "aulas";
	}
}
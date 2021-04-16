package compasso.estagio.grupo.projeto5.Telas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;

@Controller
@RequestMapping("dashboard")
public class DashboardController {

	@Autowired
	AulaRepository aulaRepository;

	@GetMapping("/aluno")
	public String aluno(Model modelo) {

		Pageable page = PageRequest.of(0, 10, Sort.by("Id").descending());

		Page<Aula> aula = aulaRepository.findAll(page);
		modelo.addAttribute("aulas", aula);
		return "dashboard_aluno";
	}
	
	@GetMapping("/{titulo}")
	public String aulaSelecionada(@PathVariable String titulo) {
		return "redirect:/aulas/{titulo}";
	}

	@GetMapping("/personal")
	public String personal() {
		return "dashboard_personal";
	}

}

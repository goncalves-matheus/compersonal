package compasso.estagio.grupo.projeto5.Telas.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

		ArrayList<Aula> aula = (ArrayList<Aula>) aulaRepository.findAll();
		modelo.addAttribute("aulas", aula);
		return "dashboard_aluno";
	}

	@GetMapping("/personal")
	public String personal() {
		return "dashboard_personal";
	}

}

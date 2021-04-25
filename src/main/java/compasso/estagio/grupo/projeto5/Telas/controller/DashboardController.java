package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("dashboard")
public class DashboardController {

	@Autowired
	AulaRepository aulaRepository;
	
	@Autowired
	PerfilRepository perfilRepository;

	@GetMapping("/aluno")
	public String aluno(Model modelo, Principal principal) {
		
		
		List<Aula> aulas = aulaRepository.findByAlunos(perfilRepository.findByEmail(principal.getName()));
		if(aulas.size()>10) {
			aulas = aulas.subList(0, 10);
		}
		
		modelo.addAttribute("aulas", aulas);

		return "dashboard_aluno";
	}

	@GetMapping("/aluno/erroAlunoSemAula")
	public String alunoSemAulas(Model modelo) {

		modelo.addAttribute("erroAlunoSemAula", "Sem aulas cadastradas, procure o inscrutor");

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

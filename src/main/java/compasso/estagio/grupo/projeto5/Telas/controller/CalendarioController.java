package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import compasso.estagio.grupo.projeto5.Telas.model.Agenda;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.AgendaRepositoy;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("/calendario")
public class CalendarioController {
	
	private int aux;
	private int cont;

	@Autowired
	private AgendaRepositoy agendaRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@GetMapping
	public String MontaAgenda(Model modelo) {

		if(aux>0) {
			modelo.addAttribute("erro", "Horário indisponível");
			aux=0;
		}
		
		if(cont>0) {
			modelo.addAttribute("agenda", "Agendado com sucesso!");
			cont=0;
		}

		return "calendario";
	}

	@GetMapping(value = "/getEventos.json")
	public @ResponseBody List<Agenda> GetEventos() {

		List<Agenda> eventos = agendaRepository.findAll();
		return eventos;

	}

	@PostMapping("/criar")
	public String Criar(String inicio, String fim) {
		
		Agenda agenda = new Agenda();
		agenda.setTitle("Livre");
		agenda.setColor("#7FFF00");
		agenda.setStart(inicio);
		agenda.setEnd(fim);
		agendaRepository.save(agenda);

		return "redirect:/calendario";
	}

	@PostMapping("/agendar")
	public String Agendar(String inicio, String fim, Principal principal) {

		Perfil aluno = perfilRepository.findByEmail(principal.getName());
		Agenda agenda = agendaRepository.findByStart(inicio);
		if (agenda.getTitle().equals("Livre")) {
			agenda.setTitle("Ocupado");
			agenda.setAluno(aluno.getPrimeiroNome()+" "+aluno.getUltimoNome());
			agenda.setEmail(aluno.getEmail());
			agenda.setColor("#FF6347");
			agenda.setStart(inicio);
			agenda.setEnd(fim);
			agendaRepository.save(agenda);
			cont++;
		}else {
			aux++;
		}

		return "redirect:/calendario";
	}

}
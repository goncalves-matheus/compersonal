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

	@Autowired
	private AgendaRepositoy repository;

	@Autowired
	private PerfilRepository perfilRepository;

	@GetMapping
	public String MontaAgenda(Principal principal, Model modelo) {

		Perfil perfil = perfilRepository.findByEmail(principal.getName());
		modelo.addAttribute("nome", perfil.getPrimeiroNome() + " " + perfil.getUltimoNome());
		modelo.addAttribute("email", perfil.getEmail());

		return "calendario";
	}

	@GetMapping(value = "/getEventos.json")
	public @ResponseBody List<Agenda> GetEventos() {

		List<Agenda> eventos = repository.findAll();
		return eventos;

	}

	@PostMapping("/criar")
	public String Criar(String inicio, String fim) {

		Agenda agenda = new Agenda();
		agenda.setTitle("Livre");
		agenda.setColor("#7FFF00");
		agenda.setStart(inicio);
		agenda.setEnd(fim);
		repository.save(agenda);

		return "redirect:/calendario";
	}

	@PostMapping("/agendar")
	public String Agendar(String nome, String email, String inicio, String fim, Principal principal, Model modelo) {

		Agenda agenda = repository.findByStart(inicio);
		if (agenda.getTitle().equals("Livre")) {
			agenda.setTitle("Ocupado");
			agenda.setAluno(nome);
			agenda.setColor("#FF6347");
			agenda.setStart(inicio);
			agenda.setEnd(fim);
			repository.save(agenda);
		}else {
			modelo.addAttribute("erro", "Horário indisponível para cadastro");
		}

		return "redirect:/calendario";
	}

}
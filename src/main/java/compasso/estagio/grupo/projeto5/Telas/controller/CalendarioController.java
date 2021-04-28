package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	private int semana;
	private int cont;

	@Autowired
	private AgendaRepositoy agendaRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@GetMapping
	public String MontaAgenda(Model modelo, Principal principal) {
		
		modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));

		if (aux > 0) {
			modelo.addAttribute("erro", "Horário indisponível");
			aux = 0;
		}
		if (semana > 0) {
			modelo.addAttribute("erro", "Hórario da semana já marcado!");
			semana = 0;
		}
		if (cont > 0) {
			modelo.addAttribute("agenda", "Agendado com sucesso!");
			cont = 0;
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
	
	@PostMapping("/deletar")
	public String Deletar(String inicio, String fim, Principal principal) {

		Agenda agenda = agendaRepository.findByStart(inicio);
		agendaRepository.delete(agenda);

		return "redirect:/calendario";
	}

	@PostMapping("/agendar")
	public String Agendar(String inicio, String fim, Principal principal) {

		Perfil aluno = perfilRepository.findByEmail(principal.getName());
		Agenda agenda = agendaRepository.findByStart(inicio);

		if (VerificarAgendamento(fim, aluno)) {
			if (agenda.getTitle().equals("Livre")) {
				agenda.setTitle("Ocupado");
				agenda.setAluno(aluno.getPrimeiroNome() + " " + aluno.getUltimoNome());
				agenda.setEmail(aluno.getEmail());
				agenda.setColor("#FF6347");
				agendaRepository.save(agenda);
				cont++;
			} else {
				aux++;
			}
		} else {
			semana++;
		}

		return "redirect:/calendario";
	}

	private Boolean VerificarAgendamento(String fim, Perfil aluno) {

		int semana = ValorSemanal(fim);
		ArrayList<Agenda> agendas = agendaRepository.findByAluno(aluno.getPrimeiroNome() + " " + aluno.getUltimoNome());

		for (Agenda agenda : agendas) {

			if (ValorSemanal(fomatador(agenda.getEnd())) == semana) {
				return false;
			}
		}

		return true;
	}

	private String fomatador(String end) {

		String ano = end.substring(11, 15);
		String dia = end.substring(8, 10);
		String mes = end.substring(4, 7);

		switch (mes) {
		case "Jan":
			mes = "01";
			break;
		case "Feb":
			mes = "02";
			break;
		case "Mar":
			mes = "03";
			break;
		case "Apr":
			mes = "04";
			break;
		case "May":
			mes = "05";
			break;
		case "Jun":
			mes = "06";
			break;
		case "Jul":
			mes = "07";
			break;
		case "Aug":
			mes = "08";
			break;
		case "Sep":
			mes = "09";
			break;
		case "Oct":
			mes = "10";
			break;
		case "Nov":
			mes = "11";
			break;
		case "Dec":
			mes = "12";
			break;
		}

		return ano + "-" + mes + "-" + dia + " 00:00:01";
	}

	private int ValorSemanal(String fim) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime data = LocalDateTime.parse(fim, formatter);
		LocalDateTime primeiraSemanaDeJaneiro = LocalDateTime.parse(data.getYear() + "-01-01 00:00:01", formatter);
		int diaDoAno = data.getDayOfYear();
		int diaDaSemana = data.getDayOfWeek().ordinal();
		int primeiroDiaDaSemanaDeJaneiro = primeiraSemanaDeJaneiro.getDayOfWeek().ordinal();
		int numeroDaSemana = ((diaDoAno + 6) / 7);
		if (diaDaSemana < primeiroDiaDaSemanaDeJaneiro) {
			numeroDaSemana++;
		}
		return numeroDaSemana;
	}

}
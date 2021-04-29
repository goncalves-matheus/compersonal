package compasso.estagio.grupo.projeto5.Telas.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.model.Agenda;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.Plano;
import compasso.estagio.grupo.projeto5.Telas.repository.AgendaRepositoy;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PlanosRepository;

@Controller
@RequestMapping("dashboard")
public class DashboardController {

	@Autowired
	AulaRepository aulaRepository;

	@Autowired
	PlanosRepository planosRepository;

	@Autowired
	PerfilRepository perfilRepository;

	@Autowired
	AgendaRepositoy agendaRepository;

	@GetMapping
	public String dashboard(Principal principal) {
		if(perfilRepository.findByEmail(principal.getName()).getPermissao().getPermissao().toLowerCase().equals("usuario")) {
			return "redirect:/dashboard/aluno";
		}
		return "redirect:/dashboard/personal";
	}

	@GetMapping("/aluno")
	public String aluno(Model modelo, Principal principal) {

		modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));
		List<Aula> aulas = aulaRepository.getAulaCadastrada(principal.getName());
		aulas = listaContraia(aulas);
		if (aulas.size() > 10) {
			aulas = aulas.subList(0, 10);
		} else if (aulas.size() == 0) {
			modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));
			modelo.addAttribute("erroAlunoSemAula", "Sem aulas cadastradas, procure o instrutor");
		}
		modelo.addAttribute("aulas", aulas);

		return "dashboard_aluno";
	}

	private List<Aula> listaContraia(List<Aula> aulas) {
		List<Aula> lista = new ArrayList<Aula>();
		for (int i=aulas.size()-1;i>=0;i--) {
			lista.add(aulas.get(i));
		}
		return lista;
	}

	@GetMapping("/aluno/erroAlunoSemAula")
	public String alunoSemAulas(Model modelo, Principal principal) {

		modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));
		modelo.addAttribute("erroAlunoSemAula", "Sem aulas cadastradas, procure o instrutor");

		return "dashboard_aluno";
	}

	@GetMapping("/{titulo}")
	public String aulaSelecionada(@PathVariable String titulo) {
		return "redirect:/aulas/{titulo}";
	}

	@GetMapping("/personal")
	public String personal(Model modelo, Principal principal) {

		modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));

		Long quandidadeDeAlunos = perfilRepository.count() - 1;
		Long quandidadeDeVideoaulas = aulaRepository.count();
		Long quantidadeDeAulasTotais = agendaRepository.count();
		int quantidadeDeAulasLivres = (agendaRepository.findByTitle("Livre")).size();
		int quantidadeDeAulasOcupadas = (agendaRepository.findByTitle("Ocupado")).size();
		int quantidadeDeHorasNoMes = calcularHoras("mes");
		int quantidadeDeHorasNaSemana = calcularHoras("semana");
		int quantidadeDeHorasNoDia = calcularHoras("dia");

		Long salarioTotal = getSalarioTotal();
		
		modelo.addAttribute("quandidadeDeAlunos", quandidadeDeAlunos);
		modelo.addAttribute("quandidadeDeVideoaulas", quandidadeDeVideoaulas);
		modelo.addAttribute("quantidadeDeAulasTotais", quantidadeDeAulasTotais);
		modelo.addAttribute("quantidadeDeAulasLivres", quantidadeDeAulasLivres);
		modelo.addAttribute("quantidadeDeAulasOcupadas", quantidadeDeAulasOcupadas);
		modelo.addAttribute("quantidadeDeHorasNoMes", quantidadeDeHorasNoMes);
		modelo.addAttribute("quantidadeDeHorasNaSemana", quantidadeDeHorasNaSemana);
		modelo.addAttribute("quantidadeDeHorasNoDia", quantidadeDeHorasNoDia);
		modelo.addAttribute("quantidadeDeSalario", salarioTotal);

		return "dashboard_personal";
	}

	private Long getSalarioTotal() {
		ArrayList<Plano> planos = planosRepository.findAll();
		Long valorTotal = Long.valueOf(0);
		for (Plano plano : planos) {
			valorTotal += Long.valueOf(plano.getValor());
		}
		return valorTotal;
	}

	private int calcularHoras(String periodoDeTempo) {
		final int segundosNumaHora = 3600;
		int totalHorasNoMes = 0;
		int totalHorasNaSemana = 0;
		int totalHorasNoDia = 0;

		ArrayList<Agenda> agendas = agendaRepository.findByTitle("Ocupado");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		for (Agenda agenda : agendas) {
			LocalDateTime inicioDaAula = LocalDateTime.parse(fomatador(agenda.getStart()), formatter);
			LocalDateTime fimDaAula = LocalDateTime.parse(fomatador(agenda.getEnd()), formatter);
			if (inicioDaAula.getMonth().equals(LocalDateTime.now().getMonth())) {
				Duration duracao = Duration.between(inicioDaAula, fimDaAula);
				totalHorasNoMes += duracao.getSeconds() / segundosNumaHora;
				if (ValorSemanal(inicioDaAula) == ValorSemanal(LocalDateTime.now())) {
					totalHorasNaSemana += ((duracao.getSeconds()) / segundosNumaHora);
					if (inicioDaAula.getDayOfMonth() == LocalDateTime.now().getDayOfMonth()){
						totalHorasNoDia += duracao.getSeconds() / segundosNumaHora;
					}
				}
			}
		}
		switch (periodoDeTempo) {
		case "dia":
			return totalHorasNoDia;
		case "semana":
			return totalHorasNaSemana;
		default:
			return totalHorasNoMes;
		}
	}

	private int ValorSemanal(LocalDateTime data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime primeiraSemanaDeJaneiro = LocalDateTime.parse(data.getYear() + "-01-01 00:00:01", formatter);
		int diaDoAno = data.getDayOfYear();
		int diaDaSemana = data.getDayOfWeek().ordinal();
		int primeiroDiaDaSemanaDeJaneiro = primeiraSemanaDeJaneiro.getDayOfWeek().ordinal();
		int numeroDaSemana = ((diaDoAno) / 7);
		if (diaDaSemana < primeiroDiaDaSemanaDeJaneiro) {
			numeroDaSemana++;
		}
		return numeroDaSemana;
	}

	private String fomatador(String data) {

		String ano = data.substring(11, 15);
		String dia = data.substring(8, 10);
		String mes = data.substring(4, 7);
		String hora = data.substring(16, 24);

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

		return ano + "-" + mes + "-" + dia + " " + hora;
	}

}

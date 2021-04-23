package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.model.Tipo;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.MensagemRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("aulas")
public class AulasController {

	@Autowired
	AulaRepository aulaRepository;

	@Autowired
	MensagemRepository mensagemRepository;

	@Autowired
	PerfilRepository perfilRepository;

	@GetMapping
	public String aulas(Model modelo, MensagemDto mensagemDto, Principal principal) {

		List<Aula> aulas = aulaRepository.findByAlunos(perfilRepository.findByEmail(principal.getName()));
		return "redirect:/aulas/" + aulas.get(0).getTitulo();
	}

	@GetMapping("/{titulo}")
	public String AulaId(@PathVariable String titulo, Model modelo, MensagemDto mensagemDto, Principal principal) {

		Perfil aluno = perfilRepository.findByEmail(principal.getName());

		modelo.addAttribute("aula", aulaRepository.findByTitulo(titulo));
		modelo.addAttribute("gluteo", aulaRepository.findByAlunoAndTipo(aluno, Tipo.GLUTEO));
		modelo.addAttribute("abdomen", aulaRepository.findByAlunoAndTipo(aluno, Tipo.ABDOMEN));
		modelo.addAttribute("perna", aulaRepository.findByAlunoAndTipo(aluno, Tipo.PERNAS));
		modelo.addAttribute("braco", aulaRepository.findByAlunoAndTipo(aluno, Tipo.BRACOS));
		modelo.addAttribute("peito", aulaRepository.findByAlunoAndTipo(aluno, Tipo.PEITO));

		carregarMensagensDoChat(modelo, principal);

		return "aulas";
	}

	private void carregarMensagensDoChat(Model modelo, Principal principal) {
		Perfil perfil = perfilRepository.findByEmail(principal.getName());
		List<Mensagem> mensagensDoAluno = mensagemRepository.findByPerfilId(perfil.getId());
		List<Mensagem> mensagensDoPersonal = mensagemRepository.findByPerfilIdDestinatarioId(Long.valueOf(1),
				perfil.getId());
		List<Mensagem> todasAsMensagens = Stream.concat(mensagensDoAluno.stream(), mensagensDoPersonal.stream())
				.collect(Collectors.toList());

		modelo.addAttribute("mensagensDoAluno", mensagensDoAluno);
		modelo.addAttribute("mensagensDoPersonal", mensagensDoPersonal);
		modelo.addAttribute("mensagens", todasAsMensagens);

	}
}
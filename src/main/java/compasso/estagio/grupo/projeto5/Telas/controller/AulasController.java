package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.Collections;
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
import compasso.estagio.grupo.projeto5.Telas.model.GestorDeMensagens;
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

	GestorDeMensagens gestorDeMensagens = new GestorDeMensagens();

	@GetMapping
	public String aulas(Model modelo, MensagemDto mensagemDto, Principal principal) {

		return "redirect:/aulas/" + aulaRepository.findAll().get(aulaRepository.findAll().size() - 1).getTitulo();

	}

	@GetMapping("/{titulo}")
	public String AulaId(@PathVariable String titulo, Model modelo, MensagemDto mensagemDto, Principal principal) {

		modelo.addAttribute("aula", aulaRepository.findByTitulo(titulo));
		modelo.addAttribute("gluteo", aulaRepository.findByTipo(Tipo.GLUTEO));
		modelo.addAttribute("abdomen", aulaRepository.findByTipo(Tipo.ABDOMEN));
		modelo.addAttribute("perna", aulaRepository.findByTipo(Tipo.PERNAS));
		modelo.addAttribute("braco", aulaRepository.findByTipo(Tipo.BRACOS));
		modelo.addAttribute("peito", aulaRepository.findByTipo(Tipo.PEITO));

		carregarMensagensDoChat(modelo, perfilRepository.findByEmail(principal.getName()));

		return "aulas";
	}
	
	public void carregarMensagensDoChat(Model modelo, Perfil aluno) {
        Perfil personal = perfilRepository.findByPermissaoPermissao("Personal");

        List<Mensagem> mensagensDoAluno = mensagemRepository.findByPerfilId(aluno.getId());
        List<Mensagem> mensagensDoPersonal = mensagemRepository.findByPerfilIdDestinatarioId(personal.getId(), aluno.getId());
        List<Mensagem> todasAsMensagens = Stream.concat(mensagensDoAluno.stream(), mensagensDoPersonal.stream()).collect(Collectors.toList());

        Collections.sort(todasAsMensagens);

        modelo.addAttribute("perfil", aluno);
        modelo.addAttribute("personal", personal);

        modelo.addAttribute("mensagens", todasAsMensagens);
        modelo.addAttribute("horaFinal", todasAsMensagens.get(todasAsMensagens.size()-1).getHoraFormatada());
    }

}
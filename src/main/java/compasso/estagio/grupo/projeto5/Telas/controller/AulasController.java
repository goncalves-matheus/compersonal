package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;

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

import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
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

		modelo.addAttribute("aula", aulaRepository.findById((long) 1).get());
		modelo.addAttribute("gluteo", aulaRepository.findByTipo(Tipo.GLUTEO));
		modelo.addAttribute("abdomen", aulaRepository.findByTipo(Tipo.ABDOMEN));
		modelo.addAttribute("perna", aulaRepository.findByTipo(Tipo.PERNAS));
		modelo.addAttribute("braco", aulaRepository.findByTipo(Tipo.BRACOS));
		modelo.addAttribute("peito", aulaRepository.findByTipo(Tipo.PEITO));

		carregarMensagensDoChat(modelo, principal);

		return "aulas";
	}

	@GetMapping("/{titulo}")
	public String AulaId(@PathVariable String titulo, Model modelo, MensagemDto mensagemDto, Principal principal) {

		modelo.addAttribute("aula", aulaRepository.findByTitulo(titulo));
		modelo.addAttribute("gluteo", aulaRepository.findByTipo(Tipo.GLUTEO));
		modelo.addAttribute("abdomen", aulaRepository.findByTipo(Tipo.ABDOMEN));
		modelo.addAttribute("perna", aulaRepository.findByTipo(Tipo.PERNAS));
		modelo.addAttribute("braco", aulaRepository.findByTipo(Tipo.BRACOS));
		modelo.addAttribute("peito", aulaRepository.findByTipo(Tipo.PEITO));
 
		carregarMensagensDoChat(modelo, principal);

		return "aulas";
	}

	private void carregarMensagensDoChat(Model modelo, Principal principal) {
		Perfil perfil = perfilRepository.findByEmail(principal.getName());
        Pageable page = PageRequest.of(0, 20, Sort.by("Id").ascending());
		Page<Mensagem> mensagens = mensagemRepository.findByPerfilId(perfil.getId(), page);
		modelo.addAttribute("mensagens", mensagens);
	}
}
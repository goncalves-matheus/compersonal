package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.GestorDeMensagens;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.MensagemRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("aulas")
public class AulasController extends GestorDeMensagens {

	@Autowired
	AulaRepository aulaRepository;

	@Autowired
	MensagemRepository mensagemRepository;

	@Autowired
	PerfilRepository perfilRepository;

	private int numeroDePags;

	@GetMapping
	@Cacheable(value = "listaDeAulas")
	public String aulas(Model modelo, MensagemDto mensagemDto, Principal principal) {
		Perfil perfil = perfilRepository.findByEmail(principal.getName());
		if (perfil.getPlano().getStatus().equals("3")) {
			try {
				List<Aula> aulas = aulaRepository.getAulaCadastrada(principal.getName());
				adiconarModelo(aulas.get(0).getTitulo(), modelo, principal);
				super.setRepositories(this.perfilRepository, this.mensagemRepository);
				carregarMensagensDoChat(modelo, perfil);

				return "aulas";
			} catch (Exception e) {
				return "redirect:/dashboard/aluno/erroAlunoSemAula";
			}
		}
		return "redirect:/dashboard/aluno";

	}

	@GetMapping("/{titulo}")
	public String AulaId(@PathVariable("titulo") String titulo, Model modelo, MensagemDto mensagemDto,
			Principal principal) {

		adiconarModelo(titulo, modelo, principal);

		super.setRepositories(this.perfilRepository, this.mensagemRepository);
		carregarMensagensDoChat(modelo, perfilRepository.findByEmail(principal.getName()));

		return "aulas";
	}

	@GetMapping("/minhasAulas/{page}")
	public String minhasAulas(@PathVariable(name = "page") int pagina, Model modelo, Principal principal) {

		Page<Aula> aulas = getListaDeAulas(principal, pagina);

		modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));
		modelo.addAttribute("aulas", aulas);
		modelo.addAttribute("erro", null);
		modelo.addAttribute("pagina", pagina);
		modelo.addAttribute("numeroPagina", numeroDePags);

		return "minhasAulas";
	}

	@PostMapping("/minhasAulas/delete")
	@Transactional
	public String minhasAulas(String titulo, Model modelo, Principal principal) {

		if (aulaRepository.getAulaCadastradaTitulo(titulo) != null) {
			modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));
			List<Aula> aulas = aulaRepository.findAll();
			modelo.addAttribute("aulas", aulas);
			modelo.addAttribute("erro", "Essa aula está associada a algum aluno!");
			return "minhasAulas";
		}

		aulaRepository.deleteByTitulo(titulo);
		return "redirect:/aulas/minhasAulas";

	}

	private void adiconarModelo(String titulo, Model modelo, Principal pricipal) {
		modelo.addAttribute("aula", aulaRepository.findByTitulo(titulo));
		modelo.addAttribute("gluteo", aulaRepository.findByTipo(0, pricipal.getName()));
		modelo.addAttribute("abdomen", aulaRepository.findByTipo(1, pricipal.getName()));
		modelo.addAttribute("perna", aulaRepository.findByTipo(2, pricipal.getName()));
		modelo.addAttribute("braco", aulaRepository.findByTipo(3, pricipal.getName()));
		modelo.addAttribute("peito", aulaRepository.findByTipo(4, pricipal.getName()));
	}

	private Page<Aula> getListaDeAulas(Principal principal, int pagina) {

		List<Aula> aulas = aulaRepository.findAll();

		numeroDePags = aulas.size() / 8;
		if (aulas.size() % 8 != 0) {
			numeroDePags++;
		}

		Pageable paginacao = PageRequest.of(pagina, 8, Sort.by("Id").ascending());
		Page<Aula> aulasPaginadas = aulaRepository.findAll(paginacao);
		return aulasPaginadas;
	}

}
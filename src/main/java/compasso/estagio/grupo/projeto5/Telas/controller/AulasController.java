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
	
	private int cont;

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
				adiconarModelo(aulas.get(aulas.size() - 1).getTitulo(), modelo, principal);
				modelo.addAttribute("proximo", 1);
				super.setRepositories(this.perfilRepository, this.mensagemRepository);
				carregarMensagensDoChat(modelo, perfil);

				return "aulas";
			} catch (Exception e) {
				return "redirect:/dashboard/aluno/erroAlunoSemAula";
			}
		}
		return "redirect:/dashboard/aluno";

	}

	@GetMapping("/pagina/{page}")
	public String proxima(@PathVariable("page") int page, Model modelo, MensagemDto mensagemDto, Principal principal) {
		Perfil perfil = perfilRepository.findByEmail(principal.getName());
		if (perfil.getPlano().getStatus().equals("3")) {
			try {
				List<Aula> aulas = aulaRepository.getAulaCadastrada(principal.getName());
				adiconarModelo(aulas.get(aulas.size() - 1 - page).getTitulo(), modelo, principal);
				modelo.addAttribute("proximo", page + 1);
				super.setRepositories(this.perfilRepository, this.mensagemRepository);
				carregarMensagensDoChat(modelo, perfil);

				return "aulas";
			} catch (Exception e) {
				if (e.getMessage().equals("Index -1 out of bounds for length 4")) {
					return "redirect:/aulas";
				}
				return "redirect:/dashboard/aluno/erroAlunoSemAula";
			}
		}
		return "redirect:/dashboard/aluno";

	}

	@GetMapping("/{titulo}")
	public String AulaId(@PathVariable("titulo") String titulo, Model modelo, MensagemDto mensagemDto,
			Principal principal) {
		
			adiconarModelo(titulo, modelo, principal);
			modelo.addAttribute("proximo", 0);
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
		
		if(cont==1) {
			modelo.addAttribute("erro", "1");
			cont=0;
		}

		return "minhasAulas";
	}

	@PostMapping("/minhasAulas/delete")
	@Transactional
	public String minhasAulas(String titulo, Model modelo, Principal principal) {

		if (aulaRepository.getAulaCadastradaTitulo(titulo) != null) {
			cont=1;
			return "redirect:/aulas/minhasAulas/0";
		}

		aulaRepository.deleteByTitulo(titulo);
		return "redirect:/aulas/minhasAulas";

	}

	private void adiconarModelo(String titulo, Model modelo, Principal pricipal) {

		List<Aula> gluteo = aulaRepository.findByTipo(0, pricipal.getName());
		List<Aula> abdomen = aulaRepository.findByTipo(1, pricipal.getName());
		List<Aula> perna = aulaRepository.findByTipo(2, pricipal.getName());
		List<Aula> braco = aulaRepository.findByTipo(3, pricipal.getName());
		List<Aula> peito = aulaRepository.findByTipo(4, pricipal.getName());

		modelo.addAttribute("aula", aulaRepository.findByTitulo(titulo));
		verificarAulas(gluteo, abdomen, perna, braco, peito, modelo);
		modelo.addAttribute("gluteo", gluteo);
		modelo.addAttribute("abdomen", abdomen);
		modelo.addAttribute("perna", perna);
		modelo.addAttribute("braco", braco);
		modelo.addAttribute("peito", peito);
	}

	private void verificarAulas(List<Aula> gluteo, List<Aula> abdomen, List<Aula> perna, List<Aula> braco,
			List<Aula> peito, Model modelo) {
		if (gluteo.isEmpty())
			modelo.addAttribute("vazioG", "Sem aulas cadastradas");
		else
			modelo.addAttribute("vazioG", null);
		if (abdomen.isEmpty())
			modelo.addAttribute("vazioA", "Sem aulas cadastradas");
		else
			modelo.addAttribute("vazioA", null);
		if (perna.isEmpty())
			modelo.addAttribute("vazioPA", "Sem aulas cadastradas");
		else
			modelo.addAttribute("vazioPA", null);
		if (braco.isEmpty())
			modelo.addAttribute("vazioB", "Sem aulas cadastradas");
		else
			modelo.addAttribute("vazioB", null);
		if (peito.isEmpty())
			modelo.addAttribute("vazioPO", "Sem aulas cadastradas");
		else
			modelo.addAttribute("vazioPO", null);
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
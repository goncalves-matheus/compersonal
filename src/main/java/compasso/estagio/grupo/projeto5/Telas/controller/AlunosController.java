package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.InformacaoAdicionalDto;
import compasso.estagio.grupo.projeto5.Telas.dto.UsuarioDto;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("/alunos")
public class AlunosController {

	private int cont;

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private AulaRepository aulaRepository;
	
	@GetMapping
	public String alunosAll(Model modelo, Principal principal) {
	
		List<UsuarioDto> estudantes = getListaDeAlunos(principal);
		modelo.addAttribute("estudantes", estudantes);
		
		return "alunos";
	}
	
    private List<UsuarioDto> getListaDeAlunos(Principal principal) {
		List<Perfil> perfis = perfilRepository.findAll();
		List<Perfil> personais = perfilRepository.findByPermissao(perfilRepository.findByEmail(principal.getName()).getPermissao());
		perfis.removeAll(personais);
		List<UsuarioDto> estudantes = UsuarioDto.converte(perfis);
		return estudantes;
	}
	
	
	@GetMapping("/perfil/{email}")
	public String uuniPerfil(@PathVariable("email") String email, Model modelo) {
		
		List<Aula> aulas = aulaRepository.findAll();
		List<Aula> aulasCadastradas = aulaRepository.findByAlunos(perfilRepository.findByEmail(email));
		aulas.removeAll(aulasCadastradas);
		
		UsuarioDto u = UsuarioDto.converte(perfilRepository.findByEmail(email));
		
		if(perfilRepository.findByEmail(email).getInformacao()!=null) {
			InformacaoAdicionalDto info = new InformacaoAdicionalDto();
			info.toInformacaoAdicionalDto(perfilRepository.findByEmail(email).getInformacao());
			modelo.addAttribute("info", info);
		} else {
			modelo.addAttribute("info", null);
		}
		modelo.addAttribute("aluno", u);
		modelo.addAttribute("aulas", aulas);
		if(cont > 0) {
			modelo.addAttribute("cadastrado", "Aula foi adicionada com sucesso!");
			cont = 0;
		}
		return "uniPerfil";
	}
	
	@PostMapping("/adicionarAula")
	public String adicionarAula(String titulo, String email, Model modelo) {
		if(!(titulo == null)) {
			Perfil perfil = perfilRepository.findByEmail(email);
			Aula aula = aulaRepository.findByTitulo(titulo);
			perfil.setAulas(aula);
			perfilRepository.save(perfil);
			cont++;
		}
		return "redirect:/alunos/perfil/"+email;
	}

}

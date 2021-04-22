package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.UsuarioDto;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("/alunos")
public class AlunosController {

	@Autowired
	private PerfilRepository repository;

	@GetMapping
	public String alunosAll(Model modelo, Principal principal) {
	
		List<UsuarioDto> estudantes = getListaDeAlunos(principal);
		modelo.addAttribute("estudantes", estudantes);
		
		return "alunos";
	}

	private List<UsuarioDto> getListaDeAlunos(Principal principal) {
		List<Perfil> perfis = repository.findAll();
		List<Perfil> personais = repository.findByPermissao(repository.findByEmail(principal.getName()).getPermissao());
		perfis.removeAll(personais);
		List<UsuarioDto> estudantes = UsuarioDto.converte(perfis);
		return estudantes;
	}

}

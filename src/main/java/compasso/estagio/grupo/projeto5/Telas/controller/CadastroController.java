package compasso.estagio.grupo.projeto5.Telas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.UsuarioDto;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.model.Permissao;
import compasso.estagio.grupo.projeto5.Telas.model.Usuario;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.UsuarioRepository;

@Controller
@RequestMapping("cadastro")
public class CadastroController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PerfilRepository perfilRepository;

	@GetMapping
	public String novoCadastro(UsuarioDto usuarioDto) {
		return "cadastro";
	}

	@PostMapping("/novo")
	public String novo(@Valid UsuarioDto usuarioDto, BindingResult result) {
		if (result.hasErrors()) {
			return "cadastro";
		}

		Perfil perfil = usuarioDto.toPerfil();
		Usuario usuario = usuarioDto.toUsuario();

		perfilRepository.save(perfil);

		Permissao permissao = new Permissao();
		permissao.setPermissao("Usuario");
		usuario.setPermissao(permissao);
		
		
		usuarioRepository.save(usuario);
		return "index";
	}

}

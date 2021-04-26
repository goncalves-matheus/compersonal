package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.AlterarSenhaDto;
import compasso.estagio.grupo.projeto5.Telas.dto.InformacaoAdicionalDto;
import compasso.estagio.grupo.projeto5.Telas.dto.PerfilDto;
import compasso.estagio.grupo.projeto5.Telas.model.Informacoes;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.model.Usuario;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.UsuarioRepository;

@Controller
@RequestMapping("perfil")
public class PerfilController {

	private int up;

	@Autowired
	PerfilRepository repository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping
	public String aulas(PerfilDto perfilDto, InformacaoAdicionalDto infoAdDto, AlterarSenhaDto senhaDto, Model modelo,
			Principal principal) {

		perfilDto = perfilDto.toPerfilDto(repository.findByEmail(principal.getName()));
		modelo.addAttribute("perfil", perfilDto);
		modelo.addAttribute("email", principal.getName());
		if (repository.findByEmail(principal.getName()).getInformacao() != null) {
			infoAdDto = infoAdDto.toInformacaoAdicionalDto(repository.findByEmail(principal.getName()).getInformacao());
			modelo.addAttribute("infoAd", infoAdDto);
			if (up > 0) {
				modelo.addAttribute("up", "Alterado com sucesso!");
				up = 0;
			}
		}

		return "perfil";
	}

	@PostMapping("/alterar")
	public String alterar(@Valid PerfilDto usuarioDto, BindingResult result, Principal principal) {

		if (result.hasErrors()) {
			return "perfil";
		}

		Perfil perfil = repository.findByEmail(principal.getName());
		perfil.setPrimeiroNome(usuarioDto.getPrimeiroNome());
		perfil.setUltimoNome(usuarioDto.getUltimoNome());
		repository.save(perfil);
		up++;
		return "redirect:/perfil";
	}

	@PostMapping("/alterarInfo")
	public String alterarInfo(@Valid InformacaoAdicionalDto infoAdDto, BindingResult result, Principal principal) {

		if (result.hasErrors()) {
			return "perfil";
		}
		Informacoes informacao = new Informacoes();
		Perfil perfil = repository.findByEmail(principal.getName());
		perfil.setInformacao(infoAdDto.toInformacoes(informacao));
		repository.save(perfil);
		up++;
		return "redirect:/perfil";
	}

	@PostMapping("/alterarSenha")
	public String alterarSenha(@Valid AlterarSenhaDto senhaDto, BindingResult result, Principal principal) {

		if (result.hasErrors()) {
			return "redirect:/perfil";
		}

		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());
		if (usuario.isPresent()) {
			Usuario user = usuario.get();
			System.out.println(user.getSenha());
			System.out.println(user.compare(senhaDto.getSenha()));
			if (user.compare(senhaDto.getSenha())) {
				System.out.println(user.compare(senhaDto.getSenha()));
				user.setSenha(senhaDto.getSenhaNova());
				usuarioRepository.save(user);
				up++;
			}
		}

		return "redirect:/perfil";
	}
}
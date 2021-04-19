package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.PerfilDto;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("perfil")
public class PerfilController {

	@Autowired
	PerfilRepository repository;

	@GetMapping
	public String aulas(PerfilDto perfilDto, Model modelo, Principal principal) {

		perfilDto = perfilDto.toPerfilDto(repository.findByEmail(principal.getName()));
		modelo.addAttribute("perfil", perfilDto);
		modelo.addAttribute("email", principal.getName());

		return "perfil";
	}

	@PostMapping("/alterar")
	public String alterar(@Valid PerfilDto usuarioDto, BindingResult result, Principal principal) {

		if(result.hasErrors()) {
			return "perfil";
		}
		
		Perfil perfil = repository.findByEmail(principal.getName());
		perfil.setPrimeiroNome(usuarioDto.getPrimeiroNome());
		perfil.setUltimoNome(usuarioDto.getUltimoNome());
		repository.save(perfil);

		return "redirect:/perfil";
	}
}
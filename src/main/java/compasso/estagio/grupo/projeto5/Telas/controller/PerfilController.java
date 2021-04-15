package compasso.estagio.grupo.projeto5.Telas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("perfil")
public class PerfilController {

	@Autowired
	PerfilRepository repository;

	@GetMapping
	public String aulas(Model modelo) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;

		if (principal instanceof UserDetails) {
			email = ((UserDetails) principal).getUsername();
			Perfil perfil = repository.findByEmail(((UserDetails) principal).getUsername());
			modelo.addAttribute("nome", perfil.getPrimeiroNome());
			modelo.addAttribute("sobrenome", perfil.getUltimoNome());
			modelo.addAttribute("email", email);
		} else {
			email = principal.toString();
		}

		return "perfil";
	}
}
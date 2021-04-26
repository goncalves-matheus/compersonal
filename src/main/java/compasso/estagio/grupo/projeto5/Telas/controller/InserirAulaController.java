package compasso.estagio.grupo.projeto5.Telas.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import compasso.estagio.grupo.projeto5.Telas.dto.AulaDto;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.Tipo;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;

@Controller
@RequestMapping("inseriraula")
public class InserirAulaController {

	private int cont;

	@Autowired
	AulaRepository aulaRepository;

	@GetMapping
	public String inserir(Model modelo,AulaDto aulaDto) {
		if(cont > 0) {
			modelo.addAttribute("cadastrado", "Aula cadastrada com sucesso!");
			cont = 0;
		}
		return "inseriraula";
	}

	@PostMapping
	public String novaAula(@Valid AulaDto aulaDto, BindingResult result) {
		if (result.hasErrors()) {
			return "inseriraula";
		}

		Aula aula = aulaDto.toAula();
		aula.setVideo(aula.getVideo().replace("watch?v=", "embed/"));

		switch (aulaDto.getTipo()) {
			case "Glúteo":
				aula.setTipo(Tipo.GLUTEO);
				break;
			case "Abdômen":
				aula.setTipo(Tipo.ABDOMEN);
				break;
			case "Pernas":
				aula.setTipo(Tipo.PERNAS);
				break;
			case "Braços":
				aula.setTipo(Tipo.BRACOS);
				break;
			case "Peito":
				aula.setTipo(Tipo.PEITO);
				break;
		}

		aulaRepository.save(aula);
		cont++;
		aulaDto = null;
		return "redirect:inseriraula";
	}
}

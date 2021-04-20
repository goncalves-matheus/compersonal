package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.MensagemRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("mensagem")
public class MensagemController {

    @Autowired
    private PerfilRepository repository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @GetMapping()
    public String mensagem(MensagemDto mensagemDto) {
        return "aulas";
    }

    @PostMapping("nova")
    public String novaMensagemAluno(@Valid MensagemDto mensagemDto, BindingResult result, Principal principal){
        if (result.hasErrors()) {
			return "aulas";
		}
        Mensagem mensagem = mensagemDto.toMensagem(); 
        Perfil perfil = repository.findByEmail(principal.getName());
        mensagem.setPerfil(perfil);
        perfil.setMensagens(mensagem);

        mensagemRepository.save(mensagem);
        return "redirect:/aulas";
    }

}

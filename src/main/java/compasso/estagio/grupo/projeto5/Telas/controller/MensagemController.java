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

import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.MensagemRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("mensagem")
public class MensagemController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @GetMapping
    public String chatPersonal(){
        return "chat-personal";
    }

    @PostMapping("nova")
    public String novaMensagemAluno(@Valid MensagemDto mensagemDto, Model modelo, BindingResult result, Principal principal){
        if (result.hasErrors()) {
			return "redirect:/aulas";
		}
        salvarMensagemDoAluno(mensagemDto, principal);

        return "redirect:/aulas";
    }

    private void salvarMensagemDoAluno(MensagemDto mensagemDto, Principal principal) {
        Mensagem mensagem = mensagemDto.toMensagem();
        //mensagem.setDestinatarioId(perfilRepository.findByPermissaoPermissao("Personal").getId());
        mensagem.setDestinatarioId(Long.valueOf(1));
        Perfil perfil = perfilRepository.findByEmail(principal.getName());
        mensagem.setPerfil(perfil);
        perfil.setMensagens(mensagem);
        mensagemRepository.save(mensagem);
    }

}

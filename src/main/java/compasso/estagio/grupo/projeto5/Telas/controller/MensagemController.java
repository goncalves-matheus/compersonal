package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("mensagem")
public class MensagemController {

    @Autowired
    private PerfilRepository repository;
    
    @PostMapping("nova")
    public String novaMensagemAluno(MensagemDto mensagemDto, Model modelo, Principal principal){
        Perfil perfil = repository.findByEmail(principal.getName());
        List<Mensagem> listaDeMensagens = perfil.getMensagens();
        listaDeMensagens.add(mensagemDto.toMensagem());
        perfil.setMensagens(listaDeMensagens);
        return "chat-aluno";
    }
    
}

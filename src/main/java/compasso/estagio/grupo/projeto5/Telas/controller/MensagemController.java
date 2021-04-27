package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
import compasso.estagio.grupo.projeto5.Telas.model.GestorDeMensagens;
import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.MensagemRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("mensagem")
public class MensagemController extends GestorDeMensagens {

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    MensagemRepository mensagemRepository;

    @GetMapping()
    public String chatPersonal(MensagemDto mensagemDto, Model modelo, Principal principal) {
        return "redirect:/mensagem/"+ perfilRepository.findAll().get(1).getEmail();
    }

    @GetMapping("{idAluno}")
    public String carregarMensagensDoAluno(@PathVariable String idAluno, MensagemDto mensagemDto, Model modelo, Principal principal) {
        super.setRepositories(this.perfilRepository, this.mensagemRepository);

        Perfil perfilDoALuno = perfilRepository.findByEmail(idAluno);
        modelo.addAttribute("alunos", listarAlunos(principal));
        carregarMensagensDoChat(modelo, perfilDoALuno);

        return "chat-personal";
    }

    @PostMapping("buscarAluno")
    public String buscarAluno(String buscarAluno, MensagemDto mensagemDto, Model modelo, Principal principal) {
        super.setRepositories(this.perfilRepository, this.mensagemRepository);
        try {
            Perfil primeiroAlunoEncontrado = perfilRepository.findByPrimeiroNome(buscarAluno);
            if(primeiroAlunoEncontrado.getEmail().isEmpty() || buscarAluno.isEmpty()){
                throw new Exception();
            }
            modelo.addAttribute("alunos", perfilRepository.findAllByPrimeiroNome(buscarAluno));
            carregarMensagensDoChat(modelo, primeiroAlunoEncontrado);
            return "chat-personal";
        } catch (Exception e) {
            return "redirect:/mensagem/";     
        }
        
    }


    @PostMapping("novaMensagemPersonal")
    public String novaMensagemDoPersonal(@Valid MensagemDto mensagemDto, String email, Model modelo, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "chat-personal";
        }
        mensagemDto.setIdDestinatario(email);
        Mensagem mensagem = mensagemDto.toMensagem();
        mensagem.setDestinatarioId(perfilRepository.findByEmail(mensagemDto.getIdDestinatario()).getId());
        salvarMensagem(mensagem, principal);
        
        return "redirect:/mensagem/" + email;
    }

    @PostMapping("novaMensagemAluno")
    public String novaMensagemDoAluno(@Valid MensagemDto mensagemDto, Model modelo, BindingResult result, Principal principal) {
        super.setRepositories(this.perfilRepository, this.mensagemRepository);
        if (result.hasErrors()) {
            return "redirect:/aulas";
        }
        Mensagem mensagem = mensagemDto.toMensagem();
        mensagem.setDestinatarioId(perfilRepository.findByPermissaoPermissao("Personal").getId());
        
        salvarMensagem(mensagem, principal);

        Perfil perfil = perfilRepository.findByEmail(principal.getName());
        carregarMensagensDoChat(modelo, perfil);

        return "redirect:/aulas";
    }

}

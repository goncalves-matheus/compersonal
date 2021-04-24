package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import compasso.estagio.grupo.projeto5.Telas.dto.UsuarioDto;
import compasso.estagio.grupo.projeto5.Telas.model.GestorDeMensagens;
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

    GestorDeMensagens gestorDeMensagens = new GestorDeMensagens();

    @GetMapping()
    public String chatPersonal(MensagemDto mensagemDto, Model modelo, Principal principal) {
        return "redirect:/mensagem/"+ perfilRepository.findAll().get(1).getEmail();
    }

    @GetMapping("{idAluno}")
    public String carregarMensagensDeCadaAlunoParaOPersonal(@PathVariable String idAluno, MensagemDto mensagemDto, Model modelo, Principal principal) {
        Perfil perfilDoALuno = perfilRepository.findByEmail(idAluno);

        modelo.addAttribute("alunos", listarAlunos(principal));
        modelo.addAttribute("hora", mensagemRepository.findByPerfilId(perfilRepository.findByEmail(idAluno).getId()).get(1).getHoraFormatada());
        carregarMensagensDoChat(modelo, perfilDoALuno);

        return "chat-personal";
    }

    @PostMapping("novaMensagemPersonal")
    public String novaMensagemPersonal(@Valid MensagemDto mensagemDto, String email, Model modelo, BindingResult result,
            Principal principal) {
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
    public String novaMensagemAluno(@Valid MensagemDto mensagemDto, Model modelo, BindingResult result,
            Principal principal) {
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
    public void salvarMensagem(Mensagem mensagem, Principal principal) {
        Perfil perfil = perfilRepository.findByEmail(principal.getName());
        mensagem.setPerfil(perfil);
        perfil.setMensagens(mensagem);
        mensagemRepository.save(mensagem);
    }

    public void carregarMensagensDoChat(Model modelo, Perfil aluno) {
        Perfil personal = perfilRepository.findByPermissaoPermissao("Personal");

        List<Mensagem> mensagensDoAluno = mensagemRepository.findByPerfilId(aluno.getId());
        List<Mensagem> mensagensDoPersonal = mensagemRepository.findByPerfilIdDestinatarioId(personal.getId(), aluno.getId());
        List<Mensagem> todasAsMensagens = Stream.concat(mensagensDoAluno.stream(), mensagensDoPersonal.stream()).collect(Collectors.toList());

        Collections.sort(todasAsMensagens);

        modelo.addAttribute("perfil", aluno);
        modelo.addAttribute("personal", personal);

        modelo.addAttribute("mensagens", todasAsMensagens);
        modelo.addAttribute("horaFinal", todasAsMensagens.get(todasAsMensagens.size()-1).getHoraFormatada());
    }

    public List<UsuarioDto> listarAlunos(Principal principal) {
        List<Perfil> perfis = perfilRepository.findAll();
        List<Perfil> personais = perfilRepository.findByPermissao(perfilRepository.findByEmail(principal.getName()).getPermissao());
        perfis.removeAll(personais);
        List<UsuarioDto> alunos = UsuarioDto.converte(perfis);
        return alunos;
    }
}

package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
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
    public String chatPersonal(MensagemDto mensagemDto, Model modelo, Principal principal){
        
        return "redirect:/mensagem/"+perfilRepository.findAll().get(1).getEmail();
    } 

    @GetMapping("/{idAluno}")
    public String carregarChatDoAluno(@PathVariable String idAluno, MensagemDto mensagemDto, Model modelo, Principal principal){
        Perfil perfil = perfilRepository.findByEmail(idAluno);
        
        modelo.addAttribute("perfil", perfil);
        modelo.addAttribute("personal", perfilRepository.findByEmail(principal.getName()));

        
		List<UsuarioDto> alunos = getListaDeAlunos(principal);
		modelo.addAttribute("alunos", alunos);
        carregarMensagensDoAlunoParaOPersonal(modelo, perfil);
	
        return "chat-personal";
    }

    @PostMapping("novaMensagemPersonal")
    public String novaMensagemPersonal(@Valid MensagemDto mensagemDto, String email,Model modelo, BindingResult result, Principal principal){
        if (result.hasErrors()) {
			return "chat-personal";
            
		}
        mensagemDto.setIdDestinatario(email);
        System.out.println(mensagemDto.getIdDestinatario());
        salvarMensagemDoPersonal(mensagemDto, principal);

        return "redirect:/mensagem/"+email;
    }


    @PostMapping("nova")
    public String novaMensagemAluno(@Valid MensagemDto mensagemDto, Model modelo, BindingResult result, Principal principal){
        if (result.hasErrors()) {
			return "redirect:/aulas";
		}
        salvarMensagemDoAluno(mensagemDto, principal);
        carregarMensagensDoChat(modelo, principal);

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
    private void salvarMensagemDoPersonal(MensagemDto mensagemDto, Principal principal) {
        Mensagem mensagem = mensagemDto.toMensagemDoPersonal();
        mensagem.setDestinatarioId(perfilRepository.findByEmail(mensagemDto.getIdDestinatario()).getId());
        //mensagem.setDestinatarioId(perfilRepository.findByPermissaoPermissao("Personal").getId());
        Perfil perfil = perfilRepository.findByEmail(principal.getName());
        mensagem.setPerfil(perfil);
        perfil.setMensagens(mensagem);
        mensagemRepository.save(mensagem);
    }

    private List<UsuarioDto> getListaDeAlunos(Principal principal) {
		List<Perfil> perfis = perfilRepository.findAll();
		List<Perfil> personais = perfilRepository.findByPermissao(perfilRepository.findByEmail(principal.getName()).getPermissao());
		perfis.removeAll(personais);
		List<UsuarioDto> estudantes = UsuarioDto.converte(perfis);
		return estudantes;
	}

    private void carregarMensagensDoChat(Model modelo, Principal principal) {
		Perfil perfil = perfilRepository.findByEmail(principal.getName());
		List<Mensagem> mensagensDoAluno = mensagemRepository.findByPerfilId(perfil.getId());
		List<Mensagem> mensagensDoPersonal = mensagemRepository.findByPerfilIdDestinatarioId(Long.valueOf(1), perfil.getId());
		List<Mensagem> todasAsMensagens = Stream.concat(mensagensDoAluno.stream(), mensagensDoPersonal.stream()).collect(Collectors.toList());

		modelo.addAttribute("mensagensDoAluno", mensagensDoAluno);
		modelo.addAttribute("mensagensDoPersonal", mensagensDoPersonal);
		modelo.addAttribute("mensagens", todasAsMensagens);

		Collections.sort(todasAsMensagens);
		for (Mensagem mensagem : todasAsMensagens) {
			System.out.println(mensagem.getTexto());
		}
	}

    private void carregarMensagensDoAlunoParaOPersonal(Model modelo, Perfil perfil) {
		List<Mensagem> mensagensDoAluno = mensagemRepository.findByPerfilId(perfil.getId());
		List<Mensagem> mensagensDoPersonal = mensagemRepository.findByPerfilIdDestinatarioId(Long.valueOf(1), perfil.getId());
		List<Mensagem> todasAsMensagens = Stream.concat(mensagensDoAluno.stream(), mensagensDoPersonal.stream()).collect(Collectors.toList());

		modelo.addAttribute("mensagensDoAluno", mensagensDoAluno);
		modelo.addAttribute("mensagensDoPersonal", mensagensDoPersonal);
		modelo.addAttribute("mensagens", todasAsMensagens);
        /* modelo.addAttribute("horaDaMensagem", attributeValue) */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        modelo.addAttribute("horaFinal", todasAsMensagens.get(todasAsMensagens.size()-1).getDataEHorario().format(formatter));

		Collections.sort(todasAsMensagens);
		/* for (Mensagem mensagem : todasAsMensagens) {
			System.out.println(mensagem.getTexto());
		} */
	}

}

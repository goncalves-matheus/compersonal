package compasso.estagio.grupo.projeto5.Telas.model;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.ui.Model;

import compasso.estagio.grupo.projeto5.Telas.dto.UsuarioDto;
import compasso.estagio.grupo.projeto5.Telas.repository.MensagemRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

public class GestorDeMensagens {

    MensagemRepository mensagemRepository;
    PerfilRepository perfilRepository;

    public void salvarMensagem(Mensagem mensagem, Principal principal) {
        Perfil perfil = perfilRepository.findByEmail(principal.getName());
        mensagem.setPerfil(perfil);
        perfil.setMensagens(mensagem);
        mensagemRepository.save(mensagem);
    }

    public void carregarMensagensDoChat(Model modelo, Perfil aluno) {
        Perfil personal = perfilRepository.findByPermissaoPermissao("Personal");
        try {
            List<Mensagem> mensagensDoAluno = mensagemRepository.findByPerfilId(aluno.getId());
            List<Mensagem> mensagensDoPersonal = mensagemRepository.findByPerfilIdDestinatarioId(personal.getId(), aluno.getId());
            List<Mensagem> todasAsMensagens = Stream.concat(mensagensDoAluno.stream(), mensagensDoPersonal.stream()).collect(Collectors.toList());

            Collections.sort(todasAsMensagens);

            modelo.addAttribute("perfil", aluno);
            modelo.addAttribute("personal", personal);
            modelo.addAttribute("mensagens", todasAsMensagens);
            modelo.addAttribute("horaFinal", todasAsMensagens.get(todasAsMensagens.size()-1).getHoraFormatada());
        } catch (Exception e) {
            modelo.addAttribute("erroMensagem", "O aluno ainda não enviou mensagens");
        }
        
    }

    public List<UsuarioDto> listarAlunos(Principal principal) {
        List<Perfil> perfis = perfilRepository.findAll();
        List<Perfil> personais = perfilRepository.findByPermissao(perfilRepository.findByEmail(principal.getName()).getPermissao());
        perfis.removeAll(personais);
        List<UsuarioDto> alunos = UsuarioDto.converte(perfis);
        return alunos;
    }

    public void setRepositories(PerfilRepository perfilRepository, MensagemRepository mensagemRepository) {
        this.perfilRepository = perfilRepository;
        this.mensagemRepository = mensagemRepository;
    }
    
}

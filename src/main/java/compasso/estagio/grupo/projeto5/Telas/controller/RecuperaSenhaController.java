package compasso.estagio.grupo.projeto5.Telas.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.RecuperaSenhaDto;
import compasso.estagio.grupo.projeto5.Telas.model.Usuario;
import compasso.estagio.grupo.projeto5.Telas.repository.UsuarioRepository;
import compasso.estagio.grupo.projeto5.Telas.security.recuperacao.EnviaEmailService;

@Controller
@RequestMapping("recuperar")
public class RecuperaSenhaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnviaEmailService emailSerive;
    
    private RecuperaSenhaDto recuperaSenhaDtoAuxiliar = new RecuperaSenhaDto();

    @GetMapping
    public String novoCadastro(RecuperaSenhaDto recuperaSenhaDto) {
        return "recuperacao";
    }
     
    @PostMapping("email")
    public String enviarEmail(RecuperaSenhaDto recuperaSenhaDto){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(recuperaSenhaDto.getEmail());
        if(usuario.isPresent()){
            this.recuperaSenhaDtoAuxiliar.setEmail(recuperaSenhaDto.getEmail());
            this.recuperaSenhaDtoAuxiliar.setCodigoEnviadoPorEmail(emailSerive.enviarCodigo(recuperaSenhaDto.getEmail()));
            return "trocar_senha";
        }
        return "recuperacao";
    }

    @PostMapping("codigo")
    public String mudarSenha(@Valid RecuperaSenhaDto recuperaSenhaDto, BindingResult resultado) {
        recuperaSenhaDto.setEmail(this.recuperaSenhaDtoAuxiliar.getEmail());
        recuperaSenhaDto.setCodigoEnviadoPorEmail(this.recuperaSenhaDtoAuxiliar.getCodigoEnviadoPorEmail());
        
        if (resultado.hasErrors() || !recuperaSenhaDto.validarCodigo()) {
            System.out.println(recuperaSenhaDto.getEmail());
            return "recuperacao";
        }

        System.out.println(recuperaSenhaDto.getEmail());
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(recuperaSenhaDto.getEmail());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setSenha(recuperaSenhaDto.getSenha());
            usuarioRepository.save(usuario);
        }
        return "login";
    }
}

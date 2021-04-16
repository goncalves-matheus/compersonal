package compasso.estagio.grupo.projeto5.Telas.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.AuxiliarRecuperaSenhaDto;
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
    
    private AuxiliarRecuperaSenhaDto auxiliarRecuperaSenhaDto;

    @GetMapping
    public String novoCadastro(RecuperaSenhaDto recuperaSenhaDto) {
        return "recuperacao";
    }
     
    @PostMapping("email")
    public String email(RecuperaSenhaDto recuperaSenhaDto){
        String email = recuperaSenhaDto.getEmail();
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if(usuario.isPresent()){
            String codigo = emailSerive.enviarCodigo(email);
            this.auxiliarRecuperaSenhaDto = new AuxiliarRecuperaSenhaDto(email, codigo);
            return "trocar_senha";
        }
        return "recuperacao";
    }

    @PostMapping("codigo")
    public String mudarSenha(@Valid RecuperaSenhaDto recuperaSenhaDto, BindingResult resultado) {
        
        recuperaSenhaDto = this.auxiliarRecuperaSenhaDto.atualizarDto(recuperaSenhaDto);
        
        if (resultado.hasErrors() || !recuperaSenhaDto.validarCodigo()) {
            return "recuperacao";
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(recuperaSenhaDto.getEmail());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setSenha(recuperaSenhaDto.getSenha());
            usuarioRepository.save(usuario);
        }
        return "login";
    }
}

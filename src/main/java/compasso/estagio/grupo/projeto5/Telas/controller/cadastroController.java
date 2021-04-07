package compasso.estagio.grupo.projeto5.Telas.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.UsuarioDto;

@Controller
@RequestMapping("cadastro")
public class cadastroController {
    @GetMapping
    public String novoCadastro(UsuarioDto usuarioDto) {
        return "cadastro";
    }

    @PostMapping("/novo")
    public String novo(@Valid UsuarioDto usuarioDto, BindingResult result) {
        if (result.hasErrors()) {
            return "cadastro";
        }
        //salva o usuário
        return "index";
    }

}

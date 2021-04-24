package compasso.estagio.grupo.projeto5.Telas.model;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import compasso.estagio.grupo.projeto5.Telas.dto.UsuarioDto;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

public class ListaDeAlunos {
    
    @Autowired
	private PerfilRepository repository;

    public List<UsuarioDto> getListaDeAlunos(Principal principal) {
		List<Perfil> perfis = repository.findAll();
		List<Perfil> personais = repository.findByPermissao(repository.findByEmail(principal.getName()).getPermissao());
		perfis.removeAll(personais);
		List<UsuarioDto> estudantes = UsuarioDto.converte(perfis);
		return estudantes;
	}
}

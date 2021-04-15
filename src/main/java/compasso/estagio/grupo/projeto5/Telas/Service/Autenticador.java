package compasso.estagio.grupo.projeto5.Telas.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import compasso.estagio.grupo.projeto5.Telas.model.Usuario;
import compasso.estagio.grupo.projeto5.Telas.repository.UsuarioRepository;

@Service
@Transactional
public class Autenticador implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuario;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = usuario.findByEmail(username);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UsernameNotFoundException("Usuário não encontrado");
	}
	
	

}

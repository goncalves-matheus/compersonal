package compasso.estagio.grupo.projeto5.Telas.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.model.Permissao;

@Repository
@Transactional
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	Perfil findByEmail(String username);

	List<Perfil> findByPermissao(Permissao permissao);

}

package compasso.estagio.grupo.projeto5.Telas.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;

@Repository
@Transactional
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	Perfil findByEmail(String username);

    @Query("SELECT p FROM Perfil p WHERE p.email != :emailPersonal")
    List<Perfil> findByNotEmailPersonal(String emailPersonal);

}

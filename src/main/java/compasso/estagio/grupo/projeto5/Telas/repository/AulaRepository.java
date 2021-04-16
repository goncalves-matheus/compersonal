package compasso.estagio.grupo.projeto5.Telas.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.Tipo;

public interface AulaRepository extends JpaRepository<Aula, Long> {

	Page<Aula> findAll(Pageable page);

	List<Aula> findByTipo(Tipo gluteo);

	@Query("select a from Aula a where tipo = :tipo and id = :id")
	Aula findByAula(@Param("id")Long id,@Param("tipo") Tipo tipo);

	Aula findByTitulo(String titulo);

}

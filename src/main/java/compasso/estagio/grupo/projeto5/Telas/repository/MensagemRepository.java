package compasso.estagio.grupo.projeto5.Telas.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;

@Repository
@Transactional
public interface MensagemRepository extends JpaRepository<Mensagem, Long>{
	
    List<Mensagem> findByPerfilId(Long id);

    @Query("select m from Mensagem m where destinatario_id = :destinatario_id and perfil_id = :perfil_id")
	List<Mensagem> findByPerfilIdDestinatarioId(@Param("perfil_id")Long perfil_id, @Param("destinatario_id")Long destinatario_id);
}

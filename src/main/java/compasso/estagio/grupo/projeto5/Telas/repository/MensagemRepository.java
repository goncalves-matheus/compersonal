package compasso.estagio.grupo.projeto5.Telas.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;

@Repository
@Transactional
public interface MensagemRepository extends JpaRepository<Mensagem, Long>{
    
}

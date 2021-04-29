package compasso.estagio.grupo.projeto5.Telas.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import compasso.estagio.grupo.projeto5.Telas.model.Plano;

@Repository
@Transactional
public interface PlanosRepository extends JpaRepository<Plano, Long> {
    ArrayList<Plano> findAll();
}

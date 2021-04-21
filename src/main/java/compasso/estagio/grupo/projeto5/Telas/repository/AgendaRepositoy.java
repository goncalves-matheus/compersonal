package compasso.estagio.grupo.projeto5.Telas.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import compasso.estagio.grupo.projeto5.Telas.model.Agenda;

@Repository
@Transactional
public interface AgendaRepositoy extends JpaRepository<Agenda, Long> {
	
	ArrayList<Agenda> findAll();

	Agenda findByStart(String start);

}

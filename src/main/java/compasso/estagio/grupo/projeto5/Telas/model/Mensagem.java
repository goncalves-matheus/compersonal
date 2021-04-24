package compasso.estagio.grupo.projeto5.Telas.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Mensagem implements Comparable<Mensagem> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String texto;

	public LocalDateTime dataEHorario = LocalDateTime.now();

    @Transient
    public String horaFormatada = this.dataEHorario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

    public Long destinatarioId;

	@ManyToOne
	private Perfil perfil;

	public Mensagem() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getDataEHorario() {
		return dataEHorario;
	}

	public void setDataEHorario(LocalDateTime dataEHorario) {
		this.dataEHorario = dataEHorario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Long getDestinatarioId() {
		return destinatarioId;
	}

	public void setDestinatarioId(Long destinatarioId) {
		this.destinatarioId = destinatarioId;
	}

    public String getHoraFormatada(){
        return this.dataEHorario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public int compareTo(Mensagem outraMensagem) {
        return this.dataEHorario.compareTo(outraMensagem.getDataEHorario());
    }

}

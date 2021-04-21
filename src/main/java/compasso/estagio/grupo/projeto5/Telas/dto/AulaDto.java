package compasso.estagio.grupo.projeto5.Telas.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;

public class AulaDto {

	@NotBlank
	private String titulo;

	@NotBlank
	private String video;

	@NotBlank
	@Length(min = 10, max = 500)
	private String descricao;

	@NotBlank
	private String pdf;

	@NotBlank
	private String tipo;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Aula toAula() {
		Aula aula = new Aula();
		aula.setTitulo(this.titulo);
		aula.setVideo(this.video);
		aula.setDescricao(this.descricao);
		aula.setPdf(this.pdf);
		return aula;
	}

}

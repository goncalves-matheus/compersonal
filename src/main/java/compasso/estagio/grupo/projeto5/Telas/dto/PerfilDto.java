package compasso.estagio.grupo.projeto5.Telas.dto;

import javax.validation.constraints.NotBlank;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;

public class PerfilDto {

	@NotBlank(message = "O nome precisa ser preenchido")
	private String primeiroNome;

	@NotBlank(message = "O nome precisa ser preenchido")
	private String ultimoNome;

	private String foto;

	public PerfilDto toPerfilDto(Perfil p) {
		this.primeiroNome = p.getPrimeiroNome();
		this.ultimoNome = p.getUltimoNome();
		this.foto = "https://compersonal-bucket.s3.amazonaws.com/"+ p.getFoto();
		return this;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

}

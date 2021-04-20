package compasso.estagio.grupo.projeto5.Telas.dto;

import javax.validation.constraints.NotBlank;

import compasso.estagio.grupo.projeto5.Telas.model.Perfil;

public class PerfilDto {

	@NotBlank(message = "O nome precisa ser preenchido")
	private String primeiroNome;

	@NotBlank(message = "O nome precisa ser preenchido")
	private String ultimoNome;
	
	public PerfilDto toPerfilDto(Perfil p) {
		this.primeiroNome = p.getPrimeiroNome();
		this.ultimoNome = p.getUltimoNome();
		return this;
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

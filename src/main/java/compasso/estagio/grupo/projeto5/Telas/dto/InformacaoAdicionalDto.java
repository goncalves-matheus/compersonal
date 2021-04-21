package compasso.estagio.grupo.projeto5.Telas.dto;

import javax.validation.constraints.NotBlank;

import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.model.Sexo;

public class InformacaoAdicionalDto {
	
	private String altura;
	private String peso;
	private String sexo;
	private String problemaDeSaude;
	
	public InformacaoAdicionalDto toInformacaoAdicionalDto(Perfil p) {
		this.altura = p.getAltura();
		this.peso = p.getPeso();
		this.sexo = p.getSexo();
		this.problemaDeSaude = p.getProblemaDeSaude();
		
		return this;
	}
	
	public String getAltura() {
		return altura;
	}
	public void setAltura(String altura) {
		this.altura = altura;
	}
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getProblemaDeSaude() {
		return problemaDeSaude;
	}
	public void setProblemaDeSaude(String problemaDeSaude) {
		this.problemaDeSaude = problemaDeSaude;
	}

}

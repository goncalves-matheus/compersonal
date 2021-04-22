package compasso.estagio.grupo.projeto5.Telas.dto;

import compasso.estagio.grupo.projeto5.Telas.model.Informacoes;

public class InformacaoAdicionalDto {

	private String altura;
	private String peso;
	private String genero;
	private String problemaDeSaude;

	public InformacaoAdicionalDto toInformacaoAdicionalDto(Informacoes p) {
		this.altura = p.getAltura();
		this.peso = p.getPeso();
		this.genero = p.getGenero();
		this.problemaDeSaude = p.getProblemaDeSaude();

		return this;
	}

	public Informacoes toInformacoes(Informacoes p) {
		p.setAltura(this.altura);
		p.setPeso(this.peso);
		p.setGenero(this.genero);
		p.setProblemaDeSaude(this.problemaDeSaude);

		return p;
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getProblemaDeSaude() {
		return problemaDeSaude;
	}

	public void setProblemaDeSaude(String problemaDeSaude) {
		this.problemaDeSaude = problemaDeSaude;
	}

}

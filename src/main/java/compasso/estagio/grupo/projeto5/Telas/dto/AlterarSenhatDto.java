package compasso.estagio.grupo.projeto5.Telas.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import compasso.estagio.grupo.projeto5.Telas.security.verificacao.CamposIguais;

@CamposIguais(primeiroCampo = "senha", segundoCampo = "confirmacaoSenha", message = "Senha e confirmação precisam ser iguais")
public class AlterarSenhatDto {
	@NotNull
    @Size(min = 8, max = 30, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String senha;

    @NotNull
    @Size(min = 8, max = 30, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String confirmacaoSenha;
    
    @NotNull
    @Size(min = 8, max = 30, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String senhaAntiga;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}
    
}

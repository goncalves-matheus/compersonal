package compasso.estagio.grupo.projeto5.Telas.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import compasso.estagio.grupo.projeto5.Telas.security.verificacao.CamposIguais;

@CamposIguais(primeiroCampo = "senhaNova", segundoCampo = "confirmacaoSenha", message = "Senha e confirmação precisam ser iguais")
public class AlterarSenhaDto {
	@NotNull
    @Size(min = 8, max = 30, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String senha;

    @NotNull
    @Size(min = 8, max = 30, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String confirmacaoSenha;
    
    @NotNull
    @Size(min = 8, max = 30, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String senhaNova;

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

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}
    
}

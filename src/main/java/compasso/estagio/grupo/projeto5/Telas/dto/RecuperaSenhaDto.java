package compasso.estagio.grupo.projeto5.Telas.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import compasso.estagio.grupo.projeto5.Telas.security.verificacao.CamposIguais;

@CamposIguais(primeiroCampo = "senha", segundoCampo = "confirmacaoSenha", message = "Senha e confirmação precisam ser iguais")

public class RecuperaSenhaDto {
    /* @NotBlank(message = "O email precisa ser preenchido!")
    @Email(message = "Email inválido!") */
    private String email;
    @NotNull
    private String codigoDigitado;

    @NotNull
    @Size(min = 8, max = 30, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String senha;

    @NotNull
    @Size(min = 8, max = 30, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String confirmacaoSenha;

    private String codigoEnviadoPorEmail = null;

    public boolean validarCodigo() {
        if(this.codigoDigitado.equals(codigoEnviadoPorEmail))
            return true;
        return false;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getCodigoDigitado() {
        return codigoDigitado;
    }


    public void setCodigoDigitado(String codigoDigitado) {
        this.codigoDigitado = codigoDigitado;
    }


    public String getCodigoEnviadoPorEmail() {
        return codigoEnviadoPorEmail;
    }


    public void setCodigoEnviadoPorEmail(String codigoEnviadoPorEmail) {
        this.codigoEnviadoPorEmail = codigoEnviadoPorEmail;
    }


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

}

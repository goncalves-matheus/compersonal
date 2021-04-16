package compasso.estagio.grupo.projeto5.Telas.dto;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.model.Usuario;

public class UsuarioDto {

	@NotBlank
	private String primeiroNome;

	@NotBlank
	private String ultimoNome;

	@NotNull
	@Size(min = 8, max = 30)
	private String senha;

	@NotNull
	@Size(min = 8, max = 30)
	private String confirmacaoSenha;

	@NotBlank
	@Email
	private String email;

	@Size(min = 11, max = 11)
	private String telefone;

	public UsuarioDto() {}

	public UsuarioDto(Perfil p) {
		this.primeiroNome = p.getPrimeiroNome();
		this.ultimoNome = p.getUltimoNome();
		this.senha = p.getSenha();
		this.confirmacaoSenha = p.getConfirmacaoSenha();
		this.email = p.getEmail();
		this.telefone = p.getTelefone();
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
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

	public Perfil toPerfil() {
		Perfil perfil = new Perfil();
		perfil.setPrimeiroNome(this.primeiroNome);
		perfil.setUltimoNome(this.ultimoNome);
		perfil.setEmail(this.email);
		perfil.setTelefone(this.telefone);

		return perfil;
	}
	
	public Usuario toUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail(this.email);
		usuario.setSenha(this.senha);
		return usuario;
	}

    public static List<UsuarioDto> converte(List<Perfil> perfis) {
        return perfis.stream().map( p -> new UsuarioDto(p)).collect(Collectors.toList());
    }

}

package compasso.estagio.grupo.projeto5.Telas.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String primeiroNome;

	private String ultimoNome;

	private String senha;

	private String email;

	private String telefone;
	
	private String foto;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Informacoes informacao;

	@OneToMany(mappedBy = "perfil", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Mensagem> mensagens = new ArrayList<Mensagem>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "perfil_aulas", joinColumns = { @JoinColumn(name = "perfil_id") }, inverseJoinColumns = {
			@JoinColumn(name = "aulas_id") })
	private List<Aula> aulas = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Permissao permissao;
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(Aula aula) {
		this.aulas.add(aula);
	}

	public Informacoes getInformacao() {
		return informacao;
	}

	public void setInformacao(Informacoes informacao) {
		this.informacao = new Informacoes();
		this.informacao.setAltura(informacao.getAltura());
		this.informacao.setGenero(informacao.getGenero());
		this.informacao.setPeso(informacao.getPeso());
		this.informacao.setProblemaDeSaude(informacao.getProblemaDeSaude());
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(Mensagem mensagem) {
		this.mensagens.add(mensagem);
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

}

package compasso.estagio.grupo.projeto5.Telas.model;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" }) })
public class Plano {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;
	private String valor;
	private LocalDateTime finalDoPlano;
	private String codigo;
	private String status;

	public Plano() {
		this.status=null;
	}

	public Plano(String codigo) {
		String situacao = "";
		ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro();
		try {
			situacao = pagSeguro.getTransacao(codigo);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		this.codigo = codigo;
		this.nome = getNome(situacao);
		this.valor = getValor(situacao);
		this.status = getStatus(situacao);
		this.finalDoPlano = getFinalDoPlano(situacao);
	}

	public Plano(String valor, String nome) {
		this.nome = nome;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public LocalDateTime getFinalDoPlano() {
		return finalDoPlano;
	}

	public void setFinalDoPlano(LocalDateTime finalDoPlano) {
		this.finalDoPlano = finalDoPlano;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String getValor(String result) {
		Pattern pattern = Pattern.compile("<grossAmount>(.*?)</grossAmount>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(result);
		if (matcher.find()) {
			return result = matcher.group(1);
		}
		return null;
	}

	private String getNome(String result) {
		Pattern pattern = Pattern.compile("<description>(.*?)</description>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(result);
		if (matcher.find()) {
			return result = matcher.group(1);
		}
		return null;
	}

	private String getStatus(String result) {
		Pattern pattern = Pattern.compile("<status>(.*?)</status>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(result);
		if (matcher.find()) {
			return result = matcher.group(1);
		}
		return null;
	}

	private LocalDateTime getFinalDoPlano(String result) {
		String situacao = result;
		Pattern pattern = Pattern.compile("<lastEventDate>(.*?)</lastEventDate>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(result);
		if (matcher.find()) {
			result = matcher.group(1);
			String data = result.substring(0, 10);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.parse(data + " 00:00:01", formatter);
			if (getNome(situacao).contains("Mensal")) {
				date = LocalDateTime.now().plusMonths(1);
			} else if (getNome(situacao).contains("Anual")) {
				date = LocalDateTime.now().plusYears(1);
			}
			return date;
		}

		return null;
	}

}

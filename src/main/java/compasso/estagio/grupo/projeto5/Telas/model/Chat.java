package compasso.estagio.grupo.projeto5.Telas.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    private Usuario aluno;

    private Usuario personal;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Mensagem> mensagens = new ArrayList<Mensagem>();

    public Chat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Usuario getPersonal() {
        return personal;
    }

    public void setPersonal(Usuario personal) {
        this.personal = personal;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

}

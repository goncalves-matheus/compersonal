package compasso.estagio.grupo.projeto5.Telas.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Chat {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

    private Usuario aluno;

    private Usuario personal;
    
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Mensagem> mensagens = new ArrayList<Mensagem>();

    public Chat() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

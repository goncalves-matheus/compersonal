package compasso.estagio.grupo.projeto5.Telas.model;

import java.time.LocalDateTime;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class Plano {
    private Long id;
    private String nome;
    private String valor; // Decimal, com duas casas decimais separadas por ponto (p.e., 1234.56)
    private LocalDateTime finalDoPlano;
    private String descricao;
    

    public Plano(Long id, String nome, String valor, LocalDateTime finalDoPlano, String descricao) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.finalDoPlano = finalDoPlano;
        this.descricao = descricao;
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
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}

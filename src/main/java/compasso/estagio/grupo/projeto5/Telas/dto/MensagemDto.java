package compasso.estagio.grupo.projeto5.Telas.dto;

import java.time.LocalDateTime;

import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;

public class MensagemDto {
    public String mensagem;
    public LocalDateTime datetime = LocalDateTime.now();

    public String getMensagem() {
        return mensagem;
    }

    public Mensagem toMensagem(){
        Mensagem mensagem = new Mensagem();
        mensagem.setDataEHorario(datetime);
        mensagem.setTexto(this.mensagem);
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    
}

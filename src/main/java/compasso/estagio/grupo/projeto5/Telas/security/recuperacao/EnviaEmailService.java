package compasso.estagio.grupo.projeto5.Telas.security.recuperacao;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

//java mail sender
@Component
public class EnviaEmailService {

    @Autowired
    private JavaMailSender emailSender;

    public String enviarCodigo(String emailDestino) {

        String codigo = String.valueOf(new Random().nextInt(899999) + 100000);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("plataforma.liberais@gmail.com");
        message.setTo(emailDestino);
        message.setSubject("Recuperação de Senha");
        message.setText("Seu código de segurança é: " + codigo);
        emailSender.send(message);

        return codigo;
    }

}

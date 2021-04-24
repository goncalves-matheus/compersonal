package compasso.estagio.grupo.projeto5.Telas.model;

import org.springframework.beans.factory.annotation.Autowired;

import compasso.estagio.grupo.projeto5.Telas.repository.MensagemRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

public class GestorDeMensagens {
    
    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    MensagemRepository mensagemRepository;
    
    
}

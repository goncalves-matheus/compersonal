package compasso.estagio.grupo.projeto5.Telas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.model.ConexaoPagSeguro;


@Controller
@RequestMapping("planos")
public class PlanosController {
    
    @GetMapping
    public String planos() {
        return "pagar";
    }
    

    @GetMapping("pagamento")
    public String pagar(){
        ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro();
        String codigoDePagamento = pagSeguro.testar();
        if(!codigoDePagamento.isEmpty()) {
            return "redirect:https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html?code="+codigoDePagamento;
        }
        return "pagar";
    }

}
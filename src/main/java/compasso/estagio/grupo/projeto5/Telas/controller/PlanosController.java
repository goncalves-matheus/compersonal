package compasso.estagio.grupo.projeto5.Telas.controller;

import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import compasso.estagio.grupo.projeto5.Telas.model.ConexaoPagSeguro;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.model.Plano;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;


@Controller
@RequestMapping("planos")
public class PlanosController {
    
    @Autowired
    PerfilRepository repository;

    @GetMapping
    public String planos() {
        return "pagar";
    }

    @ResponseBody
    @RequestMapping(value = "/pagseguro-notificacao", method = RequestMethod.POST)
    public String registrarNogificacao(){
        String codigo = "";
        ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro();
        try {
            System.out.println(pagSeguro.getStatus(codigo));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println();
        return
        //return "redirect:/";

    }
    
   /*  @GetMapping("/1")
    public String nogificacao(){
        String codigo = "67DE4C45-FE5C-4CA9-B495-F5DD77A877DF";
        ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro();
        try {
            System.out.println(pagSeguro.getStatus(codigo));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println();
        return "redirect:/";
    } */
    
    
    @GetMapping("pagamento/{escolha}")
    public String pagar(@PathVariable (name = "escolha")int escolha, Principal principal){
        if(escolha == 1){
            return "redirect:/";
        }
        Perfil perfil = repository.findByEmail(principal.getName());
        String codigoDePagamento = "";
        if(escolha == 2){
            Plano plano = new Plano(Long.valueOf(1), "mensal", "50.00", LocalDateTime.now().plusDays(30), "Plano Mensal");
            ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro(perfil, plano);
            codigoDePagamento = pagSeguro.gerarCodigoDeCompra();
        }
        else{
            Plano plano = new Plano(Long.valueOf(2), "anual", "550.00", LocalDateTime.now().plusYears(1), "Plano Anual");
            ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro(perfil, plano);
            codigoDePagamento = pagSeguro.gerarCodigoDeCompra();
        }
        
        if(!codigoDePagamento.isEmpty()) {
            System.out.println(codigoDePagamento);
            //return "redirect:https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html?code="+codigoDePagamento;
            return "redirect:https://pagseguro.uol.com.br/v2/checkout/payment.html?code="+codigoDePagamento;
        }
        return "pagar";
    }

}
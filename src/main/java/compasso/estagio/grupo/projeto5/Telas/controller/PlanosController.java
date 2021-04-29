package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/pagamento")
	public String plano(Model modelo, Principal principal) {	
		
		modelo.addAttribute("plano", repository.findByEmail(principal.getName()).getPlano());
		return "pagar";
	}

	@GetMapping("pagamento/{escolha}")
	public String pagar(@PathVariable(name = "escolha") int escolha, Principal principal) {
		
		Perfil perfil = repository.findByEmail(principal.getName());
		String codigoDePagamento = "";
		Plano plano = null;
		
		switch (escolha) {
		case 1:
			perfil.setPlano(perfil.getPlano().Teste7Dias());
			repository.save(perfil);
			return "redirect:/";
			
		case 2:
			plano = new Plano("50.00", "Plano Mensal");
			break;
			
		case 3:
			plano = new Plano("550.00", "Plano Anual");
			break;
		}
		
		ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro(perfil, plano);
		codigoDePagamento = pagSeguro.gerarCodigoDeCompra();
		
		if (!codigoDePagamento.isEmpty()) {
			//return "redirect:https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html?code="+codigoDePagamento;
			return "redirect:https://pagseguro.uol.com.br/v2/checkout/payment.html?code=" + codigoDePagamento;
		}
		return "pagar";
	}
	
	@GetMapping("/meuPlano")
	public String meuPlano(Model modelo, Principal principal) {
		
		Perfil perfil = repository.findByEmail(principal.getName());
		
		if(perfil.getPlano().getNome()==null) {
			return "redirect:/planos/pagamento";
		}
		
		perfil.setPlano(perfil.getPlano());
		repository.save(perfil);
		
		switch (perfil.getPlano().getStatus()) {
		case "1":
			modelo.addAttribute("situacao", "Aguardando pagamento");
			break;
		case "3":
			modelo.addAttribute("situacao", "Pago");
			break;
		case "7":
			modelo.addAttribute("situacao", "Pagamento cancelado");
			break;
		case "8":
			modelo.addAttribute("situacao", "Sem plano");
			break;
		}
		
		String data = perfil.getPlano().getFinalDoPlano().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString();
		modelo.addAttribute("dataFinal", data);
		modelo.addAttribute("perfil", perfil);
		modelo.addAttribute("plano", perfil.getPlano());
		
		return "meuPlano";
	}

}
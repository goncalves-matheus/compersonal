package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@PostMapping(value = "/pagamentoNotificacao", headers = {
			"Access-Control-Allow-Origin : https://sandbox.pagseguro.uol.com.br", "Content-Type:application/x-www-form-urlencoded" })
	public String registrarNogificacao(@RequestParam("notificationCode") String code,
			@RequestParam("notificationType") String type, @RequestHeader HttpResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "https://sandbox.pagseguro.uol.com.br");
		System.out.println(code + type);
		return "hello";
		// return "redirect:/";

	}

	@GetMapping("pagamento/{escolha}")
	public String pagar(@PathVariable(name = "escolha") int escolha, Principal principal) {
		if (escolha == 1) {
			return "redirect:/";
		}
		Perfil perfil = repository.findByEmail(principal.getName());
		String codigoDePagamento = "";
		if (escolha == 2) {
			Plano plano = new Plano("50.00", "Plano Mensal");
			ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro(perfil, plano);
			codigoDePagamento = pagSeguro.gerarCodigoDeCompra();
		} else {
			Plano plano = new Plano("550.00", "Plano Anual");
			ConexaoPagSeguro pagSeguro = new ConexaoPagSeguro(perfil, plano);
			codigoDePagamento = pagSeguro.gerarCodigoDeCompra();
		}

		if (!codigoDePagamento.isEmpty()) {
			System.out.println(codigoDePagamento);
			// return "redirect:https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html?code="+codigoDePagamento;
			return "redirect:https://pagseguro.uol.com.br/v2/checkout/payment.html?code=" + codigoDePagamento;
		}
		return "pagar";
	}

}
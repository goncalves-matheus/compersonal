package compasso.estagio.grupo.projeto5.Telas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/recuperar")
	public String recupera() {
		return "recuperacao";
	}

	@RequestMapping("/pagamento")
	public String pagamento() {
		return "pagar";
	}

}

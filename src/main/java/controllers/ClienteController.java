
package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.Cliente;
import forms.ClienteForm;
import services.ClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController extends AbstractController {

	@Autowired
	private ClienteService	clienteService;
	private final Logger	logger	= LoggerFactory.getLogger(this.getClass());


	@RequestMapping(value = "/registro", method = RequestMethod.GET)
	public ModelAndView registro() {
		ModelAndView result;
		result = new ModelAndView("cliente/registro");

		result.addObject("clienteForm", new ClienteForm());

		return result;
	}

	@RequestMapping(value = "/registroCliente", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView registroCliente(@RequestBody final ClienteForm clienteForm) {
		Cliente cliente = new Cliente();
		cliente = this.clienteService.create(clienteForm);
		ModelAndView result = null;
		try {
			cliente = this.clienteService.save(cliente);
			result = new ModelAndView("welcome/index");
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha registrado correctamente.");

		} catch (final DataIntegrityViolationException e) {
			result = new ModelAndView("cliente/registro");
			result.addObject("error", true);
			result.addObject("clienteForm", clienteForm);
			result.addObject("mensaje", "Ya existe ese nombre de usuario.");
			this.logger.error(e.getMessage());
		}

		return result;
	}
}

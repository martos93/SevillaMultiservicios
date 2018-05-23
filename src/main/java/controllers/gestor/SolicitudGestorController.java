
package controllers.gestor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Cliente;
import forms.SolicitudForm;
import security.LoginService;
import services.ActorService;
import services.ClienteService;
import services.SolicitudService;
import services.TipoTrabajoService;

@Controller
@RequestMapping("/gestor/solicitud")
public class SolicitudGestorController extends AbstractController {

	private final Logger		logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private SolicitudService	solicitudService;

	@Autowired
	private TipoTrabajoService	tipoTrabajoService;


	@RequestMapping(value = "/verSolicitudes", method = RequestMethod.GET)
	public @ResponseBody ModelAndView verSolicitudes() {
		ModelAndView result;
		result = new ModelAndView("solicitud/verSolicitudes");
		this.actorService.checkGestor();
		final Cliente cliente = this.clienteService.findByPrincipal(LoginService.getPrincipal().getId());
		result.addObject("solicitudes", this.solicitudService.findAll());
		result.addObject("tiposTrabajo", this.tipoTrabajoService.findAll());
		result.addObject("solicitudForm", new SolicitudForm());
		result.addObject("cliente", cliente);
		return result;
	}
}

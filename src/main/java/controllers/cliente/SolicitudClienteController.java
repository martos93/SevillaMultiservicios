
package controllers.cliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Cliente;
import domain.Solicitud;
import forms.PresupuestoForm;
import forms.SolicitudForm;
import security.LoginService;
import services.ActorService;
import services.ClienteService;
import services.SolicitudService;
import services.TipoTrabajoService;

@Controller
@RequestMapping("/cliente/solicitud")
public class SolicitudClienteController extends AbstractController {

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
		this.actorService.checkCliente();
		final Cliente cliente = this.clienteService.findByPrincipal(LoginService.getPrincipal().getId());
		final List<Solicitud> solicitudes = (List<Solicitud>) cliente.getSolicitudes();
		result.addObject("solicitudes", solicitudes);
		result.addObject("tiposTrabajo", this.tipoTrabajoService.findAll());
		result.addObject("solicitudForm", new SolicitudForm());
		result.addObject("presupuestoForm", new PresupuestoForm());
		final ArrayList<Solicitud> sol = this.solicitudService.solicitudesSinLeerCliente();
		for (final Solicitud s : sol) {
			s.setLeidoCliente(true);
			this.solicitudService.save(s);
		}

		return result;
	}

	@RequestMapping(value = "/crearSolicitud", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView crearTarea(@RequestBody final SolicitudForm solicitudForm) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			final Cliente cliente = this.clienteService.findByPrincipal(LoginService.getPrincipal().getId());

			Solicitud s = new Solicitud();
			s.setDescripcion(solicitudForm.getDescripcion());
			s.setTitulo(solicitudForm.getTitulo());
			s.setEstado("ENVIADO");
			s.setCantidad(solicitudForm.getCantidad());
			s.setCliente(cliente);
			s.setLeidoCliente(false);
			s.setLeidoGestor(false);
			s.setTipoTrabajo(this.tipoTrabajoService.findOne(solicitudForm.getTipoTrabajoId()));
			s.setFechaCreacion(new Date(System.currentTimeMillis()));
			s.setLeidoGestor(false);
			s.setLeidoCliente(true);
			s = this.solicitudService.save(s);
			result = this.crearVistaPadre();
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	public ModelAndView crearVistaPadre() {
		ModelAndView result;
		result = new ModelAndView("solicitud/verSolicitudes");
		this.actorService.checkCliente();
		final Cliente cliente = this.clienteService.findByPrincipal(LoginService.getPrincipal().getId());
		final List<Solicitud> solicitudes = (List<Solicitud>) cliente.getSolicitudes();
		result.addObject("solicitudes", solicitudes);
		result.addObject("tiposTrabajo", this.tipoTrabajoService.findAll());
		result.addObject("solicitudForm", new SolicitudForm());
		result.addObject("presupuestoForm", new PresupuestoForm());

		return result;
	}
}

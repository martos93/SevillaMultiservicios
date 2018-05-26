
package controllers.gestor;

import java.util.ArrayList;

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
import domain.Solicitud;
import forms.PresupuestoForm;
import forms.SolicitudForm;
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
		result.addObject("solicitudes", this.solicitudService.findAllOrderFecha());
		result.addObject("tiposTrabajo", this.tipoTrabajoService.findAll());
		result.addObject("solicitudForm", new SolicitudForm());
		result.addObject("presupuestoForm", new PresupuestoForm());
		final ArrayList<Solicitud> solicitudes = this.solicitudService.solicitudesSinLeerGestor();
		for (final Solicitud s : solicitudes) {
			s.setLeidoGestor(true);
			this.solicitudService.save(s);
		}
		return result;
	}

	@RequestMapping(value = "/rechazarSolicitud", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView rechazarSolicitud(@RequestBody final SolicitudForm solicitudForm) {
		Solicitud sol = this.solicitudService.findOne(solicitudForm.getId());
		final ModelAndView result = new ModelAndView("solicitud/verSolicitudes");
		try {
			this.actorService.checkGestor();

			sol.setMotivoRechazo(solicitudForm.getMotivoRechazo());
			sol.setLeidoCliente(false);
			sol.setEstado("RECHAZADO");
			sol = this.solicitudService.save(sol);
			result.addObject("solicitudes", this.solicitudService.findAllOrderFecha());
			result.addObject("tiposTrabajo", this.tipoTrabajoService.findAll());
			result.addObject("solicitudForm", new SolicitudForm());
			result.addObject("presupuestoForm", new PresupuestoForm());
			final ArrayList<Solicitud> solicitudes = this.solicitudService.solicitudesSinLeerGestor();
			for (final Solicitud s : solicitudes) {
				s.setLeidoGestor(true);
				this.solicitudService.save(s);
			}
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return result;
	}
}

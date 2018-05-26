
package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.Cliente;
import domain.Presupuesto;
import domain.Solicitud;
import domain.TipoTrabajo;
import forms.ConceptoForm;
import forms.PresupuestoForm;
import forms.TareaForm;
import services.ActorService;
import services.ClienteService;
import services.PresupuestoService;
import services.SolicitudService;
import services.TipoTrabajoService;
import utilities.OperacionesPresupuesto;

@Controller
@RequestMapping("/presupuesto")
public class PresupuestoController extends AbstractController {

	private final Logger		logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private TipoTrabajoService	tipoTrabajoService;

	@Autowired
	private SolicitudService	solicitudService;


	@RequestMapping(value = "/verPresupuesto", method = RequestMethod.GET)
	public @ResponseBody ModelAndView ver(@RequestParam final int presupuestoId) {
		ModelAndView result = null;

		try {
			this.actorService.checkCliente();
			final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
			final Cliente cliente = this.clienteService.findOne(presupuesto.getCliente().getId());
			final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(presupuesto);
			result = new ModelAndView("presupuesto/verPresupuestoCliente");
			result.addObject("ocultaCabecera", true);
			result.addObject("cliente", cliente);
			result.addObject("presupuestoForm", presupuestoForm);

			final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(presupuesto);
			result.addObject("totalPresupuesto", totalPresupuesto);
			final ConceptoForm conceptoForm = new ConceptoForm();
			conceptoForm.setClienteId(cliente.getId());
			conceptoForm.setPresupuestoId(presupuestoId);
			result.addObject("conceptoForm", conceptoForm);

			final TareaForm tareaForm = new TareaForm();
			tareaForm.setPresupuestoId(presupuestoId);
			result.addObject("tareaForm", tareaForm);
			final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
			result.addObject("tiposTrabajo", tiposTrabajo);
			result.addObject("tipoTrabajoId", presupuesto.getTipoTrabajo().getId());
			result.addObject("observaciones", presupuesto.getObservaciones());
			result.addObject("presupuesto", presupuesto);
		} catch (final Exception e) {
			this.logger.error("Se ha producido un error al mostrar el presupuesto");
		}
		return result;
	}

	@RequestMapping(value = "/aceptarRechazarPresupuesto", method = RequestMethod.GET)
	public @ResponseBody ModelAndView aceptarRechazarPresupuesto(@RequestParam final int presupuestoId, @RequestParam final int valor) {
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		final Solicitud solicitud = presupuesto.getSolicitud();

		final ModelAndView result = null;
		try {
			this.actorService.checkCliente();
			solicitud.setLeidoGestor(false);

			if (valor == 1) {
				presupuesto.setAceptado(true);
				solicitud.setEstado("ACEPTADO_CLIENTE");
				this.solicitudService.save(solicitud);
				presupuesto.setSolicitud(solicitud);
			} else {
				presupuesto.setAceptado(false);
				solicitud.setEstado("RECHAZADO_CLIENTE");
				this.solicitudService.save(solicitud);
				presupuesto.setSolicitud(solicitud);
			}
			this.presupuestoService.save(presupuesto);

		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		this.crearVistaPadre(presupuestoId);
		return result;
	}

	public ModelAndView crearVistaPadre(final int presupuestoId) {
		this.actorService.checkCliente();
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		final Cliente cliente = this.clienteService.findOne(presupuesto.getCliente().getId());
		final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(presupuesto);
		final ModelAndView result = new ModelAndView("presupuesto/verPresupuestoCliente");
		result.addObject("ocultaCabecera", true);
		result.addObject("cliente", cliente);
		result.addObject("presupuestoForm", presupuestoForm);

		final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(presupuesto);
		result.addObject("totalPresupuesto", totalPresupuesto);
		final ConceptoForm conceptoForm = new ConceptoForm();
		conceptoForm.setClienteId(cliente.getId());
		conceptoForm.setPresupuestoId(presupuestoId);
		result.addObject("conceptoForm", conceptoForm);

		final TareaForm tareaForm = new TareaForm();
		tareaForm.setPresupuestoId(presupuestoId);
		result.addObject("tareaForm", tareaForm);
		final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
		result.addObject("tiposTrabajo", tiposTrabajo);
		result.addObject("tipoTrabajoId", presupuesto.getTipoTrabajo().getId());
		result.addObject("observaciones", presupuesto.getObservaciones());
		result.addObject("presupuesto", presupuesto);

		return result;
	}
}

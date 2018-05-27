
package controllers.gestor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Cliente;
import domain.Concepto;
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
@RequestMapping("/gestor/presupuesto")
public class PresupuestoGestorController extends AbstractController {

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


	@RequestMapping(value = "/verPresupuestos", method = RequestMethod.GET)
	public @ResponseBody ModelAndView list(@RequestParam final int clienteId) {
		ModelAndView result;
		this.actorService.checkGestor();
		final Cliente cliente = this.clienteService.findOne(clienteId);
		result = new ModelAndView("presupuesto/verPresupuestos");
		result.addObject("presupuestos", cliente.getPresupuesto());
		result.addObject("requestURI", "gestor/presupuesto/verPresupuestos.do");
		result.addObject("cliente", cliente);
		result.addObject("presupuestoForm", new PresupuestoForm());
		final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
		result.addObject("tiposTrabajo", tiposTrabajo);

		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/crearPresupuesto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView crear(@RequestBody final PresupuestoForm presupuestoForm) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			Solicitud solicitud = null;
			final Cliente cliente = this.clienteService.findOne(presupuestoForm.getClienteId());
			Presupuesto presupuesto = this.presupuestoService.create();
			presupuesto.setCliente(cliente);

			presupuesto.setTitulo(presupuestoForm.getTitulo());
			presupuesto.setCodigo(presupuestoForm.getCodigo());
			presupuesto.setDireccionObra(presupuestoForm.getDireccionObra());
			presupuesto.setLocalidad(presupuestoForm.getLocalidad());
			presupuesto.setProvincia(presupuestoForm.getProvincia());
			presupuesto.setTipoTrabajo(this.tipoTrabajoService.findOne(presupuestoForm.getTipoTrabajo()));
			presupuesto.setCodigoPostal(presupuestoForm.getCodigoPostal());

			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			final String d3 = dateFormat.format(presupuesto.getFechaInicio());
			presupuestoForm.setFechaInicio(new Date(d3));
			presupuestoForm.setFechaInicioS(d3);

			if (presupuestoForm.getSolicitudId() != null) {
				presupuesto.setSolicitudTemporal(presupuestoForm.getSolicitudId());
				solicitud = this.solicitudService.findOne(presupuestoForm.getSolicitudId());
			} else
				presupuesto.setSolicitudTemporal(0);

			presupuesto = this.presupuestoService.save(presupuesto);
			if (solicitud != null) {
				solicitud.setPresupuestoTemporal(presupuesto.getId());
				solicitud.setEstado("PENDIENTE");
				solicitud.setLeidoCliente(false);
				solicitud = this.solicitudService.save(solicitud);

			}

			result = new ModelAndView("presupuesto/modificarPresupuesto");
			result.addObject("ocultaCabecera", true);
			result.addObject("cliente", cliente);
			presupuestoForm.setId(presupuesto.getId());
			result.addObject("presupuestoForm", presupuestoForm);

			final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(presupuesto);
			result.addObject("totalPresupuesto", totalPresupuesto);
			final ConceptoForm conceptoForm = new ConceptoForm();
			conceptoForm.setClienteId(cliente.getId());
			conceptoForm.setPresupuestoId(presupuesto.getId());
			result.addObject("conceptoForm", conceptoForm);
			final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
			result.addObject("tiposTrabajo", tiposTrabajo);
			result.addObject("tipoTrabajoId", presupuesto.getTipoTrabajo().getId());
			final TareaForm tareaForm = new TareaForm();
			tareaForm.setPresupuestoId(presupuesto.getId());
			result.addObject("tareaForm", tareaForm);
			result.addObject("observaciones", presupuesto.getObservaciones());
			result.addObject("presupuesto", presupuesto);
		} catch (final Exception e) {
			this.logger.error("Se ha producido un error al crear el presupuesto");
		}
		return result;
	}

	@RequestMapping(value = "/modificarPresupuesto", method = RequestMethod.GET)
	public @ResponseBody ModelAndView modificar(@RequestParam final int presupuestoId) {
		ModelAndView result = null;

		try {
			this.actorService.checkGestor();
			final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
			final Cliente cliente = this.clienteService.findOne(presupuesto.getCliente().getId());
			final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(presupuesto);
			result = new ModelAndView("presupuesto/modificarPresupuesto");
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
			this.logger.error("Se ha producido un error al modificar el presupuesto");
		}
		return result;
	}

	@RequestMapping(value = "/guardarDatosPresupuesto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView guardarDatos(@RequestBody PresupuestoForm presupuestoForm) {
		ModelAndView result = null;
		final Cliente cliente = this.clienteService.findOne(presupuestoForm.getClienteId());
		Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoForm.getId());

		try {
			this.actorService.checkGestor();

			presupuesto.setTitulo(presupuestoForm.getTitulo());
			presupuesto.setCodigo(presupuestoForm.getCodigo());
			presupuesto.setDireccionObra(presupuestoForm.getDireccionObra());
			presupuesto.setLocalidad(presupuestoForm.getLocalidad());
			presupuesto.setProvincia(presupuestoForm.getProvincia());
			presupuesto.setTipoTrabajo(this.tipoTrabajoService.findOne(presupuestoForm.getTipoTrabajo()));
			presupuesto.setCodigoPostal(presupuestoForm.getCodigoPostal());

			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			final Date d = dateFormat.parse(presupuestoForm.getFechaObraS());
			presupuesto.setFechaObra(d);
			final Date d2 = dateFormat.parse(presupuestoForm.getFechaFinS());
			presupuesto.setFechaFin(d2);
			presupuesto = this.presupuestoService.save(presupuesto);

			result = new ModelAndView("presupuesto/modificarPresupuesto");
			result.addObject("ocultaCabecera", true);
			result.addObject("cliente", cliente);

			presupuestoForm = this.presupuestoService.createForm(presupuesto);
			result.addObject("presupuestoForm", presupuestoForm);
			final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(presupuesto);
			result.addObject("totalPresupuesto", totalPresupuesto);
			final ConceptoForm conceptoForm = new ConceptoForm();
			conceptoForm.setClienteId(cliente.getId());
			conceptoForm.setPresupuestoId(presupuesto.getId());
			result.addObject("conceptoForm", conceptoForm);

			final TareaForm tareaForm = new TareaForm();
			tareaForm.setPresupuestoId(presupuesto.getId());
			result.addObject("tareaForm", tareaForm);
			final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
			result.addObject("tiposTrabajo", tiposTrabajo);
			result.addObject("tipoTrabajoId", presupuesto.getTipoTrabajo().getId());
			result.addObject("observaciones", presupuesto.getObservaciones());
			result.addObject("presupuesto", presupuesto);
		} catch (final Exception e) {
			result = new ModelAndView("presupuesto/modificarPresupuesto");
			result.addObject("ocultaCabecera", true);
			result.addObject("cliente", cliente);

			presupuestoForm = this.presupuestoService.createForm(presupuesto);
			final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
			result.addObject("tiposTrabajo", tiposTrabajo);
			result.addObject("presupuestoForm", presupuestoForm);
			final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(presupuesto);
			result.addObject("totalPresupuesto", totalPresupuesto);
			final ConceptoForm conceptoForm = new ConceptoForm();
			conceptoForm.setClienteId(cliente.getId());
			conceptoForm.setPresupuestoId(presupuesto.getId());
			result.addObject("conceptoForm", conceptoForm);
			result.addObject("tipoTrabajoId", presupuesto.getTipoTrabajo().getId());
			final TareaForm tareaForm = new TareaForm();
			tareaForm.setPresupuestoId(presupuesto.getId());
			result.addObject("tareaForm", tareaForm);
			result.addObject("observaciones", presupuesto.getObservaciones());
			result.addObject("presupuesto", presupuesto);
			this.logger.error("Se ha producido un error al modificar el presupuesto");
		}
		return result;
	}

	@RequestMapping(value = "/guardarObservaciones", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView guardarObservaciones(@RequestBody final PresupuestoForm presupuestoForm) {
		ModelAndView result = null;
		try {

			this.actorService.checkGestor();
			final Presupuesto p = this.presupuestoService.findOne(presupuestoForm.getId());
			String observaciones = p.getObservaciones();
			if (observaciones == null || observaciones.isEmpty()) {
				observaciones = presupuestoForm.getObservaciones();
				p.setObservaciones(observaciones);
			} else {
				observaciones += "," + presupuestoForm.getObservaciones();
				p.setObservaciones(observaciones);
			}

			this.presupuestoService.save(p);

			result = this.crearVistaPadre(p);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha guardado correctamente la observacion.");

		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/eliminarObservacion", method = RequestMethod.GET)
	public @ResponseBody ModelAndView eliminarObservacion(@RequestParam final int indice, @RequestParam final int presupuestoId) {
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			final String observaciones = presupuesto.getObservaciones();
			String observacionesAux = "";
			final List<String> obs = Arrays.asList(observaciones.split(","));
			for (int i = 0; i < obs.size(); i++)
				if (i != indice)
					observacionesAux += obs.get(i) + ",";
			if (!observacionesAux.isEmpty())
				observacionesAux = observacionesAux.substring(0, observacionesAux.length() - 1);
			presupuesto.setObservaciones(observacionesAux);
			this.presupuestoService.save(presupuesto);
			result = this.crearVistaPadre(presupuesto);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha borrado correctamente la observacion");

		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/enviarPresupuesto", method = RequestMethod.GET)
	public @ResponseBody ModelAndView enviarPresupuesto(@RequestParam final int solicitudId, @RequestParam final int presupuestoId) {
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		Solicitud sol = this.solicitudService.findOne(solicitudId);
		ModelAndView result = null;
		try {
			if (presupuesto.getConceptos().isEmpty()) {
				result = this.crearVistaPadre(presupuesto);
				result.addObject("error", true);
				result.addObject("mensaje", "No puede enviar un presupuesto sin conceptos ni tareas.");
				return result;
			}
			for (final Concepto c : presupuesto.getConceptos())
				if (c.getTareas().isEmpty()) {
					result = this.crearVistaPadre(presupuesto);
					result.addObject("error", true);
					result.addObject("mensaje", "No puede enviar un presupuesto con conceptos sin tareas.");
					return result;
				}
			this.actorService.checkGestor();
			sol.setPresupuesto(presupuesto);
			sol.setLeidoCliente(false);
			sol.setEstado("TERMINADO");
			sol = this.solicitudService.save(sol);
			presupuesto.setSolicitud(sol);
			this.presupuestoService.save(presupuesto);
			result = this.crearVistaPadre(presupuesto);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return result;
	}

	public ModelAndView crearVistaPadre(final Presupuesto presupuesto) {
		ModelAndView result = null;
		final Cliente cliente = this.clienteService.findOne(presupuesto.getCliente().getId());
		final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(presupuesto);
		result = new ModelAndView("presupuesto/modificarPresupuesto");
		result.addObject("ocultaCabecera", true);
		result.addObject("cliente", cliente);
		result.addObject("presupuestoForm", presupuestoForm);

		final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(presupuesto);
		result.addObject("totalPresupuesto", totalPresupuesto);
		final ConceptoForm conceptoForm = new ConceptoForm();
		conceptoForm.setClienteId(cliente.getId());
		conceptoForm.setPresupuestoId(presupuestoForm.getId());
		result.addObject("conceptoForm", conceptoForm);

		final TareaForm tareaForm = new TareaForm();
		tareaForm.setPresupuestoId(presupuestoForm.getId());
		result.addObject("tareaForm", tareaForm);
		final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
		result.addObject("tiposTrabajo", tiposTrabajo);
		result.addObject("tipoTrabajoId", presupuesto.getTipoTrabajo().getId());
		result.addObject("observaciones", presupuesto.getObservaciones());
		result.addObject("presupuesto", presupuesto);

		return result;
	}
}

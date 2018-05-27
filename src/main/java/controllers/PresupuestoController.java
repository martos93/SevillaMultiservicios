
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

import domain.Albaran;
import domain.Cliente;
import domain.Concepto;
import domain.Factura;
import domain.Presupuesto;
import domain.Solicitud;
import domain.TipoTrabajo;
import forms.ConceptoForm;
import forms.PresupuestoForm;
import forms.TareaForm;
import services.ActorService;
import services.AlbaranService;
import services.ClienteService;
import services.FacturaService;
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

	@Autowired
	private FacturaService		facturaService;

	@Autowired
	private AlbaranService		albaranService;


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
			result.addObject("factura", presupuesto.getFactura());
			result.addObject("albaran", presupuesto.getAlbaran());
			result.addObject("verPresupuesto", true);

			BigDecimal importeTotalSinIVA = new BigDecimal(0);
			for (final Concepto c : presupuesto.getConceptos())
				if (c.getTotal() != null)
					importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
			for (final Concepto c : presupuesto.getAlbaran().getConceptos())
				if (c.getTotal() != null)
					importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
			importeTotalSinIVA = importeTotalSinIVA.setScale(2, BigDecimal.ROUND_HALF_UP);
			result.addObject("importeTotalSinIVA", importeTotalSinIVA);

		} catch (final Exception e) {
			this.logger.error("Se ha producido un error al mostrar el presupuesto");
		}
		return result;
	}

	@RequestMapping(value = "/aceptarRechazarPresupuesto", method = RequestMethod.GET)
	public @ResponseBody ModelAndView aceptarRechazarPresupuesto(@RequestParam final int presupuestoId, @RequestParam final int valor) {
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		final Solicitud solicitud = presupuesto.getSolicitud();

		ModelAndView result = null;
		try {
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
		result = this.crearVistaPadre(presupuestoId);
		return result;
	}

	@RequestMapping(value = "/crearFactura", method = RequestMethod.GET)
	public @ResponseBody ModelAndView crearFactura(@RequestParam final int presupuestoId) {
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		Factura factura = null;
		ModelAndView result = null;
		try {

			if (presupuesto.getConceptos().isEmpty()) {
				result = this.crearVistaPadreModificar(presupuestoId);

				result.addObject("error", true);
				result.addObject("mensaje", "No puede crear una factura de un presupuesto sin conceptos ni tareas.");
				return result;
			}
			for (final Concepto c : presupuesto.getConceptos())
				if (c.getTareas().isEmpty()) {
					result = this.crearVistaPadreModificar(presupuestoId);

					result.addObject("error", true);
					result.addObject("mensaje", "No puede crear una factura si hay conceptos sin tareas.");
					return result;
				}

			factura = this.facturaService.create(presupuestoId);
			factura = this.facturaService.save(factura);
			presupuesto.setFactura(factura);
			this.presupuestoService.save(presupuesto);
			final Solicitud sol = presupuesto.getSolicitud();
			sol.setLeidoCliente(false);
			this.solicitudService.save(sol);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		result = this.facturaService.crearVistaPadreVerFactura(presupuestoId);
		result.addObject("success", true);
		result.addObject("mensaje", "Se ha creado correctamente la factura.");

		return result;
	}

	@RequestMapping(value = "/crearAlbaran", method = RequestMethod.GET)
	public @ResponseBody ModelAndView crearAlbaran(@RequestParam final int presupuestoId) {
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		Albaran albaran = null;
		ModelAndView result = null;
		try {

			if (presupuesto.getConceptos().isEmpty()) {
				result = this.crearVistaPadreModificar(presupuestoId);

				result.addObject("error", true);
				result.addObject("mensaje", "No puede crear un albarán de un presupuesto sin conceptos ni tareas.");
				return result;
			}
			for (final Concepto c : presupuesto.getConceptos())
				if (c.getTareas().isEmpty()) {
					result = this.crearVistaPadreModificar(presupuestoId);

					result.addObject("error", true);
					result.addObject("mensaje", "No puede crear un albarán si hay conceptos sin tareas.");
					return result;
				}

			albaran = this.albaranService.create(presupuestoId);
			albaran = this.albaranService.save(albaran);
			presupuesto.setAlbaran(albaran);
			this.presupuestoService.save(presupuesto);
			final Solicitud sol = presupuesto.getSolicitud();
			sol.setLeidoCliente(false);
			this.solicitudService.save(sol);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		result = this.albaranService.crearVistaPadreVerAlbaran(presupuestoId);
		result.addObject("success", true);
		result.addObject("mensaje", "Se ha creado correctamente el Albaran.");

		return result;
	}

	public ModelAndView crearVistaPadre(final int presupuestoId) {
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
		result.addObject("factura", presupuesto.getFactura());
		result.addObject("albaran", presupuesto.getAlbaran());

		result.addObject("verPresupuesto", true);

		BigDecimal importeTotalSinIVA = new BigDecimal(0);
		for (final Concepto c : presupuesto.getConceptos())
			if (c.getTotal() != null)
				importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
		for (final Concepto c : presupuesto.getAlbaran().getConceptos())
			if (c.getTotal() != null)
				importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
		importeTotalSinIVA = importeTotalSinIVA.setScale(2, BigDecimal.ROUND_HALF_UP);
		result.addObject("importeTotalSinIVA", importeTotalSinIVA);
		return result;
	}

	public ModelAndView crearVistaPadreModificar(final int presupuestoId) {
		ModelAndView result = null;
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

		return result;
	}
}

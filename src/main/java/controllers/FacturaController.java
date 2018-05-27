
package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import domain.Cliente;
import domain.Concepto;
import domain.Factura;
import domain.IVA;
import domain.Presupuesto;
import domain.Solicitud;
import domain.Tarea;
import domain.TipoTrabajo;
import forms.ConceptoForm;
import forms.PresupuestoForm;
import forms.TareaForm;
import services.ActorService;
import services.ClienteService;
import services.ConceptoService;
import services.FacturaService;
import services.IVAService;
import services.PresupuestoService;
import services.SolicitudService;
import services.TareaService;
import services.TipoTrabajoService;
import utilities.OperacionesPresupuesto;

@Controller
@RequestMapping("/factura")
public class FacturaController extends AbstractController {

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
	private ConceptoService		conceptoService;

	@Autowired
	private TareaService		tareaService;
	@Autowired
	private IVAService			ivaService;


	@RequestMapping(value = "/verFactura", method = RequestMethod.GET)
	public @ResponseBody ModelAndView ver(@RequestParam final int presupuestoId) {
		ModelAndView result = null;
		final Logger logger = LoggerFactory.getLogger(this.getClass());

		try {
			result = this.facturaService.crearVistaPadreVerFactura(presupuestoId);

		} catch (final Exception e) {
			logger.error("Se ha producido un error al mostrar la factura");
		}
		return result;
	}

	@RequestMapping(value = "/nuevoConcepto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView nuevoConcepto(@RequestBody final ConceptoForm conceptoForm) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			final Presupuesto p = this.presupuestoService.findOne(conceptoForm.getPresupuestoId());
			Factura f = p.getFactura();
			Concepto c = new Concepto();
			c.setTitulo(conceptoForm.getTituloC());
			c = this.conceptoService.save(c);
			f.getConceptos().add(c);
			f = this.facturaService.save(f);
			result = this.crearVistaPadre(p.getId());
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/editarConcepto", method = RequestMethod.GET)
	public @ResponseBody ConceptoForm editarConcepto(@RequestParam final int conceptoId) {
		final ConceptoForm conceptoForm = new ConceptoForm();
		Concepto concepto = new Concepto();
		try {
			this.actorService.checkGestor();
			concepto = this.conceptoService.findOne(conceptoId);
			conceptoForm.setTituloC(concepto.getTitulo());
			conceptoForm.setConceptoId(conceptoId);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return conceptoForm;
	}

	@RequestMapping(value = "/eliminarConcepto", method = RequestMethod.GET)
	public ModelAndView borrarConcepto(@RequestParam final int conceptoId, @RequestParam final int presupuestoId) {
		ModelAndView result = new ModelAndView();
		try {

			this.actorService.checkGestor();
			final Concepto concepto = this.conceptoService.findOne(conceptoId);
			final Presupuesto p = this.presupuestoService.findOne(presupuestoId);
			final Factura factura = p.getFactura();

			factura.getConceptos().remove(concepto);
			this.facturaService.save(factura);
			this.conceptoService.delete(concepto);
			result = this.crearVistaPadre(p.getId());

			result.addObject("success", true);
			result.addObject("mensaje", "Se ha borrado correctamente el concepto.");
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/modificarConcepto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView modificarConcepto(@RequestBody final ConceptoForm conceptoForm) {
		final Presupuesto p = this.presupuestoService.findOne(conceptoForm.getPresupuestoId());
		ModelAndView result = null;
		try {
			Concepto c = this.conceptoService.findOne(conceptoForm.getConceptoId());
			c.setTitulo(conceptoForm.getTituloC());
			c = this.conceptoService.save(c);

			result = this.crearVistaPadre(p.getId());
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente el concepto.");

		} catch (final Exception e) {
			result = this.crearVistaPadre(p.getId());
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al modificar el concepto.");
			this.logger.error(e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/nuevaTarea", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView crearTarea(@RequestBody final TareaForm tareaForm) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			final Presupuesto p = this.presupuestoService.findOne(tareaForm.getPresupuestoId());
			Tarea t = new Tarea();
			t.setDescripcion(tareaForm.getDescripcion());
			t.setPrecioUnidad(tareaForm.getPrecioUnidad());
			t.setUnidades(tareaForm.getUnidades());
			t.setSubTotal(tareaForm.getSubTotal());
			t = this.tareaService.save(t);

			Concepto c = this.conceptoService.findOne(tareaForm.getConceptoId());
			c.getTareas().add(t);
			BigDecimal totalConcepto = new BigDecimal(0);
			for (final Tarea ta : c.getTareas())
				totalConcepto = totalConcepto.add(ta.getSubTotal());
			c.setTotal(totalConcepto);
			c = this.conceptoService.save(c);

			result = this.crearVistaPadre(p.getId());
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/eliminarTarea", method = RequestMethod.GET)
	public ModelAndView eliminarTarea(@RequestParam final int tareaId, @RequestParam final int presupuestoId, @RequestParam final int conceptoId) {
		ModelAndView result = new ModelAndView();
		try {

			this.actorService.checkGestor();
			final Tarea tarea = this.tareaService.findOne(tareaId);
			final Presupuesto p = this.presupuestoService.findOne(presupuestoId);
			Concepto concepto = this.conceptoService.findOne(conceptoId);
			concepto.getTareas().remove(tarea);
			concepto = this.conceptoService.save(concepto);
			this.tareaService.delete(tarea);
			result = this.crearVistaPadre(p.getId());
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha borrado correctamente la tarea.");
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/editarTarea", method = RequestMethod.GET)
	public @ResponseBody TareaForm editarTarea(@RequestParam final int tareaId) {
		final TareaForm tareaForm = new TareaForm();
		Tarea tarea = new Tarea();
		try {
			this.actorService.checkGestor();
			tarea = this.tareaService.findOne(tareaId);
			tareaForm.setDescripcion(tarea.getDescripcion());
			tareaForm.setPrecioUnidad(tarea.getPrecioUnidad());
			tareaForm.setSubTotal(tarea.getSubTotal());
			tareaForm.setTareaId(tareaId);
			tareaForm.setUnidades(tarea.getUnidades());
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return tareaForm;
	}

	@RequestMapping(value = "/modificarTarea", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView modificarTarea(@RequestBody final TareaForm tareaForm) {
		final Presupuesto p = this.presupuestoService.findOne(tareaForm.getPresupuestoId());
		ModelAndView result = null;
		try {
			Tarea t = this.tareaService.findOne(tareaForm.getTareaId());
			t.setDescripcion(tareaForm.getDescripcion());
			t.setPrecioUnidad(tareaForm.getPrecioUnidad());
			t.setSubTotal(tareaForm.getSubTotal());
			t.setUnidades(tareaForm.getUnidades());
			t = this.tareaService.save(t);

			result = this.crearVistaPadre(p.getId());
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente la tarea.");

		} catch (final Exception e) {
			result = this.crearVistaPadre(p.getId());
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al modificar la tarea.");
			this.logger.error(e.getMessage());
		}

		return result;
	}

	public ModelAndView crearVistaPadre(final int presupuestoId) {
		ModelAndView result = null;
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
		result.addObject("verPresupuesto", false);
		result.addObject("verFactura", true);

		BigDecimal importeTotalSinIVA = new BigDecimal(0);
		BigDecimal ivaCalculado = new BigDecimal(0);
		BigDecimal importeTotalConIVA = new BigDecimal(0);
		for (final Concepto c : presupuesto.getConceptos())
			if (c.getTotal() != null)
				importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
		for (final Concepto c : presupuesto.getFactura().getConceptos())
			if (c.getTotal() != null)
				importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());

		final IVA iva = ((List<IVA>) this.ivaService.findAll()).get(0);
		ivaCalculado = importeTotalSinIVA.multiply(new BigDecimal(iva.getPorcentaje()).divide(new BigDecimal(100)));
		importeTotalConIVA = importeTotalSinIVA.add(ivaCalculado);

		importeTotalSinIVA = importeTotalSinIVA.setScale(2, BigDecimal.ROUND_HALF_UP);
		importeTotalConIVA = importeTotalConIVA.setScale(2, BigDecimal.ROUND_HALF_UP);
		ivaCalculado = ivaCalculado.setScale(2, BigDecimal.ROUND_HALF_UP);

		result.addObject("importeTotalSinIVA", importeTotalSinIVA);
		result.addObject("importeTotalConIVA", importeTotalConIVA);
		result.addObject("ivaCalculado", ivaCalculado);
		return result;
	}

	@RequestMapping(value = "/terminarFactura", method = RequestMethod.GET)
	public @ResponseBody ModelAndView terminarFactura(@RequestParam final int facturaId) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			Factura factura = this.facturaService.findOne(facturaId);
			for (final Concepto c : factura.getConceptos())
				if (c.getTareas().isEmpty()) {
					result = this.crearVistaPadre(factura.getPresupuesto().getId());
					result.addObject("error", true);
					result.addObject("mensaje", "No puede terminar una factura si hay conceptos sin tareas.");
					return result;
				}
			factura.setTerminado(true);
			factura = this.facturaService.save(factura);
			if (factura.getPresupuesto().getSolicitud() != null) {
				final Solicitud s = factura.getPresupuesto().getSolicitud();
				s.setLeidoCliente(false);
				this.solicitudService.save(s);
			}

			result = this.crearVistaPadre(factura.getPresupuesto().getId());
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha terminado correctamente la factura.");

		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return result;
	}
}

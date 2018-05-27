
package controllers.gestor;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import domain.TipoTrabajo;
import forms.ConceptoForm;
import forms.PresupuestoForm;
import forms.TareaForm;
import services.ActorService;
import services.ClienteService;
import services.ConceptoService;
import services.PresupuestoService;
import services.TareaService;
import services.TipoTrabajoService;
import utilities.OperacionesPresupuesto;

@Controller
@RequestMapping("/gestor/concepto")
public class ConceptoGestorController extends AbstractController {

	private final Logger		logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private ConceptoService		conceptoService;

	@Autowired
	private TareaService		tareaService;

	@Autowired
	private TipoTrabajoService	tipoTrabajoService;


	@RequestMapping(value = "/modificarConcepto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView modificarConcepto(@RequestBody final ConceptoForm conceptoForm) {
		final Presupuesto p = this.presupuestoService.findOne(conceptoForm.getPresupuestoId());
		ModelAndView result = null;
		try {
			Concepto c = this.conceptoService.findOne(conceptoForm.getConceptoId());
			c.setTitulo(conceptoForm.getTituloC());
			c = this.conceptoService.save(c);

			result = this.crearVistaPadre(p);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente el concepto.");

		} catch (final Exception e) {
			result = this.crearVistaPadre(p);
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al modificar el concepto.");
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
			p.getConceptos().remove(concepto);
			this.presupuestoService.save(p);
			this.conceptoService.delete(concepto);
			result = this.crearVistaPadre(p);

			result.addObject("success", true);
			result.addObject("mensaje", "Se ha borrado correctamente el concepto.");
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/nuevoConcepto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView nuevoConcepto(@RequestBody final ConceptoForm conceptoForm) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			Presupuesto p = this.presupuestoService.findOne(conceptoForm.getPresupuestoId());
			Concepto c = new Concepto();
			c.setTitulo(conceptoForm.getTituloC());
			c = this.conceptoService.save(c);
			p.getConceptos().add(c);
			p = this.presupuestoService.save(p);
			result = this.crearVistaPadre(p);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	public ModelAndView crearVistaPadre(final Presupuesto p) {

		final Cliente cliente = this.clienteService.findOne(p.getCliente().getId());
		final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(p);
		final ModelAndView result = new ModelAndView("presupuesto/modificarPresupuesto");
		result.addObject("ocultaCabecera", true);
		result.addObject("cliente", cliente);
		result.addObject("presupuestoForm", presupuestoForm);
		final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(p);
		result.addObject("totalPresupuesto", totalPresupuesto);
		final ConceptoForm conceptoForm = new ConceptoForm();
		conceptoForm.setClienteId(cliente.getId());
		conceptoForm.setPresupuestoId(p.getId());
		result.addObject("conceptoForm", conceptoForm);
		final TareaForm tareaForm = new TareaForm();
		tareaForm.setPresupuestoId(p.getId());
		result.addObject("tareaForm", tareaForm);
		result.addObject("observaciones", p.getObservaciones());
		result.addObject("presupuesto", p);
		final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
		result.addObject("tiposTrabajo", tiposTrabajo);
		result.addObject("tipoTrabajoId", p.getTipoTrabajo().getId());

		return result;
	}

}

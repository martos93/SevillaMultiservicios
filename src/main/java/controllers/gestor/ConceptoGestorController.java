
package controllers.gestor;

import java.math.BigDecimal;

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
import forms.ConceptoForm;
import forms.PresupuestoForm;
import forms.TareaForm;
import services.ActorService;
import services.ClienteService;
import services.ConceptoService;
import services.PresupuestoService;
import services.TareaService;
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


	@RequestMapping(value = "/eliminarConcepto", method = RequestMethod.GET)
	public ModelAndView borrarConcepto(@RequestParam final int conceptoId, @RequestParam final int presupuestoId) {
		ModelAndView result = new ModelAndView();
		try {

			this.actorService.checkGestor();
			final Concepto concepto = this.conceptoService.findOne(conceptoId);
			final Presupuesto p = this.presupuestoService.findOne(presupuestoId);
			//			final ArrayList<Tarea> tareas = (ArrayList<Tarea>) concepto.getTareas();

			//			for (final Tarea t : tareas)
			//				this.tareaService.delete(t);
			//			concepto.setTareas(new ArrayList<Tarea>());
			//			concepto = this.conceptoService.save(concepto);
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
		final ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			Presupuesto p = this.presupuestoService.findOne(conceptoForm.getPresupuestoId());
			Concepto c = new Concepto();
			c.setTitulo(conceptoForm.getTituloC());
			c = this.conceptoService.save(c);
			p.getConceptos().add(c);
			p = this.presupuestoService.save(p);

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
		return result;
	}

}

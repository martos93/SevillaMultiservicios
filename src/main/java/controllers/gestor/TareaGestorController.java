
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
import domain.Tarea;
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
@RequestMapping("/gestor/tarea")
public class TareaGestorController extends AbstractController {

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

			final Cliente cliente = this.clienteService.findOne(p.getCliente().getId());
			final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(p);
			result = new ModelAndView("presupuesto/modificarPresupuesto");
			result.addObject("ocultaCabecera", true);
			result.addObject("cliente", cliente);
			result.addObject("presupuestoForm", presupuestoForm);
			final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(p);
			result.addObject("totalPresupuesto", totalPresupuesto);

			final ConceptoForm conceptoForm = new ConceptoForm();
			conceptoForm.setClienteId(cliente.getId());
			conceptoForm.setPresupuestoId(p.getId());
			result.addObject("conceptoForm", conceptoForm);

			final TareaForm tareaFormNew = new TareaForm();
			tareaFormNew.setPresupuestoId(p.getId());
			result.addObject("tareaForm", tareaFormNew);
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
			result = this.crearVistaPadre(p);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha borrado correctamente la tarea.");
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

			result = this.crearVistaPadre(p);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente la tarea.");

		} catch (final Exception e) {
			result = this.crearVistaPadre(p);
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al modificar la tarea.");
			this.logger.error(e.getMessage());
		}

		return result;
	}

}

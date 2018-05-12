
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
}

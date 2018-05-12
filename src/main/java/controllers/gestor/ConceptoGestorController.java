
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
import forms.ConceptoForm;
import forms.PresupuestoForm;
import services.ActorService;
import services.ClienteService;
import services.ConceptoService;
import services.PresupuestoService;

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


	@RequestMapping(value = "/nuevoConcepto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView list(@RequestBody final ConceptoForm conceptoForm) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			Presupuesto p = this.presupuestoService.findOne(conceptoForm.getPresupuestoId());
			Concepto c = new Concepto();
			c.setTitulo(conceptoForm.getTituloC());
			c = this.conceptoService.save(c);
			p.getConceptos().add(c);
			p = this.presupuestoService.save(p);

			final Cliente cliente = this.clienteService.findOne(conceptoForm.getClienteId());
			final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(p);
			result = new ModelAndView("presupuesto/modificarPresupuesto");
			result.addObject("ocultaCabecera", true);
			result.addObject("cliente", cliente);
			result.addObject("presupuestoForm", presupuestoForm);
			BigDecimal totalPresupuesto = new BigDecimal(0);
			for (final Concepto ac : p.getConceptos())
				totalPresupuesto = totalPresupuesto.add(ac.getTotal());
			result.addObject("totalPresupuesto", totalPresupuesto);
			conceptoForm.setClienteId(cliente.getId());
			conceptoForm.setPresupuestoId(p.getId());
			result.addObject("conceptoForm", conceptoForm);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

}

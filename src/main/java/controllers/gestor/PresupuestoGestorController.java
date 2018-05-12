
package controllers.gestor;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import services.ActorService;
import services.ClienteService;
import services.PresupuestoService;

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


	@RequestMapping(value = "/verPresupuestos", method = RequestMethod.GET)
	public @ResponseBody ModelAndView list(@RequestParam final int clienteId) {
		ModelAndView result;
		this.actorService.checkGestor();
		final Cliente cliente = this.clienteService.findOne(clienteId);
		result = new ModelAndView("presupuesto/verPresupuestos");
		result.addObject("presupuestos", cliente.getPresupuesto());
		result.addObject("requestURI", "gestor/presupuesto/verPresupuestos.do");
		result.addObject("cliente", cliente);
		return result;
	}

	@RequestMapping(value = "/crearPresupuesto", method = RequestMethod.GET)
	public @ResponseBody ModelAndView crear(@RequestParam final int clienteId) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			final Cliente cliente = this.clienteService.findOne(clienteId);
			final Presupuesto presupuesto = this.presupuestoService.create();
			presupuesto.setCliente(cliente);
			this.presupuestoService.save(presupuesto);

			result = new ModelAndView("presupuesto/modificarPresupuesto");
			result.addObject("requestURI", "gestor/presupuesto/verPresupuestos.do");
			result.addObject("cliente", cliente);
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

			BigDecimal totalPresupuesto = new BigDecimal(0);
			for (final Concepto c : presupuesto.getConceptos())
				if (c.getTotal() != null)
					totalPresupuesto = totalPresupuesto.add(c.getTotal());
			result.addObject("totalPresupuesto", totalPresupuesto);
			final ConceptoForm conceptoForm = new ConceptoForm();
			conceptoForm.setClienteId(cliente.getId());
			conceptoForm.setPresupuestoId(presupuestoId);
			result.addObject("conceptoForm", conceptoForm);
		} catch (final Exception e) {
			this.logger.error("Se ha producido un error al crear el presupuesto");
		}
		return result;
	}

}

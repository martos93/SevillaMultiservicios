
package controllers.gestor;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Concepto;
import domain.Gasto;
import domain.Presupuesto;
import forms.CobroForm;
import forms.PresupuestoForm;
import services.ActorService;
import services.ClienteService;
import services.GastoService;
import services.PresupuestoService;

@Controller
@RequestMapping("/gestor/cobro")
public class CobroGestorController extends AbstractController {

	private final Logger		logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private GastoService		gastoService;


	@RequestMapping(value = "/resumenFinanciero", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int presupuestoId) {
		ModelAndView result = null;
		try {
			final Presupuesto p = this.presupuestoService.findOne(presupuestoId);
			final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(p);
			result = new ModelAndView("cobro/resumenFinanciero");
			BigDecimal presupuestado = new BigDecimal(0);
			for (final Concepto g : p.getConceptos())
				presupuestado = presupuestado.add(g.getTotal());
			result.addObject("presupuestado", presupuestado);
			BigDecimal addFactura = new BigDecimal(0);
			if (p.getFactura() != null)
				for (final Concepto c : p.getFactura().getConceptos())
					addFactura = addFactura.add(c.getTotal());
			result.addObject("addFactura", addFactura);
			BigDecimal mo = new BigDecimal(0);
			BigDecimal mat = new BigDecimal(0);
			BigDecimal sub = new BigDecimal(0);
			for (final Gasto g : p.getGastos())
				if (g.getTipo().equals("Mano de obra"))
					mo = mo.add(g.getCantidad());
				else if (g.getTipo().equals("Material"))
					mat = mat.add(g.getCantidad());
				else
					sub = sub.add(g.getCantidad());
			result.addObject("manoObra", mo);
			result.addObject("material", mat);
			result.addObject("subCont", sub);

			BigDecimal margenManiobra = new BigDecimal(0);
			margenManiobra = margenManiobra.add(presupuestado);
			margenManiobra = margenManiobra.add(addFactura);
			margenManiobra = margenManiobra.subtract(mo);
			margenManiobra = margenManiobra.subtract(mat);
			margenManiobra = margenManiobra.subtract(sub);
			result.addObject("margenManiobra", margenManiobra);

			if (margenManiobra.compareTo(new BigDecimal(0)) == -1)
				result.addObject("margenNegativo", true);
			result.addObject("cobros", p.getCobros());

			final CobroForm cobroForm = new CobroForm();
			cobroForm.setPresupuestoId(presupuestoId);
			result.addObject("cobroForm", cobroForm);
			result.addObject("presupuestoId", presupuestoId);
			result.addObject("codigoPresupuesto", p.getCodigo());
			result.addObject("ocultaCabecera", true);
			result.addObject("presupuestoForm", presupuestoForm);
			result.addObject("cliente", this.clienteService.findOne(p.getCliente().getId()));
		} catch (final Exception e) {
			this.logger.error(e.getLocalizedMessage());
		}
		return result;
	}
}
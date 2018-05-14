
package controllers.gestor;

import java.util.Date;

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
import domain.Gasto;
import domain.Presupuesto;
import forms.GastoForm;
import services.ActorService;
import services.ClienteService;
import services.GastoService;
import services.PresupuestoService;

@Controller
@RequestMapping("/gestor/gasto")
public class GastoGestorController extends AbstractController {

	private final Logger		logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private GastoService		gastoService;


	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int presupuestoId) {
		ModelAndView result = null;
		try {
			result = new ModelAndView("gasto/listAll");
			result.addObject("gastos", this.presupuestoService.findOne(presupuestoId).getGastos());
			final GastoForm gastoForm = new GastoForm();
			gastoForm.setPresupuestoId(presupuestoId);
			result.addObject("gastoForm", gastoForm);
			result.addObject("requestURI", "gestor/gasto/listAll.do");
			result.addObject("presupuestoId", presupuestoId);
		} catch (final Exception e) {
			this.logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/nuevoGasto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView nuevoGasto(@RequestBody final GastoForm gastoForm) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			Presupuesto p = this.presupuestoService.findOne(gastoForm.getPresupuestoId());
			Gasto gasto = new Gasto();
			gasto.setCantidad(gastoForm.getCantidad());
			gasto.setConcepto(gastoForm.getConcepto());
			gasto.setFecha(new Date(gastoForm.getFecha()));
			gasto.setObservaciones(gastoForm.getObservaciones());
			gasto.setProveedor(gastoForm.getProveedor());
			gasto = this.gastoService.save(gasto);
			p.getGastos().add(gasto);
			p = this.presupuestoService.save(p);
			result = this.crearVistaPadre(p);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/editarGasto", method = RequestMethod.GET)
	public @ResponseBody GastoForm editarGasto(@RequestParam final int gastoId, @RequestParam final int presupuestoId) {
		final GastoForm gastoForm = new GastoForm();
		Gasto gasto = new Gasto();
		try {
			this.actorService.checkGestor();
			gasto = this.gastoService.findOne(gastoId);
			gastoForm.setCantidad(gasto.getCantidad());
			gastoForm.setConcepto(gasto.getConcepto());
			gastoForm.setFecha(gasto.getFecha().toString());
			gastoForm.setObservaciones(gasto.getObservaciones());
			gastoForm.setPresupuestoId(presupuestoId);
			gastoForm.setProveedor(gasto.getProveedor());
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return gastoForm;
	}

	public ModelAndView crearVistaPadre(final Presupuesto p) {
		final ModelAndView result = new ModelAndView("gasto/listAll");
		result.addObject("gastos", this.presupuestoService.findOne(p.getId()).getGastos());
		final GastoForm gastoForm = new GastoForm();
		gastoForm.setPresupuestoId(p.getId());
		result.addObject("presupuestoId", p.getId());
		result.addObject("gastoForm", gastoForm);
		result.addObject("requestURI", "gestor/gasto/listAll.do");
		return result;
	}
}

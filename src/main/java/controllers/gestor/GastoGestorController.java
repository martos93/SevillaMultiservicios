
package controllers.gestor;

import java.text.SimpleDateFormat;
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
			final Presupuesto p = this.presupuestoService.findOne(presupuestoId);
			result = new ModelAndView("gasto/listAll");
			result.addObject("gastos", p.getGastos());
			final GastoForm gastoForm = new GastoForm();
			gastoForm.setPresupuestoId(presupuestoId);
			result.addObject("gastoForm", gastoForm);
			result.addObject("requestURI", "gestor/gasto/listAll.do");
			result.addObject("presupuestoId", presupuestoId);
			result.addObject("codigoPresupuesto", p.getCodigo());

		} catch (final Exception e) {
			this.logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/nuevoGasto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView nuevoGasto(@RequestBody final GastoForm gastoForm) {
		ModelAndView result = null;
		Presupuesto p = this.presupuestoService.findOne(gastoForm.getPresupuestoId());

		try {
			this.actorService.checkGestor();
			Gasto gasto = new Gasto();
			gasto.setCantidad(gastoForm.getCantidad());
			gasto.setConcepto(gastoForm.getConcepto());
			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			final Date d = dateFormat.parse(gastoForm.getFecha());
			final Date hoy = new Date(System.currentTimeMillis());
			if (d.after(hoy)) {
				result = this.crearVistaPadre(p);
				result.addObject("error", true);
				result.addObject("mensaje", "Debe introducir una fecha anterior a hoy");
				return result;
			}
			gasto.setFecha(d);
			gasto.setObservaciones(gastoForm.getObservaciones());
			gasto.setProveedor(gastoForm.getProveedor());
			gasto.setTipo(gastoForm.getTipo());
			gasto = this.gastoService.save(gasto);
			p.getGastos().add(gasto);
			p = this.presupuestoService.save(p);
			result = this.crearVistaPadre(p);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha guardado correctamente el gasto.");
		} catch (final Exception e) {
			result = this.crearVistaPadre(p);
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al guardar.");
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/modificarGasto", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView modificarGasto(@RequestBody final GastoForm gastoForm) {
		ModelAndView result = null;
		final Presupuesto p = this.presupuestoService.findOne(gastoForm.getPresupuestoId());
		try {
			this.actorService.checkGestor();
			Gasto gasto = this.gastoService.findOne(gastoForm.getGastoId());
			gasto.setCantidad(gastoForm.getCantidad());
			gasto.setConcepto(gastoForm.getConcepto());
			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			final Date date = dateFormat.parse(gastoForm.getFecha());
			final Date hoy = new Date(System.currentTimeMillis());
			if (date.after(hoy)) {
				result = this.crearVistaPadre(p);
				result.addObject("error", true);
				result.addObject("mensaje", "Debe introducir una fecha anterior a hoy");
				return result;
			}
			gasto.setFecha(date);
			gasto.setObservaciones(gastoForm.getObservaciones());
			gasto.setProveedor(gastoForm.getProveedor());
			gasto.setTipo(gastoForm.getTipo());
			gasto = this.gastoService.save(gasto);
			result = this.crearVistaPadre(p);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente el gasto");

		} catch (final Exception e) {
			result = this.crearVistaPadre(p);
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al guardar.");
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

			final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			final String formattedDate = formatter.format(gasto.getFecha());
			gastoForm.setFecha(formattedDate);
			gastoForm.setObservaciones(gasto.getObservaciones());
			gastoForm.setPresupuestoId(presupuestoId);
			gastoForm.setProveedor(gasto.getProveedor());
			gastoForm.setGastoId(gastoId);
			gastoForm.setTipo(gasto.getTipo());
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return gastoForm;
	}

	@RequestMapping(value = "/eliminarGasto", method = RequestMethod.GET)
	public ModelAndView eliminarGasto(@RequestParam final int gastoId, @RequestParam final int presupuestoId) {
		ModelAndView result = new ModelAndView();
		try {

			this.actorService.checkGestor();
			final Gasto gasto = this.gastoService.findOne(gastoId);
			final Presupuesto p = this.presupuestoService.findOne(presupuestoId);
			p.getGastos().remove(gasto);
			this.presupuestoService.save(p);
			this.gastoService.delete(gasto);
			result = this.crearVistaPadre(p);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha borrado correctamente el gasto.");
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	public ModelAndView crearVistaPadre(final Presupuesto p) {
		final ModelAndView result = new ModelAndView("gasto/listAll");
		result.addObject("gastos", this.presupuestoService.findOne(p.getId()).getGastos());
		final GastoForm gastoForm = new GastoForm();
		gastoForm.setPresupuestoId(p.getId());
		result.addObject("presupuestoId", p.getId());
		result.addObject("gastoForm", gastoForm);
		result.addObject("requestURI", "gestor/gasto/listAll.do");
		result.addObject("codigoPresupuesto", p.getCodigo());
		return result;
	}
}

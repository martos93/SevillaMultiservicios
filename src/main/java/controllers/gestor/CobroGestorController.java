
package controllers.gestor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import domain.Agenda;
import domain.Cobro;
import domain.Concepto;
import domain.Empleado;
import domain.Gasto;
import domain.Presupuesto;
import forms.CobroForm;
import forms.PresupuestoForm;
import services.ActorService;
import services.AgendaService;
import services.ClienteService;
import services.CobroService;
import services.EmpleadoService;
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
	private EmpleadoService		empleadoService;

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private AgendaService		agendaService;

	@Autowired
	private CobroService		cobroService;


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
			result.addObject("cobros", this.cobroService.obtenerCobrosPorFecha(p.getId()));

			final CobroForm cobroForm = new CobroForm();
			cobroForm.setPresupuestoId(presupuestoId);
			result.addObject("cobroForm", cobroForm);
			result.addObject("presupuestoId", presupuestoId);
			result.addObject("codigoPresupuesto", p.getCodigo());
			result.addObject("ocultaCabecera", true);
			result.addObject("presupuestoForm", presupuestoForm);
			result.addObject("cliente", this.clienteService.findOne(p.getCliente().getId()));
			result.addObject("direccionObra", p.getInvolucradosObra());
			final ArrayList<Empleado> empleados = (ArrayList<Empleado>) this.empleadoService.findAll();
			final ArrayList<Empleado> empleadosPresupuesto = new ArrayList<Empleado>();
			for (final Agenda e : p.getAgendas())
				empleadosPresupuesto.add(e.getEmpleado());
			empleados.removeAll(empleadosPresupuesto);
			result.addObject("empleadosPresupuesto", empleadosPresupuesto);
			result.addObject("empleados", empleados);
		} catch (final Exception e) {
			this.logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	@RequestMapping(value = "/editarCobro", method = RequestMethod.GET)
	public @ResponseBody CobroForm editarConcepto(@RequestParam final int cobroId) {
		final CobroForm cobroForm = new CobroForm();
		Cobro cobro = new Cobro();
		try {
			this.actorService.checkGestor();
			cobro = this.cobroService.findOne(cobroId);
			cobroForm.setFecha(cobro.getFecha());
			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			final String d1 = dateFormat.format(cobro.getFecha());
			cobroForm.setFechaS(d1);
			cobroForm.setLiquidado(cobro.getLiquidado());

			cobroForm.setCobroId(cobroId);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return cobroForm;
	}

	@RequestMapping(value = "/guardarEmpleado", method = RequestMethod.GET)
	public @ResponseBody ModelAndView guardarEmpleado(@RequestParam final int empleadoId, @RequestParam final int presupuestoId) {
		final Empleado empleado = this.empleadoService.findOne(empleadoId);
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);

		try {
			this.actorService.checkGestor();
			final Agenda agenda = new Agenda();
			agenda.setEmpleado(empleado);
			agenda.setPresupuesto(presupuesto);
			agenda.setEntradas(new ArrayList<String>());
			this.agendaService.save(agenda);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
			final ModelAndView result = this.crearVistaPadre(presupuestoId);
			result.addObject("error", true);
			result.addObject("mensaje", "Se producido un error al guardar el empleado.");

		}
		final ModelAndView result = this.crearVistaPadre(presupuestoId);
		result.addObject("success", true);
		result.addObject("mensaje", "Se ha asignado correctamente el empleado. Se ha creado una agenda asociada a este presupuesto para el mismo.");

		return result;
	}

	@RequestMapping(value = "/eliminarEmpleadoPresupuesto", method = RequestMethod.GET)
	public @ResponseBody ModelAndView eliminarEmpleadoPresupuesto(@RequestParam final int empleadoId, @RequestParam final int presupuestoId) {
		final Empleado empleado = this.empleadoService.findOne(empleadoId);
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		try {
			this.actorService.checkGestor();
			Agenda agenda = null;
			for (final Agenda a : empleado.getAgendas())
				if (a.getPresupuesto().getId() == presupuestoId)
					agenda = a;
			empleado.getAgendas().remove(agenda);
			this.empleadoService.save(empleado);
			presupuesto.getAgendas().remove(agenda);
			this.presupuestoService.save(presupuesto);

			this.agendaService.delete(agenda);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		final ModelAndView result = this.crearVistaPadre(presupuestoId);
		result.addObject("success", true);
		result.addObject("mensaje", "Se ha borrado correctamente el empleado");

		return result;
	}

	@RequestMapping(value = "/eliminarCobro", method = RequestMethod.GET)
	public ModelAndView borrarCobro(@RequestParam final int cobroId, @RequestParam final int presupuestoId) {
		ModelAndView result = new ModelAndView();
		try {

			this.actorService.checkGestor();
			final Cobro cobro = this.cobroService.findOne(cobroId);
			final Presupuesto p = this.presupuestoService.findOne(presupuestoId);
			final ArrayList<Cobro> cobros = this.cobroService.obtenerCobrosPorFecha(p.getId());
			final int posicion = cobros.indexOf(cobro);
			p.getCobros().remove(cobro);
			this.presupuestoService.save(p);

			Cobro actual = null;
			Cobro anterior = null;
			if (cobros.size() == 1) {
				this.cobroService.delete(cobro);
				result = this.crearVistaPadre(p.getId());
				result.addObject("success", true);
				result.addObject("mensaje", "Se ha borrado correctamente el cobro.");
				return result;
			}
			if (posicion == 0) {
				actual = cobros.get(posicion + 1);
				BigDecimal presupuestado = new BigDecimal(0);
				for (final Concepto g : p.getConceptos())
					presupuestado = presupuestado.add(g.getTotal());

				actual.setPendiente(presupuestado.subtract(actual.getLiquidado()));
				actual.setTotal(actual.getLiquidado());
				actual = this.cobroService.save(actual);
				anterior = actual;
				for (int i = posicion + 1; i < cobros.size(); i++) {
					actual = cobros.get(i);
					actual.setPendiente(anterior.getPendiente().subtract(actual.getLiquidado()));
					actual.setTotal(anterior.getTotal().add(actual.getLiquidado()));
					anterior = this.cobroService.save(actual);
				}
				this.cobroService.delete(cobro);
			} else if (posicion == cobros.size() - 1)
				this.cobroService.delete(cobro);
			else {
				actual = cobros.get(posicion + 1);
				anterior = cobros.get(posicion - 1);
				for (int i = posicion + 1; i < cobros.size(); i++) {
					actual = cobros.get(i);
					actual.setPendiente(anterior.getPendiente().subtract(actual.getLiquidado()));
					actual.setTotal(anterior.getTotal().add(actual.getLiquidado()));
					anterior = this.cobroService.save(actual);
				}
			}

			result = this.crearVistaPadre(p.getId());
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha borrado correctamente el cobro.");
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/modificarCobro", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView modificarConcepto(@RequestBody final CobroForm cobroForm) {
		final Presupuesto p = this.presupuestoService.findOne(cobroForm.getPresupuestoId());
		ModelAndView result = null;
		try {
			BigDecimal presupuestado = new BigDecimal(0);
			for (final Concepto g : p.getConceptos())
				presupuestado = presupuestado.add(g.getTotal());
			Cobro c = this.cobroService.findOne(cobroForm.getCobroId());
			Cobro anterior = null;
			final ArrayList<Cobro> cobros = this.cobroService.obtenerCobrosPorFecha(cobroForm.getPresupuestoId());
			if (cobros.size() > 1) {

				if (cobros.indexOf(c) == 0)
					anterior = cobros.get(0);
				else
					anterior = cobros.get(cobros.indexOf(c) - 1);

				if (anterior.getPendiente().subtract(cobroForm.getLiquidado()).compareTo(new BigDecimal(0)) == -1) {
					result = this.crearVistaPadre(p.getId());
					result.addObject("error", true);
					result.addObject("mensaje", "No puede añadir un cobro mayor de lo presupuestado.");
					return result;
				}

				c.setLiquidado(cobroForm.getLiquidado());
				if (cobros.indexOf(anterior) == 0) {

					c.setPendiente(presupuestado.subtract(cobroForm.getLiquidado()));
					c.setTotal(cobroForm.getLiquidado());
				} else {
					anterior = cobros.get(cobros.indexOf(c) - 1);
					c.setPendiente(anterior.getPendiente().subtract(cobroForm.getLiquidado()));
					c.setTotal(anterior.getTotal().add(cobroForm.getLiquidado()));
				}

				c = this.cobroService.save(c);
				anterior = c;
				Cobro actual = null;
				final int indexNuevo = cobros.indexOf(c);
				for (int i = indexNuevo + 1; i < cobros.size(); i++) {
					actual = cobros.get(i);
					actual.setPendiente(anterior.getPendiente().subtract(actual.getLiquidado()));
					actual.setTotal(anterior.getTotal().add(actual.getLiquidado()));
					anterior = this.cobroService.save(actual);
				}
			} else {
				if (c.getPendiente().subtract(cobroForm.getLiquidado()).compareTo(new BigDecimal(0)) == -1) {
					result = this.crearVistaPadre(p.getId());
					result.addObject("error", true);
					result.addObject("mensaje", "No puede añadir un cobro mayor de lo presupuestado.");
					return result;
				}

				c.setPendiente(presupuestado.subtract(cobroForm.getLiquidado()));
				c.setTotal(cobroForm.getLiquidado());
				c.setLiquidado(cobroForm.getLiquidado());
			}
			result = this.crearVistaPadre(p.getId());
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente el cobro.");

		} catch (final Exception e) {
			result = this.crearVistaPadre(p.getId());
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al modificar el concepto.");
			this.logger.error(e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/nuevoCobro", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView nuevoCobro(@RequestBody final CobroForm cobroForm) {
		ModelAndView result = null;
		try {
			this.actorService.checkGestor();
			final Presupuesto p = this.presupuestoService.findOne(cobroForm.getPresupuestoId());
			final ArrayList<Cobro> cobros = this.cobroService.obtenerCobrosPorFecha(p.getId());
			Cobro c = new Cobro();
			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			final Date fecha = dateFormat.parse(cobroForm.getFechaS());
			c.setFecha(fecha);
			c.setLiquidado(cobroForm.getLiquidado());
			BigDecimal totalPresupuesto = new BigDecimal(0);
			if (p.getCobros().isEmpty()) {
				for (final Concepto con : p.getConceptos())
					totalPresupuesto = totalPresupuesto.add(con.getTotal());
				if (p.getFactura() != null)
					for (final Concepto confac : p.getFactura().getConceptos())
						totalPresupuesto = totalPresupuesto.add(confac.getTotal());

				totalPresupuesto = totalPresupuesto.subtract(cobroForm.getLiquidado());
				c.setPendiente(totalPresupuesto);
				c.setTotal(cobroForm.getLiquidado());
				c.setFechaCreacion(new Date(System.currentTimeMillis()));
				if (c.getPendiente().compareTo(new BigDecimal(0)) == -1) {
					result = this.crearVistaPadre(p.getId());
					result.addObject("error", true);
					result.addObject("mensaje", "No puede añadir un cobro mayor de lo presupuestado o de la cantidad pendiente por cobrar.");
					return result;
				}
				c = this.cobroService.save(c);
				p.getCobros().add(c);
				this.presupuestoService.save(p);
			} else {
				final Cobro ultimo = cobros.get(cobros.size() - 1);
				c.setPendiente(ultimo.getPendiente().subtract(cobroForm.getLiquidado()));
				c.setTotal(ultimo.getTotal().add(cobroForm.getLiquidado()));
				c.setFechaCreacion(new Date(System.currentTimeMillis()));
				if (c.getPendiente().compareTo(new BigDecimal(0)) == -1) {
					result = this.crearVistaPadre(p.getId());
					result.addObject("error", true);
					result.addObject("mensaje", "No puede añadir un cobro mayor de lo presupuestado.");
					return result;
				}
				c = this.cobroService.save(c);
				p.getCobros().add(c);
				this.presupuestoService.save(p);
			}

			result = this.crearVistaPadre(cobroForm.getPresupuestoId());
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha guardado correctamente el cobro.");

		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/guardarDireccionObra", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView guardarDireccionObra(@RequestBody final PresupuestoForm presupuestoForm) {
		ModelAndView result = null;
		try {

			this.actorService.checkGestor();
			final Presupuesto p = this.presupuestoService.findOne(presupuestoForm.getId());
			p.setInvolucradosObra(presupuestoForm.getInvolucradosObra());
			this.presupuestoService.save(p);
			result = this.crearVistaPadre(presupuestoForm.getId());
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha guardado correctamente la dirección de obra.");

		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}
		return result;
	}

	public ModelAndView crearVistaPadre(final int presupuestoId) {
		ModelAndView result = null;
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
		if (p.getAlbaran() != null)
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
		result.addObject("cobros", this.cobroService.obtenerCobrosPorFecha(p.getId()));

		final CobroForm cobroForm = new CobroForm();
		cobroForm.setPresupuestoId(presupuestoId);
		result.addObject("cobroForm", cobroForm);
		result.addObject("presupuestoId", presupuestoId);
		result.addObject("codigoPresupuesto", p.getCodigo());
		result.addObject("ocultaCabecera", true);
		result.addObject("presupuestoForm", presupuestoForm);
		result.addObject("cliente", this.clienteService.findOne(p.getCliente().getId()));
		result.addObject("direccionObra", p.getInvolucradosObra());
		final ArrayList<Empleado> empleados = (ArrayList<Empleado>) this.empleadoService.findAll();
		final ArrayList<Empleado> empleadosPresupuesto = new ArrayList<Empleado>();
		for (final Agenda e : p.getAgendas())
			empleadosPresupuesto.add(e.getEmpleado());
		empleados.removeAll(empleadosPresupuesto);
		result.addObject("empleadosPresupuesto", empleadosPresupuesto);
		result.addObject("empleados", empleados);
		return result;
	}
}

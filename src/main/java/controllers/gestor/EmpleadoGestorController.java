
package controllers.gestor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import domain.Empleado;
import forms.EmpleadoForm;
import services.ActorService;
import services.AgendaService;
import services.EmpleadoService;

@Controller
@RequestMapping("/gestor/empleado")
public class EmpleadoGestorController extends AbstractController {

	private final Logger	logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActorService	actorService;

	@Autowired
	private EmpleadoService	empleadoService;

	@Autowired
	private AgendaService	agendaService;


	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		this.actorService.checkGestor();
		result = new ModelAndView("empleado/listAll");
		result.addObject("empleados", this.empleadoService.findAll());
		result.addObject("empleadoForm", new EmpleadoForm());
		result.addObject("requestURI", "gestor/empleado/listAll.do");

		return result;
	}

	@RequestMapping(value = "/nuevoEmpleado", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView crearEmpleado(@RequestBody final EmpleadoForm empleadoForm) {
		Empleado empleado = new Empleado();
		this.actorService.checkGestor();
		empleado = this.empleadoService.create(empleadoForm);
		ModelAndView result = null;
		try {
			empleado = this.empleadoService.save(empleado);
			result = this.creaVistaPadre();
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha creado correctamente el empleado.");

		} catch (final DataIntegrityViolationException e) {
			result = this.creaVistaPadre();
			result.addObject("error", true);
			result.addObject("mensaje", "Ya existe ese nombre de usuario.");
			this.logger.error(e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/eliminaEmpleado", method = RequestMethod.GET)
	public ModelAndView borrarEmpleado(@RequestParam final int empleadoId) {
		Empleado empleado = new Empleado();
		ModelAndView result = null;
		this.actorService.checkGestor();
		empleado = this.empleadoService.findOne(empleadoId);
		for (final Agenda a : empleado.getAgendas())
			this.agendaService.delete(a);
		try {
			this.actorService.checkGestor();
			this.empleadoService.delete(empleado);

		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		result = this.creaVistaPadre();

		result.addObject("success", true);
		result.addObject("mensaje", "Se ha borrado correctamente el empleado.");

		return result;
	}

	@RequestMapping(value = "/editarEmpleado", method = RequestMethod.GET)
	public @ResponseBody EmpleadoForm editarEmpleado(@RequestParam final int empleadoId) {
		Empleado empleado = new Empleado();

		final EmpleadoForm empleadoForm = new EmpleadoForm();
		try {
			this.actorService.checkGestor();
			empleado = this.empleadoService.findOne(empleadoId);
			empleadoForm.setNombre(empleado.getNombre());
			empleadoForm.setApellidos(empleado.getApellidos());
			empleadoForm.setIdentificacion(empleado.getIdentificacion());
			empleadoForm.setCodigoPostal(empleado.getCodigoPostal());
			empleadoForm.setDireccion(empleado.getDireccion());
			empleadoForm.setLocalidad(empleado.getLocalidad());
			empleadoForm.setProvincia(empleado.getProvincia());
			empleadoForm.setEmail(empleado.getEmail());
			empleadoForm.setEmpleadoId(empleadoId);
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return empleadoForm;
	}

	@RequestMapping(value = "/modificarEmpleado", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView modificarEmpleado(@RequestBody final EmpleadoForm empleadoForm) {
		Empleado empleado = new Empleado();
		this.actorService.checkGestor();
		empleado = this.empleadoService.findOne(empleadoForm.getEmpleadoId());
		ModelAndView result = null;
		try {
			empleado.setNombre(empleadoForm.getNombre());
			empleado.setApellidos(empleadoForm.getApellidos());
			empleado.setCodigoPostal(empleadoForm.getCodigoPostal());
			empleado.setDireccion(empleadoForm.getDireccion());
			empleado.setLocalidad(empleadoForm.getLocalidad());
			empleado.setProvincia(empleadoForm.getProvincia());
			empleado.setIdentificacion(empleadoForm.getIdentificacion());
			empleado.setEmail(empleadoForm.getEmail());
			empleado = this.empleadoService.save(empleado);
			result = this.creaVistaPadre();
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente el empleado.");

		} catch (final Exception e) {
			result = this.creaVistaPadre();
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al modificar usuario.");
			this.logger.error(e.getMessage());
		}

		return result;
	}

	public ModelAndView creaVistaPadre() {

		final ModelAndView result = new ModelAndView("empleado/listAll");
		result.addObject("empleados", this.empleadoService.findAll());
		result.addObject("empleadoForm", new EmpleadoForm());
		result.addObject("requestURI", "gestor/empleado/listAll.do");

		return result;

	}
}

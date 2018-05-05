
package controllers.gestor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	private final Logger	logger	= Logger.getLogger(EmpleadoGestorController.class);;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private EmpleadoService	empleadoService;

	@Autowired
	private AgendaService	agendaService;


	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("empleado/listAll");
		result.addObject("empleados", this.empleadoService.findAll());
		result.addObject("empleadoForm", new EmpleadoForm());
		result.addObject("requestURI", "gestor/empleado/listAll.do");

		return result;
	}

	@RequestMapping(value = "/nuevoEmpleado", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView crearEmpleado(@RequestBody final EmpleadoForm empleadoForm) {
		Empleado empleado = new Empleado();
		empleado = this.empleadoService.create(empleadoForm);
		ModelAndView result = null;
		try {
			empleado = this.empleadoService.save(empleado);
			result = this.creaVistaPadre();

		} catch (final Exception e) {
			this.logger.error(e);
		}

		result.addObject("success", true);
		result.addObject("mensaje", "Se ha creado correctamente el empleado.");

		return result;
	}

	@RequestMapping(value = "/eliminaEmpleado", method = RequestMethod.GET)
	public ModelAndView borrarEmpleado(@RequestParam final int empleadoId) {
		Empleado empleado = new Empleado();
		ModelAndView result = null;
		empleado = this.empleadoService.findOne(empleadoId);
		for (final Agenda a : empleado.getAgendas())
			this.agendaService.delete(a);
		try {
			this.actorService.checkGestor();
			this.empleadoService.delete(empleado);

		} catch (final Exception e) {
			this.logger.error(e);
		}

		result = this.creaVistaPadre();

		result.addObject("success", true);
		result.addObject("mensaje", "Se ha borrado correctamente el empleado.");

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

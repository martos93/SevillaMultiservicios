
package controllers.gestor;

import java.util.List;

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
import domain.Agenda;
import domain.Empleado;
import forms.AgendaForm;
import services.ActorService;
import services.AgendaService;
import services.ClienteService;
import services.EmpleadoService;
import services.PresupuestoService;

@Controller
@RequestMapping("/gestor/agenda")
public class AgendaGestorController extends AbstractController {

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


	@RequestMapping(value = "/verAgendas", method = RequestMethod.GET)
	public @ResponseBody ModelAndView list(@RequestParam final int empleadoId) {
		ModelAndView result;
		result = new ModelAndView("agenda/verAgendas");
		final Empleado e = this.empleadoService.findOne(empleadoId);
		final List<Agenda> agendas = (List<Agenda>) e.getAgendas();
		result.addObject("agendas", agendas);
		result.addObject("agendaForm", new AgendaForm());
		result.addObject("empleado", e);

		return result;
	}

}

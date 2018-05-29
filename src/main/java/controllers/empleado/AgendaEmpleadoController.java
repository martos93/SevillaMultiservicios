
package controllers.empleado;

import java.util.List;

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
import domain.Agenda;
import domain.Empleado;
import forms.AgendaForm;
import security.LoginService;
import services.ActorService;
import services.AgendaService;
import services.ClienteService;
import services.EmpleadoService;
import services.PresupuestoService;

@Controller
@RequestMapping("/empleado/agenda")
public class AgendaEmpleadoController extends AbstractController {

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
	public @ResponseBody ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("agenda/verAgendas");

		final Empleado e = this.empleadoService.obtenerEmpleadoLogueado(LoginService.getPrincipal().getUsername());
		final List<Agenda> agendas = (List<Agenda>) e.getAgendas();
		result.addObject("agendas", agendas);
		result.addObject("agendaForm", new AgendaForm());
		result.addObject("empleado", e);

		return result;
	}

	@RequestMapping(value = "/guardarEntrada", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView nuevoCobro(@RequestBody final AgendaForm agendaForm) {
		ModelAndView result;
		result = new ModelAndView("agenda/verAgendas");
		final String entrada = agendaForm.getFecha() + " - " + agendaForm.getEntradaTexto();
		Agenda agenda = this.agendaService.findOne(agendaForm.getAgendaId());
		agenda.getEntradas().add(entrada);
		agenda = this.agendaService.save(agenda);
		final Empleado e = this.empleadoService.obtenerEmpleadoLogueado(LoginService.getPrincipal().getUsername());
		final List<Agenda> agendas = (List<Agenda>) e.getAgendas();
		result.addObject("agendas", agendas);
		result.addObject("agendaForm", new AgendaForm());
		result.addObject("empleado", e);
		return result;
	}
}

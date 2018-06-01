
package controllers.gestor;

import java.util.ArrayList;
import java.util.List;

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
import services.AgendaService;
import services.EmpleadoService;

@Controller
@RequestMapping("/gestor/agenda")
public class AgendaGestorController extends AbstractController {

	@Autowired
	private EmpleadoService	empleadoService;

	@Autowired
	private AgendaService	agendaService;


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

	@RequestMapping(value = "/verAgenda", method = RequestMethod.GET)
	public @ResponseBody AgendaForm ver(@RequestParam final int agendaId) {
		final AgendaForm agendaForm = new AgendaForm();
		final Agenda agenda = this.agendaService.findOne(agendaId);
		final List<String> agendas = (List<String>) agenda.getEntradas();
		final ArrayList<String> aux = new ArrayList<String>();
		for (final String s : agendas)
			aux.add(s);
		agendaForm.setEntradas(aux);
		return agendaForm;
	}

}

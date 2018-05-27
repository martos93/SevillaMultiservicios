/*
 * WelcomeController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 *
 */

package controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Solicitud;
import services.ActorService;
import services.FacturaService;
import services.SolicitudService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private SolicitudService	solicitudService;

	@Autowired
	private FacturaService		facturaService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		boolean factura = false;
		result = new ModelAndView("welcome/index");

		try {
			if (this.actorService.checkGestor()) {
				final ArrayList<Solicitud> solicitudes = this.solicitudService.solicitudesSinLeerGestor();
				result.addObject("solicitudes", solicitudes);
				if (solicitudes.size() > 0) {
					result.addObject("info", true);
					result.addObject("mensaje", "Una o más solicitudes han cambiado de estado.");
				}
			}

			if (this.actorService.checkCliente()) {
				final ArrayList<Solicitud> solicitudes = this.solicitudService.solicitudesSinLeerCliente();
				result.addObject("solicitudes", solicitudes);
				if (solicitudes.size() > 0)
					for (final Solicitud s : solicitudes)
						if (s.getPresupuesto().getFactura() != null && s.getPresupuesto().getFactura().getLeido() == false) {
							factura = true;

							break;
						}
				if (factura && solicitudes.size() > 0) {
					result.addObject("info", true);
					result.addObject("mensaje", "Se ha creado una factura de uno de sus presupuestos.");
				} else if (solicitudes.size() > 0) {
					result.addObject("info", true);
					result.addObject("mensaje", "Una o más solicitudes han cambiado de estado.");
				}
			}
		} catch (final Exception e) {
			return result;
		}
		return result;
	}
}

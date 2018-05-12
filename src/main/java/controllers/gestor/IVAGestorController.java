
package controllers.gestor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.IVA;
import forms.IVAForm;
import services.ActorService;
import services.IVAService;

@Controller
@RequestMapping("/gestor/iva")
public class IVAGestorController extends AbstractController {

	private final Logger	logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActorService	actorService;

	@Autowired
	private IVAService		ivaService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		this.actorService.checkGestor();
		final List<IVA> iva = new ArrayList<IVA>(this.ivaService.findAll());
		result = new ModelAndView("iva/list");
		final IVAForm ivaForm = new IVAForm();
		ivaForm.setPorcentaje(iva.get(0).getPorcentaje());
		ivaForm.setIvaID(iva.get(0).getId());

		result.addObject("ivaForm", ivaForm);

		result.addObject("requestURI", "gestor/iva/list.do");

		return result;
	}

	@RequestMapping(value = "/modificarIVA", method = RequestMethod.POST)
	public @ResponseBody ModelAndView editarIVA(@RequestBody final IVAForm ivaForm) {
		ModelAndView result = new ModelAndView("iva/list");
		try {
			IVA iva = new IVA();
			this.actorService.checkGestor();
			iva = this.ivaService.findOne(ivaForm.getIvaID());
			iva.setPorcentaje(ivaForm.getPorcentaje());
			iva = this.ivaService.save(iva);

			result = new ModelAndView("iva/list");

			final IVAForm ivaFormAct = new IVAForm();
			ivaFormAct.setPorcentaje(iva.getPorcentaje());
			ivaFormAct.setIvaID(iva.getId());
			result.addObject("ivaForm", ivaFormAct);
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente el IVA.");
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return result;
	}
}


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
import domain.Cliente;
import forms.ClienteForm;
import services.ActorService;
import services.AgendaService;
import services.ClienteService;
import services.FacturaService;
import services.PresupuestoService;
import services.SolicitudService;

@Controller
@RequestMapping("/gestor/cliente")
public class ClienteGestorController extends AbstractController {

	private final Logger		logger	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private PresupuestoService	presupuestoService;

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("cliente/listAll");
		result.addObject("clientes", this.clienteService.findAll());
		result.addObject("clienteForm", new ClienteForm());
		result.addObject("requestURI", "gestor/cliente/listAll.do");

		return result;
	}

	@RequestMapping(value = "/nuevoCliente", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView crearCliente(@RequestBody final ClienteForm clienteForm) {
		Cliente cliente = new Cliente();
		cliente = this.clienteService.create(clienteForm);
		ModelAndView result = null;
		try {
			cliente = this.clienteService.save(cliente);
			result = this.creaVistaPadreList();
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha creado correctamente el cliente.");

		} catch (final DataIntegrityViolationException e) {
			result = this.creaVistaPadreList();
			result.addObject("error", true);
			result.addObject("mensaje", "Ya existe ese nombre de usuario.");
			this.logger.error(e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/editarCliente", method = RequestMethod.GET)
	public @ResponseBody ClienteForm editarCliente(@RequestParam final int clienteId) {
		final ClienteForm clienteForm = new ClienteForm();
		Cliente cliente = new Cliente();
		try {
			this.actorService.checkGestor();
			cliente = this.clienteService.findOne(clienteId);
			clienteForm.setNombre(cliente.getNombre());
			clienteForm.setApellidos(cliente.getApellidos());
			clienteForm.setIdentificacion(cliente.getIdentificacion());
			clienteForm.setCodigoPostal(cliente.getCodigoPostal());
			clienteForm.setDireccion(cliente.getDireccion());
			clienteForm.setLocalidad(cliente.getLocalidad());
			clienteForm.setProvincia(cliente.getProvincia());
			clienteForm.setEmail(cliente.getEmail());
			clienteForm.setClienteId(clienteId);
			clienteForm.setRefCatastro(cliente.getRefCatastro());
		} catch (final Exception e) {
			this.logger.error(e.getMessage());
		}

		return clienteForm;
	}

	@RequestMapping(value = "/modificarCliente", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelAndView modificarCliente(@RequestBody final ClienteForm clienteForm) {
		Cliente cliente = new Cliente();
		cliente = this.clienteService.findOne(clienteForm.getClienteId());
		ModelAndView result = null;
		try {
			cliente.setNombre(clienteForm.getNombre());
			cliente.setApellidos(clienteForm.getApellidos());
			cliente.setCodigoPostal(clienteForm.getCodigoPostal());
			cliente.setDireccion(clienteForm.getDireccion());
			cliente.setLocalidad(clienteForm.getLocalidad());
			cliente.setProvincia(clienteForm.getProvincia());
			cliente.setIdentificacion(clienteForm.getIdentificacion());
			cliente.setEmail(clienteForm.getEmail());
			cliente.setRefCatastro(clienteForm.getRefCatastro());
			cliente = this.clienteService.save(cliente);
			result = this.creaVistaPadreList();
			result.addObject("success", true);
			result.addObject("mensaje", "Se ha modificado correctamente el cliente.");

		} catch (final Exception e) {
			result = this.creaVistaPadreList();
			result.addObject("error", true);
			result.addObject("mensaje", "Se ha producido un error al modificar usuario.");
			this.logger.error(e.getMessage());
		}

		return result;
	}

	//	@RequestMapping(value = "/eliminaCliente", method = RequestMethod.GET)
	//	public ModelAndView borrarCliente(@RequestParam final int clienteId) {
	//		Cliente cliente = new Cliente();
	//		ModelAndView result = null;
	//		cliente = this.clienteService.findOne(clienteId);
	//		Factura factura = null;
	//		Solicitud solicitud = null;
	//		try {
	//			this.actorService.checkGestor();
	//			for (final Presupuesto p : cliente.getPresupuesto()) {
	//				for (final Agenda a : p.getAgendas())
	//					p.getAgendas().remove(a);
	//				this.agendaService.delete(a);
	//
	//				if (p.getSolicitud() != null) {
	//					solicitud = this.solicitudService.findOne(p.getSolicitud().getId());
	//					this.solicitudService.delete(solicitud);
	//				}
	//
	//				if (p.getFactura() != null) {
	//					factura = this.facturaService.findOne(p.getFactura().getId());
	//					this.facturaService.delete(factura);
	//				}
	//
	//				this.presupuestoService.delete(p);
	//			}
	//
	//			this.clienteService.delete(cliente);
	//			result = this.creaVistaPadre();
	//			result.addObject("success", true);
	//			result.addObject("mensaje", "Se ha borrado correctamente el cliente y todos los presupuestos asociados.");
	//
	//		} catch (final IllegalArgumentException e) {
	//			result = this.creaVistaPadre();
	//			result.addObject("error", true);
	//			result.addObject("mensaje", "Se ha producido un error al borrar.");
	//
	//			this.logger.error(e.getMessage());
	//		}
	//
	//		return result;
	//	}

	public ModelAndView creaVistaPadreList() {

		final ModelAndView result = new ModelAndView("cliente/listAll");
		result.addObject("clientes", this.clienteService.findAll());
		result.addObject("clienteForm", new ClienteForm());
		result.addObject("requestURI", "gestor/cliente/listAll.do");

		return result;

	}

}

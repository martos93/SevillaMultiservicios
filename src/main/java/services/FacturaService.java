
package services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import domain.Cliente;
import domain.Concepto;
import domain.Factura;
import domain.IVA;
import domain.Presupuesto;
import domain.TipoTrabajo;
import forms.ConceptoForm;
import forms.PresupuestoForm;
import forms.TareaForm;
import repositories.FacturaRepository;
import utilities.OperacionesPresupuesto;

@Service
@Transactional
public class FacturaService {

	@Autowired
	private FacturaRepository	facturaRepository;

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private IVAService			ivaService;

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private TipoTrabajoService	tipoTrabajoService;


	public FacturaService() {
		super();
	}

	public Collection<Factura> findAll() {
		return this.facturaRepository.findAll();
	}

	public Factura findOne(final int id) {
		return this.facturaRepository.findOne(id);
	}

	public Factura save(final Factura factura) {
		Assert.notNull(factura);
		return this.facturaRepository.save(factura);
	}
	public void delete(final Factura factura) {
		this.facturaRepository.delete(factura);
	}

	public Factura create(final int presupuestoId) {
		final Factura factura = new Factura();
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		factura.setConceptos(new ArrayList<Concepto>());
		factura.setPresupuesto(presupuesto);
		final IVA iva = ((List<IVA>) this.ivaService.findAll()).get(0);
		BigDecimal ivaCalc = new BigDecimal(iva.getPorcentaje());
		ivaCalc = ivaCalc.divide(new BigDecimal(100));
		BigDecimal importeTotalSinIVA = new BigDecimal(0);
		BigDecimal importeTotalConIVA = new BigDecimal(0);
		for (final Concepto c : presupuesto.getConceptos())
			importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
		importeTotalConIVA = importeTotalConIVA.add(importeTotalSinIVA);
		importeTotalConIVA = importeTotalConIVA.add(importeTotalConIVA.multiply(ivaCalc));
		factura.setImporteTotalConIVA(importeTotalConIVA);
		factura.setImporteTotalSinIVA(importeTotalSinIVA);
		factura.setLeido(false);
		return factura;
	}

	public ModelAndView crearVistaPadreVerFactura(final int presupuestoId) {
		ModelAndView result = null;
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		final Cliente cliente = this.clienteService.findOne(presupuesto.getCliente().getId());
		final PresupuestoForm presupuestoForm = this.presupuestoService.createForm(presupuesto);
		result = new ModelAndView("presupuesto/verPresupuestoCliente");
		result.addObject("ocultaCabecera", true);
		result.addObject("cliente", cliente);
		result.addObject("presupuestoForm", presupuestoForm);

		final BigDecimal totalPresupuesto = OperacionesPresupuesto.totalPresupuesto(presupuesto);
		result.addObject("totalPresupuesto", totalPresupuesto);
		final ConceptoForm conceptoForm = new ConceptoForm();
		conceptoForm.setClienteId(cliente.getId());
		conceptoForm.setPresupuestoId(presupuestoId);
		result.addObject("conceptoForm", conceptoForm);

		final TareaForm tareaForm = new TareaForm();
		tareaForm.setPresupuestoId(presupuestoId);
		result.addObject("tareaForm", tareaForm);
		final ArrayList<TipoTrabajo> tiposTrabajo = (ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll();
		result.addObject("tiposTrabajo", tiposTrabajo);
		result.addObject("tipoTrabajoId", presupuesto.getTipoTrabajo().getId());
		result.addObject("observaciones", presupuesto.getObservaciones());
		result.addObject("presupuesto", presupuesto);
		result.addObject("factura", presupuesto.getFactura());
		result.addObject("verPresupuesto", false);
		result.addObject("verFactura", true);

		BigDecimal importeTotalSinIVA = new BigDecimal(0);
		BigDecimal ivaCalculado = new BigDecimal(0);
		BigDecimal importeTotalConIVA = new BigDecimal(0);
		for (final Concepto c : presupuesto.getConceptos())
			if (c.getTotal() != null)
				importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
		for (final Concepto c : presupuesto.getFactura().getConceptos())
			if (c.getTotal() != null)
				importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());

		final IVA iva = ((List<IVA>) this.ivaService.findAll()).get(0);
		ivaCalculado = importeTotalSinIVA.multiply(new BigDecimal(iva.getPorcentaje()).divide(new BigDecimal(100)));
		importeTotalConIVA = importeTotalSinIVA.add(ivaCalculado);

		importeTotalSinIVA = importeTotalSinIVA.setScale(2, BigDecimal.ROUND_HALF_UP);
		importeTotalConIVA = importeTotalConIVA.setScale(2, BigDecimal.ROUND_HALF_UP);
		ivaCalculado = ivaCalculado.setScale(2, BigDecimal.ROUND_HALF_UP);

		result.addObject("importeTotalSinIVA", importeTotalSinIVA);
		result.addObject("importeTotalConIVA", importeTotalConIVA);
		result.addObject("ivaCalculado", ivaCalculado);

		return result;
	}
}

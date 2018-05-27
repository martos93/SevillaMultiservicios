
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

import domain.Albaran;
import domain.Cliente;
import domain.Concepto;
import domain.IVA;
import domain.Presupuesto;
import domain.TipoTrabajo;
import forms.ConceptoForm;
import forms.PresupuestoForm;
import forms.TareaForm;
import repositories.AlbaranRepository;
import utilities.OperacionesPresupuesto;

@Service
@Transactional
public class AlbaranService {

	@Autowired
	private AlbaranRepository	albaranRepository;

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private IVAService			ivaService;

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private TipoTrabajoService	tipoTrabajoService;


	public AlbaranService() {
		super();
	}

	public Collection<Albaran> findAll() {
		return this.albaranRepository.findAll();
	}

	public Albaran findOne(final int id) {
		return this.albaranRepository.findOne(id);
	}

	public Albaran save(final Albaran albaran) {
		Assert.notNull(albaran);
		return this.albaranRepository.save(albaran);
	}
	public void delete(final Albaran albaran) {
		this.albaranRepository.delete(albaran);
	}

	public Albaran create(final int presupuestoId) {
		final Albaran albaran = new Albaran();
		final Presupuesto presupuesto = this.presupuestoService.findOne(presupuestoId);
		albaran.setConceptos(new ArrayList<Concepto>());
		albaran.setPresupuesto(presupuesto);
		final IVA iva = ((List<IVA>) this.ivaService.findAll()).get(0);
		BigDecimal ivaCalc = new BigDecimal(iva.getPorcentaje());
		ivaCalc = ivaCalc.divide(new BigDecimal(100));
		BigDecimal importeTotalSinIVA = new BigDecimal(0);
		BigDecimal importeTotalConIVA = new BigDecimal(0);
		for (final Concepto c : presupuesto.getConceptos())
			importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
		importeTotalConIVA = importeTotalConIVA.add(importeTotalSinIVA);
		importeTotalConIVA = importeTotalConIVA.add(importeTotalConIVA.multiply(ivaCalc));
		albaran.setImporteTotalSinIVA(importeTotalSinIVA);
		albaran.setLeido(false);
		return albaran;
	}

	public ModelAndView crearVistaPadreVerAlbaran(final int presupuestoId) {
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
		result.addObject("albaran", presupuesto.getAlbaran());
		result.addObject("verPresupuesto", false);
		result.addObject("verAlbaran", true);

		BigDecimal importeTotalSinIVA = new BigDecimal(0);
		for (final Concepto c : presupuesto.getConceptos())
			if (c.getTotal() != null)
				importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());
		for (final Concepto c : presupuesto.getAlbaran().getConceptos())
			if (c.getTotal() != null)
				importeTotalSinIVA = importeTotalSinIVA.add(c.getTotal());

		importeTotalSinIVA = importeTotalSinIVA.setScale(2, BigDecimal.ROUND_HALF_UP);

		result.addObject("importeTotalSinIVA", importeTotalSinIVA);

		return result;
	}
}

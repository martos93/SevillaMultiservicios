
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Agenda;
import domain.Cobro;
import domain.Concepto;
import domain.Gasto;
import domain.Presupuesto;
import forms.PresupuestoForm;
import repositories.PresupuestoRepository;

@Service
@Transactional
public class PresupuestoService {

	@Autowired
	private PresupuestoRepository presupuestoRepository;


	public PresupuestoService() {
		super();
	}

	public Collection<Presupuesto> findAll() {
		return this.presupuestoRepository.findAll();
	}

	public Presupuesto findOne(final int id) {
		return this.presupuestoRepository.findOne(id);
	}

	public Presupuesto save(final Presupuesto presupuesto) {
		Assert.notNull(presupuesto);
		return this.presupuestoRepository.save(presupuesto);
	}
	public void delete(final Presupuesto presupuesto) {
		this.presupuestoRepository.delete(presupuesto);
	}

	public Presupuesto create() {
		final Presupuesto presupuesto = new Presupuesto();
		final Date date = new Date(System.currentTimeMillis());
		presupuesto.setFechaInicio(date);
		presupuesto.setAceptado(null);
		presupuesto.setGastos(new ArrayList<Gasto>());
		presupuesto.setConceptos(new ArrayList<Concepto>());
		presupuesto.setCobros(new ArrayList<Cobro>());
		presupuesto.setAgendas(new ArrayList<Agenda>());

		return presupuesto;
	}

	@SuppressWarnings("deprecation")
	public PresupuestoForm createForm(final Presupuesto p) {
		final PresupuestoForm pForm = new PresupuestoForm();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		pForm.setAceptado(p.isAceptado());
		pForm.setAgendas(p.getAgendas());
		pForm.setAlbaran(p.getAlbaran());
		pForm.setCobros(p.getCobros());
		pForm.setCodigo(p.getCodigo());
		pForm.setConceptos(p.getConceptos());
		pForm.setDireccionObra(p.getDireccionObra());
		pForm.setFactura(p.getFactura());
		pForm.setCodigoPostal(p.getCodigoPostal());
		pForm.setTipoTrabajoS(p.getTipoTrabajo().getDescripcion());
		if (p.getFechaFin() != null) {
			final String d1 = dateFormat.format(p.getFechaFin());
			pForm.setFechaFin(new Date(d1));
			pForm.setFechaFinS(d1);
		}
		if (p.getFechaObra() != null) {
			final String d2 = dateFormat.format(p.getFechaObra());
			pForm.setFechaObra(new Date(d2));
			pForm.setFechaObraS(d2);
		}

		if (p.getFechaInicio() != null) {
			final String d3 = dateFormat.format(p.getFechaInicio());
			pForm.setFechaInicio(new Date(d3));
			pForm.setFechaInicioS(d3);
		}
		pForm.setGastos(p.getGastos());
		pForm.setLocalidad(p.getLocalidad());
		pForm.setObservaciones(p.getObservaciones());
		pForm.setProvincia(p.getProvincia());
		pForm.setTitulo(p.getTitulo());
		pForm.setId(p.getId());
		pForm.setSolicitudId(p.getSolicitudTemporal());
		return pForm;

	}
}

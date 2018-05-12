
package services;

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
		presupuesto.setAceptado(false);
		presupuesto.setGastos(new ArrayList<Gasto>());
		presupuesto.setConceptos(new ArrayList<Concepto>());
		presupuesto.setCobros(new ArrayList<Cobro>());
		presupuesto.setAgendas(new ArrayList<Agenda>());

		return presupuesto;
	}

	public PresupuestoForm createForm(final Presupuesto p) {
		final PresupuestoForm pForm = new PresupuestoForm();

		pForm.setAceptado(p.isAceptado());
		pForm.setAgendas(p.getAgendas());
		pForm.setAlbaran(p.getAlbaran());
		pForm.setCliente(p.getCliente());
		pForm.setCobros(p.getCobros());
		pForm.setCodigo(p.getCodigo());
		pForm.setConceptos(p.getConceptos());
		pForm.setDireccionObra(p.getDireccionObra());
		pForm.setFactura(p.getFactura());
		pForm.setFechaFin(p.getFechaFin());
		pForm.setFechaInicio(p.getFechaInicio());
		pForm.setGastos(p.getGastos());
		pForm.setLocalidad(p.getLocalidad());
		pForm.setObservaciones(p.getObservaciones());
		pForm.setProvincia(p.getProvincia());
		pForm.setSolicitud(p.getSolicitud());
		pForm.setTitulo(p.getTitulo());
		pForm.setId(p.getId());
		return pForm;

	}
}

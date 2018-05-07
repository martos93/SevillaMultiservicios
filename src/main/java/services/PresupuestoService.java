
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Presupuesto;
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
}

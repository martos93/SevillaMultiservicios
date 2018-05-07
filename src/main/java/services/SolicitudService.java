
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Solicitud;
import repositories.SolicitudRepository;

@Service
@Transactional
public class SolicitudService {

	@Autowired
	private SolicitudRepository presupuestoRepository;


	public SolicitudService() {
		super();
	}

	public Collection<Solicitud> findAll() {
		return this.presupuestoRepository.findAll();
	}

	public Solicitud findOne(final int id) {
		return this.presupuestoRepository.findOne(id);
	}

	public Solicitud save(final Solicitud presupuesto) {
		Assert.notNull(presupuesto);
		return this.presupuestoRepository.save(presupuesto);
	}
	public void delete(final Solicitud presupuesto) {
		this.presupuestoRepository.delete(presupuesto);
	}
}

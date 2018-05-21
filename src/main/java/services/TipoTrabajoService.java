
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.TipoTrabajo;
import repositories.TipoTrabajoRepository;

@Service
@Transactional
public class TipoTrabajoService {

	@Autowired
	private TipoTrabajoRepository tipoTrabajoRepository;


	public TipoTrabajoService() {
		super();
	}

	public Collection<TipoTrabajo> findAll() {
		return this.tipoTrabajoRepository.findAll();
	}

	public TipoTrabajo findOne(final int id) {
		return this.tipoTrabajoRepository.findOne(id);
	}

	public TipoTrabajo save(final TipoTrabajo tipoTrabajo) {
		Assert.notNull(tipoTrabajo);
		return this.tipoTrabajoRepository.save(tipoTrabajo);
	}
	public void delete(final TipoTrabajo tipoTrabajo) {
		this.tipoTrabajoRepository.delete(tipoTrabajo);
	}
}

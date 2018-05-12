
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Tarea;
import repositories.TareaRepository;

@Service
@Transactional
public class TareaService {

	@Autowired
	private TareaRepository tareaRepository;


	public TareaService() {
		super();
	}

	public Collection<Tarea> findAll() {
		return this.tareaRepository.findAll();
	}

	public Tarea findOne(final int id) {
		return this.tareaRepository.findOne(id);
	}

	public Tarea save(final Tarea tarea) {
		Assert.notNull(tarea);
		return this.tareaRepository.save(tarea);
	}
	public void delete(final Tarea tarea) {
		this.tareaRepository.delete(tarea);
	}
}

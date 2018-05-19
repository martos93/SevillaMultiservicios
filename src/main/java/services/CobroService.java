
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Cobro;
import repositories.CobroRepository;

@Service
@Transactional
public class CobroService {

	@Autowired
	private CobroRepository cobroRepository;


	public CobroService() {
		super();
	}

	public Collection<Cobro> findAll() {
		return this.cobroRepository.findAll();
	}

	public Cobro findOne(final int id) {
		return this.cobroRepository.findOne(id);
	}

	public Cobro save(final Cobro cobro) {
		Assert.notNull(cobro);
		return this.cobroRepository.save(cobro);
	}
	public void delete(final Cobro cobro) {
		this.cobroRepository.delete(cobro);
	}

}

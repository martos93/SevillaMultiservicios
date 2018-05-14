
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Gasto;
import repositories.GastoRepository;

@Service
@Transactional
public class GastoService {

	@Autowired
	private GastoRepository gastoRepository;


	public GastoService() {
		super();
	}

	public Collection<Gasto> findAll() {
		return this.gastoRepository.findAll();
	}

	public Gasto findOne(final int id) {
		return this.gastoRepository.findOne(id);
	}

	public Gasto save(final Gasto gasto) {
		Assert.notNull(gasto);
		return this.gastoRepository.save(gasto);
	}
	public void delete(final Gasto gasto) {
		this.gastoRepository.delete(gasto);
	}
}

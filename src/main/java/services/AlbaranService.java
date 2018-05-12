
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Albaran;
import repositories.AlbaranRepository;

@Service
@Transactional
public class AlbaranService {

	@Autowired
	private AlbaranRepository albaranRepository;


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
}


package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.IVA;
import repositories.IVARepository;

@Service
@Transactional
public class IVAService {

	@Autowired
	private IVARepository ivaRepository;


	public Collection<IVA> findAll() {
		return this.ivaRepository.findAll();
	}

	public IVA findOne(final int id) {
		return this.ivaRepository.findOne(id);
	}

	public IVA save(final IVA iva) {
		Assert.notNull(iva);
		//		this.actorService.checkIsLogged();
		return this.ivaRepository.save(iva);
	}
	public void delete(final IVA iva) {
		this.ivaRepository.delete(iva);
	}
}

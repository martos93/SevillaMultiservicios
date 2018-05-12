
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Concepto;
import repositories.ConceptoRepository;

@Service
@Transactional
public class ConceptoService {

	@Autowired
	private ConceptoRepository conceptoRepository;


	public ConceptoService() {
		super();
	}

	public Collection<Concepto> findAll() {
		return this.conceptoRepository.findAll();
	}

	public Concepto findOne(final int id) {
		return this.conceptoRepository.findOne(id);
	}

	public Concepto save(final Concepto concepto) {
		Assert.notNull(concepto);
		return this.conceptoRepository.save(concepto);
	}
	public void delete(final Concepto concepto) {
		this.conceptoRepository.delete(concepto);
	}
}

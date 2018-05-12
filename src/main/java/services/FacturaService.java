
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Factura;
import repositories.FacturaRepository;

@Service
@Transactional
public class FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;


	public FacturaService() {
		super();
	}

	public Collection<Factura> findAll() {
		return this.facturaRepository.findAll();
	}

	public Factura findOne(final int id) {
		return this.facturaRepository.findOne(id);
	}

	public Factura save(final Factura factura) {
		Assert.notNull(factura);
		return this.facturaRepository.save(factura);
	}
	public void delete(final Factura factura) {
		this.facturaRepository.delete(factura);
	}
}


package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Agenda;
import repositories.AgendaRepository;

@Service
@Transactional
public class AgendaService {

	@Autowired
	private AgendaRepository agendaRepository;


	public Collection<Agenda> findAll() {
		return this.agendaRepository.findAll();
	}

	public Agenda findOne(final int id) {
		return this.agendaRepository.findOne(id);
	}

	public Agenda save(final Agenda agenda) {
		Assert.notNull(agenda);
		//		this.actorService.checkIsLogged();
		return this.agendaRepository.save(agenda);
	}
	public void delete(final Agenda agenda) {
		this.agendaRepository.delete(agenda);
	}

}

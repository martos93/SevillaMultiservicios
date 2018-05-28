
package services;

import java.util.ArrayList;
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
	private SolicitudRepository solicitudRepository;


	public SolicitudService() {
		super();
	}

	public Collection<Solicitud> findAll() {
		return this.solicitudRepository.findAll();
	}

	public Collection<Solicitud> findAllOrderFecha() {
		return this.solicitudRepository.findAllOrderFecha();
	}

	public Solicitud findOne(final int id) {
		return this.solicitudRepository.findOne(id);
	}

	public Solicitud save(final Solicitud solicitud) {
		Assert.notNull(solicitud);
		return this.solicitudRepository.save(solicitud);
	}
	public void delete(final Solicitud solicitud) {
		this.solicitudRepository.delete(solicitud);
	}

	public ArrayList<Solicitud> solicitudesSinLeerGestor() {

		return (ArrayList<Solicitud>) this.solicitudRepository.solicitudesSinLeerGestor();
	}

	public ArrayList<Solicitud> solicitudesSinLeerCliente() {

		return (ArrayList<Solicitud>) this.solicitudRepository.solicitudesSinLeerCliente();
	}

	public ArrayList<Solicitud> solicitudesCliente(final int id) {
		return (ArrayList<Solicitud>) this.solicitudRepository.solicitudesCliente(id);
	}
}


package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {

	@Query("select s from Solicitud s order by s.fechaCreacion DESC")
	Collection<Solicitud> findAllOrderFecha();

	@Query("select s from Solicitud s where s.cliente.id = ?1 order by s.fechaCreacion DESC")
	Collection<Solicitud> solicitudesCliente(int id);

	@Query("select s from Solicitud s where s.leidoGestor = false")
	Collection<Solicitud> solicitudesSinLeerGestor();

	@Query("select s from Solicitud s where s.leidoCliente = false")
	Collection<Solicitud> solicitudesSinLeerCliente();

}

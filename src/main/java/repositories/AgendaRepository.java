
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

}

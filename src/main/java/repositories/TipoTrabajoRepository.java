
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.TipoTrabajo;

@Repository
public interface TipoTrabajoRepository extends JpaRepository<TipoTrabajo, Integer> {

}


package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Presupuesto;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Integer> {

}

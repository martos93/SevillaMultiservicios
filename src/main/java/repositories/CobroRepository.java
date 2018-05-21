
package repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cobro;

@Repository
public interface CobroRepository extends JpaRepository<Cobro, Integer> {

	@Query("select c from Presupuesto p join p.cobros c where p.id = ?1 order by c.fechaCreacion ASC")
	ArrayList<Cobro> obtenerCobrosPorFecha(int id);

}

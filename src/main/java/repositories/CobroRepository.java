
package repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cobro;

@Repository
public interface CobroRepository extends JpaRepository<Cobro, Integer> {

	@Query("select distinct p.cobros from Presupuesto p join p.cobros c where p.id = ?1 order by CONVERT(DATE, c.fechaCreacion) ASC")
	ArrayList<Cobro> obtenerCobrosPorFecha(int id);

}

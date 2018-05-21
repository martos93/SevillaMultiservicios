
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

	@Query("select e from Empleado e where e.userAccount.username = ?1 ")
	Empleado obtenerEmpleadoLogueado(String username);

}

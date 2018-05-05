
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Cobro;

@Repository
public interface CobroRepository extends JpaRepository<Cobro, Integer> {

}

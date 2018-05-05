
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Concepto;

@Repository
public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {

}

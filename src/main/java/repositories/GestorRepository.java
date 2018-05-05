
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Gestor;

@Repository
public interface GestorRepository extends JpaRepository<Gestor, Integer> {

}

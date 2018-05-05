
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

}

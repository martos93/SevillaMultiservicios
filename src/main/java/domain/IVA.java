
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class IVA extends DomainEntity {

	// Atributos
	private Integer porcentaje;


	public Integer getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(final Integer porcentaje) {
		this.porcentaje = porcentaje;
	}

}

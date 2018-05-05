
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class IVA extends DomainEntity {

	// Atributos
	private Integer porcentaje;


	@NotNull
	public Integer getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(final Integer porcentaje) {
		this.porcentaje = porcentaje;
	}

}


package domain;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Factura extends DomainEntity {

	// Atributos
	private BigDecimal	importeTotalConIVA;
	private BigDecimal	importeTotalSinIVA;


	@NotNull
	public BigDecimal getImporteTotalSinIVA() {
		return this.importeTotalSinIVA;
	}
	public void setImporteTotalSinIVA(final BigDecimal importeTotalSinIVA) {
		this.importeTotalSinIVA = importeTotalSinIVA;
	}

	@NotNull
	public BigDecimal getImporteTotalConIVA() {
		return this.importeTotalConIVA;
	}
	public void setImporteTotalConIVA(final BigDecimal importeTotalConIVA) {
		this.importeTotalConIVA = importeTotalConIVA;
	}


	// Relaciones

	private Presupuesto				presupuesto;
	private Collection<Concepto>	conceptos;


	@Valid
	@OneToOne(optional = false)
	public Presupuesto getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(final Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Concepto> getConceptos() {
		return this.conceptos;
	}

	public void setConceptos(final Collection<Concepto> conceptos) {
		this.conceptos = conceptos;
	}

}

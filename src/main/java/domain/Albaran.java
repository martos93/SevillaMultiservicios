
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
public class Albaran extends DomainEntity {

	// Atributos
	private BigDecimal	importeTotalSinIVA;
	private Boolean		leido;
	private Boolean		terminado;


	@NotNull
	public BigDecimal getImporteTotalSinIVA() {
		return this.importeTotalSinIVA;
	}
	public void setImporteTotalSinIVA(final BigDecimal importeTotalSinIVA) {
		this.importeTotalSinIVA = importeTotalSinIVA;
	}


	// Relaciones
	private Collection<Concepto>	conceptos;
	private Presupuesto				presupuesto;


	@NotNull
	@Valid
	@OneToMany
	public Collection<Concepto> getConceptos() {
		return this.conceptos;
	}
	public void setConceptos(final Collection<Concepto> conceptos) {
		this.conceptos = conceptos;
	}

	@Valid
	@OneToOne(optional = false)
	public Presupuesto getPresupuesto() {
		return this.presupuesto;
	}
	public void setPresupuesto(final Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}
	public Boolean getLeido() {
		return leido;
	}
	public void setLeido(Boolean leido) {
		this.leido = leido;
	}
	public Boolean getTerminado() {
		return terminado;
	}
	public void setTerminado(Boolean terminado) {
		this.terminado = terminado;
	}
}

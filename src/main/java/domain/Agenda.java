
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Agenda extends DomainEntity {

	// Atributos

	private Collection<String> entradas;


	@ElementCollection
	public Collection<String> getEntradas() {
		return this.entradas;
	}

	public void setEntradas(final Collection<String> entrada) {
		this.entradas = entrada;
	}

	// Constructor


	// Relaciones

	private Empleado	empleado;
	private Presupuesto	presupuesto;


	@Valid
	@ManyToOne(optional = true)
	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(final Empleado empleado) {
		this.empleado = empleado;
	}

	@Valid
	@ManyToOne(optional = true)
	public Presupuesto getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(final Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

}

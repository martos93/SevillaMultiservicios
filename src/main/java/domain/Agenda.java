
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Agenda extends DomainEntity {

	// Atributos

	private String	entrada;
	private Date	fecha;


	@NotBlank
	@SafeHtml
	public String getEntrada() {
		return this.entrada;
	}

	public void setEntrada(final String entrada) {
		this.entrada = entrada;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	// Constructor


	// Relaciones

	private Empleado	empleado;
	private Presupuesto	presupuesto;


	@Valid
	@ManyToOne(optional = false)
	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(final Empleado empleado) {
		this.empleado = empleado;
	}

	@Valid
	@ManyToOne(optional = false)
	public Presupuesto getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(final Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

}

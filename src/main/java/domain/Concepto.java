
package domain;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Concepto extends DomainEntity {

	// Atributos
	private String		Titulo;
	private BigDecimal	total;


	@NotBlank
	public String getTitulo() {
		return this.Titulo;
	}

	public void setTitulo(final String titulo) {
		this.Titulo = titulo;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}


	// Relaciones
	private Collection<Tarea> tareas;


	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Tarea> getTareas() {
		return this.tareas;
	}

	public void setTareas(final Collection<Tarea> tareas) {
		this.tareas = tareas;
	}
}

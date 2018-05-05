
package domain;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Tarea extends DomainEntity {

	private String		descripcion;
	private Integer		unidades;
	private BigDecimal	precioUnidad;
	private BigDecimal	subTotal;


	@NotBlank
	@SafeHtml
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	@NotNull
	public Integer getUnidades() {
		return this.unidades;
	}

	public void setUnidades(final Integer unidades) {
		this.unidades = unidades;
	}

	@NotNull
	public BigDecimal getPrecioUnidad() {
		return this.precioUnidad;
	}

	public void setPrecioUnidad(final BigDecimal precioUnidad) {
		this.precioUnidad = precioUnidad;
	}

	@NotNull
	public BigDecimal getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(final BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

}

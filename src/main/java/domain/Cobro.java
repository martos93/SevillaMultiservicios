
package domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Cobro extends DomainEntity {

	// Atributos
	private Date		fecha;
	private BigDecimal	liquidado;
	private BigDecimal	pendiente;
	private BigDecimal	total;
	private Date		fechaCreacion;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	@NotNull
	public BigDecimal getLiquidado() {
		return this.liquidado;
	}

	public void setLiquidado(final BigDecimal liquidado) {
		this.liquidado = liquidado;
	}

	@NotNull
	public BigDecimal getPendiente() {
		return this.pendiente;
	}

	public void setPendiente(final BigDecimal pendiente) {
		this.pendiente = pendiente;
	}

	@NotNull
	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}

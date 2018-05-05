
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Gasto extends DomainEntity {

	private BigDecimal	cantidad;
	private String		concepto;
	private Date		fecha;
	private String		observaciones;
	private String		proveedor;


	@NotNull
	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(final BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	@NotBlank
	@SafeHtml
	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(final String concepto) {
		this.concepto = concepto;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	@SafeHtml
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}

	@NotBlank
	@SafeHtml
	public String getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(final String proveedor) {
		this.proveedor = proveedor;
	}

}

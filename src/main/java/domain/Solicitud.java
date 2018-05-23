
package domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Solicitud extends DomainEntity {

	// Atributos
	private BigDecimal	cantidad;
	private String		descripcion;
	private String		estado;
	private boolean		leidoCliente;
	private boolean		leidoGestor;
	private String		motivoRechazo;
	private String		titulo;
	private Date		fechaCreacion;


	@NotNull
	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(final BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	@NotEmpty
	@SafeHtml
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	@NotEmpty
	@SafeHtml
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(final String estado) {
		this.estado = estado;
	}

	public boolean isLeidoCliente() {
		return this.leidoCliente;
	}

	public void setLeidoCliente(final boolean leidoCliente) {
		this.leidoCliente = leidoCliente;
	}

	public boolean isLeidoGestor() {
		return this.leidoGestor;
	}

	public void setLeidoGestor(final boolean leidoGestor) {
		this.leidoGestor = leidoGestor;
	}

	@SafeHtml
	public String getMotivoRechazo() {
		return this.motivoRechazo;
	}

	public void setMotivoRechazo(final String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	@NotEmpty
	@SafeHtml
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}


	// Relaciones

	private Cliente		cliente;
	private Presupuesto	presupuesto;
	private TipoTrabajo	tipoTrabajo;


	@Valid
	@ManyToOne(optional = false)
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	@Valid
	@OneToOne(optional = true)
	public Presupuesto getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(final Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	@NotNull
	@ManyToOne
	public TipoTrabajo getTipoTrabajo() {
		return this.tipoTrabajo;
	}

	public void setTipoTrabajo(final TipoTrabajo tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}

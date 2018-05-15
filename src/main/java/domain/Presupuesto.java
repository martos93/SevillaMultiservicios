
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Presupuesto extends DomainEntity {

	// Atributos
	private boolean	aceptado;
	private String	codigo;
	private String	direccionObra;
	private Date	fechaFin;
	private Date	fechaInicio;
	private Date	fechaObra;
	private String	localidad;
	private String	provincia;
	private String	observaciones;
	private String	titulo;


	public boolean isAceptado() {
		return this.aceptado;
	}

	public void setAceptado(final boolean aceptado) {
		this.aceptado = aceptado;
	}

	@SafeHtml
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	@SafeHtml
	public String getDireccionObra() {
		return this.direccionObra;
	}

	public void setDireccionObra(final String direccionObra) {
		this.direccionObra = direccionObra;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@SafeHtml
	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(final String localidad) {
		this.localidad = localidad;
	}

	@SafeHtml
	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(final String provincia) {
		this.provincia = provincia;
	}

	@SafeHtml
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}

	@SafeHtml
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}


	// Relaciones
	private Collection<Gasto>		gastos;
	private Collection<Concepto>	conceptos;
	private Collection<Cobro>		cobros;
	private Albaran					albaran;
	private Factura					factura;
	private Collection<Agenda>		agendas;
	private Cliente					cliente;
	private Solicitud				solicitud;


	@Valid
	@OneToMany
	public Collection<Gasto> getGastos() {
		return this.gastos;
	}

	public void setGastos(final Collection<Gasto> gastos) {
		this.gastos = gastos;
	}

	@Valid
	@OneToMany
	public Collection<Concepto> getConceptos() {
		return this.conceptos;
	}

	public void setConceptos(final Collection<Concepto> conceptos) {
		this.conceptos = conceptos;
	}

	@Valid
	@OneToMany
	public Collection<Cobro> getCobros() {
		return this.cobros;
	}

	public void setCobros(final Collection<Cobro> cobros) {
		this.cobros = cobros;
	}

	@Valid
	@OneToOne(optional = true)
	public Albaran getAlbaran() {
		return this.albaran;
	}

	public void setAlbaran(final Albaran albaran) {
		this.albaran = albaran;
	}

	@Valid
	@OneToOne(optional = true)
	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(final Factura factura) {
		this.factura = factura;
	}

	@Valid
	@OneToMany(mappedBy = "presupuesto")
	public Collection<Agenda> getAgendas() {
		return this.agendas;
	}

	public void setAgendas(final Collection<Agenda> agendas) {
		this.agendas = agendas;
	}

	@Valid
	@ManyToOne(optional = false)
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	@OneToOne(optional = true)
	public Solicitud getSolicitud() {
		return this.solicitud;
	}

	public void setSolicitud(final Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaObra() {
		return this.fechaObra;
	}

	public void setFechaObra(final Date fechaObra) {
		this.fechaObra = fechaObra;
	}

}

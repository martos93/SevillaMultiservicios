
package forms;

import java.util.Collection;
import java.util.Date;

import domain.Agenda;
import domain.Albaran;
import domain.Cliente;
import domain.Cobro;
import domain.Concepto;
import domain.Factura;
import domain.Gasto;
import domain.Solicitud;

public class PresupuestoForm {

	// Atributos
	private boolean	aceptado;
	private String	codigo;
	private String	direccionObra;
	private Date	fechaFin;
	private Date	fechaInicio;
	private String	localidad;
	private String	provincia;
	private String	observaciones;
	private String	titulo;
	private int		id;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public boolean isAceptado() {
		return this.aceptado;
	}

	public void setAceptado(final boolean aceptado) {
		this.aceptado = aceptado;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	public String getDireccionObra() {
		return this.direccionObra;
	}

	public void setDireccionObra(final String direccionObra) {
		this.direccionObra = direccionObra;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(final String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(final String provincia) {
		this.provincia = provincia;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}

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


	public Collection<Gasto> getGastos() {
		return this.gastos;
	}

	public void setGastos(final Collection<Gasto> gastos) {
		this.gastos = gastos;
	}

	public Collection<Concepto> getConceptos() {
		return this.conceptos;
	}

	public void setConceptos(final Collection<Concepto> conceptos) {
		this.conceptos = conceptos;
	}

	public Collection<Cobro> getCobros() {
		return this.cobros;
	}

	public void setCobros(final Collection<Cobro> cobros) {
		this.cobros = cobros;
	}

	public Albaran getAlbaran() {
		return this.albaran;
	}

	public void setAlbaran(final Albaran albaran) {
		this.albaran = albaran;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(final Factura factura) {
		this.factura = factura;
	}

	public Collection<Agenda> getAgendas() {
		return this.agendas;
	}

	public void setAgendas(final Collection<Agenda> agendas) {
		this.agendas = agendas;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	public Solicitud getSolicitud() {
		return this.solicitud;
	}

	public void setSolicitud(final Solicitud solicitud) {
		this.solicitud = solicitud;
	}

}


package forms;

import java.math.BigDecimal;

public class SolicitudForm {

	private BigDecimal	cantidad;
	private String		descripcion;
	private String		estado;
	private boolean		leidoCliente;
	private boolean		leidoGestor;
	private String		motivoRechazo;
	private String		titulo;
	private int			clienteId;
	private int			presupuestoId;
	private int			tipoTrabajoId;


	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(final BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

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

	public String getMotivoRechazo() {
		return this.motivoRechazo;
	}

	public void setMotivoRechazo(final String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	public int getClienteId() {
		return this.clienteId;
	}

	public void setClienteId(final int clienteId) {
		this.clienteId = clienteId;
	}

	public int getPresupuestoId() {
		return this.presupuestoId;
	}

	public void setPresupuestoId(final int presupuestoId) {
		this.presupuestoId = presupuestoId;
	}

	public int getTipoTrabajoId() {
		return this.tipoTrabajoId;
	}

	public void setTipoTrabajoId(final int tipoTrabajoId) {
		this.tipoTrabajoId = tipoTrabajoId;
	}
}

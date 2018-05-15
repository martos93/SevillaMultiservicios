
package forms;

import java.math.BigDecimal;

public class GastoForm {

	private BigDecimal	cantidad;
	private String		concepto;
	private String		fecha;
	private String		observaciones;
	private String		proveedor;
	private int			presupuestoId;
	private int			gastoId;


	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(final BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(final String concepto) {
		this.concepto = concepto;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}

	public String getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(final String proveedor) {
		this.proveedor = proveedor;
	}

	public int getPresupuestoId() {
		return this.presupuestoId;
	}

	public void setPresupuestoId(final int presupuestoId) {
		this.presupuestoId = presupuestoId;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(final String fecha) {
		this.fecha = fecha;
	}

	public int getGastoId() {
		return this.gastoId;
	}

	public void setGastoId(final int gastoId) {
		this.gastoId = gastoId;
	}

}

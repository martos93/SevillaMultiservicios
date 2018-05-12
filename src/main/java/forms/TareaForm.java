
package forms;

import java.math.BigDecimal;

public class TareaForm {

	private String		descripcion;
	private Integer		unidades;
	private BigDecimal	precioUnidad;
	private BigDecimal	subTotal;
	private int			presupuestoId;
	private int			conceptoId;
	private int			tareaId;


	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getUnidades() {
		return this.unidades;
	}

	public void setUnidades(final Integer unidades) {
		this.unidades = unidades;
	}

	public BigDecimal getPrecioUnidad() {
		return this.precioUnidad;
	}

	public void setPrecioUnidad(final BigDecimal precioUnidad) {
		this.precioUnidad = precioUnidad;
	}

	public BigDecimal getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(final BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public int getPresupuestoId() {
		return this.presupuestoId;
	}

	public void setPresupuestoId(final int presupuestoId) {
		this.presupuestoId = presupuestoId;
	}

	public int getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(final int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public int getTareaId() {
		return this.tareaId;
	}

	public void setTareaId(final int tareaId) {
		this.tareaId = tareaId;
	}
}

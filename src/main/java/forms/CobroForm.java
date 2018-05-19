
package forms;

import java.math.BigDecimal;
import java.util.Date;

public class CobroForm {

	private Date		fecha;
	private String		fechaS;
	private BigDecimal	liquidado;
	private BigDecimal	pendiente;
	private BigDecimal	total;
	private int			presupuestoId;
	private int			cobroId;


	public int getPresupuestoId() {
		return this.presupuestoId;
	}

	public void setPresupuestoId(final int presupuestoId) {
		this.presupuestoId = presupuestoId;
	}

	public int getCobroId() {
		return this.cobroId;
	}

	public void setCobroId(final int cobroId) {
		this.cobroId = cobroId;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getLiquidado() {
		return this.liquidado;
	}

	public void setLiquidado(final BigDecimal liquidado) {
		this.liquidado = liquidado;
	}

	public BigDecimal getPendiente() {
		return this.pendiente;
	}

	public void setPendiente(final BigDecimal pendiente) {
		this.pendiente = pendiente;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}

	public String getFechaS() {
		return this.fechaS;
	}

	public void setFechaS(final String fechaS) {
		this.fechaS = fechaS;
	}

}

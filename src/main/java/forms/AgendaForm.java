
package forms;

import java.util.ArrayList;

public class AgendaForm {

	private ArrayList<String>	entradas;
	private int					empleadoId;
	private int					presupuestoId;
	private int					agendaId;
	private String				fecha;
	private String				entradaTexto;


	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(final String fecha) {
		this.fecha = fecha;
	}

	public String getEntradaTexto() {
		return this.entradaTexto;
	}

	public void setEntradaTexto(final String entradaTexto) {
		this.entradaTexto = entradaTexto;
	}

	public ArrayList<String> getEntradas() {
		return this.entradas;
	}

	public void setEntradas(final ArrayList<String> entradas) {
		this.entradas = entradas;
	}

	public int getEmpleadoId() {
		return this.empleadoId;
	}

	public void setEmpleadoId(final int empleadoId) {
		this.empleadoId = empleadoId;
	}

	public int getPresupuestoId() {
		return this.presupuestoId;
	}

	public void setPresupuestoId(final int presupuestoId) {
		this.presupuestoId = presupuestoId;
	}

	public int getAgendaId() {
		return this.agendaId;
	}

	public void setAgendaId(final int agendaId) {
		this.agendaId = agendaId;
	}
}

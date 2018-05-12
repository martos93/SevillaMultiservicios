
package forms;

public class ConceptoForm {

	private String	tituloC;
	private int		presupuestoId;
	private int		clienteId;
	private int		conceptoId;


	public int getPresupuestoId() {
		return this.presupuestoId;
	}

	public void setPresupuestoId(final int presupuestoId) {
		this.presupuestoId = presupuestoId;
	}

	public int getClienteId() {
		return this.clienteId;
	}

	public void setClienteId(final int clienteId) {
		this.clienteId = clienteId;
	}

	public int getConceptoId() {
		return this.conceptoId;
	}

	public void setConceptoId(final int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getTituloC() {
		return this.tituloC;
	}

	public void setTituloC(final String tituloC) {
		this.tituloC = tituloC;
	}

}

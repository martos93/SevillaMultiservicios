
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Cliente extends Actor {

	// Relaciones
	private Collection<Presupuesto>	presupuesto;
	private Collection<Solicitud>	solicitudes;


	@Valid
	@OneToMany(mappedBy = "cliente")
	public Collection<Presupuesto> getPresupuesto() {
		return this.presupuesto;
	}
	public void setPresupuesto(final Collection<Presupuesto> presupuesto) {
		this.presupuesto = presupuesto;
	}

	@Valid
	@OneToMany(mappedBy = "cliente")
	public Collection<Solicitud> getSolicitudes() {
		return this.solicitudes;
	}
	public void setSolicitudes(final Collection<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

}

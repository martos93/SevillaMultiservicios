
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Empleado extends Actor {

	// Relaciones
	private Collection<Agenda> agendas;


	//
	//	public Empleado(final EmpleadoForm empleadoForm) {
	//		this.nombre = empleadoForm.getNombre();
	//		this.apellidos = empleadoForm.getApellidos();
	//		this.codigoPostal = empleadoForm.getCodigoPostal();
	//		this.direccion = empleadoForm.getDireccion();
	//		this.email = empleadoForm.getEmail();
	//		this.localidad = empleadoForm.getLocalidad();
	//		this.provincia = empleadoForm.getProvincia();
	//		if (!empleadoForm.getRefCatastro().isEmpty())
	//			this.refCatastro = empleadoForm.getRefCatastro();
	//		this.identificacion = empleadoForm.getIdentificacion();
	//	}

	@Valid
	@OneToMany(mappedBy = "empleado")
	public Collection<Agenda> getAgendas() {
		return this.agendas;
	}

	public void setAgendas(final Collection<Agenda> agendas) {
		this.agendas = agendas;
	}
}

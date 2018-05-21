
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Atributos
	protected String	nombre;
	protected String	apellidos;
	protected String	codigoPostal;
	protected String	direccion;
	protected String	email;
	protected String	localidad;
	protected String	provincia;
	protected String	refCatastro;
	protected String	identificacion;
	protected String	telefono;


	@NotBlank
	@SafeHtml
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	@NotBlank
	@SafeHtml
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	@NotBlank
	@SafeHtml
	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(final String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@NotBlank
	@SafeHtml
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	@Email
	@NotBlank
	@SafeHtml
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@SafeHtml
	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(final String localidad) {
		this.localidad = localidad;
	}

	@NotBlank
	@SafeHtml
	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(final String provincia) {
		this.provincia = provincia;
	}

	@NotBlank
	@SafeHtml
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}

	public String getRefCatastro() {
		return this.refCatastro;
	}

	public void setRefCatastro(final String refCatastro) {
		this.refCatastro = refCatastro;
	}

	@NotBlank
	@SafeHtml
	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(final String identificacion) {
		this.identificacion = identificacion;
	}
	//Relaciones


	private UserAccount userAccount;


	@Valid
	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}


package forms;

import java.io.Serializable;

public class EmpleadoForm implements Serializable {

	private static final long	serialVersionUID	= -3476856080759986487L;
	private String				identificacion;
	private String				nombre;
	private String				apellidos;
	private String				codigoPostal;
	private String				direccion;
	private String				localidad;
	private String				provincia;
	private String				email;
	private String				password;
	private String				passwordRepeat;
	private String				usuario;
	private int					empleadoId;
	private String				telefono;


	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return this.passwordRepeat;
	}

	public void setPasswordRepeat(final String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(final String identificacion) {
		this.identificacion = identificacion;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(final String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(final String direccion) {
		this.direccion = direccion;
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

	public static long getSerialversionuid() {
		return EmpleadoForm.serialVersionUID;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public int getEmpleadoId() {
		return this.empleadoId;
	}

	public void setEmpleadoId(final int empleadoId) {
		this.empleadoId = empleadoId;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

}

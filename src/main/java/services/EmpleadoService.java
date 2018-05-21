
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Agenda;
import domain.Empleado;
import forms.EmpleadoForm;
import repositories.EmpleadoRepository;
import security.UserAccount;

@Service
@Transactional
public class EmpleadoService {

	@Autowired
	private EmpleadoRepository	empleadoRepository;

	@Autowired
	private ActorService		actorService;


	public EmpleadoService() {
		super();
	}

	public Collection<Empleado> findAll() {
		return this.empleadoRepository.findAll();
	}

	public Empleado findOne(final int id) {
		return this.empleadoRepository.findOne(id);
	}

	public Empleado save(final Empleado empleado) {
		Assert.notNull(empleado);
		//		this.actorService.checkIsLogged();
		return this.empleadoRepository.save(empleado);
	}
	public void delete(final Empleado empleado) {
		this.empleadoRepository.delete(empleado);
	}

	public Empleado obtenerEmpleadoLogueado(final String username) {
		return this.empleadoRepository.obtenerEmpleadoLogueado(username);
	}

	public Empleado create(final EmpleadoForm empleadoForm) {
		final Empleado empleado = new Empleado();
		empleado.setNombre(empleadoForm.getNombre());
		empleado.setApellidos(empleadoForm.getApellidos());
		empleado.setIdentificacion(empleadoForm.getIdentificacion());
		empleado.setCodigoPostal(empleadoForm.getCodigoPostal());
		empleado.setDireccion(empleadoForm.getDireccion());
		empleado.setLocalidad(empleadoForm.getLocalidad());
		empleado.setProvincia(empleadoForm.getProvincia());
		empleado.setEmail(empleadoForm.getEmail());
		empleado.setTelefono(empleadoForm.getTelefono());
		final UserAccount userAccount = this.actorService.userAccountEmpleado();
		userAccount.setUsername(empleadoForm.getUsuario());
		final Md5PasswordEncoder enc = new Md5PasswordEncoder();
		userAccount.setPassword(enc.encodePassword(empleadoForm.getPassword(), null));
		empleado.setUserAccount(userAccount);
		empleado.setAgendas(new ArrayList<Agenda>());
		return empleado;
	}

}

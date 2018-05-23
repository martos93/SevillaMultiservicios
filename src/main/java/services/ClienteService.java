
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Cliente;
import domain.Presupuesto;
import domain.Solicitud;
import forms.ClienteForm;
import repositories.ClienteRepository;
import security.UserAccount;

@Service
@Transactional
public class ClienteService {

	@Autowired
	private ClienteRepository	clienteRepository;

	@Autowired
	private ActorService		actorService;


	public ClienteService() {
		super();
	}

	public Collection<Cliente> findAll() {
		return this.clienteRepository.findAll();
	}

	public Cliente findOne(final int id) {
		return this.clienteRepository.findOne(id);
	}

	public Cliente save(final Cliente cliente) {
		Assert.notNull(cliente);
		return this.clienteRepository.save(cliente);
	}
	public void delete(final Cliente cliente) {
		this.clienteRepository.delete(cliente);
	}

	public Cliente create(final ClienteForm clienteForm) {
		final Cliente cliente = new Cliente();
		cliente.setNombre(clienteForm.getNombre());
		cliente.setApellidos(clienteForm.getApellidos());
		cliente.setIdentificacion(clienteForm.getIdentificacion());
		cliente.setCodigoPostal(clienteForm.getCodigoPostal());
		cliente.setDireccion(clienteForm.getDireccion());
		cliente.setLocalidad(clienteForm.getLocalidad());
		cliente.setProvincia(clienteForm.getProvincia());
		cliente.setEmail(clienteForm.getEmail());
		cliente.setTelefono(clienteForm.getTelefono());
		final UserAccount userAccount = this.actorService.userAccountCliente();
		userAccount.setUsername(clienteForm.getUsuario());
		final Md5PasswordEncoder enc = new Md5PasswordEncoder();
		userAccount.setPassword(enc.encodePassword(clienteForm.getPassword(), null));
		cliente.setUserAccount(userAccount);
		cliente.setPresupuesto(new ArrayList<Presupuesto>());
		cliente.setSolicitudes(new ArrayList<Solicitud>());
		return cliente;
	}

	public Cliente findByPrincipal(final int id) {
		return this.clienteRepository.findByPrincipal(id);
	}
}

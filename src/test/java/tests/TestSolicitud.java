
package tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Cliente;
import domain.Solicitud;
import domain.TipoTrabajo;
import security.LoginService;
import services.ClienteService;
import services.SolicitudService;
import services.TipoTrabajoService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestSolicitud extends AbstractTest {

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private TipoTrabajoService	tipoTrabajoService;

	@Autowired
	private SolicitudService	solicitudService;


	@Test
	public void createSolicitud() {
		final Object[][] testingData = {
			{
				"Descripcion solicitud", "Titulo solicitud", "ENVIADO", new BigDecimal(200), null
			}, {
				"Descripcion solicitud", "Titulo solicitud", "ENVIADO", null, IllegalArgumentException.class
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.crearSolicitud((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (BigDecimal) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void crearSolicitud(final String descripcion, final String titulo, final String estado, final BigDecimal cantidad, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate("cliente");

			final Cliente cliente = this.clienteService.findByPrincipal(LoginService.getPrincipal().getId());

			Solicitud s = new Solicitud();
			s.setDescripcion(descripcion);
			s.setTitulo(titulo);
			s.setEstado(estado);
			Assert.isTrue(cantidad != null);
			s.setCantidad(cantidad);
			s.setCliente(cliente);
			s.setLeidoCliente(false);
			s.setLeidoGestor(false);
			s.setTipoTrabajo(((ArrayList<TipoTrabajo>) this.tipoTrabajoService.findAll()).get(0));
			s.setFechaCreacion(new Date(System.currentTimeMillis()));
			s.setLeidoGestor(false);
			s.setLeidoCliente(true);
			s = this.solicitudService.save(s);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

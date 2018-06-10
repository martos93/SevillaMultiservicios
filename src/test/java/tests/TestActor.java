/*
 * SampleTest.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package tests;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Cliente;
import domain.Empleado;
import services.ClienteService;
import services.EmpleadoService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestActor extends AbstractTest {

	@Autowired
	private ClienteService	clienteService;

	@Autowired
	private EmpleadoService	empleadoService;


	@Test
	public void createActor() {
		final Object[][] testingData = {
			{
				false, "Manuel", "Díaz Martín", "41710", "Calle Sarmiento", "Utrera", "Sevilla", "48122003T", "manolete@gmail.com", "659183896", null
			}, {
				true, "Manuel", "Díaz Martín", "41710", "Calle Sarmiento", "Utrera", "Sevilla", "48122003T", "manolete@gmail.com", "659183896", null
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.crearActor((boolean) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void crearActor(final boolean clienteEmpleado, final String nombre, final String apellidos, final String codPostal, final String direccion, final String localidad, final String provincia, final String dni, final String correo,
		final String telefono, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			if (!clienteEmpleado) {
				Empleado usuario = new Empleado();
				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuario.setCodigoPostal(codPostal);
				usuario.setDireccion(direccion);
				usuario.setLocalidad(localidad);
				usuario.setProvincia(provincia);
				usuario.setIdentificacion(dni);
				usuario.setEmail(correo);
				usuario.setTelefono(telefono);
				usuario = this.empleadoService.save(usuario);
			} else {
				Cliente usuario = new Cliente();
				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuario.setCodigoPostal(codPostal);
				usuario.setDireccion(direccion);
				usuario.setLocalidad(localidad);
				usuario.setProvincia(provincia);
				usuario.setIdentificacion(dni);
				usuario.setEmail(correo);
				usuario.setTelefono(telefono);
				usuario = this.clienteService.save(usuario);
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

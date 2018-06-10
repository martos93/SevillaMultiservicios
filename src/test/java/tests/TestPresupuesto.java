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
import org.springframework.util.Assert;

import domain.Cliente;
import domain.Presupuesto;
import domain.Solicitud;
import forms.PresupuestoForm;
import services.ActorService;
import services.ClienteService;
import services.PresupuestoService;
import services.SolicitudService;
import services.TipoTrabajoService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestPresupuesto extends AbstractTest {

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private ClienteService		clienteService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TipoTrabajoService	tipoTrabajoService;

	@Autowired
	private SolicitudService	solicitudService;


	@Test
	public void createPresupuesto() {
		final Object[][] testingData = {
			{
				false, "PL050", "Calle amalgama", "Utrera", "Sevilla", "Este presupuesto tiene validez de dos días", "Obra de baño", "41710", null
			}, {
				true, "PL011", "Calle amalgama", "Utrera", "Sevilla", "Este presupuesto tiene validez de dos días", "Obra de baño", "41710", IllegalArgumentException.class
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.crearPresupuesto((boolean) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void crearPresupuesto(final boolean aceptado, final String codigo, final String direccion, final String localidad, final String provincia, final String observaciones, final String titulo, final String codigoPostal, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final PresupuestoForm presupuestoForm = new PresupuestoForm();

			presupuestoForm.setAceptado(aceptado);
			presupuestoForm.setCodigo(codigo);
			presupuestoForm.setDireccionObra(direccion);
			presupuestoForm.setLocalidad(localidad);
			presupuestoForm.setProvincia(provincia);
			presupuestoForm.setObservaciones(observaciones);
			presupuestoForm.setTitulo(titulo);
			presupuestoForm.setCodigoPostal(codigoPostal);

			this.authenticate("gestor");

			this.actorService.checkGestor();
			Solicitud solicitud = null;
			final Cliente cliente = this.clienteService.findOne(presupuestoForm.getClienteId());
			Presupuesto presupuesto = this.presupuestoService.create();
			presupuesto.setCliente(cliente);

			presupuesto.setTitulo(presupuestoForm.getTitulo());
			presupuesto.setCodigo(presupuestoForm.getCodigo());
			presupuesto.setDireccionObra(presupuestoForm.getDireccionObra());
			presupuesto.setLocalidad(presupuestoForm.getLocalidad());
			presupuesto.setProvincia(presupuestoForm.getProvincia());
			presupuesto.setTipoTrabajo(this.tipoTrabajoService.findOne(presupuestoForm.getTipoTrabajo()));
			presupuesto.setCodigoPostal(presupuestoForm.getCodigoPostal());

			if (aceptado)
				presupuesto.setFechaInicio(null);
			if (presupuestoForm.getSolicitudId() != null) {
				presupuesto.setSolicitudTemporal(presupuestoForm.getSolicitudId());
				solicitud = this.solicitudService.findOne(presupuestoForm.getSolicitudId());
			} else
				presupuesto.setSolicitudTemporal(0);

			presupuesto = this.presupuestoService.save(presupuesto);
			if (solicitud != null) {
				solicitud.setPresupuestoTemporal(presupuesto.getId());
				solicitud.setEstado("PENDIENTE");
				solicitud.setLeidoCliente(false);
				solicitud = this.solicitudService.save(solicitud);

			}

			if (aceptado)
				Assert.isTrue(presupuesto.getFechaInicio() != null);
			Assert.isTrue(presupuesto.getId() != 0);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

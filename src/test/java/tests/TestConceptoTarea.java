
package tests;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Concepto;
import domain.Presupuesto;
import domain.Tarea;
import services.ActorService;
import services.ConceptoService;
import services.PresupuestoService;
import services.TareaService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestConceptoTarea extends AbstractTest {

	@Autowired
	private PresupuestoService	presupuestoService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ConceptoService		conceptoService;

	@Autowired
	private TareaService		tareaService;


	@Test
	public void createConceptoTarea() {
		final Object[][] testingData = {
			{
				"Concepto 1", "Descripción Tarea 1", 2, new BigDecimal(2), null
			}, {
				"Concepto 2", null, 2, new BigDecimal(2), IllegalArgumentException.class
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.crearConceptoTarea((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (BigDecimal) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void crearConceptoTarea(final String descConcepto, final String descTarea, final Integer unidades, final BigDecimal precioUd, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate("gestor");
			this.actorService.checkGestor();
			Presupuesto p = ((ArrayList<Presupuesto>) this.presupuestoService.findAll()).get(0);
			Concepto c = new Concepto();
			c.setTitulo(descConcepto);
			c = this.conceptoService.save(c);
			p.getConceptos().add(c);
			p = this.presupuestoService.save(p);

			Tarea t = new Tarea();
			t.setDescripcion(descTarea);
			t.setPrecioUnidad(precioUd);
			t.setUnidades(unidades);
			t.setSubTotal(precioUd.multiply(new BigDecimal(unidades)));
			Assert.isTrue(t.getDescripcion() != null && !t.getDescripcion().isEmpty());
			t = this.tareaService.save(t);

			c.setTareas(new ArrayList<Tarea>());
			c.getTareas().add(t);
			BigDecimal totalConcepto = new BigDecimal(0);
			for (final Tarea ta : c.getTareas())
				totalConcepto = totalConcepto.add(ta.getSubTotal());
			c.setTotal(totalConcepto);
			c = this.conceptoService.save(c);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

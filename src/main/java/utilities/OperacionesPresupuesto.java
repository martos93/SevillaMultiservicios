
package utilities;

import java.math.BigDecimal;

import domain.Concepto;
import domain.Presupuesto;

public class OperacionesPresupuesto {

	public static BigDecimal totalPresupuesto(final Presupuesto p) {
		BigDecimal totalPresupuesto = new BigDecimal(0);
		for (final Concepto ac : p.getConceptos())
			if (ac.getTareas() != null && !ac.getTareas().isEmpty())
				totalPresupuesto = totalPresupuesto.add(ac.getTotal());

		return totalPresupuesto;
	}
}

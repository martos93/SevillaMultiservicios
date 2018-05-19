
package utilities;

import java.util.Comparator;

import domain.Cobro;

public class CobroComparator implements Comparator<Cobro> {

	@Override
	public int compare(final Cobro arg0, final Cobro arg1) {

		return arg0.getFecha().compareTo(arg1.getFecha());
	}

}

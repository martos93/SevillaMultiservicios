
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.Presupuesto;

@Component
@Transactional
public class PresupuestoToStringConverter implements Converter<Presupuesto, String> {

	@Override
	public String convert(final Presupuesto arg0) {
		Assert.notNull(arg0);

		String string;
		string = String.valueOf(arg0.getId());

		return string;
	}

}

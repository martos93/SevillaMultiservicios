
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.TipoTrabajo;

@Component
@Transactional
public class TipoTrabajoToStringConverter implements Converter<TipoTrabajo, String> {

	@Override
	public String convert(final TipoTrabajo arg0) {
		Assert.notNull(arg0);

		String string;
		string = String.valueOf(arg0.getId());

		return string;
	}

}

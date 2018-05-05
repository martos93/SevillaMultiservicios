
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.Cobro;

@Component
@Transactional
public class CobroToStringConverter implements Converter<Cobro, String> {

	@Override
	public String convert(final Cobro arg0) {
		Assert.notNull(arg0);

		String string;
		string = String.valueOf(arg0.getId());

		return string;
	}

}

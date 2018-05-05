
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.Agenda;

@Component
@Transactional
public class AgendaToStringConverter implements Converter<Agenda, String> {

	@Override
	public String convert(final Agenda arg0) {
		Assert.notNull(arg0);

		String string;
		string = String.valueOf(arg0.getId());

		return string;
	}

}

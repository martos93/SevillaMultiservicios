
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.IVA;

@Component
@Transactional
public class IVAToStringConverter implements Converter<IVA, String> {

	@Override
	public String convert(final IVA arg0) {
		Assert.notNull(arg0);

		String string;
		string = String.valueOf(arg0.getId());

		return string;
	}

}

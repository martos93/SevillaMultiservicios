
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Factura;
import repositories.FacturaRepository;

@Component
@Transactional
public class StringToFacturaConverter implements Converter<String, Factura> {

	@Autowired
	private FacturaRepository bannerRepository;


	@Override
	public Factura convert(final String string) {
		final Factura res;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				res = null;
			else {
				id = Integer.valueOf(string);
				res = this.bannerRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}

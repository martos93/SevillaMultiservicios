
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Cobro;
import repositories.CobroRepository;

@Component
@Transactional
public class StringToCobroConverter implements Converter<String, Cobro> {

	@Autowired
	private CobroRepository bannerRepository;


	@Override
	public Cobro convert(final String string) {
		final Cobro res;
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

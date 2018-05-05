
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Gasto;
import repositories.GastoRepository;

@Component
@Transactional
public class StringToGastoConverter implements Converter<String, Gasto> {

	@Autowired
	private GastoRepository bannerRepository;


	@Override
	public Gasto convert(final String string) {
		final Gasto res;
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

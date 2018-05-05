
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Albaran;
import repositories.AlbaranRepository;

@Component
@Transactional
public class StringToAlbaranConverter implements Converter<String, Albaran> {

	@Autowired
	private AlbaranRepository bannerRepository;


	@Override
	public Albaran convert(final String string) {
		final Albaran res;
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

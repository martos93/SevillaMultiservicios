
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Gestor;
import repositories.GestorRepository;

@Component
@Transactional
public class StringToGestorConverter implements Converter<String, Gestor> {

	@Autowired
	private GestorRepository bannerRepository;


	@Override
	public Gestor convert(final String string) {
		final Gestor res;
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

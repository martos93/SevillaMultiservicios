
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Tarea;
import repositories.TareaRepository;

@Component
@Transactional
public class StringToTareaConverter implements Converter<String, Tarea> {

	@Autowired
	private TareaRepository bannerRepository;


	@Override
	public Tarea convert(final String string) {
		final Tarea res;
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

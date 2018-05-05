
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.TipoTrabajo;
import repositories.TipoTrabajoRepository;

@Component
@Transactional
public class StringToTipoTrabajoConverter implements Converter<String, TipoTrabajo> {

	@Autowired
	private TipoTrabajoRepository bannerRepository;


	@Override
	public TipoTrabajo convert(final String string) {
		final TipoTrabajo res;
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

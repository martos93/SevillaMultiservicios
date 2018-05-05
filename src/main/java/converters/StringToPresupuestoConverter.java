
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Presupuesto;
import repositories.PresupuestoRepository;

@Component
@Transactional
public class StringToPresupuestoConverter implements Converter<String, Presupuesto> {

	@Autowired
	private PresupuestoRepository bannerRepository;


	@Override
	public Presupuesto convert(final String string) {
		final Presupuesto res;
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

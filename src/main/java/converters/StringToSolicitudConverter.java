
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Solicitud;
import repositories.SolicitudRepository;

@Component
@Transactional
public class StringToSolicitudConverter implements Converter<String, Solicitud> {

	@Autowired
	private SolicitudRepository bannerRepository;


	@Override
	public Solicitud convert(final String string) {
		final Solicitud res;
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

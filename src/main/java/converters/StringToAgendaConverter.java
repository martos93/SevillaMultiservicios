
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Agenda;
import repositories.AgendaRepository;

@Component
@Transactional
public class StringToAgendaConverter implements Converter<String, Agenda> {

	@Autowired
	private AgendaRepository bannerRepository;


	@Override
	public Agenda convert(final String string) {
		final Agenda res;
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


package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Concepto;
import repositories.ConceptoRepository;

@Component
@Transactional
public class StringToConceptoConverter implements Converter<String, Concepto> {

	@Autowired
	private ConceptoRepository bannerRepository;


	@Override
	public Concepto convert(final String string) {
		final Concepto res;
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

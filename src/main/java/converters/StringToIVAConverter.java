
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.IVA;
import repositories.IVARepository;

@Component
@Transactional
public class StringToIVAConverter implements Converter<String, IVA> {

	@Autowired
	private IVARepository bannerRepository;


	@Override
	public IVA convert(final String string) {
		final IVA res;
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

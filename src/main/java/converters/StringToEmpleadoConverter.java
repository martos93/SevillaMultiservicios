
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Empleado;
import repositories.EmpleadoRepository;

@Component
@Transactional
public class StringToEmpleadoConverter implements Converter<String, Empleado> {

	@Autowired
	private EmpleadoRepository bannerRepository;


	@Override
	public Empleado convert(final String string) {
		final Empleado res;
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

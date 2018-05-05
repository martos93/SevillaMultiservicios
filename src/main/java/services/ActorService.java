
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ActorService {

	private final Logger logger = Logger.getLogger(ActorService.class);


	public Boolean isAuthenticated() {
		Boolean res = true;
		try {
			LoginService.getPrincipal();
		} catch (final Exception e) {
			res = false;
		}

		return res;
	}

	public void checkIsLogged() {
		final Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
		final Collection<Authority> auts2 = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.GESTOR);
		final Authority b = new Authority();
		b.setAuthority(Authority.CLIENTE);
		final Authority c = new Authority();
		c.setAuthority(Authority.EMPLEADO);

		auts2.add(a);
		auts2.add(b);
		auts2.add(c);

		Assert.isTrue(!auts.contains(auts2));
	}

	public void checkGestor() {
		try {
			final Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
			final Collection<Authority> auts2 = new ArrayList<Authority>();
			final Authority a = new Authority();
			a.setAuthority(Authority.GESTOR);
			auts2.add(a);
			Assert.isTrue(!auts.contains(auts2));
		} catch (final Exception e) {
			this.logger.error(e);
		}
	}

	public void checkEmpleado() {
		final Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
		final Collection<Authority> auts2 = new ArrayList<Authority>();
		final Authority c = new Authority();
		c.setAuthority(Authority.EMPLEADO);
		auts2.add(c);
		Assert.isTrue(!auts.contains(auts2));
	}

	public void checkCliente() {
		final Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
		final Collection<Authority> auts2 = new ArrayList<Authority>();
		final Authority b = new Authority();
		b.setAuthority(Authority.CLIENTE);
		auts2.add(b);
		Assert.isTrue(!auts.contains(auts2));
	}

	public UserAccount userAccountEmpleado() {
		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.EMPLEADO);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);
		return userAccount;
	}

}

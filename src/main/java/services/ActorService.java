
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

	public boolean checkGestor() throws IllegalArgumentException {
		final Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
		final Collection<Authority> auts2 = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.GESTOR);
		auts2.add(a);
		for (final Authority at : auts)
			if (at.getAuthority().equals(a.getAuthority()))
				return true;
		return false;
	}

	public boolean checkEmpleado() {
		final Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
		final Collection<Authority> auts2 = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.EMPLEADO);
		auts2.add(a);
		for (final Authority at : auts)
			if (at.getAuthority().equals(a.getAuthority()))
				return true;
		return false;
	}

	public boolean checkCliente() {
		final Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
		final Collection<Authority> auts2 = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.CLIENTE);
		auts2.add(a);
		for (final Authority at : auts)
			if (at.getAuthority().equals(a.getAuthority()))
				return true;
		return false;
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

	public UserAccount userAccountCliente() {
		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.CLIENTE);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);
		return userAccount;
	}

}

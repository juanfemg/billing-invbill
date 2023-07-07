package co.com.juan.invbill.authentication;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import co.com.juan.invbill.delegate.businessdelegate.IBusinessDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.Encrypt;

/**
 * @author Juan Felipe
 * 
 */
@Scope("singleton")
@Component("AppAuthenticationProvider")
public class AppAuthenticationProvider implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(AppAuthenticationProvider.class);

	@Autowired
	private IBusinessDelegate businessDelegator;

	private UsuarioApp usuarioApp;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String name = authentication.getName();
		String credentials = authentication.getCredentials().toString();
		final List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

		try {
			final UserDetails principal = getUser(name, credentials);
			final Authentication auth = new UsernamePasswordAuthenticationToken(principal, credentials, grantedAuths);

			SecurityContextHolder.getContext().setAuthentication(auth);

			return auth;
		} catch (Exception e) {
			log.error("the encapsulation of user information failed. An error has occurred.", e);
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private UserDetails getUser(String userName, String password) {
		boolean enabled = false;

		usuarioApp = businessDelegator.findUsuarioByID(userName);

		if (usuarioApp != null) {
			if (usuarioApp.getEstado() == StatusEnum.A) {
				enabled = true;
			} else {
				enabled = false;
			}

			if (password.equals(new Encrypt().decrypt(usuarioApp.getPassword()))) {
				enabled = true;
			} else {
				enabled = false;
			}
		}

		return new AppUserDetails(usuarioApp, userName, password, enabled);
	}

	/**
	 * @return the usuarioApp
	 */
	public UsuarioApp getUsuarioApp() {
		return usuarioApp;
	}

	/**
	 * @param usuarioApp the usuarioApp to set
	 */
	public void setUsuarioApp(UsuarioApp usuarioApp) {
		this.usuarioApp = usuarioApp;
	}

}

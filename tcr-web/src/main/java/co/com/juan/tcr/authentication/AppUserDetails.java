package co.com.juan.tcr.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import co.com.juan.tcr.model.UsuarioApp;

/**
 * @author Juan Felipe
 * 
 */
public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = -8459618414372381991L;
	private UsuarioApp usuarioApp;
	private String userName;
	private String password;
	private boolean enabled;

	public AppUserDetails(UsuarioApp usuarioApp, String userName, String password, boolean enabled) {
		super();
		this.usuarioApp = usuarioApp;
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority authority = new SimpleGrantedAuthority(usuarioApp.getRolApp().getRol());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);

		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the usuarioApp
	 */
	public UsuarioApp getUsuarioApp() {
		return usuarioApp;
	}

}

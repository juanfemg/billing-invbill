package co.com.juan.invbill.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author Juan Felipe
 */
public class AppUserDetails implements UserDetails {

    private final String userName;
    private final String password;
    private final boolean enabled;
    private final List<GrantedAuthority> grantedAuths;

    public AppUserDetails(String userName, String password, boolean enabled, List<GrantedAuthority> grantedAuths) {
        super();
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.grantedAuths = grantedAuths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuths;
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

}

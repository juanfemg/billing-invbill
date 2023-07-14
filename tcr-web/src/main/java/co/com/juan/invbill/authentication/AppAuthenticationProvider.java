package co.com.juan.invbill.authentication;

import co.com.juan.invbill.delegate.businessdelegate.IUsuarioDelegate;
import co.com.juan.invbill.enums.StatusEnum;
import co.com.juan.invbill.model.UsuarioApp;
import co.com.juan.invbill.util.security.Encryption;
import co.com.juan.invbill.util.security.IEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

/**
 * @author Juan Felipe
 */
@Singleton
@Component
public class AppAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(AppAuthenticationProvider.class);
    private final IUsuarioDelegate usuarioDelegator;
    private final IEncryption encryption;

    @Inject
    public AppAuthenticationProvider(IUsuarioDelegate usuarioDelegator, Encryption encryption) {
        this.usuarioDelegator = usuarioDelegator;
        this.encryption = encryption;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String name = authentication.getName();
        String credentials = (String) authentication.getCredentials();
        try {
            UserDetails principal = this.getUser(name, credentials);
            Authentication auth = new UsernamePasswordAuthenticationToken(principal, credentials, principal.getAuthorities());
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
        List<GrantedAuthority> grantedAuths = null;
        UsuarioApp usuarioApp = this.usuarioDelegator.findUsuarioByID(userName);

        if (usuarioApp != null && usuarioApp.getRolApp() != null) {
            if ((usuarioApp.getEstado() == StatusEnum.A) && (password.equals(this.encryption.decrypt(usuarioApp.getPassword())))) {
                enabled = true;
                grantedAuths = Collections.singletonList(new SimpleGrantedAuthority(usuarioApp.getRolApp().getRol()));
            }
        }

        return new AppUserDetails(userName, password, enabled, grantedAuths);
    }

}

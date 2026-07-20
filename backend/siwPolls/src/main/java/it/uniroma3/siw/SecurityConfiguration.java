package it.uniroma3.siw;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
@Configuration
@EnableWebSecurity 
public class SecurityConfiguration { 

    private final DataSource dataSource; 

    public SecurityConfiguration(DataSource dataSource) { 
        this.dataSource = dataSource;
    } 

    @Bean 
    public UserDetailsService userDetailsService() { 
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource); 
        manager.setUsersByUsernameQuery("SELECT username, psw, 1 as enabled FROM credential WHERE username=?");
        manager.setAuthoritiesByUsernameQuery("SELECT username, ruolo FROM credential WHERE username=?"); 
        return manager;
    }

    @Bean 
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder();
    }

    @Bean 
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception { 
    	httpSecurity.authorizeHttpRequests(authorize -> { 
    		authorize.requestMatchers(HttpMethod.GET, "/commento/**"). hasAnyAuthority(Credential.DEFAULT_ROLE,Credential.ADMIN_ROLE);
    		authorize.requestMatchers(HttpMethod.POST, "/commento/**"). hasAnyAuthority(Credential.DEFAULT_ROLE,Credential.ADMIN_ROLE);
    		authorize.requestMatchers(HttpMethod.GET, "/admin/**"). hasAnyAuthority(Credential.ADMIN_ROLE) ; 
    		authorize.requestMatchers(HttpMethod.POST, "/admin/**"). hasAnyAuthority(Credential.ADMIN_ROLE) ; 
    		authorize.anyRequest().permitAll(); });

    	httpSecurity.formLogin(form -> form
    		    .loginPage("/login")
    		    .successHandler((request, response, authentication) -> {
    		        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
    		        
    		        if (savedRequest != null) {
    		            new SavedRequestAwareAuthenticationSuccessHandler().onAuthenticationSuccess(request, response, authentication);
    		            return;
    		        }
    		        String urlProvenienza = (String) request.getSession().getAttribute("url_pre_login");
    		        
    		        if (urlProvenienza != null) {
    		            request.getSession().removeAttribute("url_pre_login");
    		            response.sendRedirect(urlProvenienza);
    		        } else {
    		            response.sendRedirect("/");
    		        }
    		    })
    		    .permitAll()
    		);
    	
    	httpSecurity.logout(logout -> { 
    		logout.logoutUrl("/logout"); 
    		logout.logoutSuccessUrl("/");
    		logout.invalidateHttpSession(true); 
    		logout.deleteCookies("JSESSIONID"); 
    		logout.clearAuthentication(true); 
    		logout.permitAll(); 
    		});

        return httpSecurity.build();
    }
}
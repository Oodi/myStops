package myStops.config;

import myStops.auth.JpaAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Ottaa <code>Spring Security</code>:n kayttoon.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * Maarittaa sallitut resurssit, sekä sivun jolle
     * ohjataan uloskirjautumisen jalkeen.
     */
    @Override
    protected final void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .authorizeRequests().antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().
                and()
                .logout()
                .logoutSuccessUrl("/");
    }

    /**
     * Yhdistaa tietokannan <code>Spring Security</code> todentamiseen.
     */
    @Configuration
    protected static class AuthenticationConfiguration
            extends GlobalAuthenticationConfigurerAdapter {

        /**
         * <code>JpaAuthenticationProvider</code>-olio.
         */
        @Autowired
        private JpaAuthenticationProvider jpaAuthenticationProvider;

        /**
         * Kaynnistaa todentamispalvelun.
         * @param auth AuthenticationManagerBuilder.
         * @throws Exception Tietokantaan yhdistamisen epaonnistuessa.
         */
        @Override
        public final void init(final AuthenticationManagerBuilder auth)
                throws Exception {
            auth.authenticationProvider(jpaAuthenticationProvider);
        }
    }

}

package socialnetwork;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        /*
         * Any user can access the /login URL and any URL under /public.
         * Access to any other resource is premitted only for authenticated users.
         */
        http
            .authorizeRequests()
                .antMatchers("/login", "/public/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin().loginPage("/login").permitAll()
                .and()
            .logout().permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        /*
         * Temporarily, define the credentials of a single user
         * for the purpose of testing authentication.
         * This code will be replaced in a later stage
         * in order to aythenticate users from a database.
         */
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("user@example.com")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
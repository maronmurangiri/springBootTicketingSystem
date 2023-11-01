package ticketing_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig {

    @Autowired
    AuthenticationSuccessHandler successHandler;
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       return http
                .authorizeRequests()
               .requestMatchers("/api/v1/sign").permitAll()
                //.requestMatchers("/api").hasAuthority("admin")
                //.anyRequest().authenticated()
                                //.anyRequest()
                                //.authenticated()
                .and()
                //.httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(anonymousAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .formLogin(formLogin ->
                        formLogin
                                  //.loginPage("/login") // Specify a custom login page if needed
                                .successHandler(successHandler)
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // Specify the URL for logout
                                .logoutSuccessUrl("/login")// Redirect to login page after logout
                                .deleteCookies("JSESSIONID") // Delete cookies, if needed
                                .invalidateHttpSession(true) // Invalidating the HTTP session

                )
               .userDetailsService(userDetailsService)
               .build();



       /* return http
                .userDetailsService(userDetailsService)
                .build();*/
    }

    @Bean
    public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
        return new AnonymousAuthenticationFilter("anonymousUser", "ROLE_ANONYMOUS", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    }
}

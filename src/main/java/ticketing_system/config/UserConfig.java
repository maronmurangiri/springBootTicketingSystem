package ticketing_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import ticketing_system.app.Business.implementation.userServiceImplementations.UserImpematation;
import ticketing_system.app.percistance.Entities.userEntities.Users;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class UserConfig {

    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/ticketing_system");
        dataSource.setUsername("develhope");
        dataSource.setPassword("Welcome@123");
        return dataSource;
    }
    @Bean
    public UserDetailsService userService() {


        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource());

        // Set a custom SQL query to retrieve user details
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, is_enabled FROM users WHERE username = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, a.role_name " +
                        "FROM users u " +
                        "INNER JOIN authorities a ON u.role_id = a.role_id " +
                        "WHERE u.username = ?");

        return  jdbcUserDetailsManager;

    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/swagger-ui/index.html"); // Set the default target URL
        return successHandler;
    }
}
